package com.xkb.web3j.service;

import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.List;

public interface BlockChainDataService {

    BigInteger getLatestBlockNumber() throws Exception;

    List<String> getAllAccounts() throws Exception;

    BigInteger getEthGasPrice() throws Exception;

    BigInteger getChainId() throws Exception;

    String getCoinBase() throws Exception;

    EthBlock.Block getBlockInfoByBlockNumber(BigInteger blockNumber) throws Exception;

    List<Transaction> getTransactionInfoByBlockNumber(BigInteger blockNumber) throws Exception;

    Transaction getTransactionInfoByHash(String txHash) throws Exception;

    TransactionReceipt getTransactionReceiptByHash(String txHash) throws Exception;
}
