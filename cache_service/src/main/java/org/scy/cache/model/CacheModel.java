package org.scy.cache.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据模型
 * Created by shicy on 2017/9/10.
 */
public class CacheModel implements Serializable {

    private static final long serialVersionUID = 1002017091000000000L;

    // 键名称
    private String key;

    // 存储的值
    private Object value;

    // 过期时间（毫秒），为 0 时永不过期
    private int expires;

    // 用于客户端存储额外信息
    private int flags;

    // 存储时间
    private long createtime;

    // 最后更新时间
    private long updatetime;

    public CacheModel() {
        this.setCreatetime(new Date().getTime());
    }

    public CacheModel(String key, Object value, int expires, int flags) {
        this.setKey(key);
        this.setValue(value);
        this.setExpires(expires);
        this.setFlags(flags);
        this.setCreatetime(new Date().getTime());
    }

    /**
     * 获取键名
     * @return
     */
    public String getKey() {
        return key;
    }

    /**
     * 设置键名
     * @param key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 获取缓存值
     * @return
     */
    public Object getValue() {
        return value;
    }

    /**
     * 设置缓存值
     * @param value
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * 获取过期时长
     * @return
     */
    @JSONField(serialize = false)
    public int getExpires() {
        return expires;
    }

    /**
     * 设置过期时长（毫秒）
     * @param expires
     */
    public void setExpires(int expires) {
        this.expires = expires;
    }

    /**
     * 获取额外信息
     * @return
     */
    public int getFlags() {
        return flags;
    }

    /**
     * 设置额外信息
     * @param flags
     */
    public void setFlags(int flags) {
        this.flags = flags;
    }

    /**
     * 获取创建时间
     * @return
     */
    @JSONField(serialize = false)
    public long getCreatetime() {
        return createtime;
    }

    /**
     * 设置创建时间
     * @param createtime
     */
    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    /**
     * 获取更新时间
     * @return
     */
    @JSONField(serialize = false)
    public long getUpdatetime() {
        return updatetime;
    }

    /**
     * 设置更新时间
     * @param updatetime
     */
    public void setUpdatetime(long updatetime) {
        this.updatetime = updatetime;
    }

}
