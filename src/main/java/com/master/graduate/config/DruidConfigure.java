package com.master.graduate.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 阿里巴巴Druid数据库连接池配置
 *
 * @author Master_Joe lutong99
 * @since 2/17/2020 3:41 PM
 */
@Configuration
public class DruidConfigure {

    /**
     * 配置druid配置，绑定数据源
     */
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druid() {
        return new DruidDataSource();
    }

}
