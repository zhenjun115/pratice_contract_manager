package com.contract.manager.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CommonConfig
{
    @Value( "${contract.dir}" )
    private String contractDir;

    @Value( "${template.dir}" )
    private String templateDir;

    public String getContractDir() {
        return this.contractDir;
    }

    public String getTemplateDir() {
        return this.templateDir;
    }
}