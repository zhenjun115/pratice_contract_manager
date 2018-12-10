package com.contract.manager.mapper;

import com.contract.manager.model.Contract;
import com.contract.manager.model.ContractParty;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ContractPartyMapper {
    boolean saveParty( ContractParty contractParty );
    ContractParty fetchParty(Contract contract );
}
