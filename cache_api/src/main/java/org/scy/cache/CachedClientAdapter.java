package org.scy.cache;

import org.scy.cache.model.CachedVO;
import org.scy.common.utils.ArrayUtilsEx;
import org.scy.common.web.controller.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 缓存客户端适配器
 * Created by shicy on 2017/9/30.
 */
@Component
@SuppressWarnings({"unused", "unchecked"})
public final class CachedClientAdapter {

    private static CachedClient cachedClient;

    @Autowired(required = false)
    private CachedClient cachedClientTemp;

    @PostConstruct
    public void init() {
        cachedClient = cachedClientTemp;
    }

    /**
     * 获取缓存对象
     */
    public static CachedVO get(String key) {
        HttpResult result = cachedClient.get(key);
        return result.getData(CachedVO.class);
    }

    /**
     * 获取多个缓存对象
     * @param keys 键值，逗号分隔
     */
    public static CachedVO[] get(String[] keys) {
        HttpResult result = cachedClient.gets(ArrayUtilsEx.join(keys, ","));
        return getResultDatas(result);
    }

    /**
     * 获取缓存内容
     */
    public static String getValue(String key) {
        HttpResult result = cachedClient.get(key);
        CachedVO cachedVO = result.getData(CachedVO.class);
        return cachedVO != null ? cachedVO.getValue() : null;
    }

    /**
     * 设置
     * @param key 关键字
     * @param value 值
     */
    public static boolean set(String key, String value) {
        HttpResult result = cachedClient.set(key, value);
        return CachedVO.STORED.equals(result.getData());
    }

    /**
     * 设置
     * @param key 关键字
     * @param value 值
     * @param expires 有效期（秒）
     */
    public static boolean set(String key, String value, int expires) {
        HttpResult result = cachedClient.set(key, value, expires);
        return CachedVO.STORED.equals(result.getData());
    }

    /**
     * 设置
     * @param key 关键字
     * @param value 值
     * @param expires 有效期（秒）
     * @param flags 缓存标记
     */
    public static boolean set(String key, String value, int expires, int flags) {
        HttpResult result = cachedClient.set(key, value, expires, flags);
        return CachedVO.STORED.equals(result.getData());
    }

    /**
     * 删除缓存对象
     * @return 返回被删除的对象
     */
    public static CachedVO delete(String key) {
        HttpResult result = cachedClient.delete(key);
        return (CachedVO)result.getData();
    }

    /**
     * 删除多个缓存对象
     * @param keys 关键字逗号分隔
     * @return 返回被删除的对象
     */
    public static CachedVO[] delete(String[] keys) {
        HttpResult result = cachedClient.deletes(ArrayUtilsEx.join(keys, ","));
        return getResultDatas(result);
    }

    /**
     * 删除缓存对象，关键字模糊匹配
     * @return 返回被删除的对象
     */
    public static CachedVO[] deleteLike(String key) {
        HttpResult result = cachedClient.deleteLike(key);
        return getResultDatas(result);
    }

    /**
     * 删除某个关键字开头的缓存对象
     * @return 返回被删除的对象
     */
    public static CachedVO[] deleteStart(String key) {
        HttpResult result = cachedClient.deleteStart(key);
        return getResultDatas(result);
    }

    /**
     * 删除某个关键字结尾的缓存对象
     * @return 返回被删除的对象
     */
    public static CachedVO[] deleteEnd(String key) {
        HttpResult result = cachedClient.deleteEnd(key);
        return getResultDatas(result);
    }

    /**
     * 更新
     * @param key 关键字
     * @param value 值
     */
    public static boolean replace(String key, String value) {
        HttpResult result = cachedClient.replace(key, value);
        return CachedVO.STORED.equals(result.getData());
    }

    /**
     * 更新
     * @param key 关键字
     * @param value 值
     * @param expires 有效期（秒）
     */
    public static boolean replace(String key, String value, int expires) {
        HttpResult result = cachedClient.replace(key, value, expires);
        return CachedVO.STORED.equals(result.getData());
    }

    /**
     * 更新
     * @param key 关键字
     * @param value 值
     * @param expires 有效期（秒）
     * @param flags 缓存标记
     */
    public static boolean replace(String key, String value, int expires, int flags) {
        HttpResult result = cachedClient.replace(key, value, expires, flags);
        return CachedVO.STORED.equals(result.getData());
    }

    private static CachedVO[] getResultDatas(HttpResult result) {
        Object resultData = result.getData();
        if (resultData != null) {
            try {
                List<CachedVO> datas = (List<CachedVO>)resultData;
                return datas.toArray(new CachedVO[0]);
            }
            catch (Exception e) {
                //
            }
        }
        return new CachedVO[0];
    }

}
