package com.thoughtworks.bankaccountapi.controller;

import com.thoughtworks.bankaccountapi.request.AccountRequest;
import com.thoughtworks.bankaccountapi.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.Principal;

import static org.mockito.Mockito.*;


public class AccountControllerTest {
    AccountService accountService;

    AccountController accountController;

    @BeforeEach
    public void before() {
        accountService = mock(AccountService.class);
        accountController = new AccountController(accountService);
    }

    @Test
    public void shouldBeAbleToInvokeSignUpMethodInAccountService() {
        //arrange
        AccountRequest accountRequest = new AccountRequest("amrutha", "password", "password");
        //act
        accountController.sign_up(accountRequest);
        //assert
        verify(accountService).signup(accountRequest);
    }

    @Test
    public void shouldBeAbleToInvokeAccountSummaryMethodInAccountService() {
        //arrange
        Principal principal=mock(Principal.class);
        String accountNumber="0816d5ee-e19d-41c6-ba7d-23188d57f000";
        when(principal.getName()).thenReturn(accountNumber);
        //act
        accountController.accountSummary(principal);
        //assert
        verify(accountService).accountSummary(accountNumber);
    }
}
