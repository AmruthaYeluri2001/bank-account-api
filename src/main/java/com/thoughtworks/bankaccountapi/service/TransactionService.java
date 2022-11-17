package com.thoughtworks.bankaccountapi.service;

import com.thoughtworks.bankaccountapi.model.AccountModel;
import com.thoughtworks.bankaccountapi.model.TransactionModel;
import com.thoughtworks.bankaccountapi.repository.AccountRepository;
import com.thoughtworks.bankaccountapi.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class TransactionService {

    AccountRepository accountRepository;
    TransactionRepository transactionRepository;

    public void credit(String accountNumber, BigDecimal transactionAmount) {
        AccountModel accountModel = accountRepository.findById(accountNumber).get();
        TransactionModel transactionModel = new TransactionModel("CREDIT", transactionAmount, accountModel);
        accountModel.setAmount((accountModel.getAmount()).add(transactionAmount));
        accountRepository.save(accountModel);
        transactionRepository.save(transactionModel);
    }

    public void debit(String accountNumber, BigDecimal transactionAmount) {
        AccountModel accountModel = accountRepository.findById(accountNumber).get();
        TransactionModel transactionModel = new TransactionModel("DEBIT", transactionAmount, accountModel);
        accountModel.setAmount((accountModel.getAmount()).subtract(transactionAmount));
        accountRepository.save(accountModel);
        transactionRepository.save(transactionModel);
    }

    public Map<String, Object> accountStatement(String accountNumber) {
        AccountModel accountModel=accountRepository.findById(accountNumber).get();
        Map<String,Object> account_Statement=new HashMap<>();
        account_Statement.put("Account Number",accountModel.getAccountNumber());
        account_Statement.put("Name of the Account Holder",accountModel.getName());
        List<TransactionModel> List_Of_Transactions = transactionRepository.findByAccountModel_accountNumber(accountNumber);
        account_Statement.put("List Of Transactions", List_Of_Transactions);
        account_Statement.put("The Current Balance In the account", String.valueOf(accountModel.getAmount()));
        return account_Statement;
    }
}
