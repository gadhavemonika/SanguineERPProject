package com.sanguine.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsStructureUpdateDao;

@Service("clsStructureUpdateService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class clsStructureUpdateServiceImpl implements clsStructureUpdateService {
	@Autowired
	private clsStructureUpdateDao objStructureUpdateDao;

	@Override
	public void funUpdateStructure(String clientCode, HttpServletRequest req) {
		objStructureUpdateDao.funUpdateStructure(clientCode,req);
	}

	@Override
	public void funClearTransaction(String clientCode, String[] str) {
		objStructureUpdateDao.funClearTransaction(clientCode, str);

	}

	@Override
	public void funClearMaster(String clientCode, String[] str) {
		objStructureUpdateDao.funClearMaster(clientCode, str);

	}

	@Override
	public void funClearTransactionByPropertyAndLoction(String clientCode, String[] str, String propName, String locName) {
		objStructureUpdateDao.funClearTransactionByPropertyAndLoction(clientCode, str, propName, locName);
	}

}
