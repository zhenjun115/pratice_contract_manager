package com.contract.manager.model;

import javax.validation.constraints.NotBlank;

public class Contract {

  private String contractId;

  @NotBlank( message = "{contract.templateId.notBlank}" )
  private String templateId;

  public void setTemplateId( String templateId ) {
    this.templateId = templateId;
  }

  public String getTemplateId() {
    return this.templateId;
  }
}