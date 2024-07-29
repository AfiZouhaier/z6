package com.example.z6.Repositories;

import com.example.z6.Entities.Transaction;
import com.example.z6.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    public List<Transaction> findAllBySendingFrom(User user);
    public List<Transaction> findAllBySendingTo(User user);
}
