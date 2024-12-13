package com.equasens.monalisa.presentation_acces_bdd.hibernate;

import com.equasens.monalisa.presentation_acces_bdd.model.Forum;
import com.equasens.monalisa.presentation_acces_bdd.model.Post;
import org.hibernate.Session;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.sql.*;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

public class HibernateTest {

    @Test
    public void hibernatePostgresRequest() {
        final var postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:15-alpine"));
        postgreSQLContainer.start();
        final Session session = openSession(postgreSQLContainer, "org.hibernate.dialect.PostgreSQLDialect");
        session.beginTransaction();

        final Post post = new Post();
        post.setTitre("titre");
        post.setContenu("contenu");
        session.persist(post);

        String jpql = "SELECT CONCAT(p.titre, ' ', p.contenu) FROM Post p";
        List<String> results = session.createQuery(jpql, String.class).getResultList();
        assertThat(results).containsExactly("titre contenu");

        final Post result2 = session.createQuery("SELECT p FROM Post p", Post.class).getSingleResult();
        assertThat(result2.getId()).isEqualTo(1);

        session.getTransaction().commit();
        session.close();
        postgreSQLContainer.stop();
    }

    @Test
    public void hibernateMySqlRequest() {
        final var mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.4.3"));
        mySQLContainer.start();
        final Session session = openSession(mySQLContainer, "org.hibernate.dialect.MySQL8Dialect");
        session.beginTransaction();

        final Post post = new Post();
        post.setTitre("titre");
        post.setContenu("contenu");
        session.persist(post);

        final List<String> result1 = session.createQuery("SELECT CONCAT(p.titre, ' ', p.contenu) FROM Post p", String.class)
                .getResultList();
        assertThat(result1).containsExactly("titre contenu");


        final Post result2 = session.createQuery("SELECT p FROM Post p", Post.class).getSingleResult();
        assertThat(result2.getId()).isEqualTo(1);


        session.getTransaction().commit();
        session.close();
        mySQLContainer.stop();
    }

    @Test
    public void jdbcRequest() throws ClassNotFoundException, SQLException {
        final var postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:15-alpine"));
        postgres.start();

        Class.forName(postgres.getDriverClassName());
        final Connection connection = DriverManager.getConnection(postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword());

        final Statement statement = connection.createStatement();
        statement.executeUpdate("create sequence forum_sequence start with 1 increment by 1");
        statement.executeUpdate("create sequence post_sequence start with 1 increment by 1");
        statement.executeUpdate("""
                    create table Forum (
                        id bigint not null,
                        titre varchar(255),
                        primary key (id)
                    )
                """);
        statement.executeUpdate("""
                    create table Post (
                        forum_id bigint,
                        id bigint not null,
                        contenu varchar(255),
                        titre varchar(255),
                        primary key (id)
                    )
                """);
        statement.executeUpdate("""
                    alter table if exists Post
                    add constraint FK_FORUM
                    foreign key (forum_id)
                    references Forum
                """);
        statement.executeUpdate("""
                    insert into
                        Post (contenu, forum_id, titre, id)
                    values
                        ('contenu', null, 'titre', nextval('post_sequence'))
                """);

        final ResultSet resultSet1 = statement.executeQuery("""
                    select
                            (p.titre||' '||p.contenu)
                        from
                            Post p
                """);

        resultSet1.next();
        assertThat(resultSet1.getString(1)).isEqualTo("titre contenu");

        final ResultSet resultSet2 = statement.executeQuery("""
                    select
                        p.id,
                        p.contenu,
                        p.forum_id,
                        p.titre
                    from
                        Post p
                """);


        resultSet2.next();
        assertThat(resultSet2.getLong(1)).isEqualTo(1L);
        assertThat(resultSet2.getString(2)).isEqualTo("contenu");
        assertThat(resultSet2.getLong(3)).isEqualTo(0L);
        assertThat(resultSet2.getString(4)).isEqualTo("titre");
        connection.close();
    }

    private Session openSession(final JdbcDatabaseContainer<?> jdbcDatabaseContainer, final String dialect) {
        final Properties settings = new Properties();
        settings.put(Environment.JAKARTA_JDBC_DRIVER, jdbcDatabaseContainer.getDriverClassName());
        settings.put(Environment.JAKARTA_JDBC_URL, jdbcDatabaseContainer.getJdbcUrl());
        settings.put(Environment.JAKARTA_JDBC_USER, jdbcDatabaseContainer.getUsername());
        settings.put(Environment.JAKARTA_JDBC_PASSWORD, jdbcDatabaseContainer.getPassword());
        settings.put(Environment.DIALECT, dialect);
        settings.put(Environment.HBM2DDL_AUTO, "create-drop");

        settings.put(Environment.SHOW_SQL, "true");
        settings.put(Environment.FORMAT_SQL, "true");

        final Configuration configuration = new Configuration();
        configuration.setProperties(settings);
        configuration.addAnnotatedClass(Forum.class);
        configuration.addAnnotatedClass(Post.class);

        final ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();

        return configuration.buildSessionFactory(serviceRegistry).openSession();
    }
}