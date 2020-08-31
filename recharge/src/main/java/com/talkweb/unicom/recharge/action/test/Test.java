package com.talkweb.unicom.recharge.action.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Test {

    private static final Logger log = LoggerFactory.getLogger(Test.class);

    private static AtomicInteger rcharErrThreadCount = new AtomicInteger(0);
    private static AtomicBoolean isInterupt = new AtomicBoolean(false);
    public static volatile  boolean flag=true;

    public static boolean isFlag() {
        return flag;
    }

    public static void setFlag(boolean flag) {
        Test.flag = flag;
    }

    public static void main(String[] args) {
        System.out.println("***************");
        for (int i = 1; i <= 20; i++) {
            int t=i;
            if(isInterupt.get()){
                break;
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    startRechargeThread(t);
                }
            }).start();
            System.out.println("我休息了吗"+System.currentTimeMillis());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("我没休息"+System.currentTimeMillis());
            System.out.println("+++++++++++++++++");
        }
        System.out.println("================");
    }

    private static void startRechargeThread(int j) {
        System.out.println("我是第"+j+"个线程开始");
//        for(int t=0;t<3;t++) {
            while (flag){
            for (int i = 0; i < 30; i++) {
//            System.out.println("aaaaaaaa");
                real(j, i);
                if (isInterupt.get()) {
                    break;
                }
            }
            if (isInterupt.get()) {
                break;
            }
        }
        System.out.println("我是第"+j+"个线程结束");
    }

    private static void real(int t,int n) {
        try{
            log.info("第{}个线程，第{}次循环",t,n);
//            System.out.println("第"+n+"次循环==========我是第"+t+"个新查");
            int m =10/0;
        }catch (Throwable e){
            int i1 = rcharErrThreadCount.incrementAndGet();

            if(i1>=100){
                isInterupt.set(true);
                setFlag(false);
                System.out.println("超了"+"数量是:"+i1+"我是第"+t+"个线程");
            }else{
                isInterupt.set(false);
            }
        }
    }

    /*static class MyThread extends Thread{
        @Override
        public void run(){
            for(int i=1;i<=100;i++){
                *//*try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*//*
                System.out.println(i);
            }
        }
    }*/
}
