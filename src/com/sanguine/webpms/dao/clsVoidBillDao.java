package com.sanguine.webpms.dao;

import com.sanguine.webpms.model.clsBillHdModel;
import com.sanguine.webpms.model.clsVoidBillHdModel;

public interface clsVoidBillDao {

	
	public clsBillHdModel funGetBillData(String roomNo,String strBillNo,String strClientCode);

	public void funUpdateVoidBillData(clsBillHdModel objBillModel, clsVoidBillHdModel objVoidHdModel);

	public void funUpdateVoidBillItemData(clsBillHdModel objBillModel, clsVoidBillHdModel objVoidHdModel);
	
	public void funUpdateBillData(clsBillHdModel objBillModel);

	public void funSaveVoidBillData(clsVoidBillHdModel objVoidHdModel);

}
