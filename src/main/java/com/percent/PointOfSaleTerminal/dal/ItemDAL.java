package com.percent.PointOfSaleTerminal.dal;

import com.percent.PointOfSaleTerminal.model.Item;
import com.percent.PointOfSaleTerminal.model.WholesaleOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * This DAL service will be used to mock up a connection to the Database.
 * Ideally We would use SpringData with Repository Interfaces to query data to teh database,
 * However for this exercise we are simply going to initialize data in memory using our constructor
 * **/
@Component
public class ItemDAL {

    private Map<String, Item> items;

    @Autowired // Note: AutoWired is not doing anything here but would if we had dependencies
    // on things such as Repository interfaces
    public ItemDAL(){
        this.items = mockInitialItems();
    }


    public Map<String, Item> getItems(){
        return items;
    }

    public Item getItem(String itemCode){
        return items.get(itemCode);
    }

    public Item saveItem(Item item){
        items.put(item.getItemCode(), item);
        return item;
    }

    public Item DeleteItem(String itemCode){
        return items.remove(itemCode);
    }

    /**
     * This function mocks data that would normally be contained in the database
     * @return returns a map of Items with the key as itemCode
     * **/
    private static Map<String, Item> mockInitialItems(){
        Map<String, Item> items = new HashMap<>();

        WholesaleOrder wholesaleA = WholesaleOrder.builder()
                .wholesaleThreshold(3)
                .wholesalePrice(3.00)
                .build();
        Item itemA = Item.builder()
                .itemCode("A")
                .name("Doughnut")
                .itemPrice(1.25)
                .wholesaleOrder(wholesaleA)
                .build();
        items.put(itemA.getItemCode(), itemA);

        Item itemB = Item.builder()
                .itemCode("B")
                .name("Apple")
                .itemPrice(4.25)
                .build();
        items.put(itemB.getItemCode(), itemB);

        WholesaleOrder wholesaleC = WholesaleOrder.builder()
                .wholesaleThreshold(6)
                .wholesalePrice(5.00)
                .build();
        Item itemC = Item.builder()
                .itemCode("C")
                .name("Shampoo")
                .itemPrice(1.00)
                .wholesaleOrder(wholesaleC)
                .build();
        items.put(itemC.getItemCode(), itemC);

        Item itemD = Item.builder()
                .itemCode("D")
                .name("Avacado")
                .itemPrice(1.00)
                .build();
        items.put(itemD.getItemCode(), itemD);

        return items;
    }
}
