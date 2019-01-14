package com.contract.manager.service;

import java.util.List;

import com.contract.manager.model.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contract.manager.mapper.TemplateMapper;
import com.contract.manager.model.ContractTemplate;

@Service("purchaseTemplateService")
public class TemplateService {
	
	@Autowired
	private TemplateMapper templateMapper;

	public boolean add( Template template ) {
		return templateMapper.add(template);
	}
	
	public List<ContractTemplate> fetch( List<String> catCodes ) {
		return templateMapper.fetch(catCodes);
	}

	public List<Template> fetch(Template template ) {
		return templateMapper.fetch( template );
	}

	public Template fetchByTemplateId(String templateId) {
		return templateMapper.fetchByTemplateId( templateId );
	}
}
