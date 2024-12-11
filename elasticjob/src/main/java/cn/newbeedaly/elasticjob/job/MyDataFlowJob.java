package cn.newbeedaly.elasticjob.job;

import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.dataflow.job.DataflowJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class MyDataFlowJob implements DataflowJob {

    private static final Logger logger = LoggerFactory.getLogger(MyDataFlowJob.class);

    @Override
    public List fetchData(ShardingContext shardingContext) {
        logger.info("data flow job fetch data..");
        return Collections.singletonList("newbeedaly");
    }

    @Override
    public void processData(ShardingContext shardingContext, List data) {
        logger.info("data flow job process data..");
    }

}
