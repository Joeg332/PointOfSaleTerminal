package com.percent.PointOfSaleTerminal.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class Item {

    private String name;
    //Note This was Produce Code in the documentation, but I have changed it to itemCode to more accurately reflect the item.
    // For example: In the documentation we used doughnuts as an example which is NOT a produce
    private String itemCode;
    private double  itemPrice;
    private WholesaleOrder wholesaleOrder;
}
