package com.contract.manager.controller;

import com.contract.manager.model.Msg;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping( "/purchase" )
public class PurchaseContractController {

    /**
     * 上传采购模版
     * @return
     */
    @RequestMapping("add")
    public Msg add() {
        Msg msg = new Msg();
        msg.setCode( 1 );
        msg.setContent( "上传采购" );
        msg.setPayload( null );

        return msg;
    }

    /**
     * 获取采购模版
     * @return
     */
    public Msg fetch() {
        Msg msg = new Msg();
        msg.setCode( 1 );
        msg.setContent( "获取采购模版成功" );
        msg.setPayload( null );
        return msg;
    }
}
