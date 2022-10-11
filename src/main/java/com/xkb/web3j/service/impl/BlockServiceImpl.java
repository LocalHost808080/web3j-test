package com.xkb.web3j.service.impl;

import com.xkb.web3j.entity.Block;
import com.xkb.web3j.mapper.BlockMapper;
import com.xkb.web3j.service.BlockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 以太坊区块表 服务实现类
 * </p>
 *
 * @author XKB
 * @since 2022-10-11
 */
@Service
public class BlockServiceImpl extends ServiceImpl<BlockMapper, Block> implements BlockService {

}
