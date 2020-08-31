package com.talkweb.unicom.recharge.service;

import cn.csatv.common.db.service.BaseService;
import com.talkweb.unicom.recharge.bean.RechargeRecord;
import com.talkweb.unicom.recharge.dao.RechargeRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 充值记录业务处理
 * @author 40642
 * @create 2019-03-10 19:20:50
 */

@Service
public class RechargeRecordService extends BaseService<RechargeRecord> {

    @Autowired
    private RechargeRecordDao rechargeRecordDao;


}
