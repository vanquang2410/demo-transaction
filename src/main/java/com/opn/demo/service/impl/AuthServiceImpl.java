package com.opn.demo.service.impl;

import com.opn.demo.dto.request.LoginRequest;
import com.opn.demo.dto.response.LoginResponse;
import com.opn.demo.entity.Account;
import com.opn.demo.exception.account.PasswordIncorrectException;
import com.opn.demo.service.AccountService;
import com.opn.demo.service.AuthService;
import com.opn.demo.util.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

  private final AccountService accountService;

  private final JwtTokenProvider jwtTokenProvider;

  private final PasswordEncoder passwordEncoder;



  @Override
  @Transactional
  public LoginResponse login(LoginRequest loginRequest) {
    try {
      log.info("(login) loginRequest:{}",loginRequest);

      Account account = accountService.findUserByUsername(loginRequest.getUsername());

      this.equalPassword(loginRequest.getPassword(), account.getPass());

      String jwt = jwtTokenProvider.generateToken(account);

      return new LoginResponse(jwt);

    } catch (RuntimeException ex) {
      ex.printStackTrace();
      throw new RuntimeException();
    }
  }

  private void equalPassword(String passwordRaw, String passwordEncrypted) {
    if (!passwordEncoder.matches(passwordRaw, passwordEncrypted)) {
      throw new PasswordIncorrectException();
    }
  }

}
