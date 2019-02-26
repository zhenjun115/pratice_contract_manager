package com.contract.manager.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.contract.manager.configuration.constant.CommonConstant;
import com.contract.manager.model.*;
import com.contract.manager.model.request.TemplatePageRequest;
import com.contract.manager.model.request.TemplateSearchRequest;
import com.contract.manager.service.TemplateParamService;
import com.contract.manager.service.TemplateService;
import com.contract.manager.util.CommonUtil;

import com.contract.manager.util.ContractCatConstant;
import com.contract.manager.util.FileUploader;
import com.contract.manager.util.POIUtil;
import com.github.pagehelper.PageHelper;
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
        // TODO: 使用常量
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
     * 根据关键字进行搜索
     */
    @RequestMapping( "search" )
    public Msg search( @RequestBody TemplateSearchRequest templateSearchRequest) {
        String keyword = templateSearchRequest.getKeyword();
        Page page = templateSearchRequest.getPage();

        if( page == null ) {
            page = new Page();
        }

        // 搜索参数
        Map<String,Object> params = new HashMap<String,Object>();
        params.put( "keyword", keyword );
        params.put( "catCode", ContractCatConstant.CATCODE_LABOR );

        PageHelper.startPage( page.getPageIndex(), page.getPageSize() );
        List<Template> templates = templateService.queryByKeyword( params );

        Map<String,Object> payload = new HashMap<String,Object>();
        payload.put( "templates", templates );
        payload.put( "page", page );

        Msg msg = new Msg();
        msg.setCode( 1 );
        msg.setContent( "获取成功" );
        msg.setPayload( payload );

        return msg;
    }

    /**
     * 获取采购模版
     * @return
     */
    @RequestMapping("fetch")
    public Msg fetch( @RequestBody TemplatePageRequest templatePageRequest) {
        Template template = templatePageRequest.getTemplate();

        // 获取参数信息
        if( template == null ) {
            template = new Template();
            templatePageRequest.setTemplate( template );
        }

        // 获取分页信息
        Page page = templatePageRequest.getPage();
        if( page == null ) {
            page = new Page();
            templatePageRequest.setPage( page );
        }

        template.setCatCode( CommonConstant.CATCODE_LABOR );

        // 进行分页操作
        PageHelper.startPage( page.getPageIndex(), page.getPageSize() );

        // 获取数据
        List<Template> templates = templateService.fetch( templatePageRequest );

        // 封装返回数据
        Map<String,Object> payload = new HashMap<String,Object>();
        payload.put( "templates", templates );
        payload.put( "page", page );

        // 返回msg信息
        Msg msg = new Msg();
        msg.setCode( 1 );
        msg.setContent( "获取采购模版成功" );
        msg.setPayload( payload );

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

        // TODO: 使用常量
        // String catCode = "cat2";
        Msg upload = FileUploader.save( file, commonConfig.getTemplateDir() );

        //TODO: 模版参数解析存在错误

        // 1.获取模版参数信息
        // Map<String,Object> payload = (Map<String, Object>) upload.getPayload();
        // List<OfficePlaceholder> officePlaceholders = POIUtil.generateParamsFromDocs( (String)payload.get( "filePath" ), "^\\${.*}$" );
        // 2.保存到数据库中
        // Map<String,Object> params = new HashMap<String, Object>();
        // params.put( "filePath", payload.get("filePath") );
        // params.put( "officePlaceholders", officePlaceholders );
        // templateParamService.addParam( params );

        // payload.put( "officePlaceholders", templateParamService.fetchByFilePath( (String)payload.get("filePath") ) );

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