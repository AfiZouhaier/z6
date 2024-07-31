package com.example.z6.auth;

import com.example.z6.Entities.BankAccount;
import com.example.z6.Entities.Role;
import com.example.z6.Entities.Transaction;
import com.example.z6.Entities.User;
import com.example.z6.Repositories.BankAccountRepo;
import com.example.z6.Repositories.UserRepo;
import com.example.z6.Service.TransactionService;
import com.example.z6.configuration.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final UserRepo userRepo;

    private final BankAccountRepo bankAccountRepo;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final TransactionService transactionService;

    private final PasswordEncoder passwordEncoder;
    public AuthenticationResponse register(AuthenticationRequest authenticationRequest) throws Exception {
        var user = User.builder()
                .name(authenticationRequest.getName())
                .last_name(authenticationRequest.getLast_name())
                .cin(authenticationRequest.getCin())
                .password(passwordEncoder.encode(authenticationRequest.getPassword()))
                .email(authenticationRequest.getEmail())
                .role(Role.USER)
                .build();
        if(userRepo.findByEmail(authenticationRequest.getEmail()).isPresent() || userRepo.findByCin(authenticationRequest.getCin()).isPresent()){
            throw new Exception("Recheck your cin or your email");
        }

        userRepo.save(user);
        var bankAccountNumber = new Random().nextInt(900000)+100000;
        while(true){
            if(bankAccountRepo.findByAccountNumber(Long.valueOf(bankAccountNumber)).isPresent()){
                bankAccountNumber = new Random().nextInt(900000)+100000;
            }else{
                break;
            }
        }
        BankAccount bankAccount = BankAccount.builder()
                .accountNumber(bankAccountNumber)
                .user(userRepo.findById(user.getId()).orElseThrow())
                .value(Long.valueOf(0))

                .build();
        bankAccountRepo.save(bankAccount);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );
        var user = userRepo.findByEmail(authenticationRequest.getEmail()).orElseThrow();
        List<BankAccount> bankAccounts = bankAccountRepo.findAllByUser(user).orElseThrow();
        List<Transaction> transactionList = transactionService.getTransactions(user.getId());
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .name(user.getName())
                .last_name(user.getLast_name())
                .cin(user.getCin())
                .bankAccounts(bankAccounts)
                .role(user.getRole())
                .transactionList(transactionList)
                .id(user.getId())
                .build();
    }
}
