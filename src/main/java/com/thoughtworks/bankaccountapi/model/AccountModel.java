package com.thoughtworks.bankaccountapi.model;

import com.thoughtworks.bankaccountapi.request.AccountRequest;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
public class AccountModel {

    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    private String accountNumber;


    @Column(nullable = false)
    private String name;


    @Column(nullable = false)
    private String password;


    @Column(nullable = false)
    private BigDecimal amount;


    public AccountModel(AccountRequest accountRequest) {
        this.name = accountRequest.getName();
        this.password = accountRequest.getPassword();
        this.amount = accountRequest.getAmount();
    }

    public AccountModel() {
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
