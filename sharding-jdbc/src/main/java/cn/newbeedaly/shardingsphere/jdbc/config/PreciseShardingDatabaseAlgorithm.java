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
 * sharding-jdbc 精准 `分库` 策略
 **/
@Slf4j
public class PreciseShardingDatabaseAlgorithm implements StandardShardingAlgorithm<String> {

    /**
     * 分库策略，按用户编号最后一位数字对数据库数量取模
     *
     * @param dbNames              所有库名
     * @param preciseShardingValue 精确分片值，包括（columnName，logicTableName，value）
     * @return 库名
     */
    @Override
    public String doSharding(Collection<String> dbNames, PreciseShardingValue<String> preciseShardingValue) {
        log.info("Database PreciseShardingAlgorithm dbNames:{} ,preciseShardingValue: {}.", Joiner.on(",").join(dbNames),
                preciseShardingValue.getValue());

        if (dbNames.size() == 1) {
            return dbNames.iterator().next();
        }
        // 使用sharding-column对数据库数量取模
        String columnLastNum = StringUtils.substring(preciseShardingValue.getValue(), -1);
        int mod = Integer.parseInt(columnLastNum) % dbNames.size();
        for (String dbName : dbNames) {
            // 分库的规则
            if (dbName.endsWith(String.valueOf(mod))) {
                return dbName;
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
