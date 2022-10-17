package com.xkb.web3j.controller;

import com.google.gson.Gson;
import com.xkb.web3j.service.BlockChainDataService;
import com.xkb.web3j.service.CustomBlockService;
import com.xkb.web3j.service.CustomTransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Transaction;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/collectBlkAndTx")
public class DataCollectController {

    private static final Logger logger = LoggerFactory.getLogger(DataCollectController.class);

    @Autowired
    private BlockChainDataService blockChainDataService;

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
    public String collectLatestData() throws Exception {

        BigInteger latestBlockNumber = blockChainDataService.getLatestBlockNumber();
        logger.info("Latest block number: #{}", latestBlockNumber);
        collectDataByBlockNumber(latestBlockNumber);

        return "Success";
    }

    /**
     * @return String
     * @description 获取某个时间点之后所有的区块和相应的全部交易信息并保存到 MySQL
     * @author xkb
     * @date 2022/10/13 20:20
     */
    @GetMapping("/afterAppointedTime")
    public String collectDataAfterAppointedTime() throws Exception {

        // Todo: Get the latest blockNumber
        BigInteger latestBlockNumber = blockChainDataService.getLatestBlockNumber();
        logger.info("Latest block number: #{}", latestBlockNumber);

        // Todo: Get target timestamp
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime targetDateTime = LocalDateTime.parse("2022-10-17 15:24:00", dtf);
        BigInteger targetTimestamp = BigInteger.valueOf(
                targetDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() / 1000L);

        // Todo: Binary search, get the first blockNumber after the appointed timestamp
        BigInteger l = new BigInteger("0");
        BigInteger r = latestBlockNumber;

        while (l.compareTo(r) <= 0) {
            BigInteger midBlockNumber = l.add(r).divide(new BigInteger("2"));
            logger.info("midBlockNumber: {}", midBlockNumber);
            BigInteger midTimestamp = blockChainDataService.getBlockInfoByBlockNumber(midBlockNumber).getTimestamp();

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
            collectDataByBlockNumber(i);
        }

        return "Success";
    }

    /**
     * @return String
     * @description 获取两个区块号之间（包含左右边界）所有的区块和相应的全部交易信息并保存到 MySQL
     * @author xkb
     * @date 2022/10/17
     */
    @GetMapping("/betweenTwoBlockNumbers")
    public String collectDataBtwTwoBlkNum(
            @RequestParam(value = "blockNumber1") BigInteger blockNumber1,
            @RequestParam(value = "blockNumber2") BigInteger blockNumber2) throws Exception {

        for (BigInteger i = blockNumber1;
             i.compareTo(blockNumber2) <= 0;
             i = i.add(BigInteger.valueOf(1))) {      // "i =" 不要忘记，否则 i 的值没有被改变，会进入死循环

            System.out.println("i = " + i);
            collectDataByBlockNumber(i);
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
    public String collectDataByBlockNumber(
            @RequestParam(value = "blockNumber") BigInteger blockNumber) throws Exception {

        // Todo: Get the block info by blockNumber
        EthBlock.Block blockInfo = blockChainDataService.getBlockInfoByBlockNumber(blockNumber);
        Gson gson = new Gson();
        String info = gson.toJson(blockInfo);
        logger.info("Info of block #{}: {}", blockNumber, info);

        // Todo: Get the txInfos in the block by blockNumber
        List<EthBlock.TransactionResult> transactionResults = blockInfo.getTransactions();
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
