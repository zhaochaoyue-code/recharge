package com.talkweb.unicom.recharge.action.test;

import java.util.concurrent.atomic.AtomicInteger;

public class Test2 {

    private static AtomicInteger RecharclickThreadCount = new AtomicInteger(0);
    public static void main(String[] args) throws InterruptedException {
        MyRunnable runnable = new MyRunnable();

        int a=0;
        //创建4个线程
        for (int i = 0; i < 4; i++) {
            Thread thread=new Thread(runnable,i+"   ");
            thread.start();
            thread.join();
            for (int j =0;j<30;j++){
                a = RecharclickThreadCount.incrementAndGet();
            }
            System.out.println(a+"===============");
        }
        //线程休眠
        Thread.sleep(2000L);
        System.out.println("——————————————————————————");
        //修改退出标志，使线程终止
        if(a>100){
        runnable.flag = false;

        }
    }
}
