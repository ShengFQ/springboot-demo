package com.elasticsearch.spring.demo.dto.req;

import java.io.Serializable;

/**
 * ClassName: WebContentReq
 * Description: 搜索条件
 *
 * @author shengfq
 * @date: 2023/3/11 5:48 下午
 */
public class WebContentReq implements Serializable {
    /**
     * 价格
     * */
    private String price;
    /**
     * 标题
     * */
    private String title;
    /**
     * 评价数量
     */
    private String commit;
    /**
     * 商品店
     */
    private String shop;
    /**
     * 商品供货地
     */
    private String province;
}
