package com.work16.work16server.persistents;

import com.work16.work16server.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from user limit 1")
    User findOneUser();

    @Insert("insert into user (username, password) values (#{username}, #{password})")
    void insertUser(User user);
}
