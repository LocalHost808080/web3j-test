package com.xkb.web3j.component;

import com.xkb.web3j.controller.DataCollectController;
import com.xkb.web3j.service.BlockChainDataService;
import com.xkb.web3j.service.LatestBlockNumberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigInteger;


@Component
public class DataSyncTimedTask {

    private static final Logger logger = LoggerFactory.getLogger(DataSyncTimedTask.class);

    @Autowired
    private BlockChainDataService blockChainDataService;

    @Autowired
    private LatestBlockNumberService latestBlockNumberService;

    @Autowired
    private DataCollectController dataCollectController;

    /**
     * cron 表达式：Seconds Minutes Hours DayofMonth Month DayofWeek [Year]
     * 从第一分钟时开始，每分钟同步一次，从上次读取的最新区块编号加一开始，到新读取的区块编号为止，保存中间所有区块和交易数据
     */
    @Scheduled(cron = "0 1/1 * ? * ?")
    private void syncBlkAndTxDataInLastMin() throws Exception {

        BigInteger startBlockNumber = latestBlockNumberService.getLast().add(BigInteger.valueOf(1));
        BigInteger latestBlockNumber = blockChainDataService.getLatestBlockNumber();

        latestBlockNumberService.saveLatest(latestBlockNumber);
        logger.info("Save latest blockNumber 【#{}】 in Redis.", latestBlockNumber);

        logger.info("Start to collect data from blocks 【#{}】 to 【#{}】", startBlockNumber, latestBlockNumber);
        dataCollectController.collectDataBtwTwoBlkNum(startBlockNumber, latestBlockNumber);
    }
}
