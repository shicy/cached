package org.scy.cache.manager;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;
import org.scy.cache.model.CacheModel;
import org.scy.common.manager.SchedulerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 内置默认缓存管理器
 * Created by shicy on 2017/9/10.
 */
public class DefaultCacheManager implements CacheManager {

    private Logger logger = LoggerFactory.getLogger(DefaultCacheManager.class);

    // 缓存对象集
    private List<String> cacheKeys = new ArrayList<String>();
    private Map<String, CacheModel> cacheModels = new HashMap<String, CacheModel>();

    /**
     * 构造方法
     */
    DefaultCacheManager() {
        this.startClearTask();
    }

    @Override
    public int add(CacheModel model) {
        return add(model.getKey(), model.getValue(), model.getExpires(), model.getFlags());
    }

    @Override
    public int add(String key, String value) {
        return add(key, value, 0, 0);
    }

    @Override
    public int add(String key, String value, int expires) {
        return add(key, value, expires, 0);
    }

    @Override
    public int add(String key, String value, int expires, int flags) {
        CacheModel model = this.get(key);
        if (model != null)
            return 0;
        if (StringUtils.isNotBlank(key)) {
            cacheKeys.add(key);
            cacheModels.put(key, new CacheModel(key, value, expires, flags));
            return 1;
        }
        logger.error("error add, the 'key' must not empty [" + key + "]");
        return -1;
    }

    @Override
    public int update(CacheModel model) {
        return update(model.getKey(), model.getValue(), model.getExpires(), model.getFlags());
    }

    @Override
    public int update(String key, String value) {
        return update(key, value, 0, 0);
    }

    @Override
    public int update(String key, String value, int expires) {
        return update(key, value, expires, 0);
    }

    @Override
    public int update(String key, String value, int expires, int flags) {
        CacheModel model = this.get(key);
        if (model != null) {
            model.setValue(value);
            model.setExpires(expires);
            model.setFlags(flags);
            model.setUpdatetime(new Date().getTime());
            return 1;
        }
        logger.error("error update, the model of [" + key + "] is not exists.");
        return 0;
    }

    @Override
    public int set(CacheModel model) {
        return set(model.getKey(), model.getValue(), model.getExpires(), model.getFlags());
    }

    @Override
    public int set(String key, String value) {
        return set(key, value, 0, 0);
    }

    @Override
    public int set(String key, String value, int expires) {
        return set(key, value, expires, 0);
    }

    @Override
    public int set(String key, String value, int expires, int flags) {
        CacheModel model = this.get(key);
        if (model == null) {
            cacheKeys.add(key);
            cacheModels.put(key, new CacheModel(key, value, expires, flags));
            return 1;
        }
        if (StringUtils.isNotBlank(key)) {
            model.setValue(value);
            model.setExpires(expires);
            model.setFlags(flags);
            model.setUpdatetime(new Date().getTime());
            return 2;
        }
        logger.error("error set, the 'key' must not empty [" + key + "]");
        return 0;
    }

    @Override
    public CacheModel get(String key) {
        if (StringUtils.isNotBlank(key)) {
            CacheModel model = cacheModels.get(key);
            if (model != null && !isModelOverdue(model))
                return model;
        }
        return null;
    }

    @Override
    public List<CacheModel> getLike(String key) {
        if (StringUtils.isNotBlank(key)) {
            List<CacheModel> models = new ArrayList<CacheModel>();
            for (String _key: cacheKeys) {
                if (StringUtils.contains(_key, key)) {
                    CacheModel model = cacheModels.get(_key);
                    if (!isModelOverdue(model))
                        models.add(model);
                }
            }
            return models;
        }
        return null;
    }

    @Override
    public List<CacheModel> getLikeStart(String key) {
        if (StringUtils.isNotBlank(key)) {
            List<CacheModel> models = new ArrayList<CacheModel>();
            for (String _key: cacheKeys) {
                if (StringUtils.startsWith(_key, key)) {
                    CacheModel model = cacheModels.get(_key);
                    if (!isModelOverdue(model))
                        models.add(model);
                }
            }
            return models;
        }
        return null;
    }

    @Override
    public List<CacheModel> getLikeEnd(String key) {
        if (StringUtils.isNotBlank(key)) {
            List<CacheModel> models = new ArrayList<CacheModel>();
            for (String _key: cacheKeys) {
                if (StringUtils.endsWith(_key, key)) {
                    CacheModel model = cacheModels.get(_key);
                    if (!isModelOverdue(model))
                        models.add(model);
                }
            }
            return models;
        }
        return null;
    }

    @Override
    public CacheModel delete(String key) {
        if (StringUtils.isNotBlank(key)) {
            CacheModel model = cacheModels.remove(key);
            if (model != null) {
                cacheKeys.remove(key);
            }
            if (!isModelOverdue(model))
                return model;
        }
        return null;
    }

    @Override
    public List<CacheModel> deleteLike(String key) {
        if (StringUtils.isNotBlank(key)) {
            List<CacheModel> models = new ArrayList<CacheModel>();
            for (int i = cacheKeys.size() - 1; i >= 0; i--) {
                String _key = cacheKeys.get(i);
                if (StringUtils.contains(_key, key)) {
                    cacheKeys.remove(i);
                    CacheModel model = cacheModels.remove(_key);
                    if (!isModelOverdue(model))
                        models.add(model);
                }
            }
            return models;
        }
        return null;
    }

    @Override
    public List<CacheModel> deleteLikeStart(String key) {
        if (StringUtils.isNotBlank(key)) {
            List<CacheModel> models = new ArrayList<CacheModel>();
            for (int i = cacheKeys.size() - 1; i >= 0; i--) {
                String _key = cacheKeys.get(i);
                if (StringUtils.startsWith(_key, key)) {
                    cacheKeys.remove(i);
                    CacheModel model = cacheModels.remove(_key);
                    if (!isModelOverdue(model))
                        models.add(model);
                }
            }
            return models;
        }
        return null;
    }

    @Override
    public List<CacheModel> deleteLikeEnd(String key) {
        if (StringUtils.isNotBlank(key)) {
            List<CacheModel> models = new ArrayList<CacheModel>();
            for (int i = cacheKeys.size() - 1; i >= 0; i--) {
                String _key = cacheKeys.get(i);
                if (StringUtils.endsWith(_key, key)) {
                    cacheKeys.remove(i);
                    CacheModel model = cacheModels.remove(_key);
                    if (!isModelOverdue(model))
                        models.add(model);
                }
            }
            return models;
        }
        return null;
    }

    /**
     * 判断模型是否过期
     * @param model 缓存对象
     * @return 如果对象已过期返回 true，否则返回 false
     */
    private boolean isModelOverdue(CacheModel model) {
        int expires = model.getExpires();
        if (expires <= 0)
            return false;
        long overdue = expires * 1000;
        overdue += model.getUpdatetime() > 0 ? model.getUpdatetime() : model.getCreatetime();
        return overdue < new Date().getTime();
    }

    /**
     * 开启定时清理任务，每分钟清理一次过期的缓存
     */
    private void startClearTask() {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("cacheManager", this);

        // 每分钟执行一次
        Trigger trigger = SchedulerManager.newCronTrigger("0 * * * * ? *");

        SchedulerManager.getInstance().addScheduleJob(ClearTask.class, trigger, data);
    }

    /**
     * 清理，删除过期对象
     */
    private void clear() {
        for (int i = cacheKeys.size() - 1; i >= 0; i--) {
            String _key = cacheKeys.get(i);
            CacheModel model = cacheModels.get(_key);
            if (isModelOverdue(model)) {
                cacheKeys.remove(i);
                cacheModels.remove(_key);
            }
        }
    }

    /**
     * 缓存定时清理任务
     */
    public static class ClearTask extends SchedulerManager.ThreadJob {
        @Override
        protected void executeJob(JobDataMap data) throws JobExecutionException {
            ((DefaultCacheManager) data.get("cacheManager")).clear();
        }
    }

}
