package com.xkb.web3j.component;

import com.xkb.web3j.service.BlockChainDataService;
import com.xkb.web3j.service.LatestBlockNumberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class InitialBlockNumberSaver implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(InitialBlockNumberSaver.class);

    @Autowired
    private BlockChainDataService blockChainDataService;

    @Autowired
    private LatestBlockNumberService latestBlockNumberService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        BigInteger latestBlockNumber = blockChainDataService.getLatestBlockNumber();
        latestBlockNumberService.saveLatest(latestBlockNumber);
        logger.info("Save latest blockNumber #{} in Redis.", latestBlockNumber);
    }
}
