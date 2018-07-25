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

import com.sanguine.webpms.model.clsRoomPackageDtl;
import com.sanguine.webpms.model.clsWalkinDtl;
import com.sanguine.webpms.model.clsWalkinHdModel;

@Repository("clsWalkinDao")
public class clsWalkinDaoImpl implements clsWalkinDao {

	@Autowired
	private SessionFactory webPMSSessionFactory;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdateWalkinHd(clsWalkinHdModel objHdModel) {
		webPMSSessionFactory.getCurrentSession().saveOrUpdate(objHdModel);
		
//		if(!(objHdModel.getStrRoomNo().isEmpty())) {
//			String sql = "update tblroom set strStatus='Confirmed' where strRoomCode='" + objHdModel.getStrRoomNo() + "' and strClientCode='" + objHdModel.getStrClientCode() + "' ";
//			webPMSSessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
//		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdateWalkinDtl(clsWalkinDtl objDtlModel) {
		webPMSSessionFactory.getCurrentSession().saveOrUpdate(objDtlModel);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetWalkinDataDtl(String walkinNo, String clientCode) {
		List list = null;
		try {
			Criteria cr = webPMSSessionFactory.getCurrentSession().createCriteria(clsWalkinHdModel.class);
			cr.add(Restrictions.eq("strWalkinNo", walkinNo));
			cr.add(Restrictions.eq("strClientCode", clientCode));
			list = cr.list();

			if (list.size() > 0) {
				clsWalkinHdModel objWalkinHdModel = (clsWalkinHdModel) list.get(0);
				objWalkinHdModel.getListWalkinDtlModel().size();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	@Override
	@Transactional(value = "WebPMSTransactionManager")
	public List<clsRoomPackageDtl> funGetWalkinIncomeList(String walkInNo, String clientCode) 
	{
		List<clsRoomPackageDtl> listPMSPkg=new ArrayList<>();
		List list=webPMSSessionFactory.getCurrentSession().createSQLQuery("SELECT a.strWalkinNo,a.strPackageCode,b.strPackageName,a.strIncomeHeadCode,a.dblIncomeHeadAmt,d.strIncomeHeadDesc "
				+ " FROM tblroompackagedtl a,tblpackagemasterhd b,tblpackagemasterdtl c,tblincomehead d "
				+ " where a.strPackageCode=b.strPackageCode and b.strPackageCode=c.strPackageCode and a.strIncomeHeadCode=d.strIncomeHeadCode "
				+ " and a.strWalkinNo='"+walkInNo+"' and a.strClientCode='"+clientCode+"' "
				+ " group by a.strIncomeHeadCode  ").list();
		
		if(list.size()>0)
		{
			for (int cnt = 0; cnt < list.size(); cnt++) 
			{
				Object[] arrObj = (Object[]) list.get(cnt);
				clsRoomPackageDtl objPkg=new clsRoomPackageDtl();
				objPkg.setStrWalkinNo(arrObj[0].toString());
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
