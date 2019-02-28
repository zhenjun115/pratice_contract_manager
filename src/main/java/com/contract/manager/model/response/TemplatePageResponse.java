package com.contract.manager.model.response;

import com.contract.manager.model.Page;
import com.contract.manager.model.Template;
import lombok.Data;

import java.util.List;

/**
 * @Date: 2019-03-01 07:02
 **/
@Data
public class TemplatePageResponse {

    private Page page;
    private List<Template> templates;
}
