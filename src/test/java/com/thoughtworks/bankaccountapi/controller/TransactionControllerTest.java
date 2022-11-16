package com.thoughtworks.bankaccountapi.controller;

import com.thoughtworks.bankaccountapi.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.security.Principal;

import static org.mockito.Mockito.*;

public class TransactionControllerTest {

    TransactionService transactionService;

    Principal principal;

    @BeforeEach
    public void before() {
        transactionService = mock(TransactionService.class);
        principal = mock(Principal.class);
    }

    @Test
    public void shouldbeAbleToInvokeCreditMethodInTransactionService() {
        //arrange
        TransactionController transactionController = new TransactionController(transactionService);
        BigDecimal transactionAmount = new BigDecimal(100);
        String accountNumber = "0816d5ee-e19d-41c6-ba7d-23188d57f000";
        when(principal.getName()).thenReturn(accountNumber);
        //act
        transactionController.credit(principal, transactionAmount);
        //assert
        verify(transactionService).credit(accountNumber, transactionAmount);
    }
}
