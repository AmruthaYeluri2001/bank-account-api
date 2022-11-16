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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
        AccountRequest accountRequest = new AccountRequest("lathaSree", bCryptPasswordEncoder.encode("password"), "password");
        AccountModel accountModel = new AccountModel(accountRequest);
        accountRepository.save(accountModel);
        String accountNumber=accountModel.getAccountNumber();
        //act
        MvcResult mvcResult=mockMvc.perform(
                post("/credit")
                       .with(httpBasic(accountNumber,"password"))
                       .param("transactionAmount", String.valueOf(new BigDecimal(100)))
        ).andReturn();
        //assert
        assertEquals(HttpStatus.CREATED.value(),mvcResult.getResponse().getStatus());
    }
}
