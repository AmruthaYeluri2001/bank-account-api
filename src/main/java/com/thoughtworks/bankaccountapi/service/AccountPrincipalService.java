package com.thoughtworks.bankaccountapi.service;

import com.thoughtworks.bankaccountapi.model.AccountModel;
import com.thoughtworks.bankaccountapi.model.AccountPrincipal;
import com.thoughtworks.bankaccountapi.repository.AccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountPrincipalService implements UserDetailsService {
    AccountRepository accountRepository;

    public AccountPrincipalService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String accountNumber) throws UsernameNotFoundException {
        AccountModel accountModel = findUserByAccountNumber(accountNumber);
        return new AccountPrincipal(accountModel);
    }

    private AccountModel findUserByAccountNumber(String accountNumber) {
        return accountRepository.findById(accountNumber).orElseThrow(()-> new UsernameNotFoundException("user not found"));
    }
}
