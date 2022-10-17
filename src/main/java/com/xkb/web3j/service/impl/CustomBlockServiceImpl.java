package com.xkb.web3j.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xkb.web3j.entity.CustomBlock;
import com.xkb.web3j.mapper.CustomBlockMapper;
import com.xkb.web3j.service.CustomBlockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.EthBlock;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * <p>
 * 以太坊区块表 服务实现类
 * </p>
 *
 * @author XKB
 * @since 2022-10-12
 */
@Service
public class CustomBlockServiceImpl extends ServiceImpl<CustomBlockMapper, CustomBlock> implements CustomBlockService {

    private static final Logger logger = LoggerFactory.getLogger(CustomBlockServiceImpl.class);

    @Autowired
    private CustomBlockMapper customBlockMapper;

    @Override
    public int saveBlockInfo(EthBlock.Block blockInfo) {

        QueryWrapper<CustomBlock> wrapper = new QueryWrapper<>();
        wrapper.eq("number", blockInfo.getNumber());
        List<CustomBlock> customBlockList = customBlockMapper.selectList(wrapper);

        if (customBlockList.size() > 0) {
            logger.info("Record of block #{} already exists.", blockInfo.getNumber());
            return 0;
        } else {
            CustomBlock customBlock = convertBlockToCustom(blockInfo);
            customBlockMapper.insert(customBlock);
            logger.info("Info of block #{} is saved.", blockInfo.getNumber());
            return 1;
        }
    }

    @Override
    public CustomBlock convertBlockToCustom(EthBlock.Block block) {

        CustomBlock customBlock = new CustomBlock();

        customBlock.setNumber(block.getNumber());
        customBlock.setStatus("");                              // to be done

        long blockTimestamp = Long.parseLong(block.getTimestampRaw().substring(2), 16);
        Instant instant = Instant.ofEpochSecond(blockTimestamp);
        ZoneId zone = ZoneId.systemDefault();
        customBlock.setTimestamp(LocalDateTime.ofInstant(instant, zone));

        customBlock.setProposedOn("");                          // to be done
        customBlock.setTxCount(block.getTransactions().size());
        customBlock.setMiner(block.getMiner());
        customBlock.setBlockReward(new BigDecimal(0));      // to be done
        customBlock.setTotalDifficulty(block.getTotalDifficulty());
        customBlock.setSize(block.getSize());
        customBlock.setGasUsed(block.getGasUsed());
        customBlock.setGasLimit(block.getGasLimit());
        customBlock.setBaseFeePerGas(new BigDecimal(0));    // to be done
        customBlock.setBurntFee(new BigDecimal(0));         // to be done
        customBlock.setExtraData(block.getExtraData());
        customBlock.setHash(block.getHash());
        customBlock.setParentHash(block.getParentHash());
        customBlock.setStateRoot(block.getStateRoot());
        customBlock.setNonce(block.getNonce());
        customBlock.setSha3Uncles(block.getSha3Uncles());
        customBlock.setLogsBloom(block.getLogsBloom());
        customBlock.setTransactionsRoot(block.getTransactionsRoot());
        customBlock.setReceiptsRoot(block.getReceiptsRoot());
        customBlock.setMixHash(block.getMixHash());
        customBlock.setDifficulty(block.getDifficulty());
        customBlock.setCreateTime(LocalDateTime.now());

        return customBlock;
    }
}
