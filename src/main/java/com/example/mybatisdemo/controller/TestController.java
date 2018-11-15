package com.example.mybatisdemo.controller;

import com.example.mybatisdemo.dao.UserDao;
import com.example.mybatisdemo.mapper.AnnotationUsersMapper;
import com.example.mybatisdemo.mapper.UsersMapper;
import com.example.mybatisdemo.model.UsersExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author : xiaojun
 * @since 15:38 2018/11/15
 */
@RestController
public class TestController {

    @Autowired
    UsersMapper usersMapper;

    @Autowired
    UserDao userDao;

    @Autowired
    AnnotationUsersMapper annotationUsersMapper;

    @RequestMapping("/users/{uid}")
    public Object test(@PathVariable("uid") String uid) {
        System.out.println("通过mapper接口查询");
        return usersMapper.selectByPrimaryKey(uid);
    }

    //分页参考
    @RequestMapping("/users/page/{index}/{size}")
    public Object testPage(@PathVariable("index") Integer index, @PathVariable("size") Integer size) {
        System.out.println("通过mapper接口分页查询");
        UsersExample usersExample = new UsersExample();
        usersExample.setLimit(size);
        usersExample.setOffset(index);
        usersExample.setOrderByClause(" username desc ");
        //Example类用法参考:http://www.mybatis.org/generator/generatedobjects/exampleClassUsage.html
        usersExample.or().andEnabledEqualTo(true);
        usersExample.or().andUsernameEqualTo("xiaoj");
        return usersMapper.selectByExample(usersExample);
    }


    @RequestMapping("/users2/{uid}")
    public Object test2(@PathVariable("uid") String uid) {
        System.out.println("通过sqlSession去查询");
        return userDao.selectByPrimaryKey(uid);
    }

    @RequestMapping("/users3/{uid}")
    public Object test3(@PathVariable("uid") String uid) {
        System.out.println("通过注解mapper去查询");
        return annotationUsersMapper.findByName(uid);
    }
}
