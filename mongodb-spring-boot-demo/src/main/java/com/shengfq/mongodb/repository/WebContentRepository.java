package com.shengfq.mongodb.repository;

import com.shengfq.mongodb.model.WebContent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 文章 Dao
 * </p>
 *
 * @author shengfq
 */
@Repository
public interface WebContentRepository extends MongoRepository<WebContent, Long> {
    /**
     * 根据标题模糊查询
     *
     * @param title 标题
     * @return 满足条件的文章列表
     */
    List<WebContent> findByTitleLike(String title);
}
