package com.example.demo.open.filter;


import com.example.demo.open.ApiProperties;
import com.example.demo.open.ProductProvider;
import com.example.demo.open.common.Appinfo;
import com.example.demo.open.constant.Constants;
import com.example.demo.open.decrypt.Decrypt;
import com.example.demo.open.util.SpringActiveUtils;
import com.example.demo.open.wrapper.HttpRequestWrapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


import java.io.IOException;

/**
 * 数据解密
 */
@Slf4j
public class DecryptFilter implements Filter {

	private ApiProperties.DecryptProperties decryptProperties;

	private Decrypt decrypt;

	private SpringActiveUtils springActiveUtils;

	private ProductProvider productProvider;

	public DecryptFilter(ApiProperties apiProperties, Decrypt decrypt, SpringActiveUtils springActiveUtils,
			ProductProvider productProvider) {
		this.decryptProperties = apiProperties.getDecrypt();
		this.decrypt = decrypt;
		this.springActiveUtils = springActiveUtils;
		this.productProvider = productProvider;
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		log.debug("begin decrypt filter");
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpRequestWrapper requestWrapper = new HttpRequestWrapper(request);
		String payload = requestWrapper.getPayloadAsString();
		JSONObject requestData = (JSONObject) JSONObject.parse(payload);
		String requestBodyJson = requestData.getString(Constants.REQUEST_BODY);
		if (springActiveUtils.isNeedDecrypt(decryptProperties.getExcludeProfiles())) {
			String decryptData;
			try {
				String appKey = (String) request.getAttribute(Constants.HEADER_APP_KEY);
				String nonce = (String) request.getAttribute(Constants.HEADER_NONCE);
				Appinfo appInfo = productProvider.getAppInfo(appKey);
				if (appInfo.getEncrypt()) {
					decryptData = decrypt.decrypt(requestBodyJson, appInfo.getDataKey(), nonce);
				} else {
					decryptData = requestBodyJson;
				}
				requestData.put(Constants.REQUEST_BODY, decryptData);
				payload = JSON.toJSONString(requestData);
			} catch (Exception e) {
				log.error("decrypt fail by {}", e.getMessage(), e);
				return;
			}
			log.debug("origin data ={} ,decrypt data ={}", payload, decryptData);
			requestWrapper.setPayload(payload.getBytes());
		}
		filterChain.doFilter(requestWrapper, response);
	}

	@Override
	public void destroy() {

	}
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
}
