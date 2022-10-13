package com.xkb.web3j.service;

import com.xkb.web3j.entity.CustomTransaction;
import com.baomidou.mybatisplus.extension.service.IService;
import org.web3j.protocol.core.methods.response.Transaction;

import java.util.List;

/**
 * <p>
 * 以太坊交易表 服务类
 * </p>
 *
 * @author XKB
 * @since 2022-10-12
 */
public interface CustomTransactionService extends IService<CustomTransaction> {

    int saveTransactionInfo(List<Transaction> txInfos) throws Exception;

    CustomTransaction convertTransactionToCustom(Transaction txInfo) throws Exception;
}
