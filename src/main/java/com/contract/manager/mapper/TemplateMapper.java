package com.contract.manager.mapper;

import java.util.List;
import java.util.Map;

import com.contract.manager.model.Template;
import com.contract.manager.model.request.TemplatePageRequest;
import org.apache.ibatis.annotations.Mapper;

import com.contract.manager.model.ContractTemplate;

@Mapper
public interface TemplateMapper {
	
	boolean add( Template template );

	List<ContractTemplate> fetch( List<String> catCodes );

	// List<Template> fetch(Template template );

	List<Template> fetch(TemplatePageRequest templatePageRequest);

	List<Template> queryByKeyword( Map<String,Object> params );

	Template fetchByTemplateId(String templateId);
}
