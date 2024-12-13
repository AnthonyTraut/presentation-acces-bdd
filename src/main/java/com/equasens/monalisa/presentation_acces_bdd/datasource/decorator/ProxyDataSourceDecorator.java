package com.equasens.monalisa.presentation_acces_bdd.datasource.decorator;

import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@Order(2)
@Profile("logs_jdbc_enabled")
public class ProxyDataSourceDecorator implements DataSourceDecorator {

    @Override
    public DataSource decorate(final DataSource dataSource) {
        return ProxyDataSourceBuilder.create(dataSource).logQueryBySlf4j().build();
    }
}
