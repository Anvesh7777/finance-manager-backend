package com.anvesh.finance_manager.service;

import com.anvesh.finance_manager.entity.Transaction;
import com.anvesh.finance_manager.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
    public void deleteTransaction(Long id) {

        Transaction transaction =
                transactionRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Transaction not found"));

        transactionRepository.delete(transaction);
    }

    public Transaction updateTransaction(Long id, Transaction updatedTransaction) {

        Transaction transaction = transactionRepository.findById(id).orElse(null);

        if (transaction == null) {
            return null;
        }

        transaction.setAmount(updatedTransaction.getAmount());
        transaction.setDate(updatedTransaction.getDate());
        transaction.setDescription(updatedTransaction.getDescription());
        transaction.setCategory(updatedTransaction.getCategory());

        return transactionRepository.save(transaction);
    }
}