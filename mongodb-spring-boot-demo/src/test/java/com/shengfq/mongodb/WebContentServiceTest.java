package com.shengfq.mongodb;

import com.shengfq.mongodb.model.WebContent;
import com.shengfq.mongodb.service.WebContentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * ClassName: WebContentServiceTest
 * Description: WebContentServiceTest
 *
 * @author shengfq
 * @date: 2023/4/2 1:45 下午
 */
@Slf4j
public class WebContentServiceTest  extends MongodbSpringBootDemoApplicationTests{
    @Autowired
    WebContentService webContentService;

    @Test
    public void testSave(){
        try {
            boolean result = webContentService.save("iphone");
            Assert.assertEquals(true, result);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void testFindAll(){
        List<WebContent> list= webContentService.findAll();
        Assert.assertEquals(116, list.size());
    }
    @Test
    public void testFindById(){
        String id="6429252fe4e1e529f2080637";
        WebContent webContent= webContentService.getById(id);
        Assert.assertNotNull( webContent);
    }

    @Test
    public void testFindByTitle(){
        String title="Apple iPhone 14";
        List<WebContent> list= webContentService.getByTitle(title);
        list.stream().forEach(System.out::println);
        Assert.assertNotNull( list);
    }
}
