package com.contract.manager.configuration;

import org.activiti.engine.*;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

// @Configuration
public class ActivitiConfig {

    // @Autowired
    private DataSource dataSource;

    /**
     * @return
     */
    // @Bean
    public StandaloneProcessEngineConfiguration processEngineConfiguration() {
        StandaloneProcessEngineConfiguration configuration = new StandaloneProcessEngineConfiguration();
        configuration.setDataSource(dataSource);
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        configuration.setAsyncExecutorActivate(false);
        configuration.setJdbcUrl( "jdbc:mysql://47.98.146.160:3306/contract?useUnicode=true&characterEncoding=UTF-8" );
        configuration.setJdbcDriver( "com.mysql.jdbc.Driver" );
        configuration.setJdbcUsername( "root" );
        configuration.setJdbcPassword( "root" );
        return configuration;
    }
}