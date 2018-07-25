package com.sanguine.crm.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.PreparedStatement;
import com.sanguine.bean.clsPOSItemMasterImportBean;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.crm.model.clsPartyMasterModel;
import com.sanguine.crm.model.clsPartyMasterModel_ID;
import com.sanguine.crm.service.clsDataImportService;
import com.sanguine.crm.service.clsPartyMasterService;
import com.sanguine.model.clsGroupMasterModel;
import com.sanguine.model.clsGroupMasterModel_ID;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.model.clsProductMasterModel_ID;
import com.sanguine.model.clsSubGroupMasterModel;
import com.sanguine.model.clsSubGroupMasterModel_ID;
import com.sanguine.model.clsUOMModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsGroupMasterService;
import com.sanguine.service.clsProductMasterService;
import com.sanguine.service.clsSubGroupMasterService;
import com.sanguine.service.clsUOMService;

/*@Transactional(value = "hibernateTransactionManager")*/
@Controller
public class clsImportData {

	@Autowired
	clsDataImportService objDataImportService;
	clsGlobalFunctions objGlobal;
	@Autowired
	private clsGroupMasterService objGrpMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsGlobalFunctions objGlobalFunctions;
	@Autowired
	private clsSubGroupMasterService objSubGrpMasterService;

	@Autowired
	private clsUOMService objclsUOMService;
	@Autowired
	private clsProductMasterService objProductMasterService;

	@Autowired
	private clsPartyMasterService objPartyMasterService;

	Map<String, String> mapSubGrp = new HashMap<String, String>();
	String urlHits = "1";

	@RequestMapping(value = "/frmDataImport", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		String clientCode = request.getSession().getAttribute("clientCode").toString();

		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmDataImport_1", "command", new clsPOSItemMasterImportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmDataImport", "command", new clsPOSItemMasterImportBean());
		} else {
			return null;
		}

	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/saveDataImport", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSItemMasterImportBean objBean, BindingResult result, HttpServletRequest req) {
		// variables
		String strDBPath = objBean.getStrDBName();
		Connection connection = null;
		Statement statement = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null, rsUOM = null, rsProduct = null, rsCustomer = null;
		try {

			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		} catch (ClassNotFoundException cnfex) {

			System.out.println("Problem in loading or " + "registering MS Access JDBC driver");
			cnfex.printStackTrace();
		}
		try {
			objDataImportService.funExecuteQuery("truncate table tblproductmaster");
			objDataImportService.funExecuteQuery("truncate table tblsubgroupmaster");
			objDataImportService.funExecuteQuery("truncate table tblgroupmaster");
			objDataImportService.funExecuteQuery("truncate table tbluommaster");
			objDataImportService.funExecuteQuery("truncate table tblpartymaster");
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			objGlobal = new clsGlobalFunctions();

			String msAccDB = strDBPath.replace("\\", "/"); // "D:/DataBase/BalajiDB.accdb";
			String dbURL = "jdbc:ucanaccess://" + msAccDB;
			connection = DriverManager.getConnection(dbURL);
			statement = connection.createStatement();

			resultSet = statement.executeQuery("select Distinct NAME from Itemmast");// where
																						// NAME
																						// not
																						// like
																						// '%Q
																						// %'
																						// And
																						// NAME
																						// not
																						// like
																						// '%
																						// Q%'
			clsGroupMasterModel group;

			System.out.println("getFetchSize   " + resultSet.getFetchSize());
			while (resultSet.next()) {
				System.out.println(resultSet.getString(1));
				clsGroupMasterModel obGroup = funPrepareModel(resultSet.getString(1), userCode, clientCode);
				objGrpMasterService.funAddGroup(obGroup);

				clsSubGroupMasterModel objModel = funPrepareSubGroupModel(resultSet.getString(1), userCode, clientCode, obGroup.getStrGCode());
				objSubGrpMasterService.funAddUpdate(objModel);
			}
			resultSet.close();
			rsUOM = statement.executeQuery("select Distinct UOM from Itemmast");
			clsUOMModel objUom = new clsUOMModel();
			System.out.println("getFetchSize   " + rsUOM.getFetchSize());
			while (rsUOM.next()) {
				objUom.setStrUOMName(rsUOM.getString(1));
				objUom.setStrClientCode(clientCode);
				objclsUOMService.funSaveOrUpdateUOM(objUom);
			}
			rsUOM.close();
			statement.close();
			connection.close();
			double dblUnitPrice = 0, dblCostOfRm = 0;
			String prodName1 = "", prodName2 = "", UOM = "";
			connection = DriverManager.getConnection(dbURL);
			statement = connection.createStatement();
			rsProduct = statement.executeQuery("select Distinct NAME,ITEMDESC,MktRate,UOM,WSRATE from Itemmast");// where
																													// NAME
																													// not
																													// like
																													// '%Q
																													// %'
																													// And
																													// NAME
																													// not
																													// like
																													// '%
																													// Q%'
			System.out.println("getFetchSize   " + rsProduct.getFetchSize());
			clsProductMasterModel objGeneralModel = new clsProductMasterModel();
			while (rsProduct.next()) {
				prodName1 = rsProduct.getString(1).trim();
				prodName2 = rsProduct.getString(2).trim();
				dblUnitPrice = rsProduct.getDouble(3);
				UOM = rsProduct.getString(4);
				dblCostOfRm = rsProduct.getDouble(5);
				objGeneralModel = funPrepareProductModel(userCode, clientCode, prodName1, prodName2, dblUnitPrice, UOM, dblCostOfRm);
				objProductMasterService.funAddUpdateGeneral(objGeneralModel);
			}
			rsProduct.close();
			statement.close();
			connection.close();
			String strCustName = "", Addr = "", mobNo = "", phNo = "";
			clsPartyMasterModel objModel = new clsPartyMasterModel();
			connection = DriverManager.getConnection(dbURL);
			statement = connection.createStatement();
			rsCustomer = statement.executeQuery("select CustomerName,Address,PH1,PH2 from Customer");

			while (rsCustomer.next()) {
				strCustName = rsCustomer.getString(1);
				Addr = rsCustomer.getString(2);
				mobNo = rsCustomer.getString(3);
				phNo = rsCustomer.getString(4);
				objModel = funPrepareCustomerModel(strCustName, Addr, mobNo, phNo, userCode, clientCode);
				objPartyMasterService.funAddUpdate(objModel);
			}
			rsCustomer.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("redirect:/frmDataImport.html?saddr=" + urlHits);
	}

	private clsGroupMasterModel funPrepareModel(String strGrpName, String userCode, String clientCode) {
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsGroupMasterModel group;

		lastNo = objGlobalFunctionsService.funGetLastNo("tblgroupmaster", "GroupMaster", "intGId", clientCode);
		String groupCode = "G" + String.format("%06d", lastNo);
		group = new clsGroupMasterModel(new clsGroupMasterModel_ID(groupCode, clientCode));
		group.setIntGId(lastNo);
		group.setStrUserCreated(userCode);
		group.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

		group.setStrGName(strGrpName.toUpperCase());
		group.setStrGDesc(strGrpName);
		group.setStrUserModified(userCode);
		group.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		return group;
	}

	private clsSubGroupMasterModel funPrepareSubGroupModel(String strSubGrpName, String userCode, String clientCode, String groupCode) {
		long lastNo = 0;
		clsSubGroupMasterModel subgroup;

		lastNo = objGlobalFunctionsService.funGetLastNo("tblsubgroupmaster", "SubGroupMaster", "intSGId", clientCode);
		String subGroupCode = "SG" + String.format("%06d", lastNo);
		subgroup = new clsSubGroupMasterModel(new clsSubGroupMasterModel_ID(subGroupCode, clientCode));
		subgroup.setIntSGId(lastNo);
		subgroup.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		subgroup.setStrUserCreated(userCode);
		mapSubGrp.put(strSubGrpName.toUpperCase().trim(), subGroupCode);
		subgroup.setStrSGName(strSubGrpName.toUpperCase());
		subgroup.setStrExciseChapter("");
		subgroup.setStrSGDesc(strSubGrpName);
		subgroup.setStrGCode(groupCode);
		subgroup.setStrExciseable("");
		subgroup.setStrUserModified(userCode);
		subgroup.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		subgroup.setIntSortingNo(1);
		subgroup.setStrSGDescHeader("");

		return subgroup;
	}

	private clsProductMasterModel funPrepareProductModel(String userCode, String clientCode, String prodName1, String prodName2, double dblUnitPrice, String UOM, double dblCostOfRm) throws IOException {
		long lastNo = 0;
		clsProductMasterModel objModel;

		lastNo = objGlobalFunctionsService.funGetLastNo("tblproductmaster", "ProductMaster", "intId", clientCode);
		String productCode = "P" + String.format("%07d", lastNo);
		objModel = new clsProductMasterModel(new clsProductMasterModel_ID(productCode, clientCode));
		objModel.setIntId(lastNo);
		objModel.setStrUserCreated(userCode);
		objModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

		objModel.setStrProductImage(funBlankBlob());
		// System.out.println("funBlankBlob() --"+funBlankBlob());

		objModel.setStrProdName(prodName1.toUpperCase() + " " + prodName2.toUpperCase());
		objModel.setStrPartNo("");
		objModel.setStrUOM(UOM);
		objModel.setStrSGCode(mapSubGrp.get(prodName1.toUpperCase()));
		objModel.setStrProdType("Procured");
		objModel.setDblCostRM(dblCostOfRm);
		objModel.setDblCostManu(0);
		objModel.setStrLocCode("");
		objModel.setDblOrduptoLvl(0);
		objModel.setDblReorderLvl(0);
		objModel.setStrNotInUse("N");
		objModel.setStrExpDate("N");
		objModel.setStrLotNo("N");
		objModel.setStrRevLevel("N");
		objModel.setStrSlNo("N");
		objModel.setStrForSale("Y");
		objModel.setStrSaleNo("");
		objModel.setStrDesc("");
		objModel.setDblUnitPrice(dblUnitPrice);
		objModel.setStrTaxIndicator(" ");
		objModel.setStrExceedPO("N");
		objModel.setStrStagDel("N");
		objModel.setIntDelPeriod(0);
		objModel.setStrType("");
		objModel.setStrSpecification("N");
		objModel.setDblWeight(0);
		objModel.setStrBomCal("");
		objModel.setStrWtUOM(UOM);
		objModel.setStrCalAmtOn("");
		objModel.setStrClass("");
		objModel.setStrUserModified(userCode);
		objModel.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setDblBatchQty(0);
		objModel.setDblMaxLvl(0);
		objModel.setStrBinNo("");
		objModel.setStrTariffNo("");
		objModel.setDblListPrice(0);
		objModel.setStrRemark("");
		objModel.setStrIssueUOM(UOM);
		objModel.setStrReceivedUOM(UOM);
		objModel.setStrRecipeUOM(UOM);
		objModel.setDblIssueConversion(1);
		objModel.setDblReceiveConversion(1);
		objModel.setDblRecipeConversion(1);
		objModel.setStrSpecification("");
		objModel.setStrNonStockableItem("N");
		objModel.setStrPickMRPForTaxCal("N");

		/*
		 * if(objBean.getDblYieldPer()==0.0) { double yieldper=100.00;
		 * objBean.setDblYieldPer(yieldper);
		 * 
		 * }
		 */
		objModel.setDblYieldPer(100.00);
		objModel.setStrBarCode("");
		objModel.setDblMRP(0);
		objModel.setStrExciseable("");
		objModel.setStrManufacturerCode("");

		return objModel;
	}

	private Blob funBlankBlob() {
		Blob blob = new Blob() {
			@Override
			public void truncate(long len) throws SQLException {
				// TODO Auto-generated method stub
			}

			@Override
			public int setBytes(long pos, byte[] bytes, int offset, int len) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int setBytes(long pos, byte[] bytes) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public OutputStream setBinaryStream(long pos) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public long position(Blob pattern, long start) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public long position(byte[] pattern, long start) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public long length() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public byte[] getBytes(long pos, int length) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public InputStream getBinaryStream(long pos, long length) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public InputStream getBinaryStream() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void free() throws SQLException {
				// TODO Auto-generated method stub

			}
		};
		return blob;
	}

	private clsPartyMasterModel funPrepareCustomerModel(String strCustName, String Addr, String mobNo, String phNo, String userCode, String clientCode) {
		String Addr2 = "";
		if (Addr == null) {
			Addr = "";
		} else if (Addr.length() > 100) {
			String arr[] = Addr.split(" ");
			if (Addr.length() > 200) {
				Addr2 = Addr.substring(99, 199);
			} else {
				Addr2 = Addr.substring(99);
			}
			Addr = Addr.substring(0, 99);

		}
		if (mobNo == null) {
			mobNo = "";
		}
		if (phNo == null) {
			phNo = "";
		}

		long lastNo = 0;
		clsPartyMasterModel objModel;
		lastNo = objGlobalFunctionsService.funGetLastNo("tblpartymaster", "PartyMaster", "intPid", clientCode);
		String PCode = "C" + String.format("%06d", lastNo);
		objModel = new clsPartyMasterModel(new clsPartyMasterModel_ID(PCode, clientCode));
		objModel.setIntPId(lastNo);
		objModel.setStrUserCreated(userCode);
		objModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

		objModel.setStrPName(strCustName);
		objModel.setStrPhone(phNo);
		objModel.setStrMobile(mobNo);
		objModel.setStrFax("");
		objModel.setStrContact("");
		objModel.setStrEmail("");
		objModel.setStrBankName("");
		objModel.setStrBankAdd1("");
		objModel.setStrBankAdd2("");
		objModel.setStrTaxNo1("");
		objModel.setStrTaxNo2("");
		objModel.setStrPmtTerms("");
		objModel.setStrAcCrCode("");
		objModel.setStrMAdd1(Addr);
		objModel.setStrMAdd2(Addr2);
		objModel.setStrMCity("");
		objModel.setStrMPin("");
		objModel.setStrMState("");
		objModel.setStrMCountry("");
		objModel.setStrBAdd1("");
		objModel.setStrBAdd2("");
		objModel.setStrBCity("");
		objModel.setStrBPin("");
		objModel.setStrBState("");
		objModel.setStrBCountry("");
		objModel.setStrSAdd1("");
		objModel.setStrSAdd2("");
		objModel.setStrSCity("");
		objModel.setStrSPin("");
		objModel.setStrSState("");
		objModel.setStrSCountry("");
		objModel.setStrCST("");
		objModel.setStrVAT("");
		objModel.setStrExcise("");
		objModel.setStrServiceTax("");
		objModel.setStrSubType("");
		objModel.setDtExpiryDate("2014-7-30");
		objModel.setStrManualCode("");
		objModel.setStrRegistration("");
		objModel.setStrRange("");
		objModel.setStrDivision("");
		objModel.setStrCommissionerate("");
		objModel.setStrBankAccountNo("");
		objModel.setStrBankABANo("");
		objModel.setIntCreditDays(0);
		objModel.setDblCreditLimit(0);
		objModel.setDblLatePercentage(0);
		objModel.setDblRejectionPercentage(0);
		objModel.setStrIBANNo("");
		objModel.setStrSwiftCode("");
		objModel.setStrCategory("");
		objModel.setStrExcisable("");
		objModel.setStrCategory("");
		objModel.setStrUserModified(userCode);
		objModel.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrUserCreated(userCode);
		objModel.setStrPartyType("");
		objModel.setStrPartyIndi(" ");
		objModel.setDblDiscount(0);
		objModel.setStrOperational("Y");
		objModel.setStrECCNo("");
		objModel.setStrPType("cust");
		objModel.setStrAccManager("");
		objModel.setDtInstallions(objGlobal.funGetDate("yyyy-MM-dd", "01-01-1990"));
		objModel.setStrGSTNo("");
		objModel.setStrDebtorCode("");

		return objModel;
	}

}
