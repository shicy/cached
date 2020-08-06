package org.scy.cache.manager;

import org.scy.cache.model.CacheModel;
import org.scy.common.ds.PageInfo;

import java.util.List;

/**
 * 缓存管理类
 * Created by shicy on 2017/9/10.
 */
@SuppressWarnings("unused")
public interface CacheManager {

    /**
     * 新增缓存对象
     * @param model 对象模型
     * @return 0-失败 1-成功
     */
    int add(CacheModel model);
    int add(String key, String value);
    int add(String key, String value, int expires);
    int add(String key, String value, int expires, int flags);

    /**
     * 修改缓存对象
     * @param model 对象模型
     * @return 0-失败 1-成功
     */
    int update(CacheModel model);
    int update(String key, String value);
    int update(String key, String value, int expires);
    int update(String key, String value, int expires, int flags);

    /**
     * 设置缓存对象
     * @param model 对象模型
     * @return 0-失败 1-新增 2-修改
     */
    int set(CacheModel model);
    int set(String key, String value);
    int set(String key, String value, int expires);
    int set(String key, String value, int expires, int flags);

    /**
     * 获取一个对象
     * @param key 想要获取对象的键名
     * @return 返回相应键名的对象
     */
    CacheModel get(String key);
    List<CacheModel> getLike(String key);
    List<CacheModel> getLikeStart(String key);
    List<CacheModel> getLikeEnd(String key);

    /**
     * 删除一个对象
     * @param key 想要删除的对象键名
     * @return 返回被删除的对象
     */
    CacheModel delete(String key);
    List<CacheModel> deleteLike(String key);
    List<CacheModel> deleteLikeStart(String key);
    List<CacheModel> deleteLikeEnd(String key);

    int getTotal(String keyLike);
    List<CacheModel> list(String keyLike, PageInfo pageInfo);

}
