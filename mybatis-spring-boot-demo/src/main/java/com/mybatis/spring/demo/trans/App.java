package com.mybatis.spring.demo.trans;

public class App {

  public static void testTransaction() {
    TransactionManager tm = new TransactionManager();

    // 创建一个实现了 TransactionOperation 接口的 lambda 表达式
    TransactionOperation<String> operation = () -> {
      System.out.println("执行数据库操作...");
      // 模拟异常
      // throw new RuntimeException("数据库出错！");
      return "操作成功";
    };

    // 使用代理包装 lambda
    TransactionOperation<String> proxy = TransactionProxy.createProxy(operation, tm);

    // 调用并触发事务控制
    try {
      String result = proxy.execute();
      System.out.println(result);
    } catch (Exception e) {
      System.err.println("操作失败: " + e.getMessage());
    }
  }
}
