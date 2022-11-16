package com.thoughtworks.bankaccountapi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "transactions")
public class TransactionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String transaction_type;

    @Column(nullable = false, updatable = false)
    private Date date_of_transaction;

    @Column(nullable = false)
    private BigDecimal transaction_amount;

    @OneToOne
    @JoinColumn(name="account_number")
    private AccountModel accountModel;

    public TransactionModel(String transaction_type, BigDecimal transaction_amount, AccountModel accountModel) {
        this.transaction_type = transaction_type;
        this.transaction_amount = transaction_amount;
        this.accountModel = accountModel;
        this.date_of_transaction=new Date();
    }
}
