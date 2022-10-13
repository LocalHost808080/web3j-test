package com.xkb.web3j.controller;

import com.google.gson.Gson;
import com.xkb.web3j.service.BlockChainInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.core.methods.response.*;

import java.math.BigInteger;
import java.util.List;

/**
 * @description 获取区块链信息
 * @author newonexd
 * @date 2022/6/22 21:14
 */
@RestController
@RequestMapping("/chainInfo")
public class BlockChainInfoController {

    private static final Logger logger = LoggerFactory.getLogger(BlockChainInfoController.class);

    @Autowired
    private BlockChainInfoService blockChainInfoService;

    /**
     * @description 获取最新的区块号
     * @author newonexd
     * @date 2022/6/22 21:11
     *
     * @return BigInteger
     */
    @GetMapping("/blockNumber")
    public BigInteger doGetLatestBlockNumber() throws Exception {
        BigInteger blockNumber = blockChainInfoService.getLatestBlockNumber();
        logger.info("BlockNumber: {}", blockNumber);
        return blockNumber;
    }

    /**
     * @description 获取所有账户
     * @author newonexd
     * @date 2022/6/22 21:11
     *
     * @return List<String>
     */
    @GetMapping("/accounts")
    public List<String> doGetAllAccounts() throws Exception {
        List<String> accounts = blockChainInfoService.getAllAccounts();
        logger.info("Accounts: {}", accounts);
        return accounts;
    }

    /**
     * @description 获取Gas价格
     * @author newonexd
     * @date 2022/6/22 21:11
     *
     * @return BigInteger
     */
    @GetMapping("/gasPrice")
    public BigInteger doGetEthGasPrice() throws Exception {
        BigInteger gasPrice = blockChainInfoService.getEthGasPrice();
        logger.info("Ethereum Gas Price: {}", gasPrice);
        return gasPrice;
    }

    /**
     * @description 获取链Id
     * @author newonexd
     * @date 2022/6/22 21:12
     *
     * * @return BigInteger
     */
    @GetMapping("/chainId")
    public BigInteger doGetChainId() throws Exception {
        BigInteger chainId = blockChainInfoService.getChainId();
        logger.info("Ethereum Chain Id: {}", chainId);
        return chainId;
    }

    /**
     * @description 获取CoinBase
     * @author newonexd
     * @date 2022/6/22 21:12
     *
     * * @return String
     */
    @GetMapping("/coinbase")
    public String doGetCoinBase() throws Exception {
        String coinBase = blockChainInfoService.getCoinBase();
        logger.info("Ethereum CoinBase Address: {}", coinBase);
        return coinBase;
    }

    /**
     * @description 根据区块号获取区块信息
     * @author newonexd
     * @date 2022/6/22 21:12
     * @param blockNumber  区块号
     * @return String
     */
    @GetMapping("/getBlockInfo")
    public String doGetAll(@RequestParam(value = "blockNumber") Long blockNumber) throws Exception {
        EthBlock.Block block = blockChainInfoService.getAll(blockNumber);
        Gson gson = new Gson();
        String info = gson.toJson(block);
        logger.info(info);
        return info;
    }

    /**
     * @description 根据区块号获取所有交易
     * @author newonexd
     * @date 2022/6/22 21:13
     * @param blockNumber 区块号
     * @return String
     */
    @GetMapping("/getTransactionByBlockNumber")
    public String doGetTransactionInfoByBlockNumber(
            @RequestParam(value="blockNumber") Long blockNumber) throws Exception {

        List<Transaction> txInfos = blockChainInfoService.getTransactionInfoByBlockNumber(blockNumber);
        Gson gson = new Gson();
        String transactionInfo = gson.toJson(txInfos);
        logger.info(transactionInfo);
        return transactionInfo;
    }

    /**
     * @description 根据交易哈希值获取交易信息
     * @author newonexd
     * @date 2022/6/22 21:13
     * @param txHash 交易哈希值
     * @return String
     */
    @GetMapping("/getTransactionInfoByHash")
    public String doGetTransactionInfoByHash(@RequestParam(value = "txHash") String txHash) throws Exception {

        Transaction transactionInfo = blockChainInfoService.getTransactionInfoByHash(txHash);
        StringBuilder txInfo = new StringBuilder();

        if (transactionInfo != null) {
            Gson gson = new Gson();
            txInfo.append(gson.toJson(transactionInfo));
        }

        logger.info(txInfo.toString());
        return txInfo.toString();
    }

    /**
     * @description 根据交易哈希值获取交易回执
     * @author xkb
     * @date 2022.10.10
     * @param txHash
     * @return String
     */
    @GetMapping("/getTransactionReceiptByHash")
    public String doGetTransactionReceiptByHash(@RequestParam(value = "txHash") String txHash) throws Exception {

        TransactionReceipt transactionReceipt = blockChainInfoService.getTransactionReceiptByHash(txHash);
        StringBuilder txRcpt = new StringBuilder();

        if (transactionReceipt != null) {
            Gson gson = new Gson();
            txRcpt.append(gson.toJson(transactionReceipt));
        }

        logger.info("Transaction Receipt: {}", txRcpt);
        return txRcpt.toString();
    }
}
