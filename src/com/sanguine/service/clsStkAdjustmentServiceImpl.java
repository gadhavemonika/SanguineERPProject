package com.sanguine.service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsStkAdjustmentDao;
import com.sanguine.model.clsStkAdjustmentDtlModel;
import com.sanguine.model.clsStkAdjustmentHdModel;

@Repository("clsStkAdjustmentService")
public class clsStkAdjustmentServiceImpl implements clsStkAdjustmentService {
	@Autowired
	private clsStkAdjustmentDao objStkAdjDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdate(clsStkAdjustmentHdModel object) {
		objStkAdjDao.funAddUpdate(object);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdateDtl(clsStkAdjustmentDtlModel object) {
		objStkAdjDao.funAddUpdateDtl(object);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsStkAdjustmentHdModel> funGetList(String clientCode) {
		return objStkAdjDao.funGetList(clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetObject(String SACode, String clientCode) {
		return objStkAdjDao.funGetObject(SACode, clientCode);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetDtlList(String SACode, String clientCode) {
		return objStkAdjDao.funGetDtlList(SACode, clientCode);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funDeleteDtl(String SACode, String clientCode) {
		objStkAdjDao.funDeleteDtl(SACode, clientCode);
		;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funDeleteHd(String SACode, String clientCode) {
		objStkAdjDao.funDeleteHd(SACode, clientCode);
		;
	}

	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetStkAdjListReport(String fromDate, String toDate, String clientCode) {
		return objStkAdjDao.funGetStkAdjListReport(fromDate, toDate, clientCode);
	}

	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetStockAdjFlashData(String sql, String clientCode, String userCode) {
		return objStkAdjDao.funGetStockAdjFlashData(sql, clientCode, userCode);
	}

	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public clsStkAdjustmentHdModel funGetDataOfModel(String SACode, String clientCode) {
		return objStkAdjDao.funGetDataOfModel(SACode, clientCode);

	}

	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsStkAdjustmentDtlModel> funGetDatewiseDtlList(String fromDate, String toDate, String clientCode) {
		return objStkAdjDao.funGetDatewiseDtlList(fromDate, toDate, clientCode);
	}

	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsStkAdjustmentDtlModel> funGetReportMonthlyDatewiseDtlList(String fromDate, String toDate, String clientCode) {
		return objStkAdjDao.funGetReportMonthlyDatewiseDtlList(fromDate, toDate, clientCode);
	}

	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public clsStkAdjustmentHdModel funGetDataModelByInvCode(String invCode, String clientCode) {
		return objStkAdjDao.funGetDataModelByInvCode(invCode, clientCode);
	}

}
