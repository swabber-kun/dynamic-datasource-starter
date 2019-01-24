package com.dynamic.service;

import com.dynamic.common.DataSourceKey;
import com.dynamic.utils.DataSourceManager;
import com.dynamic.config.DynamicDataSource;
import com.dynamic.utils.ApplicationContextHolder;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private DataSourceManager dataSourceManager;

    @Bean("master")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.hikari.master")
    public DataSource master() {
        return DataSourceBuilder.create().build();
    }

    public void refreshDataSource() {
        logger.info("Refreshing data source starting...");

        //获取context
        DynamicDataSource dynamicDataSource = ApplicationContextHolder.getBean("dynamicDataSource");

        // 覆盖原有的dataSource
        HikariDataSource oldDataSource = dynamicDataSource.setAndGetDataSource(DataSourceKey.slaveAlpha.name(),dataSourceManager.dataSource());

        // 关闭原有连接
        dataSourceManager.asyncTerminate(oldDataSource);
        logger.info("Refreshing data source success...");
    }
}
