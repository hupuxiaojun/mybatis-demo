package com.example.mybatisdemo.mapper;

import com.example.mybatisdemo.model.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 通过注解方式使用mybatis
 */
@Mapper
public interface AnnotationUsersMapper {
    @Select("SELECT * FROM users WHERE USERNAME = #{name}")
    Users findByName(@Param("name") String name);
}