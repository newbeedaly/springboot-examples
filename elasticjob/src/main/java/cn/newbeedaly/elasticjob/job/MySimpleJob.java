package cn.newbeedaly.elasticjob.job;

import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author newbeedaly
 */
@Component
public class MySimpleJob implements SimpleJob {

    private static final Logger logger = LoggerFactory.getLogger(MySimpleJob.class);

    @Override
    public void execute(ShardingContext context) {
        switch (context.getShardingItem()) {
            case 0:
                logger.info("------Thread ID: {}, 任務總片數: {}, 当前分片項: {}.当前分片參數: {}, 当前任務名稱: {}.当前任務參數: {}",
                        Thread.currentThread().getId(), context.getShardingTotalCount(), 0,
                        context.getShardingParameter(), context.getJobName(), context.getJobParameter()
                );
                break;
            case 1:
                logger.info("------Thread ID: {}, 任務總片數: {}, 当前分片項: {}.当前分片參數: {}, 当前任務名稱: {}.当前任務參數: {}",
                        Thread.currentThread().getId(), context.getShardingTotalCount(), 1,
                        context.getShardingParameter(), context.getJobName(), context.getJobParameter()
                );
                break;
            // case n: ...
            default:
                logger.info("------Thread ID: {}, 任務總片數: {}, 当前分片項: {}.当前分片參數: {}, 当前任務名稱: {}.当前任務參數: {}",
                        Thread.currentThread().getId(), context.getShardingTotalCount(), context.getShardingItem(),
                        context.getShardingParameter(), context.getJobName(), context.getJobParameter()
                );
                break;
        }

    }

}
