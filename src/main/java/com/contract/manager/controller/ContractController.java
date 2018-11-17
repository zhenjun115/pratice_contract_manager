package com.contract.manager.controller;

import java.util.List;

import com.contract.manager.model.Contract;
import com.contract.manager.model.Msg;
import com.contract.manager.service.ContractService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContractController
{
  @Autowired
  private ContractService contractService;

  @RequestMapping( "/contract" )
  public List<Contract> selectAll() {
    return contractService.selectAll();
  }

  @RequestMapping( "/hello" )
  public String test() {
    return "word";
  }

  /**
   * 基于合同模版创建合同草稿
   * @param contract 模版编号
   * @return
   */
  @RequestMapping( "/createDraft" )
  public @ResponseBody Msg createDraft( @RequestBody @Validated Contract contract ) {
    //1. 创建草稿
    boolean created = contractService.createDraft( contract );
    //2. 创建草稿失败
    Msg msg = new Msg();
    if( created == false ) {
      msg.setCode( 0 );
      msg.setContent( "新建失败" );
    } else {
      //3. 创建草稿成功
      msg.setCode( 1 );
      msg.setContent( "新建成功" );
      msg.setPayload( created );
    }

    return msg;
  }

  /**
   * 基于合同草稿编号获取合同草稿
   * @param contract 合同草稿编号
   * @return
   */
  public @ResponseBody Msg fetchDraft( @RequestBody @Validated Contract contract ) {
    //1. 获取草稿
    contract = contractService.fetchDraft( contract );
    //2. 获取到数据
    Msg msg = new Msg();
    if( contract != null ) {
      msg.setCode( 1 );
      msg.setContent( "获取成功" );
      msg.setPayload( contract );
    } else {
      msg.setCode( 1 );
      msg.setContent( "获取失败" );
      msg.setPayload( null );
    }

    return msg;
  }

  public @ResponseBody Msg saveDraft( @RequestBody @Validated Contract contract ) {
    //1. 保存草稿
    boolean saved = contractService.saveDraft( contract );
    //2. 保存草稿成功
    //3. 保存草稿失败
  }
}
