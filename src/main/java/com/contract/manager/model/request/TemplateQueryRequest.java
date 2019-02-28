package com.contract.manager.model.request;

import com.contract.manager.model.Page;

import java.util.List;

public class TemplateQueryRequest extends Page {
    private List<String> catCodes;

    public void setCatCodes( List<String> catCodes ) {
        this.catCodes = catCodes;
    }

    public List<String> getCatCodes() {
        return this.catCodes;
    }

}