package com.talkweb.unicom.recharge.dao;

import cn.csatv.common.db.dao.BaseDao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.talkweb.unicom.recharge.bean.Mobile;

/**
 * 手机号数据库操作
 * 
 * @author 40642
 * @create 2019-03-07 11:36:57
 */
public interface MobileDao extends BaseDao<Mobile> {

	/**
	 * 获取一些还需要充值的手机号。
	 */
	List<Mobile> getSomeMobilesToRecharge(@Param("maxRechargeRretryCount") int maxRechargeRretryCount,
			@Param("limitCount") int limitCount);

	/**
	 * 获取一些查询充值的手机号。
	 */
	List<Mobile> getSomeMobilesToQueryStatus(@Param("maxQueryRretryCount") int maxQueryRretryCount,
			@Param("limitCount") int limitCount);

	/**
	 * 获取一些还需要发送短信的手机号。
	 */
	List<Mobile> getSomeMobilesToSendSms(@Param("maxSmsRetryCount") int maxQueryRretryCount,
			@Param("limitCount") int limitCount);

	/**
	 * 把发送充值中状态的手机号改为待充值状态。
	 */
	void updateProcessStatus0();

	/**
	 * 把发送状态查询中状态的手机号改为已发送充值待查询状态。
	 */
	void updateProcessStatus2();

	/**
	 * 把短信发送中状态的手机号改为已状态查询充值成功待发送短信状态。
	 */
	void updateProcessStatus4();

	/**
	 * 5条充值记录都充值成功才需要发送短信通知。
	 */
	List<String> getAllMobileToSendMsg();

	List<String> findAllMobiles();
	List<Mobile> findAllMobilesList();

	void updateProcessStatusFromOrder(@Param("id") String id,@Param("mobile") String mobile,@Param("status") int status);

	Mobile findByUid(@Param("uid") String uid);

	void  deleteByUid(@Param("uid") String uid);

	/**
	 * 获取一些还需要发送短信的手机号。
	 */
	List<Mobile> getSomeMobilesByActivityIdToSendSms(@Param("maxSmsRetryCount") int maxQueryRretryCount,
										 @Param("limitCount") int limitCount,@Param("activityid") String activityid);

	Mobile selectOne();
	List<Mobile> getALLMobiles(@Param("maxSmsRetryCount") int maxQueryRretryCount);
    ////把漏掉的手机号更新为初始,为了下次可以充值上
	void updateProcessStatus0ForNext(@Param("mobileUids") List<String> mobileUids);


	List<Mobile> getAllMobilesToRecharge(@Param("maxRechargeRretryCount") int maxRechargeRretryCount);
}
