package com.xkb.web3j.service.impl;

import com.xkb.web3j.entity.CustomTransaction;
import com.xkb.web3j.mapper.CustomTransactionMapper;
import com.xkb.web3j.service.CustomTransactionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.Transaction;

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

    @Override
    public int saveTransactionInfo(List<Transaction> txInfos) {
        return 0;
    }
}
