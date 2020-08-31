package com.talkweb.unicom.recharge.action;

import cn.csatv.common.http.HttpClient;
import cn.csatv.common.http.HttpClientResolver;
import com.talkweb.unicom.order.bean.MailFeeStatus;
import com.talkweb.unicom.recharge.scheduled.CheckRechargeTimer;
import com.talkweb.unicom.recharge.scheduled.Result;
import com.talkweb.unicom.recharge.service.v5.RechargeServiceV5;
import com.talkweb.unicom.recharge.service.v6.RechargeServiceV6;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.talkweb.unicom.recharge.service.RechargeServiceTest;
import com.talkweb.unicom.recharge.service.RechargeServiceV3;

import cn.csatv.common.utils.StringMap;

import java.util.concurrent.Future;


@RestController
public class RechargeAction extends AbstractAction {

	private static final Logger log = LoggerFactory.getLogger(RechargeAction.class);

	@Autowired
	private RechargeServiceV3 rechargeServiceV3;

	@Autowired
	private RechargeServiceV5 rechargeServiceV5;

	@Autowired
	private RechargeServiceV6 rechargeServiceV6;

	@Autowired
	private RechargeServiceTest rechargeServiceTest;

	@Autowired
	private HttpClient httpClient;

	@Value("${rechargeConfig.orderUrl}")
	private String orderUrl;

	@Value("${rechargeConfig.orderQueryUrl}")
	private String orderQueryUrl;
	
	/**
	 * 启动测试，测试一个手机号
	 */
	//@RequestMapping("rechargeOne")
	public StringMap rechargeOne() throws Throwable {
		rechargeServiceTest.rechargeOne();
		return null;
	}
	
	/**
	 * 启动压力测试
	 */
	//@RequestMapping("stressTest")
	public StringMap rechargeTest() throws Throwable {
		rechargeServiceTest.start20Recharge();
		return null;
	}

	/**
	 * 启动充值线程和状态查询线程 http://localhost:10881/startRechargeAndQuery
	 */
	//@RequestMapping("startRechargeAndQuery")
	public StringMap startRechargeAndQuery() throws Throwable {
		rechargeServiceV3.startRechargeAndQuery();
		return null;
	}

	/**
	 * 启动充值线程 http://localhost:10881/startRechargeThread
	 */
	//@RequestMapping("startRechargeThread")
	public StringMap startRechargeThread() throws Throwable {
		rechargeServiceV3.startRechargeThread();
		return null;
	}

	/**
	 * 启动充值状态查询线程 http://localhost:10881/startQueryThread
	 */
	//@RequestMapping("startQueryThread")
	public StringMap startQueryThread() throws Throwable {
		rechargeServiceV3.startQueryThread();
		return null;
	}

	/**
	 * 启动充值状态查询线程 http://localhost:10881/startRechargeForThread
	 */
	@RequestMapping("startRechargeForThread")
	public Object startRechargeThreadByQueue(@RequestParam(value = "auto",defaultValue = "0") Integer auto) throws Throwable {
		return rechargeServiceV5.startRechargeForThread();
	}

	/**
	 * 启动充值状态查询线程 http://localhost:10881/stopRechargeForThread
	 */
	@RequestMapping("stopRechargeForThread")
	public Object stopRechargeThreadByQueue(@RequestParam(value = "auto",defaultValue = "0") Integer auto) throws Throwable {
		return rechargeServiceV5.stopRechargeForThread();
	}

	@RequestMapping("startRechargeThreadByQueue")
	public StringMap startRechargeThreadByQueue(@RequestParam(value = "auto",defaultValue = "0") Integer clearCache,
												@RequestParam(value = "rechargeCount",defaultValue ="0") Integer rechargeCount) throws Throwable {
		log.info("进入充值");
		StringMap stringMap = new StringMap();
		Result.setPersonFlag(true);
		Result.setStopFlag(false);
		log.info("==============人工点击{}",Result.isPersonFlag());
//        rechargeServiceV6.startRechargeThread6(null);
//        rechargeServiceV3.startRechargeThread();
		rechargeServiceV6.startRechargeThread6(null, null);
		stringMap.put("提示","启动充值成功");
		return stringMap;
	}

	@RequestMapping("stopRechargeThreadByQueue")
	public StringMap stopRechargeThreadByQueue(@RequestParam(value = "auto",defaultValue = "0") Integer clearCache,
												@RequestParam(value = "rechargeCount",defaultValue ="0") Integer rechargeCount) throws Throwable {
		//停止标志 为了定时器控制
		Result.setStopFlag(true);

        //异常或者充值中停止
        Result.setFlag(true);

        StringMap stringMap = new StringMap();
        stringMap.put("提示","已停止充值服务");
		return stringMap;
	}

	/**
	 * 更新mobile 状态 更新值为order传来的值，（order的值来源是邮箱调用接口返回的值）
	 * @param mobile
	 * @param status
	 * @return
	 * @throws Throwable
	 */
	@RequestMapping("/recharge/notify.do")
	public void rechargeNotify(@RequestParam String id, @RequestParam String mobile, @RequestParam int status) throws Throwable {
		rechargeServiceV6.updateProcessStatusFromOrder(id,mobile,status);
	}


	@RequestMapping("/test.do")
	public Future<MailFeeStatus> rechargeQueryCallOrder(@RequestParam String id, @RequestParam String mobile, @RequestParam int status) throws Throwable {
		Future<MailFeeStatus> f = rechargeQueryCallOrder("11", "22");
		return  f;
	}
	public Future<MailFeeStatus> rechargeQueryCallOrder(String activityId, String busiId) throws Exception{
		HttpClientResolver clientResolver = httpClient.supportBalance(false);
		clientResolver.params("activityId",activityId);
		clientResolver.params("busiId",busiId);
		Future<MailFeeStatus> mailFeeStatusFuture = clientResolver.postForObject(orderQueryUrl, MailFeeStatus.class);
//		logger.info("{}活动id调用order成功并返回查询状态{}",activityId,mailFeeStatusFuture.get().getStatus());
		return mailFeeStatusFuture;
	}
}
