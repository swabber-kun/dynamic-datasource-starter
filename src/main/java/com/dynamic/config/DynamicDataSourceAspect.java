package com.dynamic.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Multiple DataSource Aspect
 *
 * @author jibingkun
 */
@Aspect
@Component
public class DynamicDataSourceAspect {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    private final String[] QUERY_PREFIX = {"select"};

    /**
     * set aop pointcut
     */
    @Pointcut("execution( * com.dynamic.mapper.*.*(..))")
    public void daoAspect() {

    }

    /**
     * do something before, such switch data source...
     */
    @Before("daoAspect()")
    public void switchDataSource(JoinPoint joinPoint) {
        boolean isUseSlave = isUseSlaveDb(joinPoint.getSignature().getName());
        if (isUseSlave) {
            DynamicRoutingDataSource.useSlaveDataSource();
            logger.info("use salve db Switch DataSource to [{}] in Method [{}]", DynamicRoutingDataSource.getDataSourceKey(), joinPoint.getSignature());
        } else {
            DynamicRoutingDataSource.userMasterDataSource();
            logger.info("use master db Switch DataSource to [{}] in Method [{}]", DynamicRoutingDataSource.getDataSourceKey(), joinPoint.getSignature());
        }
    }

    /**
     * do something after, such restore data source...
     */
    @After("daoAspect()")
    public void restoreDataSource(JoinPoint joinPoint) {
        DynamicRoutingDataSource.clearDataSourceKey();
        logger.debug("Restore DataSource to [{}] in Method [{}]", DynamicRoutingDataSource.getDataSourceKey(), joinPoint.getSignature());
    }

    /**
     * 是否使用从库的策略开放给业务方，如果想使用从库，便在数据库中结尾新增salve标志，比如:
     * 主库：order
     * 从库：order_salve
     */
    private boolean isUseSlaveDb(String methodName) {
        for (String prefix : QUERY_PREFIX) {
            if (methodName.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }
}
