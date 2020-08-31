package com.talkweb.unicom.recharge.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "order")
public class Config {

    /**
     * 活动编号
     */
    private Long activityId;

    /**
     * 15元产品
     */
    private String spid15;

    /**
     * 20元产品
     */
    private String spid20;

    private String cpid;

    private String spid;

    /**
     * 活动类型，用于领取会员
     */
    private Integer activeType;

    private String orderChannel;


    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getSpid15() {
        return spid15;
    }

    public void setSpid15(String spid15) {
        this.spid15 = spid15;
    }

    public String getSpid20() {
        return spid20;
    }

    public void setSpid20(String spid20) {
        this.spid20 = spid20;
    }

    public String getCpid() {
        return cpid;
    }

    public void setCpid(String cpid) {
        this.cpid = cpid;
    }

    public String getOrderChannel() {
        return orderChannel;
    }

    public void setOrderChannel(String orderChannel) {
        this.orderChannel = orderChannel;
    }

    public String getSpid() {
        return spid;
    }

    public void setSpid(String spid) {
        this.spid = spid;
    }

    public Integer getActiveType() {
        return activeType;
    }

    public void setActiveType(Integer activeType) {
        this.activeType = activeType;
    }
}
