package com.talkweb.unicom.recharge.wrapper;

import cn.csatv.common.exception.BusinessException;
import cn.csatv.common.exception.ServiceException;
import cn.csatv.common.utils.JsonUtils;
import cn.csatv.common.utils.StringMap;
import cn.csatv.common.wrapper.IInWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;

/**
 * 解析科创接口消息
 */
public class KCWrapper implements IInWrapper {

    private static Logger logger = LoggerFactory.getLogger(KCWrapper.class);

    @Override
    public Object unwrap(Object obj) {
        if(obj != null && obj instanceof String) {
            String str = (String) obj;
            StringMap map = JsonUtils.fromJson(str, StringMap.class);
            map.remove("resultcode");
            map.remove("errorinfo");
            return map;
        }
        return obj;
    }

    @Override
    public void valid(HttpEntity entity, Object obj) {
        if(obj != null && obj instanceof String) {

            logger.debug("调用科创接口返回的结果：" + obj);

            String str = (String) obj;
            StringMap map = JsonUtils.fromJson(str, StringMap.class);
            String code = (String) map.remove("resultcode");
            if(code != null && !"0".equals(code)) {
                if("9010".equals(code)) {
                    throw new BusinessException("9010", "您已经购买了免流产品，不能参与此活动");
                } else if("103".equals(code)) {
                    throw new BusinessException("103", "网络超时，请稍候再试");
                }
                String msg = (String) map.remove("errorinfo");
                if(msg == null) {
                    msg = "系统异常";
                }
                if(map.size() > 0) {
                    throw new ServiceException(code, msg, map);
                } else {
                    throw new ServiceException(code, msg);
                }
            }
        }
    }

}
