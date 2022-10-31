package com.xkb.web3j.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xkb.web3j.entity.CustomTransaction;
import com.xkb.web3j.mapper.CustomTransactionMapper;
import com.xkb.web3j.service.AsyncCustomTransactionService;
import com.xkb.web3j.service.CustomTransactionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
    private AsyncCustomTransactionService asyncCustomTransactionService;

    @Override
    public int saveTransactionInfo(List<Transaction> txInfos) throws Exception {

        int insertCnt = 0;

        for (Transaction txInfo : txInfos) {
            QueryWrapper<CustomTransaction> wrapper = new QueryWrapper<>();
            wrapper.eq("hash", txInfo.getHash());
            List<CustomTransaction> customTransactionList = customTransactionMapper.selectList(wrapper);

            if (customTransactionList.size() == 0) {
                asyncCustomTransactionService.convertAndInsertTxInfo(txInfo);
                // logger.info("Info of transaction 【#{}】 is saved.", txInfo.getHash());
                insertCnt++;
                // } else {
                // logger.info("Record of transaction 【#{}】 already exists.", txInfo.getHash());
            }
        }

        return insertCnt;
    }
}
