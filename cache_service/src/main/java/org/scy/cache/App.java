package org.scy.cache;

import org.scy.common.BaseApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

/**
 * 缓存管理服务
 * Created by shicy on 2017/9/9.
 */
@SpringBootApplication(scanBasePackages = {"org.scy"},
        exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class App extends BaseApplication {

    public static void main(String[] args) throws Exception {
        BaseApplication.startup(App.class, args);
    }

    public App() {
        super();
        System.out.println("333333333333333333");
        this.run(new String[]{});
    }


}
