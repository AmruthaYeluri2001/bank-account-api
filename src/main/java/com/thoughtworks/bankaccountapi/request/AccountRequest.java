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

    private BigDecimal amount;

    private String email;

    public AccountRequest(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.amount = new BigDecimal(0);
        this.email = email;
    }

}
