package com.thoughtworks.bankaccountapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.bankaccountapi.BankAccountApiApplication;
import com.thoughtworks.bankaccountapi.model.AccountModel;
import com.thoughtworks.bankaccountapi.repository.AccountRepository;
import com.thoughtworks.bankaccountapi.request.AccountRequest;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(classes = BankAccountApiApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AccountControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    AccountRepository accountRepository;

    @BeforeEach
    public void before() {
        accountRepository.deleteAll();
    }

    @AfterEach
    public void after() {
        accountRepository.deleteAll();
    }

    @Test
    public void shouldBeAbleToSignUpWhenRequiredDetailsAreProvided() throws Exception {
        //arrange
        AccountRequest accountRequest=new AccountRequest("vaishnavi","password","password");
        String requestJson=objectMapper.writeValueAsString(accountRequest);

        //act
        MvcResult mvcResult=mockMvc.perform(
                post("/sign-up")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestJson)
        ).andReturn();

        //assert
        assertEquals(HttpStatus.CREATED.value(),mvcResult.getResponse().getStatus());
        assertEquals("Sign up Successful",mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void shouldBeAbleToLogInWhenDetailsAreProvided() throws Exception {
        //arrange
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        AccountRequest accountRequest = new AccountRequest("amrutha", bCryptPasswordEncoder.encode("password"), "password");
        AccountModel accountModel = new AccountModel(accountRequest);
        accountRepository.save(accountModel);
        String accountNumber=accountModel.getAccountNumber();
        //act
        MvcResult mvcResult=mockMvc.perform(
                get("/log-in")
                        .with(httpBasic(accountNumber,"password"))
        ).andReturn();
        //assert
        assertEquals(HttpStatus.OK.value(),mvcResult.getResponse().getStatus());
        //assertEquals("Logged in Successful",mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void shouldReturnAccountSummaryOnlyWhenUserLoggedIn() throws Exception {
        //arrange
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        AccountRequest accountRequest = new AccountRequest("amrutha", bCryptPasswordEncoder.encode("password"), "password");
        AccountModel accountModel = new AccountModel(accountRequest);
        accountRepository.save(accountModel);
        String accountNumber=accountModel.getAccountNumber();
        //act
        MvcResult mvcResult = mockMvc.perform(
                get("/accountSummary")
                        .with(httpBasic(accountNumber, "password"))
        ).andReturn();
        //assert
        assertEquals(HttpStatus.OK.value(),mvcResult.getResponse().getStatus());
    }
}
