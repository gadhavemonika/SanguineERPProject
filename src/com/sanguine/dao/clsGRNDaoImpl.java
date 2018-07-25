package com.sanguine.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.model.clsGRNDtlModel;
import com.sanguine.model.clsGRNHdModel;
import com.sanguine.model.clsGRNTaxDtlModel;

@Repository("clsGRNDao")
public class clsGRNDaoImpl implements clsGRNDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void funAddUpdate(clsGRNHdModel object) {
		sessionFactory.getCurrentSession().saveOrUpdate(object);
	}

	@Override
	public void funAddUpdateDtl(clsGRNDtlModel object) {
		sessionFactory.getCurrentSession().save(object);
	}

	@Override
	public void funUpdateProductSupplier(String suppCode, String prodCode, String clientCode, String maxQty, String price) {
		String hql = "UPDATE clsProdSuppMasterModel set dblLastCost = :price, dblMaxQty = :maxQty" + "WHERE strSuppCode = :suppCode and strProdCode = :prodCode and strClientCode =: clientCode";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("price", price);
		query.setParameter("maxQty", maxQty);
		query.setParameter("suppCode", suppCode);
		query.setParameter("prodCode", prodCode);
		query.setParameter("clientCode", clientCode);
		query.executeUpdate();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetProdSupp(String suppCode, String prodCode, String clientCode) {
		String hql = "from clsProdSuppMasterModel where strSuppCode = :suppCode and strProdCode = :prodCode and strClientCode= :clientCode ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("suppCode", suppCode);
		query.setParameter("prodCode", prodCode);
		query.setParameter("clientCode", clientCode);
		return query.list();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<clsGRNHdModel> funGetList(String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("from clsGRNHdModel where strClientCode= :clientCode");
		query.setParameter("clientCode", clientCode);
		List list = query.list();
		return (List<clsGRNHdModel>) list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetObject(String GRNCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("from clsGRNHdModel a,clsSupplierMasterModel b,clsLocationMasterModel c " + "where a.strGRNCode= :grnCode and a.strSuppCode=b.strPCode and a.strLocCode=c.strLocCode " + "and a.strClientCode= :clientCode");
		query.setParameter("grnCode", GRNCode);
		query.setParameter("clientCode", clientCode);
		return query.list();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetDtlList(String GRNCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("from clsGRNDtlModel a,clsProductMasterModel b " + " where a.strGRNCode = :grnCode and a.strProdCode=b.strProdCode and a.strClientCode= :clientCode" + " and b.strClientCode= :clientCode");
		query.setParameter("grnCode", GRNCode);
		query.setParameter("clientCode", clientCode);
		List list = query.list();
		return list;
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public List funGetDtlListAgainst(String code, String clientCode, String againstTableName) {
		/*
		 * String strPOCodes=""; String tempPoCode[]=code.split(","); for(int
		 * i=0;i<tempPoCode.length;i++) { if(strPOCodes.length()>0) { strPOCodes
		 * =strPOCodes+" or a.strPOCode='"+ tempPoCode[i]+"' "; } else {
		 * strPOCodes = "a.strPOCode='"+ tempPoCode[i]+"' ";
		 * 
		 * } }
		 */
		// String sql="";

		// Old Query with out NonStockable
		/*
		 * String sql=
		 * "select c.strProdCode,a.strPOCode,a.dblOrdQty, ifnull(b.POQty,0), a.dblOrdQty - ifnull(b.POQty,0) as BalanceQty,a.dblPrice"
		 * + ",a.dblWeight,c.strUOM,c.strProdName,c.strExpDate,a.dblDiscount " +
		 * "from tblpurchaseorderdtl a left outer join (SELECT b.strCode AS POCode, b.strProdCode, SUM(b.dblQty) AS POQty "
		 * +
		 * "FROM tblgrnhd a INNER JOIN tblgrndtl b ON a.strGRNCode = b.strGRNCode "
		 * + "WHERE (a.strAgainst = 'Purchase Order') " +
		 * "GROUP BY POCode, b.strProdCode) b on a.strPOCode = b.POCode and a.strProdCode = b.strProdCode "
		 * +
		 * "left outer join tblproductmaster c on a.strProdCode=c.strProdCode "
		 * + "where a.dblOrdQty > ifnull(b.POQty,0) and a.strPOCode='"+code+
		 * "' and a.strClientCode='"+clientCode+"'";
		 */
		String sql = "";
		StringBuffer sbsql = new StringBuffer();
		/*
		 * sql=
		 * "select c.strProdCode,a.strPOCode,a.dblOrdQty, ifnull(b.POQty,0), a.dblOrdQty - ifnull(b.POQty,0) as BalanceQty,a.dblPrice , "
		 * +
		 * " a.dblWeight,c.strUOM,c.strProdName,c.strExpDate,a.dblDiscount,ifnull(f.strLocCode,'') as strLocCode, "
		 * +
		 * " ifnull(f.strLocName,'') as strLocName,ifnull(c.strNonStockableItem,'') as strNonStockableItem,ifnull(e.strReqCode,'') as strReqCode "
		 * +
		 * " from tblpurchaseorderdtl a left outer join (SELECT b.strCode AS POCode, b.strProdCode, SUM(b.dblQty) AS POQty "
		 * +
		 * " FROM tblgrnhd a INNER JOIN tblgrndtl b ON a.strGRNCode = b.strGRNCode  and b.strClientCode='"
		 * +clientCode+"'" +
		 * " WHERE (a.strAgainst = 'Purchase Order')  and a.strClientCode='"
		 * +clientCode+"'" +
		 * " GROUP BY POCode, b.strProdCode) b on a.strPOCode = b.POCode and a.strProdCode = b.strProdCode "
		 * +
		 * " left outer join tblproductmaster c on a.strProdCode=c.strProdCode and c.strClientCode='"
		 * +clientCode+"' " +
		 * " left outer join tblmrpidtl d on d.strPICode=a.strPICode and d.strClientCode='"
		 * +clientCode+"'" +
		 * " left outer join tblreqhd e on e.strReqCode=d.strReqCode and e.strClientCode='"
		 * +clientCode+"'" +
		 * " left outer join tbllocationmaster f on f.strLocCode=e.strLocBy and f.strClientCode='"
		 * +clientCode+"' " +
		 * " where a.dblOrdQty > ifnull(b.POQty,0) and a.strPOCode='"
		 * +code+"' and a.strClientCode='"+clientCode+"'";
		 */

		sbsql.append("select c.strProdCode,a.strPOCode,a.dblOrdQty, ifnull(b.POQty,0), a.dblOrdQty - ifnull(b.POQty,0) ,a.dblPrice , " + " a.dblWeight,c.strUOM,c.strProdName,if(LENGTH(c.strExpDate)=0,'N',c.strExpDate),a.dblDiscount,ifnull(f.strLocCode,''), " + " ifnull(f.strLocName,'') ,ifnull(c.strNonStockableItem,'') ,ifnull(e.strReqCode,'') "
				+ " , ifnull(p.dblConversion,1),ifnull(p.strCurrency,''),p.dblFreight,p.dblInsurance,p.dblOtherCharges,p.dblCIF,p.dblClearingAgentCharges,p.dblVATClaim,p.dblFOB FROM tblpurchaseorderhd p, tblpurchaseorderdtl a left outer join (SELECT b.strCode AS POCode, b.strProdCode, SUM(b.dblQty) AS POQty " + " FROM tblgrnhd a INNER JOIN tblgrndtl b ON a.strGRNCode = b.strGRNCode  and b.strClientCode='"
				+ clientCode
				+ "'"
				+ " WHERE (a.strAgainst = 'Purchase Order')  and a.strClientCode='"
				+ clientCode
				+ "'"
				+ " GROUP BY POCode, b.strProdCode) b on a.strPOCode = b.POCode and a.strProdCode = b.strProdCode "
				+ " left outer join tblproductmaster c on a.strProdCode=c.strProdCode and c.strClientCode='"
				+ clientCode
				+ "' "
				+ " left outer join tblmrpidtl d on d.strPICode=a.strPICode and d.strClientCode='"
				+ clientCode
				+ "'"
				+ " left outer join tblreqhd e on e.strReqCode=d.strReqCode and e.strClientCode='"
				+ clientCode
				+ "'"
				+ " left outer join tbllocationmaster f on f.strLocCode=e.strLocBy and f.strClientCode='"
				+ clientCode
				+ "' "
				+ " where a.dblOrdQty > ifnull(b.POQty,0) and a.strPOCode='"
				+ code
				+ "' and a.strClientCode='" + clientCode + "' and p.strPOCode=a.strPOCode ");

		System.out.println(sbsql);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sbsql.toString());

		List list = query.list();
		return list;
	}

	@SuppressWarnings("unused")
	@Override
	public void funDeleteDtl(String GRNCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("delete clsGRNDtlModel where strGRNCode = :grnCode and strClientCode= :clientCode");
		query.setParameter("grnCode", GRNCode);
		query.setParameter("clientCode", clientCode);
		int result = query.executeUpdate();
	}

	@Override
	public int funDeleteProdSupp(String suppCode, String prodCode, String clientCode) {
		String hql = "delete clsProdSuppMasterModel where strSuppCode = :suppCode and strProdCode = :prodCode and strClientCode= :clientCode ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("suppCode", suppCode);
		query.setParameter("prodCode", prodCode);
		query.setParameter("clientCode", clientCode);
		return query.executeUpdate();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetFixedAmtTaxList(String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("from clsTaxHdModel where strTaxType='Fixed Amount' " + " and strClientCode= :clientCode");
		query.setParameter("clientCode", clientCode);
		return query.list();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetList(String groupCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("from clsGroupMasterModel ");
		List list = query.list();
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funLoadPOforGRN(String strSuppCode, String strPropCode, String strClientCode) {
		/*
		 * String
		 * sql="select strPOCode,date(dtPODate),strCode from tblpurchaseorderhd  "
		 * + " where strSuppCode='"+strSuppCode+"' and strclosePO='No' ";
		 */
		String sql = "select strPOCode,Date(dtPODate),strAgainst,ifnull(dblTotal,0),ifnull(b.strCurrencyName,''),ifnull(dblConversion,1) from tblpurchaseorderhd a "
				+ " left outer join tblcurrencymaster b on a.strCurrency=b.strCurrencyCode "
				+ " Where strPOCode IN " + "(select distinct a.strPOCode from tblpurchaseorderdtl a " + "left outer join (SELECT b.strCode AS POCode, b.strProdCode, SUM(b.dblQty) AS POQty " + "FROM tblgrnhd a INNER JOIN tblgrndtl b ON a.strGRNCode = b.strGRNCode and b.strClientCode='" + strClientCode + "' "
				+ " WHERE (a.strAgainst = 'Purchase Order') and a.strClientCode='" + strClientCode + "' GROUP BY b.strCode, b.strProdCode) b " + "on a.strPOCode = b.POCode and a.strProdCode = b.strProdCode and a.strClientCode='" + strClientCode + "'" + "where  a.dblOrdQty > ifnull(b.POQty,0)) " + " and a.strClosePO != 'Yes' and a.strAuthorise='Yes' and a.strSuppCode='" + strSuppCode
				+ "' and a.strClientCode='" + strClientCode + "' and a.strPropCode='" + strPropCode + "'";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		List list = query.list();
		return list;
	}

	public String funAuthorise(String strFormName) {
		String flag = "No";
		String sql = "select count(*) from tblworkflowforslabbasedauth";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		int cnt = Integer.parseInt(query.list().get(0).toString());
		if (cnt != 0) {
			flag = "Yes";
		}
		return flag;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetNonStkData(String strPOCode, String strGrnCode, String strClientCode) {
		List list = new ArrayList();
		String sql = "select IfNULL(b.strReqCode,'') from tblpurchaseorderdtl a " + " inner join tblmrpidtl b on a.strPIcode =b.strPIcode" + " where a.strPOCode='" + strPOCode + "' group by b.strReqCode";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		List ReqCodelist = query.list();
		if (!ReqCodelist.isEmpty()) {
			String strReqCode = ReqCodelist.get(0).toString();

			String sql1 = " SELECT reqhd.strReqCode,DATE_FORMAT(reqhd.dtReqDate,'%m-%d-%Y') as reqDate,reqdtl.strProdCode,p.strProdName," + " l .strLocName as strLocByName,reqhd.strLocby ,lo.strLocName as strLocToName,reqhd.strLocon,reqdtl.dblQty,reqdtl.strRemarks," + " p.dblCostRM ,p.dblCostRM*reqdtl.dblQty as price,reqhd.dblSubTotal  ,grnDtl.GRNQty "
					+ " FROM tblreqhd reqhd inner join tblreqdtl reqdtl on reqhd.strReqCode =reqdtl.strReqCode and reqdtl.strClientCode='"
					+ strClientCode
					+ "' "
					+ " INNER JOIN tblproductmaster p ON reqdtl.strProdCode = p.strProdCode and p.strClientCode='"
					+ strClientCode
					+ "' "
					+ " inner join tbllocationmaster l on l.strLocCode=reqhd.strLocby and l.strClientCode='"
					+ strClientCode
					+ "' "
					+ " inner join tbllocationmaster lo on lo.strLocCode=reqhd.strLocon  and lo.strClientCode='"
					+ strClientCode
					+ "'  "
					+ " left outer Join  (SELECT strProdCode, sum(dblQty-dblRejected) as GRNQty "
					+ " from tblgrndtl where strGRNCode='"
					+ strGrnCode
					+ "'  and strClientCode='"
					+ strClientCode
					+ "' group by strProdCode) grnDtl on grnDtl.strProdCode=reqdtl.strProdCode "
					+ " where reqhd.strReqCode='"
					+ strReqCode
					+ "' and p.strNonStockableItem= 'Y' and reqhd.strClientCode='"
					+ strClientCode
					+ "' order by p.strProdCode ";
			query = sessionFactory.getCurrentSession().createSQLQuery(sql1);
			list = query.list();

		}
		return list;

	}

	@Override
	public int funDeleteGRNTaxDtl(String GRNCode, String clientCode) {
		String sql = "DELETE clsGRNTaxDtlModel WHERE strGRNCode= :GRNCode and strClientCode= :clientCode";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query.setParameter("GRNCode", GRNCode);
		query.setParameter("clientCode", clientCode);
		return query.executeUpdate();
	}

	@Override
	public void funAddUpdateGRNTaxDtl(clsGRNTaxDtlModel objTaxDtlModel) {
		sessionFactory.getCurrentSession().saveOrUpdate(objTaxDtlModel);
	}
}
