package com.talkweb.unicom.recharge.action.test;

import java.util.concurrent.atomic.AtomicInteger;

public class Test02 {
    //定义初始票数
//    public static int chepiao = 20;
    private static AtomicInteger chepiao = new AtomicInteger(20);


    public static void main(String[] args) {

        Test02 t = new Test02();
        //匿名类创建线程
        Thread t1 = new Thread() {
            @Override
            public void run() {
//加同步锁
                synchronized(Test02.class) {
//卖完就停止
                    if(chepiao.get()<=0) {
                        return;
                    }
                    try {
                        t.jianfa();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    super.run();
                }
            }

        };

//启动30个线程
        for(int i = 1;i<20;i++) {
            new Thread(t1).start();


        }
    }

    //票数-1
    public synchronized   void jianfa() throws InterruptedException {
//        chepiao--;
        chepiao.decrementAndGet();
        System.out.println("线程: "+Thread.currentThread().getName()+"，抢到1张票，剩余"+chepiao+"张！");

    }
}
