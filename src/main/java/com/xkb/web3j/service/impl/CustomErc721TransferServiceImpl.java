package com.xkb.web3j.service.impl;

import com.xkb.web3j.entity.CustomErc721Transfer;
import com.xkb.web3j.mapper.CustomErc20TransferMapper;
import com.xkb.web3j.mapper.CustomErc721TransferMapper;
import com.xkb.web3j.service.CustomErc721TransferService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.utils.Numeric;

/**
 * <p>
 * ERC721 转账记录表 服务实现类
 * </p>
 *
 * @author XKB
 * @since 2022-10-28
 */
@Service
public class CustomErc721TransferServiceImpl extends ServiceImpl<CustomErc721TransferMapper, CustomErc721Transfer> implements CustomErc721TransferService {

    @Autowired
    private CustomErc721TransferMapper customErc721TransferMapper;

    @Override
    public void saveErc721TransferInfo(Log log) {

        CustomErc721Transfer customErc721Transfer = new CustomErc721Transfer();

        customErc721Transfer.setContractAddr(log.getAddress());
        customErc721Transfer.setFromAccount("0x" + log.getTopics().get(1).substring(26));
        customErc721Transfer.setToAccount("0x" + log.getTopics().get(2).substring(26));

        // String tokenIdStr = "0x" + log.getTopics().get(3).substring(2).replaceFirst("^0*", "");
        // customErc721Transfer.setTokenId(Numeric.decodeQuantity(tokenIdStr));
        customErc721Transfer.setTokenId("0x" + log.getTopics().get(3).substring(2).replaceFirst("^0*", ""));

        customErc721Transfer.setTxHash(log.getTransactionHash());
        customErc721Transfer.setTxIndex(log.getTransactionIndex());
        customErc721Transfer.setBlockHash(log.getBlockHash());
        customErc721Transfer.setBlockNumber(log.getBlockNumber());

        customErc721TransferMapper.insert(customErc721Transfer);
    }
}
