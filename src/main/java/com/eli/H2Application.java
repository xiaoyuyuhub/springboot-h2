package com.eli;


import com.eli.bean.User;
import com.eli.dao.UserDao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@SpringBootApplication
public class H2Application {

    public static void main(String[] args) {
        SpringApplication.run(H2Application.class,args);
    }

    @Resource
    private UserDao userMapper;


    @RestController
    public class TestController {
        @GetMapping(value = "/users")
        public List<User> users() {
            return userMapper.selectList(null);
        }
    }

}
