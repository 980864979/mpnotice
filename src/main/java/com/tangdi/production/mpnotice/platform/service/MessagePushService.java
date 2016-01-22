package com.tangdi.production.mpnotice.platform.service;

import java.util.Map;

/***
 * 消息推送接口<br>
 * 往外部平台(微信、QQ、支付宝)通送
 * 
 * @author sunhaining
 *
 */
@SuppressWarnings("rawtypes")
public interface MessagePushService {

	/**
	 * 第三方平台（微信、QQ、支付宝） 检验服务器是否正常。
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> signIn(Map param) throws Exception;

	/**
	 * 第三方平台（微信、QQ、支付宝） 获取 访问 订阅号 权限access_token
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getAccessToken(Map param) throws Exception;

	/**
	 * 第三方平台（微信、QQ、支付宝） 获取 访问 用户 权限access_token
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getOAuthAccessToken(Map param) throws Exception;

	/**
	 * 发送数据到第三方平台（微信、QQ、支付宝）
	 * 
	 * @param protocolType
	 *            协议类型: json/xml
	 * @param param
	 *            NoticeCT.PUSHUSER 推送 用户<br>
	 *            NoticeCT.PUSHMSGTYPE (NoticeCT.PUSHMSG_TEXT 纯文字/NoticeCT.PUSHMSG_NEWS 图文,带URL)<br>
	 *            NoticeCT.PUSHDESCRIPTION 推送 详细内容
	 * @return
	 * @throws Exception
	 */
	public String pushNotice(String protocolType, Map param) throws Exception;

	/***
	 * 加密数据
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	String encryp(String data) throws Exception;

}
