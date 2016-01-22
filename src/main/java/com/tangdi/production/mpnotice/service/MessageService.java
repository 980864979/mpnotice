package com.tangdi.production.mpnotice.service;

import java.util.Map;

public interface MessageService {
	/**
	 * 获取验证码
	 * */
	public String getVidateCode(Map<String,Object> param)throws Exception;
}
