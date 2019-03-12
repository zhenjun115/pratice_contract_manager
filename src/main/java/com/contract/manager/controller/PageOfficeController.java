package com.contract.manager.controller;

import com.contract.manager.model.Contract;
import com.contract.manager.model.ContractFile;
import com.contract.manager.model.ContractTemplate;
import com.contract.manager.service.ContractFileService;
import com.contract.manager.service.ContractService;
import com.contract.manager.service.ContractTemplateService;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
// import java.util.UUID;

@Controller
@RequestMapping( "/pageoffice" )
public class PageOfficeController {

    @Autowired
    ContractTemplateService templateService;

    @Autowired
    ContractService contractService;

    @Autowired
    ContractFileService contractFileService;

    @Autowired
    HttpServletRequest httpServletRequest;

    /**
     * office 模版阅览地址
     * @param request
     * @param map
     * @return
     */
    @RequestMapping(value="template", method= RequestMethod.GET)
    public ModelAndView showTemplate(HttpServletRequest request, Map<String,Object> map){
        String templateId = request.getParameter( "templateId" );
        ContractTemplate template = templateService.fetchByTemplateId( templateId );
        //--- PageOffice的调用代码 开始 -----
        PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
        poCtrl.setServerPage("/pageoffice/poserver.zz");//设置授权程序servlet
        // poCtrl.addCustomToolButton("保存","Save",1); //添加自定义按钮
        // poCtrl.setSaveFilePage("/pageoffice/save");//设置保存的action
        poCtrl.webOpen(template.getFileName(), OpenModeType.docNormalEdit,"");
        map.put("pageoffice",poCtrl.getHtmlCode("PageOfficeCtrl1"));
        //--- PageOffice的调用代码 结束 -----
        ModelAndView mv = new ModelAndView("Word");
        mv.addAllObjects( map );
        return mv;
    }

    /**
     * office 合同阅览地址
     * @param request
     * @param map
     * @return
     */
    @RequestMapping(value="contract", method= RequestMethod.GET)
    public ModelAndView showContract(HttpServletRequest request, Map<String,Object> map){
        String contractId = request.getParameter( "contractId" );
        Contract contract = contractService.fetch( contractId );
        //--- PageOffice的调用代码 开始 -----
        PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
        poCtrl.setServerPage( "/pageoffice/poserver.zz" );//设置授权程序servlet
        poCtrl.addCustomToolButton("保存","Save",1 ); //添加自定义按钮
        poCtrl.setSaveFilePage( "/interface/contract/save" );//设置保存的action
        poCtrl.webOpen( contract.getConname(), OpenModeType.docNormalEdit,"");
        map.put("pageoffice",poCtrl.getHtmlCode("PageOfficeCtrl1"));
        //--- PageOffice的调用代码 结束 -----
        ModelAndView mv = new ModelAndView("Word");
        mv.addAllObjects( map );
        return mv;
    }

    /**
     * office 合同阅览地址
     * @param request
     * @param map
     * @return
     */
    @RequestMapping(value="contractFile", method= RequestMethod.GET)
    public ModelAndView showContractFile(HttpServletRequest request, Map<String,Object> map){
        String fileId = request.getParameter( "fileId" );
        ContractFile contractFile = contractFileService.fetch( fileId );
        //--- PageOffice的调用代码 开始 -----
        PageOfficeCtrl poCtrl=new PageOfficeCtrl( request );
        poCtrl.setServerPage( "/pageoffice/poserver.zz" );//设置授权程序servlet
        poCtrl.addCustomToolButton("保存","Save",1 ); //添加自定义按钮
        // TODO: 合同附件的保存按钮
        poCtrl.setSaveFilePage( "/interface/contract/save" );//设置保存的action
        poCtrl.webOpen( contractFile.getFilePath(), OpenModeType.docNormalEdit,"");
        map.put("pageoffice",poCtrl.getHtmlCode("PageOfficeCtrl1"));
        //--- PageOffice的调用代码 结束 -----
        ModelAndView mv = new ModelAndView("Word");
        mv.addAllObjects( map );
        return mv;
    }

    @RequestMapping( "demoTemplate" )
    public ModelAndView demoTemplate( @RequestParam String templateId ) {
        ModelAndView modelAndView = new ModelAndView( "template_view" );
        Map<String,Object> dataObjects = new HashMap<String,Object>();
        dataObjects.put( "templateId",templateId );
        modelAndView.addAllObjects( dataObjects );
        return modelAndView;
    }

    @RequestMapping( "demoContract" )
    public ModelAndView demoContract( @RequestParam String contractId ) {
        ModelAndView modelAndView = new ModelAndView( "contract_view" );
        Map<String,Object> dataObjects = new HashMap<String,Object>();
        dataObjects.put( "contractId",contractId );
        modelAndView.addAllObjects( dataObjects );
        return modelAndView;
    }

    @RequestMapping( "demoContractFile" )
    public ModelAndView demoContractFile( @RequestParam String fileId ) {
        ModelAndView modelAndView = new ModelAndView( "contract_file_view" );
        Map<String,Object> dataObjects = new HashMap<String,Object>();
        dataObjects.put( "fileId",fileId );
        modelAndView.addAllObjects( dataObjects );
        return modelAndView;
    }
}