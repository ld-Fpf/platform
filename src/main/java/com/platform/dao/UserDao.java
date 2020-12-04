package com.platform.dao;

import com.platform.entity.User;

import java.util.List;

/**
 * 用户类持久层
 *
 * @author luxx
 */
public interface UserDao {

    /**
     * 注册用户
     *
     * @param user
     * @return
     */
    int addUser(User user);

    /**
     * 根据ID查找用户
     *
     * @param id
     * @return
     */
    User findUserById(int id);

    /**
     * 修改用户信息
     *
     * @param user
     */
    void saveUser(User user);

    List<User> findAllUser();
}

