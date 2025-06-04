package com.mybatis.spring.demo.trans;

public class TransactionManager {

  public void beginTransaction() {
    System.out.println("开启事务");
  }

  public void commit() {
    System.out.println("提交事务");
  }

  public void rollback() {
    System.out.println("回滚事务");
  }
}
