package io.shengfq.aop.common;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.shengfq.aop.domain.LoginVal;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ThreadPoolUtilsTest {

  @Autowired
  private ThreadPoolUtils threadPoolUtils;

  @Test
  public void testHandlerAsync() {
    LoginVal mainLoginVal = LoginVal.builder().facilityCode("5802").userName("shengfq").build();
    OauthContext.set(mainLoginVal);
    threadPoolUtils.handlerAsync(() -> {
      LoginVal loginVal = OauthContext.get();
      log.info("子线程中的loginVal:{}", loginVal);
    });
  }

}
