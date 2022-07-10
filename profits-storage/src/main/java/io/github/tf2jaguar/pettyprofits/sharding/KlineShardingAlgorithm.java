package io.github.tf2jaguar.pettyprofits.sharding;

import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author : zhangguodong
 * @since : 2022/7/9 22:29
 */
public class KlineShardingAlgorithm implements PreciseShardingAlgorithm<Date>, RangeShardingAlgorithm<Date> {
    private static final Logger log = LoggerFactory.getLogger(KlineShardingAlgorithm.class);

    private static final String SHARDING_TABLE = "t_stock_kline";

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Date> shardingValue) {
        Date date = shardingValue.getValue();
        String year = String.format("%tY", date);
        return shardingValue.getLogicTableName() + "_" + year;
    }

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Date> shardingValue) {
        Range<Date> valueRange = shardingValue.getValueRange();
        String upperYear = String.format("%tY", valueRange.upperEndpoint());
        String lowerYear = String.format("%tY", valueRange.lowerEndpoint());
        int upperYearInt = Integer.parseInt(upperYear);
        int lowerYearInt = Integer.parseInt(lowerYear);
        return availableTargetNames.stream().filter(a -> {
            String availableYearStr = a.replace(SHARDING_TABLE + "_", "");
            int availableYearInt = Integer.parseInt(availableYearStr);
            return availableYearInt >= lowerYearInt && availableYearInt <= upperYearInt;
        }).collect(Collectors.toList());
    }
}
