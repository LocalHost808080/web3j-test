package com.xkb.web3j.service.impl;

import com.xkb.web3j.entity.CustomTransaction;
import com.xkb.web3j.mapper.CustomTransactionMapper;
import com.xkb.web3j.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@Service
public class AsyncCustomTransactionServiceImpl implements AsyncCustomTransactionService {

    private static final Logger logger = LoggerFactory.getLogger(AsyncCustomTransactionServiceImpl.class);

    private static final String TRANSFER_HASH = "0xddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef";

    private static final String TRANSFER_SINGLE_HASH = "0xc3d58168c5ae7397731d063d5bbf3d657854427343f4c083240f7aacaa2d0f62";

    private static final String TRANSFER_BATCH_HASH = "0x4a39dc06d4c0dbc64b70af90fd698a233a518aa5d07e595d983b8c0526c8f7fb";

    @Autowired
    private CustomTransactionMapper customTransactionMapper;

    @Autowired
    private BlockChainDataService blockChainInfoService;

    @Autowired
    private CustomErc20TransferService customErc20TransferService;

    @Autowired
    private CustomErc721TransferService customErc721TransferService;

    @Autowired
    private CustomErc1155TransferService customErc1155TransferService;

    @Async("threadPoolTaskExecutor")
    @Override
    public void convertAndInsertTxInfo(Transaction txInfo) throws Exception {

        CustomTransaction customTransaction = new CustomTransaction();
        TransactionReceipt txRcpt = blockChainInfoService.getTransactionReceiptByHash(txInfo.getHash());
        this.identifyErcTypeAndSave(txRcpt);

        customTransaction.setHash(txInfo.getHash());

        customTransaction.setStatus(txRcpt.getStatus());
        // customTransaction.setStatus("");

        customTransaction.setBlockNumber(txInfo.getBlockNumber());
        customTransaction.setTimestamp(LocalDateTime.of(1970, Month.JANUARY, 1, 0, 0, 0));          // to be done
        customTransaction.setFromAccount(txInfo.getFrom());
        customTransaction.setToAccount(txInfo.getTo());
        customTransaction.setValue(new BigDecimal(txInfo.getValue())
                .divide(new BigDecimal(10).pow(18), 18, RoundingMode.DOWN));

        customTransaction.setTxFee(new BigDecimal(txInfo.getGasPrice().multiply(txRcpt.getGasUsed()))
                .divide(new BigDecimal(10).pow(18), 18, RoundingMode.DOWN));
        // customTransaction.setTxFee(new BigDecimal(0));

        customTransaction.setGasPrice(new BigDecimal(txInfo.getGasPrice())
                .divide(new BigDecimal(10).pow(18), 18, RoundingMode.DOWN));
        customTransaction.setEtherPrice(new BigDecimal(0));     // to be done
        customTransaction.setGasLimit(txInfo.getGas());

        customTransaction.setGasUsed(txRcpt.getGasUsed());
        // customTransaction.setGasUsed(new BigInteger(String.valueOf(0)));

        customTransaction.setGasFeesBase(new BigDecimal(0));    // to be done
        customTransaction.setGasFeesMax(new BigDecimal(0));     // to be done
        customTransaction.setGasFeesMaxPri(new BigDecimal(0));  // to be done
        customTransaction.setBurntFees(new BigDecimal(0));      // to be done
        customTransaction.setTxSavingsFees(new BigDecimal(0));  // to be done
        customTransaction.setTxType("");                            // to be done
        customTransaction.setNonce(txInfo.getNonce());
        customTransaction.setTxIndex(txInfo.getTransactionIndex());
        // customTransaction.setInputData(txInfo.getInput());
        customTransaction.setPrivateNote("");                       // to be done
        customTransaction.setBlockHash(txInfo.getBlockHash());
        customTransaction.setR(txInfo.getR());
        customTransaction.setS(txInfo.getS());

        customTransaction.setCumulativeGasUsed(txRcpt.getCumulativeGasUsed());
        // customTransaction.setCumulativeGasUsed(new BigInteger(String.valueOf(0)));

        customTransaction.setLogsBloom(txRcpt.getLogsBloom());
        // customTransaction.setLogsBloom("");

        customTransaction.setCreateTime(LocalDateTime.now());

        customTransactionMapper.insert(customTransaction);
    }

    @Override
    public void identifyErcTypeAndSave(TransactionReceipt txRcpt) {

        for (Log log : txRcpt.getLogs()) {
            List<String> topics = log.getTopics();
            if (topics.size() == 3 && TRANSFER_HASH.equals(topics.get(0)))
                customErc20TransferService.saveErc20TransferInfo(log);
            else if ("0x".equals(log.getData()) && topics.size() == 4 && TRANSFER_HASH.equals(topics.get(0)))
                customErc721TransferService.saveErc721TransferInfo(log);
            else if ((!"0x".equals(log.getData())) && topics.size() == 4
                    && (TRANSFER_SINGLE_HASH.equals(topics.get(0)) || TRANSFER_BATCH_HASH.equals(topics.get(0))))
                customErc1155TransferService.saveErc1155TransferInfo(log);
        }
    }
}
