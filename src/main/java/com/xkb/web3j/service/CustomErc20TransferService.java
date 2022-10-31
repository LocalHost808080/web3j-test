package com.xkb.web3j.service;

import com.xkb.web3j.entity.CustomErc20Transfer;
import com.baomidou.mybatisplus.extension.service.IService;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

/**
 * <p>
 * ERC20 转账记录表 服务类
 * </p>
 *
 * @author XKB
 * @since 2022-10-28
 */
public interface CustomErc20TransferService extends IService<CustomErc20Transfer> {

    void saveErc20TransferInfo(Log log);
}
