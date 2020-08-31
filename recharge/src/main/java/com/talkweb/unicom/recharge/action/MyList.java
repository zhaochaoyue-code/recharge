package com.talkweb.unicom.recharge.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyList {
    private static List<String> list = new ArrayList<String>();

             public static void add() {
                 list.add("anyString");
             }

             public static int size() {
                 return list.size();
             }

    public static void main(String[] args) {

                 for(int i=0;i<30;i++) {
                     Random random = new Random();
                     int i1 =  new Random().nextInt(4)+1;
                        System.out.println(i1);
                 }

       /* List<String> list = new ArrayList<>();
        list.add("111");
        list.add("222");
        List<String> list1 = new ArrayList<>();
        list1.add("333");
        list.addAll(list1);
        list1.addAll(list);
        System.out.println(list);
        System.out.println(list1);*/
    }

}
