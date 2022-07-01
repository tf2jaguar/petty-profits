package io.github.tf2jaguar.pettyprofits.util;

import io.github.tf2jaguar.pettyprofits.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : zhangguodong
 * @since : 2022/7/1 16:39
 */
public class RestDecoratorTest extends BaseTest {
    @Autowired
    private RestDecorator restDecorator;

    @Test
    public void doGet() {
        String s = restDecorator.doGet("www.baidu.com", null);
        System.out.println(s);
    }

    @Test
    public void doPost() {
        Map<String, Object> param = new HashMap<>();
        param.put("user_id", 37784357);
        Map<String, Object> request = new HashMap<>();
        request.put("params", param);
        String s = restDecorator.doPost("http://xx:8080/xx", request);
        System.out.println(s);
    }

    @Test
    public void doGet2() {
//            params = (
//        ('fields1', 'f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f11,f12,f13'),
//        ('fields2', fields2),
//        ('beg', beg),
//        ('end', end),
//        ('rtntype', '6'),
//        ('secid', quote_id),
//        ('klt', f'{klt}'),
//        ('fqt', f'{fqt}'),
//    )
        Map<String, Object> param = new HashMap<>();
        param.put("fields1", "f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f11,f12,f13");
        param.put("fields2", "f51,f52,f53,f54,f55,f56,f57,f58,f59,f60,f61");
        param.put("beg", "20220101");
        param.put("end", "20220701");
        param.put("rtntype", "6");
        param.put("secid", "0.000603");
        param.put("klt", "101");
        param.put("fqt", "1");

        String url = "push2his.eastmoney.com/api/qt/stock/kline/get";
        String s = restDecorator.doGet("https", url, param);
        System.out.println(s);
    }
}