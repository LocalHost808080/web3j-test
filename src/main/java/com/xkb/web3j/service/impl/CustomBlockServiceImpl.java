package com.xkb.web3j.service.impl;

import com.xkb.web3j.entity.CustomBlock;
import com.xkb.web3j.mapper.CustomBlockMapper;
import com.xkb.web3j.service.CustomBlockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.EthBlock;

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

    @Override
    public int saveBlockInfo(EthBlock.Block blockInfo) {
        return 0;
    }
}
