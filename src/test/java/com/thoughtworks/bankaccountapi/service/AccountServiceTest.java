package com.thoughtworks.bankaccountapi.service;

import com.thoughtworks.bankaccountapi.model.AccountModel;
import com.thoughtworks.bankaccountapi.repository.AccountRepository;
import com.thoughtworks.bankaccountapi.request.AccountRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import static org.hamcrest.MatcherAssert.assertThat;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class AccountServiceTest {



    private AccountService accountService;
    private AccountModel accountModel;

   @Autowired
   AccountRepository accountRepository;

    @BeforeEach
    public void before() {
        accountRepository =mock(AccountRepository.class);
        accountModel = mock(AccountModel.class);
    }

    @Test
    public void shouldSaveAccountWhenAccountSignUpSuccessful() {
        AccountRequest accountRequest =new AccountRequest("amrutha","password","password");
        ResponseEntity expectedResponseEntity=new ResponseEntity("Sign up Successful", HttpStatus.OK);
        accountService = new AccountService(accountRepository);

        ResponseEntity actualResponseEntity= accountService.signup(accountRequest);

        verify(accountRepository).save(any(AccountModel.class));
        assertThat(actualResponseEntity,is(equalTo(expectedResponseEntity)));
    }
}
