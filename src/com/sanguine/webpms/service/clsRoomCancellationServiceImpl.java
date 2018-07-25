package com.sanguine.webpms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webpms.dao.clsRoomCancellationDao;
import com.sanguine.webpms.model.clsReservationHdModel;
import com.sanguine.webpms.model.clsRoomCancellationModel;

@Service("clsRoomCancellationService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebPMSTransactionManager")
public class clsRoomCancellationServiceImpl implements clsRoomCancellationService {
	@Autowired
	private clsRoomCancellationDao objRoomCancellationDao;

	@Override
	public void funAddUpdateRoomCancellationReservationTable(clsReservationHdModel objMaster) {
		objRoomCancellationDao.funAddUpdateRoomCancellationReservationTable(objMaster);
	}

	@Override
	public clsRoomCancellationModel funGetRoomCancellation(String docCode, String clientCode) {
		return objRoomCancellationDao.funGetRoomCancellation(docCode, clientCode);
	}

	@Override
	public clsReservationHdModel funGetReservationModel(String reservationNo, String clientCode) {
		return objRoomCancellationDao.funGetReservationModel(reservationNo, clientCode);
	}

	@Override
	public void funAddUpdateRoomCancellation(clsRoomCancellationModel objModel) {
		objRoomCancellationDao.funAddUpdateRoomCancellation(objModel);

	}

	@Override
	public void funUpdateRoomStatus(String sql) 
	{
		objRoomCancellationDao.funUpdateRoomStatus(sql);
		
	}

	
}
