package io.github.tf2jaguar.pettyprofits.bo;

import lombok.Data;

/**
 * "2022-01-10,12.05,12.56,12.74,11.92,130992,163007539.00,6.81,4.32,0.52,2.22",
 * 'f51': '日期',
 * 'f52': '开盘',
 * 'f53': '收盘',
 * 'f54': '最高',
 * 'f55': '最低',
 * 'f56': '成交量',
 * 'f57': '成交额',
 * 'f58': '振幅',
 * 'f59': '涨跌幅',
 * 'f60': '涨跌额',
 * 'f61': '换手率'
 *
 * @author : zhangguodong
 * @since : 2022/7/4 19:55
 */
@Data
public class KLine {
    // 0 日期
    private String dayStr;
    // 1 开盘
    private Double openPrice;
    // 2 收盘
    private Double closePrice;
    // 3 最高
    private Double highestPrice;
    // 4 最低
    private Double lowestPrice;
    // 5 成交量
    private Double tradingVolume;
    // 6 成交额
    private Double tradingAmount;
    // 7 振幅
    private Double amplitude;
    // 8 涨跌幅
    private Double udVolume;
    // 9 涨跌额
    private Double udAmount;
    // 10 换手率
    private Double turnoverRate;

    public static KLine buildByArr(String[] splits) {
        KLine kLine = new KLine();
        kLine.setDayStr(splits[0]);
        kLine.setOpenPrice(Double.parseDouble(splits[1]));
        kLine.setClosePrice(Double.parseDouble(splits[2]));
        kLine.setHighestPrice(Double.parseDouble(splits[3]));
        kLine.setLowestPrice(Double.parseDouble(splits[4]));
        kLine.setTradingVolume(Double.parseDouble(splits[5]));
        kLine.setTradingAmount(Double.parseDouble(splits[6]));
        kLine.setAmplitude(Double.parseDouble(splits[7]));
        kLine.setUdVolume(Double.parseDouble(splits[8]));
        kLine.setUdAmount(Double.parseDouble(splits[9]));
        kLine.setTurnoverRate(Double.parseDouble(splits[10]));
        return kLine;
    }
}
