package com.tangdi.production.mpnotice.constants;

/**
 * 
 * 微信 参数 配置 类
 * 
 * @author limiao
 *
 */
public class WeixinConfig {
	/**
	 * AppID(应用ID)
	 */
	//public final static String WEIXIN_APPID = "wx119c3c231a2a1409";
	public final static String WEIXIN_APPID = "wx40b587cb8d5f3633";
	
	/**
	 * AppSecret(应用密钥)
	 */
	//public final static String WEIXIN_APPSECRET = "f9da027c4435853231843ceb95545931";
	public final static String WEIXIN_APPSECRET = "d4624c36b6795d1d99dcf0547af5443d";
	
	/**
	 * 微信 Token 必须为英文或数字，长度为3-32字符。
	 */
	public final static String WEIXIN_TOKEN = "0007654321";

	/**
	 * 微信 EncodingAESKey 消息加密密钥由43位字符组成
	 */
	public final static String WEIXIN_ENCODINGAESKEY = "w4mqceR8HjRPdwW9RPDGGTYN4LA3xrrXxKlOaJxwrRB";

	/**
	 * FromUserName
	 */
	public final static String WEIXIN_FROMUSERNAME = "gh_dda000fc300f";

	public final static String WEIXIN_EVENTTYPE_SUBSCRIBE = "subscribe";

	public final static String WEIXIN_EVENTTYPE_UNSUBSCRIBE = "unsubscribe";
	
	
	
	/**
	 * 推送 微信用户绑定系统账户 网页URL
	 */
	public final static String WEIXIN_BINDPREVIEWURL="http://121.42.48.139/ntser/wxAccountBindView.nt";
	public final static String WEIXIN_BINDPREVIEWPIC="http://210.22.153.30:8899/pay/img/logo.png";
	
	/**
	 * 推送 微信用户绑定系统账户 网页URL
	 */
	public final static String WEIXIN_BINDURL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WeixinConfig.WEIXIN_APPID + "&redirect_uri=%1$s&response_type=code&scope=snsapi_base&state=wenxin_state#wechat_redirect";
	

	/**
	 * 获取 订阅号 访问权限 URL
	 */
	public final static String WEIXIN_ACCESSTOKENURI = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + WeixinConfig.WEIXIN_APPID + "&secret=" + WeixinConfig.WEIXIN_APPSECRET;

	/**
	 * 获取 oauth权限 访问权限 URL
	 */
	public final static String WEIXIN_OAUTHACCESSTOKENURI = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + WeixinConfig.WEIXIN_APPID + "&secret=" + WeixinConfig.WEIXIN_APPSECRET + "&code=%1$s&grant_type=authorization_code";
	
	public final static String WEIXIN_SENDMESSAGEURI = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=%1$s";

}
