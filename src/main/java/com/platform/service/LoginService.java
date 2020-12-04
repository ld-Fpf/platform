package com.platform.service;

import com.platform.dao.UserDao;
import com.platform.entity.User;
import com.platform.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * 登录业务
 *
 * @author luxx
 */
@Service
public class LoginService {

    public Result checkPassword(HttpServletRequest request, int id, String password) {
        Result response;

        User user = userDao.findUserById(id);
        if (user == null) {
            response = new Result("0", "账号错误");
        } else if (password.equals(user.getPassword())) {
            response = new Result("1", "登录成功");
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
        } else {
            response = new Result("0", "密码错误");
        }
        return response;
    }


    @Autowired
    private UserDao userDao;
}
