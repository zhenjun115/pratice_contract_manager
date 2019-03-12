package com.contract.manager.model.request;

import com.contract.manager.model.Page;
import lombok.Data;

/**
 * @Date: 2019-03-11 17:51
 **/
@Data
public class DictPageRequest {

    String keyword;
    Page page;
}
