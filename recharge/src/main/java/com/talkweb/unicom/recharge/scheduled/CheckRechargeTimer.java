package com.talkweb.unicom.recharge.scheduled;

import cn.csatv.common.cache.CacheService;
//import com.talkweb.unicom.recharge.service.RechargeServiceV5;
import cn.csatv.common.exception.BusinessException;
import cn.csatv.common.utils.StringMap;
import com.talkweb.unicom.recharge.bean.Mobile;
import com.talkweb.unicom.recharge.dao.MobileDao;
import com.talkweb.unicom.recharge.service.v5.RechargeServiceV5;
import com.talkweb.unicom.recharge.service.v6.RechargeServiceV6;
import com.talkweb.unicom.recharge.utils.SmsKCUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class CheckRechargeTimer {
    private static final Logger log = LoggerFactory.getLogger(CheckRechargeTimer.class);

    static List<String> mobiles = new ArrayList<>();

    static {
        mobiles.add("15611386054");
        mobiles.add("18513086625");
    }

//    @Autowired
//    private RechargeServiceV5 rechargeServiceV5;

//    private AtomicInteger RecharTimerThreadCount = new AtomicInteger(0);

    @Autowired
    private CacheService cacheService;

    @Autowired
    private MobileDao mobileDao;

    @Autowired
    RechargeServiceV5 rechargeServiceV5;

    @Autowired
    RechargeServiceV6 rechargeServiceV6;

    @Autowired
    SmsKCUtil smsKCUtil;


    @Value("${rechargeConfig.maxRechargeErrorCount}")
    private int maxRechargeErrorCount;

    //每五分钟执行一次
//    @Scheduled(cron = "0 */5 * * * ?")
    @Scheduled(cron = "0/20 * * * * ?")
    public void sendRecharge() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Integer totalErrorCount = cacheService.get("totalErrorCount", Integer.class);
		int totalCount = null != totalErrorCount ? totalErrorCount.intValue() : 0;
		log.info("定时器获取的值{}",totalCount);

        List<Mobile> mobiles = mobileDao.getAllMobilesToRecharge(1);
        log.info("线程结束标志{}",Result.isFlag());


        if(!Result.isPersonFlag()){
            log.info("还没手动点击，定时器不需要执行");
            return;
        }

        if(!Result.isFlag()){
            log.info("未出现异常不必启动定时器");
            return;
        }

        if(Result.isStopFlag()){
            log.info("手动停止之后，定时器应该停止");
            return;
        }

        if(totalCount>=maxRechargeErrorCount){
            //为了可以再次手动启动， 还原错误数量为0
            Result.setPersonFlag(false);
            cacheService.set("totalErrorCount",0);
            //发邮件/短信
            log.info("【中国联通】充值程序异常数量已经超过{}个,请处理异常充值订单. 时间是：{}",maxRechargeErrorCount,sdf.format(System.currentTimeMillis()));
//            sendRegchargeExceptionSms();
            return;
        }
        log.info("准备充值,手动标记应该是true===实际是{},未出现异常标记应该是true -----实际是{},停止标志应该是false ****实际是{},数量应该不大于30///// 实际是{}",
                Result.isPersonFlag(),Result.isFlag(),Result.isStopFlag(),totalCount);
//        rechargeServiceV6.startRechargeThread6(mobiles);
        rechargeServiceV6.startRechargeThread6(mobiles, null);

    }


    public void sendRegchargeExceptionSms() {
        //短信内容
        String content = "【中国联通】充值程序异常数量已经超过【"+maxRechargeErrorCount+"】个,请处理异常充值订单,程序停止时间是:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
        List<String> mobilesSend=mobiles;
        if(mobilesSend!=null && mobilesSend.size()>0) {
            for(String mobile : mobilesSend) {
                smsKCUtil.sendSplitContent(mobile, content,1000);
            }
        }
    }

}
