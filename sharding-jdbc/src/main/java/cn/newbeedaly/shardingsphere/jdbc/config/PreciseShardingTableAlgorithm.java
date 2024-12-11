package cn.newbeedaly.shardingsphere.jdbc.config;

import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.util.Collection;
import java.util.Properties;

/**
 * sharding-jdbc 精准`分表`策略
 **/
@Slf4j
public class PreciseShardingTableAlgorithm implements StandardShardingAlgorithm<String> {


    /**
     * @param tableNames           所有表名
     * @param preciseShardingValue 精确分片值，包括（columnName，logicTableName，value）
     * @return 表名
     * @description 分表策略，按用户编号倒数二三位数字对数据库表数量取模
     */
    @Override
    public String doSharding(Collection<String> tableNames, PreciseShardingValue<String> preciseShardingValue) {
        log.info("Table PreciseShardingAlgorithm tableNames:{} ,preciseShardingValue: {}.", Joiner.on(",").join(tableNames),
                preciseShardingValue.getValue());
        // 使用sharding-column对表数量取模，截取用户编号最后1位数字，（如1234的最后一位为4）
        String columnLastNum = StringUtils.substring(preciseShardingValue.getValue(), -1);
        int mod = Integer.parseInt(columnLastNum) % tableNames.size();
        for (String tableName : tableNames) {
            // 分表的规则
            if (tableName.endsWith(String.valueOf(mod))) {
                return tableName;
            }
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<String> rangeShardingValue) {
        return null;
    }

    @Override
    public Properties getProps() {
        return null;
    }

    @Override
    public void init(Properties properties) {

    }
}