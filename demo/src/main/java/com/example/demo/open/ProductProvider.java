package com.example.demo.open;

import com.example.demo.open.common.Appinfo;

/**
 * 获取接入客户端信息
 */
public interface ProductProvider {

    /**
     * 获取app客户端配置信息
     * @param appKey
     * @return
     */
    Appinfo getAppInfo(String appKey);
  
}
