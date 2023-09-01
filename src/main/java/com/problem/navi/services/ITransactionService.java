package com.problem.navi.services;

import com.problem.navi.models.Account;

public interface ITransactionService {

    void transfer(long sourceAccount, long destinationAccount, double amount);
    void withdraw(long accountId, double amount);
    void deposit(long accountId, double amount);

    double getBalance(long accountId) throws Exception;

    void createAccount(int accountId, double initialBalance);

    Account getAccountDetails(int accountId) throws Exception;
}
