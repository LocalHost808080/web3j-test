package com.xkb.web3j.service.impl;

import com.xkb.web3j.entity.CustomErc20Transfer;
import com.xkb.web3j.mapper.CustomErc20TransferMapper;
import com.xkb.web3j.service.CustomErc20TransferService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

/**
 * <p>
 * ERC20 转账记录表 服务实现类
 * </p>
 *
 * @author XKB
 * @since 2022-10-28
 */
@Service
public class CustomErc20TransferServiceImpl extends ServiceImpl<CustomErc20TransferMapper, CustomErc20Transfer> implements CustomErc20TransferService {

    @Override
    public int saveErc20TransferInfo(TransactionReceipt txRcpt) throws Exception {
        return 0;
    }
}
