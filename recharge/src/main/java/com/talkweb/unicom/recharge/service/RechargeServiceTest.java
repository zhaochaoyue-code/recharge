package com.talkweb.unicom.recharge.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talkweb.unicom.order.bean.MailFeeStatus;
import com.talkweb.unicom.order.client.FeeClient;
import com.talkweb.unicom.recharge.bean.Mobile;

import cn.csatv.common.utils.StringMap;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.json.JsonParser;
import jodd.util.RandomString;

/**
 * 开启多线程测试充值接口。
 *
 * @author zouxixi
 */
@Service
public class RechargeServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(RechargeServiceTest.class);

	@Autowired
	private FeeClient feeClient;

	private static ArrayList<Mobile> mobiles = new ArrayList<Mobile>();
	// static String activity_id = "talkweb_test";
	// static String activity_id = "tencent_activity";

	static String activity_id = "talkweb_test";
	static {
		Mobile m = new Mobile();
		m.setUid(UUID.randomUUID().toString().replaceAll("-", ""));
		m.setMobile("18874952573");
		m.setActivityId(activity_id);
		m.setMoney(new BigDecimal(1));

		mobiles.add(m);
	}

	public void start20Recharge() {
		Random random = new Random();
		for (int i = 1; i <= 3; i++) {// 启动多个个线程来充值，把这些线程放到一个线程组内。
			new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						int n = random.nextInt(mobiles.size());
						recharge1(mobiles.get(n));
						try {
							Thread.sleep(10L);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
		}
	}

	private void recharge1(Mobile m) {
		try {
			HttpRequest request = HttpRequest.post("http://localhost:10801/recharge.do");
			request.form("sid", "talkweb");
			request.form("activityId", "talkweb_test");
			request.form("busiId", UUID.randomUUID().toString().replaceAll("-", ""));
			request.form("mobile", "1" + RandomString.get().randomNumeric(10));
			request.form("money", "1");
			request.timeout(100000);
			HttpResponse response = request.send();
			String bodyText = response.bodyText();
			System.out.println("bodyText=" + bodyText);
			MailFeeStatus result = new MailFeeStatus();
			result = new JsonParser().parse(bodyText, result.getClass());
			logger.debug("id=" + result.getId() + ",staus=" + result.getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void recharge(Mobile m) {
		Future<MailFeeStatus> f = null;
		try {
			f = feeClient.recharge(m.getMobile(), m.getMoney().intValue(), m.getActivityId(),
					UUID.randomUUID().toString().replaceAll("-", ""));
		} catch (Exception e) {
			e.printStackTrace();
		}

		MailFeeStatus msf = null;
		try {
			msf = f.get();
			logger.debug("id=" + msf.getId() + ",staus=" + msf.getStatus());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	public void rechargeOne() {
		Mobile m = mobiles.get(0);
		Future<MailFeeStatus> f = null;
		try {
			f = feeClient.recharge(m.getMobile(), m.getMoney().intValue(), m.getActivityId(),
					UUID.randomUUID().toString().replaceAll("-", ""));
		} catch (Exception e) {
			e.printStackTrace();
		}

		MailFeeStatus msf = null;
		try {
			msf = f.get();
			logger.debug("id=" + msf.getId() + ",staus=" + msf.getStatus());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	public void rechargeOne1() {
		String url = "http://192.168.1.102:10801/recharge.do";
		HttpRequest request = HttpRequest.post(url);
		request.form("sid", "talkweb");
		request.form("activityId", "talkweb_test");
		request.form("busiId", UUID.randomUUID().toString().replaceAll("-", ""));
		request.form("mobile", UUID.randomUUID().toString().replaceAll("-", ""));
		HttpResponse response = request.send();
		String bodyText = response.bodyText();
		System.out.println("bodyText=" + bodyText);
		StringMap result = new StringMap();
		result = new JsonParser().parse(bodyText, result.getClass());
	}
}
