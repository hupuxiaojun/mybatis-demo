package com.example.mybatisdemo.dao;

import com.example.mybatisdemo.model.Users;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

/**
 * 通过sqlSession原生方式查询
 *
 * @author : xiaojun
 * @since 16:21 2018/11/15
 */
@Component
public class UserDao {

    private final SqlSession sqlSession;


    public UserDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public Users selectByPrimaryKey(String username) {
        return sqlSession.selectOne("com.example.mybatisdemo.mapper.UsersMapper.selectByPrimaryKey", username);
    }
}
