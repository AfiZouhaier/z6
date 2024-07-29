package com.example.z6.Repositories;

import com.example.z6.Entities.BankAccount;
import com.example.z6.Entities.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankAccountRepo extends JpaRepository<BankAccount, Long> {

    Optional<BankAccount> findByAccountNumber(Long aLong);
    Optional<List<BankAccount>> findAllByUser(User user);
}
