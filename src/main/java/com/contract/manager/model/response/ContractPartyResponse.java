package com.contract.manager.model.response;

import com.contract.manager.model.ContractParty;
import lombok.Data;

/**
 * @Date: 2019-02-25 17:51
 **/
@Data
public class ContractPartyResponse {

    /**
     * 合同编号
     */
    private String contractId;

    /**
     * 甲方
     */
    private ContractParty partyMain;

    /**
     * 乙方
     */
    private ContractParty partyOther;
}
