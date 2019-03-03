package com.contract.manager.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.contract.manager.mapper.ContractMapper;
import com.contract.manager.model.Contract;

import com.contract.manager.model.request.ContractPageRequest;
import com.contract.manager.model.request.ContractWorkflowRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("contractService")
public class ContractService {
    @Autowired
    ContractMapper contractMapper;

    /**
     * 查询合同列表
     *
     * @param request
     * @return
     */
    public List<Contract> query(ContractPageRequest request) {
        return contractMapper.query(request);
    }

    /**
     * 查询合同列表
     *
     * @param request
     * @return
     */
    public List<Contract> queryContract(ContractPageRequest request) {
        return contractMapper.queryContract(request);
    }

    public Contract fetch(Contract contract) {
        return contractMapper.fetch(contract);
    }

    public Contract fetch(String contractId) {
        Contract contract = new Contract();
        contract.setContractId(contractId);
        return this.fetch(contract);
    }

    public boolean createDraft(Contract contract) {
        long createdCount = contractMapper.createDraft(contract);
        if (createdCount == 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据合同编号获取工作流程
     * @param contractId
     * @param workflowKey
     */
    public void fetchWorkflow( String contractId, String workflowKey) {
    }

    public Contract fetchDraft(Contract contract) {
        return contractMapper.fetchDraft(contract);
    }

    public boolean saveDraft(Contract contract) {
        long savedCount = contractMapper.saveDraft(contract);
        if (savedCount == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean save(String contractId, String contractName, String templateId) {
        Map<String, Object> contract = new HashMap<String, Object>();
        contract.put("contractId", contractId);
        contract.put("contractName", contractName);
        contract.put("templateId", templateId);
        long saved = contractMapper.save(contract);
        return saved > 0 ? true : false;
    }

    public boolean saveInfo( Contract contract ) {
        return contractMapper.saveInfo( contract );
    }

}