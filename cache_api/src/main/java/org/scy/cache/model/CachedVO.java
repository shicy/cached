package org.scy.cache.model;

import java.io.Serializable;

/**
 * 缓存对象
 * Created by shicy on 2017/9/28.
 */
@SuppressWarnings("unused")
public class CachedVO implements Serializable {

    private static final long serialVersionUID = 1002017092815400000L;

    public final static String STORED = "STORED";
    public final static String NOSTORED = "NOT_STORED";
    public final static String ERROR = "ERROR";

    // 键名称
    private String key;

    // 存储的值
    private String value;

    // 用于客户端存储额外信息
    private int flags;

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

}
