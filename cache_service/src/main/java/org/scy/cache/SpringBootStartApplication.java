package org.scy.cache;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * 部署到 tomcat 时 SpringBoot 启动类
 * Created by shicy on 2017/9/22.
 */
@SuppressWarnings("unused")
public class SpringBootStartApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(App.class);
    }

}
