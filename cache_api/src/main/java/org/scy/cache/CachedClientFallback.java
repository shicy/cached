package org.scy.cache;

import org.scy.cache.model.CacheVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CachedClientFallback implements CachedClient {

    private Logger logger = LoggerFactory.getLogger(CachedClientFallback.class);

    @Override
    public CacheVO get(String key) {
        logger.error("jfouewif");
//        return null;
        throw new RuntimeException("jfowjfoiejwfo");
    }

}
