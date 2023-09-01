package com.problem.navi.dao.impl;

import com.problem.navi.dao.ITransactionDao;
import com.problem.navi.models.Account;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CurrentAccountTransactionDao implements ITransactionDao  {

    private Map<Long, Account> currentAccountMap =  new HashMap<>();

    @Override
    public void updateAccount(long accountId, Account account) {
        currentAccountMap.put(accountId, account);
    }

    @Override
    public Account fetchAccountDetails(long accountId) throws Exception {
        if(currentAccountMap.containsKey(accountId)) {
            return currentAccountMap.get(accountId);
        }
        throw new Exception("AccountId: " + accountId + " not present is system");
    }

    @Override
    public Map<Long, Account> fetchAccounts() {
        return currentAccountMap;
    }

}
