package com.example.z6.Service;

import com.example.z6.Entities.BankAccount;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BankAccountService {

    public List<BankAccount> getAllBankAccounts();
    public BankAccount createBankAccount(BankAccount bankAccount, Long userId);
}
