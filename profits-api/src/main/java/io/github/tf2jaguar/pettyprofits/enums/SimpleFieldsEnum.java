package io.github.tf2jaguar.pettyprofits.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 股票、ETF、债券 K 线表头
 *
 * @author : zhangguodong
 * @since : 2022/7/1 20:14
 */
public enum SimpleFieldsEnum {

    F_DATE("f51", "日期"),
    F_OPEN("f52", "开盘"),
    F_CLOSE("f53", "收盘"),
    F_HIGHEST("f54", "最高"),
    F_LOWEST("f55", "最低"),
    F_TRADING_VOLUME("f56", "成交量"),
    F_TRANSACTION_VOLUME("f57", "成交额"),
    F_AMPLITUDE("f58", "振幅"),
    F_RISE_RANGE("f59", "涨跌幅"),
    F_RISE_AMOUNT("f60", "涨跌额"),
    F_TURNOVER_RATE("f61", "换手");

    public static final Map<String, SimpleFieldsEnum> ALL = new HashMap<String, SimpleFieldsEnum>();

    static {
        for (SimpleFieldsEnum item : SimpleFieldsEnum.values()) {
            ALL.put(item.getCode(), item);
        }
    }

    private final String code;
    private final String name;

    SimpleFieldsEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static SimpleFieldsEnum instanceOf(String code) {
        return ALL.get(code);
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValidCode(String code) {
        if (Objects.isNull(code)) {
            return false;
        }
        return Objects.nonNull(SimpleFieldsEnum.instanceOf(code));
    }

    /**
     * 根据code获取名称
     */
    public static String getNameByCode(String code) {
        return Objects.nonNull(instanceOf(code)) ? instanceOf(code).getName() : "";
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public boolean eq(String code) {
        if (Objects.isNull(code)) {
            return false;
        }
        return this.code.equals(code);
    }

}
