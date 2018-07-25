package com.sanguine.webpms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanguine.webpms.dao.clsVoidBillDao;
import com.sanguine.webpms.model.clsBillHdModel;
import com.sanguine.webpms.model.clsVoidBillHdModel;

@Service("clsVoidBillService")
public class clsVoidBillServiceImpl implements clsVoidBillService{

	@Autowired 
	clsVoidBillDao objVoidBillDao;
	
	@Override
	public clsBillHdModel funGetBillData(String roomNo, String strBillNo, String strClientCode) {
		
		return objVoidBillDao.funGetBillData(roomNo, strBillNo, strClientCode);
	}

	@Override
	public void funUpdateVoidBillData(clsBillHdModel objBillModel, clsVoidBillHdModel objVoidHdModel) {
		objVoidBillDao.funUpdateVoidBillData(objBillModel,objVoidHdModel);
	}

	@Override
	public void funUpdateVoidBillItemData(clsBillHdModel objBillModel, clsVoidBillHdModel objVoidHdModel) {
		objVoidBillDao.funUpdateVoidBillItemData(objBillModel,objVoidHdModel);
		
	}

	@Override
	public void funUpdateBillData(clsBillHdModel objBillModel) {
		objVoidBillDao.funUpdateBillData(objBillModel);
		
	}
	
	@Override
	public void funSaveVoidBillData(clsVoidBillHdModel objVoidHdModel){
		objVoidBillDao.funSaveVoidBillData(objVoidHdModel);
	}

}
