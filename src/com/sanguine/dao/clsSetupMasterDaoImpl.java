package com.sanguine.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.model.clsCompanyLogoModel;
import com.sanguine.model.clsCompanyMasterModel;
import com.sanguine.model.clsProcessSetupModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.model.clsTCMasterModel;
import com.sanguine.model.clsTreeMasterModel;
import com.sanguine.model.clsWorkFlowForSlabBasedAuth;
import com.sanguine.model.clsWorkFlowModel;

@Repository("clsSetupMasterDao")
public class clsSetupMasterDaoImpl implements clsSetupMasterDao {
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("rawtypes")
	@Override
	public void funAddUpdate(clsCompanyMasterModel object) {
		sessionFactory.getCurrentSession().saveOrUpdate(object);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public clsCompanyMasterModel funGetObject(String clientCode) {
		clsCompanyMasterModel objclsCompanyMasterModel = null;
		try {
			List ls = new ArrayList();
			Query query = sessionFactory.getCurrentSession().createQuery("from clsCompanyMasterModel WHERE strClientCode = :clientCode ");
			query.setParameter("clientCode", clientCode);
			ls = query.list();
			if (ls.size() > 0) {
				objclsCompanyMasterModel = (clsCompanyMasterModel) ls.get(0);
			} else {
				objclsCompanyMasterModel = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objclsCompanyMasterModel;
	}

	@Override
	public void funDeleteProcessSetup(String propertyCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("DELETE clsProcessSetupModel WHERE strPropertyCode= :propertyCode and strClientCode=:clientCode");
		query.setParameter("propertyCode", propertyCode);
		query.setParameter("clientCode", clientCode);
		query.executeUpdate();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sanguine.dao.clsSetupMasterDao#funAddUpdateProcessSetup(com.sanguine
	 * .model.clsProcessSetupModel) to insert data into tblprocesssetup
	 */

	@Override
	public void funAddUpdateProcessSetup(clsProcessSetupModel ProcessSetupModel) {
		sessionFactory.getCurrentSession().save(ProcessSetupModel);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<clsTreeMasterModel> funGetProcessSetupForms() {
		Query query = sessionFactory.getCurrentSession().createQuery("from clsTreeMasterModel WHERE strProcessForm='YES' ");
		List list = query.list();
		return (List<clsTreeMasterModel>) list;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<clsProcessSetupModel> funGetProcessSetupModelList(String propertyCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("from clsProcessSetupModel WHERE strPropertyCode=:propertyCode and strClientCode=:clientCode");
		query.setParameter("propertyCode", propertyCode);
		query.setParameter("clientCode", clientCode);
		List list = query.list();
		return (List<clsProcessSetupModel>) list;

	}

	@Override
	public void funDeleteWorkFlowAutorization(String propertyCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("DELETE clsWorkFlowModel WHERE strPropertyCode= :propertyCode and strClientCode=:clientCode");
		query.setParameter("propertyCode", propertyCode);
		query.setParameter("clientCode", clientCode);
		query.executeUpdate();
	}

	@Override
	public void funAddWorkFlowAuthorization(clsWorkFlowModel WorkFlowModel) {
		sessionFactory.getCurrentSession().save(WorkFlowModel);
	}

	@Override
	public void funDeleteWorkFlowForslabBasedAuth(String propertyCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("DELETE clsWorkFlowForSlabBasedAuth WHERE strPropertyCode= :propertyCode and strClientCode=:clientCode");
		query.setParameter("propertyCode", propertyCode);
		query.setParameter("clientCode", clientCode);
		query.executeUpdate();

	}

	@Override
	public void funAddWorkFlowForslabBasedAuth(clsWorkFlowForSlabBasedAuth WorkFlowForSlabBasedAuth) {
		sessionFactory.getCurrentSession().save(WorkFlowForSlabBasedAuth);
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public clsPropertySetupModel funGetObjectPropertySetup(String propertyCode, String clientCode) {

		// String
		// sql="Select  from tblpropertysetup where strPropertyCode='"+propertyCode+"' and  strClientCode = '"+clientCode+"' ";
		/*
		 * // This is for getting all cell data of the row in tblPropertysetup
		 * String hql=
		 * "FROM clsPropertySetupModel  where strPropertyCode= :propertyCode AND strClientCode= :clientCode "
		 * ; Query query = sessionFactory.getCurrentSession().createQuery(hql);
		 * query.setParameter("clientCode",clientCode);
		 * query.setParameter("propertyCode",propertyCode); List results =
		 * query.list(); if(results.size()>0){ return
		 * (clsPropertySetupModel)results.get(0); }else{ return null; }
		 */

		clsPropertySetupModel objSetUpModel = new clsPropertySetupModel();
		// String
		// sql="Select  a.strCompanyCode,a.strPropertyCode,a.dblBondAmt,a.dtEnd from tblpropertysetup a where a.strPropertyCode=: and  a.strClientCode =:clientCode ";
		String sql = "SELECT a.strCompanyCode,a.strPropertyCode,a.dblBondAmt,a.dtFromTime,a.dtToTime, " + " a.intDueDays,a.intId,a.strAcceptanceNo,a.strAdd1,a.strAdd2,a.strCity,a.strState,a.strCountry,a.strPin,a.strAsseeCode,a.strBAdd1,a.strBAdd2, " + " a.strBCity,a.strBCountry,a.strBPin,a.strBState,a.strBankAccountNo,a.strBankAdd1,a.strBankAdd2,a.strBankCity,a.strBankName,a.strBranchName, "
				+ "a.strCST,a.strCommi,a.strDivision,a.strEmail ,a.strFax,a.strLocCode,a.strNegStock,a.strPOBOM,a.strPanNo " + " FROM clsPropertySetupModel a where a.strPropertyCode='" + propertyCode + "' and  a.strClientCode ='" + clientCode + "' ";

		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		// query.setParameter("propertyCode", propertyCode);
		// query.setParameter("clientCode", clientCode);
		List list = query.list();
		Object[] objPropSetUp = (Object[]) list.get(0);
		objSetUpModel.setStrCompanyCode(objPropSetUp[0].toString());
		objSetUpModel.setStrPropertyCode(objPropSetUp[1].toString());
		objSetUpModel.setDblBondAmt(objPropSetUp[2].toString());
		objSetUpModel.setDtFromTime(objPropSetUp[3].toString());
		objSetUpModel.setDtToTime(objPropSetUp[4].toString());
		objSetUpModel.setIntDueDays(objPropSetUp[5].toString());
		objSetUpModel.setIntId(Integer.parseInt(objPropSetUp[6].toString()));
		objSetUpModel.setStrAcceptanceNo(objPropSetUp[7].toString());
		objSetUpModel.setStrAdd1(objPropSetUp[8].toString());
		objSetUpModel.setStrAdd2(objPropSetUp[9].toString());
		objSetUpModel.setStrCity(objPropSetUp[10].toString());
		objSetUpModel.setStrState(objPropSetUp[11].toString());
		objSetUpModel.setStrCountry(objPropSetUp[12].toString());
		objSetUpModel.setStrPin(objPropSetUp[13].toString());
		objSetUpModel.setStrAsseeCode(objPropSetUp[14].toString());
		objSetUpModel.setStrBAdd1(objPropSetUp[15].toString());
		objSetUpModel.setStrBAdd2(objPropSetUp[16].toString());
		objSetUpModel.setStrBCity(objPropSetUp[17].toString());
		objSetUpModel.setStrBCountry(objPropSetUp[18].toString());
		objSetUpModel.setStrBPin(objPropSetUp[19].toString());
		objSetUpModel.setStrBState(objPropSetUp[20].toString());
		objSetUpModel.setStrBankAccountNo(objPropSetUp[21].toString());
		objSetUpModel.setStrBankAdd1(objPropSetUp[22].toString());
		objSetUpModel.setStrBAdd2(objPropSetUp[23].toString());
		objSetUpModel.setStrBankCity(objPropSetUp[24].toString());
		objSetUpModel.setStrBankName(objPropSetUp[25].toString());
		objSetUpModel.setStrBranchName(objPropSetUp[26].toString());
		objSetUpModel.setStrCST(objPropSetUp[27].toString());
		objSetUpModel.setStrCommi(objPropSetUp[28].toString());
		objSetUpModel.setStrDivision(objPropSetUp[29].toString());
		objSetUpModel.setStrEmail(objPropSetUp[30].toString());
		objSetUpModel.setStrFax(objPropSetUp[31].toString());
		objSetUpModel.setStrLocCode(objPropSetUp[32].toString());
		objSetUpModel.setStrNegStock(objPropSetUp[33].toString());
		objSetUpModel.setStrPOBOM(objPropSetUp[34].toString());
		objSetUpModel.setStrPanNo(objPropSetUp[35].toString());

		String sql2 = "SELECT a.strPhone,a.strPurEmail,a.strRangeDiv,a.strRegNo,a.strSAdd1,a.strSAdd2,a.strSCity,a.strSCountry,a.strSOBOM,a.strSPin,a.strSState, " + " a.strSaleEmail,a.strSerTax,a.strSwiftCode,a.strTotalWorkhour,a.strVAT,a.strWebsite,a.strWorkFlowbasedAuth ,a.strIndustryType,a.strLate,a.strRej, "
				+ " a.strPChange,a.strExDelay,a.strMask,a.strRangeAdd,a.intdec,a.strListPriceInPO,a.strCMSModule,a.strSOKOTPrint "
				// +" ,a.strTC1,a.strTC2,a.strTC3,a.strTC4,a.strTC5,a.strTC6,a.strTC7,a.strTC8,a.strTC9,a.strTC10,a.strTC11,a.strTC12 "
				+ " FROM clsPropertySetupModel a where a.strPropertyCode='" + propertyCode + "' and  a.strClientCode ='" + clientCode + "' ";

		Query query2 = sessionFactory.getCurrentSession().createQuery(sql2);
		// query.setParameter("propertyCode", propertyCode);
		// query.setParameter("clientCode", clientCode);
		List list2 = query2.list();
		Object[] objPropSetUp2 = (Object[]) list2.get(0);
		objSetUpModel.setStrPhone(objPropSetUp2[0].toString());
		objSetUpModel.setStrPurEmail(objPropSetUp2[1].toString());
		objSetUpModel.setStrRangeDiv(objPropSetUp2[2].toString());
		objSetUpModel.setStrRegNo(objPropSetUp2[3].toString());
		objSetUpModel.setStrSAdd1(objPropSetUp2[4].toString());
		objSetUpModel.setStrSAdd2(objPropSetUp2[5].toString());
		objSetUpModel.setStrSCity(objPropSetUp2[6].toString());
		objSetUpModel.setStrSCountry(objPropSetUp2[7].toString());
		objSetUpModel.setStrSOBOM(objPropSetUp2[8].toString());
		objSetUpModel.setStrSPin(objPropSetUp2[9].toString());
		objSetUpModel.setStrSState(objPropSetUp2[10].toString());
		objSetUpModel.setStrSaleEmail(objPropSetUp2[11].toString());
		objSetUpModel.setStrSerTax(objPropSetUp2[12].toString());
		objSetUpModel.setStrSwiftCode(objPropSetUp2[13].toString());
		objSetUpModel.setStrTotalWorkhour(objPropSetUp2[14].toString());
		objSetUpModel.setStrVAT(objPropSetUp2[15].toString());
		objSetUpModel.setStrWebsite(objPropSetUp2[16].toString());
		objSetUpModel.setStrWorkFlowbasedAuth(objPropSetUp2[17].toString());
		objSetUpModel.setStrIndustryType(objPropSetUp2[18].toString());
		objSetUpModel.setStrLate(objPropSetUp2[19].toString());
		objSetUpModel.setStrRej(objPropSetUp2[20].toString());
		objSetUpModel.setStrPChange(objPropSetUp2[21].toString());
		objSetUpModel.setStrExDelay(objPropSetUp2[22].toString());
		objSetUpModel.setStrMask(objPropSetUp2[23].toString());

		if (objPropSetUp2[24] == null) {
			objSetUpModel.setStrRangeAdd("");
		} else {
			objSetUpModel.setStrRangeAdd(objPropSetUp2[24].toString());
		}
		objSetUpModel.setIntdec(Integer.parseInt(objPropSetUp2[25].toString()));
		objSetUpModel.setStrListPriceInPO(objPropSetUp2[26].toString());
		objSetUpModel.setStrCMSModule(objPropSetUp2[27].toString());
		objSetUpModel.setStrSOKOTPrint(objPropSetUp2[28].toString());

		String sql3 = "SELECT a.strBatchMethod,a.strTPostingType,a.strAudit,a.strAutoDC,a.strUserCreated,a.dtCreatedDate,a.strUserModified,a.dtLastModified,a.strClientCode,a.intqtydec,a.strRatePickUpFrom, " + " a.strShowReqVal,a.strShowStkReq,a.strShowValMISSlip,a.strChangeUOMTrans,a.strShowProdMaster,a.strAuditFrom,a.strShowProdDoc, "
				+ " a.strAllowDateChangeInMIS,a.strShowTransAsc_Desc,a.strNameChangeProdMast,a.strStkAdjReason,a.intNotificationTimeinterval, " + " a.strMonthEnd,a.strShowAllProdToAllLoc,a.strLocWiseProductionOrder,a.strShowStockInOP,a.strShowAvgQtyInOP, " + "a.strShowStockInSO,a.strShowAvgQtyInSO,a.strDivisionAdd,a.strEffectOfDiscOnPO,a.strInvFormat,a.strECCNo,  "
				+ "a.strSMSProvider,a.strSMSAPI,a.strSMSContent,strInvNote,strCurrencyCode,strShowAllPropCustomer, " + " strEffectOfInvoice,strEffectOfGRNWebBook , strMultiCurrency ,strShowAllPartyToAllLoc " + " FROM clsPropertySetupModel a where a.strPropertyCode='" + propertyCode + "' and  a.strClientCode ='" + clientCode + "' ";

		Query query3 = sessionFactory.getCurrentSession().createQuery(sql3);
		// query.setParameter("propertyCode", propertyCode);
		// query.setParameter("clientCode", clientCode);
		List list3 = query3.list();
		Object[] objPropSetUp3 = (Object[]) list3.get(0);
		objSetUpModel.setStrBatchMethod(objPropSetUp3[0].toString());
		objSetUpModel.setStrTPostingType(objPropSetUp3[1].toString());
		objSetUpModel.setStrAudit(objPropSetUp3[2].toString());
		objSetUpModel.setStrAutoDC(objPropSetUp3[3].toString());
		objSetUpModel.setStrUserCreated(objPropSetUp3[4].toString());
		objSetUpModel.setDtCreatedDate(objPropSetUp3[5].toString());
		objSetUpModel.setStrUserModified(objPropSetUp3[6].toString());
		objSetUpModel.setDtLastModified(objPropSetUp3[7].toString());
		objSetUpModel.setClientCode(objPropSetUp3[8].toString());
		if (!(objPropSetUp3[9].toString().equals(" "))) {
			objSetUpModel.setIntqtydec(Integer.parseInt(objPropSetUp3[9].toString()));
		}
		objSetUpModel.setStrRatePickUpFrom(objPropSetUp3[10].toString());
		objSetUpModel.setStrShowReqVal(objPropSetUp3[11].toString());
		objSetUpModel.setStrShowStkReq(objPropSetUp3[12].toString());
		objSetUpModel.setStrShowValMISSlip(objPropSetUp3[13].toString());
		objSetUpModel.setStrChangeUOMTrans(objPropSetUp3[14].toString());
		objSetUpModel.setStrShowProdMaster(objPropSetUp3[15].toString());
		objSetUpModel.setStrAuditFrom(objPropSetUp3[16].toString());
		objSetUpModel.setStrShowProdDoc(objPropSetUp3[17].toString());
		objSetUpModel.setStrAllowDateChangeInMIS(objPropSetUp3[18].toString());
		objSetUpModel.setStrShowTransAsc_Desc(objPropSetUp3[19].toString());
		objSetUpModel.setStrNameChangeProdMast(objPropSetUp3[20].toString());
		objSetUpModel.setStrStkAdjReason(objPropSetUp3[21].toString());
		if (!(objPropSetUp3[22].toString().equals(" "))) {
			objSetUpModel.setIntNotificationTimeinterval(Integer.parseInt(objPropSetUp3[22].toString()));
		}
		objSetUpModel.setStrMonthEnd(objPropSetUp3[23].toString());
		if (!(objPropSetUp3[24] == null)) {
			objSetUpModel.setStrShowAllProdToAllLoc(objPropSetUp3[24].toString());
		}
		if (!(objPropSetUp3[25] == null)) {
			objSetUpModel.setStrLocWiseProductionOrder(objPropSetUp3[25].toString());

		} else {
			objSetUpModel.setStrLocWiseProductionOrder("N");

		}

		objSetUpModel.setStrShowStockInOP(objPropSetUp3[26].toString());
		objSetUpModel.setStrShowAvgQtyInOP(objPropSetUp3[27].toString());
		objSetUpModel.setStrShowStockInSO(objPropSetUp3[28].toString());
		objSetUpModel.setStrShowAvgQtyInSO(objPropSetUp3[29].toString());
		objSetUpModel.setStrDivisionAdd(objPropSetUp3[30].toString());
		objSetUpModel.setStrEffectOfDiscOnPO(objPropSetUp3[31].toString());
		objSetUpModel.setStrInvFormat(objPropSetUp3[32].toString());
		objSetUpModel.setStrECCNo(objPropSetUp3[33].toString());
		objSetUpModel.setStrSMSProvider(objPropSetUp3[34].toString());
		objSetUpModel.setStrSMSAPI(objPropSetUp3[35].toString());
		objSetUpModel.setStrSMSContent(objPropSetUp3[36].toString());
		objSetUpModel.setStrInvNote(objPropSetUp3[37].toString());
		objSetUpModel.setStrCurrencyCode(objPropSetUp3[38].toString());
		objSetUpModel.setStrShowAllPropCustomer(objPropSetUp3[39].toString());
		objSetUpModel.setStrEffectOfInvoice(objPropSetUp3[40].toString());
		objSetUpModel.setStrEffectOfGRNWebBook(objPropSetUp3[41].toString());
		objSetUpModel.setStrMultiCurrency(objPropSetUp3[42].toString());
		objSetUpModel.setStrShowAllPartyToAllLoc(objPropSetUp3[43].toString());


		String sql4 = "SELECT a.strRateHistoryFormat,a.strPOSlipFormat,a.strSRSlipFormat"
				+ " FROM clsPropertySetupModel a where a.strPropertyCode='" + propertyCode + "' and  a.strClientCode ='" + clientCode + "' ";

		Query query4 = sessionFactory.getCurrentSession().createQuery(sql4);
		List list4 = query4.list();
		Object[] objPropSetUp4 = (Object[]) list4.get(0);
		objSetUpModel.setStrRateHistoryFormat(objPropSetUp4[0].toString());
		objSetUpModel.setStrPOSlipFormat(objPropSetUp4[1].toString());
		objSetUpModel.setStrSRSlipFormat(objPropSetUp4[2].toString());
	
	
		return objSetUpModel;

	}

	@Override
	public void funAddUpdatePropertySetupModel(clsPropertySetupModel PropertySetupModel) {
		sessionFactory.getCurrentSession().saveOrUpdate(PropertySetupModel);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<clsWorkFlowModel> funGetWorkFlowModelList(String propertyCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("from clsWorkFlowModel WHERE strPropertyCode=:propertyCode and strCompanyCode=:clientCode");
		query.setParameter("propertyCode", propertyCode);
		query.setParameter("clientCode", clientCode);
		List list = query.list();
		return (List<clsWorkFlowModel>) list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<clsWorkFlowForSlabBasedAuth> funGetWorkFlowForSlabBasedAuthList(String propertyCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("from clsWorkFlowForSlabBasedAuth WHERE strPropertyCode=:propertyCode and strClientCode=:clientCode");
		query.setParameter("propertyCode", propertyCode);
		query.setParameter("clientCode", clientCode);
		List list = query.list();
		return (List<clsWorkFlowForSlabBasedAuth>) list;

	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public clsTCMasterModel funGetTCForSetup(String tcCode, String clientCode) {
		String sql = "from clsTCMasterModel where strTCCode=:tcCode and strClientCode=:clientCode and strApplicable=:applicable";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query.setParameter("tcCode", tcCode);
		query.setParameter("clientCode", clientCode);
		query.setParameter("applicable", "Y");
		List list = query.list();
		if (list.size() > 0) {
			return (clsTCMasterModel) list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<clsCompanyMasterModel> funGetListCompanyMasterModel() {
		String sql = "from clsCompanyMasterModel order by strFinYear asc";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		@SuppressWarnings("unchecked")
		List<clsCompanyMasterModel> list = query.list();
		return (List<clsCompanyMasterModel>) list;
	}

	@Override
	public List<clsCompanyMasterModel> funGetListCompanyMasterModel(String clientCode) {
		String sql = "from clsCompanyMasterModel where strClientCode=:clientCode order by intId desc";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query.setParameter("clientCode", clientCode);
		@SuppressWarnings("unchecked")
		List<clsCompanyMasterModel> list = query.list();
		return (List<clsCompanyMasterModel>) list;
	}

	@Override
	public void funSaveUpdateCompanyLogo(clsCompanyLogoModel comLogo) {
		sessionFactory.getCurrentSession().saveOrUpdate(comLogo);

	}

	@SuppressWarnings("rawtypes")
	@Override
	public clsCompanyLogoModel funGetCompanyLogoObject(String strCompanyCode) {
		String sql = "from clsCompanyLogoModel where strCompanyCode=:strCompanyCode";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query.setParameter("strCompanyCode", strCompanyCode);
		List list = query.list();
		if (!list.isEmpty()) {
			return (clsCompanyLogoModel) query.list().get(0);
		} else {
			return new clsCompanyLogoModel();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<clsTreeMasterModel> funGetAuditForms() {

		Query query = sessionFactory.getCurrentSession().createQuery("from clsTreeMasterModel WHERE strAuditForm='YES' ");
		List list = query.list();
		return (List<clsTreeMasterModel>) list;
	}

}
