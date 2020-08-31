package com.talkweb.unicom.recharge.utils;

import cn.csatv.common.utils.StringMap;

public class SynchronizedLock {

    private static StringMap caches = new StringMap();

    public synchronized static Object get(String key) {
        if (!caches.containsKey(key)) {
            caches.put(key, key);
        }
        return caches.get(key);
    }

    public synchronized static void remove(String key) {
        caches.remove(key);
    }

}
