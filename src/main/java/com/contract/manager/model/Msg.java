package com.contract.manager.model;

public class Msg
{
  private int code;
  private String content;

  private Object payload; //其他数据

  public Msg() {
  }

  public Msg( int code, String content ) {
    this.code = code;
    this.content = content;
  }

  public Msg(int code, String content, Object payload ) {
    this.code = code;
    this.content = content;
    this.payload = payload;
  }

  public void setCode( int code ) {
    this.code = code;
  }

  public int getCode( ) {
    return this.code;
  }

  public void setContent( String content ) {
    this.content = content;
  }

  public String getContent() {
    return this.content;
  }

  public void setPayload( Object payload ) {
    this.payload = payload;
  }

  public Object getPayload() {
    return this.payload;
  }
}