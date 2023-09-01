package com.problem.navi.services.impl;

import com.problem.navi.dao.ITransactionDao;
import com.problem.navi.enums.AccountType;
import com.problem.navi.models.Account;
import com.problem.navi.services.ITransactionService;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class SavingsAccountTransactionService implements ITransactionService {
    private final Lock lock;

    private ITransactionDao transactionDao;

    public SavingsAccountTransactionService(ITransactionDao transactionDao) {
        lock = new ReentrantLock();
        this.transactionDao = transactionDao;

    }
    @Override
    public void transfer(long sourceAccount, long destinationAccount, double amount) {
        if(sourceAccount == destinationAccount) {
            throw new IllegalArgumentException("Cannot transfer to the same account");
        }
        lock.lock();
        try {
            Account sourceUpdateInfo = transactionDao.fetchAccountDetails(sourceAccount);
            if(sourceUpdateInfo.getBalance() >= amount) {
                Account destUpdateInfo = transactionDao.fetchAccountDetails(destinationAccount);
                double updatedSourceBalance = sourceUpdateInfo.getBalance() - amount;
                double updatedDestBalance = destUpdateInfo.getBalance() + amount;
                destUpdateInfo.setBalance(updatedDestBalance);
                sourceUpdateInfo.setBalance(updatedSourceBalance);
                transactionDao.updateAccount(destUpdateInfo.getAccountId(), destUpdateInfo);
                transactionDao.updateAccount(sourceUpdateInfo.getAccountId(), sourceUpdateInfo);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void withdraw(long account, double amount) {
        lock.lock();
        try {
            Account accountInfo = transactionDao.fetchAccountDetails(account);
            if(accountInfo.getBalance() >= amount) {
                double updatedBalance = accountInfo.getBalance() - amount;
                accountInfo.setBalance(updatedBalance);
                transactionDao.updateAccount(accountInfo.getAccountId(), accountInfo);
            }
            else {
                throw new IllegalArgumentException("Insufficient balance in accountId: " + account);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void deposit(long account, double amount) {
        lock.lock();
        try {
            Account accountInfo = transactionDao.fetchAccountDetails(account);
            double updatedBalance = accountInfo.getBalance() + amount;
            accountInfo.setBalance(updatedBalance);
            transactionDao.updateAccount(accountInfo.getAccountId(), accountInfo);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public double getBalance(long accountId) throws Exception {
        return transactionDao.fetchAccountDetails(accountId).getBalance();
    }

    @Override
    public void createAccount(int accountId, double initialBalance) {
        lock.lock();
        try {
            Map<Long, Account> accounts = transactionDao.fetchAccounts();
            if (!accounts.containsKey(accountId)) {
                transactionDao.updateAccount(accountId, new Account(accountId, initialBalance, AccountType.SAVINGS));
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Account getAccountDetails(int accountId) throws Exception {
        return transactionDao.fetchAccountDetails(accountId);
    }


}
