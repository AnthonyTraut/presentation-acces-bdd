package com.equasens.monalisa.presentation_acces_bdd.util;

import com.vladmihalcea.flexypool.FlexyPoolDataSource;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.lang.reflect.Field;

@Slf4j
public class DataSourceUtils {

    public static int getNumberOfActivesConnections() {
        return getHikariDataSource().getHikariPoolMXBean().getActiveConnections();
    }

    public static void logNumberOfActivesConnections() {
        log.info("Nombres de connections actives : {}", getHikariDataSource().getHikariPoolMXBean().getActiveConnections());
    }

    public static void logConnectionsState() {
        log.info("Connections [active : {}, total : {}, idle : {}]",
                getHikariDataSource().getHikariPoolMXBean().getActiveConnections(),
                getHikariDataSource().getHikariPoolMXBean().getTotalConnections(),
                getHikariDataSource().getHikariPoolMXBean().getIdleConnections());
    }

    private static HikariDataSource getHikariDataSource() {
        final DataSource dataSource = SpringContext.getBean(DataSource.class);
        try {
            if (dataSource instanceof HikariDataSource) {
                return (HikariDataSource) dataSource;
            } else if (dataSource instanceof FlexyPoolDataSource<?>) {
                Field f = ((FlexyPoolDataSource) dataSource).getClass().getDeclaredField("targetDataSource"); //NoSuchFieldException
                f.setAccessible(true);
                return (HikariDataSource) f.get(dataSource);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
