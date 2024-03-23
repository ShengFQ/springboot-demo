package com.shengfq.webserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemoApplication {

    /***
     * webserver优雅停机,
     * */
    public static void main(String[] args) {
        //SpringApplication.run(DemoApplication.class, args);
        SpringApplication application = new SpringApplication(DemoApplication.class);
        ConfigurableApplicationContext context = application.run(args);

        // 添加优雅停机关闭钩子
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
          //  context.getBean(TomcatShutdown.class).shutdownGracefully();
            context.close();
        }));

    }

}
