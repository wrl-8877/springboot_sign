package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.demo.open.model.RequestData;
import com.example.demo.open.model.RequestHeader;
import com.example.demo.open.util.AES;
import com.example.demo.open.util.SHA;
import com.example.demo.open.exception.SecurityException;


import java.security.SecureRandom;

public class Test {
    protected static SerializerFeature[] JSON_FEATURE = { SerializerFeature.WriteMapNullValue, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteNullListAsEmpty, SerializerFeature.SortField };
    protected transient static String appKey="123";
    protected transient static String signKey="123";
    protected transient static String dataKey="123";
    public static void main(String[] args) throws SecurityException, SecurityException {
        try {
            //打印的是请求的body
            System.out.println(getRequestJson("123", "123", "1.0", false));
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //解密
        String decrytString = AES.decrypt128("PHzHIN/9lSqIBThn7k6zKg==", "123", "2775");
        System.out.println(decrytString);

    }

    public static String getRequestJson(String appMessageId, Object data, String version, boolean encrypt)
            throws SecurityException
    {
        RequestHeader requestHeader = new RequestHeader();
        requestHeader.setAppKey(appKey);
        requestHeader.setAppMessageId(appMessageId);
        requestHeader.setVersion(version);
        requestHeader.setTimestamp(1575450047910L);
        SecureRandom secureRandom = new SecureRandom(String.valueOf(System.currentTimeMillis()).getBytes());
        String nonce = "123";
        requestHeader.setNonce("123");
        String requestBodyJson = JSON.toJSONString(data, JSON_FEATURE);
        if (encrypt) {
            requestBodyJson = AES.encrypt128(requestBodyJson, dataKey, nonce);
        }
        String signature = SHA.genSign256(requestHeader.getSignString() + "," + requestBodyJson + "," + signKey);
        requestHeader.setSignature(signature);
        RequestData requestData = new RequestData();
        requestData.setRequestHeader(requestHeader);
        requestData.setRequestBody(requestBodyJson);
        return JSON.toJSONString(requestData, JSON_FEATURE);
    }
}
