package com.thoughtworks.bankaccountapi.controller;

import com.thoughtworks.bankaccountapi.request.AccountRequest;
import com.thoughtworks.bankaccountapi.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

    @Mock
    AccountService accountService;

    @InjectMocks
    AccountController accountController;

    @Mock
    Principal principal;

    @Test
    public void shouldBeAbleToInvokeSignUpMethodInAccountService() {
        //arrange
        AccountRequest accountRequest = new AccountRequest("amrutha", "password", "amrutha@gmail.com");
        //act
        accountController.sign_up(accountRequest);
        //assert
        verify(accountService).signup(accountRequest);
    }

    @Test
    public void shouldBeAbleToInvokeAccountSummaryMethodInAccountService() {
        //arrange
        Map<String, String> expectedAccountSummaryResponse = new HashMap<>();
        when(accountService.accountSummary(principal.getName())).thenReturn(expectedAccountSummaryResponse);
        //act
        Map<String, String> actualAccountSummaryResponse = accountController.accountSummary(principal);
        //assert
        verify(accountService).accountSummary(principal.getName());
        assertThat(actualAccountSummaryResponse, is(expectedAccountSummaryResponse));
    }
}
