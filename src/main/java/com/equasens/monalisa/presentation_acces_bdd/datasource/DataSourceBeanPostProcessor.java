package com.equasens.monalisa.presentation_acces_bdd.datasource;

import com.equasens.monalisa.presentation_acces_bdd.datasource.decorator.DataSourceDecorator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class DataSourceBeanPostProcessor implements BeanPostProcessor {

    private final List<DataSourceDecorator> dataSourceDecorators;

    public DataSourceBeanPostProcessor(List<DataSourceDecorator> dataSourceDecorators) {
        this.dataSourceDecorators = dataSourceDecorators.stream().sorted(AnnotationAwareOrderComparator.INSTANCE).toList();
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof DataSource dataSource) {
            return dataSourceDecorators.stream()
                    .reduce(dataSource,
                            (currentDataSource, decorator) -> decorator.decorate(currentDataSource),
                            (a, b) -> a);
        }
        return bean;
    }
}
