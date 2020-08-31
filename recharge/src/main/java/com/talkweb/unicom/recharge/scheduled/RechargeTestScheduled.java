package com.talkweb.unicom.recharge.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.talkweb.unicom.recharge.service.RechargeServiceTest;

/**
 * 程序启动时把手机号充值和充值状态查询线程都启动。充值线程运行完后会自己退出。然后每周一启动运行一次充值线程。状态查询线程会一直在运行。
 *
 * @author zouxixi
 * @create 2019/03/08.
 */
//@Component
public class RechargeTestScheduled implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(RechargeTestScheduled.class);

	@Autowired
	private RechargeServiceTest rechargeServiceTest;

	@Override
	public void run(String... args) throws Exception {
		log.info("系统启动时启动处理线程*************************************");
		try {
			//rechargeServiceTest.rechargeOne();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}