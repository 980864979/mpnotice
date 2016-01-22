package com.tangdi.production.mpnotice.platform.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tangdi.production.mpnotice.constants.NoticeCT;
import com.tangdi.production.mpnotice.constants.WeixinConfig;
import com.tangdi.production.mpnotice.platform.service.MessagePushService;
import com.tangdi.production.mpnotice.service.MessageService;
import com.tangdi.production.mpnotice.service.PushAccessService;
import com.tangdi.production.mpnotice.service.PushBindService;
import com.tangdi.production.mpnotice.utils.HttpServletUtil;
import com.tangdi.production.mpnotice.utils.ParamValidate;
import com.tangdi.production.mpnotice.utils.XmlUtils;
import com.tangdi.production.mpnotice.context.SpringContext;

@Controller
@SuppressWarnings("rawtypes")
public class PlatformInController {
	private static final Logger log = LoggerFactory.getLogger(PlatformInController.class);

	@Autowired
	private MessagePushService messagePushService;
	
	@Autowired
	private PushAccessService pushAccessService;

	@Autowired
	private PushBindService pushBindService;

	@RequestMapping(value = "/wenxin")
	public void wenXin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String url = request.getServletPath().substring(1);
		Map parameterMap = HttpServletUtil.getParameterMap(request);

		log.info("微信请求：url={{}}", url);
		log.info("请求参数：{}", parameterMap);

		

		// 微信校验 校验服务器 有效
		if (null != parameterMap.get("echostr") && parameterMap.get("echostr").toString().length() > 0) {
			log.info("微信公众平台，校验服务器有效性开始");
			Map map = messagePushService.signIn(parameterMap);
			String echostr = parameterMap.get("echostr").toString();
			if (parameterMap.get("signature").equals(map.get("digest"))) {
				log.info("服务器校验成功！");
				HttpServletUtil.outPutMsg(response, echostr);
			} else {
				log.info("服务器校验失敗！");
				HttpServletUtil.outPutMsg(response, "");
			}
		} else {
			log.info("用户关注/取消关注开始");
			Map<String, String> xmlMap = XmlUtils.requestXml2Map(request);
			xmlMap.put("openId", xmlMap.get("FromUserName"));
			log.info("接收微信xml数据：{}", xmlMap);

			if (WeixinConfig.WEIXIN_EVENTTYPE_SUBSCRIBE.equals(xmlMap.get("Event"))) {
				log.info("用户关注");

				xmlMap.put(NoticeCT.PUSHMSGTYPE, NoticeCT.PUSHMSGTYPE_TEXT);
				xmlMap.put(NoticeCT.PUSHUSER, xmlMap.get("FromUserName"));
				xmlMap.put(NoticeCT.PUSHTITLE, "欢迎关注棠棣支付公众号");
				xmlMap.put(NoticeCT.PUSHDESCRIPTION, "感谢您关注棠棣支付公众号,请点击[阅读全文] 绑定您的棠棣支付APP 账户,以便于我们及时通知您的账户变更情况");

				String sendStr = messagePushService.pushNotice(NoticeCT.PROTOCOLTYPE_XML, xmlMap);

				log.info("发送微信xml数据：{}", sendStr);
				HttpServletUtil.outPutMsg(response, sendStr);
			}

			if (WeixinConfig.WEIXIN_EVENTTYPE_UNSUBSCRIBE.equals(xmlMap.get("Event"))) {
				log.info("用户取消关注");
			}

		}
	}
	
	public void tuisong(Map<String, Object> parameterMap){
		log.info("请求参数：{}", parameterMap);
		try {
			ParamValidate.doing(parameterMap, "custId", "content", "pushPlatfrom");

			// 1.通过 custId,pushPlatfrom 查询出 商户的 openid
			Map map = new HashMap();
			map.put("custId", parameterMap.get("custId"));
			map.put("platfrom", parameterMap.get("pushPlatfrom"));
			Map bindInfMap = pushBindService.getBindInf(map);
			log.info("推送 平台  信息 , 查询结果：{}", bindInfMap);

			Map accesstokenMap = null;
			// 2.查询 推送 平台 的 订阅号 授权码
			if (NoticeCT.PLATFROM_WX_1.equals(parameterMap.get("pushPlatfrom"))) {
				accesstokenMap = pushAccessService.getAccessToken(NoticeCT.PLATFROM_WX_1);
			}
			log.info("推送 平台 的 订阅号 授权码 查询结果：{}", accesstokenMap);
			messagePushService = SpringContext.getBean("WXMessagePushServiceImpl", MessagePushService.class);

			// 3.消息推送
			if (NoticeCT.PLATFROM_WX_1.equals(parameterMap.get("pushPlatfrom"))) {
				log.info("往微信发送消息开始");
				Map params = new LinkedHashMap<String, String>();
				params.put("touser", bindInfMap.get("platId"));
				params.put("msgtype", "text");
				Map textMap = new HashMap<String, String>();
				textMap.put("content", parameterMap.get("content"));
				params.put("text", textMap);
				String strJson = messagePushService.pushNotice(NoticeCT.PROTOCOLTYPE_JSON, params);
				log.info("发送数据：{}", strJson);

				String uri = String.format(WeixinConfig.WEIXIN_SENDMESSAGEURI, accesstokenMap.get("accessToken"));
				log.info("发送url:{}", uri);
				//String msg = httpRequestClient.outputStreamPost(uri, strJson);
				//log.info("微信 推送 返回的  消息 :{}", msg);

				log.info("如果 下面 报错   <不用管> <不用管> <不用管> <不用管> <不用管>    因为 推送 成功了。。。。");
			}
		} catch (Exception e) {
			log.error(" 推送 报错了     报错了     报错了    {}", e);
		}
	}
}