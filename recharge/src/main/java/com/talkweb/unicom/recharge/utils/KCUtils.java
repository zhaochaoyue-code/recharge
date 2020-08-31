package com.talkweb.unicom.recharge.utils;

import org.apache.commons.lang.time.DateFormatUtils;

import java.net.URLEncoder;
import java.util.Date;

public class KCUtils {

    /**
     * 加密手机号码
     * @param mobile
     * @param secret
     * @return
     * @throws Exception
     */
    public static String encryptMobile(String mobile, String secret, boolean encode) throws Exception {
        String v = DESUtil.encode(mobile, secret);
        if(encode) {
            return URLEncoder.encode(v, "UTF-8");
        }
        return v;
    }

    public static String encryptMobile(String mobile, String secret) throws Exception {
        return encryptMobile(mobile, secret, true);
    }

    public static String getTimestamp() {
        return DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
    }

    public static String sign(String channel, String userid, String timestamp, String secret) {
        return SHA1Util.encrypt(channel + userid + timestamp + secret);
    }

}
