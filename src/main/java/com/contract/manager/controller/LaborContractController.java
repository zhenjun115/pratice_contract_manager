package com.contract.manager.controller;

import com.contract.manager.configuration.constant.CommonConstant;
import com.contract.manager.model.*;
import com.contract.manager.model.request.ContractInfoUpdateRequest;
import com.contract.manager.model.request.ContractPartyUpdateRequest;
import com.contract.manager.model.request.ContractPageRequest;
import com.contract.manager.model.response.ContractPartyResponse;
import com.contract.manager.service.ContractFileService;
import com.contract.manager.service.ContractPartyService;
import com.contract.manager.service.ContractService;
import com.contract.manager.service.WorkFlowService;
import com.contract.manager.util.CommonUtil;
import com.contract.manager.util.FileUploader;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * 劳务合同,基于合同模版创建合同
     *
     * @param contract 模版编号
     * @return
     */
    @RequestMapping("/create")
    public @ResponseBody Msg create(@RequestBody Contract contract) {
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
        ContractParty partyMain = contractPartyService.fetchParty(contract, CommonConstant.PARTY_MAIN );
        //2. 获取乙方
        ContractParty partyOther = contractPartyService.fetchParty(contract, CommonConstant.PARTY_OTHER );

        //3. 返回消息
        ContractPartyResponse response = new ContractPartyResponse();
        response.setContractId( contract.getContractId() );
        response.setPartyMain( partyMain );
        response.setPartyOther( partyOther );

        Msg msg = new Msg();
        msg.setCode(1);
        msg.setContent("获取成功");
        msg.setPayload( response );

        return msg;
    }

    /**
     * 更新签约主体信息
     *
     * @param infoUpdateRequest
     * @return
     */
    @RequestMapping("/info/update")
    public @ResponseBody Msg updateInfo(@RequestBody ContractInfoUpdateRequest infoUpdateRequest ) {
        Contract contract = infoUpdateRequest.getContract();
        if( contract == null ) {
            contract = new Contract();
            infoUpdateRequest.setContract( contract );
        }

        contract.setContractId( infoUpdateRequest.getContractId() );
        boolean savedInfo = contractService.saveInfo( contract );

        Msg msg = new Msg();
        if( !savedInfo ) {
            msg.setCode(0);
            msg.setContent("保存失败");
            msg.setPayload( null );
        } else {
            msg.setCode(1);
            msg.setContent("保存成功");
            msg.setPayload( contract );
        }

        return msg;
    }

    /**
     * 更新签约主体信息
     *
     * @param updateRequest
     * @return
     */
    @RequestMapping("/party/update")
    public @ResponseBody Msg updateParty(@RequestBody ContractPartyUpdateRequest updateRequest) {
        ContractParty partyMain = updateRequest.getPartyMain();
        ContractParty partyOther = updateRequest.getPartyOther();

        partyMain.setContractId( updateRequest.getContractId() );
        partyMain.setType(CommonConstant.PARTY_MAIN);

        partyOther.setContractId( updateRequest.getContractId() );
        partyOther.setType( CommonConstant.PARTY_OTHER );

        // TODO: 增加事务管理
        boolean savedPartyMain = contractPartyService.saveParty( partyMain );
        boolean savedPartyOther = contractPartyService.saveParty( partyOther );

        Msg msg = new Msg();
        if( !savedPartyMain || !savedPartyOther ) {
            msg.setCode(0);
            msg.setContent("保存失败");
            msg.setPayload( null );
        } else {
            msg.setCode(1);
            msg.setContent("保存成功");
            msg.setPayload( updateRequest );
        }

        return msg;
    }

    /**
     * 获取合同关联的附件
     * @param contract
     * @return
     */
    @RequestMapping("/files/fetch")
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
    @RequestMapping( "/file/add" )
    public @ResponseBody Msg addFile(@RequestParam MultipartFile file, @RequestParam String fileCat, @RequestParam String contractId ) {
        // TODO: 数据库中添加文件路径信息
        Msg upload = FileUploader.save( file );
        ContractFile contractFile = new ContractFile();

        if( upload.getCode() == 200 ) {
            Map<String,Object> payload = (HashMap<String, Object>) upload.getPayload();

            contractFile.setFileId( CommonUtil.randomUUID() );
            contractFile.setContractId( contractId );
            contractFile.setFileName((String) payload.get( "fileName") );
            contractFile.setFilePath((String) payload.get( "filePath") );
            contractFile.setFileCat( fileCat );

            contractFileService.addFile( contractFile );
        }

        Msg msg = new Msg();
        msg.setCode( 1 );
        msg.setContent( "上传文件成功" );
        msg.setPayload( contractFile );

        return msg;
    }

    /**
     * 移除合同关联的附件
     * @param contractFile
     * @return
     */
    @RequestMapping( "/file/del" )
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
    @RequestMapping("/save")
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
     * @param request
     * @return
     */
    @RequestMapping( "/fetch" )
    public @ResponseBody Msg fetchAll( @RequestBody ContractPageRequest request ) {
        Contract contract = request.getContract();
        Page page = request.getPage();

        if( page == null ) {
            page = new Page();
            request.setPage( page );
        }

        if( contract == null ) {
            contract = new Contract();
            request.setContract( contract );
        }

        contract.setCatCode( CommonConstant.CATCODE_LABOR );

        PageHelper.startPage(page.getPageIndex(), page.getPageSize() );
        List<Contract> contracts = contractService.queryContract( request );

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