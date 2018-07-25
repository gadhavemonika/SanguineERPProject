package com.sanguine.excise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.excise.dao.clsExciseMonthEndDao;
import com.sanguine.excise.model.clsExciseMonthEndModel;

@Service("clsExciseMonthEndService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class clsExciseMonthEndServiceImpl implements clsExciseMonthEndService {

	@Autowired
	private clsExciseMonthEndDao objMonthEndDao;

	@Override
	public void funAddMonthEnd(clsExciseMonthEndModel MonthEndModel) {
		objMonthEndDao.funAddMonthEnd(MonthEndModel);

	}

}
