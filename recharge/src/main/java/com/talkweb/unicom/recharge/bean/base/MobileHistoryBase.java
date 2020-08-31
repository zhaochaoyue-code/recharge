package com.talkweb.unicom.recharge.bean.base;

import cn.csatv.common.db.bean.IDEntity;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Date;
import java.util.Date;


/**
 * 基类不要手动添加代码，不然重复生成时会覆盖。如果要手动添加自定义的代码要加到子类里面
 * @author mybatis-generator
 */
public class MobileHistoryBase extends IDEntity {
	
	private static final long serialVersionUID = 1L;

    /**
	 * 提交充值时使用此id。使用REPLACE(UUID(), '-', '')方式获取。
	*/
	private String uid;
	
	/**
	 * 活动id，同个活动内手机号不能重复。
	*/
	private String activityId;
	
	/**
	 * 手机号码
	*/
	private String mobile;
	
	/**
	 * 充值金额。单位为分。
	*/
	private BigDecimal money;
	
	/**
	 * 调用充值接口后返回的id，用于后来的状态查询。
	*/
	private String kcRechargeId;
	
	/**
	 * 调用充值接口后返回状态。0：初始状态，1：充值中，2：充值成功，3：充值失败，4：充值超时
	*/
	private Integer kcRechargeStatus;
	
	/**
	 * 调用查询充值状态接口后返回的id。
	*/
	private String kcQueryId;
	
	/**
	 * 调用查询充值状态接口后返回状态。0：初始状态，1：充值中，2：充值成功，3：充值失败，4：充值超时
	*/
	private Integer kcQueryStatus;
	
	/**
	 * 是否完成。发送成功或者重试超过配置的次数即认为完成。1是0否。
	*/
	private Integer isFinished;
	
	/**
	 * 处理状态。0待充值，1发送充值中，2已发送充值待查询状态，3发送状态查询中，4已状态查询充值成功待发送短信。5充值getFuture异常，6查询状态getFuture异常，7提交充值异常，8提交查询状态异常，9短信发送处理中，10短信发送成功，11发送短信异常
	*/
	private Integer processStatus;
	
	/**
	 * 已经重试过充值的次数。
	*/
	private Integer rechargeRetryCount;
	
	/**
	 * 已经重试过查询充值状态的次数。
	*/
	private Integer queryRetryCount;
	
	/**
	 * 已经重试过短信发送的次数。
	*/
	private Integer smsRetryCount;
	
	/**
	 * 调用充值接口的时间
	*/
	private Date lastRechargTime;
	
	/**
	 * 调用查询状态接口的时间
	*/
	private Date lastQueryTime;
	
	/**
	 * 调用短信接口的时间
	*/
	private Date lastSmsTime;
	
	/**
	 * 调用充值接口的异常信息
	*/
	private String rechargeExceptionMsg;
	
	/**
	 * 调用查询状态接口的异常信息
	*/
	private String queryExceptionMsg;
	
	/**
	 * 调用短信发送接口的异常信息。
	*/
	private String smsExceptionMsg;
	
	public String getUid() {
		return uid;
	}
	
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public String getActivityId() {
		return activityId;
	}
	
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public BigDecimal getMoney() {
		return money;
	}
	
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	
	public String getKcRechargeId() {
		return kcRechargeId;
	}
	
	public void setKcRechargeId(String kcRechargeId) {
		this.kcRechargeId = kcRechargeId;
	}
	
	public Integer getKcRechargeStatus() {
		return kcRechargeStatus;
	}
	
	public void setKcRechargeStatus(Integer kcRechargeStatus) {
		this.kcRechargeStatus = kcRechargeStatus;
	}
	
	public String getKcQueryId() {
		return kcQueryId;
	}
	
	public void setKcQueryId(String kcQueryId) {
		this.kcQueryId = kcQueryId;
	}
	
	public Integer getKcQueryStatus() {
		return kcQueryStatus;
	}
	
	public void setKcQueryStatus(Integer kcQueryStatus) {
		this.kcQueryStatus = kcQueryStatus;
	}
	
	public Integer getIsFinished() {
		return isFinished;
	}
	
	public void setIsFinished(Integer isFinished) {
		this.isFinished = isFinished;
	}
	
	public Integer getProcessStatus() {
		return processStatus;
	}
	
	public void setProcessStatus(Integer processStatus) {
		this.processStatus = processStatus;
	}
	
	public Integer getRechargeRetryCount() {
		return rechargeRetryCount;
	}
	
	public void setRechargeRetryCount(Integer rechargeRetryCount) {
		this.rechargeRetryCount = rechargeRetryCount;
	}
	
	public Integer getQueryRetryCount() {
		return queryRetryCount;
	}
	
	public void setQueryRetryCount(Integer queryRetryCount) {
		this.queryRetryCount = queryRetryCount;
	}
	
	public Integer getSmsRetryCount() {
		return smsRetryCount;
	}
	
	public void setSmsRetryCount(Integer smsRetryCount) {
		this.smsRetryCount = smsRetryCount;
	}
	
	public Date getLastRechargTime() {
		return lastRechargTime;
	}
	
	public void setLastRechargTime(Date lastRechargTime) {
		this.lastRechargTime = lastRechargTime;
	}
	
	public Date getLastQueryTime() {
		return lastQueryTime;
	}
	
	public void setLastQueryTime(Date lastQueryTime) {
		this.lastQueryTime = lastQueryTime;
	}
	
	public Date getLastSmsTime() {
		return lastSmsTime;
	}
	
	public void setLastSmsTime(Date lastSmsTime) {
		this.lastSmsTime = lastSmsTime;
	}
	
	public String getRechargeExceptionMsg() {
		return rechargeExceptionMsg;
	}
	
	public void setRechargeExceptionMsg(String rechargeExceptionMsg) {
		this.rechargeExceptionMsg = rechargeExceptionMsg;
	}
	
	public String getQueryExceptionMsg() {
		return queryExceptionMsg;
	}
	
	public void setQueryExceptionMsg(String queryExceptionMsg) {
		this.queryExceptionMsg = queryExceptionMsg;
	}
	
	public String getSmsExceptionMsg() {
		return smsExceptionMsg;
	}
	
	public void setSmsExceptionMsg(String smsExceptionMsg) {
		this.smsExceptionMsg = smsExceptionMsg;
	}
	
	

}