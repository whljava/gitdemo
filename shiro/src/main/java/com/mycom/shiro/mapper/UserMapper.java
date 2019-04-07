package com.mycom.shiro.mapper;

import com.mycom.shiro.dao.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from user where name = #{name}")
    User findByName(String name);

    @Select("select * from user where id=#{id}")
    User findById(Integer id);
}
