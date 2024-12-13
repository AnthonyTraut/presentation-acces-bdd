package com.equasens.monalisa.presentation_acces_bdd.datasource.decorator;

import com.vladmihalcea.flexypool.FlexyPoolDataSource;
import com.vladmihalcea.flexypool.adaptor.HikariCPPoolAdapter;
import com.vladmihalcea.flexypool.config.Configuration;
import com.vladmihalcea.flexypool.connection.ConnectionDecoratorFactoryResolver;
import com.vladmihalcea.flexypool.strategy.IncrementPoolOnTimeoutConnectionAcquiringStrategy;
import com.vladmihalcea.flexypool.strategy.RetryConnectionAcquiringStrategy;
import com.vladmihalcea.flexypool.strategy.UniqueNamingStrategy;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.UUID;

@Component
@Order(1)
@Profile("flexypool_enabled")
public class FlexyPoolDataSourceDecorator implements DataSourceDecorator {


    @Override
    public DataSource decorate(final DataSource dataSource) {
        if (dataSource instanceof HikariDataSource) {
            return new FlexyPoolDataSource<>(
                    flexypoolConfiguration((HikariDataSource) dataSource),
                    new IncrementPoolOnTimeoutConnectionAcquiringStrategy.Factory<>(4),
                    new RetryConnectionAcquiringStrategy.Factory<>(2));
        } else {
            return dataSource;
        }
    }

    private Configuration<HikariDataSource> flexypoolConfiguration(final HikariDataSource dataSource) {
        return new Configuration.Builder<>(UUID.randomUUID().toString(), dataSource, HikariCPPoolAdapter.FACTORY)
                .setConnectionProxyFactory(ConnectionDecoratorFactoryResolver.INSTANCE.resolve())
                .setMetricNamingUniqueName(UniqueNamingStrategy.INSTANCE)
                .setJmxEnabled(true)
                .setJmxAutoStart(true)
                .setConnectionLeaseTimeThresholdMillis(5000L)
                .build();
    }
}
