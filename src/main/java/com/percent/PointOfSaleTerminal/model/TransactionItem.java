package com.percent.PointOfSaleTerminal.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class TransactionItem {

    private UUID transactionItemId;
    private UUID transactionId;
    private String itemCode;

}
