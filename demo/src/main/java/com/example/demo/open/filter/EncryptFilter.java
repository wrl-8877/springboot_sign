package com.example.demo.open.filter;


import com.example.demo.open.ApiProperties;
import com.example.demo.open.ProductProvider;
import com.example.demo.open.common.Appinfo;
import com.example.demo.open.constant.Constants;
import com.example.demo.open.encrypt.Encrypt;
import com.example.demo.open.model.ActionResult;
import com.example.demo.open.model.ResponseData;
import com.example.demo.open.model.ResponseHeader;
import com.example.demo.open.util.SHA;
import com.example.demo.open.util.SpringActiveUtils;
import com.example.demo.open.wrapper.HttpRequestWrapper;
import com.example.demo.open.wrapper.HttpResponseWrapper;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * 数据解密
 */
@Slf4j
public class EncryptFilter implements Filter {

	private ApiProperties.EncryptProperties encryptProperties;

	private Encrypt encrypt;

	private SpringActiveUtils springActiveUtils;

	private ProductProvider productProvider;

	public EncryptFilter(ApiProperties apiProperties, Encrypt encrypt, SpringActiveUtils springActiveUtils,
			ProductProvider productProvider) {
		this.encryptProperties = apiProperties.getEncrypt();
		this.encrypt = encrypt;
		this.springActiveUtils = springActiveUtils;
		this.productProvider = productProvider;
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		log.debug("begin encrypt filter");
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpRequestWrapper req = (HttpRequestWrapper) servletRequest;
		HttpResponseWrapper httpResponseWrapper = new HttpResponseWrapper(response);
		filterChain.doFilter(servletRequest, httpResponseWrapper);
		if (springActiveUtils.isNeedEncrypt(encryptProperties.getExcludeProfiles())) {
			byte[] content = httpResponseWrapper.getResponseData();
			String result = new String(httpResponseWrapper.getResponseData(), StandardCharsets.UTF_8);
			ActionResult<Object> encryptResult = null;
			ResponseData responseData = new ResponseData();
			try {
				if (content.length > 0) {
					String str = new String(content, "UTF-8");
					encryptResult = JSONObject.parseObject(str, ActionResult.class);
					log.debug("not encrypt data = {}", result);
					String responseBodyJson;
					String appKey = (String) req.getAttribute(Constants.HEADER_APP_KEY);
					String appMessageId = (String) req.getAttribute(Constants.HEADER_APP_MESSAGE_ID);
					// 根据appkey取appinfo信息
					Appinfo appInfo = productProvider.getAppInfo(appKey);
					ResponseHeader responseHeader = new ResponseHeader();
					responseHeader.setAppKey(appKey);
					responseHeader.setAppMessageId(appMessageId);
					SecureRandom secureRandom = new SecureRandom(String.valueOf(System.currentTimeMillis()).getBytes());
					String nonce = String.valueOf(secureRandom.nextInt(10000));
					responseHeader.setNonce(nonce);
					responseHeader.setResultCode(encryptResult.getCode());
					responseHeader.setResultMessage(encryptResult.getMessage());
					responseHeader.setTimestamp(System.currentTimeMillis());
					if(appInfo.getEncrypt()) {
						responseBodyJson = encrypt.encrypt(JSONObject.toJSONString(encryptResult.getData()), appInfo.getDataKey(), nonce);
					}else {
					    responseBodyJson = String.valueOf(encryptResult.getData());
					}
					String signature = SHA.genSign256(responseHeader.getSignString() + "," + responseBodyJson + "," + appInfo.getSingnKey());
					responseHeader.setSignature(signature );
					log.debug("encrypt data = {}", responseBodyJson);
					responseData.setResponseBody(responseBodyJson);
					responseData.setResponseHeader(responseHeader);
				}
			} catch (Exception e) {
				log.error("encrypt fail by {}", e.getMessage(), e);
				return;
			}
			writeResponse(servletResponse, JSONObject.toJSONString(responseData));
		}
	}

	private void writeResponse(ServletResponse response, String responseString) throws IOException {
		@Cleanup
		PrintWriter out = response.getWriter();
		out.print(responseString);
		out.flush();
	}

}
