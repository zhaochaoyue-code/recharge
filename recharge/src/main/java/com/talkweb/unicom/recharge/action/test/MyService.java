package com.talkweb.unicom.recharge.action.test;

import java.util.concurrent.Semaphore;

public class MyService {
    private static  Semaphore semaphore =new Semaphore(3);

    public static void main(String[] args) {
//        test();
        try {
            semaphore.acquire();
            System.out.println(Thread.currentThread().getName()+" 开始时间："+System.currentTimeMillis());
            for(int i = 0; i < 10; i++){
                System.out.println(Thread.currentThread().getName()+"打印"+ (i+1)+"次");
            }
            System.out.println(Thread.currentThread().getName()+" 结束时间： "+System.currentTimeMillis());
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

   /* public static  void test(){

    }*/

}
