package com.sanguine.webbooks.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.dao.clsChargeProcessingDao;
import com.sanguine.webbooks.model.clsChargeProcessingHDModel;

@Service("clsChargeProcessingService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebBooksTransactionManager")
public class clsChargeProcessingServiceImpl implements clsChargeProcessingService {
	@Autowired
	private clsChargeProcessingDao objChargeProcessingDao;

	@Override
	public void funAddUpdateChargeProcessing(clsChargeProcessingHDModel objMaster) {
		objChargeProcessingDao.funAddUpdateChargeProcessing(objMaster);
	}

	@Override
	public void funClearTblChargeGenerationTemp(String strMemberCode) {
		objChargeProcessingDao.funClearTblChargeGenerationTemp(strMemberCode);
	}

	@Override
	public List funGetAllMembers(String clientCode, String propertyCode) {
		return objChargeProcessingDao.funGetAllMembers(clientCode, propertyCode);
	}

	@Override
	public List funCalculateOutstanding(String string, String funGetDate, String funGetDate2, String strMemberCode, String clientCode, String propertyCode) {
		return objChargeProcessingDao.funCalculateOutstanding(string, funGetDate, funGetDate2, strMemberCode, clientCode, propertyCode);
	}

	@Override
	public void funUpdateMemberOutstanding(String memberCode, double dblOutstanding, String clientCode, String propertyCode) {
		objChargeProcessingDao.funUpdateMemberOutstanding(memberCode, dblOutstanding, clientCode, propertyCode);
	}

	@Override
	public List funIsChargeApplicable(String memberCode, String chargeCode, String clientCode, String propertyCode) {
		return objChargeProcessingDao.funIsChargeApplicable(memberCode, chargeCode, clientCode, propertyCode);
	}

}
