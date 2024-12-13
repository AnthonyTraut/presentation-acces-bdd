package com.equasens.monalisa.presentation_acces_bdd.jdbc;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

public class JDBCTest {

    @Test
    public void jdbcRequest() throws ClassNotFoundException, SQLException {
        final var postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:15-alpine"));
        postgres.start();

        // Chargement du driver
        Class.forName(postgres.getDriverClassName());
        
        // Création d'une connexion à la BDD
        final Connection connection = DriverManager.getConnection(postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword());
        
        // Initilisation du statement pour envoyer une requête
        final Statement statement = connection.createStatement();
        
        // Execution de la requête et récupération du Résultat
        final ResultSet resultSet = statement.executeQuery("select now()");
        
        // Fermeture de la connexion
        connection.close();
        
        resultSet.next();
        assertThat(resultSet.getDate(1)).hasSameTimeAs(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }
}
