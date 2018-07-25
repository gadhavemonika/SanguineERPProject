package com.sanguine.webpms.service;

import java.util.List;

import com.sanguine.webpms.model.clsBillHdModel;
import com.sanguine.webpms.model.clsFolioHdModel;

public interface clsCheckOutService {

	public void funSaveCheckOut(String strRoomNo, String clientCode, String userCode);

	public clsFolioHdModel funGetFolioHdModel(String strRoomNo, String strRegistrationNo, String strReservationNo, String clientCode);

	public void funSaveCheckOut(clsFolioHdModel objFolioHdModel, clsBillHdModel objBillHdModel);

	public List funGetCheckOut(String roomNo, String billNo, String clientCode);
}
