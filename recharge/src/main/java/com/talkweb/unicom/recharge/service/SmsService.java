package com.talkweb.unicom.recharge.service;

import cn.csatv.common.exception.BusinessException;
import cn.csatv.common.exception.ICodeExcetpion;
import cn.csatv.common.utils.ExceptionUtils;
import cn.csatv.common.utils.StringMap;
import com.talkweb.unicom.order.client.SmsClient;
import com.talkweb.unicom.recharge.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class SmsService {

    @Autowired(required = false)
    private HttpServletRequest request;

    @Autowired
    private SmsClient smsClient;

    @Autowired
    private Config config;

    /**
     * 发送短信验证码
     * @param mobile
     * @param spid
     * @param channel
     * @return
     */
    public StringMap sendSmsCode(String mobile, Long activityId, String spid, String channel) throws Throwable {
        StringMap map = null;
        try {
            map = smsClient.sendSmsCode(mobile, activityId, spid, channel).get();
        } catch (Throwable t) {
            t = ExceptionUtils.getCause(t);
            if(t instanceof ICodeExcetpion && ((ICodeExcetpion) t).getCode().equals("8000")) {
                map = new StringMap();
                map.put("remainTime", 60);
            } else {
                throw t;
            }
        }
        return map;
    }

    /**
     * 校验验证码
     * @param mobile
     * @param code
     * @throws Exception
     */
    public void checkSmsCode(String mobile, Long activityId, String code) throws Exception {
        smsClient.checkSmsCode(mobile, activityId, code).get();
        if(request != null) {
            request.getSession().setAttribute("mobile", mobile);
        }
    }

}
