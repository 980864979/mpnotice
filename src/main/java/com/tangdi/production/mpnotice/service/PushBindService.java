package com.tangdi.production.mpnotice.service;

import java.util.Map;

public interface PushBindService {
	/**
	 * 根据平台，账号进行查询
	 * 
	 * @param platId
	 * @return
	 */
	public Map<String, Object> getBindInf(Map<String, Object> param) throws Exception;

	/**
	 * 添加新纪录
	 * 
	 * @param param
	 * @return
	 */
	public int addBindInf(Map<String, Object> param) throws Exception;
}
