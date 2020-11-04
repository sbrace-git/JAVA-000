package io.github.kimmking.gateway.router;

public enum RouterEnum {
    /**
     * 随机
     */
    RANDOM,
    /**
     * 轮询
     */
    ROUND_RIBBON,
    /**
     * 权重
     */
    WEIGHT
}
