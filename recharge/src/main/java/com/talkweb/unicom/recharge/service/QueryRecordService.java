package com.talkweb.unicom.recharge.service;

import cn.csatv.common.db.service.BaseService;
import com.talkweb.unicom.recharge.bean.QueryRecord;
import com.talkweb.unicom.recharge.dao.QueryRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 充值状态查询业务处理
 * @author 40642
 * @create 2019-03-10 19:20:50
 */

@Service
public class QueryRecordService extends BaseService<QueryRecord> {

    @Autowired
    private QueryRecordDao queryRecordDao;


}
