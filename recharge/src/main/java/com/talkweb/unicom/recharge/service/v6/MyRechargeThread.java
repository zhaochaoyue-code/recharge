package com.talkweb.unicom.recharge.service.v6;

import cn.csatv.common.cache.CacheService;
import cn.csatv.common.utils.JsonUtils;
import com.talkweb.unicom.order.bean.MailFeeStatus;
import com.talkweb.unicom.order.client.FeeClient;
import com.talkweb.unicom.recharge.bean.Mobile;
import com.talkweb.unicom.recharge.bean.ThreadMobile;
import com.talkweb.unicom.recharge.dao.MobileDao;
import com.talkweb.unicom.recharge.dao.RechargeRecordDao;
import com.talkweb.unicom.recharge.dao.ThreadMobileDao;
import com.talkweb.unicom.recharge.scheduled.Result;
import com.talkweb.unicom.recharge.service.v5.RechargeThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class MyRechargeThread extends Thread{

    public static final Logger logger = LoggerFactory.getLogger(RechargeThread.class);

    // 调用充值接口失败时最大重试次数
    private final int maxRechargeRretryCount = 1;

    // 每次从数据库拿多少条手机号来充值
    private final int rechargeSize = 30;

    private FeeClient feeClient;

    private MobileDao mobileDao;

    private ThreadMobileDao threadMobileDao;

    private RechargeRecordDao rechargeRecordDao;

    private CacheService cacheService;

    private AtomicBoolean isInterupt = new AtomicBoolean(false);
    private AtomicInteger rcharErrThreadCount = new AtomicInteger(0);

    public MyRechargeThread(FeeClient feeClient, MobileDao mobileDao, ThreadMobileDao threadMobileDao, RechargeRecordDao rechargeRecordDao,CacheService cacheService) {
        this.feeClient = feeClient;
        this.mobileDao = mobileDao;
        this.threadMobileDao = threadMobileDao;
        this.rechargeRecordDao = rechargeRecordDao;
        this.cacheService=cacheService;
    }

    @Override
    public void run() {
        List<Mobile> list = mobileDao.findAllMobilesList();
        List<Mobile> mobiles = mobileDao.getAllMobilesToRecharge(1);
        startRechargeThread(mobiles, 0, list);
    }

    public  void
    startRechargeThread(List<Mobile> mobiles, int t, List<Mobile> lsit) {
        logger.info("启动充值线程id：{},线程名称:{} *************************",Thread.currentThread().getId(),Thread.currentThread().getName());
//		ReentrantLock lock=new ReentrantLock();
//		List<Mobile> mobilesList = getSomeMobilesToRecharge(t);
        List<Mobile> mobilesList = mobileDao.getAllMobilesToRecharge(maxRechargeRretryCount);
//		Queue<Mobile> queue = new ConcurrentLinkedQueue(mobiles);
//	while (!mobilesList.isEmpty() && !isInterupt.get()) {
		/*for (int i=0;i<=10;i++){
//            System.out.println("aaaaaaaa");
//			recharge(null,mobiles);
			real();
			if(isInterupt.get()){
				break;
			}
		}*/
//加lock lock
//			lock.lock();

        if(mobiles!=null) {
            for (Mobile m : mobilesList) {

                recharge(m, mobilesList, t);
//				real();
                if (isInterupt.get()) {
                    rcharErrThreadCount.set(0);
                    break;
                }

            }
        }else{
            for (Mobile m : mobiles) {

                recharge(m, mobiles, t);
//				real();
				/*if (isInterupt.get()) {
					rcharErrThreadCount.set(0);
					break;
				}*/

            }
        }

		/*if (isInterupt.get()) {
			break;
		}

//		mobilesList = getSomeMobilesToRecharge(t);
		if(!isInterupt.get()){
			mobilesList = getSomeMobilesToRecharge(t);
		}

		//释放lock
//			if(!isInterupt.get()){
//
//			}

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*//*finally {
				lock.unlock();
			}*//*
}*/
        logger.info("充值线程运行结束id：{},线程名称:{} *************************",Thread.currentThread().getId(),Thread.currentThread().getName());
    }

    public void recharge(Mobile m,List<Mobile> mobiles,int t) {
        saveThreadMobile(Thread.currentThread().getName(), m);
        Future<MailFeeStatus> f = null;

        try {
//			f = feeClient.recharge(m.getMobile(), m.getMoney().intValue(), m.getActivityId(), m.getUid());

			/*Callable ca1 = new Callable() {
				@Override
				public MailFeeStatus call() throws Exception {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					MailFeeStatus mf = new MailFeeStatus();
					mf.setStatus(1);
					return mf;
				}
			};*/

//		Future<MailFeeStatus> f = new FutureTask<MailFeeStatus>(ca1);
//			FutureTask<String> f = new FutureTask<String>(ca1);
//			new Thread(new FutureTask<String>(ca1)).start();

//			Thread.sleep(1000);
            int a =10/0;
//			System.out.println("正常执行");
//			m.setProcessStatus(2);
//			mobileDao.update(m);
        } catch (Throwable e1) {
            m.setProcessStatus(7);
            logger.info("异常手机号是{},线程是{}",m.getMobile(),t);
            m.setRechargeExceptionMsg(e1.getMessage());
            mobileDao.update(m);
            deleteThreadMobile(m);

            int rechargeErrCount = rcharErrThreadCount.incrementAndGet();
            Integer totalErrorCount = cacheService.get("totalErrorCount", Integer.class);
            int totalCount = null != totalErrorCount ? totalErrorCount.intValue() : 0;

            logger.info("报警数量是{}，第{}个线程,手机号是{},状态是{}",rechargeErrCount,t,m.getMobile(),m.getProcessStatus());
            if(rechargeErrCount>=10){
                isInterupt.set(true);
//				Result.setPersonFlag(true);
                logger.info("==============超过报警数量是{}，第{}个线程,手机号是{},状态是{}",rechargeErrCount,t,m.getMobile(),m.getProcessStatus());
                //把漏掉的手机号更新为初始,为了下次可以充值上
				/*List<Mobile> mobileList = mobiles.stream().filter(s -> RechargeProcessStatusEnums.SENDRECHAGRE.getId()==s.getProcessStatus()).collect(Collectors.toList());
				List<String> mobileUids = mobileList.stream().map(Mobile::getUid).collect(Collectors.toList());
				mobileDao.updateProcessStatus0ForNext(mobileUids);*/
                cacheService.set("totalErrorCount",totalCount+rechargeErrCount);
                Integer count1 = cacheService.get("totalErrorCount", Integer.class);
                logger.info("数量是{}",count1);
                if(count1 >=40 ){
                    Result.setFlag(true);
                }
            }else{
                isInterupt.set(false);
            }

            logger.error(
                    "手机号 " + m.getMobile() + "提交充值异常，返回状态:" + JsonUtils.toJson(e1) + "***************************");
            return;
        }
        m.setLastRechargTime(new Date());
        m.setRechargeRetryCount(m.getRechargeRetryCount() + 1);

        mobileDao.update(m);
        deleteThreadMobile(m);

//		saveRechargeRecord(m, mfs);// 保存充值记录

    }

    private void saveThreadMobile(String threadName, Mobile m) {
        ThreadMobile threadMobile = new ThreadMobile();
        threadMobile.setId(m.getId());
        threadMobile.setThreadName(threadName);
        threadMobile.setUid(m.getUid());
        threadMobile.setMobile(m.getMobile());
        threadMobileDao.insert(threadMobile);
    }
    private void deleteThreadMobile(Mobile m) {
        threadMobileDao.removeById(m.getId());
    }
}
