package org.scy.cache.model;

import java.io.Serializable;

public class CacheModel4Admin implements Serializable {

    private static final long serialVersionUID = 1002020080213500000L;

    private String key;
    private String value;
    private int flags;
    private int expires;
    private long createTime;
    private long updateTime;

    public CacheModel4Admin(CacheModel model) {
        this.key = model.getKey();
        this.value = model.getValue();
        this.flags = model.getFlags();
        this.expires = model.getExpires();
        this.createTime = model.getCreatetime();
        this.updateTime = model.getUpdatetime();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public int getExpires() {
        return expires;
    }

    public void setExpires(int expires) {
        this.expires = expires;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
