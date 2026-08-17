package com.qiuzhitech.onlineshopping_06.config;

import com.qiuzhitech.onlineshopping_06.user.UserTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean(name = "zhangsan")
    public UserTest userZhangSanProvider() {
        return new UserTest(0, "zhangSan", "zhangSan@xxx.com");
    }

    @Bean(name = "lisi")
    public UserTest userLisiDemoProvider() {
        return new UserTest(0, "lisi", "lisi@xxx.com");
    }
}
