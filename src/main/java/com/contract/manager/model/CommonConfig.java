package com.contract.manager.model;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CommonConfig
{
    @Getter
    @Value( "${contract.dir}" )
    private String contractDir;

    @Getter
    @Value( "${template.dir}" )
    private String templateDir;

    @Getter
    @Value( "${pageoffice.licence}" )
    private String pageofficeLicenceDir;

    @Getter
    @Value("${file.dir}")
    private String fileDir;

//    public String getContractDir() {
//        return this.contractDir;
//    }
//
//    public String getTemplateDir() {
//        return this.templateDir;
//    }
//
//    public String getPageofficeLicenceDir() {
//        return this.pageofficeLicenceDir;
//    }
}