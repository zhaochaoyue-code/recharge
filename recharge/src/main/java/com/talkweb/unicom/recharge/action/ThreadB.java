package com.talkweb.unicom.recharge.action;

public class ThreadB extends  Thread{
    private Object lock;

    public ThreadB(Object lock) {
        super();
        this.lock = lock;
    }
    @Override
    public void run() {
        try {
                         synchronized (lock) {
                                 for (int i = 0; i < 10; i++) {
//                                     String name = Thread.currentThread().getName();
//                                     long id = Thread.currentThread().getId();
//                                     System.out.println("线程名"+name+"\r\n"+"线程id"+id);
                                         MyList.add();
                                        if (MyList.size() == 3) {
                                                 lock.notify();
                                                System.out.println("已经发出了通知");
                                             }
                                         System.out.println("添加了" + (i + 1) + "个元素!");
                                         Thread.sleep(1000);
                                     }
                             }
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }
    }
}
