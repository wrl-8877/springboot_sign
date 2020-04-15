package com.example.demo.open.filter;

import java.io.IOException;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.open.ApiProperties;
import com.example.demo.open.ProductProvider;
import com.example.demo.open.common.Appinfo;
import com.example.demo.open.common.Result;
import com.example.demo.open.constant.Constants;
import com.example.demo.open.enums.ResultCode;
import com.example.demo.open.exception.CustomException;
import com.example.demo.open.hmac.SignBuilder;
import com.example.demo.open.util.SpringActiveUtils;
import com.example.demo.open.wrapper.HttpRequestWrapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ShaFilter implements Filter {

	public ShaFilter(SignBuilder signBuilder, ApiProperties apiProperties, SpringActiveUtils springActiveUtils,
					 ProductProvider productProvider) {
		this.signBuilder = signBuilder;
		this.hmacProperties = apiProperties.getHmac();
		this.springActiveUtils = springActiveUtils;
		this.productProvider = productProvider;
	}

	private SignBuilder signBuilder;

	private ApiProperties.HmacProperties hmacProperties;

	private SpringActiveUtils springActiveUtils;

	private ProductProvider productProvider;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		log.debug("begin hmac filter");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpRequestWrapper requestWrapper = new HttpRequestWrapper(req);
		if (!springActiveUtils.isIgnoredProfiles(hmacProperties.getExcludeProfiles())) {
			try {
				validateSign(requestWrapper, req);
			} catch (CustomException e) {
				// 直接返回请求报文异常
				Result result = Result.failure(e.getResultCode());
				String s = JSONObject.toJSONString(result);
				response.getOutputStream().write(s.getBytes());
				return;
			}
		}
		chain.doFilter(requestWrapper, resp);
	}

	private void validateSign(HttpRequestWrapper requestWrapper, HttpServletRequest request)
			throws IOException, CustomException {
		log.debug("payload is  {}", requestWrapper.getPayloadAsString());
		Boolean serverSign = null;
		try {

			String body = requestWrapper.getPayloadAsString();
			JSONObject requestData = (JSONObject) JSONObject.parse(body);
			JSONObject responseHeaderJson = requestData.getJSONObject(Constants.REQUEST_HEADER);
			String appKey = responseHeaderJson.getString(Constants.HEADER_APP_KEY);
			Appinfo appInfo = productProvider.getAppInfo(appKey);
			serverSign = signBuilder.build(appInfo, requestWrapper);
		} catch (Exception e1) {
			throw e1;
		}
		if (!serverSign) {
			try {
				throw new CustomException(ResultCode.AM000002);
			} catch (CustomException e) {
				log.error("the client sign doesn't match server sign");
				throw e;
			}
		}
	}
	@Override
	public void destroy() {
		
	} 
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
}