package com.dynamic.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * @author jibingkun
 * @date 2019/1/22.
 */
@Component
public class DynamicDataSource implements DataSource {

    private final static ConcurrentHashMap<String, DataSource> dataSourceAtomicReference = new ConcurrentHashMap<>();

    /**
     * set the new data source and return the previous one
     */
    public HikariDataSource setAndGetDataSource(String dataSourceKey, DataSource newDataSource) {
        DataSource oldDataSource = dataSourceAtomicReference.get(dataSourceKey);
        dataSourceAtomicReference.put(dataSourceKey, newDataSource);
        return (HikariDataSource) oldDataSource;
    }

    public ConcurrentHashMap<String, DataSource> getDataSourceAtomicReference() {
        return dataSourceAtomicReference;
    }

    @Override
    public Connection getConnection() throws SQLException {
        String dataSourceKey = DynamicRoutingDataSource.getDataSourceKey();
        DataSource dataSource = dataSourceAtomicReference.get(dataSourceKey);
        return dataSource.getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
