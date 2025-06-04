package com.mybatis.spring.demo.trans;

@FunctionalInterface
public interface TransactionOperation<T> {
  T execute() throws Exception;
}
