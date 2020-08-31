package com.talkweb.unicom.recharge.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaochaoyue
 * @date 2020年7月31日 11点30分
 * 0 - 初始状态
 * 1 - 充值中
 * 2 - 充值成功
 * 3 - 充值失败
 * 4 - 充值超时
 */
public enum OrderStatusEnums {
    INITSTATUS(0,"初始状态"),
    RECHARGE(1,"充值中"),
    SUCCESS(2,"充值成功"),
    FAIL(3,"充值失败"),
    CLOSE(4,"充值超时"),
    ;
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    OrderStatusEnums(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public  static Map<Integer,OrderStatusEnums> maps = new HashMap<>();

    static {
        for (OrderStatusEnums orderStatusEnums:
        OrderStatusEnums.values()) {
            maps.put(orderStatusEnums.getId(),orderStatusEnums);
        }
    }
}
