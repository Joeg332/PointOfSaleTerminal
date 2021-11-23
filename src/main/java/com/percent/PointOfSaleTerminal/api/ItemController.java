package com.percent.PointOfSaleTerminal.api;

import com.percent.PointOfSaleTerminal.handler.ItemHandler;
import com.percent.PointOfSaleTerminal.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Map;

@EnableSwagger2
@RequestMapping(value = "/v1", produces = "application/json")
@RestController
public class ItemController {

    private ItemHandler itemHandler;

    @Autowired
    public ItemController(ItemHandler itemHandler){
        this.itemHandler = itemHandler;
    }

    @GetMapping("/item")
    public Map<String, Item> getItems(){
        return itemHandler.getItems();
    }

    @GetMapping("/item/{itemCode}")
    public Item getItem(@PathVariable String itemCode){
        return itemHandler.getItem(itemCode);
    }

    @PostMapping("/item")
    public Item createItem(@RequestBody Item item){
        return itemHandler.createItem(item);
    }

    @PutMapping("/item/{itemCode}")
    public Item updateItem(@RequestBody Item item,
                           @PathVariable String itemCode){
        return itemHandler.updateItem(item, itemCode);
    }
    @DeleteMapping ("/item/{itemCode}")
    public Item deleteItem(@PathVariable String itemCode){
        return itemHandler.DeleteItem(itemCode);
    }

}
