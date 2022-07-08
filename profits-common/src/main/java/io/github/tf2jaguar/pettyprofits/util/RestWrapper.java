package io.github.tf2jaguar.pettyprofits.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class RestWrapper {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 生成post请求的JSON请求参数
     *
     * @return v
     */
    public HttpEntity<Map<String, Object>> generatePostJson(Map<String, Object> jsonMap) {
        //如果需要其它的请求头信息、都可以在这里追加
        HttpHeaders httpHeaders = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json;charset=UTF-8");
        httpHeaders.setContentType(type);
        return new HttpEntity<>(jsonMap, httpHeaders);
    }

    public String doGet(String requestWithParameters) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestWithParameters, String.class);
        return (String) responseEntity.getBody();
    }

    /**
     * post请求、请求参数为json
     *
     * @return v
     */
    public String doPost(String url, Map<String, Object> dataMap) {
        ResponseEntity<String> apiResponse = restTemplate.postForEntity(url, generatePostJson(dataMap), String.class);
        return apiResponse.getBody();
    }

}

