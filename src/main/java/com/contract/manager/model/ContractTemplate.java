package com.contract.manager.model;

import lombok.Data;

@Data
public class ContractTemplate {

    private String title;
    private String cat_code;
    private String content;
    private String created_time;
    private String last_modified_time;
    private String templateId;
    private String fileName;
}
