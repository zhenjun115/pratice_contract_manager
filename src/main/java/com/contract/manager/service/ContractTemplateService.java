package com.contract.manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contract.manager.mapper.ContractTemplateMapper;
import com.contract.manager.model.ContractTemplate;

@Service("contractTemplateService")
public class ContractTemplateService {
	
	@Autowired
	private ContractTemplateMapper contractTemplateMapper;
	
	public List<ContractTemplate> fetch( List<String> catCodes ) {
		return contractTemplateMapper.fetch(catCodes);
	}
}
