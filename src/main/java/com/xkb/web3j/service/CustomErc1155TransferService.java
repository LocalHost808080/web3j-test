package com.xkb.web3j.service;

import com.xkb.web3j.entity.CustomErc1155Transfer;
import com.baomidou.mybatisplus.extension.service.IService;
import org.web3j.protocol.core.methods.response.Log;

/**
 * <p>
 * ERC1155 转账记录表 服务类
 * </p>
 *
 * @author XKB
 * @since 2022-10-28
 */
public interface CustomErc1155TransferService extends IService<CustomErc1155Transfer> {

    void saveErc1155TransferInfo(Log log);
}
