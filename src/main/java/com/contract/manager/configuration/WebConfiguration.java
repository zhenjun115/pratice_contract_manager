package com.contract.manager.configuration;

import com.contract.manager.model.CommonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter
{
    private String allowedOrigins = "*";

    @Autowired
    CommonConfig commonConfig;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
            .addMapping("/**")
            .allowedMethods("*")
            .allowedOrigins( allowedOrigins )
            .allowCredentials( true )
            .allowedHeaders( "*" );
    }

    @Override
    public void addViewControllers( ViewControllerRegistry registry ) {
        // 配置静态资源
//        registry.addViewController("/home").setViewName("home");
//        registry.addViewController("/").setViewName("home");
//        registry.addViewController("/hello").setViewName("hello");
//        registry.addViewController("/login").setViewName("login");
    }

    @Override
    public void addResourceHandlers( ResourceHandlerRegistry registry ) {
        // TODO: 区分对待
        registry.addResourceHandler("/pageoffice/**" ).addResourceLocations( "file:" + commonConfig.getContractDir() );
        // super.addResourceHandlers( registry );
    }
}