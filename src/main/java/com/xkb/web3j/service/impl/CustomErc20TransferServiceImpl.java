package com.xkb.web3j.service.impl;

import com.xkb.web3j.entity.CustomErc20Transfer;
import com.xkb.web3j.mapper.CustomErc20TransferMapper;
import com.xkb.web3j.service.CustomErc20TransferService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.Log;
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

    @Autowired
    private CustomErc20TransferMapper customErc20TransferMapper;

    @Override
    public void saveErc20TransferInfo(Log log) {

        CustomErc20Transfer customErc20Transfer = new CustomErc20Transfer();

        customErc20Transfer.setContractAddr(log.getAddress());
        customErc20Transfer.setFromAccount("0x" + log.getTopics().get(1).substring(26));
        customErc20Transfer.setToAccount("0x" + log.getTopics().get(2).substring(26));

        // String data = "0x" + log.getData().substring(2).replaceFirst("^0*", "");
        // customTransfer.setValue(Numeric.decodeQuantity(data));
        // customErc20Transfer.setValue(log.getData());
        customErc20Transfer.setValue("0x" + log.getData().substring(2).replaceFirst("^0*", ""));

        customErc20Transfer.setTxHash(log.getTransactionHash());
        customErc20Transfer.setTxIndex(log.getTransactionIndex());
        customErc20Transfer.setBlockHash(log.getBlockHash());
        customErc20Transfer.setBlockNumber(log.getBlockNumber());

        customErc20TransferMapper.insert(customErc20Transfer);
    }
}
