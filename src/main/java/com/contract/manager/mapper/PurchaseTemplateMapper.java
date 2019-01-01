package com.contract.manager.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.contract.manager.model.ContractTemplate;
import com.contract.manager.model.PurchaseTemplate;

@Mapper
public interface PurchaseTemplateMapper {
	
	boolean add( PurchaseTemplate template );

	List<ContractTemplate> fetch( List<String> catCodes );

	List<PurchaseTemplate> fetch( PurchaseTemplate template );

	PurchaseTemplate fetchByTemplateId(String templateId);
}
