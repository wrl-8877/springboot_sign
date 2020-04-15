package com.example.demo.open.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.open.config.ApiVersion;
import com.example.demo.open.model.ActionResult;
import com.example.demo.open.wrapper.HttpRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 多版本接口测试（向下兼容）
 */
@Slf4j
@RestController
@RequestMapping("/api/{version}/")
public class MultiApiController {

	@RequestMapping("hello")
	@ApiVersion(1)
	@ResponseBody
	public ActionResult<String> hello(HttpServletRequest request) throws IOException {
		System.out.println("test1..........");
		HttpRequestWrapper requestWrapper;
		JSONObject requestData = new JSONObject();
		requestWrapper = new HttpRequestWrapper(request);
		String payload = requestWrapper.getPayloadAsString();
		System.out.println("payload:"+payload);
		requestData = (JSONObject) JSONObject.parse(payload);
		System.out.println("requestData:"+requestData);
		ActionResult<String> result = ActionResult.getSuccessResult("success");
		return result;
	}

}
