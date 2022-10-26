package com.xkb.web3j.service;

import org.web3j.protocol.core.methods.response.Transaction;

public interface AsyncCustomTransactionService {

    void convertAndInsertTxInfo(Transaction txInfo) throws Exception;
}
