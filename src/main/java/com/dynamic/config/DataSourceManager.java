package com.dynamic.config;

import com.dynamic.common.DataSourceKey;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author spuerKun
 * @date 2019/1/23.
 */
@Component
public class DataSourceManager {

    /**
     * master DataSource
     *
     * @Primary 注解用于标识默认使用的 DataSource Bean，因为有5个 DataSource Bean，该注解可用于 master
     * 或 slave DataSource Bean, 但不能用于dynamicDataSource Bean, 否则会产生循环调用
     * @ConfigurationProperties 注解用于从 application.properties 文件中读取配置，为 Bean 设置属性
     */
    @Bean("master")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.hikari.master")
    public DataSource master() {

//        HikariConfig config = new HikariConfig();
//        config.setPoolName("master");
//        config.setUsername("root");
//        config.setPassword("root");
//        config.setJdbcUrl("jdbc:mysql://127.0.0.1:3006/product_master?useSSL=false");
//        config.setDriverClassName("com.mysql.jdbc.Driver");
//        return new HikariDataSource(config);
//
//        return DataSourceBuilder.create()
//                .username("root")
//                .password("root")
//                .url("jdbc:mysql://127.0.0.1:3006/product_master?useSSL=false")
//                .driverClassName("com.mysql.jdbc.Driver")
//                .build();
        return DataSourceBuilder.create().build();
    }

    /**
     * Slave alpha data source.
     *
     * @return the data source
     */
    @Bean("slaveAlpha")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.slave-alpha")
    public DataSource slaveAlpha() {

        return DataSourceBuilder.create().type(HikariDataSource.class)
                .username("root")
                .password("root")
                .url("jdbc:mysql://127.0.0.1:3006/product_slave_alpha?useSSL=false")
                .driverClassName("com.mysql.jdbc.Driver")
                .build();

        //return DataSourceBuilder.create().build();
    }

    /**
     * Slave beta data source.
     *
     * @return the data source
     */
    @Bean("slaveBeta")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.slave-beta")
    public DataSource slaveBeta() {

//        return DataSourceBuilder.create()
//                .username("root")
//                .password("root")
//                .url("jdbc:mysql://127.0.0.1:3006/product_slave_beta?useSSL=false")
//                .driverClassName("com.mysql.jdbc.Driver")
//                .build();


//        HikariConfig config = new HikariConfig();
//        config.setPoolName("slaveBeta");
//        config.setUsername("root");
//        config.setPassword("root");
//        config.setJdbcUrl("jdbc:mysql://127.0.0.1:3006/product_slave_beta?useSSL=false");
//        config.setDriverClassName("com.mysql.jdbc.Driver");
//        return new HikariDataSource(config);

        return DataSourceBuilder.create().build();
    }

    /**
     * Slave gamma data source.
     *
     * @return the data source
     */
    @Bean("slaveGamma")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.slave-gamma")
    public DataSource slaveGamma() {

//        return DataSourceBuilder.create()
//                .username("root")
//                .password("root")
//                .url("jdbc:mysql://127.0.0.1:3006/product_slave_gamma?useSSL=false")
//                .driverClassName("com.mysql.jdbc.Driver")
//                .build();

//        HikariConfig config = new HikariConfig();
//        config.setPoolName("slaveGamma");
//        config.setUsername("root");
//        config.setPassword("root");
//        config.setJdbcUrl("jdbc:mysql://127.0.0.1:3006/product_slave_gamma?useSSL=false");
//        config.setDriverClassName("com.mysql.jdbc.Driver");
//        return new HikariDataSource(config);

        return DataSourceBuilder.create().build();
    }

    public Map<String, DataSource> test() {

        Map<String, DataSource> dataSourceMap = new HashMap<>(6);

        Map<Object, Object> dataSourceMap2 = new HashMap<>();

                // mapping of thr master datasource
        DynamicDataSource dynamicMasterDataSource = new DynamicDataSource();
        dynamicMasterDataSource.setDataSource(DataSourceKey.master.name(), master());
        dataSourceMap.put(DataSourceKey.master.name(), master());
        dataSourceMap2.put(DataSourceKey.master.name(), dynamicMasterDataSource);

        // mapping of thr slaveAlpha datasource
        DynamicDataSource dynamicSlaveAlphaDataSource = new DynamicDataSource();
        dynamicSlaveAlphaDataSource.setDataSource(DataSourceKey.slaveAlpha.name(), slaveAlpha());
        dataSourceMap.put(DataSourceKey.slaveAlpha.name(), slaveAlpha());

        // mapping of thr slaveBeta datasource
        DynamicDataSource dynamicSlaveBetaDataSource = new DynamicDataSource();
        dynamicSlaveBetaDataSource.setDataSource(DataSourceKey.slaveBeta.name(), slaveBeta());
        dataSourceMap.put(DataSourceKey.slaveBeta.name(), slaveBeta());

        // mapping of thr slaveGamma datasource
        DynamicDataSource dynamicSlaveGammaDataSource = new DynamicDataSource();
        dynamicSlaveGammaDataSource.setDataSource(DataSourceKey.slaveGamma.name(), slaveGamma());
        dataSourceMap.put(DataSourceKey.slaveGamma.name(), slaveGamma());

        return dataSourceMap;
    }
}
