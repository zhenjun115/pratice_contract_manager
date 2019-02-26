package com.contract.manager.model;

import lombok.Data;

@Data
public class Template {

	private String templateId;

	private String name;

	private String catCode;

	private String desc;

	private String file;

	private String content;

	private String title;

	private String keyword;

	private String fileName;

	// 分页信息
	private Page page;
}