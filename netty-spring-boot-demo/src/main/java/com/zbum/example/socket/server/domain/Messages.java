package com.zbum.example.socket.server.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: Message
 * Description: 消息对象
 *
 * @author shengfq
 * @date: 2023/3/24 1:54 下午
 */
@Data
public class Messages implements Serializable {
    private int code;

    private String content;

}
