package com.tangdi.production.mpnotice.dao;

import java.util.Map;

import com.tangdi.production.mpbase.exception.TranException;

public interface MerchantDao {
	
	/**
	 * 查询商户存不存在
	 * */
	public String queryCustId(Map<String,Object> param)throws TranException;
	
	/***
	 * 检查绑定推送码
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public int selectPushCode(Map<String,Object> param)throws TranException;
}
