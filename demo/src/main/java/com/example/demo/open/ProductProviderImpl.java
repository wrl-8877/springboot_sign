package com.example.demo.open;


import com.example.demo.open.common.Appinfo;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Component;

@Component
public class ProductProviderImpl implements ProductProvider {



	@Override
	public Appinfo getAppInfo(String appKey) {
		Appinfo appInfo = new Appinfo();

		appInfo.setAppKey("123");
		appInfo.setAppName("123");
		appInfo.setSingnKey("123");
		appInfo.setDataKey("123");
		appInfo.setEncrypt(BooleanUtils.toBoolean(0));
		return appInfo;
	}

	
}