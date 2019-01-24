package com.dynamic.config;

import com.dynamic.common.DataSourceKey;
import com.dynamic.utils.DataSourceManager;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Multiple DataSource Configurer
 *
 * @author jibingkun
 */
@Configuration(value = "DataSourceConfigurer")
public class DataSourceConfigurer {

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

//        return DataSourceBuilder.create()
//                .username("root")
//                .password("root")
//                .url("jdbc:mysql://127.0.0.1:3006/product_slave_alpha?useSSL=false")
//                .driverClassName("com.mysql.jdbc.Driver")
//                .build();


//        HikariConfig config = new HikariConfig();
//        config.setPoolName("slaveAlpha");
//        config.setUsername("root");
//        config.setPassword("root");
//        config.setJdbcUrl("jdbc:mysql://127.0.0.1:3006/product_slave_alpha?useSSL=false");
//        config.setDriverClassName("com.mysql.jdbc.Driver");
//        return new HikariDataSource(config);
        return DataSourceBuilder.create().build();
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

    /**
     * Dynamic data source.
     *
     * @return the data source
     */
    @Bean("dynamicDataSourceConfig")
    public DataSource dynamicDataSourceConfig() {

        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();

        DataSourceManager dataSourceManager = new DataSourceManager();

        Map<Object, Object> dataSourceMap = new HashMap<>(6);

        // mapping of thr master datasource
        DynamicDataSource dynamicMasterDataSource = new DynamicDataSource();
        dynamicMasterDataSource.setAndGetDataSource(DataSourceKey.master.name(), master());
        dataSourceMap.put(DataSourceKey.master.name(), dynamicMasterDataSource);

        // mapping of thr slaveAlpha datasource
        DynamicDataSource dynamicSlaveAlphaDataSource = new DynamicDataSource();
        dynamicSlaveAlphaDataSource.setAndGetDataSource(DataSourceKey.slaveAlpha.name(), slaveAlpha());
        dataSourceMap.put(DataSourceKey.slaveAlpha.name(), dynamicSlaveAlphaDataSource);

        // mapping of thr slaveBeta datasource
        DynamicDataSource dynamicSlaveBetaDataSource = new DynamicDataSource();
        dynamicSlaveBetaDataSource.setAndGetDataSource(DataSourceKey.slaveBeta.name(), slaveBeta());
        dataSourceMap.put(DataSourceKey.slaveBeta.name(), dynamicSlaveBetaDataSource);

        // mapping of thr slaveGamma datasource
        DynamicDataSource dynamicSlaveGammaDataSource = new DynamicDataSource();
        dynamicSlaveGammaDataSource.setAndGetDataSource(DataSourceKey.slaveGamma.name(), slaveGamma());
        dataSourceMap.put(DataSourceKey.slaveGamma.name(), dynamicSlaveGammaDataSource);

        // 将 master 数据源作为默认指定的数据源
        dynamicRoutingDataSource.setDefaultTargetDataSource(master());

        // 将 master 和 slave 数据源作为指定的数据源
        dynamicRoutingDataSource.setTargetDataSources(dataSourceMap);

        // 将数据源的 key 放到数据源上下文的 key 集合中，用于切换时判断数据源是否有效
        DynamicRoutingDataSource.SLAVE_DATA_SOURCE_KEYS.addAll(dataSourceMap.keySet());

        // 将 Slave 数据源的 key 放在集合中，用于轮循
        DynamicRoutingDataSource.SLAVE_DATA_SOURCE_KEYS.remove(DataSourceKey.master.name());

        System.out.println(DynamicRoutingDataSource.SLAVE_DATA_SOURCE_KEYS);
        return dynamicRoutingDataSource;
    }

    /**
     * 配置 SqlSessionFactoryBean
     *
     * @return the sql session factory bean
     * @ConfigurationProperties 在这里是为了将 MyBatis 的 mapper 位置和持久层接口的别名设置到Bean的属性中，如果没有使用 *.xml 则可以不用该配置，否则将会产生 invalid bond statement 异常
     */
    @Bean
    @ConfigurationProperties(prefix = "mybatis")
    public SqlSessionFactoryBean sqlSessionFactoryBean() throws IOException {

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();

        // 配置 MyBatis
        sqlSessionFactoryBean.setTypeAliasesPackage("com.dynamic.mapper");
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("mappers/**Mapper.xml"));


        // 配置数据源，此处配置为关键配置，如果没有将 dynamicDataSource 作为数据源则不能实现切换
        sqlSessionFactoryBean.setDataSource(dynamicDataSourceConfig());
        return sqlSessionFactoryBean;
    }

    /**
     * 注入 DataSourceTransactionManager 用于事务管理
     *
     * @return the platform transaction manager
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSourceConfig());
    }
}
