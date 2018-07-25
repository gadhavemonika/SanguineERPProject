package com.sanguine.webpms.service;

import java.util.List;


import com.sanguine.webpms.model.clsReservationHdModel;
import com.sanguine.webpms.model.clsReservationRoomRateModelDtl;
import com.sanguine.webpms.model.clsRoomPackageDtl;

public interface clsReservationService {

	public void funAddUpdateReservationHd(clsReservationHdModel objHdModel, String bookingType);

	public clsReservationHdModel funGetReservationList(String reservationNo, String clientCode, String propertyCode);

	public List<clsReservationRoomRateModelDtl> funGetReservationRoomRateList(String reservationNo, String clientCode, String roomno) ;
	
	public List<clsRoomPackageDtl> funGetReservationIncomeList(String reservationNo, String clientCode) ;
	
	
}
