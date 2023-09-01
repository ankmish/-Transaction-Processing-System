package com.problem.navi.models;

import com.problem.navi.enums.AccountType;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Account {
    private final long accountId;
    private double balance;
    private AccountType accountType;
    private final Lock lock = new ReentrantLock();

    public Account(long accountId, double initialBalance, AccountType accountType) {
        this.accountId = accountId;
        this.balance = initialBalance;
        this.accountType = accountType;
    }

}