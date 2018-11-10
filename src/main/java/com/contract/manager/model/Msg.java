package com.contract.manager.model;

public class Msg
{
  private int code;
  private String content;

  public Msg() {
  }

  public Msg( int code, String content ) {
    this.code = code;
    this.content = content;
  }

  public void setCode( int code ) {
    this.code = code;
  }

  public int getCode( int code ) {
    return this.code;
  }

  public void setContent( String content ) {
    this.content = content;
  }

  public String getContent() {
    return this.content;
  }
}