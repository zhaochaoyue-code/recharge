package com.talkweb.unicom.recharge.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kc")
public class KCConfig {

    /**
     * 基础地址
     */
    private String baseUri;

    private String spid;

    private String cpid;

    /**
     * 密钥
     */
    private String secret;

    /**
     * 腾讯渠道
     */
    private String tencentChannel;

    /**
     * 爱奇艺渠道
     */
    private String iqiyiChannel;

    public String getBaseUri() {
        return baseUri;
    }

    public void setBaseUri(String baseUri) {
        this.baseUri = baseUri;
    }

    public String getSpid() {
        return spid;
    }

    public void setSpid(String spid) {
        this.spid = spid;
    }

    public String getCpid() {
        return cpid;
    }

    public void setCpid(String cpid) {
        this.cpid = cpid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getTencentChannel() {
        return tencentChannel;
    }

    public void setTencentChannel(String tencentChannel) {
        this.tencentChannel = tencentChannel;
    }

    public String getIqiyiChannel() {
        return iqiyiChannel;
    }

    public void setIqiyiChannel(String iqiyiChannel) {
        this.iqiyiChannel = iqiyiChannel;
    }
}
