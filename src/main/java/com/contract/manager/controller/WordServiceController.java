package com.contract.manager.controller;

import com.contract.manager.model.CommonConfig;
import com.contract.manager.model.Contract;
import com.contract.manager.model.ContractTemplate;
import com.contract.manager.model.Msg;
import com.contract.manager.service.ContractService;
import com.contract.manager.service.ContractTemplateService;
import com.contract.manager.util.CommonUtil;
import com.contract.manager.util.POIUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping( "/interface" )
public class WordServiceController {

    @Autowired
    ContractService contractService;

    @Autowired
    ContractTemplateService templateService;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    CommonConfig commonConfig;

    @Autowired
    HttpServletResponse httpServletResponse;

    /**
     * 获取所有模版
     * @param params
     * @return
     */
    @RequestMapping( "template/fetch" )
    public Msg fetchTemplate( @RequestBody Map<String,Object> params ) {
        Msg msg = new Msg();
        msg.setCode( 1 );
        msg.setContent( "获取成功" );
        List<String> catCodes = (List<String>)params.get( "catCodes" );
        List<ContractTemplate> templates = templateService.fetch( catCodes );
        msg.setPayload( templates );
        return msg;
    }

    /**
     * 通过contractId获取合同
     * @param params
     * @return
     */
    @RequestMapping( "contract/fetch" )
    public Msg fetchContract( @RequestBody Map<String,Object> params ) {
        Msg msg = new Msg();
        String contractId = ( String ) params.get( "contractId" );
        Contract contract = contractService.fetch( contractId );
        msg.setPayload( contract );
        return msg;
    }

    /**
     * 查看模版内容
     * @param params
     * @return
     */
    @RequestMapping( "template/view" )
    public Msg viewTemplate(@RequestBody Map<String,Object> params) {
        String templateId = ( String ) params.get( "templateId" );
        ContractTemplate template = templateService.fetchByTemplateId( templateId );
        Map<String,Object> payload = new HashMap<String,Object>();
        payload.put( "templateId", template.getTemplateId() );
        payload.put( "uri", "/pageoffice/demoTemplate" );

        Msg msg = new Msg();
        msg.setCode( 1 );
        msg.setContent( "获取成功" );
        msg.setPayload( payload );

        return msg;
    }

    /**
     * 查看合同内容
     * @param params
     * @return
     */
    @RequestMapping( "contract/view" )
    public Msg viewContract(@RequestBody Map<String,Object> params) {
        String contractId = ( String ) params.get( "contractId" );
        Contract contract = contractService.fetch( contractId );
        Map<String,Object> payload = new HashMap<String,Object>();
        payload.put( "contractId", contract.getContractId() );
        payload.put( "uri", "/pageoffice/demoContract" );

        Msg msg = new Msg();
        msg.setCode( 1 );
        msg.setContent( "获取成功" );
        msg.setPayload( payload );

        return msg;
    }

    @RequestMapping( "contract/generate" )
    public Msg editWord( @RequestBody Map<String,Object> params ) {
        String templateId = (String) params.get( "templateId" );
        String contractId = UUID.randomUUID().toString().replace( "-","" );

        // 1.从db中读取模版信息
        ContractTemplate template = templateService.fetchByTemplateId( templateId );

        // 2.生成合同文件
        String templateFileName = template.getFileName();
        // String templateFileName = "test_2.docx";
        String contractFileName = generateContractFileNameWithSuffix( template.getFileName(), contractId );
        // String templatePath = httpServletRequest.getServletContext().getRealPath( "/pageoffice/" + templateFileName );
        String templatePath = commonConfig.getTemplateDir() + templateFileName;
        // String contractPath = httpServletRequest.getServletContext().getRealPath( "/pageoffice/" + contractFileName );
        String contractPath = commonConfig.getContractDir() + contractFileName ;
        File contractFile = CommonUtil.copyFile( templatePath, contractPath );

        // POIUtil.generateThumbnailImageFromWord( contractPath, commonConfig.getContractDir() );

        // System.out.print();

        // 3.替换合同文件中的模版
        Map<String,String> datas = ( HashMap<String,String> ) params.get( "datas" );
        // TODO: 编码存在问题
        // datas.put( "合同管理系统自动填写-甲方公司名称", "杭州测试公司名称" );
        POIUtil.generateDocWithDatas(datas, new File( contractPath ) );

        // 4.保存合同到数据库
        contractService.save(contractId, contractFileName, templateId );

        // 6.返回合同编号和预览地址
        Msg msg = new Msg();
        msg.setCode( 1 );
        msg.setContent( "生成模版成功" );
        msg.setPayload( contractId );

        return msg;
    }

    private String generateContractFileNameWithSuffix( String templateName, String suffix ) {
        String[] templateProperties = templateName.split( "\\." );
        // String uuid = UUID.randomUUID().toString().replace( "-", "");
        return templateProperties[0] + "_" + suffix + "." + templateProperties[1];
    }
}