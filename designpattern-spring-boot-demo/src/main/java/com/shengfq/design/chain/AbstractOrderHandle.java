package com.shengfq.design.chain;
/**
 * 责任链模式,定义抽象类，抽象方法
 * 定义链式数据结构,实现链式数据结构
 * */
public abstract class AbstractOrderHandle {

    /**
     * 责任链，下一个链接节点
     */
    private AbstractOrderHandle next;

    /**
     * 执行入口
     * @param context
     * @return
     */
    public OrderContext execute(OrderContext context){
        context = handle(context);
        // 判断是否还有下个责任链节点，没有的话，说明已经是最后一个节点
        if(getNext() != null){
            getNext().execute(context);
        }
        return context;
    }

    /**
     * 对参数进行处理
     * @param context
     * @return
     */
    public abstract OrderContext handle(OrderContext context);


    public AbstractOrderHandle getNext() {
        return next;
    }

    public void setNext(AbstractOrderHandle next) {
        this.next = next;
    }
}
