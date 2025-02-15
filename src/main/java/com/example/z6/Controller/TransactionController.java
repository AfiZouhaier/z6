package com.example.z6.Controller;


import com.example.z6.Entities.Transaction;
import com.example.z6.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.FileOutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private SimpMessagingTemplate template;

    @GetMapping
    public List<Transaction> getTransactions(){
        return transactionService.getTransactions();
    }

    @PostMapping("/create")
    public ResponseEntity createTransaction(@RequestBody Transaction transaction){
        System.out.println(transaction);
        ResponseEntity responseEntity = transactionService.createTransaction(transaction);
        template.convertAndSend("/topic/notifications", "Transaction received for " + transaction.getAmount());

        return transactionService.createTransaction(transaction);
    }

    @GetMapping("/myTransactions")
    public List<Transaction> getTransactions(@RequestBody Long id)  {
        return transactionService.getTransactions(id);

    }

}
