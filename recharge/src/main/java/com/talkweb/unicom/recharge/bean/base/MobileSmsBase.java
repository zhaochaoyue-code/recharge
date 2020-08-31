package com.talkweb.unicom.recharge.bean.base;

import cn.csatv.common.db.bean.IDEntity;


/**
 * 基类不要手动添加代码，不然重复生成时会覆盖。如果要手动添加自定义的代码要加到子类里面
 * @author mybatis-generator
 */
public class MobileSmsBase extends IDEntity {
	
	private static final long serialVersionUID = 1L;

    /**
	 * 活动id
	*/
	private String activityId;
	
	/**
	 * 手机号
	*/
	private String mobile;
	
	/**
	 * 发送状态，1待发送，2发送成功，3发送失败，4发送异常
	*/
	private Integer sendStatus;
	
	/**
	 * 异常信息。
	*/
	private String exceptionMsg;
	
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
	
	public Integer getSendStatus() {
		return sendStatus;
	}
	
	public void setSendStatus(Integer sendStatus) {
		this.sendStatus = sendStatus;
	}
	
	public String getExceptionMsg() {
		return exceptionMsg;
	}
	
	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}
	
	

}