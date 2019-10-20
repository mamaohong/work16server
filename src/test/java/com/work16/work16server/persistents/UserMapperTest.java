package com.work16.work16server.persistents;

import com.work16.work16server.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserMapperTest {
    @Resource
    private UserMapper userMapper;

    @Test
    public void test() {
        User user = new User();
        user.setUsername("123");
        user.setPassword("123456");
        userMapper.insertUser(user);

        User user1 = userMapper.findOneUser();
        System.out.println(user1);
    }
}