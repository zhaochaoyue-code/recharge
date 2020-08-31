package com.talkweb.unicom.recharge.bean;

import java.util.concurrent.Future;

import com.talkweb.unicom.order.bean.MailFeeStatus;
import com.talkweb.unicom.recharge.bean.base.MobileBase;

public class Mobile extends MobileBase {

/*	public Mobile(String uid, String mobile, String activityId, BigDecimal money) {
		this.setUid(uid);
		this.setMobile(mobile);
		this.setActivityId(activityId);
		this.setMoney(money);
	}
*/
	private static final long serialVersionUID = 1L;

	private Future<MailFeeStatus> futuremailFeeStatus;

	private MailFeeStatus mailFeeStatus;

	public Future<MailFeeStatus> getFuturemailFeeStatus() {
		return futuremailFeeStatus;
	}

	public void setFuturemailFeeStatus(Future<MailFeeStatus> futuremailFeeStatus) {
		this.futuremailFeeStatus = futuremailFeeStatus;
	}

	public MailFeeStatus getMailFeeStatus() {
		return mailFeeStatus;
	}

	public void setMailFeeStatus(MailFeeStatus mailFeeStatus) {
		this.mailFeeStatus = mailFeeStatus;
	}

}