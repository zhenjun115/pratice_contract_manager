package com.contract.manager.service;

import java.util.List;

import com.contract.manager.mapper.ContractMapper;
import com.contract.manager.model.Contract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service( "contractService" )
public class ContractService
{
  @Autowired
  ContractMapper contractMapper;

  public List<Contract> selectAll() {
    return contractMapper.selectAll();
  }

  public Contract fetch( Contract contract ) {
    return contractMapper.fetch( contract );
  }

  public boolean createDraft( Contract contract ) {
    long createdCount = contractMapper.createDraft( contract );
    if( createdCount == 1 ) {
      return true;
    } else {
      return false;
    }
  }

  public Contract fetchDraft( Contract contract ) {
    return contractMapper.fetchDraft( contract );
  }

  public boolean saveDraft( Contract contract ) {
    long savedCount = contractMapper.saveDraft( contract );
    if( savedCount == 1 ) {
      return true;
    } else {
      return false;
    }
  }
}