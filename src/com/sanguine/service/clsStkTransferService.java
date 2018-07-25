package com.sanguine.service;

import java.util.List;

import com.sanguine.model.clsStkAdjustmentDtlModel;
import com.sanguine.model.clsStkAdjustmentHdModel;
import com.sanguine.model.clsStkPostingDtlModel;
import com.sanguine.model.clsStkPostingHdModel;
import com.sanguine.model.clsStkTransferDtlModel;
import com.sanguine.model.clsStkTransferHdModel;

public interface clsStkTransferService {
	public void funAddUpdate(clsStkTransferHdModel object);

	public void funAddUpdateDtl(clsStkTransferDtlModel object);

	public List funGetList(String clientCode);

	public List funGetDtlList(String SACode, String clientCode);

	public List funGetObject(String SACode, String clientCode);

	public void funDeleteDtl(String SACode, String clientCode);

	public List funGetProdAgainstActualProduction(String strOPCode, String clientCode);

	public clsStkTransferHdModel funGetModel(String strPDCode, String clientCode);
	
	public List funStkforSRDetails(String strLocFrom, String strLocTo, String strClientCode);

}
