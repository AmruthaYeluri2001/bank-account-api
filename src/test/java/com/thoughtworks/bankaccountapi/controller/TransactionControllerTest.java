package com.thoughtworks.bankaccountapi.controller;

import com.thoughtworks.bankaccountapi.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.security.Principal;

import static org.mockito.Mockito.*;

public class TransactionControllerTest {

    private TransactionService transactionService;

    private Principal principal;

    private TransactionController transactionController;

    @BeforeEach
    public void before() {
        transactionService = mock(TransactionService.class);
        principal = mock(Principal.class);
        transactionController = new TransactionController(transactionService);
    }

    @Test
    public void shouldbeAbleToInvokeCreditMethodInTransactionService() {
        //arrange
        BigDecimal transactionAmount = new BigDecimal(100);
        String accountNumber = "0816d5ee-e19d-41c6-ba7d-23188d57f000";
        when(principal.getName()).thenReturn(accountNumber);
        //act
        transactionController.credit(principal, transactionAmount);
        //assert
        verify(transactionService).credit(accountNumber, transactionAmount);
    }

    @Test
    public void shouldbeAbleToInvokeDebitMethodInTransactionService() {
        //arrange
        BigDecimal transactionAmount = new BigDecimal(100);
        String accountNumber = "0816d5ee-e19d-41c6-ba7d-23188d57f000";
        when(principal.getName()).thenReturn(accountNumber);
        //act
        transactionController.debit(principal, transactionAmount);
        //assert
        verify(transactionService).debit(accountNumber, transactionAmount);
    }

    @Test
    public void shouldBeAbleToInvokeAccountStatementMethodInTransactionService() {
        //arrange
        Principal principal=mock(Principal.class);
        String accountNumber="0816d5ee-e19d-41c6-ba7d-23188d57f000";
        when(principal.getName()).thenReturn(accountNumber);
        //act
        transactionController.accountStatement(principal);
        //assert
        verify(transactionService).accountStatement(accountNumber);
    }
}
