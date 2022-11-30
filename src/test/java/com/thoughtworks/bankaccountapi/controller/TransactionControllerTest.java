package com.thoughtworks.bankaccountapi.controller;

import com.thoughtworks.bankaccountapi.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        //act
        transactionController.credit(principal, transactionAmount);
        //assert
        verify(transactionService).credit(principal.getName(), transactionAmount);
    }

    @Test
    public void shouldbeAbleToInvokeDebitMethodInTransactionService() {
        //arrange
        BigDecimal transactionAmount = new BigDecimal(100);
        //act
        transactionController.debit(principal, transactionAmount);
        //assert
        verify(transactionService).debit(principal.getName(), transactionAmount);
    }

    @Test
    public void shouldBeAbleToInvokeAccountStatementMethodInTransactionService() {
        //arrange
        HashMap<String, Object> expectedAccountStatementResponse = new HashMap<>();
        when(transactionService.accountStatement(principal.getName())).thenReturn(expectedAccountStatementResponse);
        //act
        Map<String, Object> actualAccountStatementResponse = transactionController.accountStatement(principal);
        //assert
        verify(transactionService).accountStatement(principal.getName());
        assertThat(actualAccountStatementResponse, is(expectedAccountStatementResponse));
    }
}
