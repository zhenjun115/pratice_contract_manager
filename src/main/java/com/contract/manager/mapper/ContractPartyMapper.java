package com.contract.manager.mapper;

import com.contract.manager.model.ContractParty;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface ContractPartyMapper {
    boolean saveParty( ContractParty contractParty );
    ContractParty fetchParty(Map<String,Object> params);
}
