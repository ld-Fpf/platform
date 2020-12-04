package com.platform.service;

import com.platform.dao.UserDao;
import com.platform.entity.User;
import com.platform.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 注册账户
 *
 * @author luxx
 */

@Service
public class RegisterService {

    /**
     * 注册用户
     *
     * @param name
     * @param password
     * @return
     */
    public Result register(String name, String password) {
        Result response;
        if (name.length() < 2 || name.length() > 14) {
            response = new Result("0", "用户名长度错误");
        } else if (password.length() < 6 || password.length() > 16) {
            response = new Result("0", "密码长度错误");
        } else {
            User user = new User();
            user.setNick(name);
            user.setPassword(password);
            userDao.addUser(user);
            response = new Result("1", user.getId() + "");
        }
        return response;
    }

    @Autowired(required = false)
    private UserDao userDao;
}
