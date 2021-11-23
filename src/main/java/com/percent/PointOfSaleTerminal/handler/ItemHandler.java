package com.percent.PointOfSaleTerminal.handler;

import com.percent.PointOfSaleTerminal.dal.ItemDAL;
import com.percent.PointOfSaleTerminal.exception.BadRequestException;
import com.percent.PointOfSaleTerminal.exception.NotFoundException;
import com.percent.PointOfSaleTerminal.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ItemHandler {

    private ItemDAL itemDAL;

    @Autowired
    public ItemHandler(ItemDAL itemDAL){
        this.itemDAL = itemDAL;
    }

    /**
     * @return All items in our persistence layer
     * **/
    public Map<String, Item> getItems(){
        return itemDAL.getItems();
    }

    /**
     * @param itemCode the key we are using when checking for the existence of the item
     * @return The Item associated with the key
     * **/
    public Item getItem(String itemCode){
        return itemDAL.getItem(itemCode);
    }

    /**
     * takes in an item code and returns a boolean denoting if the item exists
     * @param itemCode the key we are using when checking for the existence of the item
     * @return boolean value denoting if the item exists
     * **/
    private boolean verifyItemExists(String itemCode){
        //1) Check if the item already exists and return results
        Item existingItem = itemDAL.getItem(itemCode);
        if (existingItem == null){
            return false;
        } else {
            return true;
        }
    }

    /**
     * This endpoint created a new item in our persistence layer
     * @param item The Object which will be persisted
     * @return The Item which was created
     * @throws BadRequestException The item and itemCode cannot be null, or if the Item already exists
     * **/
    public Item createItem(Item item){
        //1) Verify required parameters
        if (item == null || item.getItemCode() == null){
            throw new BadRequestException("Invalid Object you must create an Item with a unique ItemCode");
        }

        //2) Check if item already exists
        boolean existingItem = verifyItemExists(item.getItemCode());
        if (existingItem == true){
            String message = String.format("an item with itemCode %s already exists. You must use a different Item Code, Update, or Delete the item instead", item.getItemCode());
            throw new BadRequestException(message);
        }

        //3) Item is valid save item
        return itemDAL.saveItem(item);
    }

    /**
     * This endpoint updates the item associated with the item code in our persistence layer
     * @param item The Object which will be persisted
     * @param itemCode The key if the item to be updated
     * @return The Item which was updated
     * @throws NotFoundException If there is no item associated with the given itemCode
     * @throws BadRequestException The item and itemCode cannot be null
     * **/
    public Item updateItem(Item item, String itemCode){
        //1) Verify required parameters
        if (item == null || itemCode == null){
            throw new BadRequestException("Invalid Object. Neither the Object or the itemCode can be null");
        }

        //2) Verify required parameters and check if item already exists
        boolean existingItem = verifyItemExists(itemCode);
        if (existingItem == false){
            String message = String.format("No Item with itemCode %s was found. Please use the create endpoint instead ", itemCode);
            throw new NotFoundException(message);
        }
        //2) Item is valid save item
        item.setItemCode(itemCode);// The ItemCode in the URL will supersede the item on the object
        return itemDAL.saveItem(item);
    }

    /**
     * This endpoint deletes the item associated with the item code from our persistence layer
     * @param itemCode The key if the item to be deleted
     * @return The Item which was deleted
     * @throws NotFoundException if there is no item associated with the given itemCode
     * **/
    public Item DeleteItem(String itemCode){
        // 1) Delete item
        Item item = itemDAL.DeleteItem(itemCode);

        //2) check if item existed.
        if (item == null){
            String message = String.format("Cannot delete. No Item with itemCode %s was found.", item.getItemCode());
            throw new NotFoundException(message);
        }
        return item;
    }

}
