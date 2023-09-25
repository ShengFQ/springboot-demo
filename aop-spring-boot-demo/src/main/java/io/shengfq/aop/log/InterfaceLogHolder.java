package io.shengfq.aop.log;

import java.util.Objects;

import io.shengfq.aop.domain.enums.LogStatusEnum;
import io.shengfq.aop.domain.vo.InterfaceLogVo;


/**
 * 当前线程接口日志ThreadLocalHolder
 *
 * @author guoy59
 * @date 2022-10-8
 * @version 1.0
 */
public class InterfaceLogHolder {

  /**
   * 初始化
   */
  private static final ThreadLocal<InterfaceLogVo> holder = new ThreadLocal<>();

  /**
   * 私有化构造方法
   */
  private InterfaceLogHolder() {}

  /**
   * 设置
   *
   * @param interfaceLogVo
   */
  public static void set(InterfaceLogVo interfaceLogVo) {
    holder.set(interfaceLogVo);
  }

  /**
   * 获取
   *
   * @return
   */
  public static InterfaceLogVo get() {
    return holder.get();
  }

  /**
   * 清除
   *
   */
  public static void clear() {
    holder.remove();
  }

  /**
   * 设置请求参数
   *
   */
  public static String getRequestMsg() {
    InterfaceLogVo interfaceLogVo = InterfaceLogHolder.get();
    if (Objects.nonNull(interfaceLogVo)) {
      return interfaceLogVo.getRequestMsg();
    } else {
      return null;
    }
  }

  /**
   * 设置请求参数
   *
   * @param requestMsg
   */
  public static void setRequestMsg(String requestMsg) {
    InterfaceLogVo interfaceLogVo = InterfaceLogHolder.get();
    if (Objects.nonNull(interfaceLogVo)) {
      interfaceLogVo.setRequestMsg(requestMsg);
    }
  }

  /**
   * 设置工厂信息
   *
   * @param facilityCode
   * @param facilityName
   */
  public static void setFacility(String facilityCode, String facilityName) {
    InterfaceLogVo interfaceLogVo = InterfaceLogHolder.get();
    if (Objects.nonNull(interfaceLogVo)) {
      interfaceLogVo.setFactory(facilityCode);
      interfaceLogVo.setFactoryName(facilityName);
    }
  }

  /**
   * 设置trackingNo
   *
   * @param trackingNo
   */
  public static void setTrackingNo(String trackingNo) {
    InterfaceLogVo interfaceLogVo = InterfaceLogHolder.get();
    if (Objects.nonNull(interfaceLogVo)) {
      interfaceLogVo.setTrackingNo(trackingNo);
    }
  }

  /**
   * 设置key
   *
   * @param key
   */
  public static void setKey(String key) {
    InterfaceLogVo interfaceLogVo = InterfaceLogHolder.get();
    if (Objects.nonNull(interfaceLogVo)) {
      interfaceLogVo.setKey(key);
    }
  }

  /**
   * 设置响应
   *
   * @param responseMsg
   * @param okCondition
   */
  public static void setResponse(String responseMsg, boolean okCondition) {
    InterfaceLogVo interfaceLogVo = InterfaceLogHolder.get();
    if (Objects.nonNull(interfaceLogVo)) {
      interfaceLogVo.setResponseMsg(responseMsg);
      interfaceLogVo
          .setStatus(okCondition ? LogStatusEnum.FINISH.getCode() : LogStatusEnum.FAIL.getCode());
    }
  }

  /**
   * 设置失败的响应
   *
   * @param ex
   */
  public static void setFailResponse(Exception ex) {
    InterfaceLogVo interfaceLogVo = InterfaceLogHolder.get();
    if (Objects.nonNull(interfaceLogVo)) {
      interfaceLogVo.setResponseMsg(ex.getMessage());
      interfaceLogVo.setStatus(LogStatusEnum.FAIL.getCode());
    }
  }
}
