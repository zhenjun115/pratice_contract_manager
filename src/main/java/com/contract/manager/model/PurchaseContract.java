package com.contract.manager.model;

import lombok.Getter;
import lombok.Setter;

public class PurchaseContract {

	@Getter
	@Setter
	private String contractId;

	@Getter
	@Setter
	private String name;

	@Getter
	@Setter
	private String desc;
}