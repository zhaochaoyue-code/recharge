package com.talkweb.unicom.recharge.action.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class Test3 {
    public static int count = 0;
    public static Counter counter = new Counter();
    public static AtomicInteger atomicInteger = new AtomicInteger(0);
    volatile public static int countVolatile = 0;

    public static void main(String[] args) {
        final CountDownLatch cdl = new CountDownLatch(10);
        for(int i=0; i<10; i++) {
            new Thread() {
                @Override
                public void run() {
                    for (int j = 0; j < 1000; j++) {
                        count++;
                        counter.increment();
                        for(int i=0;i<10;i++){
                            for (int t=0;t<5;t++){
//                                atomicInteger.getAndIncrement();
                        if(atomicInteger.getAndIncrement()>490000){
                            System.out.println("停止");
                        }
//                                atomicInteger.getAndIncrement();
                            }
                        }
                        countVolatile++;
                    }
                    cdl.countDown();
                }
            }.start();
        }
        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("static count: " + count);
        System.out.println("Counter: " + counter.getValue());
        System.out.println("AtomicInteger: " + atomicInteger.intValue());
        System.out.println("countVolatile: " + countVolatile);
    }



}
class Counter {

    private int value;

    public synchronized int getValue() {
        return value;
    }

    public synchronized int increment() {
        return value++;
    }

    public synchronized int decrement() {
        return --value;
    }
}
