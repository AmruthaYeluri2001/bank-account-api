package com.thoughtworks.bankaccountapi.controller;

import com.thoughtworks.bankaccountapi.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping
public class TransactionController {

    TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/credit")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void credit(
            Principal principal,
            @RequestParam(value = "transactionAmount") BigDecimal transactionAmount
    ) {
        String email = principal.getName();
        transactionService.credit(email, transactionAmount);
    }

    @PostMapping("/debit")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void debit(
            Principal principal,
            @RequestParam(value = "transactionAmount") BigDecimal transactionAmount
    ) {
        String email = principal.getName();
        transactionService.debit(email, transactionAmount);

    }

    @GetMapping("/accountStatement")
    @ResponseStatus(code = HttpStatus.OK)
    public Map<String, Object> accountStatement(Principal principal) {
        String email = principal.getName();
        return transactionService.accountStatement(email);
    }
}
