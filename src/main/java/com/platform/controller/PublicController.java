package com.platform.controller;

import com.alibaba.fastjson.JSONObject;
import com.platform.dao.CompetitionDao;
import com.platform.dao.StudentDao;
import com.platform.entity.Competition;
import com.platform.entity.Student;
import com.platform.response.Result;
import com.platform.service.*;
import org.apache.xmlbeans.impl.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;


/**
 * 公共区域控制
 *
 * @author luxx
 */
@Controller
@RequestMapping(value = "/public")
public class PublicController {

    /**
     * 登录页面
     *
     * @return
     */
    @RequestMapping("/login")
    public String login() {
        System.out.println("----------login----------");
        return "public/login";
    }

    /**
     * 登录验证
     *
     * @param request
     * @param response
     * @param id
     * @param password
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/checkLogin")
    public void login(HttpServletRequest request, HttpServletResponse response, @RequestParam String id, @RequestParam String password) throws IOException {
        Result result;
        try {
            int userId = Integer.parseInt(id);
            result = loginService.checkPassword(request, userId, password);
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof ParseException) {
                result = new Result("0", "账号不是纯数字");
            } else {
                result = new Result("0", "系统异常");
            }
        }
        JSONObject json = new JSONObject();
        json.put("result", result);
        response.getWriter().write(json.toJSONString());
    }


    /**
     * 获取验证码
     *
     * @param request
     * @param response
     */
    @RequestMapping("/getVerifyCode")
    public void getVerifyCode(HttpServletRequest request, HttpServletResponse response) {
        try {
            verifyCodeService.service(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/download")
    public String download(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws IOException {
        try {
            String str = request.getParameter("id");
            if ("".equals(str) || str == null) {
                return "public/query_ticket";
            }
            long id = Long.parseLong(str);
            String identity = request.getParameter("identity");
            if (id == 0 || "".equals(identity) || identity == null) {
                return "public/query_ticket";
            }
            Student student = studentDao.findOneById(id);
            if (student != null) {
                if (student.getIdentity().equals(identity)) {
                    modelMap.put("student", student);
                    Competition competition = competitionDao.findOneById(student.getCompetitionId());
                    modelMap.put("name", competition.getName());
                    return "public/download_ticket";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "public/query_ticket";
    }

    @RequestMapping("/query")
    public String query() {
        return "public/query_ticket";
    }

    @RequestMapping("/error")
    public String error() {
        return "public/error";
    }

    ///**
    // * 注册
    // *
    // * @param request
    // * @param response
    // * @throws IOException
    // */
    //@ResponseBody
    //@RequestMapping("/checkRegister")
    //public void register(HttpServletRequest request, @RequestParam String name, @RequestParam String password, HttpServletResponse response) throws IOException {
    //    System.out.println("-----------doRegister----------");
    //
    //    JSONObject json = new JSONObject();
    //    try {
    //        Result res = registerService.register(name, password);
    //        json.put("response", res);
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //    }
    //    response.getWriter().write(json.toJSONString());
    //}

    /**
     * 获取短信验证码
     *
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping("/getSmsCode")
    public void getSmsCode(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("-----------getSmsCode----------");
        Result result;
        String telephone = request.getParameter("tel");
        String regex = "^\\d{11}$";

        try {

            if (telephone == null || "".equals(telephone)) {
                result = new Result(Result.RESPONSE_RESULT_ERROR, "号码为空");
            } else {
                boolean isMatch = Pattern.matches(regex, telephone);
                if (!isMatch) {
                    result = new Result(Result.RESPONSE_RESULT_ERROR, "号码格式错误");
                } else {
                    smsService.sendSMS(request, telephone);
                    result = new Result(Result.RESPONSE_RESULT_SUCCESS, "已发送");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(Result.RESPONSE_RESULT_ERROR, "发送失败，请稍后");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", result);
        try {
            response.getWriter().write(jsonObject.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @ResponseBody
    @RequestMapping("/checkSmsCode")
    public void checkSmsCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("-----------checkSmsCode----------");

        JSONObject json = new JSONObject();
        Result res = new Result();
        try {
            Object smsCode = request.getSession().getAttribute("smsCode");
            if (smsCode == null) {
                res = new Result(Result.RESPONSE_RESULT_ERROR, "验证码已失效");
            } else {
                System.out.println("smsCode=" + smsCode);
                String sessionCode = smsCode.toString();
                String code = request.getParameter("code");
                res = smsService.checkSmsCode(sessionCode, code);
            }

        } catch (Exception e) {
            e.printStackTrace();
            res = new Result(Result.RESPONSE_RESULT_ERROR, "系统异常");
        }
        json.put("result", res);
        response.getWriter().write(json.toJSONString());
    }

    /**
     * 验证码校验
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/checkVerifyCode")
    public void checkVerifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("-----------checkVerifyCode----------");

        JSONObject json = new JSONObject();
        Result res = new Result();
        try {
            Object sesionCode = request.getSession().getAttribute("validCode");
            if (sesionCode == null) {
                res = new Result("0", "验证码已失效");
            } else {
                String validCode = (String) sesionCode;
                String verifyCode = request.getParameter("verifyCode").toUpperCase();
                res = verifyCodeService.checkVerifyCode(validCode, verifyCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        json.put("response", res);
        response.getWriter().write(json.toJSONString());
    }

    @ResponseBody
    @RequestMapping("/getDownload")
    public void getDownload(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String data = request.getParameter("data");
        byte[] content = Base64.decode(data.getBytes());
        try {
            response.reset();
            response.setHeader("content-disposition", "attachment;fileName=zhunkaozheng.png");
            OutputStream out = response.getOutputStream();
            out.write(content);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping("/search")
    public void search(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        JSONObject jsonObject = new JSONObject();
        Student student = null;
        try {
            int competitionId = Integer.parseInt(request.getParameter("competitionId"));
            String name = request.getParameter("name");
            String identity = request.getParameter("identity");

            Student findStudent = new Student(name, identity, competitionId);
            student = studentService.search(findStudent);
            if (student != null) {
                student.setState(true);
                studentDao.modifyOneByState(student);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        jsonObject.put("student", student);
        response.getWriter().write(jsonObject.toJSONString());
    }

    @ResponseBody
    @RequestMapping("/getCompetitionList")
    public void getCompetitionList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject json = new JSONObject();
        try {
            List<Competition> list = requestSerivce.findCompetitionByRequest(request, new HashMap());
            json.put("rows", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.getWriter().write(json.toJSONString());
    }

    @Autowired(required = false)
    private CompetitionDao competitionDao;

    @Autowired(required = false)
    private StudentDao studentDao;
    @Autowired
    private LoginService loginService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private VerifyCodeService verifyCodeService;
    @Autowired
    private RequestSerivce requestSerivce;
    @Autowired
    private StudentService studentService;

}
