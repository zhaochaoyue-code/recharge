package com.talkweb.unicom.recharge.action.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author tanglinsheng
 * @version 1.0
 * @date 2020/8/7 5:29 下午
 */
public class Test01 {

    public static final Logger logger = LoggerFactory.getLogger(Test01.class);

    private static AtomicInteger RecharclickThreadCount = new AtomicInteger(0);

    public static void main(String[] args) {
       threadStart();


        try {
            Thread.currentThread().sleep(2000);
            logger.info("线程启动完毕=============={}",concurrentHashMap.size());
            threadInterrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("测试结束================");

    }

    public static ConcurrentHashMap<String,Thread> concurrentHashMap = new ConcurrentHashMap(20);


    public static void threadStart(){
        for(int i=1;i<=20;i++){
            String name  = String.format("myThread_%d",i);
            Thread t = new MyThread(name);
            concurrentHashMap.put(name,t);
            t.start();
        }
    }

    public static void threadInterrupt(){
        while (true) {
            logger.info("concurrentHashMap.size()={}",concurrentHashMap.size());
            if(concurrentHashMap.size()==0){
                logger.info("concurrentHashMap.size()==0 结束线程");
                return;
            }



            for (Map.Entry<String, Thread> entry : concurrentHashMap.entrySet()) {
                Thread myThread = entry.getValue();
                int i = RecharclickThreadCount.incrementAndGet();

                    if (myThread.isAlive()) {

                        logger.info("错误数量{}，线程id={}，线程名称={},是否存在{}===============", i, myThread.getId(), myThread.getName(), myThread.isAlive());
//                        myThread.interrupt();
//                        logger.info("线程id={}，线程名称={},interrupt", myThread.getId(), myThread.getName());
//                    if(i>=10){
                            myThread.stop();
                            logger.info("线程id={}，线程名称={},stop", myThread.getId(), myThread.getName());
//                        }
//
                        concurrentHashMap.remove(entry.getKey(), myThread);
                    }


        }
            }
        }







}
