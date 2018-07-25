package com.sanguine.webpms.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webpms.model.clsBillDtlModel;
import com.sanguine.webpms.model.clsCheckInDtl;
import com.sanguine.webpms.model.clsCheckInHdModel;
import com.sanguine.webpms.model.clsReservationDtlModel;
import com.sanguine.webpms.model.clsReservationHdModel;
import com.sanguine.webpms.model.clsRoomPackageDtl;

@Repository("clsCheckInDao")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebPMSTransactionManager")
public class clsCheckInDaoImpl implements clsCheckInDao {

	@Autowired
	private SessionFactory webPMSSessionFactory;

	@Override
	public void funAddUpdateCheckInHd(clsCheckInHdModel objHdModel) {
		webPMSSessionFactory.getCurrentSession().saveOrUpdate(objHdModel);

		for (clsCheckInDtl objCheckInDtlModel : objHdModel.getListCheckInDtl()) {
			String sql = "update tblroom set strStatus='Occupied' " + " where strRoomCode='" + objCheckInDtlModel.getStrRoomNo() + "' and strClientCode='" + objHdModel.getStrClientCode() + "'";
			webPMSSessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
		}

	}

	@Override
	public clsCheckInHdModel funGetCheckInData(String checkInNo, String clientCode) {
		Criteria cr = webPMSSessionFactory.getCurrentSession().createCriteria(clsCheckInHdModel.class);
		cr.add(Restrictions.eq("strCheckInNo", checkInNo));
		cr.add(Restrictions.eq("strClientCode", clientCode));
		List list = cr.list();

		clsCheckInHdModel objModel = null;
		if (list.size() > 0) {
			objModel = (clsCheckInHdModel) list.get(0);
			objModel.getListCheckInDtl().size();
		}

		return objModel;
	}
	
	
	@Override
	@Transactional(value = "WebPMSTransactionManager")
	public List<clsRoomPackageDtl> funGetCheckInIncomeList(String checkInNo, String clientCode) 
	{
		List<clsRoomPackageDtl> listPMSPkg=new ArrayList<>();
		List list=webPMSSessionFactory.getCurrentSession().createSQLQuery("SELECT a.strWalkinNo,a.strReservationNo,a.strCheckInNo,a.strPackageCode,b.strPackageName,a.strIncomeHeadCode,a.dblIncomeHeadAmt,d.strIncomeHeadDesc "
				+ " FROM tblroompackagedtl a,tblpackagemasterhd b,tblpackagemasterdtl c,tblincomehead d "
				+ " where a.strPackageCode=b.strPackageCode and b.strPackageCode=c.strPackageCode and a.strIncomeHeadCode=d.strIncomeHeadCode "
				+ " and a.strCheckInNo='"+checkInNo+"' and a.strClientCode='"+clientCode+"' "
				+ " group by a.strIncomeHeadCode  ").list();
		
		if(list.size()>0)
		{
			for (int cnt = 0; cnt < list.size(); cnt++) 
			{
				Object[] arrObj = (Object[]) list.get(cnt);
				clsRoomPackageDtl objPkg=new clsRoomPackageDtl();
				objPkg.setStrWalkinNo(arrObj[0].toString());
				objPkg.setStrReservationNo(arrObj[1].toString());
				objPkg.setStrCheckInNo(arrObj[2].toString());
				objPkg.setStrPackageCode(arrObj[3].toString());
				objPkg.setStrPackageName(arrObj[4].toString());
				objPkg.setStrIncomeHeadCode(arrObj[5].toString());
				objPkg.setDblIncomeHeadAmt(Double.valueOf(arrObj[6].toString()));
				objPkg.setStrIncomeHeadName(arrObj[7].toString());
				listPMSPkg.add(objPkg);
			}
		}	
		
		return listPMSPkg;
	}

}
