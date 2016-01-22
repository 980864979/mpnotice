package com.tangdi.production.mpnotice.constants;

public class NoticeCT {

	/**
	 * 发送 数据类型 json
	 */
	public final static String PROTOCOLTYPE_JSON = "json";
	/**
	 * 发送 数据类型 json
	 */
	public final static String PROTOCOLTYPE_XML = "xml";

	public final static String WEIXIN = "_weixin";

	public final static String WEIXIN_CODE = "_weixinCode";

	public final static String WEIXIN_ACCESSTOKEN = "_weixinAccessToken";
	public final static String WEIXIN_ACCESSTOKEN_EXPIRESIN = "_weixinAccessTokenexpiresIn";
	
	public final static String WEIXIN_OPENID = "_weixinopenid";

	/**
	 * 推送 用户
	 */
	public final static String PUSHUSER = "user";

	/**
	 * 消息类型
	 */
	public final static String PUSHMSGTYPE = "MsgType";

	/**
	 * 纯文本
	 */
	public final static String PUSHMSGTYPE_TEXT = "text";

	/**
	 * 图文
	 */
	public final static String PUSHMSGTYPE_NEWS = "news";

	/**
	 * 推送 标题
	 */
	public final static String PUSHTITLE = "title";
	/**
	 * 推送 详细内容
	 */
	public final static String PUSHDESCRIPTION = "欢迎关注尤子安的测试账号";

	/**
	 * 验证码类型：04 第三方平台绑定
	 */
	public final static String CODE_TYPE_04 = "04";
	/**
	 * 短信发送状态 : 00 未发送
	 */
	public final static String SMS_SEND_INIT = "00";
	/**
	 * 短信发送状态 : 01 已发送
	 */
	public final static String SMS_SEND_OK = "01";
	/**
	 * 短信发送状态 : 02 发送失败
	 */
	public final static String SMS_SEND_NG = "02";
	/**
	 * 验证码状态0 未使用
	 */
	public final static String VALIDATE_CODE_STATUS_0 = "0";
	/**
	 * 验证码状态1 失效
	 */
	public final static String VALIDATE_CODE_STATUS_1 = "1";
	/**
	 * 第三方平台：1微信
	 */
	public final static String PLATFROM_WX_1 = "1";
	/**
	 * 第三方平台：2QQ
	 */
	public final static String PLATFROM_QQ_2 = "2";
	/**
	 * 第三方平台：3阿里
	 */
	public final static String PLATFROM_AL_3 = "3";
}
