package com.contract.manager.model.request;

import com.contract.manager.model.Page;
import lombok.Data;

@Data
public class TemplateSearchRequest {

    private String keyword;
    private Page page;
}
