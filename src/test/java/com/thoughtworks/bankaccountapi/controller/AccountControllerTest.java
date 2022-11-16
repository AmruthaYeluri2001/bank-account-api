package com.thoughtworks.bankaccountapi.controller;

import com.thoughtworks.bankaccountapi.request.AccountRequest;
import com.thoughtworks.bankaccountapi.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class AccountControllerTest {
    AccountService accountService;

    @BeforeEach
    public void before() {
        accountService = mock(AccountService.class);
    }

    @Test
    public void shouldBeAbleToInvokeSignUpMethodInAccountService() {
        //arrange
        AccountController accountController = new AccountController(accountService);
        AccountRequest accountRequest = new AccountRequest("amrutha", "password", "password");
        //act
        accountController.sign_up(accountRequest);
        //assert
        verify(accountService).signup(accountRequest);
    }

}
