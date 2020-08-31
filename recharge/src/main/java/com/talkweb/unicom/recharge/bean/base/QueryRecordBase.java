package com.talkweb.unicom.recharge.bean.base;

import cn.csatv.common.db.bean.IDEntity;


/**
 * 基类不要手动添加代码，不然重复生成时会覆盖。如果要手动添加自定义的代码要加到子类里面
 * @author mybatis-generator
 */
public class QueryRecordBase extends IDEntity {
	
	private static final long serialVersionUID = 1L;

    /**
	 * 活动id
	*/
	private String activityId;
	
	/**
	 * 关联mobile表的id字段。
	*/
	private Long mobileId;
	
	/**
	 * 手机号。
	*/
	private String mobile;
	
	/**
	 * 调用科创充值状态查询接口返回的id。
	*/
	private String kcId;
	
	/**
	 * 调用科创充值状态查询接口返回的status。0：初始状态，1：充值中，2：充值成功，3：充值失败，4：充值超时
	*/
	private Integer kcStatus;
	
	/**
	 * 其它异常信息
	*/
	private String exceptionMsg;
	
	public String getActivityId() {
		return activityId;
	}
	
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	
	public Long getMobileId() {
		return mobileId;
	}
	
	public void setMobileId(Long mobileId) {
		this.mobileId = mobileId;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getKcId() {
		return kcId;
	}
	
	public void setKcId(String kcId) {
		this.kcId = kcId;
	}
	
	public Integer getKcStatus() {
		return kcStatus;
	}
	
	public void setKcStatus(Integer kcStatus) {
		this.kcStatus = kcStatus;
	}
	
	public String getExceptionMsg() {
		return exceptionMsg;
	}
	
	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}
	
	

}