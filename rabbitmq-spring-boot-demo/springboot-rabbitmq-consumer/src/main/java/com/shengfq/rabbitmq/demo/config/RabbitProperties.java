package com.shengfq.rabbitmq.demo.config;

/**
 * ClassName: RabbitProperties
 * Description: 常量
 *
 * @author shengfq
 * @date: 2022/8/17 9:51 下午
 */
public interface RabbitProperties {
    boolean QUEUE_DURABLE=true,EXCHANGE_DURABLE=false,AUTO_DELETE=true;
    String DIRECT_EXCHANGE="order_direct_exchange";
    String DIRECT_EMAIL_QUEUE ="email.direct.queue";
    String DIRECT_SMS_QUEUE="sms.direct.queue";
    String DIRECT_WX_QUEUE ="weixin.direct.queue";

    String FANOUT_EXCHANGE="order_fanout_exchange";
    String FANOUT_EMAIL_QUEUE ="email.fanout.queue";
    String FANOUT_SMS_QUEUE="sms.fanout.queue";
    String FANOUT_WX_QUEUE ="weixin.fanout.queue";

    String TOPIC_EXCHANGE="order_topic_exchange";
    String EMAIL_ROUTING_KEY="*.email";
    String SMS_ROUTING_KEY="#.sms.#";
    String WX_ROUTING_KEY="weixin.#";

    //String DELAY_EXCHANGE="order_delay_exchange";
    String DEAD_EXCHANGE="dead.exchange";
    String DEAD_ROUTING_KEY="dead.routing.key";
    String DEAD_QUEUE="dead.queue";

    String BASE_DEAD_EXCHANGE="proxy.dead.exchange";
    String BASE_DEAD_ROUTING_KEY="proxy.routing.key";
    String REAL_CONSUMER_QUEUE="real.consumer.queue";
    //消息的存活时间TTL
    int KILL_EXPIRE=2000;
}
