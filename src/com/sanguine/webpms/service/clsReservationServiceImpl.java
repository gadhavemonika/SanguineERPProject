package com.sanguine.webpms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.sanguine.webpms.dao.clsReservationDao;

import com.sanguine.webpms.model.clsReservationDtlModel;
import com.sanguine.webpms.model.clsReservationHdModel;
import com.sanguine.webpms.model.clsReservationRoomRateModelDtl;
import com.sanguine.webpms.model.clsRoomPackageDtl;

@Service("clsReservationService")
public class clsReservationServiceImpl implements clsReservationService {
	@Autowired
	private clsReservationDao objReservationDao;

	@Override
	public void funAddUpdateReservationHd(clsReservationHdModel objHdModel, String bookingType) {

		objReservationDao.funAddUpdateReservationHd(objHdModel, bookingType);
	}

	@Override
	public clsReservationHdModel funGetReservationList(String reservationNo, String clientCode, String propertyCode) {
		return objReservationDao.funGetReservationList(reservationNo, clientCode, propertyCode);
	}

	public List<clsReservationRoomRateModelDtl> funGetReservationRoomRateList(String reservationNo, String clientCode, String roomno) {
		return objReservationDao.funGetReservationRoomRateList(reservationNo, clientCode, roomno);
	}
	
	public List<clsRoomPackageDtl> funGetReservationIncomeList(String reservationNo, String clientCode) {
		return objReservationDao.funGetReservationIncomeList(reservationNo, clientCode);
	}
	

}
