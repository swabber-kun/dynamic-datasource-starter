package com.dynamic.config;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 暂停原有的链接
 *
 * @author jibingkun
 * @date 2019/1/2.
 */
public class DataSourceTerminationTask {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceTerminationTask.class);

    private volatile int retryTimes;

    private static final int MAX_RETRY_TIMES = 10;

    private static final int RETRY_DELAY_IN_MILLISECONDS = 5000;

    public boolean terminationDataSource(HikariDataSource dataSource) {
        HikariPoolMXBean poolMXBean = dataSource.getHikariPoolMXBean();

        poolMXBean.softEvictConnections();

        if (poolMXBean.getActiveConnections() > 0 && retryTimes < MAX_RETRY_TIMES) {
            logger.info("Data source {} still has {} active connections, will retry in {} ms.", dataSource, poolMXBean.getActiveConnections(), RETRY_DELAY_IN_MILLISECONDS);
            return false;
        }

        if (poolMXBean.getActiveConnections() > 0) {
            logger.info("Retry times({}) >= {}, force closing data source {}, with {} active connections!", retryTimes, MAX_RETRY_TIMES, dataSource, poolMXBean.getActiveConnections());
        }

        dataSource.close();
        return true;
    }
}
