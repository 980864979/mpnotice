package com.tangdi.production.mpnotice.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.mpnotice.constants.NoticeCT;
import com.tangdi.production.mpnotice.platform.service.MessagePushService;
import com.tangdi.production.mpnotice.context.SpringContext;
import com.tangdi.production.mpnotice.domain.ReturnMsg;

/**
 * 微信账户绑定 页面
 * 
 * @author limiao
 *
 */
@Controller
@SuppressWarnings({ "rawtypes", "unchecked" })
public class WXAccountBindController {
	private static final Logger log = LoggerFactory.getLogger(WXAccountBindController.class);

	@RequestMapping(value = "wxAccountBindView")
	public String accountBindView(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		log.info("");
		session.setAttribute(NoticeCT.WEIXIN_CODE, request.getParameter("code"));
		return "wxAccountBind";
	}

	@RequestMapping(value = "getWeixinoAuth")
	@ResponseBody
	public ReturnMsg getWeixinCode(HttpSession session) throws Exception {
		ReturnMsg rm = new ReturnMsg();
		MessagePushService messagePushService = SpringContext.getBean("WXMessagePushServiceImpl", MessagePushService.class);
		Map param = new HashMap();
		param.put(NoticeCT.WEIXIN_CODE, session.getAttribute(NoticeCT.WEIXIN_CODE));
		param = messagePushService.getOAuthAccessToken(param);
		session.setAttribute(NoticeCT.WEIXIN_ACCESSTOKEN, param.get(NoticeCT.WEIXIN_ACCESSTOKEN));
		session.setAttribute(NoticeCT.WEIXIN_OPENID, param.get(NoticeCT.WEIXIN_OPENID));
		rm.setObj("ok");
		return rm;
	}

}
