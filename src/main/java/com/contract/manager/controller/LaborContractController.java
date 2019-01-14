package com.contract.manager.controller;

import com.contract.manager.model.*;
import com.contract.manager.service.ContractFileService;
import com.contract.manager.service.ContractPartyService;
import com.contract.manager.service.ContractService;
import com.contract.manager.service.WorkFlowService;
import com.contract.manager.util.FileUploader;
import com.contract.manager.util.JwtTokenUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping( "/labor/contract" )
public class LaborContractController {
    @Autowired
    private ContractService contractService;

    @Autowired
    private ContractPartyService contractPartyService;

    @Autowired
    private ContractFileService contractFileService;

    @Autowired
    private WorkFlowService workFlowService; // 流程服务

//    @RequestMapping("/fetch")
//    public List<Contract> selectAll() {
//        return contractService.selectAll();
//    }

    /**
     * 基于合同模版创建合同草稿
     *
     * @param contract 模版编号
     * @return
     */
    @RequestMapping("/draft/create")
    public @ResponseBody Msg createDraft(@RequestBody Contract contract) {
        //1. 创建草稿
        String contractId = UUID.randomUUID().toString().replace("-", "");
        String conname = new Date().toString();
        String description = conname + "创建的合同";
        contract.setContractId( contractId ); //使用UUID为合同指定编号
        contract.setConname( conname );
        contract.setDescription( description );

        // 2. 保存合同信息
        boolean created = contractService.createDraft(contract);
        //2. 创建草稿失败
        Msg msg = new Msg();
        if (created == false) {
            msg.setCode(0);
            msg.setContent("fail");
        } else {
            //3. 创建草稿成功
            msg.setCode(1);
            msg.setContent("success");
            msg.setPayload(contract);
        }

        return msg;
    }

    /**
     * 获取合同
     * @param contractId
     * @return
     */
    @RequestMapping( "/fetch/{contractId}" )
    public @ResponseBody Msg fetch(@PathVariable String contractId ) {
        Contract contract = new Contract();
        contract.setContractId( contractId );
        contract = contractService.fetch( contract );

        Msg msg = new Msg();
        msg.setCode( 1 );
        msg.setContent( "获取成功" );
        msg.setPayload( contract );

        return msg;
    }

    /**
     * 基于合同草稿编号获取合同草稿
     *
     * @param contract 合同草稿编号
     * @return
     */
    @RequestMapping("/draft/fetch")
    public @ResponseBody Msg fetchDraft(@RequestBody @Validated Contract contract) {
        //1. 获取合同基本信息
        contract = contractService.fetchDraft(contract);
        //2. 获取到数据
        Msg msg = new Msg();
        if (contract != null) {
            msg.setCode(1);
            msg.setContent("获取成功");
            msg.setPayload(contract);
        } else {
            msg.setCode(1);
            msg.setContent("获取失败");
            msg.setPayload(null);
        }

        return msg;
    }

    /**
     * 获取签约主体信息
     *
     * @param contract
     * @return
     */
    @RequestMapping("/party/fetch")
    public @ResponseBody Msg fetchParty(@RequestBody Contract contract) {
        //1. 获取甲方
        ContractParty partyA = contractPartyService.fetchParty(contract, "partyA");
        //2. 获取乙方
        ContractParty partyB = contractPartyService.fetchParty(contract, "partyB");

        //3. 返回消息
        contract.setPartyA(partyA);
        contract.setPartyB(partyB);

        Msg msg = new Msg();
        msg.setCode(1);
        msg.setContent("获取成功");
        msg.setPayload(contract);

        return msg;
    }

    /**
     * 更新签约主体信息
     *
     * @param contract
     * @return
     */
    @RequestMapping("/contract/party/update")
    public @ResponseBody Msg updateParty(@RequestBody Contract contract) {
        ContractParty partyA = contract.getPartyA();
        ContractParty partyB = contract.getPartyB();

        partyA.setContractId( contract.getContractId() );
        partyA.setType( "partyA" );
        partyB.setContractId( contract.getContractId() );
        partyB.setType( "partyB" );

        contractPartyService.saveParty( partyA );
        contractPartyService.saveParty( partyB );

        Msg msg = new Msg();
        msg.setCode(1);
        msg.setContent("获取成功");
        msg.setPayload( contract );

        return msg;
    }

    /**
     * 获取合同关联的附件
     * @param contract
     * @return
     */
    @RequestMapping("/contract/files")
    public @ResponseBody Msg fetchFiles(@RequestBody Contract contract) {
        List<ContractFile> files = contractFileService.fetchFiles( contract );
        Msg msg = new Msg();
        msg.setContent( "附件查询成功" );
        msg.setCode( 1 );
        msg.setPayload( files );

        return msg;
    }

    /**
     * 保存合同关联的附件
     * @param file
     * @param fileCat
     * @param contractId
     * @return
     */
    @RequestMapping( "/contract/file/add" )
    public @ResponseBody Msg addFile(@RequestParam MultipartFile file, @RequestParam String fileCat, @RequestParam String contractId ) {
        Msg upload = FileUploader.save( file );
        if( upload.getCode() == 200 ) {
            Map<String,Object> payload = (HashMap<String, Object>) upload.getPayload();

            ContractFile contractFile = new ContractFile();
            contractFile.setContractId( contractId );
            contractFile.setFileName((String) payload.get( "fileName") );
            contractFile.setFilePath((String) payload.get( "filePath") );
            contractFile.setFileCat( fileCat );

            contractFileService.addFile( contractFile );
        }

        Msg msg = new Msg();
        msg.setCode( 1 );
        msg.setContent( "上传文件成功" );
        msg.setPayload( upload );

        return msg;
    }

    /**
     * 移除合同关联的附件
     * @param contractFile
     * @return
     */
    @RequestMapping( "/contract/file/del" )
    public @ResponseBody Msg deleteFile( @RequestBody ContractFile contractFile ) {
        boolean deleted = contractFileService.delFile( contractFile );
        Msg msg = new Msg();
        msg.setCode( 1 );
        msg.setContent( "移除成功" );
        msg.setPayload( deleted );

        return msg;
    }

    /**
     * 基于草稿编号保存合同草稿信息
     *
     * @param contract
     * @return
     */
    @RequestMapping("/contract/save")
    public @ResponseBody Msg saveDraft(@RequestBody @Validated Contract contract) {
        //1. 保存草稿基本信息
        boolean contractSaved = contractService.saveDraft(contract);
        //2. 保存签约主体甲方信息
        ContractParty partyA = contract.getPartyA();
        boolean partyASaved = contractPartyService.saveParty(partyA);
        //3. 保存签约主体乙方信息
        ContractParty partyB = contract.getPartyB();
        boolean partyBSaved = contractPartyService.saveParty(partyB);
        Msg msg = new Msg();
        //4. 保存草稿成功
        if (contractSaved && partyASaved && partyBSaved) {
            msg.setCode(1);
            msg.setContent("saved success");
            msg.setPayload(true);
        } else {
            msg.setCode(1);
            msg.setContent("saved fail");
            msg.setPayload(false);
        }
        //5. 保存草稿失败
        return msg;
    }

    /**
     * 获取所有合同
     * @param param
     * @return
     */
    @RequestMapping( "/fetch" )
    public @ResponseBody Msg fetchAll(@RequestBody ContractQueryParam param) {
        Contract contract = param.getContract();
        Page page = param.getPage();

        if( page == null ) {
            page = new Page();
        }

        if( contract == null ) {
            contract = new Contract();
        }

        contract.setCatCode( "cat_2" );

        PageHelper.startPage(page.getPageIndex(), page.getPageSize() );
        List<Contract> contracts = contractService.queryAll( contract );

        Map<String,Object> payload = new HashMap<String,Object>();
        payload.put( "contracts", contracts );
        payload.put( "page", page );

        Msg msg = new Msg();
        msg.setCode( 1 );
        msg.setContent( "获取成功" );
        msg.setPayload( payload );

        return msg;
    }
}