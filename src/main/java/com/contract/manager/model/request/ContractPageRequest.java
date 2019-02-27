package com.contract.manager.model.request;

import com.contract.manager.model.Contract;
import com.contract.manager.model.Page;
import lombok.Data;

import java.util.List;

@Data
public class ContractPageRequest {

    // 查询关键字
    private String keyword;
    // 合同状态
    private List<String> status;
    // 分类
    private List<String> catCode;
    // 合同信息
    private Contract contract;
    // 分页信息
    private Page page;
}
