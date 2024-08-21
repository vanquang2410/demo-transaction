package com.opn.demo.repository;


import com.opn.demo.dto.response.AccountInformationBasic;
import com.opn.demo.entity.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;



public interface AccountRepository extends JpaRepository<Account, Integer> {
  @Modifying
  @Transactional
  @Query("""
        UPDATE Account a SET
         a.username = :username, a.pass = :pass 
         WHERE a.id = :id
        """)
  void update(int id, String username, String pass);

  @Query("""
        SELECT new com.opn.demo.dto.response.AccountInformationBasic(u.id, a.username,a.pass,a.role) from Account a
        join User u on a.id = u.accountId
        where u.id = :id
        """)
  AccountInformationBasic findByUserId(int id);


  Account findAccountByUsername(String username);
}
