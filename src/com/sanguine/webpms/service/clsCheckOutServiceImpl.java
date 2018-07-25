package com.sanguine.webpms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanguine.webpms.dao.clsCheckOutDao;
import com.sanguine.webpms.model.clsBillHdModel;
import com.sanguine.webpms.model.clsFolioHdModel;

@Service("clsCheckOutService")
public class clsCheckOutServiceImpl implements clsCheckOutService {
	@Autowired
	private clsCheckOutDao objCheckOutDao;

	@Override
	public void funSaveCheckOut(String strRoomNo, String clientCode, String userCode) {
		objCheckOutDao.funSaveCheckOut(strRoomNo, clientCode, userCode);
	}

	@Override
	public void funSaveCheckOut(clsFolioHdModel objFolioHdModel, clsBillHdModel objBillHdModel) {
		objCheckOutDao.funSaveCheckOut(objFolioHdModel, objBillHdModel);
	}

	@Override
	public clsFolioHdModel funGetFolioHdModel(String strRoomNo, String strRegistrationNo, String strReservationNo, String clientCode) {
		clsFolioHdModel objFolioHdModel = objCheckOutDao.funGetFolioHdModel(strRoomNo, strRegistrationNo, strReservationNo, clientCode);
		return objFolioHdModel;
	}

	public List funGetCheckOut(String roomNo, String billNo, String clientCode) {
		return objCheckOutDao.funGetCheckOut(roomNo, billNo, clientCode);
	}
}
