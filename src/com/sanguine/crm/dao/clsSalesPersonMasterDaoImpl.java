package com.sanguine.crm.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.crm.model.clsSalesPersonMasterModel;
import com.sanguine.crm.model.clsSalesPersonMasterModel_ID;
import com.sanguine.model.clsCurrencyMasterModel;

@Repository("clsSalesPersonMasterDao")
public class clsSalesPersonMasterDaoImpl implements clsSalesPersonMasterDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void funAddUpdateclsSalesPersonMaster(clsSalesPersonMasterModel objMaster) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public List funGetclsSalesPersonMaster(String docCode, String clientCode) {
		// TODO Auto-generated method stub
		List<clsSalesPersonMasterModel> objDCList = new ArrayList();
		//funGet(docCode,clientCode);
		String sql="select strSalesPersonCode,strSalesPersonName,intID,strClientCode from tblsalesperson where strSalesPersonCode='"+docCode+"' and strClientCode='"+clientCode+"'";
		List list=sessionFactory.getCurrentSession().createSQLQuery(sql).list();
		clsSalesPersonMasterModel obModel=new clsSalesPersonMasterModel();
		if(list.size()>0){
			Object ob[]=(Object[])list.get(0);
			obModel.setStrClientCode(ob[3].toString());
			obModel.setStrSalesPersonCode(ob[0].toString());
			obModel.setStrSalesPersonName(ob[1].toString());
			obModel.setIntID(Long.parseLong(ob[2].toString()));
			objDCList.add(obModel);
		}
		
		
		//clsSalesPersonMasterModel objMaster=(clsSalesPersonMasterModel) sessionFactory.getCurrentSession().get(clsSalesPersonMasterModel.class, new clsSalesPersonMasterModel_ID(docCode, clientCode));
	/*	
	Query query = sessionFactory.getCurrentSession().createQuery(" from clsSalesPersonMasterModel a where a.strSalesPersonCode=:spCode and a.strClientCode=:clientCode ");
		query.setParameter("spCode", docCode);
		query.setParameter("clientCode", clientCode);
		List<clsSalesPersonMasterModel> list = query.list();

		if (list.size() > 0) {
			objDCList = list;

		}*/
		return objDCList;
		// 
	}
	private clsSalesPersonMasterModel funGet(String docCode, String clientCode){
		clsSalesPersonMasterModel ob=(clsSalesPersonMasterModel) sessionFactory.getCurrentSession().get(clsSalesPersonMasterModel.class,new clsSalesPersonMasterModel_ID(docCode,clientCode));
		return ob;
	}
	
	@Override
	public Map<String, String> funCurrencyListToDisplay(String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("from clsSalesPersonMasterModel WHERE strClientCode='" + clientCode + "'");
		List<clsSalesPersonMasterModel> list = query.list();

		Map<String, String> hmSalesPerson= new HashMap<String, String>();
		for (clsSalesPersonMasterModel objSalesModel : list) {
			hmSalesPerson.put(objSalesModel.getStrSalesPersonCode(), objSalesModel.getStrSalesPersonName());
		}

		return hmSalesPerson;
	}
}
