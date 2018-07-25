package com.sanguine.service;

import java.util.List;

import com.sanguine.model.clsStkAdjustmentDtlModel;
import com.sanguine.model.clsStkAdjustmentHdModel;

@SuppressWarnings("rawtypes")
public interface clsStkAdjustmentService {
	public void funAddUpdate(clsStkAdjustmentHdModel object);

	public void funAddUpdateDtl(clsStkAdjustmentDtlModel object);

	public List<clsStkAdjustmentHdModel> funGetList(String clientCode);

	public List<clsStkAdjustmentDtlModel> funGetDtlList(String SACode, String clientCode);

	public List funGetObject(String SACode, String clientCode);

	public void funDeleteDtl(String SACode, String clientCode);

	public void funDeleteHd(String SACode, String clientCode);

	public List funGetStkAdjListReport(String fromDate, String toDate, String clientCode);

	public List funGetStockAdjFlashData(String sql, String clientCode, String userCode);

	public clsStkAdjustmentHdModel funGetDataOfModel(String SACode, String clientCode);

	public List<clsStkAdjustmentDtlModel> funGetDatewiseDtlList(String fromDate, String toDate, String clientCode);

	public List<clsStkAdjustmentDtlModel> funGetReportMonthlyDatewiseDtlList(String fromDate, String toDate, String clientCode);

	public clsStkAdjustmentHdModel funGetDataModelByInvCode(String invCode, String clientCode);

}
