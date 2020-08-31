package com.talkweb.unicom.recharge.action;

public class ThreadA extends Thread {
    private Object lock;

    public ThreadA(Object lock) {
        super();
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
                         synchronized (lock) {
                                 if (MyList.size() != 5) {
//                                     String name = Thread.currentThread().getName();
//                                     long id = Thread.currentThread().getId();
//                                     System.out.println("线程名"+name+"\r\n"+"线程id"+id);
                                     System.out.println("wait begin "
                                                         + System.currentTimeMillis());
                                         lock.wait();
                                         System.out.println("wait end  "
                                                         + System.currentTimeMillis());
                                     }
                            }
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }
    }
}
