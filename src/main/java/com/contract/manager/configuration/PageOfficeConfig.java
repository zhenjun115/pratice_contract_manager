package com.contract.manager.configuration;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PageOfficeConfig {

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        com.zhuozhengsoft.pageoffice.poserver.Server poserver = new com.zhuozhengsoft.pageoffice.poserver.Server();
        //设置PageOffice注册成功后,license.lic文件存放的目录
        poserver.setSysPath("/Users/zhenjun/Downloads/apache-tomcat-7.0.90/pageoffices/");
        ServletRegistrationBean srb = new ServletRegistrationBean(poserver);
        srb.addUrlMappings("/pageoffice/poserver.zz");
        srb.addUrlMappings("/pageoffice/posetup.exe");
        srb.addUrlMappings("/pageoffice/pageoffice.js");
        srb.addUrlMappings("/pageoffice/jquery.min.js");
        srb.addUrlMappings("/pageoffice/pobstyle.css");
        srb.addUrlMappings("/pageoffice/sealsetup.exe");
        return srb;
    }
}
