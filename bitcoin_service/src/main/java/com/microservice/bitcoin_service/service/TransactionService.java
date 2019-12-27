package com.microservice.bitcoin_service.service;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import com.zbsnetwork.zbsjava.Account;
import com.zbsnetwork.zbsjava.Asset;
import com.zbsnetwork.zbsjava.Node;
import com.zbsnetwork.zbsjava.PrivateKeyAccount;
import com.zbsnetwork.zbsjava.Transactions;
import com.zbsnetwork.zbsjava.transactions.TransferTransaction;

@Service
public class TransactionService{
	
	public String transfer(String seedSender, long amount, String seedReceiver){
		PrivateKeyAccount accountSender = PrivateKeyAccount.fromSeed(seedSender, 0, Account.TESTNET);
		PrivateKeyAccount accountReceiver = PrivateKeyAccount.fromSeed(seedReceiver, 0, Account.TESTNET);
		
		Node node = new Node();
		
		TransferTransaction tx = Transactions.makeTransferTx(accountSender, accountReceiver.getAddress(), 1 * Asset.TOKEN, Asset.ZBS, amount * Asset.TOKEN, Asset.ZBS, "Here's some Asset for you!");
		try {
			node.send(tx);
			return "transaction succeed.";
		} catch (IOException e) {
			return e.getMessage();
		}
	}
}
