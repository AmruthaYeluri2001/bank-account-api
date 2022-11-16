package com.thoughtworks.bankaccountapi.controller;

import com.thoughtworks.bankaccountapi.model.AccountModel;
import com.thoughtworks.bankaccountapi.model.TransactionModel;
import com.thoughtworks.bankaccountapi.repository.AccountRepository;
import com.thoughtworks.bankaccountapi.repository.TransactionRepository;
import com.thoughtworks.bankaccountapi.request.AccountRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping
public class TransactionController {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    @PostMapping("/credit")
    @ResponseStatus(code= HttpStatus.CREATED)
    public void credit(
            Principal principal,
            @RequestParam(value = "transactionAmount") BigDecimal transactionAmount
    )
    {
        String accountNumber = principal.getName();
        AccountModel accountModel = accountRepository.findById(accountNumber).get();
        TransactionModel transactionModel = new TransactionModel("credit", transactionAmount, accountModel);
        transactionRepository.save(transactionModel);
    }
}
