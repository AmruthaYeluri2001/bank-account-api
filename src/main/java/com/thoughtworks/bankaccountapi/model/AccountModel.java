package com.thoughtworks.bankaccountapi.model;

import com.thoughtworks.bankaccountapi.request.AccountRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
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
}
