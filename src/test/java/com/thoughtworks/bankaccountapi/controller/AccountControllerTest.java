package com.thoughtworks.bankaccountapi.controller;

import com.thoughtworks.bankaccountapi.request.AccountRequest;
import com.thoughtworks.bankaccountapi.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

    @Mock
    AccountService accountService;

    @InjectMocks
    AccountController accountController;


    @Test
    public void shouldBeAbleToInvokeSignUpMethodInAccountService() {
        //arrange
        AccountRequest accountRequest = new AccountRequest("amrutha", "password", "password", "amrutha@gmail.com");
        //act
        accountController.sign_up(accountRequest);
        //assert
        verify(accountService).signup(accountRequest);
    }

    @Test
    public void shouldBeAbleToInvokeAccountSummaryMethodInAccountService() {
        //arrange
        Principal principal = mock(Principal.class);
        String email = "amrutha@gmail.com";
        when(principal.getName()).thenReturn(email);
        //act
        accountController.accountSummary(principal);
        //assert
        verify(accountService).accountSummary(email);
    }
}
