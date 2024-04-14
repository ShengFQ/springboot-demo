package com.shengfq.design.chain;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
/**
 * 业务处理类,参数校验器
 * */
@Order(2)
@Component
public class ValidOrderHandle extends AbstractOrderHandle {

    @Override
    public OrderContext handle(OrderContext context) {
        System.out.println("检查请求参数，是否合法，并且获取客户的银行账户");
        return context;
    }
}
