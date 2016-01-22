package com.tangdi.production.mpnotice.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tangdi.production.mpnotice.constants.NoticeCT;
import com.tangdi.production.mpnotice.dao.PushAccessDao;
import com.tangdi.production.mpnotice.platform.service.MessagePushService;
import com.tangdi.production.mpnotice.service.PushAccessService;
import com.tangdi.production.mpnotice.context.SpringContext;
import com.tangdi.production.mpnotice.utils.TdExpBasicFunctions;

@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class PushAccessServiceImpl implements PushAccessService {
	private static Logger log = LoggerFactory.getLogger(PushAccessServiceImpl.class);
	@Autowired
	private PushAccessDao pushAccessDao;

	@Autowired
	private MessagePushService messagePushService;

	@Override
	public Map<String, Object> getAccessToken(String plantForm) throws Exception {
		return pushAccessDao.selectByPlatfrom(plantForm);
	}

	@Override
	public int modifyAccessToken(Map<String, Object> param) throws Exception {
		return pushAccessDao.updateEntity(param);
	}

	@Override
	public void process() throws Exception {
		log.info(" 获取微信   accessToken   start");
		String date = TdExpBasicFunctions.GETDATETIME();
		Map map = messagePushService.getAccessToken(null);
		map.put("accessToken", map.get(NoticeCT.WEIXIN_ACCESSTOKEN));
		String[] args = {date,"+","s",map.get(NoticeCT.WEIXIN_ACCESSTOKEN_EXPIRESIN).toString()};
		map.put("expiresIn", TdExpBasicFunctions.CALCTIME(args));
		map.put("date", date);
		map.put("platfrom", "1");
		
		modifyAccessToken(map);

		log.info(" 获取微信   accessToken   end");
	}
}