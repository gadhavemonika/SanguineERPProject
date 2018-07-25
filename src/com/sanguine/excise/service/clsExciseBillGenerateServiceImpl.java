package com.sanguine.excise.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.excise.dao.clsExciseBillGenerateDao;

@SuppressWarnings("rawtypes")
@Service("clsExciseBillGenerateService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "OtherTransactionManager")
public class clsExciseBillGenerateServiceImpl implements clsExciseBillGenerateService {
	@Autowired
	private clsExciseBillGenerateDao objBillGenerate;

	@Override
	public List funGetList(String strFromDate, String strToDate, String clientCode) {
		return objBillGenerate.funGetList(strFromDate, strToDate, clientCode);
	}

	@Override
	public List funGetSalesDtlList(long intId, String clientCode) {
		return objBillGenerate.funGetSalesDtlList(intId, clientCode);
	}

	@Override
	public void funDeleteSaleData(String strFromDate, String strToDate, String clientCode, String licenceCode) {
		objBillGenerate.funDeleteSaleData(strFromDate, strToDate, clientCode, licenceCode);
	}

	@Override
	public int funExecute(String sql) {
		return objBillGenerate.funExecute(sql);
	}

}
