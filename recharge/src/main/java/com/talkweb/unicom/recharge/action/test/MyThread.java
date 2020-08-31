package com.talkweb.unicom.recharge.action.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author tanglinsheng
 * @version 1.0
 * @date 2020/8/9 12:16 下午
 */
public class MyThread extends Thread {

    public static final Logger logger = LoggerFactory.getLogger(MyThread.class);

    private static AtomicInteger RecharclickThreadCount = new AtomicInteger(0);

    public MyThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        int i = RecharclickThreadCount.incrementAndGet();
        logger.info("到几了{}",i);
        while (!Thread.currentThread().isInterrupted()) {
            try {
                logger.info("线程id={}，线程名称={},休眠1s", currentThread().getId(), currentThread().getName());
             //   Thread.sleep(1000);
                logger.info("线程id={}，线程名称={},休眠结束", currentThread().getId(), currentThread().getName());
            } catch (Exception e) {
                logger.error("error", e);
            }
        }

        /*while (true){
            try {
                  Thread.sleep(1000);
                logger.info("线程id={}，线程名称={},isAlive={},isInterrupted={},isDaemon={},getState={}", currentThread().getId(), currentThread().getName(),currentThread().isAlive(),currentThread().isInterrupted(),currentThread().isDaemon(),currentThread().getState());
            } catch (Exception e) {
                logger.error("error", e);
            }
        }*/
    }
}
