package com.thoughtworks.bankaccountapi.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
public class AccountRequest {

    private String name;

    private String password;

    private String confirmPassword;

    private BigDecimal amount;

    public AccountRequest(String name, String password, String confirmPassword) {
        this.name = name;
        this.password = password;
        this.confirmPassword=confirmPassword;
        this.amount=new BigDecimal(0);
    }
}
