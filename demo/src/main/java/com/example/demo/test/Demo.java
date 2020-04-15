package com.example.demo.test;

import com.example.demo.sign.SignatureUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

public class Demo {

    public static void main(String[] args) {
        User user = new User();
        user.setUserName("demmo");
        user.setCode("123456");
        String secretKey = "Test";
        Map map = SignatureUtil.getSignature(user,secretKey);
        System.out.println(map);

    }
}
