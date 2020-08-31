package com.talkweb.unicom.recharge.action.test;

import java.util.ArrayList;
import java.util.List;

public class Test4 {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        list.add("ddd");

        int index =1;
        for (String s:
             list) {
            int b =index++;
            System.out.println(b);
        }
    }
}
