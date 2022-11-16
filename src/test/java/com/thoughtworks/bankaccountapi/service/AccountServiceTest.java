package com.thoughtworks.bankaccountapi.service;

import com.thoughtworks.bankaccountapi.model.AccountModel;
import com.thoughtworks.bankaccountapi.repository.AccountRepository;
import com.thoughtworks.bankaccountapi.request.AccountRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class AccountServiceTest {


    private AccountService accountService;
    private AccountModel accountModel;

    private AccountRepository accountRepository;

    @BeforeEach
    public void before() {
        accountRepository = mock(AccountRepository.class);
        accountModel = mock(AccountModel.class);
    }

    @Test
    public void shouldSaveAccountWhenAccountSignUpSuccessful() {
        //arrange
        AccountRequest accountRequest = new AccountRequest("amrutha", "password", "password");
        ResponseEntity expectedResponseEntity = new ResponseEntity("Sign up Successful", HttpStatus.CREATED);
        accountService = new AccountService(accountRepository);

        //act
        ResponseEntity actualResponseEntity = accountService.signup(accountRequest);

        //assert
        verify(accountRepository).save(any(AccountModel.class));
        assertThat(actualResponseEntity, is(equalTo(expectedResponseEntity)));
    }
}
