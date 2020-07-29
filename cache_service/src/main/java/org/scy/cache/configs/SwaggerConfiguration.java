package org.scy.cache.configs;

import org.scy.common.configs.BaseSwaggerConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Profile({"dev"})
@SuppressWarnings("unused")
public class SwaggerConfiguration extends BaseSwaggerConfiguration {
    @Override
    protected String getTitle() {
        return "Cached Service API";
    }

    @Override
    protected String getDescription() {
        return "缓存服务接口";
    }

    @Override
    protected String getServiceUrl() {
        return "http://cached.scyok.com";
    }

    @Override
    protected String getApiPackage() {
        return "org.scy.cache.controller";
    }
}
