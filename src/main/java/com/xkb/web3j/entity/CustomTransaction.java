package com.xkb.web3j.entity;

import java.math.BigDecimal;
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
 * 以太坊交易表
 * </p>
 *
 * @author XKB
 * @since 2022-10-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("custom_transaction")
@ApiModel(value="CustomTransaction对象", description="以太坊交易表")
public class CustomTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "交易哈希值")
    private String hash;

    @ApiModelProperty(value = "交易的最终状态")
    private String status;

    @ApiModelProperty(value = "区块编号")
    private BigInteger blockNumber;

    @ApiModelProperty(value = "时间戳")
    private LocalDateTime timestamp;

    @ApiModelProperty(value = "交易发起账号")
    private String fromAccount;

    @ApiModelProperty(value = "交易接受账号")
    private String toAccount;

    @ApiModelProperty(value = "交易金额 (Ether)")
    private BigDecimal value;

    @ApiModelProperty(value = "交易消耗的Gas (Ether)")
    private BigDecimal txFee;

    @ApiModelProperty(value = "Gas价格 (Ether)")
    private BigDecimal gasPrice;

    @ApiModelProperty(value = "CNY/ETH")
    private BigDecimal etherPrice;

    @ApiModelProperty(value = "Gas上限")
    private BigInteger gasLimit;

    @ApiModelProperty(value = "Gas用量")
    private BigInteger gasUsed;

    @ApiModelProperty(value = "出块时的网络基本费用 (Gwei)")
    private BigDecimal gasFeesBase;

    @ApiModelProperty(value = "用户愿意支付的最大费用 (Gwei)")
    private BigDecimal gasFeesMax;

    @ApiModelProperty(value = "用户愿意支付的最大优先费用 (Gwei)")
    private BigDecimal gasFeesMaxPri;

    @ApiModelProperty(value = "(Ether)")
    private BigDecimal burntFees;

    @ApiModelProperty(value = "(Ether)")
    private BigDecimal txSavingsFees;

    @ApiModelProperty(value = "交易类型")
    private String txType;

    @ApiModelProperty(value = "nonce值")
    private BigInteger nonce;

    @ApiModelProperty(value = "交易序号（在区块中）")
    private BigInteger txIndex;

    // @ApiModelProperty(value = "交易额外数据")
    // private String inputData;

    private String privateNote;

    @ApiModelProperty(value = "区块哈希")
    private String blockHash;

    private String r;

    private String s;

    @ApiModelProperty(value = "区块累计使用的Gas")
    private BigInteger cumulativeGasUsed;

    @ApiModelProperty(value = "bloom日志")
    private String logsBloom;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
}
