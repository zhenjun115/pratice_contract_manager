package com.contract.manager.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.contract.manager.mapper.ContractMapper;
import com.contract.manager.model.Contract;

import com.contract.manager.model.request.ContractQueryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("contractService")
public class ContractService {
    @Autowired
    ContractMapper contractMapper;

    public List<Contract> selectAll() {
        return contractMapper.selectAll();
    }

    /**
     * 查询合同列表
     *
     * @param contract
     * @return
     */
    public List<Contract> queryAll(Contract contract) {
        return contractMapper.queryAll(contract);
    }

    /**
     * 查询合同列表
     *
     * @param request
     * @return
     */
    public List<Contract> queryContract(ContractQueryRequest request) {
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