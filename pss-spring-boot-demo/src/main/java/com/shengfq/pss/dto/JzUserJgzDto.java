package com.shengfq.pss.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
* @description 用户照片(警官证)表
* @author shengfq
* @date 2022年09月19日 13:34:53
*/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class JzUserJgzDto implements Serializable {

    private Long id;

    private String jycode;

    private String zmlj;

    private String fmlj;

}




