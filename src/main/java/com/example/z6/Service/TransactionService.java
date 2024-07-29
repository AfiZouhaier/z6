package com.example.z6.Service;


import com.example.z6.Entities.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionService {

    public ResponseEntity createTransaction(Transaction transaction);
    public List<Transaction> getTransactions();
    public List<Transaction> getTransactions(Long id);
}
