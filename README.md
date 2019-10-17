## 缓存服务


### 客户端
* org.scy.cache.model.CachedClient
* org.scy.cache.model.CachedClientAdapter

### API
* get(String key)
* get(String[] keys)
* getValue(String key)
* set(String key, String value)
* set(String key, String value, int expires)
* set(String key, String value, int expires, int flags)
* delete(String key)
* delete(String[] keys)
* deleteLike(String key)
* deleteStart(String key)
* deleteEnd(String key)
* replace(String key, String value)
* replace(String key, String value, int expires)
* replace(String key, String value, int expires, int flags)


### 启动服务
运行 org.scy.cache.App

需要引用`jcoms`项目

### 打包发布
mvn clean package -f pom.xml -P prod   
IDEL 配置选项 Profiles 为 prod
