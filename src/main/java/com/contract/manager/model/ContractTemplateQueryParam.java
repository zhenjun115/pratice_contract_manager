package com.contract.manager.model;

import java.util.List;

public class ContractTemplateQueryParam extends Page {
    private List<String> catCodes;

    public void setCatCodes( List<String> catCodes ) {
        this.catCodes = catCodes;
    }

    public List<String> getCatCodes() {
        return this.catCodes;
    }

}