package com.contract.manager.model;

import lombok.Data;

@Data
public class ContractParty
{
  private String contractId;
  private String name;
  private String type;
  private String role;
  private String address;
  private String phone;
  private String idNumber;
  private int count;
}