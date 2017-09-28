package org.scy.cache;

import org.scy.cache.model.CacheVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 缓存管理客户端
 * Created by shicy on 2017/9/28.
 */
@FeignClient(name = "cache-service", url = "${app.cache-service.url}")
public interface CachedClient {

    @RequestMapping(value = "get/{key}", method = RequestMethod.GET)
    CacheVO get(@PathVariable("key") String key);

}
