package com.talkweb.unicom.recharge.bean.base;

import cn.csatv.common.db.bean.IDEntity;


/**
 * 基类不要手动添加代码，不然重复生成时会覆盖。如果要手动添加自定义的代码要加到子类里面
 * @author mybatis-generator
 */
public class SmsRecordBase extends IDEntity {
	
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
	 * 发送内容。
	*/
	private String content;
	
	/**
	 * 调用短信发送接口返回的code。如果不为空且不为0，一般说明有异常。
	*/
	private String code;
	
	/**
	 * 调用短信发送接口返回的msg。
	*/
	private String msg;
	
	/**
	 * 发送状态，1成功，2失败
	*/
	private Integer result;
	
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
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public Integer getResult() {
		return result;
	}
	
	public void setResult(Integer result) {
		this.result = result;
	}
	
	

}