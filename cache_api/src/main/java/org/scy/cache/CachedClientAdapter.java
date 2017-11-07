package org.scy.cache;

import org.scy.cache.model.CachedVO;
import org.scy.common.web.controller.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 缓存客户端适配器
 * Created by shicy on 2017/9/30.
 */
@Component
@SuppressWarnings("unused")
public final class CachedClientAdapter {

    private static CachedClient cachedClient;

    @Autowired(required = false)
    private CachedClient cachedClientTemp;

    @PostConstruct
    public void init() {
        cachedClient = cachedClientTemp;
    }

    public static CachedVO get(String key) {
        HttpResult result = cachedClient.get(key);
        return result.getData(CachedVO.class);
    }

    public static String getValue(String key) {
        CachedVO cachedVO = get(key);
        return cachedVO != null ? cachedVO.getValue() : null;
    }

    public static boolean set(String key, String value) {
        HttpResult result = cachedClient.set(key, value);
        return CachedVO.STORED.equals(result.getData());
    }

    public static boolean set(String key, String value, int expires) {
        HttpResult result = cachedClient.set(key, value, expires);
        return CachedVO.STORED.equals(result.getData());
    }

    public static boolean set(String key, String value, int expires, int flags) {
        HttpResult result = cachedClient.set(key, value, expires, flags);
        return CachedVO.STORED.equals(result.getData());
    }

    public static HttpResult delete(String key) {
        return cachedClient.delete(key);
    }

    public static boolean replace(String key, String value) {
        HttpResult result = cachedClient.replace(key, value);
        return CachedVO.STORED.equals(result.getData());
    }

    public static boolean replace(String key, String value, int expires) {
        HttpResult result = cachedClient.replace(key, value, expires);
        return CachedVO.STORED.equals(result.getData());
    }

    public static boolean replace(String key, String value, int expires, int flags) {
        HttpResult result = cachedClient.replace(key, value, expires, flags);
        return CachedVO.STORED.equals(result.getData());
    }

}
