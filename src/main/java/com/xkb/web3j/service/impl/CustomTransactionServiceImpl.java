package com.xkb.web3j.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xkb.web3j.entity.CustomTransaction;
import com.xkb.web3j.mapper.CustomTransactionMapper;
import com.xkb.web3j.service.AsyncCustomTransactionService;
import com.xkb.web3j.service.BlockChainDataService;
import com.xkb.web3j.service.CustomTransactionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xkb.web3j.service.CustomTransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

/**
 * <p>
 * 以太坊交易表 服务实现类
 * </p>
 *
 * @author XKB
 * @since 2022-10-12
 */
@Service
public class CustomTransactionServiceImpl extends ServiceImpl<CustomTransactionMapper, CustomTransaction> implements CustomTransactionService {

    private static final Logger logger = LoggerFactory.getLogger(CustomTransactionServiceImpl.class);

    @Autowired
    private CustomTransactionMapper customTransactionMapper;

    @Autowired
    private BlockChainDataService blockChainInfoService;

    @Autowired
    private CustomTransferService customTransferService;

    @Autowired
    private AsyncCustomTransactionService asyncCustomTransactionService;

    @Override
    public int saveTransactionInfo(List<Transaction> txInfos) throws Exception {

        int insertCnt = 0;

        for (Transaction txInfo : txInfos) {
            QueryWrapper<CustomTransaction> wrapper = new QueryWrapper<>();
            wrapper.eq("hash", txInfo.getHash());
            List<CustomTransaction> customTransactionList = customTransactionMapper.selectList(wrapper);

            if (customTransactionList.size() == 0) {
                // CustomTransaction customTransaction = this.convertTransactionToCustom(txInfo);
                // customTransactionMapper.insert(customTransaction);

                asyncCustomTransactionService.convertAndInsertTxInfo(txInfo);

                // logger.info("Info of transaction 【#{}】 is saved.", txInfo.getHash());
                insertCnt++;
                // } else {
                // logger.info("Record of transaction 【#{}】 already exists.", txInfo.getHash());
            }
        }

        return insertCnt;
    }

    @Override
    public CustomTransaction convertTransactionToCustom(Transaction txInfo) throws Exception {

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

        return customTransaction;
        // customTransactionMapper.insert(customTransaction);
    }
}
