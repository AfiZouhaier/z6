package com.example.z6.auth;

import com.example.z6.Entities.BankAccount;
import com.example.z6.Entities.Role;
import com.example.z6.Entities.Transaction;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponse {
    private String token;

    private String name;

    private String last_name;

    private Long cin;

    private String email;

    private List<Transaction> transactionList;

    private List<BankAccount> bankAccounts;

    private Role role;

}
