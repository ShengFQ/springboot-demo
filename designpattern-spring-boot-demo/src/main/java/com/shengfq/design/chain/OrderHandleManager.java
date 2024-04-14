package com.shengfq.design.chain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
/**
 * 工厂类,将责任链的各个节点组装起来
 * */
@Component
public class OrderHandleManager {
    /**
     * 链式存储
     * */
    @Autowired
    private List<AbstractOrderHandle> orderHandleList;

    /**
     * 容器初始化就建立链表
     * */
    @PostConstruct
    public void init(){
        //如果List没有按照@Order注解方式排序，可以通过如下方式手动排序
        Collections.sort(orderHandleList, AnnotationAwareOrderComparator.INSTANCE);
        int size = orderHandleList.size();
        for (int i = 0; i < size; i++) {
            if(i == size -1){
                orderHandleList.get(i).setNext(null);
            } else {
                orderHandleList.get(i).setNext(orderHandleList.get(i + 1));
            }
        }

    }

    /**
     * 执行处理
     * @param context
     * @return
     */
    public OrderContext execute(OrderContext context){
        context = orderHandleList.get(0).execute(context);
        return context;
    }
}
