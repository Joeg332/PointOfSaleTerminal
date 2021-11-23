package com.percent.PointOfSaleTerminal.model;

import lombok.*;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    private UUID transactionId;
    private double totalBeforeSavings;
    private double totalAfterSavings;
    private double totalSavings;
    private Map<UUID, TransactionItem> transactionItems;
    private Map<String, WholesaleTracker> wholesaleTrackers;

}
