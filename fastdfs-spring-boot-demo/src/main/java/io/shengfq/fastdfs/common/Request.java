package io.shengfq.fastdfs.common;

import lombok.Data;
import lombok.ToString;

/**
 * ClassName: RequestParam
 * Description: 请求参数
 *
 * @author shengfq
 * @date: 2023/4/13 4:22 下午
 */
@Data
@ToString
public class Request {
  public static final String DATA="data";
  public static final String SIGN="sign";
  public static final String TIMESTAMP="timestamp";

  private  String sign;
    private  String timestamp;
    private  String data;
}
