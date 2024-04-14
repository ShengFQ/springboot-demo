package com.shengfq.design.chain;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
/**
 *业务处理类,是否重复校验器
 * */
@Order(1)
@Component
public class RepeatOrderHandle extends AbstractOrderHandle {

    @Override
    public OrderContext handle(OrderContext context) {
        System.out.println("通过seqId，检查客户是否重复下单");
        return context;
    }
}
