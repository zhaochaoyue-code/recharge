package com.talkweb.unicom.recharge.action;

public class Thread001 extends Thread{
    private volatile boolean flag=true;
    private final Object obj = new Object();
    @Override
    public void run() {
        while (flag){
//            System.out.println("ok");
        }
    }

    public  void stopThrea(){
        this.flag=false;
    }
    public static void main(String[] args) {
//        ThreadTest threadTest = new ThreadTest();
//        ThreadA threadA = threadTest.new ThreadA();
//        threadA.start();
//        ThreadB threadB = threadTest.new ThreadB();
//        threadB.start();
        /*Thread001 thread001 =new Thread001();
        thread001.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        thread001.stop();
        thread001.stopThrea();*/
        /*Object o = new Object();
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (o) {
                    try {
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();*/

        /*class ThreadA extends Thread {
            @Override
            public void run() {
                synchronized (obj) {
                    for (int i = 1; i <= 100; i += 2) {
                        obj.notify();
                        System.out.println(i); // 奇数
                        try {
                            obj.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }*/
    }
}
