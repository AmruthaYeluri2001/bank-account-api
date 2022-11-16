package com.thoughtworks.bankaccountapi.service;

import com.thoughtworks.bankaccountapi.model.AccountModel;
import com.thoughtworks.bankaccountapi.model.TransactionModel;
import com.thoughtworks.bankaccountapi.repository.AccountRepository;
import com.thoughtworks.bankaccountapi.repository.TransactionRepository;
import com.thoughtworks.bankaccountapi.request.AccountRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {
    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;


    @BeforeEach
    public void before() {
        accountRepository = mock(AccountRepository.class);
        transactionRepository = mock(TransactionRepository.class);
    }

    @Test
    public void shouldSaveTransactionOnlyWhenLoggedInUserCreditsAmount() {
        //arrange
        TransactionService transactionService = new TransactionService(accountRepository, transactionRepository);
        String accountNumber = "0816d5ee-e19d-41c6-ba7d-23188d57f000";
        BigDecimal transactionAmount = new BigDecimal(100);
        AccountRequest accountRequest =new AccountRequest("amrutha","password","password");
        AccountModel accountModel = new AccountModel(accountRequest);
        when(accountRepository.findById(accountNumber)).thenReturn(Optional.of(accountModel));
        BigDecimal amountBeforeTransaction = accountModel.getAmount();
        //act
        transactionService.credit(accountNumber, transactionAmount);
        BigDecimal amountAfterTransaction = accountModel.getAmount();
        //assert
        verify(accountRepository).save(any(AccountModel.class));
        verify(transactionRepository).save(any(TransactionModel.class));
        assertEquals(amountBeforeTransaction.add(transactionAmount),amountAfterTransaction);
    }
}
