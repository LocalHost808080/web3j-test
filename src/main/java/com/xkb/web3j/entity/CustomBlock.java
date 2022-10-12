package com.xkb.web3j.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 以太坊区块表
 * </p>
 *
 * @author XKB
 * @since 2022-10-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("custom_block")
@ApiModel(value="CustomBlock对象", description="以太坊区块表")
public class CustomBlock implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "区块的序号")
    private Long number;

    @ApiModelProperty(value = "区块的最终状态")
    private String status;

    @ApiModelProperty(value = "时间戳")
    private Long timestamp;

    @ApiModelProperty(value = "该区块所基于的 slot 和 epoch")
    private String proposedOn;

    @ApiModelProperty(value = "出矿账号")
    private String miner;

    @ApiModelProperty(value = "Block Reward (Ether)")
    private BigDecimal blockReward;

    @ApiModelProperty(value = "到该区块为止的总难度")
    private Long totalDifficulty;

    @ApiModelProperty(value = "区块字节数")
    private Long size;

    @ApiModelProperty(value = "已用Gas")
    private Long gasUsed;

    @ApiModelProperty(value = "Gas上限")
    private Long gasLimit;

    @ApiModelProperty(value = "Base Fee Per Gas (Ether)")
    private BigDecimal baseFeePerGas;

    @ApiModelProperty(value = "Burnt Fees (Ether)")
    private BigDecimal burntFee;

    @ApiModelProperty(value = "额外数据")
    private String extraData;

    @ApiModelProperty(value = "区块哈希值")
    private String hash;

    @ApiModelProperty(value = "父区块哈希")
    private String parentHash;

    @ApiModelProperty(value = "状态树根哈希")
    private String stateRoot;

    @ApiModelProperty(value = "nonce值")
    private Long nonce;

    @ApiModelProperty(value = "叔伯块sha3哈希")
    private String sha3Uncles;

    @ApiModelProperty(value = "bloom日志")
    private String logsBloom;

    @ApiModelProperty(value = "交易根")
    private String transactionsRoot;

    @ApiModelProperty(value = "收据树根哈希")
    private String receiptsRoot;

    @ApiModelProperty(value = "混合哈希值")
    private String mixHash;

    @ApiModelProperty(value = "区块难度")
    private Long difficulty;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;


}
