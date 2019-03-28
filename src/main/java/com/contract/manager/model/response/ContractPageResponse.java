package com.contract.manager.model.response;

import com.contract.manager.model.Contract;
import com.contract.manager.model.Page;
import lombok.Data;

import java.util.List;

/**
 * @Date: 2019-03-28 17:20
 **/
@Data
public class ContractPageResponse {

    // 查询关键字
    private String keyword;
    // 合同状态
    private List<String> status;
    // 分类
    private List<String> catCode;
    // 合同信息
    private List<Contract> contractList;
    
    // 分页信息
    private Page page;
}
