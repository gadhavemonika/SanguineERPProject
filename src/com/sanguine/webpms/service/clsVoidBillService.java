package com.sanguine.webpms.service;

import com.sanguine.webpms.model.clsBillHdModel;
import com.sanguine.webpms.model.clsVoidBillHdModel;


public interface clsVoidBillService {

	public clsBillHdModel funGetBillData(String roomNo,String strBillNo,String strClientCode);

	public void funUpdateVoidBillData(clsBillHdModel objBillModel, clsVoidBillHdModel objVoidHdModel);

	public void funUpdateVoidBillItemData(clsBillHdModel objBillModel, clsVoidBillHdModel objVoidHdModel);
	
	public void funUpdateBillData(clsBillHdModel objBillModel);

	public void funSaveVoidBillData(clsVoidBillHdModel objVoidHdModel);
}
