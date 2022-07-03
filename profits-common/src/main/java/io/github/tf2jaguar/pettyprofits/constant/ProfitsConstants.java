package io.github.tf2jaguar.pettyprofits.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : zhangguodong
 * @since : 2022/7/1 20:03
 */
public class ProfitsConstants {

   public static final String EF_HISTORY_K_LINES_URL = "http://push2his.eastmoney.com/api/qt/stock/kline/get";
   public static final String EF_HISTORY_K_LINES_PARAMS_PARTNER = "secid=%s&klt=%d&fqt=%d&beg=%s&end=%s&fields1=f1,f2,f3,f4,f5,f6&fields2=f51,f52,f53,f54,f55,f56,f57,f58,f59,f60,f61";

   public static final String EF_MARKET_URL = "http://push2.eastmoney.com/api/qt/clist/get";
   public static final String EF_MARKET_PARAMS_PARTNER = "pn=1&pz=10000&po=1&np=1&fltt=2&invt=2&fid=f3&fs=%s&fields=%s";

}
