package com.contract.manager.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.contract.manager.model.*;
import com.contract.manager.service.TemplateParamService;
import com.contract.manager.service.TemplateService;
import com.contract.manager.util.CommonUtil;

import com.contract.manager.util.FileUploader;
import com.contract.manager.util.POIUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping( "/labor/template" )
public class LaborTemplateController {

    @Autowired
    TemplateService templateService;

    @Autowired
    TemplateParamService templateParamService;

    @Autowired
    CommonConfig commonConfig;

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

    @RequestMapping( "fetchParamsByTemplateId" )
    public Msg fetchParamsByTemplateId(@RequestBody HashMap<String,Object> params) {
        String templateId = (String)params.get( "templateId" );
        Template template = templateService.fetchByTemplateId( templateId );

        List<TemplateParam> officePlaceholders = templateParamService.fetchByFilePath( commonConfig.getTemplateDir() + template.getFileName() );

        Msg msg = new Msg();
        msg.setCode( 1 );
        msg.setContent( "获取模版参数成功成功" );
        msg.setPayload( officePlaceholders );
        return msg;
    }

    /**
     * 上传合同模版文件
     * @param file
     * @return
     */
    @RequestMapping( "/upload" )
    public Msg upload(@RequestParam MultipartFile file ) {
        String catCode = "cat2";
        Msg upload = FileUploader.save( file, commonConfig.getTemplateDir() );

        // 1.获取模版参数信息
        Map<String,Object> payload = (Map<String, Object>) upload.getPayload();
        List<OfficePlaceholder> officePlaceholders = POIUtil.generateParamsFromDocs( (String)payload.get( "filePath" ), "^\\${.*}$" );
        // 2.保存到数据库中
        Map<String,Object> params = new HashMap<String, Object>();
        params.put( "filePath", payload.get("filePath") );
        params.put( "officePlaceholders", officePlaceholders );
        templateParamService.addParam( params );

        payload.put( "officePlaceholders", templateParamService.fetchByFilePath( (String)payload.get("filePath") ) );

        return upload;
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

    /**
     * 根据模版文件获取模版参数
     * @return
     */
    @RequestMapping( "/param/fetch" )
    public Msg fetchParams( @RequestParam String filePath ) {
        List<TemplateParam> params = templateParamService.fetchByFilePath( filePath );

        Msg msg = new Msg();
        msg.setCode(1);
        msg.setContent( "获取模版参数" );
        msg.setPayload( params );

        return msg;
    }
}