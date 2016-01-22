package com.tangdi.production.mpnotice.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class HttpServletUtil {

	public static Map getParameterMap(HttpServletRequest request) {
		Map parameterMap = request.getParameterMap();
		Map returnMap = new HashMap();
		// 返回值Map
		Iterator entries = parameterMap.entrySet().iterator();
		Map.Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}
		return returnMap;
	}

	/**
	 * response.getOutputStream().write(rspMsg);
	 * 
	 * @param response
	 * @param rspMsg
	 */
	public static void outPutMsg(HttpServletResponse response, String rspMsg) {
		try {
			response.getOutputStream().write(rspMsg.getBytes("UTF-8"));
		} catch (Exception e) {
		}
	}
}