package com.percent.PointOfSaleTerminal.handler;

import com.percent.PointOfSaleTerminal.dal.TransactionDAL;
import com.percent.PointOfSaleTerminal.model.Item;
import com.percent.PointOfSaleTerminal.model.Transaction;
import com.percent.PointOfSaleTerminal.model.TransactionItem;
import com.percent.PointOfSaleTerminal.model.WholesaleTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class TransactionHandler {

    private ItemHandler itemHandler;
    private TransactionDAL transactionDAL;

    @Autowired
    public TransactionHandler(ItemHandler itemHandler, TransactionDAL transactionDAL){
        this.itemHandler = itemHandler;
        this.transactionDAL = transactionDAL;
    }

    public Transaction getTransaction(UUID transactionId){
        return transactionDAL.getTransaction(transactionId);
    }

    //ToDo: Valid error handling
    //toDo: Javadoc
    //Note: If I were using a different persistence model such as spring data I wouldn't need to split this up into multiple save actions however since
    // I'm generating the UUID in the dal I need to get the ID back before I can persist child elements
    public Transaction createTransactionAndScan(String itemCode){
        //1) Get item from ItemHandler
        itemHandler.getItem(itemCode);

        //2) create Transaction
        Transaction transaction = new Transaction();
        transaction = transactionDAL.saveTransaction(transaction);

        //3) Add TransactionItem to transaction
        TransactionItem transactionItem = TransactionItem.builder()
                .transactionId(transaction.getTransactionId())
                .itemCode(itemCode)
                .build();
        transaction = transactionDAL.addItemToTransaction(transaction.getTransactionId(), transactionItem);

        //3) add WholesaleItem to Transaction
        WholesaleTracker wholesaleTracker = WholesaleTracker.builder()
                .transactionId(transaction.getTransactionId())
                .itemCode(itemCode)
                .count(1)
                .build();
        transaction = transactionDAL.saveWholesaleTrackerOnTransaction(transaction.getTransactionId(), wholesaleTracker);

        //4) return transaction
        return transaction;
    }

    //ToDo: Valid error handling
    //toDo: Javadoc
    public Transaction scanAndUpdateTransaction(UUID transactionId, String itemCode){
        //1) Get item from ItemHandler
        itemHandler.getItem(itemCode);

        //2) create Transaction
        Transaction transaction = transactionDAL.getTransaction(transactionId);

        //3) Add TransactionItem to transaction
        TransactionItem transactionItem = TransactionItem.builder()
                .transactionId(transaction.getTransactionId())
                .itemCode(itemCode)
                .build();
        transaction = transactionDAL.addItemToTransaction(transaction.getTransactionId(), transactionItem);

        //3) add or update WholesaleItem on Transaction
        WholesaleTracker wholesaleTracker = transaction.getWholesaleTrackers().get(itemCode);
        if(wholesaleTracker == null){
            wholesaleTracker = WholesaleTracker.builder()
                    .transactionId(transaction.getTransactionId())
                    .itemCode(itemCode)
                    .count(1)
                    .build();
        } else {
            wholesaleTracker.setCount(wholesaleTracker.getCount() +1);
        }
        transaction = transactionDAL.saveWholesaleTrackerOnTransaction(transaction.getTransactionId(), wholesaleTracker);


        //4) return transaction
        return transaction;
    }

    public Transaction calculateTotal(UUID transactionId){
        //ToDo: Valid error handling
        //toDo: Javadoc
        //1) verify valid TransactionId
        Transaction transaction = transactionDAL.getTransaction(transactionId);

        double totalBeforeSavings = 0;
        double totalAfterSavings = 0;
        double totalSavings = 0;

        for (Map.Entry entry : transaction.getWholesaleTrackers().entrySet()){
            WholesaleTracker wholesaleTracker = (WholesaleTracker) entry.getValue();

            Item item = itemHandler.getItem(wholesaleTracker.getItemCode());
            totalBeforeSavings += item.getItemPrice() * wholesaleTracker.getCount();

            if (item.getWholesaleOrder() != null && wholesaleTracker.getCount() >= item.getWholesaleOrder().getWholesaleThreshold()){
                //ToDo: figure this out its too late and my brain is not working
                int threshold = item.getWholesaleOrder().getWholesaleThreshold();
                int bundles = wholesaleTracker.getCount() / threshold;
                int remainder = wholesaleTracker.getCount() % threshold;
                totalAfterSavings += (bundles * item.getWholesaleOrder().getWholesalePrice()) + (remainder * item.getItemPrice());
            } else {
                totalAfterSavings += totalBeforeSavings;
            }
            totalSavings += totalBeforeSavings - totalAfterSavings;


        }

        transaction.setTotalBeforeSavings(totalBeforeSavings);
        transaction.setTotalAfterSavings(totalAfterSavings);
        transaction.setTotalSavings(totalSavings);
        return transaction;
    }

    //ToDo: Valid error handling
    //toDo: Javadoc
    public Transaction removeItemFromTransaction(UUID transactionId, UUID transactionItemId){
        //1) Get TransactionItem so that we can obtain the itemCode
        TransactionItem transactionItem = transactionDAL.getTransaction(transactionId).getTransactionItems().get(transactionItemId);

        //2) remove item from transaction
        Transaction transaction = transactionDAL.removeItemFromTransaction(transactionId, transactionItemId);

        //3) update wholesale tracker to reflect changes
        WholesaleTracker wholesaleTracker = transaction.getWholesaleTrackers().get(transactionItem.getItemCode());
        wholesaleTracker.setCount(wholesaleTracker.getCount()-1);
        transaction = transactionDAL.saveWholesaleTrackerOnTransaction(transactionId, wholesaleTracker);

        //4) return transaction
        return transaction;
    }

}
