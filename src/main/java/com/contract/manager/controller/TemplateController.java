package com.contract.manager.controller;

import com.contract.manager.model.Msg;
import com.contract.manager.model.Page;
import com.contract.manager.model.Template;
import com.contract.manager.model.request.TemplatePageRequest;
import com.contract.manager.model.response.TemplatePageResponse;
import com.contract.manager.service.TemplateService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping( "/template" )
public class TemplateController {

	@Autowired
	private TemplateService templateService;

	/**
	 * @param request 模版查询参数
	 * @return Msg 查询结果
	 */
	@RequestMapping( "/fetch" )
	public @ResponseBody Msg fetch( @RequestBody TemplatePageRequest request) {
		Page page = request.getPage();

		if( page == null ) {
			page = new Page();
			request.setPage( page );
		}

		PageHelper.startPage( page.getPageIndex(), page.getPageSize() );
		List<Template> templates = templateService.fetch( request );

		TemplatePageResponse payload = new TemplatePageResponse();
		payload.setPage( page );
		payload.setTemplates( templates );

		Msg msg = new Msg();
		msg.setCode( 1 );
		msg.setContent( "获取成功" );
		msg.setPayload( payload );
		
		return msg;
	}
}