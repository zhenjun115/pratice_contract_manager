package com.contract.manager.model.request;

import com.contract.manager.model.Contract;
import com.contract.manager.model.ContractParty;
import lombok.Data;

/**
 * @Date: 2019-02-25 17:51
 **/
@Data
public class ContractInfoUpdateRequest {

    /**
     * 合同编号
     */
    private String contractId;

    /**
     * 合同
     */
    private Contract contract;
}
