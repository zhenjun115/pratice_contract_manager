package com.contract.manager.mapper;

import com.contract.manager.model.ContractParty;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ContractPartyMapper {
    boolean saveParty( ContractParty contractParty );
    boolean saveParties(List<ContractParty> partyList);
    ContractParty fetchParty(Map<String,Object> params);
}
