package com.talkweb.unicom.recharge.action.test;

import java.util.concurrent.atomic.AtomicInteger;

public class Test1 {
    private static AtomicInteger rcharErrThreadCount = new AtomicInteger(0);
    public static void main(String[] args) {
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                Thread.currentThread().setName("我是1");
                String name = Thread.currentThread().getName();
                System.out.println(name);
                int i = rcharErrThreadCount.incrementAndGet();
                System.out.println("11111111==========="+i);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Thread.currentThread().setName("我是2");
                String name = Thread.currentThread().getName();
                System.out.println(name);
                int i = rcharErrThreadCount.incrementAndGet();
                System.out.println("2222222============"+i);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Thread.currentThread().setName("我是3");
                String name = Thread.currentThread().getName();
                System.out.println(name);
                int i = rcharErrThreadCount.incrementAndGet();
                System.out.println("333333============="+i);
            }
        }).start();*/

        for(int i=1;i<=3;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int i = rcharErrThreadCount.incrementAndGet();
                    System.out.println("333333============="+i);
                }
            }).start();
        }
    }
}
