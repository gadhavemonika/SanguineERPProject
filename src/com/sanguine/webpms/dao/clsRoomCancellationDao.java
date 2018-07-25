package com.sanguine.webpms.dao;

import com.sanguine.webpms.model.clsReservationHdModel;
import com.sanguine.webpms.model.clsRoomCancellationModel;

public interface clsRoomCancellationDao {

	public void funAddUpdateRoomCancellationReservationTable(clsReservationHdModel objMaster);

	public clsRoomCancellationModel funGetRoomCancellation(String docCode, String clientCode);

	public clsReservationHdModel funGetReservationModel(String reservationNo, String clientCode);

	public void funAddUpdateRoomCancellation(clsRoomCancellationModel objModel);
	
	public void funUpdateRoomStatus(String sqlQuery);

}
