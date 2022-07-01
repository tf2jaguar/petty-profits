package io.github.tf2jaguar.pettyprofits.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class RestDecorator {

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


    /**
     * 生成get参数请求url
     * 示例：https://0.0.0.0:80/api?u=u&o=o
     * 示例：https://0.0.0.0:80/api
     *
     * @param protocol 请求协议 示例: http 或者 https
     * @param uri      请求的uri 示例: 0.0.0.0:80
     * @param params   请求参数
     * @return v
     */
    public String generateRequestParameters(String protocol, String uri, Map<String, Object> params) {
        StringBuilder sb = new StringBuilder(protocol).append("://").append(uri);
        if (!CollectionUtils.isEmpty(params)) {
            sb.append("?");
            for (Map.Entry<String, Object> map : params.entrySet()) {
                sb.append(map.getKey())
                        .append("=")
                        .append(map.getValue())
                        .append("&");
            }
            uri = sb.substring(0, sb.length() - 1);
            return uri;
        }
        return sb.toString();
    }

    /**
     * get请求、请求参数为?拼接形式的
     * <p>
     * 最终请求的URI如下：
     * <p>
     * http://127.0.0.1:80/?name=张耀烽&sex=男
     *
     * @return v
     */
    public String doGet(String uri, Map<String, Object> dataMap) {
        String requestWithParameters = generateRequestParameters("http", uri, dataMap);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestWithParameters, String.class);
        return (String) responseEntity.getBody();
    }

    public String doGet(String protocol, String uri, Map<String, Object> dataMap) {
        String requestWithParameters = generateRequestParameters(protocol, uri, dataMap);
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

