package com.talkweb.unicom.recharge.service;

import cn.csatv.common.db.service.BaseService;
import com.talkweb.unicom.recharge.bean.SmsRecord;
import com.talkweb.unicom.recharge.dao.SmsRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 短信发送业务处理
 * @author 40642
 * @create 2019-03-10 19:20:50
 */

@Service
public class SmsRecordService extends BaseService<SmsRecord> {

    @Autowired
    private SmsRecordDao smsRecordDao;


}
