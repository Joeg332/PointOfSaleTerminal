package com.percent.PointOfSaleTerminal.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class Transaction {

    private UUID transactionId;
    private double totalBeforeSavings;
    private double totalAfterSavings;
    private double totalSavings;
    private Map<UUID, TransactionItem> transactionItems;
    private Map<String, WholesaleTracker> wholesaleTrackers;

}
