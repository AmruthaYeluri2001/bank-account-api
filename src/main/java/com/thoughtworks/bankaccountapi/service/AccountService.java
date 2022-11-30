package com.thoughtworks.bankaccountapi.service;

import com.thoughtworks.bankaccountapi.model.AccountModel;
import com.thoughtworks.bankaccountapi.repository.AccountRepository;
import com.thoughtworks.bankaccountapi.request.AccountRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AccountService {


    private AccountRepository accountRepository;


    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void signup(AccountRequest accountRequest) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        accountRequest.setPassword(bCryptPasswordEncoder.encode(accountRequest.getPassword()));
        AccountModel accountModel = new AccountModel(accountRequest);
        accountRepository.save(accountModel);
    }

    public Map<String, String> accountSummary(String email) {
        AccountModel accountModel = accountRepository.findByEmail(email).get();
        Map<String, String> account_Summary = new HashMap<>();
        account_Summary.put("Account Number", accountModel.getAccountNumber());
        account_Summary.put("Name of the Account Holder", accountModel.getName());
        account_Summary.put("The Current Balance In the account", String.valueOf(accountModel.getAmount()));
        return account_Summary;
    }
}
