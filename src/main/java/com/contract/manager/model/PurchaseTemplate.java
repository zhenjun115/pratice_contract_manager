package com.contract.manager.model;

import lombok.Getter;
import lombok.Setter;

public class PurchaseTemplate {

	@Getter
	@Setter
	private String templateId;

	@Getter
	@Setter
	private String name;

	@Getter
	@Setter
	private String desc;

	@Getter
	@Setter
	private String file;

	@Getter
	@Setter
	private String content;

	@Getter
	@Setter
	private String title;

	@Getter
	@Setter
	private String keyword;
}