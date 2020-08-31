package com.talkweb.unicom.recharge.action.test;

public class MyRunnable implements Runnable {
    public   volatile  boolean flag= true;
    @Override
    public void run() {
        System.out.println("第" + Thread.currentThread().getName() + "个线程创建");

        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //退出标志生效位置
        while (flag) {
        }
        System.out.println("第" + Thread.currentThread().getName() + "个线程终止");
    }
}
