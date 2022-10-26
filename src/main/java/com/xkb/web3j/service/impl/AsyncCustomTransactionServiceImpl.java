package com.xkb.web3j.service.impl;

import com.xkb.web3j.entity.CustomTransaction;
import com.xkb.web3j.mapper.CustomTransactionMapper;
import com.xkb.web3j.service.AsyncCustomTransactionService;
import com.xkb.web3j.service.BlockChainDataService;
import com.xkb.web3j.service.CustomTransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.Month;

@Service
public class AsyncCustomTransactionServiceImpl implements AsyncCustomTransactionService {

    private static final Logger logger = LoggerFactory.getLogger(AsyncCustomTransactionServiceImpl.class);

    @Autowired
    private CustomTransactionMapper customTransactionMapper;

    @Autowired
    private BlockChainDataService blockChainInfoService;

    @Autowired
    private CustomTransferService customTransferService;

    @Async("threadPoolTaskExecutor")
    @Override
    public void convertAndInsertTxInfo(Transaction txInfo) throws Exception {

        CustomTransaction customTransaction = new CustomTransaction();
        TransactionReceipt txRcpt = blockChainInfoService.getTransactionReceiptByHash(txInfo.getHash());

        customTransferService.saveErc20TokenTransferInfo(txRcpt);

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

        // return customTransaction;
        customTransactionMapper.insert(customTransaction);
    }
}
