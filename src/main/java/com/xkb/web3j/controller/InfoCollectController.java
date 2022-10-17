package com.xkb.web3j.controller;

import com.google.gson.Gson;
import com.xkb.web3j.service.CustomBlockService;
import com.xkb.web3j.service.CustomTransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.Transaction;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/collectBlkAndTx")
public class InfoCollectController {

    private static final Logger logger = LoggerFactory.getLogger(InfoCollectController.class);

    @Autowired
    private Web3j web3j;

    @Autowired
    private CustomBlockService customBlockService;

    @Autowired
    private CustomTransactionService customTransactionService;

    /**
     * @return String
     * @description 获取最新的区块和相应的全部交易信息并保存到 MySQL
     * @author xkb
     * @date 2022/10/11 19:20
     */
    @GetMapping("/latestBlock")
    public String collectLatestInfo() throws Exception {

        // Todo: Get the latest blockNumber
        EthBlockNumber ethBlockNumber = web3j.ethBlockNumber().sendAsync().get();
        BigInteger latestBlockNumber = ethBlockNumber.getBlockNumber();
        logger.info("Latest block number: #{}", latestBlockNumber);

        // Todo: Get the latest block info and tx infos by blockNumber
        collectInfoByBlockNumber(latestBlockNumber);

        return "Success";
    }

    /**
     * @return String
     * @description 获取某个时间点之后所有的区块和相应的全部交易信息并保存到 MySQL
     * @author xkb
     * @date 2022/10/13 20:20
     */
    @GetMapping("/afterAppointedTime")
    public String collectInfoAfterAppointedTime() throws Exception {

        // Todo: Get the latest blockNumber
        EthBlockNumber ethBlockNumber = web3j.ethBlockNumber().sendAsync().get();
        BigInteger latestBlockNumber = ethBlockNumber.getBlockNumber();
        logger.info("Latest block number: #{}", latestBlockNumber);

        // Todo: Get target timestamp
        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, Calendar.OCTOBER, 13, 22, 54, 0);   // 月份写 10 的话实际上是指 11 月
        Date targetTime = calendar.getTime();
        BigInteger targetTimestamp = BigInteger.valueOf(targetTime.getTime() / 1000L);    // 1664553600L;

        // Todo: Binary search, get the first blockNumber on 2022.10.01
        BigInteger l = new BigInteger("0");
        BigInteger r = latestBlockNumber;

        while (l.compareTo(r) <= 0) {
            BigInteger midBlockNumber = l.add(r).divide(new BigInteger("2"));
            logger.info("midBlockNumber: {}", midBlockNumber);
            DefaultBlockParameterNumber defaultBlockParameterNumber = new DefaultBlockParameterNumber(midBlockNumber);
            EthBlock ethBlock = web3j.ethGetBlockByNumber(defaultBlockParameterNumber, true).sendAsync().get();
            BigInteger midTimestamp = ethBlock.getBlock().getTimestamp();

            if (midTimestamp.compareTo(targetTimestamp) < 0)
                l = midBlockNumber.add(BigInteger.valueOf(1));
            else
                r = midBlockNumber.subtract(BigInteger.valueOf(1));
        }

        // Todo: Save the info of all blocks and transactions after the appointed time
        BigInteger startBlockNumber = l;
        logger.info("startBlockNumber: {}", startBlockNumber);
        logger.info("latestBlockNumber: {}", latestBlockNumber);

        for (BigInteger i = startBlockNumber;
             i.compareTo(latestBlockNumber) <= 0;
             i = i.add(BigInteger.valueOf(1))) {  // "i =" 不要忘记，否则 i 的值没有被改变，会进入死循环

            System.out.println("i = " + i);
            collectInfoByBlockNumber(i);
        }

        return "Success";
    }

    /**
     * @return String
     * @description 获取指定序号的区块和相应的全部交易信息并保存到 MySQL
     * @author xkb
     * @date 2022/10/13 22:00
     */
    @GetMapping("/byBlockNumber")
    public String collectInfoByBlockNumber(
            @RequestParam(value = "blockNumber") BigInteger blockNumber) throws Exception {

        // Todo: Get the block info by blockNumber
        DefaultBlockParameterNumber defaultBlockParameterNumber = new DefaultBlockParameterNumber(blockNumber);
        EthBlock ethBlock = web3j.ethGetBlockByNumber(defaultBlockParameterNumber, true).sendAsync().get();
        EthBlock.Block blockInfo = ethBlock.getBlock();
        Gson gson = new Gson();
        String info = gson.toJson(blockInfo);
        logger.info("Info of block #{}: {}", blockNumber, info);

        // Todo: Get the txInfos in the block by blockNumber
        List<EthBlock.TransactionResult> transactionResults = ethBlock.getBlock().getTransactions();
        List<Transaction> txInfos = new ArrayList<>();

        transactionResults.forEach(txInfo -> {
            Transaction transaction = (Transaction) txInfo;
            txInfos.add(transaction);
        });

        String transactionInfo = gson.toJson(txInfos);
        logger.info("Transactions in the block #{}: {}", blockNumber, transactionInfo);

        // Todo: Save the block and transaction info into the database
        int saveBlockCnt = customBlockService.saveBlockInfo(blockInfo);
        logger.info("Info of {} block is saved.", saveBlockCnt);

        int saveTxCnt = customTransactionService.saveTransactionInfo(txInfos);
        logger.info("Info of {} transactions is saved.", saveTxCnt);

        return "Success";
    }
}
