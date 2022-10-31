package com.xkb.web3j.service;

import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

public interface AsyncCustomTransactionService {

    void convertAndInsertTxInfo(Transaction txInfo) throws Exception;

    void identifyErcTypeAndSave(TransactionReceipt txRcpt);
}
