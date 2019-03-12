package com.contract.manager.controller;

import com.contract.manager.configuration.constant.CommonConstant;
import com.contract.manager.model.*;
import com.contract.manager.model.request.TemplatePageRequest;
import com.contract.manager.service.TemplateParamService;
import com.contract.manager.service.TemplateService;
import com.contract.manager.service.WorkFlowService;
import com.contract.manager.util.CommonUtil;
import com.contract.manager.util.FileUploader;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping( "/purchase/template" )
public class PurchaseTemplateController {

    @Autowired
    TemplateService templateService;

//    @Autowired
//    TemplateParamService templateParamService;

    @Autowired
    CommonConfig commonConfig;

    @Autowired
    WorkFlowService workFlowService;

    /**
     * 新增采购模版
     * @param template 模版实体类
     * @return Msg 操作结果
     */
    @RequestMapping("add")
    public Msg add( @RequestBody Template template ) {
        template.setTemplateId( CommonUtil.randomUUID() );
        template.setCatCode( CommonConstant.CATCODE_PURCHASE );
        boolean added = templateService.add( template );

        String processId = workFlowService.startProcess( CommonConstant.TEMPLATE_PUCHASE_CREATE_WORKFLOW );
        templateService.saveWorkflow( processId, template.getTemplateId() );

        Msg msg = new Msg();
        msg.setCode( 1 );
        msg.setContent( "上传采购模版: " + added );
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
    public Msg fetch( @RequestBody TemplatePageRequest templatePageRequest ) {
        Template template = templatePageRequest.getTemplate();
        if( template == null ) {
            template = new Template();
            templatePageRequest.setTemplate( template );
        }
        template.setCatCode( CommonConstant.CATCODE_PURCHASE );

        // 获取分页信息
        Page page = templatePageRequest.getPage();
        if( page == null ) {
            page = new Page();
            templatePageRequest.setPage( page );
        }

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

//    @RequestMapping( "fetchParamsByTemplateId" )
//    public Msg fetchParamsByTemplateId(@RequestBody HashMap<String,Object> params) {
//        String templateId = (String)params.get( "templateId" );
//        Template template = templateService.fetchByTemplateId( templateId );
//
//        List<TemplateParam> officePlaceholders = templateParamService.fetchByFilePath( commonConfig.getTemplateDir() + template.getFileName() );
//
//        Msg msg = new Msg();
//        msg.setCode( 1 );
//        msg.setContent( "获取模版参数成功成功" );
//        msg.setPayload( officePlaceholders );
//        return msg;
//    }

    /**
     * 上传合同模版文件
     * @param file
     * @return
     */
    @RequestMapping( "/upload" )
    public Msg upload(@RequestParam MultipartFile file ) {
        String catCode = CommonConstant.CATCODE_PURCHASE;
        Msg upload = FileUploader.save( file, commonConfig.getTemplateDir() );

        // 1.获取模版参数信息
        // Map<String,Object> payload = (Map<String, Object>) upload.getPayload();
        // List<OfficePlaceholder> officePlaceholders = POIUtil.generateParamsFromDocs( (String)payload.get( "filePath" ), "^\\$.*}$" );
        // 2.保存到数据库中
        // Map<String,Object> params = new HashMap<String, Object>();
        // params.put( "filePath", payload.get("filePath") );
        // params.put( "officePlaceholders", officePlaceholders );
        /// templateParamService.addParam( params );

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


}