package com.thoughtworks.bankaccountapi.controller;

import com.thoughtworks.bankaccountapi.request.AccountRequest;
import com.thoughtworks.bankaccountapi.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping
public class AccountController {

    AccountService accountService;


    @PostMapping("/sign-up")
    ResponseEntity sign_up(@RequestBody AccountRequest accountRequest) {
        return accountService.signup(accountRequest);
    }

    @GetMapping("/log-in")
    @ResponseStatus(code = HttpStatus.OK)
    public Map<String, Object> log_in(Principal principal) {
        String accountNumber = principal.getName();
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("accountNumber", accountNumber);
        return userDetails;
    }

    @GetMapping("/accountSummary")
    @ResponseStatus(code = HttpStatus.OK)
    public Map<String,String> accountSummary(Principal principal)
    {
        String accountNumber=principal.getName();
        Map<String, String> accountSummary = accountService.accountSummary(accountNumber);
        return accountSummary;
    }
}
