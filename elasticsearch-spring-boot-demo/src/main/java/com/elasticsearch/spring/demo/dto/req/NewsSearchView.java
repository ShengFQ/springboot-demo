package com.elasticsearch.spring.demo.dto.req;

import com.elasticsearch.spring.demo.entity.WebContent;
import lombok.Data;

import java.util.List;

@Data
public class NewsSearchView {

    private List<WebContent> list;

    private Integer totalPage;

    private Long totalNum;
}
