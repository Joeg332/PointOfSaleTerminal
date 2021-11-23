package com.percent.PointOfSaleTerminal.api;

import com.percent.PointOfSaleTerminal.handler.ItemHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@RequestMapping(value = "/v1", produces = "application/json")
@RestController
public class ItemController {

    private ItemHandler itemHandler;

    @Autowired
    public ItemController(ItemHandler itemHandler){
        this.itemHandler = itemHandler;
    }




}
