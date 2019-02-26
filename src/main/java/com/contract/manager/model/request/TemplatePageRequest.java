package com.contract.manager.model.request;

import com.contract.manager.model.Page;
import com.contract.manager.model.Template;
import lombok.Data;

@Data
public class TemplatePageRequest {

    private Template template;
    private Page page;
    private String keyword;
}
