package com.dynamic.service;

import com.dynamic.config.DynamicRoutingDataSource;
import com.dynamic.utils.ApplicationContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * @author jibingkun
 * @date 2019/1/22.
 */
@Component
public class DataSourceRefresher {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceRefresher.class);

    @Bean("master")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.hikari.master")
    public DataSource master() {
        return DataSourceBuilder.create().build();
    }

    public void refreshDataSource() {
        logger.info("Refreshing data source");

        //获取context
        DynamicRoutingDataSource dynamicDataSource = ApplicationContextHolder.getBean("dynamicDataSourceConfig");



//        ConcurrentHashMap<String, DataSource> dataSourceConcurrentHashMap = dynamicDataSource.getDataSourceAtomicReference();
//
//        dataSourceConcurrentHashMap.forEach((k, v) -> {
//            System.out.println("key: " + k + " value: " + v);
//        });
    }
}
