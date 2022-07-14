package io.github.tf2jaguar.pettyprofits.service;

import io.github.tf2jaguar.pettyprofits.BaseTest;
import io.github.tf2jaguar.pettyprofits.dao.StockKlineDao;
import io.github.tf2jaguar.pettyprofits.entity.StockKlineEntity;
import io.github.tf2jaguar.pettyprofits.enums.MarketFsEnum;
import io.github.tf2jaguar.pettyprofits.util.DateUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author : zhangguodong
 * @since : 2022/7/1 21:19
 */
public class StockServiceTest extends BaseTest {

    @Autowired
    private StockService stockService;
    @Resource
    private StockKlineDao stockKlineDao;

    @Test
    @Ignore
    public void refreshStockBase() {
        stockService.refreshStockBase(MarketFsEnum.hu_shen_a);
    }

    @Test
    public void refreshStockKlineTable() {
        String sql = "CREATE TABLE `t_stock_kline`\n" +
                "(\n" +
                "    `stock_code`     varchar(16)    NOT NULL DEFAULT '' COMMENT '股票code',\n" +
                "    `stock_name`     varchar(16)    NOT NULL DEFAULT '' COMMENT '股票名称',\n" +
                "    `market_type`    tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '市场类型',\n" +
                "    `deal_time`      datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '交易时间',\n" +
                "    `k_period`       tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT 'k线周期 1:1分; 5:5分; 15;30;60;120分; 101:日k 102:周k 103:月k 104:季k 105:半年k 106:年k 60:小时',\n" +
                "    `k_fq`           tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '复权 1: 前复权 2: 后复权 0: 不复权',\n" +
                "    `open_price`     decimal(10, 4) NOT NULL DEFAULT '0.0000' COMMENT '开盘价',\n" +
                "    `close_price`    decimal(10, 4) NOT NULL DEFAULT '0.0000' COMMENT '收盘价',\n" +
                "    `higest_price`   decimal(10, 4) NOT NULL DEFAULT '0.0000' COMMENT '最高价',\n" +
                "    `lowest_price`   decimal(10, 4) NOT NULL DEFAULT '0.0000' COMMENT '最低价',\n" +
                "    `trading_volume` decimal(20, 4) NOT NULL DEFAULT '0.0000' COMMENT '成交量',\n" +
                "    `trading_amount` decimal(20, 4) NOT NULL DEFAULT '0.0000' COMMENT '成交额',\n" +
                "    `amplitude`      varchar(32)    NOT NULL DEFAULT '0.0000' COMMENT '振幅%',\n" +
                "    `ud_volume`      varchar(32)    NOT NULL DEFAULT '0.0000' COMMENT '涨跌幅%',\n" +
                "    `ud_amount`      decimal(10, 4) NOT NULL DEFAULT '0.0000' COMMENT '涨跌额',\n" +
                "    `turnover_rate`  decimal(10, 4) NOT NULL DEFAULT '0.0000' COMMENT '换手率%',\n" +
                "    `create_time`    datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
                "    `update_time`    datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',\n" +
                "    PRIMARY KEY (`stock_code`, `deal_time`) USING BTREE,\n" +
                "    KEY              `k_time_code_price` (`deal_time`,`stock_code`,`close_price`) USING BTREE\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
        for (int i = 1990; i < 2023; i++) {
            System.out.println(sql.replace("t_stock_kline", "t_stock_kline_" + i));
        }
    }

    @Test
    @Ignore
    public void writeStockKLines() {
        stockService.writeStockKLines();
//        StockBaseEntity stockBase = StockBaseEntity.builder().stockCode("000001").stockName("平安银行").marketType(0).build();
//        stockService.writeStockKLines(stockBase,"20000101","20220708");
    }

    @Test
    @Ignore
    public void writeStockKLinesAuto() {
        StockKlineEntity lastKline = stockKlineDao.selectLastDayKline();
        Date previousDayOf = DateUtils.previousDayOf(lastKline.getDealTime(), -1);
        String begDate = DateUtils.date2String(DateUtils.PATTERN_NO_HOUR_NO_MINUS, previousDayOf);
        String endDate = DateUtils.date2String(DateUtils.PATTERN_NO_HOUR_NO_MINUS, new Date());

        stockService.writeStockKLines(begDate, endDate);
    }

    @Test
    public void refreshStockRps() {
        Date endDate = DateUtils.string2date(DateUtils.PATTERN_NO_HOUR, "2022-07-14");
        stockService.refreshStockRps(new int[]{50}, endDate);
    }
}