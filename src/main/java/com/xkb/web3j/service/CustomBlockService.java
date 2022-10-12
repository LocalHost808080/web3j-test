package com.xkb.web3j.service;

import com.xkb.web3j.entity.CustomBlock;
import com.baomidou.mybatisplus.extension.service.IService;
import org.web3j.protocol.core.methods.response.EthBlock;

/**
 * <p>
 * 以太坊区块表 服务类
 * </p>
 *
 * @author XKB
 * @since 2022-10-12
 */
public interface CustomBlockService extends IService<CustomBlock> {

    public int saveBlockInfo(EthBlock.Block blockInfo);
}
