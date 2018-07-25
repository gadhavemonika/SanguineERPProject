package com.sanguine.webclub.dao;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.excise.model.clsExciseLocationMasterModel;
import com.sanguine.webclub.model.clsWebClubAreaMasterModel;
import com.sanguine.webclub.model.clsWebClubAreaMasterModel_ID;

@Repository("clsWebClubAreaMasterDao")
public class clsWebClubAreaMasterDaoImpl implements clsWebClubAreaMasterDao {

	@Autowired
	private SessionFactory WebClubSessionFactory;

	@Override
	public void funAddUpdateWebClubAreaMaster(clsWebClubAreaMasterModel objMaster) {
		WebClubSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsWebClubAreaMasterModel funGetWebClubAreaMaster(String docCode, String clientCode) {
		return (clsWebClubAreaMasterModel) WebClubSessionFactory.getCurrentSession().get(clsWebClubAreaMasterModel.class, new clsWebClubAreaMasterModel_ID(docCode, clientCode));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List funGetWebClubAllAreaData(String tableName, String clientCode) {
		List ls = new LinkedList();
		try {
			// Query query =
			// WebClubSessionFactory.getCurrentSession().createQuery("from clsWebClubAreaMasterModel where strClientCode=:clientCode ");
			// query.setParameter("clientCode", clientCode);

			Query query = null;

			switch (tableName) {
			case "Area": {
				query = WebClubSessionFactory.getCurrentSession().createSQLQuery("select a.strAreaCode,a.strAreaName,a.strCityCode,b.strCityName from tblareamaster a, tblcitymaster b  where a.strClientCode='" + clientCode + "' and a.strCityCode=b.strCityCode ");
				break;
			}
			case "City": {
				query = WebClubSessionFactory.getCurrentSession().createSQLQuery("select strCityCode,strCityName from tblcitymaster  where strClientCode='" + clientCode + "'  ");
				break;
			}
			case "Region": {
				query = WebClubSessionFactory.getCurrentSession().createSQLQuery("select strRegionCode,strRegionCodeName from tblregionmaster  where strClientCode='" + clientCode + "'  ");
				break;
			}
			case "State": {
				query = WebClubSessionFactory.getCurrentSession().createSQLQuery("select strStateCode,strStateName from tblstatemaster  where strClientCode='" + clientCode + "'  ");
				break;
			}
			case "Country": {
				query = WebClubSessionFactory.getCurrentSession().createSQLQuery("select strCountryCode,strCountryName from tblcountrymaster  where strClientCode='" + clientCode + "'  ");
				break;
			}
			case "Education": {
				query = WebClubSessionFactory.getCurrentSession().createSQLQuery("select strEducationCode,strEducationDesc from tbleducationmaster  where strClientCode='" + clientCode + "'  ");
				break;
			}
			case "MaritalStatus": {
				query = WebClubSessionFactory.getCurrentSession().createSQLQuery("select strMaritalCode,strMaritalName from tblmaritalstatusmaster  where strClientCode='" + clientCode + "'  ");
				break;
			}

			case "Profession": {
				query = WebClubSessionFactory.getCurrentSession().createSQLQuery("select strProfessionCode,strProfessionName from tblprofessionmaster  where strClientCode='" + clientCode + "'  ");
				break;
			}

			case "Reason": {
				query = WebClubSessionFactory.getCurrentSession().createSQLQuery("select strReasonCode,strReasonDesc from tblreasonmaster  where strClientCode='" + clientCode + "'  ");
				break;
			}
			case "Designation": {
				query = WebClubSessionFactory.getCurrentSession().createSQLQuery("select strDesignationCode,strDesignationName from tbldesignationmaster  where strClientCode='" + clientCode + "'  ");
				break;
			}
			}

			ls = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ls;
	}

}
