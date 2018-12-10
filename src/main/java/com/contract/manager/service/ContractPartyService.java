package com.contract.manager.service;

import com.contract.manager.mapper.ContractPartyMapper;
import com.contract.manager.model.Contract;
import com.contract.manager.model.ContractParty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service( "contractPartyService" )
public class ContractPartyService
{
    @Autowired
    ContractPartyMapper contractPartyMapper;

    public boolean saveParty(ContractParty contractParty) {
        return contractPartyMapper.saveParty( contractParty );
    }

    public ContractParty fetchParty(Contract contract, String role ) {
        return contractPartyMapper.fetchParty( contract );
    }
}