package com.dynamic.config;

import com.dynamic.common.DataSourceKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author jibingkun
 */
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    private static final Logger logger = LoggerFactory.getLogger(DynamicRoutingDataSource.class);

    private static ThreadLocal<String> CONTEXT_HOLDER = ThreadLocal.withInitial(DataSourceKey.master::name);

    /**
     * the count of salve
     */
    private static AtomicLong counter = new AtomicLong(0);

    /**
     * The list of slave dataSource keys.
     */
    public static List<Object> SLAVE_DATA_SOURCE_KEYS = new ArrayList<>();

    /**
     * Set dynamic DataSource to Application Context
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * clear dataSource context
     */
    protected static void clearDataSourceKey() {
        CONTEXT_HOLDER.remove();
    }

    /**
     * Get current DataSource
     *
     * @return data source key
     */
    protected static String getDataSourceKey() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * use master datasource
     */
    protected static void userMasterDataSource() {
        CONTEXT_HOLDER.set(DataSourceKey.master.name());
    }

    /**
     * use slave datasource
     */
    protected static void useSlaveDataSource() {
        try {
            int index = (int) (counter.longValue() % SLAVE_DATA_SOURCE_KEYS.size());
            CONTEXT_HOLDER.set(String.valueOf(SLAVE_DATA_SOURCE_KEYS.get(index)));
            counter.incrementAndGet();
        } catch (Exception ex) {
            logger.error("Switch slave datasource failed, error message is {},{}", ex.getMessage(), ex);
            // if catch Exception -> just use master
            userMasterDataSource();
        }
    }

    public void refreshDataSource(){
        logger.info("Refreshing data source");
    }
}
