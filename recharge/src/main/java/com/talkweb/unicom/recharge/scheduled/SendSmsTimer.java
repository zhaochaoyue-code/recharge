package com.talkweb.unicom.recharge.scheduled;

import com.talkweb.unicom.recharge.bean.Mobile;
//import com.talkweb.unicom.recharge.service.RechargeServiceV5;
import com.talkweb.unicom.recharge.service.v6.RechargeServiceV6;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.List;

//@Component
public class SendSmsTimer {
    private static final Logger log = LoggerFactory.getLogger(SendSmsTimer.class);

    @Autowired
    private RechargeServiceV6 rechargeServiceV6;

    @Value("${rechargeConfig.startHour}")
    private int startHour;

    @Value("${rechargeConfig.startMinute}")
    private int startMinute;

    @Value("${rechargeConfig.startSecond}")
    private int startSecond;

    @Value("${rechargeConfig.endHour}")
    private int endHour;

    @Value("${rechargeConfig.endMinute}")
    private int endMinute;

    @Value("${rechargeConfig.endSecond}")
    private int endSecond;


    //早上八点到晚上七点内每半小时
//    @Scheduled(cron = "0 0/30 8-19 * * ?")
    @Scheduled(cron = "0/20 * * * * ?")

    public void sendSms() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        LocalTime time = LocalTime.now();
        LocalTime startTime = LocalTime.of(8,00,00);
        LocalTime endTime = LocalTime.of(19,00,00);
        if(time.isBefore(startTime) || time.isAfter(endTime)){
            log.info("当前时间不在发送短信的时间之内");
            return;
        }
        log.debug("开始执行发送短信定时任务");
        List<Mobile> allMobilesSendSms = rechargeServiceV6.getALLMobilesSendSms();
        for (Mobile m:
        allMobilesSendSms) {
            log.info("当前发送的手机号：{},开始时间是：{}",m,sdf.format(System.currentTimeMillis()));
            rechargeServiceV6.sms(m);
            log.info("当前发送的手机号：{},结束时间是：{}",m,sdf.format(System.currentTimeMillis()));
        }
        log.debug("开始执行发送短信定时任务结束");
    }
}
