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
public class RestWrapperTest extends BaseTest {
    @Autowired
    private RestWrapper restWrapper;

    @Test
    public void doGet() {
        String s = restWrapper.doGet("www.baidu.com");
        System.out.println(s);
    }

    @Test
    public void doPost() {
        Map<String, Object> param = new HashMap<>();
        param.put("user_id", 37784357);
        Map<String, Object> request = new HashMap<>();
        request.put("params", param);
        String s = restWrapper.doPost("http://xx:8080/xx", request);
        System.out.println(s);
    }

}