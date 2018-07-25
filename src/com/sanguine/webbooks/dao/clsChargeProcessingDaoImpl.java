package com.sanguine.webbooks.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.bean.clsCreditorOutStandingReportBean;
import com.sanguine.webbooks.model.clsChargeProcessingHDModel;

@Repository("clsChargeProcessingDao")
public class clsChargeProcessingDaoImpl implements clsChargeProcessingDao
{

	@Autowired
	private SessionFactory webBooksSessionFactory;

	@Override
	public void funAddUpdateChargeProcessing(clsChargeProcessingHDModel objMaster)
	{
		webBooksSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsChargeProcessingHDModel funGetChargeProcessing(String docCode, String clientCode)
	{
		return new clsChargeProcessingHDModel();
	}

	@Override
	public void funClearTblChargeGenerationTemp(String strMemberCode)
	{
		Query query;
		if (strMemberCode.equalsIgnoreCase("All"))
		{
			query = webBooksSessionFactory.getCurrentSession().createSQLQuery("TRUNCATE `tblChargeGenerationTemp` ");
		}
		else
		{
			query = webBooksSessionFactory.getCurrentSession().createSQLQuery("delete from tblChargeGenerationTemp where strMemberCode='" + strMemberCode + "' ");
		}

		int affectedRows = query.executeUpdate();
		System.out.println("affectedRows=" + affectedRows);
	}

	@Override
	public List funGetAllMembers(String clientCode, String propertyCode)
	{
		List<?> list = null;
		list = webBooksSessionFactory.getCurrentSession().createSQLQuery("select strDebtorCode,CONCAT_WS(' ',strPrefix,strFirstName,strMiddleName,strLastName) as strFullName from tblsundarydebtormaster ").list();
		return list;
	}

	@Override
	public List funCalculateOutstanding(String accountCode, String dteFromDate, String dteToDate, String memberCode, String clientCode, String propertyCode)
	{

		ArrayList<clsCreditorOutStandingReportBean> listMemberOutstanding = new ArrayList();

		String sqlQuery = "select c.strDebtorCode,c.strDebtorName ,sum(b.dblDrAmt),sum(b.dblCrAmt) " + " FROM tbljvhd a,tbljvdtl b,tbljvdebtordtl c where a.strVouchNo= b.strVouchNo and b.strVouchNo=c.strVouchNo " + " and date(a.dteVouchDate) between '" + dteFromDate + "' and '" + dteToDate + "' " + "and a.strClientCode='" + clientCode + "' " + "and a.strPropertyCode='" + propertyCode + "' and b.strAccountCode='" + accountCode + "'  ";
		if (!memberCode.equalsIgnoreCase("All"))
		{
			sqlQuery = sqlQuery + " and  c.strDebtorCode= '" + memberCode + "' ";
		}

		sqlQuery = sqlQuery + " group by c.strDebtorCode order by c.strDebtorCode ";

		List listProdDtl = webBooksSessionFactory.getCurrentSession().createSQLQuery(sqlQuery).list();

		if (listProdDtl.size() > 0 && listProdDtl != null)
		{
			for (int j = 0; j < listProdDtl.size(); j++)
			{

				Object[] prodArr = (Object[]) listProdDtl.get(j);
				double bal = 0.0;
				String billNo = "";
				double debitAmt = Double.parseDouble(prodArr[2].toString());
				double creditAmt = Double.parseDouble(prodArr[3].toString());
				String paymnetDate = "";

				String sqlOpen = "select a.dblOpeningbal,a.strCrDr from  tblsundarydebtoropeningbalance a where a.strDebtorCode='" + prodArr[0].toString() + "' ";
				List listOpen = webBooksSessionFactory.getCurrentSession().createSQLQuery(sqlOpen).list();
				if (listOpen.size() > 0 && listOpen != null)
				{
					Object[] open = (Object[]) listOpen.get(0);
					if (open[1].toString().equalsIgnoreCase("Dr"))
					{
						debitAmt = debitAmt + Double.parseDouble(open[0].toString());
					}
					else
					{
						creditAmt = creditAmt + Double.parseDouble(open[0].toString());
					}
				}
				String sql = "  SELECT  b.dblDrAmt , b.dblCrAmt  " + " FROM tblreceipthd a, tblreceiptdtl b,tblreceiptdebtordtl c  WHERE a.strVouchNo=b.strVouchNo and a.strVouchNo=c.strVouchNo " + " AND DATE(a.dteVouchDate) BETWEEN '" + dteFromDate + "' AND '" + dteToDate + "' " + " and  b.strAccountCode='" + accountCode + "'  AND a.strPropertyCode=b.strPropertyCode " + " AND c.strDebtorCode='" + prodArr[0].toString() + "'  " + " AND a.strPropertyCode='" + propertyCode + "' AND a.strClientCode='" + clientCode + "'  ";

				List listREcipt = webBooksSessionFactory.getCurrentSession().createSQLQuery(sql).list();

				if (listREcipt.size() > 0 && listREcipt != null)
				{
					Object[] payment = (Object[]) listREcipt.get(0);
					creditAmt = creditAmt + Double.parseDouble(payment[1].toString());
					debitAmt = debitAmt + Double.parseDouble(payment[0].toString());

				}

				String sqlPay = "SELECT b.dblDrAmt, b.dblCrAmt " + " FROM tblpaymenthd a, tblpaymentdtl b,tblpaymentdebtordtl c " + " WHERE a.strVouchNo=b.strVouchNo AND a.strVouchNo=c.strVouchNo and  b.strAccountCode='" + accountCode + "'  " + " AND DATE(a.dteVouchDate) BETWEEN '" + dteFromDate + "' AND '" + dteToDate + "'  AND a.strPropertyCode=b.strPropertyCode " + " AND c.strDebtorCode='" + prodArr[0].toString() + "' AND a.strPropertyCode='" + propertyCode + "' AND a.strClientCode='" + clientCode + "' ";

				List listPaymt = webBooksSessionFactory.getCurrentSession().createSQLQuery(sqlPay).list();

				if (listPaymt.size() > 0 && listPaymt != null)
				{
					Object[] payment = (Object[]) listPaymt.get(0);
					creditAmt = creditAmt + Double.parseDouble(payment[1].toString());
					debitAmt = debitAmt + Double.parseDouble(payment[0].toString());

				}

				bal = (debitAmt - creditAmt);

				clsCreditorOutStandingReportBean objProdBean = new clsCreditorOutStandingReportBean();

				objProdBean.setStrDebtorCode(prodArr[0].toString());
				objProdBean.setStrDebtorName(prodArr[1].toString());

				objProdBean.setDblCrAmt(creditAmt);
				objProdBean.setDblDrAmt(debitAmt);
				objProdBean.setDblBalAmt(bal);

				listMemberOutstanding.add(objProdBean);

			}

		}

		return listMemberOutstanding;
	}

	@Override
	public void funUpdateMemberOutstanding(String memberCode, double dblOutstanding, String clientCode, String propertyCode)
	{
		Query sqlUpdateOutstanding = webBooksSessionFactory.getCurrentSession().createSQLQuery("update tblsundarydebtormaster a set a.dblOutstanding='" + dblOutstanding + "' " + "where a.strDebtorCode='" + memberCode + "' and a.strClientCode='" + clientCode + "' and a.strPropertyCode='" + propertyCode + "' ");
		int affectedRows = sqlUpdateOutstanding.executeUpdate();
	}

	@Override
	public List funIsChargeApplicable(String memberCode, String chargeCode, String clientCode, String propertyCode)
	{

		Query sqlChargeCondition = webBooksSessionFactory.getCurrentSession().createSQLQuery("select strSql from tblchargemaster where strChargeCode='" + chargeCode + "' and strClientCode='" + clientCode + "' ");
		List listCondition = sqlChargeCondition.list();

		String sqlCondition = listCondition.get(0).toString();

		Query sqlIsChargeApplicable = webBooksSessionFactory.getCurrentSession().createSQLQuery("select  Debtor_Code,Member_Full_Name,Outstanding  from dbwebmms.vwdebtormemberdtl " + "where  " + sqlCondition + "  " + "and Customer_Code ='" + memberCode + "' and Client_Code='" + clientCode + "' " + "and Property_Code='" + propertyCode + "' " + "");

		return sqlIsChargeApplicable.list();
	}

}
