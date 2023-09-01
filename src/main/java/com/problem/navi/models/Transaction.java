package com.problem.navi.models;

import com.problem.navi.enums.TransactionStatus;
import com.problem.navi.enums.TransactionType;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Transaction {
    private final long transactionId;
    private final TransactionType type;
    private final int fromAccountId;
    private final int toAccountId;
    private final double amount;
    private final TransactionStatus status;
    private final long timestamp;

}
