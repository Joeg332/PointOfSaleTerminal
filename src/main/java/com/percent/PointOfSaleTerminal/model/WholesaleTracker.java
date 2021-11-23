package com.percent.PointOfSaleTerminal.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class WholesaleTracker {

    private UUID transactionId;
    private String itemCode;
    private int count;

}
