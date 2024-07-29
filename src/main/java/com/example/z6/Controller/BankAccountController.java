package com.example.z6.Controller;

import com.example.z6.Entities.BankAccount;
import com.example.z6.Service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bankAccount")
public class BankAccountController {
    @Autowired
    private BankAccountService bankAccountService;

    @GetMapping
    public List<BankAccount> getAllBankAccounts(){
        return bankAccountService.getAllBankAccounts();
    }

    @PostMapping("/add/{userId}")
    public BankAccount saveBankAccount(@RequestBody BankAccount bankAccount, @PathVariable Long userId){
        return bankAccountService.createBankAccount(bankAccount, userId);
    }
}
