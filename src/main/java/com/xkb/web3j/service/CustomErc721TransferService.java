package com.xkb.web3j.service;

import com.xkb.web3j.entity.CustomErc721Transfer;
import com.baomidou.mybatisplus.extension.service.IService;
import org.web3j.protocol.core.methods.response.Log;

/**
 * <p>
 * ERC721 转账记录表 服务类
 * </p>
 *
 * @author XKB
 * @since 2022-10-28
 */
public interface CustomErc721TransferService extends IService<CustomErc721Transfer> {

    void saveErc721TransferInfo(Log log);
}
