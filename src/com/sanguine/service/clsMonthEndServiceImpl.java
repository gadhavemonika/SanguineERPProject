package com.sanguine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsMonthEndDao;
import com.sanguine.model.clsMonthEndModel;

@Service("clsMonthEndService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class clsMonthEndServiceImpl implements clsMonthEndService {

	@Autowired
	private clsMonthEndDao objMonthEndDao;

	@Override
	public void funAddMonthEnd(clsMonthEndModel MonthEndModel) {
		objMonthEndDao.funAddMonthEnd(MonthEndModel);

	}

}
