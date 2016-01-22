package com.tangdi.production.mpnotice.dao;

import java.util.Map;

public interface PushBindDao {

	/**
	 * 根据平台，账号进行查询
	 * 
	 * @param platId
	 * @return
	 */
	public Map<String, Object> selectBindInf(Map<String, Object> param);

	/**
	 * 添加新纪录
	 * 
	 * @param param
	 * @return
	 */
	public int insertEntity(Map<String, Object> param);
}
