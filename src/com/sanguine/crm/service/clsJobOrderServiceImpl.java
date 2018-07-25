package com.sanguine.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.crm.dao.clsJobOrderDao;
import com.sanguine.crm.model.clsJobOrderModel;

@Service("clsJobOrderService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "hibernateTransactionManager")
public class clsJobOrderServiceImpl implements clsJobOrderService {
	@Autowired
	private clsJobOrderDao objJobOrderDao;

	@Override
	public boolean funAddUpdateJobOrder(clsJobOrderModel objMaster) {
		return objJobOrderDao.funAddUpdateJobOrder(objMaster);
	}

	@Override
	public List<Object> funGetJobOrderUsingSOCode(String strSOcode, String clientCode) {
		return objJobOrderDao.funGetJobOrderUsingSOCode(strSOcode, clientCode);
	}

	@Override
	public List<Object> funGetJobOrder(String strJOcode, String clientCode) {
		return objJobOrderDao.funGetJobOrder(strJOcode, clientCode);
	}

}
