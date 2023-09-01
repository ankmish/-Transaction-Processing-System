package com.problem.navi;

import com.problem.navi.enums.AccountType;
import com.problem.navi.enums.TransactionStatus;
import com.problem.navi.enums.TransactionType;
import com.problem.navi.factory.TransactionFactory;
import com.problem.navi.models.Transaction;
import com.problem.navi.services.TransactionProcessingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NaviApplication {

    public static void main(String[] args) throws Exception {

        TransactionFactory transactionFactory = new TransactionFactory();
        TransactionProcessingService transactionProcessingSystem = new TransactionProcessingService(transactionFactory);

        // Create accounts
        transactionProcessingSystem.createAccount(1, 1000.0, AccountType.SAVINGS);
        transactionProcessingSystem.createAccount(2, 2000.0, AccountType.SAVINGS);

        // Perform transactions
        Transaction depositTransaction = new Transaction(1, TransactionType.DEPOSIT, 1, 1, 500.0, TransactionStatus.SUCCESS, 12345);
        Transaction withdrawalTransaction = new Transaction(2, TransactionType.WITHDRAWAL, 2, 2, 100.0, TransactionStatus.SUCCESS, 12346);
        Transaction transferTransaction = new Transaction(3, TransactionType.TRANSFER, 2, 1, 200.0, TransactionStatus.SUCCESS, 12347);

        transactionProcessingSystem.processTransaction(depositTransaction, AccountType.SAVINGS);
        transactionProcessingSystem.processTransaction(withdrawalTransaction, AccountType.SAVINGS);
        transactionProcessingSystem.processTransaction(transferTransaction, AccountType.SAVINGS);

        // Print balances
        double balance1 = transactionProcessingSystem.getAccountBalance(1, AccountType.SAVINGS);
        double balance2 = transactionProcessingSystem.getAccountBalance(2, AccountType.SAVINGS);

        System.out.println("Account 1 balance: " + balance1);
        System.out.println("Account 2 balance: " + balance2);

        SpringApplication.run(NaviApplication.class, args);
    }

}
