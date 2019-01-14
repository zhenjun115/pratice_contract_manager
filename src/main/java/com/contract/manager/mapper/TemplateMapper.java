package com.contract.manager.mapper;

import java.util.List;

import com.contract.manager.model.Template;
import org.apache.ibatis.annotations.Mapper;

import com.contract.manager.model.ContractTemplate;

@Mapper
public interface TemplateMapper {
	
	boolean add( Template template );

	List<ContractTemplate> fetch( List<String> catCodes );

	List<Template> fetch(Template template );

	Template fetchByTemplateId(String templateId);
}
