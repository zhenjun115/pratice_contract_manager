package com.contract.manager.configuration;

//import org.activiti.engine.*;
//import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
public class ActivitiConfig {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private ResourcePatternResolver resourceLoader;

    /**
     * @return
     */
//    @Bean
//    public StandaloneProcessEngineConfiguration processEngineConfiguration() {
//        StandaloneProcessEngineConfiguration configuration = new StandaloneProcessEngineConfiguration();
//        configuration.setDataSource(dataSource);
//        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
//        configuration.setAsyncExecutorActivate(false);
//        return configuration;
//    }
}