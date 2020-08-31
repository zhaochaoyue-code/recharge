package com.talkweb.unicom.recharge;

import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jodd.http.Cookie;

public abstract class ApplicationWebTest {

	protected Logger logger = LoggerFactory.getLogger(ApplicationWebTest.class);

	protected static String host = "http://localhost";
	protected static String port = "10801";
	protected static String serverName = host + ":" + port;
	protected static String context_path = "/";
	protected static String url_prefix = serverName + context_path;

	protected static Cookie[] cookies;// 保存登录成功后的cookies

	@Before
	public void before() {
		logger.info("begin------------------------------------------------------------------------------");
	}

	@After
	public void after() {
		logger.info("end---------------------------------------------------------------------------------");
	}

}
