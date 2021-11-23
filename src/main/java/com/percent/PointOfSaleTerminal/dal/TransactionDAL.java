package com.percent.PointOfSaleTerminal.dal;

import com.percent.PointOfSaleTerminal.model.Transaction;
import com.percent.PointOfSaleTerminal.model.TransactionItem;
import com.percent.PointOfSaleTerminal.model.WholesaleTracker;
import org.springframework.stereotype.Component;

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

    public Transaction getTransaction(UUID transactionId){
        return transactions.get(transactionId);
    }

    public Transaction DeleteTransaction(UUID transactionId){
        return transactions.remove(transactionId);
    }

    public Transaction saveTransaction(Transaction transaction){
        UUID newId = UUID.randomUUID();
        transaction.setTransactionId(newId);
        return  transactions.put(newId, transaction);
    }


    public Transaction addItemToTransaction(UUID transactionId, TransactionItem transactionItem){
        //1) Create new TransactionItem on a transaction
        UUID newId = UUID.randomUUID();
        transactionItem.setTransactionItemId(newId);
        transactions.get(transactionId).getTransactionItems().put(newId, transactionItem);

//        //2) update the wholesaleTracker
//        //ToDo: handle case where wholesaleTracker is initialized
//        //ToDo: Determine if we want to move this logic into the handler
//        WholesaleTracker wholesaleTracker = transactions.get(transactionId).getWholesaleTrackers().get(transactionItem.getItemCode());
//        wholesaleTracker.setCount(wholesaleTracker.getCount()+1);
//        transactions.get(transactionId).getWholesaleTrackers().put(wholesaleTracker.getItemCode(), wholesaleTracker);

        //3) return
        return transactions.get(transactionId);
    }

    public Transaction saveWholesaleTrackerOnTransaction(UUID transactionId, WholesaleTracker wholesaleTracker){
        transactions.get(transactionId).getWholesaleTrackers().put(wholesaleTracker.getItemCode(), wholesaleTracker);
        return transactions.get(transactionId);

    }

    public Transaction removeItemFromTransaction(UUID transactionId, UUID transactionItemId){
//        //1) Get Existing TransactionItem
//        TransactionItem transactionItem = transactions.get(transactionId).getTransactionItems().get(transactionItemId);

        //2) Remove the existing transactionItem from the list
        transactions.get(transactionId).getTransactionItems().remove(transactionItemId);

//        //3) update the wholesaleTracker
//        //ToDo: handle case where wholesaleTracker is initialized
//        //ToDo: Determine if we want to move this logic into the handler
//        WholesaleTracker wholesaleTracker = transactions.get(transactionId).getWholesaleTrackers().get(transactionItem.getItemCode());
//        wholesaleTracker.setCount(wholesaleTracker.getCount()-1);
//        transactions.get(transactionId).getWholesaleTrackers().put(wholesaleTracker.getItemCode(), wholesaleTracker);

        //4) return
        return transactions.get(transactionId);
    }






}
