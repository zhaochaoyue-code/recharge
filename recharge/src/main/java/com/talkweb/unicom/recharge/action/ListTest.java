package com.talkweb.unicom.recharge.action;

import java.util.ArrayList;
import java.util.List;

public class ListTest {
    public static void main(String[] args) {

        for(int i=0;i<10;i++){
            for (int j=0;j<5;j++){
                if(j==3){
                    break;
                }
            }
            if(i==6){
                break;
            }
        }

        List<String> list = new ArrayList<>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        list.add("ddd");
        list.add("eee");
        List<String> newList = new ArrayList<>();
        /*for(int i=0;i<list.size();i++){
            String s = list.get(i);
            if(!s.equals("ccc")){
                newList.add(list.get(i));
            }
        }*/
        for (String s:
        list) {
            if(!s.equals("ccc")){
                newList.add(s);
            }
        }

        /*for (String s:
             list) {
            if(s.equals("ccc")){
                list.remove("ccc");
            }
        }*/
        System.out.println(list+"------------原来");
        System.out.println(newList+"=============新的");
    }
}
