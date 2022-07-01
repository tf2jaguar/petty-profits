package io.github.tf2jaguar.pettyprofits.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author : zhangguodong
 * @since : 2022/7/1 16:09
 */
@ConfigurationProperties(prefix = "http.pool")
public class HttpClientConfiguration {
    private int maxTotal = 200;
    private int defaultMaxPerRoute = 100;
    private int connTimeOut = 3000;
    private int connReqTimeOut = 500;
    private int socketTimeout = 65000;
    private int inactivity = 2000;

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public int getDefaultMaxPerRoute() {
        return defaultMaxPerRoute;
    }

    public void setDefaultMaxPerRoute(int defaultMaxPerRoute) {
        this.defaultMaxPerRoute = defaultMaxPerRoute;
    }

    public int getConnTimeOut() {
        return connTimeOut;
    }

    public void setConnTimeOut(int connTimeOut) {
        this.connTimeOut = connTimeOut;
    }

    public int getConnReqTimeOut() {
        return connReqTimeOut;
    }

    public void setConnReqTimeOut(int connReqTimeOut) {
        this.connReqTimeOut = connReqTimeOut;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public int getInactivity() {
        return inactivity;
    }

    public void setInactivity(int inactivity) {
        this.inactivity = inactivity;
    }
}
