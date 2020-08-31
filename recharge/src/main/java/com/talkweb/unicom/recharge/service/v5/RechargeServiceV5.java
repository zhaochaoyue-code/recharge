package com.talkweb.unicom.recharge.service.v5;

import cn.csatv.common.cache.CacheService;
import cn.csatv.common.utils.JsonUtils;
import cn.csatv.common.utils.StringMap;
import com.talkweb.unicom.order.bean.MailFeeStatus;
import com.talkweb.unicom.order.client.FeeClient;
import com.talkweb.unicom.recharge.bean.*;
import com.talkweb.unicom.recharge.client.SmsClient;
import com.talkweb.unicom.recharge.dao.*;
import com.talkweb.unicom.recharge.dictionary.RechargeProcessStatusEnums;
import com.talkweb.unicom.recharge.scheduled.Result;
import com.talkweb.unicom.recharge.service.RechargeServiceV3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * 把数据库mobile中的手机号数据分页读到内存中，开启两个线程处理充值逻辑。 一个线程负责调用充值接口，另一个线程负责查询充值状态。
 * 
 * @author zouxixi
 */
@Service
public class RechargeServiceV5 {

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
	}
	// 调用充值接口失败时最大重试次数
	private final int maxRechargeRretryCount = 1;

	// 要启动的充值线程数
	private final int rechargeThreadCount = 1;

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
	private AtomicBoolean isInterupt = new AtomicBoolean(false);
	private AtomicInteger rcharErrThreadCount = new AtomicInteger(0);

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
	private CacheService cacheService;

	public static ConcurrentHashMap<Long,Thread> concurrentHashMap = new ConcurrentHashMap(2);

	// 还没结束的充值线程数
	public static AtomicInteger tryErrorCount = new AtomicInteger(0);


	/**
	 * 开启线程充值。
	 */
	public String startRechargeForThread() throws Throwable {
		logger.info("concurrentHashMap.size()={}",concurrentHashMap.size());
		if(concurrentHashMap.size()>0){
			logger.info("正在充值，请稍后再试");
			return "正在充值，请稍后再试";
		}

		for (int i = 1; i <= rechargeThreadCount; i++) {// 启动多个个线程来充值，把这些线程放到一个线程组内。
			RechargeThread t = new RechargeThread(feeClient,mobileDao,threadMobileDao,rechargeRecordDao);
			concurrentHashMap.put(t.getId(),t);
			t.start();
		}
		return "启动成功...";
	}

	/**
	 * 手动结束充值所有线程
	 */
	public static String stopRechargeForThread(){
		while (true) {
				logger.info("concurrentHashMap.size()={}",concurrentHashMap.size());
				if(concurrentHashMap.size()==0){
					logger.info("concurrentHashMap.size()==0 结束线程");
					return "没有可停止的充值程序";
				}
			for (Map.Entry<Long, Thread> entry : concurrentHashMap.entrySet()) {
				Thread myThread = entry.getValue();
				myThread.stop();
				logger.info("线程id={}，线程名称={},stop",myThread.getId(), myThread.getName());
				/*if (!myThread.isInterrupted()) {
                    myThread.interrupt();
                    logger.info("线程id={}，线程名称={},interrupt",myThread.getId(), myThread.getName());
//                    myThread.stop();
//                    logger.info("线程id={}，线程名称={},stop",myThread.getId(), myThread.getName());
					concurrentHashMap.remove(entry.getKey(),myThread);
				}*/
					concurrentHashMap.remove(entry.getKey(),myThread);
			}
			return "停止成功，停止"+concurrentHashMap.size()+"个线程";
		}
	}

	public StringMap startRechargeThreadByQueue(List<Mobile> mobiles) {
		List<Mobile> list = mobileDao.findAllMobilesList();
		new Thread(new Runnable() {
			@Override
			public void run() {
				startRechargeThread(mobiles, list);
			}
		}).start();
		StringMap resMap =	new StringMap();
		resMap.put("totalCount",list.size());
		return  resMap;
	}


	/*private synchronized List<Mobile> getSomeMobilesToRecharge() {
		List<Mobile> mobiles = mobileDao.getSomeMobilesToRecharge(maxRechargeRretryCount, rechargeSize);
		logger.info("从数据库获取了一批手机号去充值，本次获取个数=" + mobiles.size() + "***************************");
		for (Mobile m : mobiles) {
			m.setProcessStatus(1);// 0待充值，1发送充值中，2已发送充值待查询状态，3发送状态查询中，4已状态查询充值成功待发送短信。5充值getFuture异常，6查询状态getFuture异常，7提交充值异常，8提交查询状态异常，9短信发送处理中，10短信发送成功。
			mobileDao.update(m);
		}
		return mobiles;
	}*/
	private synchronized List<Mobile> getSomeMobilesToRecharge(int j) {
		synchronized (RechargeServiceV5.class) {
//		ReentrantLock lock=new ReentrantLock();
//		lock.lock();
//		try {
//			List<Mobile> mobiles = mobileDao.getSomeMobilesToRecharge(maxRechargeRretryCount, rechargeSize);
			List<Mobile> mobiles = mobileDao.getAllMobilesToRecharge(maxRechargeRretryCount);
			String ids = mobiles.stream().map(Mobile::getMobile).collect(Collectors.joining(",", "", ""));
			logger.info("线程{},从数据库获取了一批手机号去充值，本次获取个数={},ids={}", j,mobiles.size(), ids);
			for (Mobile m : mobiles) {
				m.setProcessStatus(1);// 0待充值，1发送充值中，2已发送充值待查询状态，3发送状态查询中，4已状态查询充值成功待发送短信。5充值getFuture异常，6查询状态getFuture异常，7提交充值异常，8提交查询状态异常，9短信发送处理中，10短信发送成功。
				mobileDao.update(m);
			}
			logger.info("线程{},本次获取个数={},状态更新完毕", j,mobiles.size());
			return mobiles;
		/*}
		 finally {
			lock.unlock();
		}*/
		}
	}

	public  void startRechargeThread(List<Mobile> mobiles,List<Mobile> lsit) {
		logger.info("启动充值线程id：{},线程名称:{} *************************",Thread.currentThread().getId(),Thread.currentThread().getName());
		aliveRechargeThreadCount.incrementAndGet();
		List<Mobile> mobilesList = mobileDao.getAllMobilesToRecharge(maxRechargeRretryCount);

		if(mobiles==null) {
			for (Mobile m : mobilesList) {

				recharge(m, mobilesList,0);
				if (isInterupt.get()) {
					rcharErrThreadCount.set(0);
					break;
				}
			}
		}else{
			for (Mobile m : mobiles) {
				recharge(m, mobiles,1);
				if (isInterupt.get()) {
					rcharErrThreadCount.set(0);
					break;
				}
			}
		}


		aliveRechargeThreadCount.decrementAndGet();
		logger.info("充值线程运行结束id：{},线程名称:{} *************************",Thread.currentThread().getId(),Thread.currentThread().getName());
	}

	public void recharge(Mobile m,List<Mobile> mobiles,int k) {
		saveThreadMobile(Thread.currentThread().getName(), m);
		Future<MailFeeStatus> f = null;

		try {
//			f = feeClient.recharge(m.getMobile(), m.getMoney().intValue(), m.getActivityId(), m.getUid());

			/*Callable ca1 = new Callable() {
				@Override
				public MailFeeStatus call() throws Exception {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					MailFeeStatus mf = new MailFeeStatus();
					mf.setStatus(1);
					return mf;
				}
			};*/

//		Future<MailFeeStatus> f = new FutureTask<MailFeeStatus>(ca1);
//			FutureTask<String> f = new FutureTask<String>(ca1);
//			new Thread(new FutureTask<String>(ca1)).start();

//			Thread.sleep(1000);
			int a =10/1;
//			System.out.println("正常执行");
//			m.setProcessStatus(2);
//			mobileDao.update(m);
		} catch (Throwable e1) {
			m.setProcessStatus(7);
			logger.info("异常手机号是{},标志是{}",m.getMobile(),k);
			m.setRechargeExceptionMsg(e1.getMessage());
			mobileDao.update(m);
			deleteThreadMobile(m);

			int rechargeErrCount = rcharErrThreadCount.incrementAndGet();
			Integer totalErrorCount = cacheService.get("totalErrorCount", Integer.class);
			int totalCount = null != totalErrorCount ? totalErrorCount.intValue() : 0;

			logger.info("报警数量是{}，第{}个线程,手机号是{},状态是{}",rechargeErrCount,m.getMobile(),m.getProcessStatus());
			if(rechargeErrCount>=10){
				isInterupt.set(true);
//				Result.setPersonFlag(true);
				logger.info("==============超过报警数量是{}，第{}个线程,手机号是{},状态是{}",rechargeErrCount,m.getMobile(),m.getProcessStatus());
				//把漏掉的手机号更新为初始,为了下次可以充值上
				/*List<Mobile> mobileList = mobiles.stream().filter(s -> RechargeProcessStatusEnums.SENDRECHAGRE.getId()==s.getProcessStatus()).collect(Collectors.toList());
				List<String> mobileUids = mobileList.stream().map(Mobile::getUid).collect(Collectors.toList());
				mobileDao.updateProcessStatus0ForNext(mobileUids);*/
				cacheService.set("totalErrorCount",totalCount+rechargeErrCount);
				Integer count1 = cacheService.get("totalErrorCount", Integer.class);
				logger.info("数量是{}",count1);
				Result.setFlag(true);
			}else{
				isInterupt.set(false);
			}

			logger.error(
					"手机号 " + m.getMobile() + "提交充值异常，返回状态:" + JsonUtils.toJson(e1) + "***************************");
			return;
		}
		m.setLastRechargTime(new Date());
		m.setRechargeRetryCount(m.getRechargeRetryCount() + 1);

		mobileDao.update(m);
		deleteThreadMobile(m);

//		saveRechargeRecord(m, mfs);// 保存充值记录

	}

	private void saveThreadMobile(String threadName, Mobile m) {
		ThreadMobile threadMobile = new ThreadMobile();
		threadMobile.setId(m.getId());
		threadMobile.setThreadName(threadName);
		threadMobile.setUid(m.getUid());
		threadMobile.setMobile(m.getMobile());
		threadMobileDao.insert(threadMobile);
	}
	private void deleteThreadMobile(Mobile m) {
		threadMobileDao.removeById(m.getId());
	}
}
