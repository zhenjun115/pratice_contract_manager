package com.contract.manager.controller;

import java.util.HashMap;
import java.util.List;

import com.contract.manager.model.Msg;
import com.contract.manager.model.Template;
import com.contract.manager.service.TemplateService;
import com.contract.manager.util.CommonUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/labor/template" )
public class LaborTemplateController {

    @Autowired
    TemplateService templateService;

    /**
     * 上传采购模版
     * @return
     */
    @RequestMapping("add")
    public Msg add( @RequestBody Template template ) {
        template.setTemplateId( CommonUtil.randomUUID() );
        template.setCatCode( "cat_2" );

        boolean added = templateService.add( template );

        Msg msg = new Msg();
        msg.setCode( 1 );
        msg.setContent( "上传劳务模版: " + added );
        msg.setPayload( template );

        return msg;
    }

    /**
     * 编辑采购模版信息
     * @return
     */
    @RequestMapping("edit")
    public Msg edit() {
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
    @RequestMapping("fetch")
    public Msg fetch( @RequestBody Template template ) {
        template.setCatCode( "cat_2" );
        List<Template> templates = templateService.fetch( template );

        Msg msg = new Msg();
        msg.setCode( 1 );
        msg.setContent( "获取采购模版成功" );
        msg.setPayload( templates );
        return msg;
    }

    @RequestMapping( "fetchById" )
    public Msg fetchById(@RequestBody HashMap<String,Object> params) {
        String templateId = (String)params.get( "templateId" );
        Template template = templateService.fetchByTemplateId( templateId );

        Msg msg = new Msg();
        msg.setCode( 1 );
        msg.setContent( "获取采购模版成功" );
        msg.setPayload( template );
        return msg;
    }

    /**
     * 删除采购模版
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