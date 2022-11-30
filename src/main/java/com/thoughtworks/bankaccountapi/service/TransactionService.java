package com.thoughtworks.bankaccountapi.service;

import com.thoughtworks.bankaccountapi.model.AccountModel;
import com.thoughtworks.bankaccountapi.model.TransactionModel;
import com.thoughtworks.bankaccountapi.repository.AccountRepository;
import com.thoughtworks.bankaccountapi.repository.TransactionRepository;
import com.thoughtworks.bankaccountapi.response.TransactionInAccountStatementResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class TransactionService {

    AccountRepository accountRepository;
    TransactionRepository transactionRepository;

    public void credit(String email, BigDecimal transactionAmount) {
        AccountModel accountModel = accountRepository.findByEmail(email).get();
        TransactionModel transactionModel = new TransactionModel("CREDIT", transactionAmount, accountModel);
        accountModel.setAmount((accountModel.getAmount()).add(transactionAmount));
        accountRepository.save(accountModel);
        transactionRepository.save(transactionModel);
    }

    public void debit(String email, BigDecimal transactionAmount) {
        AccountModel accountModel = accountRepository.findByEmail(email).get();
        TransactionModel transactionModel = new TransactionModel("DEBIT", transactionAmount, accountModel);
        accountModel.setAmount((accountModel.getAmount()).subtract(transactionAmount));
        accountRepository.save(accountModel);
        transactionRepository.save(transactionModel);
    }

    public Map<String, Object> accountStatement(String email) {
        AccountModel accountModel = accountRepository.findByEmail(email).get();
        Map<String, Object> account_Statement = new HashMap<>();
        account_Statement.put("Account Number", accountModel.getAccountNumber());
        account_Statement.put("Name of the Account Holder", accountModel.getName());
        List<TransactionModel> List_Of_Transactions = transactionRepository.findByAccountModel_accountNumber(email);
        List<TransactionInAccountStatementResponse> modifiedTransactionsList = new ArrayList<>();
        for (TransactionModel transaction : List_Of_Transactions) {
            TransactionInAccountStatementResponse transactionInAccountStatementResponse = new TransactionInAccountStatementResponse(transaction.getTransaction_type(),
                    transaction.getTransaction_amount());
            modifiedTransactionsList.add(transactionInAccountStatementResponse);
        }
        account_Statement.put("List Of Transactions", modifiedTransactionsList);
        account_Statement.put("The Current Balance In the account", String.valueOf(accountModel.getAmount()));
        return account_Statement;
    }
}
