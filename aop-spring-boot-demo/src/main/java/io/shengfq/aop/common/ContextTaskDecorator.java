package io.shengfq.aop.common;

import org.springframework.core.task.TaskDecorator;

import io.shengfq.aop.domain.LoginVal;


/**
 * @author 公众号：码猿技术专栏
 * @description 上下文装饰器
 */
public class ContextTaskDecorator implements TaskDecorator {
  @Override
  public Runnable decorate(Runnable runnable) {
    // 获取父线程的loginVal
    LoginVal loginVal = OauthContext.get();
    return () -> {
      try {
        // 将主线程的请求信息，设置到子线程中
        OauthContext.set(loginVal);
        // 执行子线程，这一步不要忘了
        runnable.run();
      } finally {
        // 线程结束，清空这些信息，否则可能造成内存泄漏
        OauthContext.clear();
      }
    };
  }
}
