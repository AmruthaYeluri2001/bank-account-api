package com.thoughtworks.bankaccountapi.service;

import com.thoughtworks.bankaccountapi.model.AccountModel;
import com.thoughtworks.bankaccountapi.model.TransactionModel;
import com.thoughtworks.bankaccountapi.repository.AccountRepository;
import com.thoughtworks.bankaccountapi.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;

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
        transactionRepository.save(transactionModel);    }
}
