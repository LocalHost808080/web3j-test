package com.xkb.web3j.controller;

import com.google.gson.Gson;
import com.xkb.web3j.service.CustomBlockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.Transaction;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/infoCollect")
public class InfoCollectController {

    private static final Logger logger = LoggerFactory.getLogger(BlockChainInfoController.class);

    @Autowired
    private Web3j web3j;

    @Autowired
    private CustomBlockService customBlockService;

    /**
     * @description 获取最新的区块和相应的全部交易信息并保存到 MySQL
     * @author xkb
     * @date 2022/10/11 19:20
     * @return String
     */
    @GetMapping("/latestBlkAndTrx")
    public String collectLatestBlockAndTransactionsInfo() throws Exception {

        // Todo: Get the latest blockNumber
        EthBlockNumber ethBlockNumber = web3j.ethBlockNumber().sendAsync().get();
        BigInteger blockNumber = ethBlockNumber.getBlockNumber();
        logger.info("Latest block number: #{}", blockNumber);

        // Todo: Get the latest block info by blockNumber
        DefaultBlockParameterNumber defaultBlockParameterNumber = new DefaultBlockParameterNumber(blockNumber);
        EthBlock ethBlock = web3j.ethGetBlockByNumber(defaultBlockParameterNumber, true).sendAsync().get();
        EthBlock.Block blockInfo = ethBlock.getBlock();
        Gson gson = new Gson();
        String info = gson.toJson(blockInfo);
        logger.info("Info of block #{}: {}", blockNumber, info);

        // Todo: Get the txInfos in the latest block by blockNumber
        List<EthBlock.TransactionResult> transactionResults = ethBlock.getBlock().getTransactions();
        List<Transaction> txInfos = new ArrayList<>();

        transactionResults.forEach(txInfo -> {
            Transaction transaction = (Transaction) txInfo;
            txInfos.add(transaction);
        });

        String transactionInfo = gson.toJson(txInfos);
        logger.info("Transactions in the block #{}: {}", blockNumber, transactionInfo);

        // Todo: Save the block and transaction info into the database
        int result1 = customBlockService.saveBlockInfo(blockInfo);

        if (result1 == 1)
            logger.info("Info of block #{} is saved.", blockNumber);
        else if (result1 == 0)
            logger.info("Record of block #{} already exists.", blockNumber);


        return "Success";
    }
}
