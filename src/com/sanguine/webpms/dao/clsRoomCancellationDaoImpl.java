package com.sanguine.webpms.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webpms.model.clsReservationHdModel;
import com.sanguine.webpms.model.clsReservationModel_ID;
import com.sanguine.webpms.model.clsRoomCancellationModel;
import com.sanguine.webpms.model.clsRoomCancellationModel_ID;

@Repository("clsRoomCancellationDao")
public class clsRoomCancellationDaoImpl implements clsRoomCancellationDao {

	@Autowired
	private SessionFactory webPMSSessionFactory;

	@Override
	@Transactional(value = "WebPMSTransactionManager")
	public void funAddUpdateRoomCancellationReservationTable(clsReservationHdModel objMaster) {
		webPMSSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	@Transactional(value = "WebPMSTransactionManager")
	public clsRoomCancellationModel funGetRoomCancellation(String docCode, String clientCode) {
		return (clsRoomCancellationModel) webPMSSessionFactory.getCurrentSession().get(clsRoomCancellationModel.class, new clsRoomCancellationModel_ID(docCode, clientCode));
	}

	@Override
	@Transactional(value = "WebPMSTransactionManager")
	public clsReservationHdModel funGetReservationModel(String reservationNo, String clientCode) {
		clsReservationHdModel objModel = null;

		objModel = (clsReservationHdModel) webPMSSessionFactory.getCurrentSession().get(clsReservationHdModel.class, new clsReservationModel_ID(reservationNo, clientCode));

		objModel.getListReservationDtlModel().size();

		return objModel;
	}

	@Override
	@Transactional(value = "WebPMSTransactionManager")
	public void funAddUpdateRoomCancellation(clsRoomCancellationModel objModel) {
		webPMSSessionFactory.getCurrentSession().saveOrUpdate(objModel);
	}
	
	@Override
	@Transactional(value = "WebPMSTransactionManager")
	public void funUpdateRoomStatus(String sqlQuery) 
	{
		webPMSSessionFactory.getCurrentSession().createSQLQuery(sqlQuery).executeUpdate();
	}
}
