package com.thoughtworks.bankaccountapi.service;

import com.thoughtworks.bankaccountapi.model.AccountModel;
import com.thoughtworks.bankaccountapi.repository.AccountRepository;
import com.thoughtworks.bankaccountapi.request.AccountRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {


    private AccountRepository accountRepository;

    private AccountModel accountModel;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public ResponseEntity signup(AccountRequest accountRequest) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        accountRequest.setPassword(bCryptPasswordEncoder.encode(accountRequest.getPassword()));
        accountModel = new AccountModel(accountRequest);
        accountRepository.save(accountModel);
        return new ResponseEntity("Sign up Successful", HttpStatus.CREATED);
    }
}
