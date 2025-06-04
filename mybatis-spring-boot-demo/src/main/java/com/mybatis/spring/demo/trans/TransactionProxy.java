package com.mybatis.spring.demo.trans;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TransactionProxy implements InvocationHandler {

  private final Object target;
  private final TransactionManager transactionManager;

  private TransactionProxy(Object target, TransactionManager transactionManager) {
    this.target = target;
    this.transactionManager = transactionManager;
  }

  public static <T> T createProxy(T target, TransactionManager transactionManager) {
    return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(),
        new Class[] {target.getClass().getInterfaces()[0]}, // 假设只有一个接口
        new TransactionProxy(target, transactionManager));
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    if (method.isAnnotationPresent(Transactional.class)) {
      try {
        transactionManager.beginTransaction();
        Object result = method.invoke(target, args);
        transactionManager.commit();
        return result;
      } catch (Exception e) {
        transactionManager.rollback();
        throw e;
      }
    } else {
      return method.invoke(target, args);
    }
  }
}
