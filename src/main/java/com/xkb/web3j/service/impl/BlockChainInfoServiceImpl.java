package com.xkb.web3j.service.impl;

import com.google.gson.Gson;
import com.xkb.web3j.service.BlockChainInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BlockChainInfoServiceImpl implements BlockChainInfoService {

    private static final Logger logger = LoggerFactory.getLogger(BlockChainInfoServiceImpl.class);

    @Autowired
    private Web3j web3j;

    @Override
    public BigInteger getLatestBlockNumber() throws Exception {
        EthBlockNumber ethBlockNumber = web3j.ethBlockNumber().sendAsync().get();
        // BigInteger blockNumber = ethBlockNumber.getBlockNumber();
        // logger.info("BlockNumber: {}", blockNumber);
        // return blockNumber;
        return ethBlockNumber.getBlockNumber();
    }

    @Override
    public List<String> getAllAccounts() throws Exception {
        EthAccounts ethAccounts = web3j.ethAccounts().sendAsync().get();
        // List<String> accounts = ethAccounts.getAccounts();
        // logger.info("Accounts: {}", accounts);
        // return accounts;
        return ethAccounts.getAccounts();
    }

    @Override
    public BigInteger getEthGasPrice() throws Exception {
        EthGasPrice ethGasPrice = web3j.ethGasPrice().sendAsync().get();
        // BigInteger gasPrice = ethGasPrice.getGasPrice();
        // logger.info("Ethereum Gas Price: {}", gasPrice);
        // return gasPrice;
        return ethGasPrice.getGasPrice();
    }

    @Override
    public BigInteger getChainId() throws Exception {
        EthChainId ethChainId = web3j.ethChainId().sendAsync().get();
        // BigInteger chainId = ethChainId.getChainId();
        // logger.info("Ethereum Chain Id: {}", chainId);
        // return chainId;
        return ethChainId.getChainId();
    }

    @Override
    public String getCoinBase() throws Exception {
        EthCoinbase ethCoinbase = web3j.ethCoinbase().sendAsync().get();
        // String coinBase = ethCoinbase.getAddress();
        // logger.info("Ethereum CoinBase Address: {}", coinBase);
        // return coinBase;
        return ethCoinbase.getAddress();
    }

    @Override
    public EthBlock.Block getAll(Long blockNumber) throws Exception {
        DefaultBlockParameterNumber defaultBlockParameterNumber = new DefaultBlockParameterNumber(blockNumber);
        EthBlock ethBlock = web3j.ethGetBlockByNumber(defaultBlockParameterNumber, true).sendAsync().get();
        // EthBlock.Block block = ethBlock.getBlock();
        // Gson gson = new Gson();
        // String info = gson.toJson(block);
        // logger.info(info);
        // return info;
        return ethBlock.getBlock();
    }

    @Override
    public List<Transaction> getTransactionInfoByBlockNumber(Long blockNumber) throws Exception {

        DefaultBlockParameterNumber defaultBlockParameterNumber = new DefaultBlockParameterNumber(blockNumber);
        EthBlock ethBlock = web3j.ethGetBlockByNumber(defaultBlockParameterNumber, true).sendAsync().get();
        List<EthBlock.TransactionResult> transactionResults = ethBlock.getBlock().getTransactions();
        List<Transaction> txInfos = new ArrayList<>();

        transactionResults.forEach(txInfo -> {
            Transaction transaction = (Transaction) txInfo;
            txInfos.add(transaction);
        });

        // Gson gson = new Gson();
        // String transactionInfo = gson.toJson(txInfos);
        // logger.info(transactionInfo);
        // return transactionInfo;
        return txInfos;
    }

    @Override
    public Transaction getTransactionInfoByHash(String txHash) throws Exception {

        EthTransaction transaction = web3j.ethGetTransactionByHash(txHash).sendAsync().get();
        Optional<Transaction> optionalTransaction = transaction.getTransaction();
        // StringBuilder txInfo = new StringBuilder();

        // if (optionalTransaction.isPresent()) {
        //     Transaction transactionInfo = optionalTransaction.get();
            // Gson gson = new Gson();
            // txInfo.append(gson.toJson(transactionInfo));
        // }

        // logger.info(txInfo.toString());
        // return txInfo.toString();

        return optionalTransaction.orElse(null);
    }

    @Override
    public TransactionReceipt getTransactionReceiptByHash(@RequestParam(value = "txHash") String txHash) throws Exception {

        EthGetTransactionReceipt getTransactionReceipt = web3j.ethGetTransactionReceipt(txHash).sendAsync().get();
        Optional<TransactionReceipt> optionalTransactionReceipt = getTransactionReceipt.getTransactionReceipt();
        // StringBuilder txRcpt = new StringBuilder();
        //
        // if (optionalTransactionReceipt.isPresent()) {
        //     TransactionReceipt transactionReceipt = optionalTransactionReceipt.get();
        //     Gson gson = new Gson();
        //     txRcpt.append(gson.toJson(transactionReceipt));
        // }
        //
        // logger.info("Transaction Receipt: {}", txRcpt);
        // return txRcpt.toString();

        return optionalTransactionReceipt.orElse(null);
    }
}
