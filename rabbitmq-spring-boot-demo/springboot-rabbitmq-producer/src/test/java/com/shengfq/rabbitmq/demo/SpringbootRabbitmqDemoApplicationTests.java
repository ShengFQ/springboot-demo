package com.shengfq.rabbitmq.demo;

import com.shengfq.rabbitmq.demo.config.MessageStruct;
import com.shengfq.rabbitmq.demo.config.RabbitProperties;

import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@Slf4j

@SpringBootTest
class SpringbootRabbitmqDemoApplicationTests {
    enum COMMAND{
        direct,Fanout,topic1,topic2,topic3,delay,expire;
    }
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 测试直接模式发送
     */
    @Test
    public void sendDirect() {
        //direct 直连模式通过exchange和routing key消息直达到绑定routing key的queue.
        multiThread(COMMAND.direct);
    }

    /**
     * 测试分列模式发送
     */
    @Test
    public void sendFanout() {
        //fanout 广播模式通过exchange将消息广播到绑定到该exchange的queue.
        multiThread(COMMAND.Fanout);
    }

    /**
     * 测试主题模式发送1
     */
    @Test
    public void sendTopic1() {
        multiThread(COMMAND.topic1);
    }

    /**
     * 测试主题模式发送2
     */
    @Test
    public void sendTopic2() {
        multiThread(COMMAND.topic2);
    }

    /**
     * 测试主题模式发送3
     */
    @Test
    public void sendTopic3() {
        multiThread(COMMAND.topic3);
    }

    /**
     * 测试延迟队列发送
     */
    @Test
    public void sendDelay() {
        multiThread(COMMAND.delay);
    }
    /**
     * 测试超时死信队列发送
     */
    @Test
    public void sendDead(){
        multiThread(COMMAND.expire);
    }
    //需要exchange routing key
    private void sendDirectMsg(){
        log.info("send direct message begin--------------");
        String msgId = UUID.randomUUID().toString().replace("-", "").substring(0, 32);
        CorrelationData correlationData = new CorrelationData(msgId);
        rabbitTemplate.convertAndSend(RabbitProperties.DIRECT_EXCHANGE,RabbitProperties.EMAIL_ROUTING_KEY, new MessageStruct("direct email message").toString(),correlationData);
        rabbitTemplate.convertAndSend(RabbitProperties.DIRECT_EXCHANGE,RabbitProperties.SMS_ROUTING_KEY, new MessageStruct("direct sms message").toString(),correlationData);
        rabbitTemplate.convertAndSend(RabbitProperties.DIRECT_EXCHANGE,RabbitProperties.WX_ROUTING_KEY, new MessageStruct("direct wx message").toString(),correlationData);
    }
    //不需要exchange 和routing key
    private void sendFanoutMsg(){
        log.info("send fanout exchange message begin--------------");
        String msgId = UUID.randomUUID().toString().replace("-", "").substring(0, 32);
        CorrelationData correlationData = new CorrelationData(msgId);
        rabbitTemplate.convertAndSend(RabbitProperties.FANOUT_EXCHANGE,"",new MessageStruct("fanout email message").toString(),correlationData);
    }

    private void sendTopic1Msg(){
        log.info("send topic exchange message begin--------------");
        String msgId = UUID.randomUUID().toString().replace("-", "").substring(0, 32);
        CorrelationData correlationData = new CorrelationData(msgId);
        rabbitTemplate.convertAndSend(RabbitProperties.TOPIC_EXCHANGE,"233.email",new MessageStruct("topic email message").toString(),correlationData);
    }
    private void sendTopic2Msg(){
        log.info("send topic exchange message begin--------------");
        String msgId = UUID.randomUUID().toString().replace("-", "").substring(0, 32);
        CorrelationData correlationData = new CorrelationData(msgId);
        rabbitTemplate.convertAndSend(RabbitProperties.TOPIC_EXCHANGE,"233.sms.ecms",new MessageStruct("topic sms message").toString(),correlationData);
    }
    private void sendTopic3Msg(){
        log.info("send topic exchange message begin--------------");
        String msgId = UUID.randomUUID().toString().replace("-", "").substring(0, 32);
        CorrelationData correlationData = new CorrelationData(msgId);
        rabbitTemplate.convertAndSend(RabbitProperties.TOPIC_EXCHANGE,"weixin.233.bms",new MessageStruct("topic weixin message").toString(),correlationData);
    }

    private void sendDelayMsg(){
        rabbitTemplate.convertAndSend(RabbitProperties.TOPIC_EXCHANGE,"233.email", new MessageStruct("delay message, delay 5s, " + System.currentTimeMillis()).toString(), message -> {
            message.getMessageProperties().setHeader("x-delay", 5000);
            return message;
        });
        rabbitTemplate.convertAndSend(RabbitProperties.TOPIC_EXCHANGE, "233.sms.ecms", new MessageStruct("delay message,  delay 2s, " + System.currentTimeMillis()).toString(), message -> {
            message.getMessageProperties().setHeader("x-delay", 2000);
            return message;
        });
        rabbitTemplate.convertAndSend(RabbitProperties.TOPIC_EXCHANGE,"weixin.233.bms", new MessageStruct("delay message,  delay 8s, " + System.currentTimeMillis()).toString(), message -> {
            message.getMessageProperties().setHeader("x-delay", 8000);
            return message;
        });
    }

    //秒杀成功后生成抢购订单-发送信息入死信队列，等待着一定时间失效超时未支付的订单
    public void sendKillExpireMsg(){
        String orderCode="System.SSID";
        try {
            if (StringUtils.isNotBlank(orderCode)){
                    rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                    rabbitTemplate.setExchange(RabbitProperties.BASE_DEAD_EXCHANGE);
                    rabbitTemplate.setRoutingKey(RabbitProperties.BASE_DEAD_ROUTING_KEY);
                    rabbitTemplate.convertAndSend(new MessageStruct("dead message, delay 5s"), new MessagePostProcessor() {
                        @Override
                        public Message postProcessMessage(Message message) throws AmqpException {
                            MessageProperties mp=message.getMessageProperties();
                            mp.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                            mp.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,MessageStruct.class);
                            //动态设置TTL(为了测试方便，暂且设置5s)
                            mp.setExpiration(RabbitProperties.KILL_EXPIRE+"");
                            return message;
                        }
                    });

            }
        }catch (Exception e){
            log.error("秒杀成功后生成抢购订单-发送信息入死信队列，等待着一定时间失效超时未支付的订单-发生异常，消息为：{}",orderCode,e.fillInStackTrace());
        }
    }

    /**
     * 单线程测试发送多条
     * */
    private void multiCount(COMMAND command,int count){
        for (int i = 0; i < count; i++) {
            switch(command){
                case direct:
                    sendDirectMsg();
                    break;
                case Fanout:
                    sendFanoutMsg();
                    break;
                case topic1:
                    sendTopic1Msg();
                    break;
                case topic2:
                    sendTopic2Msg();
                    break;
                case topic3:
                    sendTopic3Msg();
                    break;
                case delay:
                    sendDelayMsg();
                    break;
                case expire:
                    sendKillExpireMsg();
            }
        }
    }
    /**
     * 多线程测试
     * */
    private void multiThread(COMMAND command){
        StopWatch stopWatch=new StopWatch();
        stopWatch.start();
        int threads = 1,count=10;
        ExecutorService executorService = Executors.newFixedThreadPool(threads);
        final CountDownLatch start = new CountDownLatch(1);
        final CountDownLatch end = new CountDownLatch(threads);
        for (int i = 0; i < threads; i++) {
            executorService.execute(() -> {
                try {
                    start.await();
                    multiCount(command,count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    end.countDown();
                }
            });
        }
        start.countDown();
        try {
            end.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
        stopWatch.stop();
        System.out.println("发送消息耗时:"+stopWatch.getTotalTimeSeconds());
    }

}
