package com.sanguine.webbooks.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.dao.clsLetterProcessingDao;
import com.sanguine.webbooks.model.clsLetterProcessingModel;

@Service("clsLetterProcessingService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebBooksTransactionManager")
public class clsLetterProcessingServiceImpl implements clsLetterProcessingService {
	@Autowired
	private clsLetterProcessingDao objLetterProcessingDao;

	@Override
	public void funAddUpdateLetterProcessing(clsLetterProcessingModel objMaster) {
		objLetterProcessingDao.funAddUpdateLetterProcessing(objMaster);
	}

	@Override
	public clsLetterProcessingModel funGetLetterProcessing(String docCode, String clientCode) {
		return objLetterProcessingDao.funGetLetterProcessing(docCode, clientCode);
	}

	@Override
	public List funGetDebtoMemberList(String sqlQuery) {

		return objLetterProcessingDao.funGetDebtoMemberList(sqlQuery);
	}

	@Override
	public void funClearLetterProcessing(String userCode) {
		objLetterProcessingDao.funClearLetterProcessing(userCode);
	}

}
