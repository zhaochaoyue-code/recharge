package com.talkweb.unicom.recharge.action;

import java.util.UUID;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.talkweb.unicom.order.bean.MailFeeStatus;
import com.talkweb.unicom.recharge.ApplicationWebTest;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.json.JsonParser;

/**
 * 测试短信发送相关接口。
 * 
 * @author zouxixi
 * @date 2019-06-29
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FeeActionTest extends ApplicationWebTest {

	protected static final String URL_CONTEXT = url_prefix + "/";

	/**
	 * 测试短信发送相
	 */
	@Test
	public void test01Recharge() {
		try {
			HttpRequest request = HttpRequest.post("http://localhost:10801/recharge.do");
			request.form("sid", "talkweb");
			request.form("activityId", "talkweb_test");
			request.form("busiId", UUID.randomUUID().toString().replaceAll("-", ""));
			request.form("mobile", "18874952573");
			request.form("money", "1");
			request.timeout(4000);
			HttpResponse response = request.send();
			String bodyText = response.bodyText();
			System.out.println("bodyText=" + bodyText);
			Assert.assertEquals(200, response.statusCode());
			MailFeeStatus result = new MailFeeStatus();
			result = new JsonParser().parse(bodyText, result.getClass());
			Assert.assertEquals(result.getStatus(), "1");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
