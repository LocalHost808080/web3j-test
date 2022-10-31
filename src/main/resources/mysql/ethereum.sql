-- Todo: To create the database and tables of Ethereum blocks and transactions
-- Date: 2022.10.10
-- Author: XKB

CREATE DATABASE IF NOT EXISTS ethereum;
USE ethereum;


# Todo: custom_block ------------------------------------------------------------------------------

DROP TABLE IF EXISTS custom_block;

# 在 Java 实体类中进行的修改：把 Long 改为 BigInteger
CREATE TABLE `custom_block`
(
    `id`                INT(11)    UNSIGNED NOT NULL AUTO_INCREMENT,
    `number`            BIGINT(10) UNSIGNED NOT NULL COMMENT '区块的序号',
    `status`            VARCHAR(255)     NOT NULL COMMENT '区块的最终状态',
    `timestamp`         DATETIME         NOT NULL COMMENT '时间戳',
    `proposed_on`       VARCHAR(255)     NOT NULL COMMENT '该区块所基于的 slot 和 epoch',
    `tx_count`          INT(11)          NOT NULL COMMENT '区块字中的交易数量',
    `miner`             VARCHAR(255)     NOT NULL COMMENT '出矿账号',
    `block_reward`      DECIMAL(28, 18)  NOT NULL COMMENT 'Block Reward (Ether)',
    `total_difficulty`  DECIMAL(30, 0)   NOT NULL COMMENT '到该区块为止的总难度', -- BIGINT UNSIGNED 存不下
    `size`              BIGINT UNSIGNED  NOT NULL COMMENT '区块字节数',
    `gas_used`          BIGINT UNSIGNED  NOT NULL COMMENT '已用Gas',
    `gas_limit`         BIGINT UNSIGNED  NOT NULL COMMENT 'Gas上限',
    `base_fee_per_gas`  DECIMAL(28, 18)  NOT NULL COMMENT 'Base Fee Per Gas (Ether)',
    `burnt_fee`         DECIMAL(28, 18)  NOT NULL COMMENT 'Burnt Fees (Ether)',
    `extra_data`        VARCHAR(255)     NOT NULL COMMENT '额外数据',
    `hash`              VARCHAR(255)     NOT NULL COMMENT '区块哈希值',
    `parent_hash`       VARCHAR(255)     NOT NULL COMMENT '父区块哈希',
    `state_root`        VARCHAR(255)     NOT NULL COMMENT '状态树根哈希',
    `nonce`             BIGINT UNSIGNED  NOT NULL COMMENT 'nonce值',
    `sha3_uncles`       VARCHAR(255)     NOT NULL COMMENT '叔伯块sha3哈希',
    `logs_bloom`        TEXT             NOT NULL COMMENT 'bloom日志',
    `transactions_root` VARCHAR(255)     NOT NULL COMMENT '交易根',
    `receipts_root`     VARCHAR(255)     NOT NULL COMMENT '收据树根哈希',
    `mix_hash`          VARCHAR(255)     NOT NULL COMMENT '混合哈希值',
    `difficulty`        BIGINT UNSIGNED  NOT NULL COMMENT '区块难度',
    `create_time`       DATETIME         NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8 COMMENT = '以太坊区块表';


# Todo: custom_transaction ------------------------------------------------------------------------

DROP TABLE IF EXISTS custom_transaction;

# 在 Java 实体类中进行的修改：把 Long 改为 BigInteger
CREATE TABLE custom_transaction
(
    `id`                  INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
    `hash`                VARCHAR(255)     NOT NULL COMMENT '交易哈希值',
    `status`              VARCHAR(255)     NOT NULL COMMENT '交易的最终状态',
    `block_number`        BIGINT UNSIGNED  NOT NULL COMMENT '区块编号',
    `timestamp`           DATETIME         NOT NULL COMMENT '时间戳',
    `from_account`        VARCHAR(255)     NOT NULL COMMENT '交易发起账号',
    `to_account`          VARCHAR(255)     NOT NULL DEFAULT '' COMMENT '交易接受账号',
    `value`               DECIMAL(28, 18)  NOT NULL COMMENT '交易金额 (Ether)',
    `tx_fee`              DECIMAL(28, 18)  NOT NULL COMMENT '交易消耗的Gas (Ether)',
    `gas_price`           DECIMAL(28, 18)  NOT NULL COMMENT 'Gas价格 (Ether)',
    `ether_price`         DECIMAL(28, 18)  NOT NULL COMMENT 'CNY/ETH',
    `gas_limit`           BIGINT UNSIGNED  NOT NULL COMMENT 'Gas上限',
    `gas_used`            BIGINT UNSIGNED  NOT NULL COMMENT 'Gas用量',
    `gas_fees_base`       DECIMAL(20, 9)   NOT NULL COMMENT '出块时的网络基本费用 (Gwei)',
    `gas_fees_max`        DECIMAL(20, 9)   NOT NULL COMMENT '用户愿意支付的最大费用 (Gwei)',
    `gas_fees_max_pri`    DECIMAL(20, 9)   NOT NULL COMMENT '用户愿意支付的最大优先费用 (Gwei)',
    `burnt_fees`          DECIMAL(28, 18)  NOT NULL COMMENT '(Ether)',
    `tx_savings_fees`     DECIMAL(28, 18)  NOT NULL COMMENT '(Ether)',
    `tx_type`             VARCHAR(255)     NOT NULL COMMENT '交易类型',
    `nonce`               BIGINT UNSIGNED  NOT NULL COMMENT 'nonce值',
    `tx_index`            BIGINT UNSIGNED  NOT NULL COMMENT '交易序号（在区块中）',
#     `input_data`          TEXT             NOT NULL COMMENT '交易额外数据',
    `private_note`        TEXT             NOT NULL COMMENT '',
    `block_hash`          VARCHAR(255)     NOT NULL COMMENT '区块哈希',
    `r`                   VARCHAR(255)     NOT NULL COMMENT '',
    `s`                   VARCHAR(255)     NOT NULL COMMENT '',
    `cumulative_gas_used` BIGINT UNSIGNED  NOT NULL COMMENT '区块累计使用的Gas',
#     `logs`                VARCHAR(255)     NOT NULL COMMENT '交易产生的日志',
    `logs_bloom`          TEXT             NOT NULL COMMENT 'bloom日志',
    `create_time`         DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8 COMMENT = '以太坊交易表';


# Todo: custom_transfer ------------------------------------------------------------------------------

DROP TABLE IF EXISTS custom_erc20_transfer;

# 在 Java 实体类中进行的修改：把 Long 改为 BigInteger
CREATE TABLE `custom_erc20_transfer`
(
    `id`             INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
    `contract_addr`  VARCHAR(255)     NOT NULL COMMENT '合约地址',
    `from_account`   VARCHAR(255)     NOT NULL COMMENT '转账发送人',
    `to_account`     VARCHAR(255)     NOT NULL COMMENT '转账接收人',
    `value`          TEXT             NOT NULL COMMENT '转账代币数',
    `tx_hash`        VARCHAR(255)     NOT NULL COMMENT '交易哈希值',
    `tx_index`       BIGINT UNSIGNED  NOT NULL COMMENT '交易序号（在区块中）',
    `block_hash`     VARCHAR(255)     NOT NULL COMMENT '区块哈希值',
    `block_number`   BIGINT UNSIGNED  NOT NULL COMMENT '区块编号',
    `create_time`    DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8 COMMENT = 'ERC20 转账记录表';


DROP TABLE IF EXISTS custom_erc721_transfer;

# 在 Java 实体类中进行的修改：把 Long 改为 BigInteger
CREATE TABLE `custom_erc721_transfer`
(
    `id`             INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
    `contract_addr`  VARCHAR(255)     NOT NULL COMMENT '合约地址',
    `from_account`   VARCHAR(255)     NOT NULL COMMENT '转账发送人',
    `to_account`     VARCHAR(255)     NOT NULL COMMENT '转账接收人',
    `token_id`       VARCHAR(255)   NOT NULL COMMENT 'tokenID',
    `tx_hash`        VARCHAR(255)     NOT NULL COMMENT '交易哈希值',
    `tx_index`       BIGINT UNSIGNED  NOT NULL COMMENT '交易序号（在区块中）',
    `block_hash`     VARCHAR(255)     NOT NULL COMMENT '区块哈希值',
    `block_number`   BIGINT UNSIGNED  NOT NULL COMMENT '区块编号',
    `create_time`    DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8 COMMENT = 'ERC721 转账记录表';


DROP TABLE IF EXISTS custom_erc1155_transfer;

# 在 Java 实体类中进行的修改：把 Long 改为 BigInteger
CREATE TABLE `custom_erc1155_transfer`
(
    `id`             INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
    `contract_addr`  VARCHAR(255)     NOT NULL COMMENT '合约地址',
    `from_account`   VARCHAR(255)     NOT NULL COMMENT '转账发送人',
    `to_account`     VARCHAR(255)     NOT NULL COMMENT '转账接收人',
    `operator`       VARCHAR(255)     NOT NULL COMMENT '操作',
    `token_ids`      VARCHAR(255)     NOT NULL COMMENT 'Token id 数组',
    `token_values`   VARCHAR(255)     NOT NULL COMMENT 'Token 数量数组',
    `tx_hash`        VARCHAR(255)     NOT NULL COMMENT '交易哈希值',
    `tx_index`       BIGINT UNSIGNED  NOT NULL COMMENT '交易序号（在区块中）',
    `block_hash`     VARCHAR(255)     NOT NULL COMMENT '区块哈希值',
    `block_number`   BIGINT UNSIGNED  NOT NULL COMMENT '区块编号',
    `create_time`    DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8 COMMENT = 'ERC1155 转账记录表';



# Todo: Test --------------------------------------------------------------------------------------

# SELECT  id,block_hash,burnt_fees,tx_savings_fees,gas_limit,gas_used,ether_price,private_note,from,value,gas_fees_base,timestamp,gas_price,logs_bloom,input_data,gas_fees_max_pri,gas_fees_max,tx_type,nonce,r,s,create_time,block_number,tx_fee,cumulative_gas_used,to,hash,tx_index,status  FROM custom_transaction     WHERE (hash = ?)

SELECT COUNT(*) as `Block Count` FROM custom_block;                         -- 68
SELECT COUNT(*) as `Transaction Count` FROM custom_transaction;             -- 6073
SELECT COUNT(*) as `ERC20 Transfer Count` FROM custom_erc20_transfer;       -- 2385
SELECT COUNT(*) as `ERC721 Transfer Count` FROM custom_erc721_transfer;     -- 383
SELECT COUNT(*) as `ERC1155 Transfer Count` FROM custom_erc1155_transfer;   -- 72
