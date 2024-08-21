package com.opn.demo.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponse {
  private String accessToken;
  private final String tokenType = "Bearer";

  public LoginResponse(String accessToken) {
    this.accessToken = accessToken;
  }
}
