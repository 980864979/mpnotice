package com.tangdi.production.mpnotice.platform.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.tangdi.production.mpnotice.platform.service.MessagePushService;

/***
 * 阿里巴巴消息推送接口
 * 
 * @author sunhaining
 *
 */
//@Service
@SuppressWarnings("rawtypes")
public class AliPayMessagePushServiceImpl implements MessagePushService {

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
