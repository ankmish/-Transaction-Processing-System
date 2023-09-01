package com.problem.navi.services;

import com.problem.navi.enums.AccountType;
import com.problem.navi.factory.TransactionFactory;
import com.problem.navi.models.Account;
import com.problem.navi.models.Transaction;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TransactionProcessingService {

    private final Lock accountsLock;

    private TransactionFactory transactionFactory;
    public TransactionProcessingService(TransactionFactory transactionFactory) {
        this.transactionFactory = transactionFactory;
        this.accountsLock = new ReentrantLock();
    }

    public void createAccount(int accountId, double initialBalance, AccountType accountType) {
        try {
            ITransactionService transactionService = getTransactionService(accountType);
            transactionService.createAccount(accountId, initialBalance);
        } catch (Exception ex) {
            // TODO log
        }
    }

    public ITransactionService getTransactionService(AccountType accountType) {
        return transactionFactory.createAccountService(accountType);
    }

    public void processTransaction(Transaction transaction, AccountType accountType) {
        accountsLock.lock();
        try {
            ITransactionService transactionService = getTransactionService(accountType);
            Account account = transactionService.getAccountDetails(transaction.getFromAccountId());
            if (account == null) {
                throw new IllegalArgumentException("Account not found");
            }
            switch (transaction.getType()) {
                case DEPOSIT:
                    transactionService.deposit(transaction.getFromAccountId(), transaction.getAmount());
                    break;
                case WITHDRAWAL:
                    transactionService.withdraw(transaction.getFromAccountId(), transaction.getAmount());
                    break;
                case TRANSFER:
                    transactionService.transfer(transaction.getFromAccountId(), transaction.getToAccountId(), transaction.getAmount());
                    break;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            accountsLock.unlock();
        }
    }

    public double getAccountBalance(int accountId, AccountType accountType) throws Exception {
        ITransactionService transactionService = getTransactionService(accountType);
        return transactionService.getBalance(accountId);
    }
}
