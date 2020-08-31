package com.talkweb.unicom.recharge.scheduled;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Result {
    // 定义一个共享变量来实现通信，它需要是volatile修饰，否则线程不能及时感知 flag
    static volatile boolean flag = false;
//    static volatile AtomicBoolean flag = new AtomicBoolean(false);
    //开始人工点击，一旦超时数量超过限制则不能在点击
    static volatile boolean personFlag = false;

    static volatile boolean stopFlag = false;

    public static boolean isStopFlag() {
        return stopFlag;
    }

    public static void setStopFlag(boolean stopFlag) {
        Result.stopFlag = stopFlag;
    }

    public static boolean isPersonFlag() {
        return personFlag;
    }

    public static void setPersonFlag(boolean personFlag) {
        Result.personFlag = personFlag;
    }

    public static boolean isFlag() {
        return flag;
    }

    public static void setFlag(boolean flag) {
        Result.flag = flag;
    }

}
