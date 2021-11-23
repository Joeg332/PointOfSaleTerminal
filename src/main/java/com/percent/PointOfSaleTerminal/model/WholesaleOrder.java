package com.percent.PointOfSaleTerminal.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WholesaleOrder {

    // Note: We changed the name from volume to wholesale in order to reduce ambiguity
    private double wholesalePrice;
    private int wholesaleThreshold;
}
