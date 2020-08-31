package com.talkweb.unicom.recharge.utils;

import cn.csatv.common.exception.BusinessException;
import cn.csatv.common.sequence.SequenceUtils;
import cn.csatv.common.utils.StringMap;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.json.JsonParser;
import jodd.log.Logger;
import jodd.log.LoggerFactory;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 短信发送工具类，与SmsUtil类中接口的区别是这个接口只能发联通的号。
 * 
 * @author zcy 复制backward发短信工具类
 */
@Component
public class SmsKCUtil {

	private static final Logger logger = LoggerFactory.getLogger(SmsKCUtil.class);

	private static final String url = "http://172.16.119.143:10901/sms/v1/sendSms.do";


	/**
	 * 短信发送，这个接口只能发联通的号。
	 */
	public StringMap send(String mobile, String content) {
		StringMap result = new StringMap();
		if (StringUtil.isBlank(mobile)) {
			logger.error("调用短信接口参数错误，手机号为空。");
			throw new BusinessException("7001", "调用短信接口参数错误，手机号为空。");
		}

		boolean isSuccess = false;// 是否发送成功。
		String code = null;// 调用接口返回的code
		String msg = null;// 调用接口返回的msg
		Integer httpStatus = null;
		long startTime = System.currentTimeMillis();
		try {
			HttpRequest request = HttpRequest.post(url);
			request.connectionTimeout(4000);
			request.form("mobile", mobile);
			request.form("content", content);
			HttpResponse response = null;
			try {
				response = request.send();
				httpStatus = response.statusCode();
			} catch (Exception e) {
				e.printStackTrace();
				code = "8001";
				msg = "操作失败，调用短信接口服务器异常： + e.getMessage()";
				throw new BusinessException("8001", "操作失败，调用短信接口服务器异常：" + e.getMessage());
			}

			String body = response.bodyText();
			StringMap responseData = JsonParser.create().parse(body, StringMap.class);
			code = (String) responseData.get("code");
			msg = (String) responseData.get("msg");
			if ("0".equals(code)) {
				isSuccess = true;
			} else {
				throw new BusinessException(code, msg, responseData);
			}
			result.put("code", code);
			result.put("msg", msg);
		} catch (Exception e) {
			e.printStackTrace();
			code = "8001";
			msg = e.getMessage();
			throw new BusinessException("8001", e.getMessage());
		} finally {
			long endTime = System.currentTimeMillis();
		}
		return result;
	}
	
	//如果字符串长度大于length，则将字符串截取为长度为length的字符串
	//防止短信内容或调用接口参数内容content和调用短信接口返回的msg字符串长度过大导致数据库插入短信日志发生异常
	public String limitStringLength(String str,int length) {
		if(str != null && str.length()>length) {
			str = str.substring(0, length);
		}
		return str;
	}
	
	
	/**
	 * 短信发送，这个接口只能发联通的号,将短信内容拆分成多条短信发送。
	 */
	public void sendSplitContent(String mobile, String content,int len) {
		String smsContent = null;
		logger.info("================开始循环===================");
		while(content.length()>len) {
			smsContent = content.substring(0, len);
			content = content.substring(len);
			this.send(mobile, smsContent);
			logger.info(smsContent);
			logger.info("***************************************");
		}
		this.send(mobile, content);
		logger.info(content);
		logger.info("=================结束循环==================");
	}
}
