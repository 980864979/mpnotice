package com.tangdi.production.mpnotice.dao;

import java.util.Map;

public interface PushAccessDao {

	/**
	 * 根据平台进行查询
	 * 
	 * @param platId
	 * @return
	 */
	public Map<String, Object> selectByPlatfrom(String platfrom);

	/**
	 * 修改记录
	 * 
	 * @param param
	 * @return
	 */
	public int updateEntity(Map<String, Object> param);

}
