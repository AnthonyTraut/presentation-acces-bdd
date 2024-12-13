package com.equasens.monalisa.presentation_acces_bdd.flexypool;

import com.equasens.monalisa.presentation_acces_bdd.common.context.AbstractTest;
import com.equasens.monalisa.presentation_acces_bdd.util.DataSourceUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

@SpringBootTest(properties = {"spring.datasource.hikari.minimum-idle=2",
        "spring.datasource.hikari.maximum-pool-size=3",
        "spring.datasource.hikari.maximum-pool-size=3",
        "logging.level.com.zaxxer.hikari=TRACE"})
@ActiveProfiles({"auto_commit_disabled", "open_in_view_disabled", "flexypool_enabled"})
public class FlexyPoolTest extends AbstractTest {

    @Autowired
    private DataSource dataSource;

    @Test
    void itShouldGetPostWithoutOpenInView() throws Exception {
        DataSourceUtils.logConnectionsState();
        
        // Utilisation des 2 connections minimum
        final Connection connection1 = dataSource.getConnection();
        final Connection connection2 = dataSource.getConnection();
        
        DataSourceUtils.logConnectionsState();
        
        // Ajout d'une connection supplémentaire
        final Connection connection3 = dataSource.getConnection();
        
        DataSourceUtils.logConnectionsState();
        
        // Ajout d'une connexion supplémentaire en overflow
        final Connection connection4 = dataSource.getConnection();

        DataSourceUtils.logConnectionsState();
    }
}
