package com.percent.PointOfSaleTerminal.api;

import com.percent.PointOfSaleTerminal.handler.TransactionHandler;
import com.percent.PointOfSaleTerminal.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.UUID;

@EnableSwagger2
@RequestMapping(value = "/v1", produces = "application/json")
@RestController
public class TransactionController {

    private TransactionHandler transactionHandler;

    @Autowired
    public TransactionController(TransactionHandler transactionHandler){
        this.transactionHandler = transactionHandler;
    }

    @GetMapping("/transaction/{transactionId}")
    public Transaction getTransaction(@PathVariable UUID transactionId){
        return transactionHandler.getTransaction(transactionId);
    }

    @PostMapping("/transaction/scan/{itemCode}")
    public Transaction createTransaction(@PathVariable String itemCode){
        return transactionHandler.createTransactionAndScan(itemCode);
    }

    @PutMapping("/transaction/{transactionId}/scan/{itemCode}")
    public Transaction updateTransaction(@PathVariable UUID transactionId, @PathVariable String itemCode){
        return transactionHandler.scanAndUpdateTransaction(transactionId, itemCode);
    }

    @DeleteMapping("/transaction/{transactionId}/transactionItem/{transactionItemId}")
    public Transaction removeItemFromTransaction(@PathVariable UUID transactionId, @PathVariable UUID transactionItemId){
        return transactionHandler.removeItemFromTransaction(transactionId, transactionItemId);
    }

    @GetMapping("/transaction/{transactionId}/calculateTotal")
    public Transaction removeItemFromTransaction(@PathVariable UUID transactionId){
        return transactionHandler.calculateTotal(transactionId);
    }
}
