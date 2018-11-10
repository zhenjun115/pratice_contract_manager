package com.contract.manager.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contract.manager.mapper.*;
import com.contract.manager.model.*;

@Service( "contractService" )
public class ContractService
{
  @Autowired
  ContractMapper contractMapper;

  public List<ContractBean> selectAll() {
    return contractMapper.selectAll();
  }
}