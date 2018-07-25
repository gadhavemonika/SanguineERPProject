package com.sanguine.webpms.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpms.model.clsBillHdModel;
import com.sanguine.webpms.model.clsFolioHdModel;

@Repository("clsCheckOutDao")
public class clsCheckOutDaoImpl implements clsCheckOutDao {

	@Autowired
	private SessionFactory webPMSSessionFactory;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Override
	@Transactional(value = "WebPMSTransactionManager", rollbackFor = { Exception.class })
	public void funSaveCheckOut(String roomNo, String clientCode, String userCode) {
		/*
		 * try { clsGlobalFunctions objGlobal = new clsGlobalFunctions(); long
		 * nextBillNo = 0; // generate nextBillNo No. nextBillNo =
		 * objGlobalFunctionsService.funGetNextNo("tblbillhd", "CheckOut",
		 * "strBillNo", clientCode); String billNo = "BN" +
		 * String.format("%06d", nextBillNo);
		 * 
		 * String
		 * sqlSelectFolioHd="select '"+billNo+"','"+objGlobal.funGetCurrentDateTime
		 * ("yyyy-MM-dd")+
		 * "',a.strFolioNo,a.strRegistrationNo,a.strReservationNo,'0.00','"
		 * +userCode+"','"+userCode+"' "
		 * +",'"+objGlobal.funGetCurrentDateTime("yyyy-MM-dd"
		 * )+"','"+objGlobal.funGetCurrentDateTime
		 * ("yyyy-MM-dd")+"','"+clientCode+"' " +"from tblfoliohd a "
		 * +"where a.strRoomNo='"
		 * +roomNo+"' and a.strClientCode='"+clientCode+"' ";
		 * 
		 * String
		 * sqlInsertIntoBillHd="insert into tblbillhd ("+sqlSelectFolioHd+")";
		 * 
		 * Query
		 * insertQuery=webPMSSessionFactory.getCurrentSession().createSQLQuery
		 * (sqlInsertIntoBillHd); insertQuery.executeUpdate();
		 * 
		 * Query
		 * deleteQuery=webPMSSessionFactory.getCurrentSession().createSQLQuery
		 * ("delete from tblfoliohd where strRoomNo='"
		 * +roomNo+"' and strClientCode='"+clientCode+"' ");
		 * deleteQuery.executeUpdate();
		 * 
		 * } catch(Exception e) { e.printStackTrace(); }
		 */
	}

	@Override
	@Transactional(value = "WebPMSTransactionManager", rollbackFor = { Exception.class })
	public clsFolioHdModel funGetFolioHdModel(String roomNo, String registrationNo, String reservationNo, String clientCode) {
		Criteria criteria = webPMSSessionFactory.getCurrentSession().createCriteria(clsFolioHdModel.class);
		criteria.add(Restrictions.eq("strRoomNo", roomNo));
		criteria.add(Restrictions.eq("strRegistrationNo", registrationNo));
		criteria.add(Restrictions.eq("strClientCode", clientCode));
		List list = criteria.list();

		clsFolioHdModel objModel = null;
		if (list.size() > 0) {
			objModel = (clsFolioHdModel) list.get(0);
			objModel.getListFolioDtlModel().size();
			objModel.getListFolioTaxDtlModel().size();
		}
		return objModel;
	}

	@Override
	@Transactional(value = "WebPMSTransactionManager", rollbackFor = { Exception.class })
	public void funSaveCheckOut(clsFolioHdModel objFolioHdModel, clsBillHdModel objBillHdModel) {
		webPMSSessionFactory.getCurrentSession().delete(objFolioHdModel);
		webPMSSessionFactory.getCurrentSession().saveOrUpdate(objBillHdModel);

		String sql = "update tblroom set strStatus='Free' " + " where strRoomCode='" + objBillHdModel.getStrRoomNo() + "' and strClientCode='" + objBillHdModel.getStrClientCode() + "'";
		webPMSSessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
	}

	@Override
	@Transactional(value = "WebPMSTransactionManager", rollbackFor = { Exception.class })
	public List funGetCheckOut(String roomNo, String billNo, String clientCode) {
		Criteria criteria = webPMSSessionFactory.getCurrentSession().createCriteria(clsBillHdModel.class);
		criteria.add(Restrictions.eq("strRoomNo", roomNo));
		criteria.add(Restrictions.eq("strBillNo", billNo));
		criteria.add(Restrictions.eq("strClientCode", clientCode));
		List list = criteria.list();

		return list;
	}

}
