package com.sanguine.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.model.clsMRPIDtl;
import com.sanguine.model.clsProductStandardModel;
import com.sanguine.model.clsRequisitionDtlModel;
import com.sanguine.model.clsRequisitionHdModel;
import com.sanguine.model.clsRequisitionHdModel_ID;

@Repository("clsRequisitionDao")
public class clsRequisitionDaoImpl implements clsRequisitionDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void funAddRequisionHd(clsRequisitionHdModel reqhd) {
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(reqhd);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void funAddUpdateRequisionDtl(clsRequisitionDtlModel reqdtl) {

		sessionFactory.getCurrentSession().saveOrUpdate(reqdtl);
	}

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

	@SuppressWarnings("unchecked")
	@Override
	public List<clsRequisitionHdModel> funGetList() {

		return (List<clsRequisitionHdModel>) sessionFactory.getCurrentSession().createCriteria(clsRequisitionHdModel.class).list();
	}

	@Override
	public clsRequisitionHdModel funGetObject(String ReqCode, String clientCode) {

		clsRequisitionHdModel reqObj = (clsRequisitionHdModel) sessionFactory.getCurrentSession().get(clsRequisitionHdModel.class, new clsRequisitionHdModel_ID(ReqCode, clientCode));
		/*
		 * Query query = sessionFactory.getCurrentSession().createQuery(
		 * "from clsRequisitionHdModel a," +
		 * "clsLocationMasterModel b,clsLocationMasterModel c " +
		 * "where a.strReqCode= :ReqCode and a.strLocBy=b.strLocCode and a.strLocOn=c.strLocCode "
		 * + "and a.strClientCode= :clientCode"); query.setParameter("ReqCode",
		 * ReqCode); query.setParameter("clientCode", clientCode);
		 */
		return reqObj;
	}

	@Override
	public List funGetProductList(String sql) {

		return sessionFactory.getCurrentSession().createSQLQuery(sql).list();
	}

	@Override
	public void funDeleteDtl(String strReqCode) {

		Query query = sessionFactory.getCurrentSession().createQuery("delete clsRequisitionDtlModel where strReqCode = :strReqCode");
		query.setParameter("strReqCode", strReqCode);
		int result = query.executeUpdate();
		// System.out.println("Result="+result);
	}

	@Override
	public clsRequisitionHdModel funGetReqHdData(String reqCode) {

		return (clsRequisitionHdModel) sessionFactory.getCurrentSession().get(clsRequisitionHdModel.class, reqCode);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List funGetDtlList(String ReqCode, String clientCode) {

		Query query = sessionFactory.getCurrentSession().createQuery("from clsRequisitionDtlModel a,clsProductMasterModel b where a.strProdCode=b.strProdCode and a.strReqCode = :ReqCode and a.strClientCode= :clientCode");
		query.setParameter("ReqCode", ReqCode);
		query.setParameter("clientCode", clientCode);
		List list = query.list();
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetReqDtlList(String reqCode, String clientCode, String locCode, String userCode) {

		// String
		// oldQuery=("SELECT r.strProdCode, p.strPartno as strPartno,p.strProdName,"
		// +
		// " r.dblqty-IFNULL(mis.dblQty, 0) as dblIssueQty , r.dblqty as reqQty,p.dblCostRM,"
		// +
		// " r.strRemarks as strRemarks,p.strIssueUOM as IssueUOM,s.dblClosingStk as dblStock FROM tblreqdtl r "
		// +
		// " LEFT OUTER JOIN (select  strProdcode,sum(IFNULL(dblQty, 0)) as dblQty,strReqCode from "
		// +
		// " tblmisdtl  where strClientCode='"+clientCode+"' and strReqCode='"+reqCode+"'  group by strProdcode)"
		// +
		// " mis on r.strreqcode=mis.strreqcode and mis.strprodcode=r.strprodcode"
		// +
		// " inner join tblproductmaster p ON r.strProdCode = p.strProdCode and strNonStockableItem='N' and p.strClientCode='"+clientCode+"' "
		// +"  left outer join tblcurrentstock s on s.strProdCode=r.strProdCode "
		// +
		// " and s.strLocCode='"+locCode+"' and s.strUserCode='"+userCode+"' and   s.strClientCode='"+clientCode+"' "
		// +
		// " WHERE (r.dblqty-IFNULL(mis.dblQty,0))>0 AND r.strReqCode='"+reqCode+"' and r.strClientCode='"+clientCode+"' group by r.strProdCode ");

		String oldQuery = " SELECT r.strProdCode, p.strPartno as strPartno,p.strProdName, r.dblqty-IFNULL(mis.dblQty, 0) as dblIssueQty , " + " r.dblqty as reqQty,p.dblCostRM, r.strRemarks as strRemarks,p.strIssueUOM as IssueUOM " + " FROM tblreqdtl r  LEFT OUTER JOIN (select  strProdcode,sum(IFNULL(dblQty, 0)) as dblQty,strReqCode " + "	from  tblmisdtl  " + "	where strClientCode='" + clientCode
				+ "' and strReqCode='" + reqCode + "'  group by strProdcode) mis on r.strreqcode=mis.strreqcode " + "	and mis.strprodcode=r.strprodcode " + "	inner join tblproductmaster p ON r.strProdCode = p.strProdCode and strNonStockableItem='N' " + "	and p.strClientCode='" + clientCode + "'   " + "	WHERE (r.dblqty-IFNULL(mis.dblQty,0))>0 " + "	AND r.strReqCode='" + reqCode
				+ "' and r.strClientCode='" + clientCode + "'   ";

		System.out.println(oldQuery);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(oldQuery);

		List list = query.list();
		return list;
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public List funGenerateAutoReq(String strLocCode, String clientCode, String userCode, String strGCode, String strSGCode, String strSuppCode) {
		Query query = null;
		String sqlQuery = "";
		if (strLocCode.trim().length() > 0 && strSGCode.equals("ALL") && strGCode.equalsIgnoreCase("ALL") && strSuppCode.trim().length() == 0) {
			sqlQuery = "select a.strProdCode,a.strProdName,a.strPartNo,a.dblCostRM,a.dblWeight,b.dblReOrderLevel," + "b.dblReOrderQty,c.dblClosingStk, ifnull(d.pendingQty,0) as OpenReq ,a.strIssueUOM " + "from tblproductmaster a inner join tblreorderlevel b ON a.strProdCode = b.strProdCode and b.strClientCode='" + clientCode + "' "
					+ "inner join tblcurrentstock c ON b.strProdCode = c.strProdCode and c.strClientCode='" + clientCode + "' and c.strUserCode = '" + userCode + "' " + "left outer join (select a.strProdCode, (a.dblQty - IFNULL(d.dblQty,0)) as pendingQty    " + "from tblreqdtl a left outer join tblreqhd b ON a.strReqCode = b.strReqCode  and b.strClientCode='" + clientCode + "' "
					+ "left outer join tblmisdtl d ON  a.strReqCode=d.strReqCode and a.strProdCode = d.strProdCode and d.strClientCode='" + clientCode + "' " + "Where b.strLocby ='" + strLocCode + "' and a.strClientCode='" + clientCode + "' Group By a.strProdCode) d ON a.strProdCode = d.strProdCode " + "where  b.strLocationCode='" + strLocCode + "' and c.dblClosingStk <= b.dblReOrderLevel "
					+ "and b.dblReOrderLevel > 0 and  a.strClientCode='" + clientCode + "' ";

			System.out.println("SqlQuery\t" + sqlQuery);
			query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery);
		} else if (strSuppCode.trim().length() == 0 && !strGCode.equalsIgnoreCase("ALL") && strSGCode.equalsIgnoreCase("ALL") && strLocCode.trim().length() > 0) {
			sqlQuery = "select a.strProdCode,a.strProdName,a.strPartNo,a.dblCostRM,a.dblWeight,b.dblReOrderLevel," + "b.dblReOrderQty,c.dblClosingStk, ifnull(d.pendingQty,0) as OpenReq ,a.strIssueUOM " + "from tblproductmaster a inner join tblreorderlevel b ON a.strProdCode = b.strProdCode and b.strClientCode='" + clientCode + "'  "
					+ "inner join tblcurrentstock c ON b.strProdCode = c.strProdCode and c.strClientCode='" + clientCode + "' and c.strUserCode = '" + userCode + "' " + "left outer join (select a.strProdCode, (a.dblQty - IFNULL(d.dblQty,0)) as pendingQty    " + "from tblreqdtl a left outer join tblreqhd b ON a.strReqCode = b.strReqCode  and b.strClientCode='" + clientCode + "' "
					+ "left outer join tblmisdtl d ON  a.strReqCode=d.strReqCode and a.strProdCode = d.strProdCode and d.strClientCode='" + clientCode + "' " + "Where b.strLocby ='" + strLocCode + "' and a.strClientCode='" + clientCode + "' Group By a.strProdCode) d ON a.strProdCode = d.strProdCode " + " inner join tblsubgroupmaster e on a.strSGCode=e.strSGCode and e.strClientCode='" + clientCode
					+ "' " + "inner join tblgroupmaster f on e.strGCode=f.strGCode and f.strGCode='" + strGCode + "' and f.strClientCode='" + clientCode + "' " + "where  b.strLocationCode='" + strLocCode + "' and c.dblClosingStk <= b.dblReOrderLevel  and b.dblReOrderLevel > 0  and a.strClientCode='" + clientCode + "' ";

			// System.out.println("SqlQuery\t"+sqlQuery);
			query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery);
		} else if (strSuppCode.trim().length() == 0 && !strGCode.equalsIgnoreCase("ALL") && !strSGCode.equals("ALL") && strLocCode.trim().length() > 0) {
			sqlQuery = "select a.strProdCode,a.strProdName,a.strPartNo,a.dblCostRM,a.dblWeight,b.dblReOrderLevel," + "b.dblReOrderQty,c.dblClosingStk, ifnull(d.pendingQty,0) as OpenReq ,a.strIssueUOM " + "from tblproductmaster a inner join tblreorderlevel b ON a.strProdCode = b.strProdCode and b.strClientCode='" + clientCode + "' "
					+ "inner join tblcurrentstock c ON b.strProdCode = c.strProdCode and c.strClientCode='" + clientCode + "' and c.strUserCode = '" + userCode + "' " + "left outer join (select a.strProdCode, (a.dblQty - IFNULL(d.dblQty,0)) as pendingQty    " + "from tblreqdtl a left outer join tblreqhd b ON a.strReqCode = b.strReqCode  and b.strClientCode='" + clientCode + "' "
					+ "left outer join tblmisdtl d ON  a.strReqCode=d.strReqCode and a.strProdCode = d.strProdCode and d.strClientCode='" + clientCode + "' " + "Where b.strLocby ='" + strLocCode + "' and a.strClientCode='" + clientCode + "' Group By a.strProdCode) d ON a.strProdCode = d.strProdCode " + " inner join tblsubgroupmaster e on a.strSGCode=e.strSGCode and e.strSGCode='" + strSGCode
					+ "'  and e.strClientCode='" + clientCode + "' " + "inner join tblgroupmaster f on e.strGCode=f.strGCode and f.strGCode='" + strGCode + "' and f.strClientCode='" + clientCode + "' " + "where  b.strLocationCode='" + strLocCode + "' and c.dblClosingStk <= b.dblReOrderLevel  and b.dblReOrderLevel > 0  and a.strClientCode='" + clientCode + "' ";

			// System.out.println("SqlQuery\t"+sqlQuery);
			query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery);
		} else if (strSuppCode.trim().length() > 0 && strGCode.equalsIgnoreCase("ALL") && strSGCode.equals("ALL") && strLocCode.trim().length() > 0) {
			sqlQuery = "select a.strProdCode,a.strProdName,a.strPartNo,a.dblCostRM,a.dblWeight,b.dblReOrderLevel," + "b.dblReOrderQty,c.dblClosingStk, ifnull(d.pendingQty,0) as OpenReq ,a.strIssueUOM " + "from tblproductmaster a inner join tblreorderlevel b ON a.strProdCode = b.strProdCode and b.strClientCode='" + clientCode + "'  "
					+ "inner join tblcurrentstock c ON b.strProdCode = c.strProdCode and c.strClientCode='" + clientCode + "' and c.strUserCode ='" + userCode + "' " + "left outer join (select a.strProdCode, (a.dblQty - IFNULL(d.dblQty,0)) as pendingQty    " + "from tblreqdtl a left outer join tblreqhd b ON a.strReqCode = b.strReqCode  and b.strClientCode='" + clientCode + "' "
					+ "left outer join tblmisdtl d ON a.strReqCode=d.strReqCode and a.strProdCode = d.strProdCode and d.strClientCode='" + clientCode + "' " + "Where b.strLocby ='" + strLocCode + "' and a.strClientCode='" + clientCode + "' Group By a.strProdCode) d ON a.strProdCode = d.strProdCode " + "where  b.strLocationCode='" + strLocCode
					+ "' and c.dblClosingStk <= b.dblReOrderLevel and b.dblReOrderLevel > 0 and a.strClientCode='" + clientCode + "' " + "and a.strProdCode IN(select strProdCode from tblprodsuppmaster where strSuppCode = '" + strSuppCode + "') ";

			// System.out.println("SqlQuery\t"+sqlQuery);
			query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery);
		} else if (strSuppCode.trim().length() > 0 && !strGCode.equalsIgnoreCase("ALL") && strSGCode.equals("ALL") && strLocCode.trim().length() > 0) {
			sqlQuery = "select a.strProdCode,a.strProdName,a.strPartNo,a.dblCostRM,a.dblWeight,b.dblReOrderLevel," + "b.dblReOrderQty,c.dblClosingStk, ifnull(d.pendingQty,0) as OpenReq,a.strIssueUOM  " + "from tblproductmaster a inner join tblreorderlevel b ON a.strProdCode = b.strProdCode and b.strClientCode='" + clientCode + "' "
					+ "inner join tblcurrentstock c ON b.strProdCode = c.strProdCode and c.strClientCode='" + clientCode + "' and c.strUserCode = '" + userCode + "' " + "left outer join (select a.strProdCode, (a.dblQty - IFNULL(d.dblQty,0)) as pendingQty    " + "from tblreqdtl a left outer join tblreqhd b ON a.strReqCode = b.strReqCode  and b.strClientCode='" + clientCode + "' "
					+ "left outer join tblmisdtl d ON a.strReqCode=d.strReqCode and a.strProdCode = d.strProdCode and d.strClientCode='" + clientCode + "' " + "Where b.strLocby ='" + strLocCode + "' and a.strClientCode='" + clientCode + "' Group By a.strProdCode) d ON a.strProdCode = d.strProdCode " + " inner join tblsubgroupmaster e on a.strSGCode=e.strSGCode  and e.strClientCode='" + clientCode
					+ "' " + "inner join tblgroupmaster f on e.strGCode=f.strGCode and f.strGCode='" + strGCode + "' and f.strClientCode='" + clientCode + "' " + "where  b.strLocationCode='" + strLocCode + "' and c.dblClosingStk <= b.dblReOrderLevel and b.dblReOrderLevel > 0 and a.strClientCode='" + clientCode + "' "
					+ "and a.strProdCode IN(select strProdCode from tblprodsuppmaster where strSuppCode = '" + strSuppCode + "') ";

			// System.out.println("SqlQuery\t"+sqlQuery);
			query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery);
		} else if (strSuppCode.trim().length() > 0 && !strGCode.equalsIgnoreCase("ALL") && !strSGCode.equals("ALL") && strLocCode.trim().length() > 0) {
			sqlQuery = "select a.strProdCode,a.strProdName,a.strPartNo,a.dblCostRM,a.dblWeight,b.dblReOrderLevel," + "b.dblReOrderQty,c.dblClosingStk, ifnull(d.pendingQty,0) as OpenReq,a.strIssueUOM  " + "from tblproductmaster a inner join tblreorderlevel b ON a.strProdCode = b.strProdCode and b.strClientCode='" + clientCode + "' "
					+ "inner join tblcurrentstock c ON b.strProdCode = c.strProdCode and c.strClientCode='" + clientCode + "' and c.strUserCode = '" + userCode + "' " + "left outer join (select a.strProdCode, (a.dblQty - IFNULL(d.dblQty,0)) as pendingQty    " + "from tblreqdtl a left outer join tblreqhd b ON a.strReqCode = b.strReqCode  and b.strClientCode='" + clientCode + "' "
					+ "left outer join tblmisdtl d ON a.strReqCode=d.strReqCode and a.strProdCode = d.strProdCode and d.strClientCode='" + clientCode + "' " + "Where b.strLocby ='" + strLocCode + "' and a.strClientCode='" + clientCode + "' Group By a.strProdCode) d ON a.strProdCode = d.strProdCode " + " inner join tblsubgroupmaster e on a.strSGCode=e.strSGCode and e.strSGCode='" + strSGCode
					+ "'  and e.strClientCode='" + clientCode + "' " + "inner join tblgroupmaster f on e.strGCode=f.strGCode and f.strGCode='" + strGCode + "' and f.strClientCode='" + clientCode + "' " + "where  b.strLocationCode='" + strLocCode + "' and c.dblClosingStk <= b.dblReOrderLevel and b.dblReOrderLevel > 0 and a.strClientCode='" + clientCode + "' "
					+ "and a.strProdCode IN(select strProdCode from tblprodsuppmaster where strSuppCode = '" + strSuppCode + "') ";

			// System.out.println("SqlQuery\t"+sqlQuery);
			query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery);
		}

		List list = query.list();
		return list;
	}

	@Override
	public void funSaveMRPIDtl(clsMRPIDtl funSaveMRPIDtl) {
		sessionFactory.getCurrentSession().saveOrUpdate(funSaveMRPIDtl);
	}

	@Override
	public void funAddProductStandard(List<clsProductStandardModel> objListprodStandard) {
		try {
			for (clsProductStandardModel obj : objListprodStandard) {
				sessionFactory.getCurrentSession().saveOrUpdate(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	@Override
	public void funDeleteProductStandard(String strLocCode, String strPropertyCode, String clientCode)
	{
		try
		{
			sessionFactory.getCurrentSession().createSQLQuery("Delete from tblproductstandard where strClientCode='" + clientCode + "' and strLocCode='" + strLocCode + "' ").executeUpdate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public List<clsProductStandardModel> funGetProductStandartList(String strPropertyCode, String strLocCode, String clientCode) {

		Criteria cr = sessionFactory.getCurrentSession().createCriteria(clsProductStandardModel.class);
		cr.add(Restrictions.eq("strClientCode", clientCode));
		cr.add(Restrictions.eq("strPropertyCode", strPropertyCode));
		cr.add(Restrictions.eq("strLocCode", strLocCode));
		List list = cr.list();
		List<clsProductStandardModel> objListModel = new ArrayList<clsProductStandardModel>();
		if (list.size() > 0) {
			clsProductStandardModel obj = null;
			for (int i = 0; i < list.size(); i++) {
				obj = (clsProductStandardModel) list.get(i);
				objListModel.add(obj);
			}
		}
		return objListModel;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> funGetProductDtl(String strProdCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("select strProdName " + "from clsProductMasterModel where strProdCode=:strProdCode and strClientCode=:clientCode");
		query.setParameter("strProdCode", strProdCode);
		query.setParameter("clientCode", clientCode);
		@SuppressWarnings("rawtypes")
		List list = query.list();
		return list;
	}

}
