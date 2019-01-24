package com.dynamic.controller;

import com.dynamic.service.DataSourceRefresher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 暂停原有的数据库连接，并且新建新的数据源
 * <p>
 * 我们在切换数据库连接的时候，如果老的连接没有做任何的处理，可能造成连接泄漏。
 * 因此我们完全切换成新的数据库连接前，我们需要获取到老的连接池，并且校验是否有活动链接，直到没有任何活动链接时，我们需要关闭老的连接
 *
 * @author jibjingkun
 * @date 2019/1/21.
 */
@RestController
@RequestMapping("/dynamic")
public class DynamicReviseController {

    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    @Autowired
    private DataSourceRefresher dataSourceRefresher;

    /**
     * curl -X GET http://localhost:8080/dynamic/revise
     * <p>
     * 再一次调用查询的时候，结果为：
     * slaveBetaDynamic
     * slaveBeta
     * slaveGamma
     */
    @GetMapping("/revise")
    public void terminateDataSource() {
        dataSourceRefresher.refreshDataSource();
    }
}
