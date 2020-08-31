package com.talkweb.unicom.recharge.dao;

import cn.csatv.common.db.dao.BaseDao;
import com.talkweb.unicom.recharge.bean.MobileHistory;

/**
 * 手机号历史表数据库操作
 * @author 40642
 * @create 2019-03-12 14:58:22
 */
public interface MobileHistoryDao extends BaseDao<MobileHistory> {

    void  insertMobileHistory(MobileHistory mobileHistory);

}
