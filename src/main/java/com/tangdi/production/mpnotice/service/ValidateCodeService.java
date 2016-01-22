package com.tangdi.production.mpnotice.service;

import java.util.Map;

import com.tangdi.production.mpbase.exception.TranException;

/**
 * 验证码接口
 * 
 * @author youdd
 *
 */
public interface ValidateCodeService {

	/**
	 * 验证码插入
	 * 
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public int addCode(Map<String, Object> param) throws TranException;

	/**
	 * 验证码校验
	 * 
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public boolean validate(Map<String, Object> param) throws TranException;

	/**
	 * 绑定第三方唯一标识
	 * @param param
	 * @return
	 * @throws TranException
	 * */
	public boolean bindCust(Map<String, Object> param) throws TranException;
}
