package io.github.tf2jaguar.pettyprofits.sharding;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Date;

/**
 * @author : zhangguodong
 * @since : 2022/7/9 22:29
 */
public class KlineShardingAlgorithm implements PreciseShardingAlgorithm<Date> {
    private static final Logger log = LoggerFactory.getLogger(KlineShardingAlgorithm.class);

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Date> shardingValue) {
        Date date = shardingValue.getValue();
        String year = String.format("%tY", date);
        return shardingValue.getLogicTableName() + "_" + year;
    }
}
