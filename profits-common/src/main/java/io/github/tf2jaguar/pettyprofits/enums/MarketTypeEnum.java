package io.github.tf2jaguar.pettyprofits.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author : zhangguodong
 * @since : 2022/7/3 12:25
 */
public enum MarketTypeEnum {
    A_0(0, "深A"),
    A_1(1, "沪A"),
    M_105(105, "美股"),
    M_106(106, "美股"),
    M_107(107, "美股"),
    H_116(116, "港股"),
    H_128(128, "港股"),
    K_155(155, "英股"),

    F_8(8, "中金所"),
    F_113(113, "上期所"),
    F_114(114, "大商所"),
    F_115(115, "郑商所"),
    F_142(142, "上海能源期货交易所"),

    BLOCK(90, "板块");

    public static final Map<Integer, MarketTypeEnum> ALL = new HashMap<Integer, MarketTypeEnum>();

    static {
        for (MarketTypeEnum item : MarketTypeEnum.values()) {
            ALL.put(item.getCode(), item);
        }
    }

    private final Integer code;
    private final String name;

    MarketTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static MarketTypeEnum instanceOf(Integer code) {
        return ALL.get(code);
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValidCode(Integer code) {
        if (Objects.isNull(code)) {
            return false;
        }
        return Objects.nonNull(MarketTypeEnum.instanceOf(code));
    }

    /**
     * 根据code获取名称
     */
    public static String getNameByCode(Integer code) {
        return Objects.nonNull(instanceOf(code)) ? instanceOf(code).getName() : "";
    }

    public Integer getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public boolean eq(Integer code) {
        if (Objects.isNull(code)) {
            return false;
        }
        return this.code.intValue() == code.intValue();
    }

}
