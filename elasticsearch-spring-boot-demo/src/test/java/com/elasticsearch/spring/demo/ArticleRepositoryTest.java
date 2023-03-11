package com.elasticsearch.spring.demo;

import com.elasticsearch.spring.demo.dao.ArticleRepository;
import com.elasticsearch.spring.demo.entity.Article;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * ClassName: ArticleRepositoryTest
 * Description: dao操作document
 *
 * @author shengfq
 * @date: 2023/1/6 9:34 上午
 */
@Slf4j
public class ArticleRepositoryTest extends ElasticSearchApplicationTests {
    @Autowired
    private ArticleRepository articleRepository;
    /**
     * 添加文档
     *
     * 跟新文档 原理：删除之后再添加    所以说更新就是在添加一个跟想更新文档id一样的文档
     */
    @Test
    public void addDocument() throws Exception {
        for (int i = 1; i <= 10; i++) {
            //创建一个article对象
            Article article = new Article();
            article.setId(i);
            article.setTitle("工匠精神2");
            article.setContent("工匠精神，是指工匠对自己的产品精雕细琢，精益求精、更完美的精神理念。工匠们喜欢不断雕琢自己的产品，不断改善自己的工艺，享受着产品在双手中升华的过程");
            //把文档写入索引库
            articleRepository.save(article);
        }
    }


    /**
     * 删除文档
     */
    @Test
    public void deleteById() throws Exception {
        //根据id删除
        articleRepository.deleteById(1L);
        //全部删除
        //articleRepository.deleteAll();
    }


    /**
     * 不设置分页的话默认是每页显示十条
     * 查询所有
     */
    @Test
    public void findAll() throws Exception {
        Iterable<Article> articles = articleRepository.findAll();
        articles.forEach(a -> System.out.println(a));
    }


    /**
     * 查询一个
     */
    @Test
    public void findById() throws Exception {
        Optional<Article> optional = articleRepository.findById(7l);
        Article article = optional.get();
        System.out.println(article);
    }


    /**
     * 自定义查询方法可以跟据搜索的内容先分词 然后进行查询
     * 每个次之间条件都是  并且关系   就是 必须同时满足
     * 自定义条件查询
     *
     */
    @Test
    public void testFindByTitle() throws Exception {
        List<Article> list = articleRepository.findByTitle("工匠");
//           for (Article list1:list){
//               System.out.println(list1);
//           }
//拉姆达表达式方法
        list.stream().forEach(article -> System.out.println(article));
    }


    /**
     * 自定义根据条件查询
     */
    @Test
    public void testFindByTitleOrContent() throws Exception {
        List<Article> list = articleRepository.findByTitleOrContent("工匠", "工匠");
        for (Article list1 : list) {
            System.out.println(list1);
        }
    }


    /**
     * 自定义根据条件查询
     */
    @Test
    public void testFindByTitleOrContent1() throws Exception {
        //设置分页  page代表页数默认从0开始   size是每页显示条数
        Pageable pageable = PageRequest.of(0, 7);
        articleRepository.findByTitleOrContent("工匠", "工匠", pageable).forEach(a -> System.out.println(a));
    }

}
