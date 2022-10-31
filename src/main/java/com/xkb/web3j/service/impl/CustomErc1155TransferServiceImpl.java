package com.xkb.web3j.service.impl;

import com.xkb.web3j.entity.CustomErc1155Transfer;
import com.xkb.web3j.mapper.CustomErc1155TransferMapper;
import com.xkb.web3j.service.CustomErc1155TransferService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

/**
 * <p>
 * ERC1155 转账记录表 服务实现类
 * </p>
 *
 * @author XKB
 * @since 2022-10-28
 */
@Service
public class CustomErc1155TransferServiceImpl extends ServiceImpl<CustomErc1155TransferMapper, CustomErc1155Transfer> implements CustomErc1155TransferService {

    @Autowired
    private CustomErc1155TransferMapper customErc1155TransferMapper;

    private static final int DATA_HEX_LEN = 64;
    @Override
    public void saveErc1155TransferInfo(Log log) {

        CustomErc1155Transfer customErc1155Transfer = new CustomErc1155Transfer();

        customErc1155Transfer.setContractAddr(log.getAddress());
        customErc1155Transfer.setFromAccount("0x" + log.getTopics().get(2).substring(26));
        customErc1155Transfer.setToAccount("0x" + log.getTopics().get(3).substring(26));

        customErc1155Transfer.setOperator("0x" + log.getTopics().get(1).substring(26));
        String data = log.getData();
        int midIndex = data.length() / 2 + 1;
        customErc1155Transfer.setTokenIds(hexStr2decArr(data.substring(0, midIndex)));
        customErc1155Transfer.setTokenValues(hexStr2decArr(data.substring(midIndex)));

        customErc1155Transfer.setTxHash(log.getTransactionHash());
        customErc1155Transfer.setTxIndex(log.getTransactionIndex());
        customErc1155Transfer.setBlockHash(log.getBlockHash());
        customErc1155Transfer.setBlockNumber(log.getBlockNumber());

        customErc1155TransferMapper.insert(customErc1155Transfer);
    }

    private String hexStr2decArr(String hexStr) {

        StringBuilder decArr = new StringBuilder("[");
        int cnt = hexStr.length() / DATA_HEX_LEN;

        for (int i = 0; i < cnt; i++) {
            String hex = "0x" + hexStr.substring(DATA_HEX_LEN * i, DATA_HEX_LEN * (i + 1));
            decArr.append(Numeric.decodeQuantity(hex));
            if (i < cnt - 1)
                decArr.append(",");
        }

        decArr.append("]");
        return decArr.toString();
    }
}
