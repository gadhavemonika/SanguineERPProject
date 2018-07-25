package com.sanguine.webpms.service;

import com.sanguine.webpms.model.clsReservationHdModel;
import com.sanguine.webpms.model.clsRoomCancellationModel;

public interface clsRoomCancellationService {

	public void funAddUpdateRoomCancellationReservationTable(clsReservationHdModel objMaster);

	public clsRoomCancellationModel funGetRoomCancellation(String docCode, String clientCode);

	public clsReservationHdModel funGetReservationModel(String strReservationNo, String clientCode);

	public void funAddUpdateRoomCancellation(clsRoomCancellationModel objModel);
	public void funUpdateRoomStatus(String sql);

}
