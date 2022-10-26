package com.xkb.web3j.service.impl;

import com.xkb.web3j.entity.CustomTransfer;
import com.xkb.web3j.mapper.CustomTransferMapper;
import com.xkb.web3j.service.CustomTransferService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Numeric;

/**
 * <p>
 * ERC20 转账记录表 服务实现类
 * </p>
 *
 * @author XKB
 * @since 2022-10-25
 */
@Service
public class CustomTransferServiceImpl extends ServiceImpl<CustomTransferMapper, CustomTransfer> implements CustomTransferService {

    @Autowired
    private CustomTransferMapper customTransferMapper;

    @Override
    public int saveErc20TokenTransferInfo(TransactionReceipt txRcpt) throws Exception {

        int count = 0;

        for (Log log : txRcpt.getLogs()) {
            if (log.getTopics().size() >= 3) {
                CustomTransfer customTransfer = new CustomTransfer();
                customTransfer.setContractAddr(log.getAddress());
                customTransfer.setFromAccount("0x" + log.getTopics().get(1).substring(26));
                customTransfer.setToAccount("0x" + log.getTopics().get(2).substring(26));

                // String data = "0x" + log.getData().substring(2).replaceFirst("^0*", "");
                // customTransfer.setValue(Numeric.decodeQuantity(data));
                customTransfer.setValue(log.getData());

                customTransfer.setTxHash(log.getTransactionHash());
                customTransfer.setTxIndex(log.getTransactionIndex());
                customTransfer.setBlockHash(log.getBlockHash());
                customTransfer.setBlockNumber(log.getBlockNumber());

                customTransferMapper.insert(customTransfer);
                count++;
            }
        }

        return count;
    }
}
