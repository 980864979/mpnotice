package com.tangdi.production.mpnotice.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.util.ParamValidate;
import com.tangdi.production.mpnotice.constants.NoticeCT;
import com.tangdi.production.mpnotice.dao.MerchantDao;
import com.tangdi.production.mpnotice.dao.PushBindDao;
import com.tangdi.production.mpnotice.dao.ValidateCodeDao;
import com.tangdi.production.mpnotice.service.ValidateCodeService;
import com.tangdi.production.mpnotice.utils.TdExpBasicFunctions;

/**
 * 验证码服务接口实现类
 * 
 * @author youdd
 *
 */
@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {

	private static Logger log = LoggerFactory.getLogger(ValidateCodeServiceImpl.class);
	@Autowired
	private ValidateCodeDao validateCodeDao;

	@Autowired
	private PushBindDao putDao;

	@Autowired
	private MerchantDao merchantDao;

	/**
	 * 1.查询是否存在未被使用的验证码 （若存在执行步骤2） 2.修改验证码状态为过期 3.新增验证码数据
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int addCode(Map<String, Object> param) throws TranException {
		log.debug("写入验证码... 参数:{}", param);
		Map<String, Object> rspMap = null;
		// 验证码类型：微信绑定
		param.put("codeType", "04");
		log.info("1.查询验证码.");
		try {
			rspMap = validateCodeDao.selectEntity(param);
			log.info("验证码查询结果：{}", rspMap);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000412, "验证验证码异常", e);
		}

		log.info("2.修改验证码状态为过期.");
		if (rspMap != null) {
			rspMap.put("codeStatus", NoticeCT.VALIDATE_CODE_STATUS_1);
			try {
				validateCodeDao.updateEntity(rspMap);
			} catch (Exception e) {
				throw new TranException(ExcepCode.EX001101, "验证码过期状态更新失败");
			}
		}

		log.info("3.新增验证码数据.");
		int rt = 0;
		try {
			param.put("sendTime", TdExpBasicFunctions.GETDATETIME());
			param.put("msgCode", param.get("validateCode"));
			param.put("codeStatus", NoticeCT.VALIDATE_CODE_STATUS_0);
			rt = validateCodeDao.insertEntity(param);
			log.debug("写入验证码完成:{}", param);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX000411, "验证码插入异常", e);
		}
		if (rt <= 0) {
			throw new TranException(ExcepCode.EX000411, "获取验证码失败");
		}
		return rt;
	}

	@Override
	public boolean validate(Map<String, Object> param) throws TranException {
		int count = merchantDao.selectPushCode(param);
		if (count <= 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean bindCust(Map<String, Object> param) throws TranException {
		ParamValidate.doing(param, "mobile", "platId", "platfrom");
		try {
			// 获取custid
			param.put("custId", merchantDao.queryCustId(param));
			param.put("bindTime", TdExpBasicFunctions.GETDATETIME());
			// 插入关系表
			putDao.insertEntity(param);
		} catch (TranException e) {
			log.info("bindCust_绑定失败！");
			throw new TranException("绑定失败！");
		}
		return true;
	}
}
