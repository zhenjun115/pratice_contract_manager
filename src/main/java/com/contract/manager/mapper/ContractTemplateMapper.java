package com.contract.manager.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.contract.manager.model.ContractTemplate;

@Mapper
public interface ContractTemplateMapper {
	List<ContractTemplate> fetch( List<String> catCodes );

	ContractTemplate fetchByTemplateId(String templateId);
}
