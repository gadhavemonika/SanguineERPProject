package com.sanguine.webclub.dao;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("clsWebClubGeneralMasterDao")
public class clsWebClubGeneralMasterDaoImpl implements clsWebClubGeneralMasterDao {

	@Autowired
	private SessionFactory WebClubSessionFactory;

	@Override
	public List funGetWebClubAllPaticulorMasterData(String tableName, String clientCode) {
		List ls = new LinkedList();
		try {
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
				query = WebClubSessionFactory.getCurrentSession().createSQLQuery("select strRegionCode,strRegionName from tblregionmaster  where strClientCode='" + clientCode + "'  ");
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
			case "Marital": {
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
			case "CommitteeMemberRole": {
				query = WebClubSessionFactory.getCurrentSession().createSQLQuery("select strRoleCode,strRoleDesc,intRoleRank from tblcommitteememberrolemaster  where strClientCode='" + clientCode + "'  ");
				break;
			}

			case "Relation": {
				query = WebClubSessionFactory.getCurrentSession().createSQLQuery("select strRelationCode,strRelation from tblrelationmaster  where strClientCode='" + clientCode + "'  ");
				break;
			}

			case "Staff": {
				query = WebClubSessionFactory.getCurrentSession().createSQLQuery("select strStaffCode,strStaffName from tblstaffmaster  where strClientCode='" + clientCode + "'  ");
				break;
			}

			case "CurrencyDetails": {
				query = WebClubSessionFactory.getCurrentSession().createSQLQuery("select strCurrCode,strDesc from tblcurrencydetails  where strClientCode='" + clientCode + "'  ");
				break;
			}
			case "InvitedBy": {
				query = WebClubSessionFactory.getCurrentSession().createSQLQuery("select strInvCode,strInvName from tblinvitedby  where strClientCode='" + clientCode + "'  ");
				break;
			}

			case "ItemCategory": {
				query = WebClubSessionFactory.getCurrentSession().createSQLQuery("select strItemCategoryCode,strItemCategoryName from tblitemcategory  where strClientCode='" + clientCode + "'  ");
				break;
			}

			case "Profile": {
				query = WebClubSessionFactory.getCurrentSession().createSQLQuery("select strProfileCode,strProfileDesc from tblprofilesource  where strClientCode='" + clientCode + "'  ");
				break;
			}

			case "Salutation": {
				query = WebClubSessionFactory.getCurrentSession().createSQLQuery("select strSalutationCode,strSalutationDesc from tblsalutationmaster  where strClientCode='" + clientCode + "'  ");
				break;
			}

			case "Title": {
				query = WebClubSessionFactory.getCurrentSession().createSQLQuery("select strTitleCode,strTitleDesc from tbltitlemaster  where strClientCode='" + clientCode + "'  ");
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
