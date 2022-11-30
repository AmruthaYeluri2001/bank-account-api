package com.thoughtworks.bankaccountapi.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionInAccountStatementResponse {

    private String transaction_type;
    private BigDecimal transaction_amount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionInAccountStatementResponse that = (TransactionInAccountStatementResponse) o;
        return Objects.equals(transaction_type, that.transaction_type) && Objects.equals(transaction_amount, that.transaction_amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transaction_type, transaction_amount);
    }
}
