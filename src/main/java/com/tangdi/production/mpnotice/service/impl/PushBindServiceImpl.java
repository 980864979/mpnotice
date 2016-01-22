package com.tangdi.production.mpnotice.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tangdi.production.mpnotice.dao.PushBindDao;
import com.tangdi.production.mpnotice.service.PushBindService;

@Service
public class PushBindServiceImpl implements PushBindService {

	@Autowired
	private PushBindDao pushBindDao;

	@Override
	public Map<String, Object> getBindInf(Map<String, Object> param) throws Exception {
		return pushBindDao.selectBindInf(param);
	}

	@Override
	public int addBindInf(Map<String, Object> param) throws Exception {
		return pushBindDao.insertEntity(param);
	}

}
