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

    @RequestMapping(value = "get/{key}", method = RequestMethod.GET)
    HttpResult get(@PathVariable("key") String key);


    @RequestMapping(value = "set/{key}/{value}", method = RequestMethod.POST)
    HttpResult set(@PathVariable("key") String key, @PathVariable("value") String value);

    @RequestMapping(value = "set/{key}/{value}/{expires}", method = RequestMethod.POST)
    HttpResult set(@PathVariable("key") String key, @PathVariable("value") String value,
            @PathVariable("expires") int expires);

    @RequestMapping(value = "set/{key}/{value}/{expires}/{flags}", method = RequestMethod.POST)
    HttpResult set(@PathVariable("key") String key, @PathVariable("value") String value,
            @PathVariable("expires") int expires, @PathVariable("flags") int flags);


    @RequestMapping(value = "delete/{key}", method = RequestMethod.POST)
    HttpResult delete(@PathVariable("key") String key);

}
