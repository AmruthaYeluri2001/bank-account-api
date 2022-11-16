package com.thoughtworks.bankaccountapi.repository;

import com.thoughtworks.bankaccountapi.model.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionModel, Long> {
}
