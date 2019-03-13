package com.contract.manager.controller;

import com.contract.manager.model.CommonConfig;
import com.contract.manager.model.Contract;
import com.contract.manager.model.ContractTemplate;
import com.contract.manager.model.Msg;
import com.contract.manager.service.ContractService;
import com.contract.manager.service.ContractTemplateService;
import com.contract.manager.util.CommonUtil;
import com.contract.manager.util.POIUtil;

import com.zhuozhengsoft.pageoffice.FileSaver;
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
    public Msg generateWord( @RequestBody Map<String,Object> params ) {
        String templateId = (String) params.get( "templateId" );
        String contractId = UUID.randomUUID().toString().replace( "-","" );

        // 1.从db中读取模版信息
        ContractTemplate template = templateService.fetchByTemplateId( templateId );

        // 2.生成合同文件
        String templateFileName = template.getFileName();
        String contractPath = generateContractFilePathWithSuffix( templateFileName, contractId );
        String templatePath = commonConfig.getTemplateDir() + template.getFilePath();
        String contractFileName = contractId + "_" + templateFileName;
        CommonUtil.copyFile( templatePath, commonConfig.getContractDir() + contractPath );

        // 3.替换合同文件中的模版
        Map<String,Object> datas = ( HashMap<String,Object> ) params.get( "datas" );

        POIUtil.generateDocWithDatas(datas, new File( commonConfig.getContractDir() + contractPath ) );

        // 4.保存合同到数据库
        contractService.save( contractId, contractFileName, contractPath, templateId );

        // 6.返回合同编号和预览地址
        Msg msg = new Msg();
        msg.setCode( 1 );
        msg.setContent( "生成合同成功" );
        msg.setPayload( contractId );

        return msg;
    }

    @RequestMapping( "contract/update" )
    public Msg editWord( @RequestBody Map<String,Object> params ) {
        String contractId = (String) params.get( "contractId" );

        // 1.从db中读取合同信息
        Contract contract = contractService.fetch( contractId );

        String contractPath = commonConfig.getContractDir() + contract.getConname();

        // 3.替换合同文件中的模版
        Map<String,Object> datas = ( HashMap<String,Object> ) params.get( "datas" );

        POIUtil.generateDocWithDatas(datas, new File( contractPath ) );

        // 6.返回合同编号和预览地址
        Msg msg = new Msg();
        msg.setCode( 1 );
        msg.setContent( "更新模版成功" );
        msg.setPayload( contractId );

        return msg;
    }

    @RequestMapping( "contract/save" )
    public void saveWord() {
        FileSaver fs=new FileSaver(httpServletRequest,httpServletResponse);
        fs.saveToFile(commonConfig.getContractDir() + fs.getFileName() );
        fs.close();
    }

    @RequestMapping( "contract/demo" )
    public Msg demo( ) {
        // demo文件路径
        String demoFile = "test_demo.docx";
        String targetFile = "test_demo_result.docx";

        Msg msg = new Msg();
        // 读取word文件
        String filePath = commonConfig.getContractDir() + demoFile;
        String targetPath = commonConfig.getContractDir() + targetFile;
        POIUtil.testDemo( filePath, targetPath );

        return msg;
    }

    private String generateContractFilePathWithSuffix(String templateName, String suffix ) {
        String[] templateProperties = templateName.split( "\\." );
        // String uuid = UUID.randomUUID().toString().replace( "-", "");
        return suffix + "." + templateProperties[1];
    }
}
