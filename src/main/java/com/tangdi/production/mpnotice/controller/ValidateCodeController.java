package com.tangdi.production.mpnotice.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.mpnotice.constants.NoticeCT;
import com.tangdi.production.mpnotice.service.MessageService;
import com.tangdi.production.mpnotice.service.ValidateCodeService;
import com.tangdi.production.mpnotice.domain.ReturnMsg;

/**
 * 验证码
 * 
 * @author youdd
 *
 */
@Controller
public class ValidateCodeController {
	private static final Logger log = LoggerFactory.getLogger(ValidateCodeController.class);

	@Autowired
	private MessageService messageService;

	@Autowired
	private ValidateCodeService validateCodeService;

	@RequestMapping(value = "getValidateCode")
	@ResponseBody
	public ReturnMsg getValidateCode(@RequestParam("moblie") String mobile) throws Exception {
		log.info("验证码获取开始...");
		log.info("请求数据为：{}", mobile);
		ReturnMsg rm = new ReturnMsg();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("mobile", mobile);
		try {
			messageService.getVidateCode(param);
		} catch (Exception e) {
			log.error("获取验证码错误！");
			rm.setObj("fail");
			return rm;
		}
		rm.setObj("success");
		return rm;
	}

	@RequestMapping(value = "validate")
	@ResponseBody
	public ReturnMsg validate(@RequestParam("moblie") String mobile, @RequestParam("code") String code, @RequestParam("fw") String fw, HttpSession session) throws Exception {
		log.info("验证码验证交易开始...");
		log.info("请求数据为：{}", mobile, code, fw);
		ReturnMsg rm = new ReturnMsg();
		// 拼装参数
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("mobile", mobile);
		param.put("code", code);
		param.put("fw", fw);
		// 商户第三方平台唯一标识
		String platId = "";
		if (NoticeCT.WEIXIN.equals(fw)) {
			platId = NoticeCT.WEIXIN_OPENID;
			param.put("platfrom", NoticeCT.PLATFROM_WX_1);// 微信平台
		}
		param.put("platId", session.getAttribute(platId));

		try {
			// 校验推送码
			 if(!validateCodeService.validate(param)){
			 log.info("推送码错误！");
			 rm.setObj("fail");
			 return rm;
			 }
			if (validateCodeService.bindCust(param)) {
				log.info("绑定成功！");
				rm.setObj("success");
				return rm;
			} else {
				log.info("绑定失败！");
				rm.setObj("fail");
				return rm;
			}
		} catch (Exception e) {
			rm.setObj("fail");
			return rm;
		}
	}
}
