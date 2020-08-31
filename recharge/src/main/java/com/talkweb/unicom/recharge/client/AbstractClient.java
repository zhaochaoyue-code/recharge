package com.talkweb.unicom.recharge.client;

import org.springframework.beans.factory.annotation.Autowired;

import cn.csatv.common.http.HttpClient;
import cn.csatv.common.http.HttpClientResolver;

public abstract class AbstractClient {

    @Autowired
    private HttpClient httpClient;

    protected HttpClientResolver getClient() {
        return httpClient.supportBalance(false);
    }

}
