package com.mybatis.spring.demo.pool;

/**
 * @ClassName ICallback
 * @Description 回调方法
 * @Version
 */
public interface ICallback {

    /**
     * 回调方法
     * @param isSuccess true ： 执行成功结束，false：try catch到异常结束
     */
    void callback(boolean isSuccess);
}
