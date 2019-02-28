package com.contract.manager.service;

import java.util.List;
import java.util.Map;

import com.contract.manager.model.Template;
import com.contract.manager.model.request.TemplatePageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contract.manager.mapper.TemplateMapper;
import com.contract.manager.model.ContractTemplate;

@Service("templateService")
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
		// return templateMapper.fetch( template );
		return null;
	}

	public List<Template> fetch(TemplatePageRequest templatePageRequest ) {
		return templateMapper.fetch( templatePageRequest );
	}

	public Template fetchByTemplateId(String templateId) {
		return templateMapper.fetchByTemplateId( templateId );
	}

	/**
	 * 通过关键字进行查询
	 */
	public List<Template> queryByKeyword( Map<String,Object> params ) {
		return templateMapper.queryByKeyword( params );
	}
}
