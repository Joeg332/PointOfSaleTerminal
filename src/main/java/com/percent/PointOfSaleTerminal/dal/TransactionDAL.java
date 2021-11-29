package com.percent.PointOfSaleTerminal.dal;

import com.percent.PointOfSaleTerminal.model.Transaction;
import com.percent.PointOfSaleTerminal.model.TransactionItem;
import com.percent.PointOfSaleTerminal.model.WholesaleTracker;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * This DAL service will be used to mock up a connection to the Database.
 * Ideally We would use SpringData with Repository Interfaces to query data to teh database,
 * However for this exercise we are simply going to keep the data in memory with simple data models
 * **/
@Component
public class TransactionDAL {

    private Map<UUID, Transaction> transactions;

    public TransactionDAL(){
        transactions = new HashMap<>();
    }

    public Transaction getTransaction(UUID transactionId){
        return transactions.get(transactionId);
    }

    public Transaction deleteTransaction(UUID transactionId){
        return transactions.remove(transactionId);
    }

    public Transaction saveTransaction(Transaction transaction){
        //1) Generate Id
        UUID newId = UUID.randomUUID();
        transaction.setTransactionId(newId);
        //2) initialize children
        transaction.setTransactionItems(new LinkedHashMap<>());
        transaction.setWholesaleTrackers(new HashMap<>());
        //3) Save
        transactions.put(newId, transaction);
        //4) Return
        return transaction;
    }


    public Transaction addItemToTransaction(UUID transactionId, TransactionItem transactionItem){
        //1) Create new TransactionItem on a transaction
        UUID newId = UUID.randomUUID();
        transactionItem.setTransactionItemId(newId);
        transactions.get(transactionId).getTransactionItems().put(newId, transactionItem);

        //2) return
        return transactions.get(transactionId);
    }

    public Transaction saveWholesaleTrackerOnTransaction(UUID transactionId, WholesaleTracker wholesaleTracker){
        transactions.get(transactionId).getWholesaleTrackers().put(wholesaleTracker.getItemCode(), wholesaleTracker);
        return transactions.get(transactionId);
    }

    public Transaction removeItemFromTransaction(UUID transactionId, UUID transactionItemId){
        //1) Remove the existing transactionItem from the list
        transactions.get(transactionId).getTransactionItems().remove(transactionItemId);

        //2) return
        return transactions.get(transactionId);
    }

}
