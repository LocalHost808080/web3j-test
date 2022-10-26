package com.xkb.web3j.mapper;

import com.xkb.web3j.entity.CustomTransfer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * ERC20 转账记录表 Mapper 接口
 * </p>
 *
 * @author XKB
 * @since 2022-10-25
 */
@Repository
public interface CustomTransferMapper extends BaseMapper<CustomTransfer> {

}
