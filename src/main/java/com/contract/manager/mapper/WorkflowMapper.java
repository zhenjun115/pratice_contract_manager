package com.contract.manager.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WorkflowMapper {
    /**
     * 根据合同ID查询流程信息
     * @param contractId
     *      合同ID
     * @return 流程实例ID
     */
    String queryProcessIdByContractId(String contractId);
}
