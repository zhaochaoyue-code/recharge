package com.talkweb.unicom.recharge.dictionary;

import java.util.HashMap;
import java.util.Map;

/**处理状态
 * @author zhaochaoyue
 * @date 2020年7月31日 11点40分
 * 0待充值
 * 1发送充值处理中
 * 2已发送充值待查询状态
 * 3发送状态查询处理中
 * 4已充值成功待发送短信
 * 5获取充值结果异常
 * 6获取查询状态异常
 * 7提交充值异常
 * 8提交查询状态异常
 * 9短信发送处理中
 * 10短信发送成功
 * 11发送短信异常
 * 12充值失败(调接口返回)
 * 13充值超时(调接口返回)
 */
public enum RechargeProcessStatusEnums {
    WAITING(0,"待充值"),
    SENDRECHAGRE(1,"发送充值处理中"),
    RECHARGEQUERY(2,"已发送充值待查询状态"),
    SENDQUERY(3,"发送状态查询处理中"),
    RECHAGRESUCCESSSENDSMS(4,"已充值成功待发送短信"),
    RECHAGRERESULTERR(5,"获取充值结果异常"),
    RECHARGESTATUSERR(6,"获取查询状态异常"),
    SUBMITRECHARGEERR(7,"提交充值异常"),
    SUBMITQUERYSTATUSERR(8,"提交查询状态异常"),
    SMSSENDING(9,"短信发送处理中"),
    SMSSENDSUCCESS(10,"短信发送成功"),
    SENDSMSERR(11,"发送短信异常"),
    RECHAGREERR(12,"充值失败(调接口返回)"),
    RECHARGETIMEOUT(13,"充值超时(调接口返回)")
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

    RechargeProcessStatusEnums(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Map<Integer,RechargeProcessStatusEnums> maps =new HashMap<>();

    static {
        for (RechargeProcessStatusEnums rechargeProcessStatusEnums:
        RechargeProcessStatusEnums.values()) {
            maps.put(rechargeProcessStatusEnums.getId(),rechargeProcessStatusEnums);
        }
    }
}
