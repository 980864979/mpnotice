package com.tangdi.production.mpnotice.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tangdi.production.mpnotice.service.PushAccessService;

public class WXAccessTokenTask {
	private static Logger log = LoggerFactory.getLogger(WXAccessTokenTask.class);

	@Autowired
	private PushAccessService pushAccessService;

	public void run() {
		log.debug("微信  accessToken 获取开始   Task Start");
		try {
			pushAccessService.process();
		} catch (Exception e) {
			log.error("微信  accessToken  Task Exception:  {}", e);
		}
		log.debug("微信  accessToken 获取开始   Task End");
	}
}
