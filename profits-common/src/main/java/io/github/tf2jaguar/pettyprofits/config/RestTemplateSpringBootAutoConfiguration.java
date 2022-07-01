package io.github.tf2jaguar.pettyprofits.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author : zhangguodong
 * @since : 2022/7/1 16:08
 */
@Configuration
@EnableConfigurationProperties({HttpClientConfiguration.class})
@ConditionalOnProperty(prefix = "http.pool", name = "enabled", havingValue = "true", matchIfMissing = true)
public class RestTemplateSpringBootAutoConfiguration {

    @Autowired
    private HttpClientConfiguration httpClientConfiguration;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(httpRequestFactory());
    }

    @Bean
    public ClientHttpRequestFactory httpRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory(httpClient());
    }

    @Bean
    public CloseableHttpClient httpClient() {
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        connectionManager.setMaxTotal(httpClientConfiguration.getMaxTotal());
        connectionManager.setDefaultMaxPerRoute(httpClientConfiguration.getDefaultMaxPerRoute());
        connectionManager.setValidateAfterInactivity(httpClientConfiguration.getInactivity());
        RequestConfig requestConfig = RequestConfig.custom()
                //服务器返回数据(response)的时间，超过抛出read timeout
                .setSocketTimeout(httpClientConfiguration.getSocketTimeout())
                //连接上服务器(握手成功)的时间，超出抛出connect timeout
                .setConnectTimeout(httpClientConfiguration.getConnTimeOut())
                //从连接池中获取连接的超时时间，超时间未拿到可用连接，会抛出org.apache.http.conn.ConnectionPoolTimeoutException: Timeout waiting for connection from pool
                .setConnectionRequestTimeout(httpClientConfiguration.getConnReqTimeOut())
                .build();
        return HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager)
                .build();
    }
}
