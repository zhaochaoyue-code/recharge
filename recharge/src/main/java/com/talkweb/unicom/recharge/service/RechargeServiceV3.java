package com.talkweb.unicom.recharge.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import cn.csatv.common.http.HttpClient;
import cn.csatv.common.http.HttpClientResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.talkweb.unicom.order.bean.MailFeeStatus;
import com.talkweb.unicom.order.client.FeeClient;
import com.talkweb.unicom.recharge.bean.Mobile;
import com.talkweb.unicom.recharge.bean.MobileSms;
import com.talkweb.unicom.recharge.bean.QueryRecord;
import com.talkweb.unicom.recharge.bean.RechargeRecord;
import com.talkweb.unicom.recharge.bean.SmsRecord;
import com.talkweb.unicom.recharge.bean.ThreadMobile;
import com.talkweb.unicom.recharge.client.SmsClient;
import com.talkweb.unicom.recharge.dao.MobileDao;
import com.talkweb.unicom.recharge.dao.MobileSmsDao;
import com.talkweb.unicom.recharge.dao.QueryRecordDao;
import com.talkweb.unicom.recharge.dao.RechargeRecordDao;
import com.talkweb.unicom.recharge.dao.SmsRecordDao;
import com.talkweb.unicom.recharge.dao.ThreadMobileDao;

import cn.csatv.common.utils.JsonUtils;

/**
 * 把数据库mobile中的手机号数据分页读到内存中，开启两个线程处理充值逻辑。 一个线程负责调用充值接口，另一个线程负责查询充值状态。
 *
 * @author zouxixi
 */
@Service
public class RechargeServiceV3 {

	private static final Logger logger = LoggerFactory.getLogger(RechargeServiceV3.class);

	// 短信提示内容。
	private static final Map<String, String> smsMessageMap = new HashMap<>(20);
	static {
		smsMessageMap.put("tencent_activity", "亲，你已成功参与“倚天在手，折扣全有——新订购用户即享3折优惠”活动，一次性返还话费14元已充值到账。WO+视频邀你畅看精彩。咨询电话：4000600611。");
		smsMessageMap.put("youku_activity", "亲爱的用户，感谢您使用联通优酷定向流量会员包产品，活动返还的15元话费已充值到账，咨询电话：4000600611。搜索关注公众号“联通视频权益助手”，赚积分，兑好礼，视频会员/流量/话费大把福利一号搞定！");
		smsMessageMap.put("mangguo_activity", "亲，你已成功参与“话费大逃脱，6元解密芒果密室探险”活动，一次性返还话费10元已充值到账。WO+视频邀你畅看精彩。咨询电话：4000600611。");
		smsMessageMap.put("pptv_activity", "亲，你已成功参与“PP视频VIP+流量低价看过瘾”活动，一次性返还话费11元已充值到账。WO+视频邀你畅看精彩。咨询电话：4000600611。");
		smsMessageMap.put("h5游戏", "小主，恭喜您获得抢红包大作战游戏话费奖励，已为您充值，请留意短信通知喔~关注公众号“联通视频权益助手”，赚积分，兑权益，福利大礼等你来！");

		smsMessageMap.put("iqi_0805", "【中国联通】感谢您订购联通爱奇艺定向流量会员包产品，活动返还的20元话费已充值成功到您的手机账户中，祝您使用愉快！");
		smsMessageMap.put("tx_0805", "【中国联通】感谢您订购联通腾讯定向流量会员包产品，活动返还的20元话费已充值成功到您的手机账户中，祝您使用愉快！");
		smsMessageMap.put("yk_0805", "【中国联通】感谢您订购联通优酷定向流量会员包产品，活动返还的20元话费已充值成功到您的手机账户中，祝您使用愉快！");

		//新建联通手机号充值一角活动测试
		smsMessageMap.put("corner_test", "【中国联通】感谢您订购联通手机号充值一角活动产品，活动返还的1角话费已充值成功到您的手机账户中，祝您使用愉快！");
		smsMessageMap.put("tx_20200512", "您已成功参与联通腾讯视频定向流量会员包 “会员免流8折购，百元大奖等你抽”活动，返还4元活费已到账。当月不退订，次月可继续参与抽奖，更多创3周边、京东卡、Q币等你拿~");
		smsMessageMap.put("yk_20200518", "您已成功参与优酷定向流量会员包 “五一7天乐5元优惠购”活动，返还15元活费已到账。后续活动更精彩，敬请关注！");
		smsMessageMap.put("tx_20200527", "您已成功参与联通腾讯视频定向流量会员包 “会员免流8折购，百元大奖等你抽”活动，返还4元活费已到账。当月不退订，次月可继续参与抽奖，更多创3周边、京东卡、Q币等你拿~");
		//20200605新建联通手机发短信活动
		smsMessageMap.put("tx_20200604", "您已成功参与联通腾讯视频定向流量会员包 “会员免流8折购，百元大奖等你抽”活动，返还4元活费已到账。当月不退订，次月可继续参与抽奖，更多创3周边、京东卡、Q币等你拿~");
		smsMessageMap.put("tx_20200618", "您已成功参与联通腾讯视频定向流量会员包5元优惠购买活动，返还15元话费已到账。当月不退订，次月可继续参与抽奖，更多创3周边、京东卡、Q币等你拿~");
		smsMessageMap.put("mg_20200705", "您已成功参与联通芒果TV视频定向流量会员包 “会员免流限时7元”活动，返还9元话费已到账。感谢您的参与！");
		//2020年07月15日，只发短信
		smsMessageMap.put("tx_20200715", "您已成功参与联通芒果TV视频定向流量会员包 “会员免流限时7元”活动，返还9元话费已到账。感谢您的参与！");
		//2020年08月5日
		smsMessageMap.put("mg_20200805", "您已成功参与联通芒果TV视频定向流量会员包 “会员免流限时7元”活动，返还9元话费已到账。感谢您的参与！");
	}
	// 调用充值接口失败时最大重试次数
	private final int maxRechargeRretryCount = 1;

	// 要启动的充值线程数
	private final int rechargeThreadCount = 20;

	// 还没结束的充值线程数
	private AtomicInteger aliveRechargeThreadCount = new AtomicInteger(0);

	// 调用充值状态最大重试次数
	private final int maxQueryRretryCount = 10;

	// 调用短信失败时最大重试次数
	private final int maxSmsRretryCount = 1;

	// 每次从数据库拿多少条手机号来充值
	private final int rechargeSize = 30;

	// 每次从数据库拿多少条手机号来查询充值状态
	private final int querySize = 1000;

	// 每次从数据库拿多少条手机号来发送短信
	private final int smsSize = 500;

	@Autowired
	private FeeClient feeClient;

	// 有两个类相同的名称，注入会异常，所以要指定别名
	@Resource(name = "mySmsClient")
	private SmsClient smsClient;

	@Autowired
	private MobileDao mobileDao;

	@Autowired
	private MobileSmsDao mobileSmsDao;

	@Autowired
	private ThreadMobileDao threadMobileDao;

	@Autowired
	private RechargeRecordDao rechargeRecordDao;

	@Autowired
	private SmsRecordDao smsRecordDao;

	@Autowired
	private QueryRecordDao queryRecordDao;

	@Autowired
	private HttpClient httpClient;

	@Value("${rechargeConfig.orderUrl}")
	private String orderUrl;

	@Value("${rechargeConfig.orderQueryUrl}")
	private String orderQueryUrl;

	/**
	 * 同时启动充值和状态查询和短信发送三个线程。
	 */
	public void startRechargeAndQueryAndSms() throws Throwable {
		for (int i = 1; i <= rechargeThreadCount; i++) {// 启动多个个线程来充值，把这些线程放到一个线程组内。
			new Thread(new Runnable() {
				@Override
				public void run() {
					startRechargeThread();
				}
			}).start();
		}

		new Thread(new Runnable() {
			@Override
			public void run() {
				startQueryThread();
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				startSmsThread();
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				updateProcessStatus1To0();
			}
		}).start();
	}

	/**
	 * 同时启动充值和状态查询发送二个线程。
	 */
	public void startRechargeAndQuery() throws Throwable {
		for (int i = 1; i <= rechargeThreadCount; i++) {// 启动多个个线程来充值，把这些线程放到一个线程组内。
			new Thread(new Runnable() {
				@Override
				public void run() {
					startRechargeThread();
				}
			}).start();
		}

		new Thread(new Runnable() {
			@Override
			public void run() {
				startQueryThread();
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				updateProcessStatus1To0();
			}
		}).start();
	}

	/**
	 * 一次从数据库拿出30条来处理，然后把这30立即标记为处理中状态。但是处理的时候，突然重启系统，或者异常，会导致有些数据在数据库中就一直是处理中状态。
	 * 要等所以的提交充值线程都结束了，才能把这把这些数据再重新标记为待充值状态，因为如果有充值线程没处理完则可能会把这些线程正真正在处理中的数据也错误的更新状态。
	 */
	private void updateProcessStatus1To0() {
		List<Mobile> mobiles = null;
		while (true) {
			logger.debug("充值还没结束的线程数有" + aliveRechargeThreadCount.get() + "个" + "*************************");
			if (aliveRechargeThreadCount.get() == 0) {// 如果所有的充值线程都已经结束
				mobileDao.updateProcessStatus0();
				mobiles = getSomeMobilesToRecharge();
				for (Mobile m : mobiles) {
					recharge(m);
				}

				if (mobiles.isEmpty()) {
					break;
				}
			}

			try {
				Thread.sleep(10 * 1000L);// 10秒钟。
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		logger.debug("充值异常善后处理结束" + "*************************");
	}

	public void startRechargeThread() {
		logger.info("启动充值线程：" + Thread.currentThread().getName() + "*************************");
		aliveRechargeThreadCount.incrementAndGet();
		List<Mobile> mobiles = getSomeMobilesToRecharge();
		while (!mobiles.isEmpty()) {

			for (Mobile m : mobiles) {
				recharge(m);
			}

			mobiles = getSomeMobilesToRecharge();

			try {
				Thread.sleep(5L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		aliveRechargeThreadCount.decrementAndGet();
		logger.info("充值线程运行结束:" + Thread.currentThread().getName() + "*************************");
	}

	/**
	 * 对单个手机号调用充值接口提交充值。
	 */
	private void recharge(Mobile m) {
		saveThreadMobile(Thread.currentThread().getName(), m);
		Future<MailFeeStatus> f = null;
		try {
			logger.info("{}通过httpclient 调用order代码",m.getMobile());
			f = rechargeCallOrder(m.getActivityId(), m.getUid(), m.getMobile(), m.getMoney().intValue());
		} catch (Throwable e1) {
			m.setProcessStatus(7);
			m.setRechargeExceptionMsg(e1.getMessage());
			mobileDao.update(m);
			deleteThreadMobile(m);
			logger.error(
					"手机号 " + m.getMobile() + "提交充值异常，返回状态:" + JsonUtils.toJson(e1) + "***************************");
			return;
		}

		MailFeeStatus mfs = null;
		try {
			mfs = f.get();

			if (mfs.getStatus() == 1) {// 充值中
				m.setProcessStatus(2);// 已发送充值待查询状态
			} else if (mfs.getStatus() == 2) {// 充值成功，// 如果这里就返回充值成功，则直接进入短信发送环节，不用再去查询充值状态了。
				m.setProcessStatus(4);
				m.setKcQueryId(mfs.getId());
				m.setKcQueryStatus(mfs.getStatus());
				m.setLastQueryTime(new Date());
			} else if (mfs.getStatus() == 3) {// 充值失败
				m.setIsFinished(1);// 已经完成
				m.setProcessStatus(12);
			} else if (mfs.getStatus() == 4) {// 充值超时
				m.setIsFinished(1);// 已经完成
				m.setProcessStatus(13);
			}
			// 0待充值，1发送充值中，2已发送充值待查询状态，3发送状态查询中，4已状态查询充值成功待发送短信。5充值getFuture异常，6查询状态getFuture异常，7提交充值异常，8提交查询状态异常，9短信发送处理中，10短信发送成功。
			m.setKcRechargeId(mfs.getId());
			m.setKcRechargeStatus(mfs.getStatus());

			logger.info("手机号 " + m.getMobile() + "充值正常，返回状态:"
					+ JsonUtils.toJson("id=" + mfs.getId() + ",status=" + mfs.getStatus())
					+ "***************************");
		} catch (Throwable e2) {
			logger.error("手机号 " + m.getMobile() + "充值异常，异常信息:" + JsonUtils.toJson(e2) + "***************************");
			m.setProcessStatus(5);
			m.setRechargeExceptionMsg(e2.getMessage());
		}

		m.setLastRechargTime(new Date());
		m.setRechargeRetryCount(m.getRechargeRetryCount() + 1);

		mobileDao.update(m);
		deleteThreadMobile(m);

		saveRechargeRecord(m, mfs);// 保存充值记录
	}

	/**
	 * 保存充值线程正在处理的手机号记录
	 */
	private void saveThreadMobile(String threadName, Mobile m) {
		ThreadMobile threadMobile = new ThreadMobile();
		threadMobile.setId(m.getId());
		threadMobile.setThreadName(threadName);
		threadMobile.setUid(m.getUid());
		threadMobile.setMobile(m.getMobile());
		threadMobileDao.insert(threadMobile);
	}

	/**
	 * 删除充值线程正在处理的手机号记录
	 */
	private void deleteThreadMobile(Mobile m) {
		threadMobileDao.removeById(m.getId());
	}

	/**
	 * 启动状态查询线程
	 */
	public void startQueryThread() {
		logger.info("启动充值状态查询线程*************************");

		// 一次从数据库拿出30条来处理，然后把这30立即标记为处理中状态。但是处理的时候，突然重启系统，或者异常，会导致有些数据就一直是处理中状态。这里把这些数据再拿去重试充值。
		mobileDao.updateProcessStatus2();

		List<Mobile> mobiles = getSomeMobilesToQueryStatus();
		int emptyMobilesCount = 1;// 查询到mobiles集合为空的次数，用于控制线程睡眠时间。

		while (true) {
			for (Mobile m : mobiles) {
				query(m);
			}

			mobiles = getSomeMobilesToQueryStatus();
			if (mobiles.isEmpty()) {
				emptyMobilesCount = emptyMobilesCount + 1;
				emptyMobilesCount = Math.min(emptyMobilesCount, 6);// 如果连续几次查询都没有要查询状态的数据，则让线程多睡眠一会。
			} else {
				emptyMobilesCount = 1;
			}

			try {
				if (mobiles.size() > 50) {
					Thread.sleep(5L);
				} else {
					Thread.sleep(emptyMobilesCount * 10 * 1000L);// 最小睡眠10秒，最大睡眠1分钟。
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 对单个手机号调用充值状态查询接口查询充值状态。
	 */
	private void query(Mobile m) {
		Future<MailFeeStatus> f = null;
		try {
//			f = feeClient.queryState(m.getActivityId(), m.getUid());
			f = rechargeQueryCallOrder(m.getActivityId(), m.getUid());
		} catch (Exception e1) {
			logger.error(
					"手机号 " + m.getMobile() + "提交状态查询异常，返回状态:" + JsonUtils.toJson(e1) + "***************************");

			m.setProcessStatus(8);// 8提交查询状态异常
			m.setQueryExceptionMsg(e1.getMessage());
			mobileDao.update(m);
			return;
		}

		MailFeeStatus mfs = null;
		try {
			mfs = f.get();

			logger.info("为手机号 " + m.getMobile() + "查询充值状态，返回状态:"
					+ JsonUtils.toJson("id=" + mfs.getId() + ",status=" + mfs.getStatus())
					+ "***************************");

			m.setKcQueryId(mfs.getId());
			m.setKcQueryStatus(mfs.getStatus());

			int queryStatus = mfs.getStatus();
			if (queryStatus == 1) {// 0：初始状态，1：充值中，2：充值成功，3：充值失败，4：充值超时
				m.setProcessStatus(2);// 0待充值，1发送充值中，2已发送充值待查询状态，3发送状态查询中，4充值成功待发短信。5充值getFuture异常，6查询状态getFuture异常，7提交充值异常，8提交查询状态异常
			} else if (queryStatus == 2) {// 充值成功
				m.setProcessStatus(4);
			} else if (queryStatus == 3) {// 充值失败
				m.setIsFinished(1);// 已经完成
				m.setProcessStatus(12);
			} else if (queryStatus == 4) {// 充值超时
				m.setIsFinished(1);// 已经完成
				m.setProcessStatus(13);
			}
		} catch (Throwable e2) {
			logger.error(
					"为手机号 " + m.getMobile() + "查询充值状态异常，返回状态:" + JsonUtils.toJson(e2) + "***************************");
			m.setProcessStatus(6);// 0待充值，1发送充值中，2已发送充值待查询状态，3发送状态查询中，4发送状态已获取。5充值getFuture异常，6查询状态getFuture异常，7提交充值异常，8提交查询状态异常
			m.setQueryExceptionMsg(e2.getMessage());
		}

		m.setLastQueryTime(new Date());
		m.setQueryRetryCount(m.getQueryRetryCount() + 1);
		if (m.getQueryRetryCount() > maxQueryRretryCount) {
			m.setIsFinished(1);
		}

		mobileDao.update(m);
		saveQueryRecord(m, mfs);// 保存查询记录
	}

	/**
	 * 启动短信发送线程
	 */
	public void startSmsThread() {
		logger.info("启动短信发送线程*************************");
		mobileDao.updateProcessStatus4();

		List<Mobile> mobiles = getSomeMobilesToSendSms();
		int emptyMobilesCount = 1;// 查询到mobiles集合为空的次数，用于控制线程睡眠时间。

		while (true) {
			for (Mobile m : mobiles) {
				sms(m);
			}

			mobiles = getSomeMobilesToSendSms();
			if (mobiles.isEmpty()) {
				emptyMobilesCount = emptyMobilesCount + 1;
				emptyMobilesCount = Math.min(emptyMobilesCount, 6);// 如果连续几次查询都没有要查询状态的数据，则让线程多睡眠一会。
			} else {
				emptyMobilesCount = 1;
			}

			try {
				if (mobiles.size() > 50) {
					Thread.sleep(5L);
				} else {
					Thread.sleep(emptyMobilesCount * 10 * 1000L);// 最小睡眠10秒，最大睡眠1分钟。
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 对单个手机号调用短信发送接口发送短信。
	 */
	private void sms(Mobile m) {
		try {
			String smdMsg = smsMessageMap.get(m.getActivityId());
			if (smdMsg == null) {
				throw new RuntimeException("未知的活动id");
			}
			sendSms(m, smdMsg);

			m.setProcessStatus(10);// 标记为短信发送成功
			m.setIsFinished(1);// 已经完成，标记为历史数据
		} catch (Throwable e) {
			logger.error("为手机号 " + m + "发送短信提示异常：" + JsonUtils.toJson(e) + "***************************");
			m.setSmsExceptionMsg(e.getMessage());
			m.setProcessStatus(11);// 11发送短信异常
		}
		m.setLastSmsTime(new Date());
		m.setSmsRetryCount(m.getSmsRetryCount() + 1);
		mobileDao.update(m);
	}

	/**
	 * 此方法会扫描mobile_sms表。而上面的发送短信线程是扫描mobile表。
	 */
	public void startSmsThread1() {
		logger.info("启动短信发送线程*************************");
		MobileSms bean = new MobileSms();
		bean.setSendStatus(1);// 发送状态，1待发送，2发送成功，3发送失败，4发送异常。
		bean.setOrderBy("id");
		List<MobileSms> mobiles = mobileSmsDao.findByExample(bean);

		for (MobileSms m : mobiles) {
			try {
				String smdMsg = smsMessageMap.get(m.getActivityId());
				if (smdMsg == null) {
					throw new RuntimeException("未知的活动id");
				}
				smsClient.sendSms(m.getMobile(), smdMsg);
				m.setSendStatus(2);
			} catch (Throwable e) {
				logger.error("为手机号 " + m + "发送短信提示异常：" + JsonUtils.toJson(e) + "***************************");
				m.setExceptionMsg(e.getMessage());
				m.setSendStatus(4);
			}
			mobileSmsDao.update(m);
		}

		logger.info("短信发送线程结束*************************");
	}

	/**
	 * 发送短信，并写发送记录
	 */
	private void sendSms(Mobile m, String msgContent) {
		try {
			smsClient.sendSms(m.getMobile(), msgContent);
			saveSmsRecord(m, null);
		} catch (Throwable e) {
			logger.error("为手机号 " + m.getMobile() + "发送短信提示异常：" + JsonUtils.toJson(e) + "***************************");
			saveSmsRecord(m, e);
		}
	}

	/**
	 * 保存短信发送记录
	 */
	private void saveSmsRecord(Mobile m, Throwable ex) {
		try {
			SmsRecord r = new SmsRecord();
			r.setMobile(m.getMobile());
			r.setMobileId(m.getId());
			r.setActivityId(m.getActivityId());
			// r.setContent(content);
			if (ex != null) {
				r.setMsg(ex.getMessage());
				r.setResult(2);// 发送状态，1成功，2失败
			} else {
				r.setResult(1);
			}
			smsRecordDao.insert(r);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存充值记录
	 */
	private void saveRechargeRecord(Mobile m, MailFeeStatus msf) {
		try {
			RechargeRecord r = new RechargeRecord();
			r.setMobile(m.getMobile());
			r.setMobileId(m.getId());
			r.setActivityId(m.getActivityId());
			if (msf == null) {
				r.setExceptionMsg(m.getRechargeExceptionMsg());
			} else {
				r.setKcId(msf.getId());
				r.setKcStatus(msf.getStatus());
			}
			rechargeRecordDao.insert(r);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存充值状态查询记录
	 */
	private void saveQueryRecord(Mobile m, MailFeeStatus msf) {
		try {
			QueryRecord r = new QueryRecord();
			r.setMobile(m.getMobile());
			r.setMobileId(m.getId());
			r.setActivityId(m.getActivityId());
			if (msf == null) {// 调用接口异常时此对象为空。
				r.setExceptionMsg(m.getQueryExceptionMsg());
			} else {
				r.setKcId(msf.getId());
				r.setKcStatus(msf.getStatus());
			}
			queryRecordDao.insert(r);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从数据库获取一批手机号来充值。
	 */
	private synchronized List<Mobile> getSomeMobilesToRecharge() {
		List<Mobile> mobiles = mobileDao.getSomeMobilesToRecharge(maxRechargeRretryCount, rechargeSize);
		logger.info("从数据库获取了一批手机号去充值，本次获取个数=" + mobiles.size() + "***************************");
		for (Mobile m : mobiles) {
			m.setProcessStatus(1);// 0待充值，1发送充值中，2已发送充值待查询状态，3发送状态查询中，4已状态查询充值成功待发送短信。5充值getFuture异常，6查询状态getFuture异常，7提交充值异常，8提交查询状态异常，9短信发送处理中，10短信发送成功。
			mobileDao.update(m);
		}
		return mobiles;
	}

	/**
	 * 从数据库获取一批手机号来查询充值状态。
	 */
	private synchronized List<Mobile> getSomeMobilesToQueryStatus() {
		List<Mobile> mobiles = mobileDao.getSomeMobilesToQueryStatus(maxQueryRretryCount, querySize);
		logger.info("从数据库获取了一批手机号去查询状态，本次获取个数=" + mobiles.size() + "***************************");
		for (Mobile m : mobiles) {
			m.setProcessStatus(3);// 0待充值，1发送充值中，2已发送充值待查询状态，3发送状态查询中，4已状态查询充值成功待发送短信。5充值getFuture异常，6查询状态getFuture异常，7提交充值异常，8提交查询状态异常，9短信发送处理中，10短信发送成功。
			mobileDao.update(m);
		}
		return mobiles;
	}

	/**
	 * 从数据库获取一批手机号来发送短信。
	 */
	private synchronized List<Mobile> getSomeMobilesToSendSms() {
		List<Mobile> mobiles = mobileDao.getSomeMobilesToSendSms(maxSmsRretryCount, smsSize);
		logger.info("从数据库获取了一批手机号去发送短信，本次获取个数=" + mobiles.size() + "***************************");
		for (Mobile m : mobiles) {
			m.setProcessStatus(9);// 0待充值，1发送充值中，2已发送充值待查询状态，3发送状态查询中，4已状态查询充值成功待发送短信。5充值getFuture异常，6查询状态getFuture异常，7提交充值异常，8提交查询状态异常，9短信发送处理中，10短信发送成功。
			mobileDao.update(m);
		}
		return mobiles;
	}

	public Future<MailFeeStatus> rechargeCallOrder(String activityId, String busiId, String mobile, Integer money) throws Exception{
		HttpClientResolver clientResolver = httpClient.supportBalance(false);
		clientResolver.params("activityId",activityId);
		clientResolver.params("busiId",busiId);
		clientResolver.params("mobile",mobile);
		clientResolver.params("money",money);
		Future<MailFeeStatus> mailFeeStatusFuture = clientResolver.postForObject(orderUrl, MailFeeStatus.class);
		logger.info("{}手机号调用order成功并返回充值状态{}",mobile,mailFeeStatusFuture.get().getStatus());
		return mailFeeStatusFuture;
	}


	public Future<MailFeeStatus> rechargeQueryCallOrder(String activityId, String busiId) throws Exception{
		HttpClientResolver clientResolver = httpClient.supportBalance(false);
		clientResolver.params("activityId",activityId);
		clientResolver.params("id",busiId);
		Future<MailFeeStatus> mailFeeStatusFuture = clientResolver.postForObject(orderQueryUrl, MailFeeStatus.class);
		logger.info("{}活动id调用order成功并返回查询状态{}",activityId,mailFeeStatusFuture.get().getStatus());
		return mailFeeStatusFuture;
	}
}
