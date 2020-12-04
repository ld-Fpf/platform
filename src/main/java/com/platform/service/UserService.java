package com.platform.service;

import com.platform.dao.UserDao;
import com.platform.entity.User;
import com.platform.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author luxx
 * @date 2017/11/13 0013
 */

@Service
public class UserService {

    public Result modfiyTel(HttpServletRequest request) {
        Result result;
        String code = request.getParameter("code");
        if ("".equals(code)) {
            result = new Result(Result.RESPONSE_RESULT_ERROR, "验证码为空");
        } else {
            Object obj = request.getSession().getAttribute("smsCode");
            Object obj2 = request.getSession().getAttribute("tel");
            Object obj3 = request.getSession().getAttribute("user");
            if (obj == null || obj2 == null) {
                result = new Result(Result.RESPONSE_RESULT_ERROR, "验证码错误");
            } else {
                String smsCode = obj.toString();
                String tel = obj2.toString();
                User user = (User) obj3;
                result = smsService.checkSmsCode(smsCode,code);

                if (Result.RESPONSE_RESULT_SUCCESS.equals(result.getResult())) {
                    User user2 = userDao.findUserById(user.getId());
                    user.setTel(tel);
                    userDao.saveUser(user2);
                    request.getSession().setAttribute("user", user2);
                } else {
                    result = new Result(Result.RESPONSE_RESULT_ERROR, "验证码错误");
                }
            }
        }
        return result;
    }


    @Autowired(required = false)
    private UserDao userDao;

    @Autowired
    private SmsService smsService;
}
