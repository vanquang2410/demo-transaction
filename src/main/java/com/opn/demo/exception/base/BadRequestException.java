package com.opn.demo.exception.base;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseException {
  public BadRequestException(String message) {
    super(message,HttpStatus.BAD_REQUEST);
  }
}
