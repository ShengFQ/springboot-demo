package com.shengfq.mongodb.repository;

import com.shengfq.mongodb.model.Article;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 文章 Dao
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-12-28 16:30
 */
@Repository
public interface ArticleRepository extends MongoRepository<Article, Long> {
    /**
     * 根据标题模糊查询
     *
     * @param title 标题
     * @return 满足条件的文章列表
     */
    List<Article> findByTitleLike(String title);
}
