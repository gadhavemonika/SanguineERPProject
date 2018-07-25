package com.sanguine.webpms.dao;

import java.util.List;

import com.sanguine.webpms.model.clsBillHdModel;
import com.sanguine.webpms.model.clsFolioHdModel;

public interface clsCheckOutDao {

	void funSaveCheckOut(String strRoomNo, String clientCode, String userCode);

	clsFolioHdModel funGetFolioHdModel(String roomNo, String registrationNo, String reservationNo, String clientCode);

	void funSaveCheckOut(clsFolioHdModel objFolioHdModel, clsBillHdModel objBillHdModel);

	public List funGetCheckOut(String roomNo, String billNo, String clientCode);

}
