package com.contract.manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.contract.manager.service.*;
import com.contract.manager.model.*;

@RestController
public class ContractController
{
  @Autowired
  private ContractService contractService;

  @RequestMapping( "/contract" )
  public List<ContractBean> selectAll() {
    return contractService.selectAll();
  }

  @RequestMapping( "/hello" )
  public String test() {
    return "word";
  }
}
