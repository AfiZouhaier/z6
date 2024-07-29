package com.example.z6.Service.Implementation;

import com.example.z6.Entities.BankAccount;
import com.example.z6.Entities.Transaction;
import com.example.z6.Entities.User;
import com.example.z6.Repositories.TransactionRepo;
import com.example.z6.Repositories.UserRepo;
import com.example.z6.Service.TransactionService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public ResponseEntity createTransaction(Transaction transaction) {
        Boolean moneyExists=false;
        if(transaction.getSendingFrom().getId()==transaction.getSendingTo().getId()){
            return null;
        }
        //Verify if the amount is availabe in the sender account
        final User userSender = userRepo.findById(transaction.getSendingFrom().getId()).orElse(null);
        final User userReciever = userRepo.findById(transaction.getSendingTo().getId()).orElse(null);
        BankAccount bankAccountSender = userSender.getBankAccounts().get(0);
           if(bankAccountSender.getValue()-transaction.getAmount()>=0){
               transaction.setStatus(TransactionStatus.COMMITTING);
           }else{
                logger.error("There is no efficient Money in your accounts");
                return new ResponseEntity("You need to have efficient amount of Money", HttpStatus.BAD_REQUEST);
           }

       //reduce the amount of money from the bank account of the owner and added to the reciver
        bankAccountSender.setValue(bankAccountSender.getValue()-transaction.getAmount());

        BankAccount bankAccountReceiver = userReciever.getBankAccounts().get(0);
        bankAccountReceiver.setValue(bankAccountReceiver.getValue()+transaction.getAmount());

        //specifie the time of transaction and the status
        transaction.setStatus(TransactionStatus.COMMITTED);
        transaction.setTimeTransactionMade(new Date());
        entityManager.merge(transaction);

        return new ResponseEntity(transaction, HttpStatus.OK);
    }

    @Override
    public List<Transaction> getTransactions() {
        return transactionRepo.findAll();
    }

    @Override
    public List<Transaction> getTransactions(Long id) {
        User user = userRepo.findById(id).orElse(null);
        List<Transaction> transactionFromList = transactionRepo.findAllBySendingFrom(user);
        List<Transaction> transactionToList = transactionRepo.findAllBySendingTo(user);
        List<Transaction> mergedList = new ArrayList<>();
        mergedList.addAll(transactionFromList);
        mergedList.addAll(transactionToList);
        return mergedList;
    }
}
