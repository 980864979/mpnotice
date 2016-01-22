package com.tangdi.production.mpnotice.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpnotice.utils.TemplateUtil;
import com.tangdi.production.mpnotice.constants.NoticeCT;
import com.tangdi.production.mpnotice.dao.MerchantDao;
import com.tangdi.production.mpnotice.dao.MessageDao;
import com.tangdi.production.mpnotice.service.MessageService;
import com.tangdi.production.mpnotice.service.ValidateCodeService;
import com.tangdi.production.mpnotice.utils.TdExpBasicFunctions;

@Service
public class MessageServiceImpl implements MessageService {
	private static Logger log = LoggerFactory.getLogger(MessageServiceImpl.class);

	@Autowired
	private MessageDao dao;

	@Autowired
	private MerchantDao merchantDao;

	@Autowired
	private ValidateCodeService vcservice;

	@Override
	public String getVidateCode(Map<String, Object> param) throws Exception {
		// 1、校验参数：手机号是否存在
		//ParamValidate.doing(param, "mobile");
		String seq = "";
		// 初始校验码
		String validateCode = "000000";

		// 2、验证手机号是否存在
		String custId = merchantDao.queryCustId(param);
		if (custId == null || "".equals(custId)) {
			throw new Exception("商户不存在！");
		}

		// 3、生成验证码
		try {
			// 生产环境把此处打开
			// validateCode = TdExpBasicFunctions.RANDOM(6, "2");
			log.info("生成的验证码:[{}]", validateCode);
			param.put("validateCode", validateCode);
			vcservice.addCode(param);

			log.info("通过短信类型,查询短信模版.");
			Map<String, Object> template = queryTemplate(param);

			String smsdata = TemplateUtil.convert(template.get("smsContent"), validateCode);
			param.put("smsdata", smsdata);

			try {
				//seq = "1" + TdExpBasicFunctions.GETDATE().substring(2) + seqNoService.getSeqNoNew("SMS_ID1", "8", "1");
				
				param.put("seq", seq);
			} catch (Exception e) {
				throw new TranException(ExcepCode.EX001101, "短信序列号获取失败!", e);
			}

			log.info("保存发送短信记录...");
			addSendMssage(param);
		} catch (TranException e) {
			// 捕获异常后, 把EX001101异常消息返回给APP
			throw new TranException(ExcepCode.EX001101, e);
		}

		try {
			log.info("调用渠道发送短信");
			// TODO 调用渠道发送短信
			// 更新短信状态
			updateMssage(param);
		} catch (TranException e) {
			param.put("smsStatus", NoticeCT.SMS_SEND_NG);
			updateMssage(param);
			// 捕获异常后, 把EX001101异常消息返回给APP
			throw new TranException(ExcepCode.EX001101, e);
		}
		return validateCode;
	}

	/**
	 * 查询短信模板
	 */
	private Map<String, Object> queryTemplate(Map<String, Object> param) throws TranException {
		log.debug("查询短信模版,短信类型:{}", param);
		Map<String, Object> rmap = new HashMap<String, Object>();
		rmap = dao.selectTemplate(param);
		log.debug("查询结果:{}", rmap);
		return rmap;
	}

	/**
	 * 添加发送短信记录
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private int addSendMssage(Map<String, Object> param) throws TranException {
		log.debug("保存发送记录开始. 参数：{}", param);

		int rt = 0;
		param.put("smsId", param.get("seq"));
		param.put("smsType", param.get("smsType"));
		param.put("smsMoblie", param.get("custMobile"));
		param.put("smsBody", param.get("smsdata"));
		param.put("smsDate", TdExpBasicFunctions.GETDATETIME());
		param.put("smsStatus", NoticeCT.SMS_SEND_INIT);
		try {
			rt = dao.insertSendMssage(param);
			log.debug("保存发送记录完成. 状态值：{}", rt);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX001106, e);
		}
		return rt;
	}

	/**
	 * 更新短信状态
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private int updateMssage(Map<String, Object> param) throws TranException {
		log.debug("更新短信状态... 参数：{}", param);

		int rt = 0;
		param.put("smsId", param.get("seq"));
		param.put("smsStatus", param.get("smsStatus"));
		try {
			rt = dao.updateMessage(param);
			log.debug("更新短信状态完成. 状态值：{}", rt);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX001107, e);
		}
		return rt;
	}

}
