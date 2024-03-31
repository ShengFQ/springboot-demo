package com.shengfq.dynamic.service;

import java.util.List;
/**
 * service层接口,抽象出业务层方法,不管具体实现
 * @author sheng
 * @date 2024-03-31
 * */
public interface UserService {
    public List selectAll();
    public List selectByCondition();
}
