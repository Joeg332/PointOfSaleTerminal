package com.percent.PointOfSaleTerminal.handler;

import com.percent.PointOfSaleTerminal.dal.ItemDAL;
import com.percent.PointOfSaleTerminal.dal.TransactionDAL;
import com.percent.PointOfSaleTerminal.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TransactionHandlerTests {

	private TransactionHandler transactionHandler;
	private ItemHandler itemHandler;
	private TransactionDAL transactionDAL;
	private ItemDAL itemDAL;

	@BeforeEach
	public void setupDefaultMocks(){
		//Note: Normally I would mock these for a unit test but Seeing as I'm doing an "integration test"
		// I'm going to instantiate them instead
		itemDAL = new ItemDAL();
		itemHandler = new ItemHandler(itemDAL);
		transactionDAL = new TransactionDAL();

		//Note: Normally the values passed in here would be mocked but this is more similar to an integration test
		// than a unit test due to the requirements of testing specific scenarios
		transactionHandler = new TransactionHandler(itemHandler, transactionDAL);

	}

	@Test
	public void testScenario1ABCDABASuccess(){
		Transaction transaction = transactionHandler.createTransactionAndScan("A");
		UUID transactionId = transaction.getTransactionId();
		transaction = transactionHandler.scanAndUpdateTransaction(transactionId, "B");
		transaction = transactionHandler.scanAndUpdateTransaction(transactionId, "C");
		transaction = transactionHandler.scanAndUpdateTransaction(transactionId, "D");
		transaction = transactionHandler.scanAndUpdateTransaction(transactionId, "A");
		transaction = transactionHandler.scanAndUpdateTransaction(transactionId, "B");
		transaction = transactionHandler.scanAndUpdateTransaction(transactionId, "A");
		transaction = transactionHandler.calculateTotal(transactionId);
		assertEquals(13.25, transaction.getTotalAfterSavings());
		assertEquals(14, transaction.getTotalBeforeSavings());
		assertEquals(0.75, transaction.getTotalSavings());
	}

	@Test
	public void testScenario2CCCCCCCSuccess(){
		Transaction transaction = transactionHandler.createTransactionAndScan("C");
		UUID transactionId = transaction.getTransactionId();
		transaction = transactionHandler.scanAndUpdateTransaction(transactionId, "C");
		transaction = transactionHandler.scanAndUpdateTransaction(transactionId, "C");
		transaction = transactionHandler.scanAndUpdateTransaction(transactionId, "C");
		transaction = transactionHandler.scanAndUpdateTransaction(transactionId, "C");
		transaction = transactionHandler.scanAndUpdateTransaction(transactionId, "C");
		transaction = transactionHandler.scanAndUpdateTransaction(transactionId, "C");
		transaction = transactionHandler.calculateTotal(transactionId);
		assertEquals(6, transaction.getTotalAfterSavings());
		assertEquals(7, transaction.getTotalBeforeSavings());
		assertEquals(1, transaction.getTotalSavings());
	}

	@Test
	public void testScenario3ABCDSuccess(){
		Transaction transaction = transactionHandler.createTransactionAndScan("A");
		UUID transactionId = transaction.getTransactionId();
		transaction = transactionHandler.scanAndUpdateTransaction(transactionId, "B");
		transaction = transactionHandler.scanAndUpdateTransaction(transactionId, "C");
		transaction = transactionHandler.scanAndUpdateTransaction(transactionId, "D");
		transaction = transactionHandler.calculateTotal(transactionId);
		assertEquals(7.25, transaction.getTotalAfterSavings());
		assertEquals(7.25, transaction.getTotalBeforeSavings());
		assertEquals(0, transaction.getTotalSavings());
	}

}
