package com.xkb.web3j.service.impl;

import com.xkb.web3j.entity.Transaction;
import com.xkb.web3j.mapper.TransactionMapper;
import com.xkb.web3j.service.TransactionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 以太坊交易表 服务实现类
 * </p>
 *
 * @author XKB
 * @since 2022-10-11
 */
@Service
public class TransactionServiceImpl extends ServiceImpl<TransactionMapper, Transaction> implements TransactionService {

}
