package com.contract.manager.controller;

import com.contract.manager.model.Msg;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/purchase" )
public class PurchaseContractController {

    /**
     * 上传采购模版
     * @return
     */
    @RequestMapping("template/add")
    public Msg addTemplate() {
        Msg msg = new Msg();
        msg.setCode( 1 );
        msg.setContent( "上传采购模版成功" );
        msg.setPayload( null );

        return msg;
    }

    /**
     * 编辑采购模版信息
     * @return
     */
    @RequestMapping("template/edit")
    public Msg editTemplate() {
        Msg msg = new Msg();
        msg.setCode( 1 );
        msg.setContent( "编辑采购模版成功" );
        msg.setPayload( null );

        return msg;
    }

    /**
     * 获取采购模版
     * @return
     */
    @RequestMapping("template/fetch")
    public Msg fetchTemplate() {
        Msg msg = new Msg();
        msg.setCode( 1 );
        msg.setContent( "获取采购模版成功" );
        msg.setPayload( null );
        return msg;
    }

    /**
     * 删除采购模版
     * @return
     */
    @RequestMapping("template/delete")
    public Msg deleteTemplate() {
        Msg msg = new Msg();
        msg.setCode( 1 );
        msg.setContent( "删除采购模版成功" );
        msg.setPayload( null );
        return msg;
    }
}