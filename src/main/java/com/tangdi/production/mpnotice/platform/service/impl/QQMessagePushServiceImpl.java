package com.tangdi.production.mpnotice.platform.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.tangdi.production.mpnotice.platform.service.MessagePushService;

/***
 * QQ消息推送接口
 * 
 * @author Administrator
 *
 */
//@Service
@SuppressWarnings("rawtypes")
public class QQMessagePushServiceImpl implements MessagePushService {

	@Override
	public Map<String, Object> signIn(Map param) throws Exception {
		return null;
	}

	@Override
	public Map<String, Object> getAccessToken(Map param) throws Exception {
		return null;
	}

	@Override
	public Map<String, Object> getOAuthAccessToken(Map param) throws Exception {
		return null;
	}

	@Override
	public String pushNotice(String protocolType,Map param) throws Exception {
		return null;
	}

	@Override
	public String encryp(String data) throws Exception {
		return null;
	}

}
