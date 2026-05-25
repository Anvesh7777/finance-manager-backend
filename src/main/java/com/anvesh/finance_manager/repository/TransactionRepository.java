package com.anvesh.finance_manager.repository;

import com.anvesh.finance_manager.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}