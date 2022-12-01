package com.thoughtworks.bankaccountapi.service;

import com.thoughtworks.bankaccountapi.model.AccountModel;
import com.thoughtworks.bankaccountapi.model.TransactionModel;
import com.thoughtworks.bankaccountapi.repository.AccountRepository;
import com.thoughtworks.bankaccountapi.repository.TransactionRepository;
import com.thoughtworks.bankaccountapi.request.AccountRequest;
import com.thoughtworks.bankaccountapi.response.TransactionInAccountStatementResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private TransactionRepository transactionRepository;
    @InjectMocks
    private TransactionService transactionService;


    @Test
    public void shouldSaveTransactionOnlyWhenLoggedInUserCreditsAmount() {
        //arrange
        String email = "amrutha@gmail.com";
        BigDecimal transactionAmount = new BigDecimal(100);
        AccountRequest accountRequest = new AccountRequest("amrutha", "password", "amrutha@gmail.com");
        AccountModel accountModel = new AccountModel(accountRequest);
        when(accountRepository.findByEmail(email)).thenReturn(Optional.of(accountModel));
        BigDecimal amountBeforeTransaction = accountModel.getAmount();
        //act
        transactionService.credit(email, transactionAmount);
        BigDecimal amountAfterTransaction = accountModel.getAmount();
        //assert
        verify(accountRepository).save(any(AccountModel.class));
        verify(transactionRepository).save(any(TransactionModel.class));
        assertEquals(amountBeforeTransaction.add(transactionAmount), amountAfterTransaction);
    }

    @Test
    public void shouldSaveTransactionOnlyWhenLoggedInUserDebitsAmount() {
        //arrange
        String email = "amrutha@gmail.com";
        BigDecimal transactionAmount = new BigDecimal(100);
        AccountRequest accountRequest = new AccountRequest("amrutha", "password", "amrutha@gmail.com");
        AccountModel accountModel = new AccountModel(accountRequest);
        when(accountRepository.findByEmail(email)).thenReturn(Optional.of(accountModel));
        BigDecimal amountBeforeTransaction = accountModel.getAmount();
        //act
        transactionService.debit(email, transactionAmount);
        BigDecimal amountAfterTransaction = accountModel.getAmount();
        //assert
        verify(accountRepository).save(any(AccountModel.class));
        verify(transactionRepository).save(any(TransactionModel.class));
        assertEquals(amountBeforeTransaction.subtract(transactionAmount), amountAfterTransaction);
    }

    @Test
    public void shouldReturnAccountStatementOnlyWhenUserLoggedIn() {
        //arrange
        String email = "amrutha@gmail.com";
        AccountRequest accountRequest = new AccountRequest("amrutha", "password", "amrutha@gmail.com");
        AccountModel accountModel = new AccountModel(accountRequest);
        when(accountRepository.findByEmail(email)).thenReturn(Optional.of(accountModel));
        TransactionModel transactionModel1 = new TransactionModel("CREDIT", new BigDecimal(100), accountModel);
        TransactionModel transactionModel2 = new TransactionModel("CREDIT", new BigDecimal(100), accountModel);
        List<TransactionModel> List_Of_transactions = new ArrayList<>();
        List_Of_transactions.add(transactionModel1);
        List_Of_transactions.add(transactionModel2);
        when(transactionRepository.findByAccountModel(accountModel)).thenReturn(List_Of_transactions);
        List<TransactionInAccountStatementResponse> modifiedTransactionsList = new ArrayList<>();
        for (TransactionModel transaction : List_Of_transactions) {
            TransactionInAccountStatementResponse transactionInAccountStatementResponse = TransactionInAccountStatementResponse.builder().
                    transaction_type(transaction.getTransaction_type()).
                    transaction_amount(transaction.getTransaction_amount())
                    .build();
            modifiedTransactionsList.add(transactionInAccountStatementResponse);
        }
        Map<String, Object> expected_accountStatement = new HashMap<>();
        expected_accountStatement.put("Account Number", accountModel.getAccountNumber());
        expected_accountStatement.put("Name of the Account Holder", accountModel.getName());
        expected_accountStatement.put("List Of Transactions", modifiedTransactionsList);
        expected_accountStatement.put("The Current Balance In the account", String.valueOf(accountModel.getAmount()));

        //act
        Map<String, Object> actual_accountStatement = transactionService.accountStatement(email);
        //assert
        assertEquals(expected_accountStatement, actual_accountStatement);
    }
}
