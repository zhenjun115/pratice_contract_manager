package com.contract.manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contract.manager.mapper.PurchaseTemplateMapper;
import com.contract.manager.model.ContractTemplate;
import com.contract.manager.model.PurchaseTemplate;

@Service("purchaseTemplateService")
public class PurchaseTemplateService {
	
	@Autowired
	private PurchaseTemplateMapper purchaseTemplateMapper;

	public boolean add( PurchaseTemplate template ) {
		return purchaseTemplateMapper.add(template);
	}
	
	public List<ContractTemplate> fetch( List<String> catCodes ) {
		return purchaseTemplateMapper.fetch(catCodes);
	}

	public List<PurchaseTemplate> fetch( PurchaseTemplate template ) {
		return purchaseTemplateMapper.fetch( template );
	}

	public PurchaseTemplate fetchByTemplateId(String templateId) {
		return purchaseTemplateMapper.fetchByTemplateId( templateId );
	}
}
