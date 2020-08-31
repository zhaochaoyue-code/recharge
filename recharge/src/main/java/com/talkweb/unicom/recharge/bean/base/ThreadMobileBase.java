package com.talkweb.unicom.recharge.bean.base;

import cn.csatv.common.db.bean.IDEntity;


/**
 * 基类不要手动添加代码，不然重复生成时会覆盖。如果要手动添加自定义的代码要加到子类里面
 * @author mybatis-generator
 */
public class ThreadMobileBase extends IDEntity {
	
	private static final long serialVersionUID = 1L;

    /**
	 * 线程名称
	*/
	private String threadName;
	
	/**
	 * uuid
	*/
	private String uid;
	
	/**
	 * 手机号
	*/
	private String mobile;
	
	public String getThreadName() {
		return threadName;
	}
	
	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}
	
	public String getUid() {
		return uid;
	}
	
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	

}