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
public enum MarketFsEnum {

    ke_zhuan_zhai("可转债", "b:MK0354"),
    zhong_gai_gu("中概股", "b:MK0201"),
    etf("ETF基金", "b:MK0021,b:MK0022,b:MK0023,b:MK0024"),
    lof("LOF基金", "b:MK0404,b:MK0405,b:MK0406,b:MK0407"),
    // 指数
    shang_zheng_zhi_shu("上证系列指数", "m:1+s:2"),
    shen_zheng_zhi_shu("深证系列指数", "m:0+t:5"),
    hu_shen_zhi_shu("沪深系列指数", "m:1+s:2,m:0+t:5"),
    // 板块
    di_yu_ban_kuai("地域板块", "m:90+t:1+f:!50"),
    hang_ye_ban_kuai("行业板块", "m:90+t:2+f:!50"),
    gai_nian_ban_kuai("概念板块", "m:90+t:3+f:!50"),
    //
    chuang_ye_ban("创业板", "m:0+t:80"),
    ke_chuang_ban("科创板", "m:1+t:23"),
    shen_a("深A", "m:0+t:6,m:0+t:80"),
    hu_a("沪A", "m:1+t:2,m:1+t:23"),
    jing_a("京A", "m:0+t:81+s:2048"),
    hu_shen_a("沪深A股", "m:0+t:6,m:0+t:80,m:1+t:2,m:1+t:23"),
    hu_shen_jing_a("沪深京A股", "m:0+t:6,m:0+t:80,m:1+t:2,m:1+t:23,m:0+t:81+s:2048"),

    mei_gu("美股", "m:105,m:106,m:107"),
    gang_gu("港股", "m:128+t:3,m:128+t:4,m:128+t:1,m:128+t:2"),
    ying_gu("英股", "m:155+t:1,m:155+t:2,m:155+t:3,m:156+t:1,m:156+t:2,m:156+t:5,m:156+t:6,m:156+t:7,m:156+t:8");

    public static final Map<String, MarketFsEnum> ALL = new HashMap<String, MarketFsEnum>();

    static {
        for (MarketFsEnum item : MarketFsEnum.values()) {
            ALL.put(item.getName(), item);
        }
    }

    private final String name;
    private final String code;

    MarketFsEnum(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public static MarketFsEnum instanceOf(String code) {
        return ALL.get(code);
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValidCode(String code) {
        if (Objects.isNull(code)) {
            return false;
        }
        return Objects.nonNull(MarketFsEnum.instanceOf(code));
    }

    /**
     * 根据code获取名称
     */
    public static String getNameByCode(String code) {
        return Objects.nonNull(instanceOf(code)) ? instanceOf(code).getCode() : "";
    }

    public String getName() {
        return this.name;
    }

    public String getCode() {
        return this.code;
    }

    public boolean eq(String code) {
        if (Objects.isNull(code)) {
            return false;
        }
        return this.name.equals(code);
    }

}
