CREATE TABLE `t_stock_base`
(
    `stock_code`  varchar(16) NOT NULL DEFAULT '' COMMENT '股票code',
    `stock_name`  varchar(16) NOT NULL DEFAULT '' COMMENT '股票名称',
    `market_type` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '市场类型',
    `create_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`stock_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 分 128张表
CREATE TABLE `t_stock_kline`
(
    `stock_code`     varchar(16)    NOT NULL DEFAULT '' COMMENT '股票code',
    `stock_name`     varchar(16)    NOT NULL DEFAULT '' COMMENT '股票名称',
    `market_type`    tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '市场类型',
    `deal_time`      datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '交易时间',
    `k_period`       tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT 'k线周期 1:1分; 5:5分; 15;30;60;120分; 101:日k 102:周k 103:月k 104:季k 105:半年k 106:年k 60:小时',
    `k_fq`           tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '复权 1: 前复权 2: 后复权 0: 不复权',
    `open_price`     decimal(10, 4) NOT NULL DEFAULT '0.0000' COMMENT '开盘价',
    `close_price`    decimal(10, 4) NOT NULL DEFAULT '0.0000' COMMENT '收盘价',
    `higest_price`   decimal(10, 4) NOT NULL DEFAULT '0.0000' COMMENT '最高价',
    `lowest_price`   decimal(10, 4) NOT NULL DEFAULT '0.0000' COMMENT '最低价',
    `trading_volume` decimal(20, 4) NOT NULL DEFAULT '0.0000' COMMENT '成交量',
    `trading_amount` decimal(20, 4) NOT NULL DEFAULT '0.0000' COMMENT '成交额',
    `amplitude`      varchar(32)    NOT NULL DEFAULT '0.0000' COMMENT '振幅%',
    `ud_volume`      varchar(32)    NOT NULL DEFAULT '0.0000' COMMENT '涨跌幅%',
    `ud_amount`      decimal(10, 4) NOT NULL DEFAULT '0.0000' COMMENT '涨跌额',
    `turnover_rate`  decimal(10, 4) NOT NULL DEFAULT '0.0000' COMMENT '换手率%',
    `create_time`    datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`stock_code`, `deal_time`) USING BTREE,
    KEY              `k_time_code_price` (`deal_time`,`stock_code`,`close_price`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_stock_deal_log`
(
    `id`           bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `stock_code`   varchar(16)    NOT NULL DEFAULT '' COMMENT '股票code',
    `stock_name`   varchar(16)    NOT NULL DEFAULT '' COMMENT '股票名称',
    `market_type`  tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '市场类型',
    `deal_time`    datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '交易时间',
    `deal_price`   decimal(10, 4) NOT NULL DEFAULT '0.0000' COMMENT '交易单价',
    `deal_volume`  int(11) NOT NULL DEFAULT '0' COMMENT '交易数量',
    `cur_volume`   int(11) NOT NULL DEFAULT '0' COMMENT '当前总数量',
    `left_money`   decimal(10, 4) NOT NULL DEFAULT '0.0000' COMMENT '剩余资金',
    `deal_version` varchar(32)    NOT NULL DEFAULT '0' COMMENT '版本号',
    PRIMARY KEY (`id`),
    KEY            `k_version_time_code` (`deal_version`,`deal_time`,`stock_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;