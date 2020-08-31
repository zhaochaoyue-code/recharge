package com.talkweb.unicom.recharge.action;

public class Test extends Thread{
    @Override
    public void run() {
        for(int i=0;i<10;i++){
            System.out.println(i+"============");
            if(i==5){
                boolean interrupted = Thread.interrupted();
                System.out.println("是否中断"+interrupted);
            }
        }
        /*while (true){

        }*/
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread =new Thread();
        thread.interrupt();
        boolean interrupted = Thread.interrupted();
        System.out.println("是否中断main"+interrupted);
//        System.out.println("停一会儿");
//        Thread.sleep(2000);
//        System.out.println("不停了");
////        thread.interrupt();
//        System.out.println("怎么了");
        /*try {
                         Object lock = new Object();

                         ThreadA a = new ThreadA(lock);
                         a.start();

                         Thread.sleep(50);

                         ThreadB b = new ThreadB(lock);
                         b.start();
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }*/
    }
}
