package com.talkweb.unicom.recharge.client;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import cn.csatv.common.exception.BusinessException;
import cn.csatv.common.utils.StringMap;

/**
 * 给指定手机号码发送自定义内容的短信。
 * 
 * @author zouxixi
 * @create 2019-03-08.
 */
@Component("mySmsClient")
public class SmsClient extends AbstractClient {

	/**
	 * 发送短信。
	 * 
	 * @param mobile
	 *            要发送的手机号
	 * @param content
	 *            短信内容
	 * @throws Exception
	 */
	public boolean sendSms(String mobile, String content) throws Exception {
		StringMap result = getClient().params("mobile", mobile).params("content", content)
				.postForObject("http://172.16.119.143:10901/sms/v1/sendSms.do", StringMap.class).get();

		String code = result.getString("code");
		if (StringUtils.isNotBlank(code) && !"0".equals(code)) {
			String msg = result.getString("msg");
			throw new BusinessException(code, msg);
		}
		return true;
	}

}
