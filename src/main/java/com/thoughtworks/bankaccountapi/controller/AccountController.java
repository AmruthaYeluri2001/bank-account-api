package com.thoughtworks.bankaccountapi.controller;

import com.thoughtworks.bankaccountapi.request.AccountRequest;
import com.thoughtworks.bankaccountapi.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    void sign_up(@RequestBody AccountRequest accountRequest) {
        accountService.signup(accountRequest);
    }

    @GetMapping("/log-in")
    @ResponseStatus(code = HttpStatus.OK)
    public Map<String, String> log_in(Principal principal) {
        String email = principal.getName();
        Map<String, String> userDetails = new HashMap<>();
        userDetails.put("User email", email);
        return userDetails;
    }

    @GetMapping("/accountSummary")
    @ResponseStatus(code = HttpStatus.OK)
    public Map<String, String> accountSummary(Principal principal) {
        String email = principal.getName();
        Map<String, String> accountSummary = accountService.accountSummary(email);
        return accountSummary;
    }
}
