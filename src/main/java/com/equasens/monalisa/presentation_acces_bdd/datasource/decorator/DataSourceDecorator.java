package com.equasens.monalisa.presentation_acces_bdd.datasource.decorator;

import javax.sql.DataSource;

public interface DataSourceDecorator {

    DataSource decorate(DataSource dataSource);
}
