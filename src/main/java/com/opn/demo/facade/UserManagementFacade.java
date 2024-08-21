package com.opn.demo.facade;

import com.opn.demo.dto.request.UserEditionDTO;
import com.opn.demo.dto.request.UserRequestDTO;
import com.opn.demo.dto.response.UserDetailDTO;
import com.opn.demo.entity.Principal;

public interface UserManagementFacade {
  boolean canAccessResource(Principal principal, int resourceId);

  void add(UserRequestDTO usersRequestDTO);

  UserDetailDTO update(UserEditionDTO userEditionDTO, int id);
}
