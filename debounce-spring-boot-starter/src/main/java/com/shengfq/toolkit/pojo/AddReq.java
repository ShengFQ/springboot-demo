package com.shengfq.toolkit.pojo;

import java.util.List;

import com.shengfq.toolkit.debounce.RequestKeyParam;

import lombok.Data;

@Data
public class AddReq {
  /**
   * 用户名称
   */
  private String userName;

  /**
   * 用户手机号
   */
  private String userPhone;

  /**
   * 角色ID列表
   */
  private List<Long> roleIdList;

  @RequestKeyParam
  private String requestToken;
}
