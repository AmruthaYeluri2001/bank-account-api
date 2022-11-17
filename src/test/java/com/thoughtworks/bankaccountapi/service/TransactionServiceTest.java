package com.thoughtworks.bankaccountapi.service;

import com.thoughtworks.bankaccountapi.model.AccountModel;
import com.thoughtworks.bankaccountapi.model.TransactionModel;
import com.thoughtworks.bankaccountapi.repository.AccountRepository;
import com.thoughtworks.bankaccountapi.repository.TransactionRepository;
import com.thoughtworks.bankaccountapi.request.AccountRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {
    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    private TransactionService transactionService;


    @BeforeEach
    public void before() {
        accountRepository = mock(AccountRepository.class);
        transactionRepository = mock(TransactionRepository.class);
        transactionService = new TransactionService(accountRepository, transactionRepository);
    }

    @Test
    public void shouldSaveTransactionOnlyWhenLoggedInUserCreditsAmount() {
        //arrange
        String accountNumber = "0816d5ee-e19d-41c6-ba7d-23188d57f000";
        BigDecimal transactionAmount = new BigDecimal(100);
        AccountRequest accountRequest = new AccountRequest("amrutha", "password", "password");
        AccountModel accountModel = new AccountModel(accountRequest);
        when(accountRepository.findById(accountNumber)).thenReturn(Optional.of(accountModel));
        BigDecimal amountBeforeTransaction = accountModel.getAmount();
        //act
        transactionService.credit(accountNumber, transactionAmount);
        BigDecimal amountAfterTransaction = accountModel.getAmount();
        //assert
        verify(accountRepository).save(any(AccountModel.class));
        verify(transactionRepository).save(any(TransactionModel.class));
        assertEquals(amountBeforeTransaction.add(transactionAmount), amountAfterTransaction);
    }

    @Test
    public void shouldSaveTransactionOnlyWhenLoggedInUserDebitsAmount() {
        //arrange
        String accountNumber = "0816d5ee-e19d-41c6-ba7d-23188d57f000";
        BigDecimal transactionAmount = new BigDecimal(100);
        AccountRequest accountRequest = new AccountRequest("amrutha", "password", "password");
        AccountModel accountModel = new AccountModel(accountRequest);
        when(accountRepository.findById(accountNumber)).thenReturn(Optional.of(accountModel));
        BigDecimal amountBeforeTransaction = accountModel.getAmount();
        //act
        transactionService.debit(accountNumber, transactionAmount);
        BigDecimal amountAfterTransaction = accountModel.getAmount();
        //assert
        verify(accountRepository).save(any(AccountModel.class));
        verify(transactionRepository).save(any(TransactionModel.class));
        assertEquals(amountBeforeTransaction.subtract(transactionAmount), amountAfterTransaction);
    }

    @Test
    public void shouldreturnAccountStatementOnlyWhenUserLoggedIn() {
        //arrange
        String accountNumber = "0816d5ee-e19d-41c6-ba7d-23188d57f000";
        AccountRequest accountRequest = new AccountRequest("amrutha", "password", "password");
        AccountModel accountModel = new AccountModel(accountRequest);
        when(accountRepository.findById(accountNumber)).thenReturn(Optional.of(accountModel));
        TransactionModel transactionModel1 = new TransactionModel("CREDIT", new BigDecimal(100), accountModel);
        TransactionModel transactionModel2 = new TransactionModel("CREDIT", new BigDecimal(100), accountModel);
        List<TransactionModel> List_Of_transactions=new ArrayList<>();
        List_Of_transactions.add(transactionModel1);
        List_Of_transactions.add(transactionModel2);
        when(transactionRepository.findByAccountModel_accountNumber(accountNumber)).thenReturn(List_Of_transactions);
        Map<String,Object> expected_accountStatement=new HashMap<>();
        expected_accountStatement.put("Account Number",accountModel.getAccountNumber());
        expected_accountStatement.put("Name of the Account Holder",accountModel.getName());
        expected_accountStatement.put("List Of Transactions",List_Of_transactions);
        expected_accountStatement.put("The Current Balance In the account", String.valueOf(accountModel.getAmount()));

        //act
        Map<String, Object> actual_accountStatement = transactionService.accountStatement(accountNumber);
        //assert
        assertEquals(expected_accountStatement,actual_accountStatement);
    }
}
