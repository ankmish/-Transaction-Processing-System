package com.problem.navi.factory;

import com.problem.navi.dao.impl.CurrentAccountTransactionDao;
import com.problem.navi.dao.impl.SavingsAccountTransactionDao;
import com.problem.navi.enums.AccountType;
import com.problem.navi.services.ITransactionService;
import com.problem.navi.services.impl.CurrentAccountTransactionService;
import com.problem.navi.services.impl.SavingsAccountTransactionService;

public class TransactionFactory {

    private SavingsAccountTransactionDao savingsAccountTransactionDao;
    private CurrentAccountTransactionDao currentAccountTransactionDao;

    public TransactionFactory() {
        this.savingsAccountTransactionDao = new SavingsAccountTransactionDao();
        this.currentAccountTransactionDao = new CurrentAccountTransactionDao();
    }

    public ITransactionService createAccountService(AccountType accountType) {
        switch (accountType) {
            case SAVINGS:
                return new SavingsAccountTransactionService(savingsAccountTransactionDao);
            case CURRENT:
                return new CurrentAccountTransactionService(currentAccountTransactionDao);
            default:
                throw new IllegalArgumentException("Invalid account type");
        }
    }
}
