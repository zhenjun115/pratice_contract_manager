package com.contract.manager.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.contract.manager.model.ContractTemplate;
import com.contract.manager.model.ContractTemplateQueryParam;
import com.contract.manager.model.Msg;
import com.contract.manager.service.ContractTemplateService;

@RestController
public class ContractTemplateController {
	
	@Autowired
	private ContractTemplateService contractTemplateService;

	/**
	 * @param queryParam 模版查询参数
	 * @return Msg 查询结果
	 */
	@RequestMapping( "/contract/template/fetch" )
	public @ResponseBody Msg fetch( @RequestBody ContractTemplateQueryParam queryParam ) {
		List<String> catCodes = queryParam.getCatCodes();
		List<ContractTemplate> templates = contractTemplateService.fetch(catCodes);
		// TODO: 合同文档整理
		// TODO: 加载数据到page页面
		Msg msg = new Msg();
		msg.setCode( 1 );
		msg.setContent( "获取成功" );
		msg.setPayload( templates );
		
		return msg;
	}
}
