package org.scy.cache.manager;

/**
 * 缓存管理工厂类
 * Created by shicy on 2017/9/10.
 */
public final class CacheManagerFactory {

    // 单例实例
    private static CacheManager instance;

    /**
     * 获取缓存管理类实例
     * @return
     */
    public static CacheManager getInstance() {
        if (instance == null) {
            synchronized (CacheManager.class) {
                if (instance == null) {
                    instance = new DefaultCacheManager();
                }
            }
        }
        return instance;
    }

}
