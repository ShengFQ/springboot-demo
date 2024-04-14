package com.shengfq.design.chain;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
/**
 * 业务处理类,银行卡校验器
 * */
@Order(3)
@Component
public class BankOrderHandle extends AbstractOrderHandle {

    @Override
    public OrderContext handle(OrderContext context) {
        System.out.println("检查银行账户是否合法，调用银行系统检查银行账户余额是否满足下单金额");
        return context;
    }
}
