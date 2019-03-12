package com.contract.manager.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class Contract {

	private String contractId;

	// 合同名称
	private String conname;

	private String description;

	@NotBlank(message = "{contract.templateId.notBlank}")
	private String templateId;

	private List<ContractParty> partyList;

	private ContractParty partyA; // 甲方信息
	private ContractParty partyB; // 乙方信息

	//合同状态
	private String status;

	//分类编号
	private String catCode;

	private String keyword;

	private String filePath;
}