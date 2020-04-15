package com.example.demo.open.common;

import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Data
public class Appinfo {
	/**
	 * 客户端key值
	 */
	private String appKey;
	
	/**
	 * key名称
	 */
	private String appName;
	/**
	 * 签名秘钥
	 */
	private String singnKey;
	/**
	 * 数据加密秘钥
	 */
	private String dataKey;
	/**
	 * 是否加密
	 */
	private Boolean encrypt=false;
}
