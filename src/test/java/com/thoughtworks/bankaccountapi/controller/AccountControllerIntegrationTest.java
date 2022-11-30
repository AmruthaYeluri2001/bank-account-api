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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        AccountRequest accountRequest = new AccountRequest("vaishnavi", "password", "vaishnavi@gmail.com");
        String requestJson = objectMapper.writeValueAsString(accountRequest);

        //act and assert
       mockMvc.perform(
                post("/sign-up")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestJson)
        ).andExpect(status().isCreated());

    }

    @Test
    public void shouldBeAbleToLogInWhenDetailsAreProvided() throws Exception {
        //arrange
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        AccountRequest accountRequest = new AccountRequest("amrutha", bCryptPasswordEncoder.encode("password"), "amrutha@gmail.com");
        AccountModel accountModel = new AccountModel(accountRequest);
        accountRepository.save(accountModel);
        String email = accountModel.getEmail();
        //act and assert
       mockMvc.perform(
                get("/log-in")
                        .with(httpBasic(email, "password"))
        ).andExpect(status().isOk());
    }

    @Test
    public void shouldReturnAccountSummaryOnlyWhenUserLoggedIn() throws Exception {
        //arrange
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        AccountRequest accountRequest = new AccountRequest("amrutha", bCryptPasswordEncoder.encode("password"), "amrutha@gmail.com");
        AccountModel accountModel = new AccountModel(accountRequest);
        accountRepository.save(accountModel);
        String email = accountModel.getEmail();
        //act and assert
        mockMvc.perform(
                get("/accountSummary")
                        .with(httpBasic(email, "password"))
        ).andExpect(status().isOk());
    }
}
