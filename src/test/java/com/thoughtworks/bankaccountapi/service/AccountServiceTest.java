package com.thoughtworks.bankaccountapi.service;

import com.thoughtworks.bankaccountapi.model.AccountModel;
import com.thoughtworks.bankaccountapi.repository.AccountRepository;
import com.thoughtworks.bankaccountapi.request.AccountRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Test
    public void shouldSaveAccountWhenAccountSignUpSuccessful() {
        //arrange
        AccountRequest accountRequest = new AccountRequest("amrutha", "password", "amrutha@gmail.com");
        //act
        accountService.signup(accountRequest);

        //assert
        verify(accountRepository).save(any(AccountModel.class));
    }

    @Test
    public void shouldReturnAccountSummaryOnlyWhenUserLoggedIn() {
        //arrange
        String email = "amrutha@gmail.com";
        AccountRequest accountRequest = new AccountRequest("amrutha", "password", "amrutha@gmail.com");
        AccountModel accountModel = new AccountModel(accountRequest);
        when(accountRepository.findByEmail(email)).thenReturn(Optional.of(accountModel));
        Map<String, String> expected_account_Summary = new HashMap<>();
        expected_account_Summary.put("Account Number", accountModel.getAccountNumber());
        expected_account_Summary.put("Name of the Account Holder", accountModel.getName());
        expected_account_Summary.put("The Current Balance In the account", String.valueOf(accountModel.getAmount()));

        //act
        Map<String, String> actual_account_Summary = accountService.accountSummary(email);
        //assert
        assertEquals(expected_account_Summary, actual_account_Summary);
    }
}
