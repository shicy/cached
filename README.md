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

测试环境：spring.profiles.active=dev

需要引用`jcoms`项目

### 打包
mvn clean package -f pom.xml -P prod   
IDEL 配置选项 Profiles 为 prod

### 部署
安装网络`docker network create -d bridge --subnet 172.2.2.0/24 mynet`

本地执行`./build/deploy_remote`

或登录服务器，进入`build`目录，执行`./deploy`命令
