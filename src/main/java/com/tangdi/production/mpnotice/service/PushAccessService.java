package com.tangdi.production.mpnotice.service;

import java.util.Map;

/***
 * 推送平台授权码接口
 * 
 * @author sunhaining
 *
 */
public interface PushAccessService {

	/***
	 * 获取平台AccessToken
	 * 
	 * @param plantForm
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getAccessToken(String plantForm) throws Exception;

	/***
	 * 修改平台AccessToken
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int modifyAccessToken(Map<String, Object> param) throws Exception;
	
	
	public void process() throws Exception;
}
