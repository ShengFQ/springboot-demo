package com.shengfq.design.chain;

import lombok.Data;

/**
* 责任链中的数据域
* */
@Data
public class OrderContext {

    /**
     * 请求唯一序列ID
     */
    private String seqId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 产品skuId
     */
    private Long skuId;

    /**
     * 下单数量
     */
    private Integer amount;

    /**
     * 用户收货地址ID
     */
    private String userAddressId;

 //..set、get
}
