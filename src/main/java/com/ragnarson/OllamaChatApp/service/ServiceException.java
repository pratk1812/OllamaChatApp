package com.ragnarson.OllamaChatApp.service;

public class ServiceException extends Exception {

  private static final long serialVersionUID = 3955819425627927860L;

  public ServiceException() {
    super();
    // TODO Auto-generated constructor stub
  }

  public ServiceException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
    // TODO Auto-generated constructor stub
  }

  public ServiceException(String message, Throwable cause) {
    super(message, cause);
    // TODO Auto-generated constructor stub
  }

  public ServiceException(String message) {
    super(message);
    // TODO Auto-generated constructor stub
  }

  public ServiceException(Throwable cause) {
    super(cause);
    // TODO Auto-generated constructor stub
  }
}
