package io.shengfq.aop.common;

import io.shengfq.aop.domain.LoginVal;

/**
 * @author 公众号：码猿技术专栏
 * @description 用户上下文信息 https://juejin.cn/post/7184599118482767909
 */
public class OauthContext {
  /*
   * private static final TransmittableThreadLocal<LoginVal> loginValThreadLocal = new
   * TransmittableThreadLocal<>();
   */
  private static final ThreadLocal<LoginVal> loginValThreadLocal = new ThreadLocal<>();

  public static LoginVal get() {
    return loginValThreadLocal.get();
  }

  public static void set(LoginVal loginVal) {
    loginValThreadLocal.set(loginVal);
  }

  public static void clear() {
    loginValThreadLocal.remove();
  }
}
