package cn.newbeedaly.kafka.kafka;

public enum AckMode {

    /**
     * 自动提交offset
     */
    AUTOMATIC,
    /**
     * 手动提交offset
     */
    MANUAL;

}
