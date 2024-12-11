package cn.newbeedaly.elasticjob.config;

import org.apache.shardingsphere.elasticjob.infra.listener.ElasticJobListener;
import org.apache.shardingsphere.elasticjob.infra.listener.ShardingContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author newbeedaly
 */
@Component
public class MyElasticJobListener implements ElasticJobListener {

    private static final Logger logger = LoggerFactory.getLogger(MyElasticJobListener.class);

    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
        long beginTime = System.currentTimeMillis();
        logger.info("===> {} JOB BEGIN TIME: {} <===", shardingContexts.getJobName(), beginTime);
    }

    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        long endTime = System.currentTimeMillis();
        logger.info("===> {} JOB END TIME: {}, SHARDING TOTAL COUNT: {} <===", shardingContexts.getJobName(), endTime, shardingContexts.getShardingTotalCount());
    }

    @Override
    public String getType() {
        // simpleListener distributeListener
        return "simpleJobListener";
    }
}
