package org.scy.cache;

import org.scy.common.web.controller.HttpResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 缓存管理客户端
 * Created by shicy on 2017/9/28.
 */
@FeignClient(name = "cache-service", url = "${app.cache-service.url:/}")
public interface CachedClient {

    /**
     * 获取缓存对象
     */
    @RequestMapping(value = "/get/{key}", method = RequestMethod.GET)
    HttpResult get(@PathVariable("key") String key);

    /**
     * 获取缓存对象
     * @param keys 逗号分隔
     */
    @RequestMapping(value = "/gets/{keys}", method = RequestMethod.GET)
    HttpResult gets(@PathVariable("keys") String keys);


    /**
     * 设置
     * @return 设置成功返回 STORED，否则返回 ERROR
     */
    @RequestMapping(value = "/set/{key}/{value}", method = RequestMethod.POST)
    HttpResult set(@PathVariable("key") String key, @PathVariable("value") String value);

    /**
     * 设置
     * @return 设置成功返回 STORED，否则返回 ERROR
     */
    @RequestMapping(value = "/set/{key}/{value}/{expires}", method = RequestMethod.POST)
    HttpResult set(@PathVariable("key") String key, @PathVariable("value") String value,
            @PathVariable("expires") int expires);

    /**
     * 设置
     * @return 设置成功返回 STORED，否则返回 ERROR
     */
    @RequestMapping(value = "/set/{key}/{value}/{expires}/{flags}", method = RequestMethod.POST)
    HttpResult set(@PathVariable("key") String key, @PathVariable("value") String value,
            @PathVariable("expires") int expires, @PathVariable("flags") int flags);


    /**
     * 删除缓存对象
     * @return 返回被删除的对象
     */
    @RequestMapping(value = "/delete/{key}", method = RequestMethod.POST)
    HttpResult delete(@PathVariable("key") String key);

    /**
     * 删除缓存对象
     * @param keys 逗号分隔
     * @return 返回被删除的对象
     */
    @RequestMapping(value = "/deletes/{keys}", method = RequestMethod.POST)
    HttpResult deletes(@PathVariable("keys") String keys);

    /**
     * 删除缓存对象（模糊匹配）
     * @return 返回被删除的对象
     */
    @RequestMapping(value = "/delete/like/{key}", method = RequestMethod.POST)
    HttpResult deleteLike(@PathVariable("key") String key);

    /**
     * 删除缓存对象（以关键字开头的缓存）
     * @return 返回被删除的对象
     */
    @RequestMapping(value = "/delete/start/{key}", method = RequestMethod.POST)
    HttpResult deleteStart(@PathVariable("key") String key);

    /**
     * 删除缓存对象（以关键字结尾的缓存）
     * @return 返回被删除的对象
     */
    @RequestMapping(value = "/delete/end/{key}", method = RequestMethod.POST)
    HttpResult deleteEnd(@PathVariable("key") String key);


    /**
     * 更新
     * @return 更新成功返回 STORED，否则返回 NOT_STORED
     */
    @RequestMapping(value = "/replace/{key}/{value}", method = RequestMethod.POST)
    HttpResult replace(@PathVariable("key") String key, @PathVariable("value") String value);

    /**
     * 更新
     * @return 更新成功返回 STORED，否则返回 NOT_STORED
     */
    @RequestMapping(value = "/replace/{key}/{value}/{expires}", method = RequestMethod.POST)
    HttpResult replace(@PathVariable("key") String key, @PathVariable("value") String value,
            @PathVariable("expires") int expires);

    /**
     * 更新
     * @return 更新成功返回 STORED，否则返回 NOT_STORED
     */
    @RequestMapping(value = "/replace/{key}/{value}/{expires}/{flags}", method = RequestMethod.POST)
    HttpResult replace(@PathVariable("key") String key, @PathVariable("value") String value,
            @PathVariable("expires") int expires, @PathVariable("flags") int flags);

}
