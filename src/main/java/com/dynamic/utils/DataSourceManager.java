package com.dynamic.utils;

import com.dynamic.service.DataSourceTerminationTask;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author jibingkun
 * @date 2019/1/23.
 */
@Component
public class DataSourceManager {

    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    /**
     * 这边处理简陋了，仅仅是模拟重新生成一个新的dataSource
     * @return 老的dataSource
     */
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://10.9.113.30:3306/product_slave_beta_dynamic?useUnicode=true&characterEncoding=UTF-8");
        dataSource.setUsername("dev_w");
        dataSource.setPassword("6nvjq0_HW");
        return dataSource;
    }

    /**
     * 异步关闭dataSource
     * @param dataSource
     */
    public void asyncTerminate(HikariDataSource dataSource) {
        DataSourceTerminationTask task = new DataSourceTerminationTask(dataSource, scheduledExecutorService);
        //start now
        scheduledExecutorService.schedule(task, 0, TimeUnit.MILLISECONDS);
    }
}
