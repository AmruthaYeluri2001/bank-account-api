package com.thoughtworks.bankaccountapi.repository;

import com.thoughtworks.bankaccountapi.model.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountModel, String> {


}
