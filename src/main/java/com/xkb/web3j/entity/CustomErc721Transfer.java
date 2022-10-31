package com.xkb.web3j.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * ERC721 转账记录表
 * </p>
 *
 * @author XKB
 * @since 2022-10-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("custom_erc721_transfer")
@ApiModel(value="CustomErc721Transfer对象", description="ERC721 转账记录表")
public class CustomErc721Transfer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "合约地址")
    private String contractAddr;

    @ApiModelProperty(value = "转账发送人")
    private String fromAccount;

    @ApiModelProperty(value = "转账接收人")
    private String toAccount;

    @ApiModelProperty(value = "tokenID")
    private String tokenId;

    @ApiModelProperty(value = "交易哈希值")
    private String txHash;

    @ApiModelProperty(value = "交易序号（在区块中）")
    private BigInteger txIndex;

    @ApiModelProperty(value = "区块哈希值")
    private String blockHash;

    @ApiModelProperty(value = "区块编号")
    private BigInteger blockNumber;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;


}
