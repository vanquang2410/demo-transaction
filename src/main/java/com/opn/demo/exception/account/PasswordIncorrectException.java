package com.opn.demo.exception.account;

import com.opn.demo.constant.MessageExceptionConstant;
import com.opn.demo.exception.base.ForbiddenException;

public class PasswordIncorrectException extends ForbiddenException {

  public PasswordIncorrectException() {
    super(MessageExceptionConstant.PASSWORD_IS_INCORRECT);
  }
}
