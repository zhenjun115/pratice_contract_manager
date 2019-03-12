package com.contract.manager.mapper;

import com.contract.manager.model.Activity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ActivityMapper {
    List<Activity> fetch();
    List<Activity> fetchByType( List<String> types );

    /**
     * 根据合同ID查询流程信息
     * @param contractId
     *      合同ID
     * @return 流程实例ID
     */
    String queryProcessIdByContractId( String contractId );
}
