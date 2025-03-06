package com.aady.swiftclaimai.model;

public class ResponseMessage {
  private String message;

  public ResponseMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
  //ai api post method

  public void setMessage(String message) {
    this.message = message;
  }
} 