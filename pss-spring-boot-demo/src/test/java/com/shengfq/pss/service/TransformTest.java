package com.shengfq.pss.service;

import com.shengfq.pss.PhotoSpringBootDemoApplication;
import com.shengfq.pss.dto.StUserPhotoDto;
import com.shengfq.pss.entity.StUserPhoto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * ClassName: TransformTest
 * Description: TODO
 *
 * @author shengfq
 * @date: 2022/9/19 4:43 下午
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PhotoSpringBootDemoApplication.class, webEnvironment =
        SpringBootTest.WebEnvironment.NONE)
public class TransformTest {
    @Autowired
    private StUserPhotoService stUserPhotoService;
    @Autowired
    private JzUserJgzService jzUserJgzService;
    @Test
   public void contextLoads() {

    }
    @Test
    public void testTransform() {
        List<StUserPhoto> list= stUserPhotoService.list(1,3000);
        list.forEach(item->{
            int expect=1,actual=0;
            StUserPhotoDto stUserPhotoDto= stUserPhotoService.writeFile(item);
            if(stUserPhotoDto==null){
                System.out.println("文件写入失败");
                return;
            }
            try {
                actual= jzUserJgzService.addOrUpdate(stUserPhotoDto);
               // actual=stUserPhotoService.updateByPrimaryKey(item);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Assert.assertEquals(expect,actual);
        });
    }
}
