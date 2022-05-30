package com.dw.ngms.cis.enums;

import java.util.stream.Stream;

/**
 * @author : prateekgoel
 * @since : 29/12/20, Tue
 **/
public enum TransactionType {
    DIARISE(1),
    EXPEDITE(2),
    REASSIGN(3),
    MARK_AS_PENDING(4),
    CANCEL_TASK(5),
    ASSIGN_TO_SECTION(6);


    private final Integer transactionType;


    TransactionType(Integer transactionType) {
        this.transactionType = transactionType;
    }

    public static TransactionType of(Integer transactionType) {
        return Stream.of(TransactionType.values())
                .filter(transactionType1 -> transactionType1
                        .getTransactionType().equals(transactionType))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public Integer getTransactionType() {
        return transactionType;
    }
}
