package com.sanguine.webpms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.sanguine.webpms.dao.clsDayEndDao;
import com.sanguine.webpms.model.clsDayEndHdModel;

@Service("clsDayEndService")
public class clsDayEndServiceImpl implements clsDayEndService {
	@Autowired
	private clsDayEndDao objDayEndDao;

	@Override
	public void funAddUpdateDayEndHd(clsDayEndHdModel objHdModel) {
		objDayEndDao.funAddUpdateDayEndHd(objHdModel);
	}
}
