package com.talkweb.unicom.recharge.service;

import cn.csatv.common.db.service.BaseService;
import com.talkweb.unicom.recharge.bean.Mobile;
import com.talkweb.unicom.recharge.dao.MobileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 手机号业务处理
 * @author 40642
 * @create 2019-03-10 19:20:49
 */

@Service
public class MobileService extends BaseService<Mobile> {

    @Autowired
    private MobileDao mobileDao;


}
