package org.scy.cache.model;

import java.io.Serializable;

/**
 * 缓存对象
 * Created by shicy on 2017/9/28.
 */
public class CacheVO implements Serializable {

    private static final long serialVersionUID = 1002017092815400000L;

    // 键名称
    private String key;

    // 存储的值
    private Object value;

    // 用于客户端存储额外信息
    private int flags;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

}
