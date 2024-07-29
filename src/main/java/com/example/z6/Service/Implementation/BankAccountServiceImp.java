package com.example.z6.Service.Implementation;

import com.example.z6.Entities.BankAccount;
import com.example.z6.Entities.User;
import com.example.z6.Repositories.BankAccountRepo;
import com.example.z6.Repositories.UserRepo;
import com.example.z6.Service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class BankAccountServiceImp implements BankAccountService {
    @Autowired
    private BankAccountRepo bankAccountRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public List<BankAccount> getAllBankAccounts() {
        return bankAccountRepo.findAll();
    }

    @Override
    public BankAccount createBankAccount(BankAccount bankAccount, Long userId) {
        Optional<User> user = userRepo.findById(userId);
        bankAccount.setUser(user.orElse(null));
        return bankAccountRepo.save(bankAccount);
    }
}
