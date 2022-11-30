package com.thoughtworks.bankaccountapi.controller;

import com.thoughtworks.bankaccountapi.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.security.Principal;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @Mock
    private Principal principal;

    @InjectMocks
    private TransactionController transactionController;


    @Test
    public void shouldbeAbleToInvokeCreditMethodInTransactionService() {
        //arrange
        BigDecimal transactionAmount = new BigDecimal(100);
        String email = "amrutha@gmail.com";
        when(principal.getName()).thenReturn(email);
        //act
        transactionController.credit(principal, transactionAmount);
        //assert
        verify(transactionService).credit(email, transactionAmount);
    }

    @Test
    public void shouldbeAbleToInvokeDebitMethodInTransactionService() {
        //arrange
        BigDecimal transactionAmount = new BigDecimal(100);
        String email = "amrutha@gmail.com";
        when(principal.getName()).thenReturn(email);
        //act
        transactionController.debit(principal, transactionAmount);
        //assert
        verify(transactionService).debit(email, transactionAmount);
    }

    @Test
    public void shouldBeAbleToInvokeAccountStatementMethodInTransactionService() {
        //arrange
        Principal principal = mock(Principal.class);
        String email = "amrutha@gmail.com";
        when(principal.getName()).thenReturn(email);
        //act
        transactionController.accountStatement(principal);
        //assert
        verify(transactionService).accountStatement(email);
    }
}
