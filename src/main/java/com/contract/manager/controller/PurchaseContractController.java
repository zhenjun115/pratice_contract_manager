package com.contract.manager.controller;

import com.contract.manager.model.Msg;
import com.contract.manager.model.PurchaseContract;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping( "/purchase/contract" )
public class PurchaseContractController {

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
    public Msg fetch(@RequestBody PurchaseContract contract) {
        Msg msg = new Msg();
        msg.setCode( 1 );
        msg.setContent( "获取采购模版成功" );
        msg.setPayload( contract );

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