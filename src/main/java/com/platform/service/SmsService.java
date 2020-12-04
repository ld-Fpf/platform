package com.platform.service;

import com.alibaba.fastjson.JSONObject;
import com.platform.response.Result;
import com.platform.util.MD5;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 短信验证接口
 *
 * @author luxx
 */
@Service
public class SmsService {

    private static String AccountSid = "6d1bd21758c7a1eb697c299c78ee9e62";
    private static String AuthToken = "1fd3bebd6d4e88a7831fe0f14c689417";
    private static String APPID = "9d14a3a1f86640db89c78159dea7c270";
    private static String URL = "https://api.ucpaas.com/2014-06-30/Accounts/6d1bd21758c7a1eb697c299c78ee9e62/Messages/templateSMS";

    private String sign() {
        StringBuilder sb = new StringBuilder(AccountSid);
        sb.append(AuthToken);
        sb.append(getTime());
        System.out.println(MD5.sign(sb.toString()).toUpperCase());
        return MD5.sign(sb.toString()).toUpperCase();
    }

    public String getTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        System.out.println(sdf.format(date));
        return sdf.format(date);
    }

    private String base64(String msg) {
        return new BASE64Encoder().encode(msg.getBytes());
    }


    public void sendSMS(HttpServletRequest request,String telephone) throws IOException {
        int code = (int) Math.floor(Math.random() * 10000);
        JSONObject jsonObject = new JSONObject();
        JSONObject json = new JSONObject();
        json.put("appId", APPID);
        json.put("param", code + "");
        System.out.println("code=" + code);
        request.getSession().setAttribute("smsCode",code);
        request.getSession().setAttribute("tel",telephone);
        json.put("templateId", "180197");
        json.put("to", telephone);
        jsonObject.put("templateSMS", json);
        System.out.println("json=" + jsonObject.toJSONString());
        HttpPost post = new HttpPost(URL + "?sig=" + sign());
        StringEntity entity = new StringEntity(jsonObject.toJSONString(), Charset.forName("UTF-8"));
        entity.setContentType("application/json");
        post.setHeader("Authorization", base64(AccountSid + ":" + getTime()));
        post.setEntity(entity);
        HttpClient client = HttpClients.createDefault();
        HttpResponse response = client.execute(post);
        HttpEntity entity1 = response.getEntity();
        System.out.println(EntityUtils.toString(entity1));
    }

    public Result checkSmsCode(String sessionCode,String code){
        Result result = new Result();
        if (code == null || "".equals(code)) {
            result.setMessage("验证码为空");
            result.setResult(Result.RESPONSE_RESULT_ERROR);
        } else if (code.equals(sessionCode)) {
            result.setMessage("验证成功");
            result.setResult(Result.RESPONSE_RESULT_SUCCESS);
        } else {
            result.setMessage("验证码错误");
            result.setResult(Result.RESPONSE_RESULT_ERROR);
        }

        return result;
    }
}
