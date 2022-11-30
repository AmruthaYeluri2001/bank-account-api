package com.thoughtworks.bankaccountapi.repository;

import com.thoughtworks.bankaccountapi.model.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountModel, String> {
    Optional<AccountModel> findByEmail(String email);
}
