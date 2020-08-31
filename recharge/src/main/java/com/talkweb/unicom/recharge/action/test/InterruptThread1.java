package com.talkweb.unicom.recharge.action.test;

public class InterruptThread1 extends Thread{
    public static void main(String[] args) {
        try {
            InterruptThread1 t = new InterruptThread1();
            t.start();
            Thread.sleep(200);
            t.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();
        for(int i = 0; i <= 10000; i++) {
            if(Thread.currentThread().isInterrupted()){
                break;
            }
            System.out.println("i=" + i);
        }
    }
}
