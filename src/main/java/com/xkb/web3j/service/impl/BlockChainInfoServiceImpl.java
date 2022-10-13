package com.xkb.web3j.service.impl;

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
        return ethBlockNumber.getBlockNumber();
    }

    @Override
    public List<String> getAllAccounts() throws Exception {
        EthAccounts ethAccounts = web3j.ethAccounts().sendAsync().get();
        return ethAccounts.getAccounts();
    }

    @Override
    public BigInteger getEthGasPrice() throws Exception {
        EthGasPrice ethGasPrice = web3j.ethGasPrice().sendAsync().get();
        return ethGasPrice.getGasPrice();
    }

    @Override
    public BigInteger getChainId() throws Exception {
        EthChainId ethChainId = web3j.ethChainId().sendAsync().get();
        return ethChainId.getChainId();
    }

    @Override
    public String getCoinBase() throws Exception {
        EthCoinbase ethCoinbase = web3j.ethCoinbase().sendAsync().get();
        return ethCoinbase.getAddress();
    }

    @Override
    public EthBlock.Block getAll(Long blockNumber) throws Exception {
        DefaultBlockParameterNumber defaultBlockParameterNumber = new DefaultBlockParameterNumber(blockNumber);
        EthBlock ethBlock = web3j.ethGetBlockByNumber(defaultBlockParameterNumber, true).sendAsync().get();
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

        return txInfos;
    }

    @Override
    public Transaction getTransactionInfoByHash(String txHash) throws Exception {

        EthTransaction transaction = web3j.ethGetTransactionByHash(txHash).sendAsync().get();
        Optional<Transaction> optionalTransaction = transaction.getTransaction();

        return optionalTransaction.orElse(null);
    }

    @Override
    public TransactionReceipt getTransactionReceiptByHash(@RequestParam(value = "txHash") String txHash) throws Exception {

        EthGetTransactionReceipt getTransactionReceipt = web3j.ethGetTransactionReceipt(txHash).sendAsync().get();
        Optional<TransactionReceipt> optionalTransactionReceipt = getTransactionReceipt.getTransactionReceipt();

        return optionalTransactionReceipt.orElse(null);
    }
}
