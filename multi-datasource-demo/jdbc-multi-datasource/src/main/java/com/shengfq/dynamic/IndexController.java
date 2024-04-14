package com.shengfq.dynamic;

import com.shengfq.dynamic.domain.User;
import com.shengfq.dynamic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ClassName: IndexController
 *
 * @author shengfq
 * @date: 2024-03-23
 */
@RestController
public class IndexController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/user")
    public String userList() {
        List list = userService.selectAll();

        list.forEach(user -> {
            System.out.println(user);
        });
        System.out.println("==============");
        List list2 = userService.selectByCondition();
        list2.forEach(user -> {
            System.out.println(user);
        });
        return "success";
    }

}
