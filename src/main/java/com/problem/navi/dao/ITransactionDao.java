package com.problem.navi.dao;

import com.problem.navi.models.Account;
import java.util.Map;

public interface ITransactionDao {
    void updateAccount(long accountId, Account account);

    Account fetchAccountDetails(long accountId) throws Exception;

    Map<Long, Account> fetchAccounts();
}
