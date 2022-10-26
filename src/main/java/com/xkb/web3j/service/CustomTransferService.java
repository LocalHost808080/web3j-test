package com.xkb.web3j.service;

import com.xkb.web3j.entity.CustomTransaction;
import com.xkb.web3j.entity.CustomTransfer;
import com.baomidou.mybatisplus.extension.service.IService;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

/**
 * <p>
 * ERC20 转账记录表 服务类
 * </p>
 *
 * @author XKB
 * @since 2022-10-25
 */
public interface CustomTransferService extends IService<CustomTransfer> {

    int saveErc20TokenTransferInfo(TransactionReceipt txRcpt) throws Exception;
}
