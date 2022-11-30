package com.thoughtworks.bankaccountapi.controller;

import com.thoughtworks.bankaccountapi.BankAccountApiApplication;
import com.thoughtworks.bankaccountapi.model.AccountModel;
import com.thoughtworks.bankaccountapi.repository.AccountRepository;
import com.thoughtworks.bankaccountapi.repository.TransactionRepository;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = BankAccountApiApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TransactionControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @BeforeEach
    public void before() {
        transactionRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @AfterEach
    public void after() {
        transactionRepository.deleteAll();
        accountRepository.deleteAll();
    }


    @Test
    public void shouldBeAbleToCreditAmountOnlyWhenTheUserIsLoggedIn() throws Exception {
        //arrange
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        AccountRequest accountRequest = new AccountRequest("lathaSree", bCryptPasswordEncoder.encode("password"), "latha@gmail.com");
        AccountModel accountModel = new AccountModel(accountRequest);
        accountRepository.save(accountModel);
        String email = accountModel.getEmail();
        //act and assert
       mockMvc.perform(
                post("/credit")
                        .with(httpBasic(email, "password"))
                        .param("transactionAmount", String.valueOf(new BigDecimal(100)))
        ).andExpect(status().isCreated());
    }

    @Test
    public void shouldBeAbleToDebitAmountOnlyWhentheUserIsLoggedIn() throws Exception {
        //arrange
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        AccountRequest accountRequest = new AccountRequest("lathaSree", bCryptPasswordEncoder.encode("password"), "lathasree@gmail.com");
        AccountModel accountModel = new AccountModel(accountRequest);
        accountRepository.save(accountModel);
        String email = accountModel.getEmail();
        //act and assert
        mockMvc.perform(
                post("/debit")
                        .with(httpBasic(email, "password"))
                        .param("transactionAmount", String.valueOf(new BigDecimal(100)))
        ).andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnAccountStatementOnlyWhenUserLoggedIn() throws Exception {
        //arrange
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        AccountRequest accountRequest = new AccountRequest("amrutha", bCryptPasswordEncoder.encode("password"), "amrutha@gmail.com");
        AccountModel accountModel = new AccountModel(accountRequest);
        accountRepository.save(accountModel);
        String email = accountModel.getEmail();
        //act and assert
        mockMvc.perform(
                get("/accountStatement")
                        .with(httpBasic(email, "password"))
        ).andExpect(status().isOk());
    }
}
