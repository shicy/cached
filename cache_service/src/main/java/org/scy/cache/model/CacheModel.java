package org.scy.cache.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * 数据模型
 * Created by shicy on 2017/9/10.
 */
public class CacheModel extends CachedVO {

    private static final long serialVersionUID = 1002017091000000000L;

    // 过期时间（毫秒），为 0 时永不过期
    private int expires;

    // 存储时间
    private long createtime;

    // 最后更新时间
    private long updatetime;

    public CacheModel() {
        this.setCreatetime(new Date().getTime());
    }

    public CacheModel(String key, String value, int expires, int flags) {
        this.setKey(key);
        this.setValue(value);
        this.setExpires(expires);
        this.setFlags(flags);
        this.setCreatetime(new Date().getTime());
    }

    /**
     * 获取过期时长
     */
    @JSONField(serialize = false)
    public int getExpires() {
        return expires;
    }

    /**
     * 设置过期时长（毫秒）
     */
    public void setExpires(int expires) {
        this.expires = expires;
    }

    /**
     * 获取创建时间
     */
    @JSONField(serialize = false)
    public long getCreatetime() {
        return createtime;
    }

    /**
     * 设置创建时间
     */
    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    /**
     * 获取更新时间
     */
    @JSONField(serialize = false)
    public long getUpdatetime() {
        return updatetime;
    }

    /**
     * 设置更新时间
     */
    public void setUpdatetime(long updatetime) {
        this.updatetime = updatetime;
    }

}
