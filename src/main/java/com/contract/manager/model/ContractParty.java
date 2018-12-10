package com.contract.manager.model;

public class ContractParty
{
  private String contractId;
  private String name;
  private String type;
  private String role;
  private String address;
  private String phone;
  private int count;

  public String getContractId() {
    return this.contractId;
  }

  public void setContractId( String contractId ) {
    this.contractId = contractId;
  }

  public void setName( String name ) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public void setType( String type ) {
    this.type = type;
  }

  public String getType() {
    return this.type;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }
}