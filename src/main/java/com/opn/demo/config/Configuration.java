package com.opn.demo.config;

import com.opn.demo.facade.TransactionMoneyFacade;
import com.opn.demo.facade.UserManagementFacade;
import com.opn.demo.facade.impl.TransactionMoneyFacadeImpl;
import com.opn.demo.facade.impl.UserManagementFacadeImpl;
import com.opn.demo.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@org.springframework.context.annotation.Configuration
public class Configuration {

  @Bean
  public TransactionMoneyFacade transactionMoneyFacade(UserService userService){
    return new TransactionMoneyFacadeImpl(userService);
  }

  @Bean
  public UserManagementFacade userManagementFacade(
        UserService userService,
        AccountService accountService,
        FullNameService fullNameService,
        AddressUserService addressUserService,
        AddressService addressService
  ) {
    return new UserManagementFacadeImpl(
          userService,
          accountService,
          fullNameService,
          addressUserService,
          addressService
    );
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
