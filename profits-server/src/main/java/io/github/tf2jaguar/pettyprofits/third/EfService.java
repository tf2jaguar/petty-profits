package io.github.tf2jaguar.pettyprofits.third;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import io.github.tf2jaguar.pettyprofits.bo.CListResponse;
import io.github.tf2jaguar.pettyprofits.bo.EfResult;
import io.github.tf2jaguar.pettyprofits.constant.ProfitsConstants;
import io.github.tf2jaguar.pettyprofits.util.RestDecorator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


/**
 * @author : zhangguodong
 * @since : 2022/7/1 19:57
 */
@Slf4j
@Service
public class EfService {

    @Autowired
    private RestDecorator restDecorator;

    /**
     * 股票k线数据
     * 经过分析东方财富网站，发现获取股票数据的接口如下：
     * http://push2his.eastmoney.com/api/qt/stock/kline/get?secid=1.600028&klt=101&fqt=0&beg=0&end=20500101&fields1=f1,f2,f3,f4,f5,f6&fields2=f51,f52,f53,f54,f55,f56,f57,f58,f59,f60,f61
     * <p>
     * fields1说明
     * f1=code(代码),f2=market,f3=name(名称),f4=decimal(精度),f5=dktotal,f6=preKPrice,f7=prePrice,f8=qtMiscType,f9,f10,f11,f12,f13
     * fields2说明
     * f51=交易日期,f52=开盘价,f53=收盘价,f54=最高价,f55=最低价,f56=成交量,f57=成交额,f58=振幅(%),f59=涨跌幅(%),f60=涨跌额,f61=换手率(%)
     * <p>
     *
     * @param secid 股票代码 上证股票添加前缀1.，深证股票添加前缀0.。例如 1.600028，0.000333 secid: 1 = sh, 0 = sz
     * @param beg   开始日期。格式示例20210101  beg=0&end=20500000，获取股票全部历史数据。
     * @param end   结束日期。格式示例20210118  beg=20210118&end20210118，表示获取2021年1月1日的数据。
     * @param klt   k线周期 101: 日k 102: 周k 103: 月k 104: 季k 105: 半年k 106:年k 60: 小时, 1, 5, 15,30,60,120,
     * @param fqt   复权 1: 前复权 2: 后复权 0: 不复权
     * @return code k line
     */
    public String history(String secid, String beg, String end, int klt, int fqt) {
        String params = String.format(ProfitsConstants.EF_HISTORY_K_LINES_PARAMS_PARTNER, secid, klt, fqt, beg, end);
        String url = String.join("?", ProfitsConstants.EF_HISTORY_K_LINES_URL, params);
        return restDecorator.doGet(url);
    }

    /**
     * f12,f14,f3,f2,f15,f16,f17,f4,f8,f10,f9,f5,f6,f18,f20,f21,f13,f124,f297
     * f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f12,f13,f14,f15,f16,f17,f18,f20,f21,f23,f24,f25,f22,f11,f62,f128,f136,f115,f152
     *
     * @param fs     fs
     * @param fields f2:最新价, f3:涨跌幅, f4:涨跌额, f5:成交量, f6:成交额, f7:振幅, f8:换手率, f9:动态市盈率, f10:量比,
     *               f12:代码, f13:市场编号, f14:名称, f15:最高, f16:最低, f17:今开, f18:昨日收盘,
     *               f20:总市值, f21:流通市值, f23:市净率, f26:无价格涨跌幅限制日期
     *               f124:更新时间戳, f297:最新交易日
     * @return str
     */
    public EfResult<CListResponse> market(String fs, String fields) {
        String params = String.format(ProfitsConstants.EF_MARKET_PARAMS_PARTNER, fs, fields);
        String url = String.join("?", ProfitsConstants.EF_MARKET_URL, params);
        String getJsonStr = restDecorator.doGet(url);
        if (StringUtils.isEmpty(getJsonStr)) {
            log.warn("获取市场行情异常: {}", url);
            return null;
        }
        return JSONObject.parseObject(getJsonStr, new TypeReference<EfResult<CListResponse>>() {
        });
    }


}
