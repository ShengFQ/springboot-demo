package com.elasticsearch.spring.demo.util;

/**
 * <p>
 * ES常量池
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-12-20 17:30
 */
public interface EsConsts {
    /**
     * 索引名称
     */
    String INDEX_NAME = "jd_goods";

    /**
     * 类型名称
     */
    String TYPE_NAME = "goods";

    String PROPERTY_TITLE = "title";

    String PROPERTY_ID = "id";

    String PROPERTY_COMMIT = "commit";

    String PROPERTY_SHOP = "shop";

    String PROPERTY_PROVINCE = "province";

    String DOC_ID_PREFIX = "100";

    String INDEX_NAME_DEMO = "demo_index";

}
