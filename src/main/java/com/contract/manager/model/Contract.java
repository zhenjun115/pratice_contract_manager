package com.contract.manager.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

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

	@Getter
	@Setter
	//合同状态
	private String status;

	@Getter
	@Setter
	//分类编号
	private String catCode;

	public String getContractId() {
		return this.contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getTemplateId() {
		return this.templateId;
	}

	public List<ContractParty> getPartyList() {
		return partyList;
	}

	public void setPartyList(List<ContractParty> partyList) {
		this.partyList = partyList;
	}

	public void setPartyA(ContractParty partyA) {
		this.partyA = partyA;
	}

	public ContractParty getPartyA() {
		return this.partyA;
	}

	public void setPartyB(ContractParty partyB) {
		this.partyB = partyB;
	}

	public ContractParty getPartyB() {
		return this.partyB;
	}

	public String getConname() {
		return conname;
	}

	public void setConname(String conname) {
		this.conname = conname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}