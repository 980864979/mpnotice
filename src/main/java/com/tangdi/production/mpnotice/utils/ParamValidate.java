package com.tangdi.production.mpnotice.utils;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Map中的必需字段效验
 * 
 * @author zhengqiang
 * 
 */

public class ParamValidate {
	private static Logger log = LoggerFactory.getLogger(ParamValidate.class);

	public static void doing(Map<String, Object> param, String... keys) throws Exception {
		for (String key : keys) {
			if (!param.containsKey(key) || param.get(key) == null || param.get(key).toString().trim().equals("")) {
				log.info("必填字段效验不通过");
				throw new Exception("必填字段效验不通过!");
			}
		}
	}
}
