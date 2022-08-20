package io.github.tf2jaguar.pettyprofits.runner;

/**
 * @author : zhangguodong
 * @since : 2022/8/18 13:53
 */
public abstract class Runner {

    public abstract void run();

    public abstract void doStrategy();

    public abstract void trySell();

    public abstract void tryBuy();

    // todo 通用过滤股票
}
