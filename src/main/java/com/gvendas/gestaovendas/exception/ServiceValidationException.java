package com.gvendas.gestaovendas.exception;

public class ServiceValidationException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public ServiceValidationException(String messagem) {
    super(messagem);
  }
}
