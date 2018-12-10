package com.contract.manager.controller;

import java.util.List;
import java.util.UUID;

import com.contract.manager.model.Contract;
import com.contract.manager.model.ContractParty;
import com.contract.manager.model.Msg;
import com.contract.manager.service.ContractPartyService;
import com.contract.manager.service.ContractService;

import com.contract.manager.service.WorkFlowService;
import com.contract.manager.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class ContractController {
    @Autowired
    private ContractService contractService;

    @Autowired
    private ContractPartyService contractPartyService;

    @Autowired
    private WorkFlowService workFlowService; // 流程服务

    @RequestMapping("/contract")
    public List<Contract> selectAll() {
        return contractService.selectAll();
    }

    /**
     * 基于合同模版创建合同草稿
     *
     * @param contract 模版编号
     * @return
     */
    @RequestMapping("/contract/draft/create")
    public @ResponseBody Msg createDraft(@RequestBody @Validated Contract contract) {
        //1. 创建草稿
        contract.setContractId(UUID.randomUUID().toString().replace("-", "")); //使用UUID为合同指定编号
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
            msg.setPayload(created);
        }

        return msg;
    }

    /**
     * 基于合同模版创建合同
     */
    @RequestMapping("/workflow/contract/create/{templateId}")
    public @ResponseBody Msg create(@PathVariable String templateId, @RequestHeader HttpHeaders headers ) {
        String userName = JwtTokenUtil.getAuthenticationUser( headers );
        String contractId = UUID.randomUUID().toString().replace( "-", "" );

        String workFlow = "contract_create_flow";
        workFlowService.startProcess( workFlow, userName ); //启动劳务合同新建流程
        // workFlowService.completeTask( );

        Msg msg = new Msg();
        msg.setContent( "测试任务" );
        msg.setCode( 1 );
        msg.setPayload( contractId );

        return msg;
    }

    /**
     * 获取合同
     * @param contractId
     * @return
     */
    @RequestMapping( "/contract/fetch/{contractId}" )
    public @ResponseBody Msg fetch(@PathVariable String contractId ) {
        Contract contract = new Contract();
        contract.setContractId( contractId );
        contract = contractService.fetch( contract );

        // 加载劳务合同甲方信息
        ContractParty partyA = contractPartyService.fetchParty( contract, "partyA" );
        // 加载劳务合同乙方信息
        ContractParty partyB = contractPartyService.fetchParty( contract, "partyB" );

        // contract.setPartyA( partyA );
        // contract.setPartyB( partyB );

        Msg msg = new Msg();
        msg.setCode( 1 );
        msg.setContent( "获取成功" );
        msg.setPayload( contract );

        return msg;
    }

    /**
     * 更新信息，并更新任务状态
     * @return
     */
    @RequestMapping( "/contract/update" )
    public @ResponseBody Msg update( @RequestHeader( value="Authorization" ) String authorization, @RequestBody Contract contract ) {
        String userName = JwtTokenUtil.getAuthenticationUser( authorization );
        // 1. 更新合同信息
        // 2. 更新任务信息
        workFlowService.completeTask( "1", userName );

        Msg msg = new Msg();
        msg.setCode( 1 );
        msg.setContent( "更新成功" );
        msg.setPayload( null );

        return msg;
    }

    /**
     * 基于合同草稿编号获取合同草稿
     *
     * @param contract 合同草稿编号
     * @return
     */
    @RequestMapping("/contract/fetchDraft")
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
    @RequestMapping("/contract/party/fetch")
    public @ResponseBody Msg fetchParty(@RequestBody @Validated Contract contract) {
        //1. 获取甲方
        ContractParty partyA = contractPartyService.fetchParty(contract, "A");
        //2. 获取乙方
        ContractParty partyB = contractPartyService.fetchParty(contract, "B");

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

    @RequestMapping("/contract/files/fetch")
    public @ResponseBody Msg fetchFiles(@RequestBody @Validated Contract contract) {
        Msg msg = new Msg();
        return msg;
    }

    //TODO: 保存草稿信息和保存合同信息分开

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
     * 获取订立中的合同列表
     * @return
     */
    @RequestMapping("/contract/fetch/carryout")
    public @ResponseBody Msg fetchCarryOut() {
        List<Contract> contractList = contractService.selectAll();
        Msg msg = new Msg();
        msg.setCode( 1 );
        msg.setContent( "获取成功" );
        msg.setPayload( contractList );

        return msg;
    }

    /**
     * 获取履行中的合同列表
     * @return
     */
    @RequestMapping("/contract/fetch/process")
    public @ResponseBody Msg fetchProcess() {
        List<Contract> contractList = contractService.selectAll();
        Msg msg = new Msg();
        msg.setCode( 1 );
        msg.setContent( "获取成功" );
        msg.setPayload( contractList );

        return msg;
    }
}