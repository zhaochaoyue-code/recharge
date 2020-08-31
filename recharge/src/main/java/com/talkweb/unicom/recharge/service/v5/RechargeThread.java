package com.talkweb.unicom.recharge.service.v5;

import cn.csatv.common.utils.JsonUtils;
import com.talkweb.unicom.order.bean.MailFeeStatus;
import com.talkweb.unicom.order.client.FeeClient;
import com.talkweb.unicom.recharge.bean.Mobile;
import com.talkweb.unicom.recharge.bean.RechargeRecord;
import com.talkweb.unicom.recharge.bean.ThreadMobile;
import com.talkweb.unicom.recharge.dao.MobileDao;
import com.talkweb.unicom.recharge.dao.RechargeRecordDao;
import com.talkweb.unicom.recharge.dao.ThreadMobileDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author tanglinsheng
 * @version 1.0
 * @date 2020/8/9 1:51 下午
 */

public class RechargeThread extends Thread{

    public static final Logger logger = LoggerFactory.getLogger(RechargeThread.class);

    // 调用充值接口失败时最大重试次数
    private final int maxRechargeRretryCount = 1;

    // 每次从数据库拿多少条手机号来充值
    private final int rechargeSize = 30;

    private FeeClient feeClient;

    private MobileDao mobileDao;

    private ThreadMobileDao threadMobileDao;

    private RechargeRecordDao rechargeRecordDao;

    public RechargeThread(FeeClient feeClient, MobileDao mobileDao, ThreadMobileDao threadMobileDao, RechargeRecordDao rechargeRecordDao) {
        this.feeClient = feeClient;
        this.mobileDao = mobileDao;
        this.threadMobileDao = threadMobileDao;
        this.rechargeRecordDao = rechargeRecordDao;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            logger.info("启动充值线程：" + Thread.currentThread().getName() + "*************************");
            List<Mobile> mobiles = getSomeMobilesToRecharge();
            while (!mobiles.isEmpty() && !Thread.currentThread().isInterrupted()) {
                for (Mobile m : mobiles) {
                    logger.debug("name={} ,mobile={} start ...",Thread.currentThread().getName(),m.getMobile());
                    recharge(m,RechargeServiceV5.tryErrorCount);
                    logger.debug("name={} ,mobile={} end ...",Thread.currentThread().getName(),m.getMobile());
                    if(!Thread.currentThread().isInterrupted()){
                        break;
                    }
                }
                if(!Thread.currentThread().isInterrupted()){
                    break;
                }
                mobiles = getSomeMobilesToRecharge();
                try {
                    Thread.sleep(5L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            logger.info("充值线程运行结束:" + Thread.currentThread().getName() + "*************************");
        }
    }


    private synchronized List<Mobile> getSomeMobilesToRecharge() {
        synchronized (RechargeServiceV5.class) {
            List<Mobile> mobiles = mobileDao.getSomeMobilesToRecharge(maxRechargeRretryCount, rechargeSize);
            String ids = mobiles.stream().map(Mobile::getMobile).collect(Collectors.joining(",", "", ""));
            logger.info("从数据库获取了一批手机号去充值，本次获取个数={},ids={}", mobiles.size(), ids);
            for (Mobile m : mobiles) {
                m.setProcessStatus(1);// 0待充值，1发送充值中，2已发送充值待查询状态，3发送状态查询中，4已状态查询充值成功待发送短信。5充值getFuture异常，6查询状态getFuture异常，7提交充值异常，8提交查询状态异常，9短信发送处理中，10短信发送成功。
                mobileDao.update(m);
            }
            logger.info("本次获取个数={},状态更新完毕", mobiles.size());
            return mobiles;
        }
    }

    /**
     * 对单个手机号调用充值接口提交充值。
     */
    private void recharge(Mobile m, AtomicInteger tryErrorCount) {
        saveThreadMobile(Thread.currentThread().getName(), m);
        Future<MailFeeStatus> f = null;
        try {
//            f = feeClient.recharge(m.getMobile(), m.getMoney().intValue());
//            f = feeClient.recharge(m.getMobile(), m.getMoney().intValue(), m.getActivityId(), m.getUid());
            f = new FutureTask<MailFeeStatus>(new Callable() {
                @Override
                public MailFeeStatus call() throws Exception {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    MailFeeStatus mailFeeStatus = new MailFeeStatus();
                    mailFeeStatus.setStatus(200);
                    return mailFeeStatus;
                }
            });
            int i  = 10/0;
        } catch (Throwable e1) {
            m.setProcessStatus(7);
            m.setRechargeExceptionMsg(e1.getMessage());
            mobileDao.update(m);
            deleteThreadMobile(m);
            logger.error(
                    "手机号 " + m.getMobile() + "提交充值异常，返回状态:" + JsonUtils.toJson(e1) + "***************************");
            if(tryErrorCount.incrementAndGet()>=50){
                logger.debug("单次数达到10次，停止线程，稍后再试");
                RechargeServiceV5.stopRechargeForThread();
                logger.debug("需要把状态为1的改成0===============");
            }
            return;
        }

        MailFeeStatus mfs = null;
        try {
            mfs = f.get();

            if (mfs.getStatus() == 1) {// 充值中
                m.setProcessStatus(2);// 已发送充值待查询状态
            } else if (mfs.getStatus() == 2) {// 充值成功，// 如果这里就返回充值成功，则直接进入短信发送环节，不用再去查询充值状态了。
                m.setProcessStatus(4);
                m.setKcQueryId(mfs.getId());
                m.setKcQueryStatus(mfs.getStatus());
                m.setLastQueryTime(new Date());
            } else if (mfs.getStatus() == 3) {// 充值失败
                m.setIsFinished(1);// 已经完成
                m.setProcessStatus(12);
            } else if (mfs.getStatus() == 4) {// 充值超时
                m.setIsFinished(1);// 已经完成
                m.setProcessStatus(13);
            }
            // 0待充值，1发送充值中，2已发送充值待查询状态，3发送状态查询中，4已状态查询充值成功待发送短信。5充值getFuture异常，6查询状态getFuture异常，7提交充值异常，8提交查询状态异常，9短信发送处理中，10短信发送成功。
            m.setKcRechargeId(mfs.getId());
            m.setKcRechargeStatus(mfs.getStatus());

            logger.info("手机号 " + m.getMobile() + "充值正常，返回状态:"
                    + JsonUtils.toJson("id=" + mfs.getId() + ",status=" + mfs.getStatus())
                    + "***************************");
        } catch (Throwable e2) {
            logger.error("手机号 " + m.getMobile() + "充值异常，异常信息:" + JsonUtils.toJson(e2) + "***************************");
            m.setProcessStatus(5);
            m.setRechargeExceptionMsg(e2.getMessage());
        }

        m.setLastRechargTime(new Date());
        m.setRechargeRetryCount(m.getRechargeRetryCount() + 1);

        mobileDao.update(m);
        deleteThreadMobile(m);

        saveRechargeRecord(m, mfs);// 保存充值记录
    }

    /**
     * 保存充值线程正在处理的手机号记录
     */
    private void saveThreadMobile(String threadName, Mobile m) {
        ThreadMobile threadMobile = new ThreadMobile();
        threadMobile.setId(m.getId());
        threadMobile.setThreadName(threadName);
        threadMobile.setUid(m.getUid());
        threadMobile.setMobile(m.getMobile());
        threadMobileDao.insert(threadMobile);
    }

    /**
     * 删除充值线程正在处理的手机号记录
     */
    private void deleteThreadMobile(Mobile m) {
        threadMobileDao.removeById(m.getId());
    }

    /**
     * 保存充值记录
     */
    private void saveRechargeRecord(Mobile m, MailFeeStatus msf) {
        try {
            RechargeRecord r = new RechargeRecord();
            r.setMobile(m.getMobile());
            r.setMobileId(m.getId());
            r.setActivityId(m.getActivityId());
            if (msf == null) {
                r.setExceptionMsg(m.getRechargeExceptionMsg());
            } else {
                r.setKcId(msf.getId());
                r.setKcStatus(msf.getStatus());
            }
            rechargeRecordDao.insert(r);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }
