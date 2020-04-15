package com.example.demo.open.hmac;

import java.io.IOException;

import com.example.demo.open.common.Appinfo;
import com.example.demo.open.constant.Constants;
import com.example.demo.open.enums.ResultCode;
import com.example.demo.open.exception.CustomException;
import com.example.demo.open.exception.SecurityException;
import com.example.demo.open.model.RequestHeader;
import com.example.demo.open.util.SHA;
import com.example.demo.open.wrapper.HttpRequestWrapper;
import org.apache.commons.codec.binary.StringUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

/**
 * 签名
 */
@Slf4j
public class SHASign implements SignBuilder{
	@Override
    public Boolean build(Appinfo appInfo, HttpRequestWrapper httpRequestWrapper) throws IOException, CustomException {
		String body = httpRequestWrapper.getPayloadAsString();
		JSONObject requestData = (JSONObject) JSONObject.parse(body);
		JSONObject responseHeaderJson = requestData.getJSONObject(Constants.REQUEST_HEADER);
		String requestBodyJson = requestData.getString(Constants.REQUEST_BODY);
		String signatureOld = responseHeaderJson.getString(Constants.HEADER_SIGNATURE);
		String appKey = responseHeaderJson.getString(Constants.HEADER_APP_KEY);
		RequestHeader requestHeader = new RequestHeader();
	    requestHeader.setAppKey(responseHeaderJson.getString(Constants.HEADER_APP_KEY));
	    requestHeader.setAppMessageId(responseHeaderJson.getString(Constants.HEADER_APP_MESSAGE_ID));
	    requestHeader.setVersion(responseHeaderJson.getString("version"));
	    requestHeader.setTimestamp(responseHeaderJson.getLongValue(Constants.HEADER_TIMESTAMP));
	    requestHeader.setNonce(responseHeaderJson.getString(Constants.HEADER_NONCE));
	    //将头部信息放置attribute上
	    httpRequestWrapper.setAttribute(Constants.HEADER_APP_KEY,responseHeaderJson.getString(Constants.HEADER_APP_KEY));
	    httpRequestWrapper.setAttribute(Constants.HEADER_APP_MESSAGE_ID,responseHeaderJson.getString(Constants.HEADER_APP_MESSAGE_ID));
	    httpRequestWrapper.setAttribute("version",responseHeaderJson.getString("version"));
	    httpRequestWrapper.setAttribute(Constants.HEADER_TIMESTAMP,responseHeaderJson.getLongValue(Constants.HEADER_TIMESTAMP));
	    httpRequestWrapper.setAttribute(Constants.HEADER_NONCE,responseHeaderJson.getString(Constants.HEADER_NONCE));
	    
	    String signature=null;
		try {
			signature = SHA.genSign256(requestHeader.getSignString() + "," + requestBodyJson + "," + appInfo.getSingnKey());
		} catch (SecurityException  e) {
			log.error("生成签名错误");
			throw new CustomException(ResultCode.FAILURE);
		}
		if(StringUtils.equals(signatureOld, signature)) {
			return true;
		}
	    return false;
	}
}
