package com.sanguine.webpms.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webpms.model.clsRoomMasterModel;
import com.sanguine.webpms.model.clsRoomMasterModel_ID;

@Repository("clsRoomMasterDao")
public class clsRoomMasterDaoImpl implements clsRoomMasterDao {

	@Autowired
	private SessionFactory webPMSSessionFactory;

	@Override
	@Transactional(value = "WebPMSTransactionManager")
	public void funAddUpdateRoomMaster(clsRoomMasterModel objMaster) {
		webPMSSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	@Transactional(value = "WebPMSTransactionManager")
	public clsRoomMasterModel funGetRoomMaster(String roomCode, String clientCode) 
	{   List<String> list = null;
		clsRoomMasterModel objModel=(clsRoomMasterModel)webPMSSessionFactory.getCurrentSession().get(clsRoomMasterModel.class, new clsRoomMasterModel_ID(roomCode, clientCode));
		if(objModel!=null)
		{
			list = webPMSSessionFactory.getCurrentSession().createSQLQuery("select strRoomTypeDesc from tblroomtypemaster where strclientCode='" + clientCode + "' and  strRoomTypeCode='"+objModel.getStrRoomTypeCode()+"' ").list();	
			objModel.setStrRoomTypeDesc((String)list.get(0));
		}
		return objModel;
	}

	@Override
	public List<String> funGetRoomTypeList(String clientCode) {
		List<String> list = null;
		list = webPMSSessionFactory.getCurrentSession().createSQLQuery("select strRoomTypeDesc from tblroomtypemaster where strclientCode='" + clientCode + "' ").list();
		return list;
	}

}
