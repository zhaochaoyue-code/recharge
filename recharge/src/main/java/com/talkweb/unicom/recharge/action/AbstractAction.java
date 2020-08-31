package com.talkweb.unicom.recharge.action;

import com.github.pagehelper.util.StringUtil;
import com.talkweb.unicom.recharge.config.Config;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class AbstractAction {

    @Autowired
    private Config config;

    protected Long getActivityId(HttpServletRequest request) {
        String channel = (String) request.getSession().getAttribute("channel");
        if("tencent".equals(channel)) {
            return 10L;
        }
        return config.getActivityId();
    }

    protected String getChannelId(HttpServletRequest request) {
        String channel = (String) request.getSession().getAttribute("channel");
        String channelCid = (String) request.getSession().getAttribute("cid");  //在拦截器中setAttribute
        //顺序不可颠倒
        if (StringUtil.isNotEmpty(channelCid)) {
            return channelCid;
        }

        if (StringUtil.isNotEmpty(channel)){
            return channel;
        }

        return config.getOrderChannel();
    }
}
