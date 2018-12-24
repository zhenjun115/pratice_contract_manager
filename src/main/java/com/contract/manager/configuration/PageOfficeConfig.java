package com.contract.manager.configuration;

import com.contract.manager.model.CommonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PageOfficeConfig {

    @Autowired
    CommonConfig commonConfig;

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        com.zhuozhengsoft.pageoffice.poserver.Server poserver = new com.zhuozhengsoft.pageoffice.poserver.Server();
        //设置PageOffice注册成功后,license.lic文件存放的目录
        poserver.setSysPath( commonConfig.getPageofficeLicenceDir() );
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
