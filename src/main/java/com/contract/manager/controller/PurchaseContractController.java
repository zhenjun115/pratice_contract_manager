package com.contract.manager.controller;

import com.contract.manager.configuration.constant.CommonConstant;
import com.contract.manager.model.Contract;
import com.contract.manager.model.Msg;
import com.contract.manager.model.Page;
import com.contract.manager.model.PurchaseContract;

import com.contract.manager.model.request.ContractQueryRequest;
import com.contract.manager.service.ContractService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping( "/purchase/contract" )
public class PurchaseContractController {

    @Autowired
    ContractService contractService;

    /**
     * 上传采购合同
     * @return
     */
    @RequestMapping("add")
    public Msg add( @RequestBody PurchaseContract contract ) {
        Msg msg = new Msg();
        msg.setCode( 1 );
        msg.setContent( "上传采购模版成功" );
        msg.setPayload( contract );

        return msg;
    }

    /**
     * 获取采购合同
     * @return
     */
    @RequestMapping("fetch")
    public Msg fetch(@RequestBody ContractQueryRequest request) {

        Contract contract = request.getContract();
        Page page = request.getPage();

        if( page == null ) {
            page = new Page();
            request.setPage( page );
        }

        if( contract == null ) {
            contract = new Contract();
            request.setContract( contract );
        }

        contract.setCatCode( CommonConstant.CATCODE_PURCHASE );

        PageHelper.startPage(page.getPageIndex(), page.getPageSize() );
        List<Contract> contracts = contractService.queryContract( request );

        Map<String,Object> payload = new HashMap<String,Object>();
        payload.put( "contracts", contracts );
        payload.put( "page", page );

        Msg msg = new Msg();
        msg.setCode( 1 );
        msg.setContent( "获取成功" );
        msg.setPayload( payload );

        return msg;
    }

    /**
     * 获取采购合同
     * @return
     */
    @RequestMapping("fetchById")
    public Msg fetchById(@RequestBody HashMap<String,Object> params) {
        String contractId = (String)params.get( "contractId" );

        Msg msg = new Msg();
        msg.setCode( 1 );
        msg.setContent( "获取采购模版成功" );
        msg.setPayload( null );
        return msg;
    }

    /**
     * 删除采购合同
     * @return
     */
    @RequestMapping("delete")
    public Msg delete() {
        Msg msg = new Msg();
        msg.setCode( 1 );
        msg.setContent( "删除采购模版成功" );
        msg.setPayload( null );
        return msg;
    }
}