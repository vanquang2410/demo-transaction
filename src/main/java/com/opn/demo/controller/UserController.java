package com.opn.demo.controller;


import com.opn.demo.dto.request.TransferDTO;
import com.opn.demo.dto.request.UserEditionDTO;
import com.opn.demo.dto.request.UserRequestDTO;
import com.opn.demo.dto.response.PageResponse;
import com.opn.demo.dto.response.TransactionDTO;
import com.opn.demo.dto.response.UserDetailDTO;
import com.opn.demo.facade.TransactionMoneyFacade;
import com.opn.demo.facade.UserManagementFacade;
import com.opn.demo.service.*;
import com.opn.demo.dto.response.ResponseGeneral;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.opn.demo.constant.MessageSuccessConstant;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
@Slf4j
public class UserController {

  private final UserService usersService;

  private final UserManagementFacade userManagementFacade;

  private final TransactionMoneyFacade transactionMoneyFacade;


  @GetMapping
  @PreAuthorize("hasAuthority('admin')")
  public ResponseGeneral<PageResponse<UserDetailDTO>> list(
        @RequestParam(value = "limit", defaultValue = "10") int limit,
        @RequestParam(value = "page", defaultValue = "1") int page
  ) {
    log.info("(list) limit:{} page:{}", limit, page);

    return ResponseGeneral.ofSuccess(
          MessageSuccessConstant.GET_ALL_USER_SUCCESS,
          new PageResponse<>(
                usersService.getAllUser(limit, page),
                usersService.getAllUser(limit, page).size()
          )
    );
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('user') and @userManagementFacade.canAccessResource(principal,#id)")
  public ResponseGeneral<UserDetailDTO> detail(@PathVariable int id) {

    log.info("{}", SecurityContextHolder.getContext().getAuthentication().getAuthorities());

    return ResponseGeneral.ofSuccess(
          MessageSuccessConstant.GET_USER_BY_ID,
          usersService.get(id)
    );
  }

  @PostMapping
  public ResponseGeneral<Void> add(
        @RequestBody UserRequestDTO usersRequestDTO
  ) {

    userManagementFacade.add(usersRequestDTO);

    return ResponseGeneral.ofSuccess(
          MessageSuccessConstant.ADD_USER
    );
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasAuthority('user') and @userManagementFacade.canAccessResource(principal,#id)")
  public ResponseGeneral<UserDetailDTO>update(
        @RequestBody UserEditionDTO userEditionDTO,
        @PathVariable int id
  ){
    return ResponseGeneral.ofSuccess(
          MessageSuccessConstant.EDIT_USER,
          userManagementFacade.update(userEditionDTO,id)
    );
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("(hasAuthority('admin')) or (hasAuthority('user') and @userManagementFacade.canAccessResource(principal, #id))")
  public ResponseGeneral<Void> delele(
        @PathVariable int id
  ) {

    usersService.deleteById(id);

    return ResponseGeneral.ofSuccess(
          MessageSuccessConstant.DELETE_USER
    );
  }

  @PostMapping("/{id}/transaction")
  @PreAuthorize("hasAuthority('user') and @userManagementFacade.canAccessResource(principal,#id)")
  public ResponseGeneral<TransactionDTO> transferMoney(
        @PathVariable int id,
        @RequestBody TransferDTO transferDTO
  ) {
    log.info("(transaction) id:{},transferDTO:{}",id,transferDTO);

    return ResponseGeneral.ofSuccess(
          MessageSuccessConstant.TRANSACTION_MONEY,
          transactionMoneyFacade.transfer(id, transferDTO)
    );
  }
}