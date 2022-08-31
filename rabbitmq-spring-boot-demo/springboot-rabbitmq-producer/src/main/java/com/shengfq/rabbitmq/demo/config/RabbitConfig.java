package com.shengfq.rabbitmq.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static com.shengfq.rabbitmq.demo.config.RabbitProperties.*;

/**
 *RabbitMQ配置，主要是配置队列，如果提前存在该队列，可以省略本配置类
 **/
@Slf4j
@Configuration
public class RabbitConfig {
    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        //设置所有的发送者mandatory=true,表示如果投递失败则会将消息返回给发送者
        rabbitTemplate.setMandatory(true);
        //producer消息确认机制 这个接口是用来确定消息是否到达交换器的
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if(ack){
                log.info("消息投递成功~消息Id：{}", correlationData.getId());
            }else{
                log.error("消息投递失败，Id：{}，错误提示：{}", correlationData.getId(), cause);
            }
        });
        //生产者获取到没有被正确路由到合适队列的消息
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.info("消息没有路由到队列，获得返回的消息");
            log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}", exchange, routingKey, replyCode, replyText, message);
        });
        return rabbitTemplate;
    }

    //队列 起名：TestDirectQueue
    @Bean
    public Queue emailQueue() {
        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
        // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
        //   return new Queue("TestDirectQueue",true,true,false);
        //一般设置一下队列的持久化就好,其余两个就是默认false
        return new Queue(FANOUT_EMAIL_QUEUE, QUEUE_DURABLE);
    }
    @Bean
    public Queue smsQueue() {
        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
        // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
        //   return new Queue("TestDirectQueue",true,true,false);
        //一般设置一下队列的持久化就好,其余两个就是默认false
        return new Queue(FANOUT_SMS_QUEUE, QUEUE_DURABLE);
    }
    @Bean
    public Queue weixinQueue() {
        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
        // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
        //   return new Queue("TestDirectQueue",true,true,false);
        //一般设置一下队列的持久化就好,其余两个就是默认false
        return new Queue(FANOUT_WX_QUEUE, QUEUE_DURABLE);
    }

    //Direct交换机 起名：directExchange
   /* @Bean
    public DirectExchange directExchange() {
        //  return new DirectExchange("TestDirectExchange",true,true);
        return new DirectExchange(DIRECT_EXCHANGE, EXCHANGE_DURABLE, AUTO_DELETE);
    }*/

    //fanout交换机 无差别广播
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    /*@Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(TOPIC_EXCHANGE);
    }*/
    //绑定  将队列和交换机绑定, 并设置用于匹配键：directExchange
    /*@Bean
    public Binding bindingDirect1() {
        return BindingBuilder.bind(weixinQueue()).to(directExchange()).with(WX_ROUTING_KEY);
    }
    @Bean
    public Binding bindingDirect2() {
        return BindingBuilder.bind(smsQueue()).to(directExchange()).with(SMS_ROUTING_KEY);
    }
    @Bean
    public Binding bindingDirect3() {
        return BindingBuilder.bind(emailQueue()).to(directExchange()).with(EMAIL_ROUTING_KEY);
    }*/

    //绑定  将队列和交换机绑定, 并设置用于匹配键：TestDirectRouting
    @Bean
    public Binding bindingFanoutExchange1() {
        return BindingBuilder.bind(weixinQueue()).to(fanoutExchange());
    }
    @Bean
    public Binding bindingFanoutExchange2() {
        return BindingBuilder.bind(smsQueue()).to(fanoutExchange());
    }
    @Bean
    public Binding bindingFanoutExchange3() {
        return BindingBuilder.bind(emailQueue()).to(fanoutExchange());
    }
    /*@Bean
    public Binding bindingTopicExchange1(){
        return BindingBuilder.bind(emailQueue()).to(topicExchange()).with(EMAIL_ROUTING_KEY);
    }
    @Bean
    public Binding bindingTopicExchange2(){
        return BindingBuilder.bind(smsQueue()).to(topicExchange()).with(SMS_ROUTING_KEY);
    }
    @Bean
    public Binding bindingTopicExchange3(){
        return BindingBuilder.bind(weixinQueue()).to(topicExchange()).with(WX_ROUTING_KEY);
    }*/


    /**
     * 死信队列
     * */
    //死信队列
    /*@Bean
    public Queue successKillDeadQueue(){
        Map<String, Object> argsMap= new HashMap<>();
        argsMap.put("x-dead-letter-exchange",RabbitProperties.DEAD_EXCHANGE);
        argsMap.put("x-dead-letter-routing-key",RabbitProperties.DEAD_ROUTING_KEY);
        return new Queue(RabbitProperties.DEAD_QUEUE,true,false,false,argsMap);
    }
    //基本交换机SimpleMessageListenerContainer
    @Bean
    public TopicExchange successKillDeadProdExchange(){
        return new TopicExchange(RabbitProperties.BASE_DEAD_EXCHANGE,true,false);
    }
    //创建基本交换机+基本路由 -> 死信队列 的绑定
    @Bean
    public Binding successKillDeadProdBinding(){
        return BindingBuilder.bind(successKillDeadQueue()).to(successKillDeadProdExchange()).with(RabbitProperties.BASE_DEAD_ROUTING_KEY);
    }
    //真正的队列
    @Bean
    public Queue successKillRealQueue(){
        return new Queue(RabbitProperties.REAL_CONSUMER_QUEUE,true);
    }
    //死信交换机
    @Bean
    public TopicExchange successKillDeadExchange(){
        return new TopicExchange(RabbitProperties.DEAD_EXCHANGE,true,false);
    }
    //死信交换机+死信路由->真正队列 的绑定
    @Bean
    public Binding successKillDeadBinding(){
        return BindingBuilder.bind(successKillRealQueue()).to(successKillDeadExchange()).with(RabbitProperties.DEAD_ROUTING_KEY);
    }*/
}
