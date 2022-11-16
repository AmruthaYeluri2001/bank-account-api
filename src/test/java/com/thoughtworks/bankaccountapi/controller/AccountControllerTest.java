package com.thoughtworks.bankaccountapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.bankaccountapi.BankAccountApiApplication;
import com.thoughtworks.bankaccountapi.repository.AccountRepository;
import com.thoughtworks.bankaccountapi.request.AccountRequest;
import com.thoughtworks.bankaccountapi.service.AccountService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class AccountControllerTest {
    AccountService accountService;
    @BeforeEach
    public void before()
    {
        accountService=mock(AccountService.class);
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
