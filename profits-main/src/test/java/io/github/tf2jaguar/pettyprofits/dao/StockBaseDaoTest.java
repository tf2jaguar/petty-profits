package io.github.tf2jaguar.pettyprofits.dao;

import io.github.tf2jaguar.pettyprofits.BaseTest;
import io.github.tf2jaguar.pettyprofits.entity.StockBase;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : zhangguodong
 * @since : 2022/7/3 12:19
 */
public class StockBaseDaoTest extends BaseTest {

    @Resource
    private StockBaseDao stockBaseDao;

    @Test
    public void testSelect() {
        List<StockBase> selectAll = stockBaseDao.selectAll();
        System.out.println(selectAll);
    }

}