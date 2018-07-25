package com.sanguine.webpms.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import com.sanguine.webpms.model.clsReservationDtlModel;
import com.sanguine.webpms.model.clsReservationHdModel;
import com.sanguine.webpms.model.clsReservationRoomRateModelDtl;
import com.sanguine.webpms.model.clsRoomPackageDtl;

@Repository("clsReservationDao")
public class clsReservationDaoImpl implements clsReservationDao {

	@Autowired
	private SessionFactory webPMSSessionFactory;

	@Override
	@Transactional(value = "WebPMSTransactionManager")
	public void funAddUpdateReservationHd(clsReservationHdModel objHdModel, String bookingType) {

		webPMSSessionFactory.getCurrentSession().saveOrUpdate(objHdModel);

//		for (clsReservationDtlModel objResDtlModel : objHdModel.getListReservationDtlModel()) {
//			String sql = "update tblroom set strStatus='" + bookingType + "' " + " where strRoomCode='" + objResDtlModel.getStrRoomNo() + "' and strClientCode='" + objHdModel.getStrClientCode() + "'";
//			webPMSSessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
//		}
	}

	@Override
	@Transactional(value = "WebPMSTransactionManager")
	public clsReservationHdModel funGetReservationList(String reservationNo, String clientCode, String propertyCode) {
		Criteria cr = webPMSSessionFactory.getCurrentSession().createCriteria(clsReservationHdModel.class);
		cr.add(Restrictions.eq("strReservationNo", reservationNo));
		cr.add(Restrictions.eq("strClientCode", clientCode));
		cr.add(Restrictions.eq("strPropertyCode", propertyCode));
		List list = cr.list();

		clsReservationHdModel objModel = null;
		if (list.size() > 0) {
			objModel = (clsReservationHdModel) list.get(0);
			objModel.getListReservationDtlModel().size();
		}

		return objModel;
	}
	
	
	@Override
	@Transactional(value = "WebPMSTransactionManager")
	public List<clsReservationRoomRateModelDtl> funGetReservationRoomRateList(String reservationNo, String clientCode, String RoomNo) {
		
		
		
		List<clsReservationRoomRateModelDtl> listPMSRoomRates= webPMSSessionFactory.getCurrentSession().createSQLQuery("select * from clsReservationRoomRateModelDtl a where a.strReservationNo='"+reservationNo+"' and a.strClientCode='"+clientCode+"' and a.strRoomNo='"+RoomNo+"' ").list();
		
//		cr.add(Restrictions.eq("strReservationNo", reservationNo));
//		cr.add(Restrictions.eq("strClientCode", clientCode));
//		cr.add(Restrictions.eq("strRoomNo", RoomNo));
//		List list = cr.list();
//
//		clsReservationRoomRateModelDtl objModel = null;
//		if (list.size() > 0) {
//			objModel = (clsReservationRoomRateModelDtl) list.get(0);
//			
//		}

		return listPMSRoomRates;
	}

	
	@Override
	@Transactional(value = "WebPMSTransactionManager")
	public List<clsRoomPackageDtl> funGetReservationIncomeList(String reservationNo, String clientCode) 
	{
		List<clsRoomPackageDtl> listPMSPkg=new ArrayList<>();
		List list=webPMSSessionFactory.getCurrentSession().createSQLQuery("SELECT a.strReservationNo,a.strPackageCode,b.strPackageName,a.strIncomeHeadCode,a.dblIncomeHeadAmt,d.strIncomeHeadDesc "
				+ " FROM tblroompackagedtl a,tblpackagemasterhd b,tblpackagemasterdtl c,tblincomehead d "
				+ " where a.strPackageCode=b.strPackageCode and b.strPackageCode=c.strPackageCode and a.strIncomeHeadCode=d.strIncomeHeadCode "
				+ " and a.strReservationNo='"+reservationNo+"' and a.strClientCode='"+clientCode+"' "
				+ " group by a.strIncomeHeadCode  ").list();
		
		if(list.size()>0)
		{
			for (int cnt = 0; cnt < list.size(); cnt++) 
			{
				Object[] arrObj = (Object[]) list.get(cnt);
				clsRoomPackageDtl objPkg=new clsRoomPackageDtl();
				objPkg.setStrReservationNo(arrObj[0].toString());
				objPkg.setStrPackageCode(arrObj[1].toString());
				objPkg.setStrPackageName(arrObj[2].toString());
				objPkg.setStrIncomeHeadCode(arrObj[3].toString());
				objPkg.setDblIncomeHeadAmt(Double.valueOf(arrObj[4].toString()));
				objPkg.setStrIncomeHeadName(arrObj[5].toString());
				listPMSPkg.add(objPkg);
			}
		}	
		
		return listPMSPkg;
	}
	
	
}
