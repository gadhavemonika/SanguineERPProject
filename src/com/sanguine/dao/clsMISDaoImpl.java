package com.sanguine.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.model.clsMISDtlModel;
import com.sanguine.model.clsMISHdModel;
import com.sanguine.model.clsMISHdModel_ID;

@Repository("clsMISDao")
public class clsMISDaoImpl implements clsMISDao {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("finally")
	@Override
	public long funGetLastNo(String tableName, String masterName, String columnName) {

		long lastNo = 0;
		try {
			@SuppressWarnings("rawtypes")
			List listLastNo = sessionFactory.getCurrentSession().createSQLQuery("select max(" + columnName + ") from " + tableName).list();
			if (listLastNo.size() > 1) {
				lastNo = ((BigInteger) listLastNo.get(0)).longValue();
			}
			lastNo++;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return lastNo;
		}
	}

	@Override
	public void funAddMISHd(clsMISHdModel MISHd) {

		sessionFactory.getCurrentSession().saveOrUpdate(MISHd);
	}

	@Override
	public void funAddUpdateMISDtl(clsMISDtlModel MISDtl) {

		sessionFactory.getCurrentSession().saveOrUpdate(MISDtl);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<clsMISHdModel> funGetList() {

		return (List<clsMISHdModel>) sessionFactory.getCurrentSession().createCriteria(clsMISHdModel.class).list();
	}

	@Override
	public clsMISHdModel funGetObject(String code, String clientCode) {

		clsMISHdModel mis = (clsMISHdModel) sessionFactory.getCurrentSession().get(clsMISHdModel.class, new clsMISHdModel_ID(code, clientCode));
		/*
		 * Query query=sessionFactory.getCurrentSession().createQuery(
		 * "from clsMISHdModel where strMISCode=:MIScode and strClientCode=:clientCode"
		 * ); query.setParameter("MIScode", code);
		 * query.setParameter("clientCode", clientCode); List list=query.list();
		 */
		return mis;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetDtlList(String MISCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("select a.strMISCode,a.strProdCode,b.strProdName,a.strReqCode," + " a.dblQty,a.dblUnitPrice,a.dblTotalPrice,b.strIssueUOM,a.strRemarks from clsMISDtlModel a,clsProductMasterModel b " + " where a.strProdCode=b.strProdCode and a.strMISCode = :misCode and a.strClientCode= :clientCode and b.strClientCode= :clientCode");
		query.setParameter("misCode", MISCode);
		query.setParameter("clientCode", clientCode);

		List list = query.list();
		return list;
	}

	@Override
	public void funDeleteDtl(String MISCode, String strClientCode) {

		Query query = sessionFactory.getCurrentSession().createQuery("delete clsMISDtlModel where strMISCode = :MISCode and strClientCode= :clientCode");
		query.setParameter("MISCode", MISCode);
		query.setParameter("clientCode", strClientCode);
		int result = query.executeUpdate();
		System.out.println("Result=" + result);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funMISforMRDetails(String strLocFrom, String strLocTo, String strClientCode) {

		String sqlQuery = "select a.strReqCode,DATE_FORMAT(a.dtReqDate,'%m-%d-%Y'),b.strLocName as Locationby,c.strLocName as LocationOn,a.strNarration,a.strAuthorise,ifnull(DATE_FORMAT(dtReqiredDate,'%m-%d-%Y'),'') as dtReqiredDate,ifNull(s.strSessionName,'') " + " from tblreqhd a" + " left outer join tbllocationmaster b on  b.strLocCode=a.strLocBy and b.strClientCode='" + strClientCode + "' "
				+ " left outer join tbllocationmaster c on  c.strLocCode=a.strLocOn and c.strClientCode='" + strClientCode + "' " + " left outer join tblsessionmaster s on a.strSessionCode=s.strSessionCode " + " Where a.strClientCode='" + strClientCode + "' and a.strReqCode IN" + " (select a.strReqCode  from tblreqdtl a left outer join (select b.strReqCode, b.strProdCode, SUM(dblQty) ReqQty "
				+ " from tblmishd a, tblmisdtl b Where a.strMISCode = b.strMISCode and a.strClientCode='" + strClientCode + "'" + " and b.strClientCode='" + strClientCode + "' group by b.strReqCode, b.strProdCode) b " + " on a.strReqCode  = b.strReqCode and a.strProdCode = b.strProdCode " + " where  a.dblQty > ifnull(b.ReqQty,0)) " + " and a.strLocby='" + strLocTo + "' and a.strLocon='"
				+ strLocFrom + "' and a.strClientCode='" + strClientCode + "' and a.strAuthorise='Yes' and a.strCloseReq='N' order by a.dtReqDate";
		System.out.println(sqlQuery);
		List list = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> funGetProductDtl(String strProdCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("select strProdName,strIssueUOM,strExpDate " + "from clsProductMasterModel where strProdCode=:strProdCode and strClientCode=:clientCode");
		query.setParameter("strProdCode", strProdCode);
		query.setParameter("clientCode", clientCode);
		@SuppressWarnings("rawtypes")
		List list = query.list();
		return list;
	}

	@Override
	public int funInsertNonStkItemDirect(String sql) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		int result = query.executeUpdate();
		return result;
	}

}
