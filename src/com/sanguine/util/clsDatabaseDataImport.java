package com.sanguine.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.bean.clsPOSItemMasterImportBean;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;

@Controller
public class clsDatabaseDataImport {

	Connection con = null;
	Statement st = null;
	ResultSet resultSet = null;

	@Autowired
	private clsGlobalFunctions objGlobal;;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	private Map<String, String> mDteDoc = null;
	private Map<String, String> mDocCode = null;

	@RequestMapping(value = "/frmDatabaseDataImport", method = RequestMethod.GET)
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
			return new ModelAndView("frmDatabaseDataImport_1", "command", new clsPOSItemMasterImportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmDatabaseDataImport", "command", new clsPOSItemMasterImportBean());
		} else {
			return null;
		}

	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/saveImportData", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSItemMasterImportBean objBean, BindingResult result, HttpServletRequest request) {

		String urlHits = "1";

		urlHits = request.getParameter("saddr").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String propCode = request.getSession().getAttribute("propertyCode").toString();
		String user = "super";
		String startDate = request.getSession().getAttribute("startDate").toString();
		String[] spDate = startDate.split("/");
		String day = spDate[0];
		String month = spDate[1];
		String yr = spDate[2];
		String stDate = yr + "-" + month + "-" + day;
		String tableName = objBean.getStrSGName();
		String sqlMs = "", blankTable = "";
		String currDate = objGlobal.funGetCurrentDateTime("yyyy-MM-dd");

		if (currDate.contains(" ")) {
			String currdateTime[] = currDate.split(" ");
			currDate = currdateTime[0];
		}

		String docDate = objGlobal.funGetCurrentDateTime("dd-MM-yyyy");
		String docDt[] = docDate.split(" ");
		docDate = docDt[0];
		long lastNo = 0;
		mDteDoc = new HashMap<String, String>();
		mDocCode = new HashMap<String, String>();
		boolean flgHDSave = false;
		try {

			switch (tableName) {
			case "tblAttributeMaster":

				sqlMs = "Select strAttCode,strAttName,strAttType,strAttDesc,strPAttCode " + " from tblAttributeMaster; ";
				resultSet = st.executeQuery(sqlMs);
				blankTable = " TRUNCATE `tblattributemaster`; ";
				objGlobalFunctionsService.funExcuteQuery(blankTable);
				while (resultSet.next()) {
					lastNo = objGlobalFunctionsService.funGetLastNo("tblattributemaster", "AttributeMaster", "intId", clientCode);
					String AttCode = "A" + String.format("%07d", lastNo);

					String mysql = "Insert into tblattributemaster( strAttCode,intId,strAttName,strAttType,strAttDesc," + " strUserCreated,dtCreatedDate,strUserModified,dtLastModified,strPAttCode,strClientCode )" + " value( '" + AttCode + "' , '" + lastNo + "' , '" + resultSet.getString(2) + "' , '" + resultSet.getString(3) + "' ," + " '" + resultSet.getString(4) + "' , '" + user + "', '"
							+ currDate + "', '" + user + "', '" + currDate + "' ," + " '" + resultSet.getString(5) + "', '" + clientCode + "' ) ";

					objGlobalFunctionsService.funExcuteQuery(mysql);

					String docSql = " INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strClientCode`) " + " value(  '" + resultSet.getString(1) + "','" + AttCode + "' ,'" + tableName + "'  , '" + clientCode + "' ) ";
					objGlobalFunctionsService.funExcuteQuery(docSql);

					lastNo++;

				}
				break;

			case "tblAttValueMaster":

				sqlMs = "Select strAVCode,strAVName,strAttCode,strAVDesc " + " from tblAttValueMaster; ";
				resultSet = st.executeQuery(sqlMs);
				blankTable = " TRUNCATE `tblattvaluemaster`; ";
				objGlobalFunctionsService.funExcuteQuery(blankTable);
				while (resultSet.next()) {
					lastNo = objGlobalFunctionsService.funGetLastNo("tblattvaluemaster", "AttributeValueMaster", "intId", clientCode);
					String AVCode = "AV" + String.format("%07d", lastNo);

					String mysql = "Insert into tblattvaluemaster  (`strAVCode`, `intId`, `strAVName`, `strAttCode`, `strAVDesc`, " + " `strUserCreated`, `dtCreatedDate`, `strUserModified`, `dtLastModified`, `strClientCode`) " + " value( '" + AVCode + "' , '" + lastNo + "' , '" + resultSet.getString(2) + "' , '" + resultSet.getString(3) + "' , '" + resultSet.getString(4) + "' , " + "  '" + user
							+ "', '" + currDate + "', '" + user + "', '" + currDate + "' , '" + clientCode + "' ) ";

					objGlobalFunctionsService.funExcuteQuery(mysql);

					String docSql = " INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strClientCode`) " + " value(  '" + resultSet.getString(1) + "','" + AVCode + "' ,'" + tableName + "'  , '" + clientCode + "' ) ";
					objGlobalFunctionsService.funExcuteQuery(docSql);

					lastNo++;
				}
				break;
			case "tblCharacteristics":

				sqlMs = "Select strCharCode,strCharName,strCharType,strCharDesc " + " from tblCharacteristics; ";
				resultSet = st.executeQuery(sqlMs);
				blankTable = " TRUNCATE `tblcharacteristics`; ";
				objGlobalFunctionsService.funExcuteQuery(blankTable);
				while (resultSet.next()) {
					lastNo = objGlobalFunctionsService.funGetLastNo("tblcharacteristics", "CharacteristicsMaster", "intid", clientCode);
					String CharCode = "C" + String.format("%07d", lastNo);

					String mysql = "Insert into tblcharacteristics  (`strCharCode`, `intId`, `strCharName`, `strCharType`, `strCharDesc`, " + " `strUserCreated`, `dtCreatedDate`, `strUserModified`, `dtLastModified`, `strClientCode`) " + " value( '" + CharCode + "' , '" + lastNo + "' , '" + resultSet.getString(2) + "' , '" + resultSet.getString(3) + "' , '" + resultSet.getString(4) + "' ," + "  '"
							+ user + "', '" + currDate + "', '" + user + "', '" + currDate + "' , '" + clientCode + "' ) ";

					objGlobalFunctionsService.funExcuteQuery(mysql);

					String docSql = " INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strClientCode`) " + " value(  '" + resultSet.getString(1) + "','" + CharCode + "' ,'" + tableName + "'  , '" + clientCode + "' ) ";
					objGlobalFunctionsService.funExcuteQuery(docSql);

					lastNo++;
				}
				break;

			case "tblGroupMaster":

				sqlMs = "Select strGCode,strGName,strGDesc " + " from tblGroupMaster; ";
				resultSet = st.executeQuery(sqlMs);
				blankTable = " TRUNCATE `tblgroupmaster`; ";
				objGlobalFunctionsService.funExcuteQuery(blankTable);
				while (resultSet.next()) {
					lastNo = objGlobalFunctionsService.funGetLastNo("tblgroupmaster", "GroupMaster", "intGId", clientCode);
					String groupCode = "G" + String.format("%06d", lastNo);

					String mysql = "Insert into tblgroupmaster  (`strGCode`, `intGId`, `strGName`, `strGDesc`," + "  `strUserCreated`, `dtCreatedDate`, `strUserModified`, `dtLastModified`, `strClientCode`) " + " value( '" + groupCode + "' , '" + lastNo + "' , '" + resultSet.getString(2) + "' , '" + resultSet.getString(3) + "' ," + "  '" + user + "', '" + currDate + "', '" + user + "', '"
							+ currDate + "' , '" + clientCode + "' ) ";

					objGlobalFunctionsService.funExcuteQuery(mysql);

					String docSql = " INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strClientCode`) " + " value(  '" + resultSet.getString(1) + "','" + groupCode + "' ,'" + tableName + "'  , '" + clientCode + "' ) ";
					objGlobalFunctionsService.funExcuteQuery(docSql);

					lastNo++;
				}
				break;

			case "tblLocationMaster":

				sqlMs = "Select strLocCode,strLocName,strLocDesc,strAvlForSale,strActive,strPickable,strReceiveable " + " from tblLocationMaster; ";
				resultSet = st.executeQuery(sqlMs);
				blankTable = " TRUNCATE `tbllocationmaster`; ";
				objGlobalFunctionsService.funExcuteQuery(blankTable);
				while (resultSet.next()) {
					lastNo = objGlobalFunctionsService.funGetLastNo("tbllocationmaster", "LocationeMaster", "intid", clientCode);
					String LocCode = "L" + String.format("%06d", lastNo);

					String mysql = "Insert into tbllocationmaster  (`strLocCode`, `intId`, `strLocName`, `strLocDesc`, `strAvlForSale`, `strActive`, `strPickable`, `strReceivable`," + "  `strUserCreated`, `dtCreatedDate`, `strUserModified`, `dtLastModified`, `strExciseNo`," + " `strType`, `strPropertyCode`, `strMonthEnd`, `strExternalCode`, `strClientCode`, `strLocPropertyCode`) " + " value( '"
							+ LocCode + "' , '" + lastNo + "' , '" + resultSet.getString(2) + "' , '" + resultSet.getString(3) + "' , '" + resultSet.getString(4) + "' " + " , '" + resultSet.getString(5) + "' , '" + resultSet.getString(6) + "' , '" + resultSet.getString(7) + "' , " + "  '" + user + "', '" + currDate + "', '" + user + "', '" + currDate + "' ,'','','','','' ," + " '" + clientCode
							+ "','' ) ";

					objGlobalFunctionsService.funExcuteQuery(mysql);

					String docSql = " INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strClientCode`) " + " value(  '" + resultSet.getString(1) + "','" + LocCode + "' ,'" + tableName + "'  , '" + clientCode + "' ) ";
					objGlobalFunctionsService.funExcuteQuery(docSql);

					lastNo++;
				}
				break;

			case "tblPartyMaster":
				String dtExp = "2000-01-01 00:00:00";

				sqlMs = "Select strPCode,strPName,strPhone,strMobile,strFax,strContact,strEmail, " + " strPType,strBankName,strBankAdd1,strBankAdd2,strTaxNo1,strTaxNo2,strPartyType," + "intCreditDays, dblCreditLimit,strPmtTerms,strAcCrCode,strMAdd1,strMAdd2,strMCity,strMPin,strMState" + ",strMCountry,strBAdd1,strBAdd2,strBCity,strBPin,strBState,strBCountry,strSAdd1,"
						+ " strSAdd2,strSCity,strSPin,strSState,strSCountry,strCST," + " strVAT,strExcise,strServiceTax,strSubType,dtExpiryDate  " + " from tblPartyMaster; ";
				resultSet = st.executeQuery(sqlMs);

				blankTable = " TRUNCATE `tblpartymaster`; ";
				objGlobalFunctionsService.funExcuteQuery(blankTable);
				while (resultSet.next()) {
					lastNo = objGlobalFunctionsService.funGetLastNo("tblpartymaster", "SupplierMaster", "intPid", clientCode);
					String PCode = "S" + String.format("%06d", lastNo);

					if (resultSet.getString(42) == null || resultSet.getString(42).toString().equals("")) {
						dtExp = "2000-01-01 00:00:00";
					} else {
						dtExp = resultSet.getString(42).toString();
					}

					String mysql = "Insert into tblpartymaster  (`strPCode`, `intPId`, `strPName`, `strPhone`, `strMobile`, `strFax`, `strContact`, `strEmail`," + " `strPType`, `strBankName`, `strBankAdd1`, `strBankAdd2`, `strTaxNo1`, `strTaxNo2`, " + " `strPartyType`, `intCreditDays`, `dblCreditLimit`, `strAcCrCode`, `strMAdd1`, `strMAdd2`, "
							+ " `strMCity`, `strMPin`, `strMState`, `strMCountry`, `strBAdd1`, `strBAdd2`, " + " `strBCity`, `strBPin`, `strBState`, `strBCountry`, `strSAdd1`, " + " `strSAdd2`, `strSCity`, `strSPin`, `strSState`, `strSCountry`, `strCST`, " + " `strVAT`, `strExcise`, `strServiceTax`, `strSubType`,"
							+ " `strUserCreated`, `dtCreatedDate`, `strUserModified`, `dtLastModified`, `dtExpiryDate`,"
							// +
							// " `strManualCode`, `strRegistration`, `strRange`, `strDivision`, `strCommissionerate`, `strBankAccountNo`,"
							// +
							// " `strBankABANo`, `strIBANNo`, `strSwiftCode`, `strCategory` ,"
							// + " `strExcisable`, "
							+ " `strClientCode` ) " + " value( '"
							+ PCode + "' , '" + lastNo + "' , '" + funCheckSymbol(resultSet.getString(2)) + "' , '" + resultSet.getString(3) + "' , '" + resultSet.getString(4) + "' " + " , '" + resultSet.getString(5) + "' , '" + resultSet.getString(6) + "' , '" + resultSet.getString(7) + "'  "

							+ " , '" + resultSet.getString(8) + "' , '" + resultSet.getString(9) + "' , '" + resultSet.getString(10) + "'  " + " , '" + resultSet.getString(11) + "' , '" + resultSet.getString(12) + "' , '" + resultSet.getString(13) + "'  "

							+ " , '" + resultSet.getString(14) + "' , '" + resultSet.getInt(15) + "' , '" + resultSet.getDouble(16) + "'  " + " , '" + resultSet.getString(17) + "'  , '" + resultSet.getString(19) + "'  " + " , '" + funCheckJunkChar(resultSet.getString(20)) + "' , '" + resultSet.getString(21) + "' , '" + resultSet.getString(22) + "'  "

							+ " , '" + resultSet.getString(23) + "' , '" + resultSet.getString(24) + "' , '" + resultSet.getString(25) + "'  " + " , '" + resultSet.getString(26) + "' , '" + resultSet.getString(27) + "' , '" + resultSet.getString(28) + "'  "

							+ " , '" + resultSet.getString(29) + "' , '" + resultSet.getString(30) + "' , '" + resultSet.getString(31) + "'  " + " , '" + resultSet.getString(32) + "' , '" + resultSet.getString(33) + "' , '" + resultSet.getString(34) + "'  "

							+ " , '" + resultSet.getString(36) + "' , '" + resultSet.getString(37) + "' , '" + resultSet.getString(38) + "'  " + " , '" + resultSet.getString(39) + "' , '" + resultSet.getString(40) + "' ,'','', " + "  '" + user + "', '" + currDate + "', '" + user + "', '" + currDate + "' , '" + dtExp + "' , '" + clientCode + "'  ) ";

					objGlobalFunctionsService.funExcuteQuery(mysql);

					String docSql = " INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strClientCode`) " + " value(  '" + resultSet.getString(1) + "','" + PCode + "' ,'" + tableName + "'  , '" + clientCode + "' ) ";
					objGlobalFunctionsService.funExcuteQuery(docSql);

					lastNo++;
				}
				break;

			case "tblProcessMaster":

				sqlMs = "Select strProcessCode,strProcessName,strDesc " + " from tblProcessMaster; ";
				resultSet = st.executeQuery(sqlMs);
				blankTable = " TRUNCATE `tblprocessmaster`; ";
				objGlobalFunctionsService.funExcuteQuery(blankTable);
				while (resultSet.next()) {
					lastNo = objGlobalFunctionsService.funGetLastNo("tblprocessMaster", "ProcessMaster", "intId", clientCode);
					String processCode = "PR" + String.format("%06d", lastNo);

					String mysql = "Insert into tblprocessMaster  (`strProcessCode`, `intId`, `strProcessName`, `strDesc`," + "  `strUserCreated`, `dtCreatedDate`, `strUserModified`, `dtLastModified`, `strClientCode`) " + " value( '" + processCode + "' , '" + lastNo + "' , '" + resultSet.getString(2) + "' , '" + resultSet.getString(3) + "',  " + "  '" + user + "', '" + currDate + "', '" + user
							+ "', '" + currDate + "' , '" + clientCode + "' ) ";

					objGlobalFunctionsService.funExcuteQuery(mysql);

					String docSql = " INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strClientCode`) " + " value(  '" + resultSet.getString(1) + "','" + processCode + "' ,'" + tableName + "'  , '" + clientCode + "' ) ";
					objGlobalFunctionsService.funExcuteQuery(docSql);

					lastNo++;
				}
				break;

			// case "tblProdAttMaster":
			// //dbl att
			// sqlMs = "Select strProdCode,strAttCode,strAVCode,strAttValue "
			// + " from tblProdAttMaster; ";
			// resultSet=st.executeQuery(sqlMs);
			// blankTable=" TRUNCATE `tblprodattmaster`; ";
			// objGlobalFunctionsService.funExcuteQuery(blankTable);
			// while(resultSet.next())
			// {
			// String mysql =
			// " INSERT INTO `tblprodattmaster` (`strProdCode`, `strAttCode`, `strAVCode`, `dblAttValue`, `strClientCode`, `intId`) "
			// +
			// " value( '"+resultSet.getString(1)+"' , '"+resultSet.getString(2)+"' , '"+resultSet.getString(3)+"', '"+resultSet.getString(4)+"','"+clientCode+"', '"+lastNo+"' ) ";
			// objGlobalFunctionsService.funExcuteQuery(mysql);
			//
			// String
			// docSql=" INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strClientCode`,`strSubCode1`) "
			// +
			// " value(  '"+resultSet.getString(1)+"','"+resultSet.getString(1)+"' ,'"+tableName+"'  , '"+clientCode+"' , '"+resultSet.getString(3)+"' ) ";
			// objGlobalFunctionsService.funExcuteQuery(docSql);
			// lastNo++;
			// }
			// break;

			case "tblProdChar":
				String value = "0.00";
				sqlMs = "Select strProdCode,strCharCode,strValue " + " from tblProdChar; ";
				resultSet = st.executeQuery(sqlMs);
				blankTable = " TRUNCATE `tblprodchar`; ";
				objGlobalFunctionsService.funExcuteQuery(blankTable);
				while (resultSet.next()) {
					if (resultSet.getString(3) == null || resultSet.getString(3).equals("")) {
						value = "0.00";
					} else {
						value = resultSet.getString(3);
					}

					String mysql = " INSERT INTO `tblprodchar` (`strProdCode`, `strCharCode`, `dblValue`, `strClientCode` ) " + " value( '" + resultSet.getString(1) + "' , '" + resultSet.getString(2) + "' , '" + value + "','" + clientCode + "' ) ";

					objGlobalFunctionsService.funExcuteQuery(mysql);

					String docSql = " INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strClientCode`) " + " value(  '" + resultSet.getString(1) + "','" + resultSet.getString(1) + "' ,'" + tableName + "'  , '" + clientCode + "' ) ";
					objGlobalFunctionsService.funExcuteQuery(docSql);

					lastNo++;
				}
				break;

			case "tblProdProcess":

				sqlMs = "Select strProdCode,strProcessCode,intLevel,dblWeight " + " from tblProdProcess; ";
				resultSet = st.executeQuery(sqlMs);
				blankTable = " TRUNCATE `tblprodprocess`; ";
				objGlobalFunctionsService.funExcuteQuery(blankTable);
				lastNo = 1;
				while (resultSet.next()) {
					String mysql = " INSERT INTO `tblprodprocess` (`strProdCode`, `intId`, `strProcessCode`, `intLevel`,`dblWeight`,`strClientCode` ) " + " value( '" + resultSet.getString(1) + "' , '" + lastNo + "' , '" + resultSet.getString(2) + "','" + resultSet.getInt(3) + "','" + resultSet.getDouble(4) + "','" + clientCode + "' ) ";
					objGlobalFunctionsService.funExcuteQuery(mysql);

					String docSql = " INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strClientCode`) " + " value(  '" + resultSet.getString(1) + "','" + resultSet.getString(1) + "' ,'" + tableName + "'  , '" + clientCode + "' ) ";
					objGlobalFunctionsService.funExcuteQuery(docSql);

					lastNo++;
				}
				lastNo = 0;
				break;

			case "tblProdSuppMaster":

				sqlMs = "Select strSuppCode,strProdCode,dblLastCost,strUOM,dtLastDate,strLeadTime,dblMaxQty,strDefault  " + " from tblProdSuppMaster; ";
				resultSet = st.executeQuery(sqlMs);
				blankTable = " TRUNCATE `tblprodsuppmaster`; ";
				objGlobalFunctionsService.funExcuteQuery(blankTable);
				while (resultSet.next()) {
					String mysql = " INSERT INTO `tblprodsuppmaster` (`strSuppCode`, `strProdCode`, `dblLastCost`, `strSuppUOM`, `dtLastDate`, `strLeadTime`, `dblMaxQty`, `strDefault`, `strClientCode`) " + " value( '" + resultSet.getString(1) + "' ,'" + resultSet.getString(2) + "','" + resultSet.getDouble(3) + "','" + resultSet.getString(4) + "', " + " '" + resultSet.getString(5) + "','"
							+ resultSet.getString(6) + "','" + resultSet.getString(7) + "', " + " '" + resultSet.getString(8) + "','" + clientCode + "' ) ";
					objGlobalFunctionsService.funExcuteQuery(mysql);

					String docSql = " INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strClientCode`) " + " value(  '" + resultSet.getString(1) + "','" + resultSet.getString(1) + "' ,'" + tableName + "'  , '" + clientCode + "' ) ";
					objGlobalFunctionsService.funExcuteQuery(docSql);

					lastNo++;
				}
				break;

			case "tblSettlementMaster":

				sqlMs = "Select strSettlementCode,strSettlementDesc,strSettlementType,strApplicable  " + " from tblSettlementMaster; ";
				resultSet = st.executeQuery(sqlMs);
				blankTable = " TRUNCATE `tblsettlementmaster`; ";
				objGlobalFunctionsService.funExcuteQuery(blankTable);
				while (resultSet.next()) {
					String mysql = " INSERT INTO `tblsettlementmaster` (`strSettlementCode`, `intid`, `strSettlementDesc`, `strSettlementType`, `strApplicable`, `strUserCreated`, `dtCreatedDate`, `strUserModified`,`dtLastModified`,`strClientCode`) " + " value( '" + resultSet.getString(1) + "' ,'" + lastNo + "','" + resultSet.getString(2) + "','" + resultSet.getString(3) + "','"
							+ resultSet.getString(4) + "', " + "  '" + user + "', '" + currDate + "', '" + user + "', '" + currDate + "' , '" + clientCode + "' ) ";
					objGlobalFunctionsService.funExcuteQuery(mysql);

					String docSql = " INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strClientCode`) " + " value(  '" + resultSet.getString(1) + "','" + resultSet.getString(1) + "' ,'" + tableName + "'  , '" + clientCode + "' ) ";
					objGlobalFunctionsService.funExcuteQuery(docSql);

					lastNo++;
				}
				break;

			case "tblProductMaster":

				sqlMs = "Select strProdCode,strPartNo,strProdName,strUOM, strSGCode,strProdType, " + " dblCostRM,dblCostManu,strLocCode,dblOrduptoLvl,dblReorderLvl,strNotInUse, " + " strExpDate,strLotNo,strRevLevel,strSlNo,strForSale,strSaleNo, " + " strDesc,dblUnitPrice,strTaxIndicator,strExceedPO,strStagDel,intDelPeriod, "
						+ " strType,strSpecification,strImagePath,dblWeight,strBomCal,strWtUOM, " + " strCalAmtOn,dtMovCust,strSelectYear,dtExpiryDate " + " from tblProductMaster; ";
				resultSet = st.executeQuery(sqlMs);
				blankTable = " TRUNCATE `tblproductmaster`; ";
				objGlobalFunctionsService.funExcuteQuery(blankTable);
				while (resultSet.next()) {
					lastNo = objGlobalFunctionsService.funGetLastNo("tblproductmaster", "ProductMaster", "intId", clientCode);
					String productCode = "P" + String.format("%07d", lastNo);
					String mysql = " INSERT INTO `tblproductmaster` (`intId`, `strProdCode`, `strPartNo`, `strProdName`, `strUOM`, `strSGCode`, `strProdType`," + " `dblCostRM`, `dblCostManu`, `strLocCode`, `dblOrduptoLvl`, `dblReorderLvl`, `strNotInUse`," + " `strExpDate`, `strLotNo`, `strRevLevel`, `strSlNo`, `strForSale`, `strSaleNo`,"
							+ " `strDesc`, `dblUnitPrice`, `strTaxIndicator`, `strExceedPO`, `strStagDel`," + " `intDelPeriod`, `strType`, `strSpecification`, `strImagePath`, `dblWeight`," + " `strBomCal`, `strWtUOM`, `strCalAmtOn`," + " `strUserCreated`, `dtCreatedDate`, `strUserModified`," + " `dtLastModified`, `strClientCode`) " + " value( '"
							+ lastNo
							+ "','"
							+ productCode
							+ "','"
							+ funCheckJunkChar(funCheckSymbol(resultSet.getString(2)))
							+ "','"
							+ funCheckSymbol(resultSet.getString(3))
							+ "','"
							+ resultSet.getString(4)
							+ "','"
							+ resultSet.getString(5)
							+ "','"
							+ resultSet.getString(6)
							+ "' "
							+ " ,'"
							+ resultSet.getDouble(7)
							+ "','"
							+ resultSet.getDouble(8)
							+ "','"
							+ resultSet.getString(9)
							+ "','"
							+ resultSet.getFloat(10)
							+ "','"
							+ resultSet.getFloat(11)
							+ "','"
							+ resultSet.getString(12)
							+ "' "
							+ " ,'"
							+ resultSet.getString(13)
							+ "','"
							+ resultSet.getString(14)
							+ "','"
							+ resultSet.getString(15)
							+ "','"
							+ resultSet.getString(16)
							+ "','"
							+ resultSet.getString(17)
							+ "','"
							+ resultSet.getString(18)
							+ " '"
							+ " ,'"
							+ resultSet.getString(19)
							+ "','"
							+ resultSet.getDouble(20)
							+ "','"
							+ resultSet.getString(21)
							+ "','"
							+ resultSet.getString(22)
							+ "','"
							+ resultSet.getString(23)
							+ "','"
							+ resultSet.getInt(24)
							+ "' "
							+ " ,'"
							+ resultSet.getString(25)
							+ "','"
							+ funCheckSymbol(resultSet.getString(26))
							+ "','"
							+ resultSet.getString(27)
							+ "','"
							+ resultSet.getFloat(28)
							+ "','"
							+ resultSet.getString(29)
							+ "','"
							+ resultSet.getString(30) + "' " + "  ,'" + resultSet.getString(31) + "', '" + user + "', '" + currDate + "', '" + user + "', '" + currDate + "' , '" + clientCode + "' ) ";
					System.out.print(mysql);
					objGlobalFunctionsService.funExcuteQuery(mysql);

					String docSql = " INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strClientCode`) " + " value(  '" + resultSet.getString(1) + "','" + productCode + "' ,'" + tableName + "'  , '" + clientCode + "' ) ";
					objGlobalFunctionsService.funExcuteQuery(docSql);

					lastNo++;
				}
				break;

			case "tblSubGroupMaster":

				sqlMs = "Select strSGCode,strSGName,strSGDesc,strGCode " + " from tblSubGroupMaster; ";
				resultSet = st.executeQuery(sqlMs);
				blankTable = " TRUNCATE `tblsubgroupmaster`; ";
				objGlobalFunctionsService.funExcuteQuery(blankTable);
				while (resultSet.next()) {
					lastNo = objGlobalFunctionsService.funGetLastNo("tblsubgroupmaster", "SubGroupMaster", "intSGId", clientCode);
					String subGroupCode = "SG" + String.format("%06d", lastNo);
					String mysql = " INSERT INTO `tblsubgroupmaster` (`strSGCode`, `intSGId`, `strSGName`, `strSGDesc`," + "  `strGCode`, `strUserCreated`, `dtCreatedDate`, `strUserModified`, " + " `dtLastModified`, `strClientCode`) " + " value( '" + subGroupCode + "' ,'" + lastNo + "', '" + resultSet.getString(2) + "' ," + " '" + resultSet.getString(3) + "','" + resultSet.getString(4) + "', '"
							+ user + "', '" + currDate + "', " + "'" + user + "', '" + currDate + "' ,'" + clientCode + "' ) ";

					objGlobalFunctionsService.funExcuteQuery(mysql);

					String docSql = " INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strClientCode`) " + " value(  '" + resultSet.getString(1) + "','" + subGroupCode + "' ,'" + tableName + "'  , '" + clientCode + "' ) ";
					objGlobalFunctionsService.funExcuteQuery(docSql);

					lastNo++;
				}
				break;

			case "tblTaxHd":

				sqlMs = "Select strTaxCode,strTaxDesc,strTaxOnSP,strTaxType,dblPercent,dblAmount," + " strTaxOnDG,dtValidFrom,dtValidTo,strTaxRounded,strTaxOnTax,strTaxIndicator, " + " strTaxCalculation " + " from tblTaxHd; ";
				resultSet = st.executeQuery(sqlMs);
				blankTable = " TRUNCATE `tbltaxhd`; ";
				objGlobalFunctionsService.funExcuteQuery(blankTable);
				while (resultSet.next()) {
					lastNo = objGlobalFunctionsService.funGetLastNo("tbltaxhd", "TaxMaster", "intId", clientCode);
					String code = "T" + String.format("%07d", lastNo);
					String mysql = " INSERT INTO `tbltaxhd` (`strTaxCode`, `intId`, `strTaxDesc`, `strTaxOnSP`, `strTaxType`, `dblPercent`," + "  `dblAmount`, `strTaxOnGD`, `dtValidFrom`, `dtValidTo`, `strTaxRounded`, `strTaxOnTax`," + " `strTaxIndicator`, `strTaxCalculation`, `strUserCreated`, `dtCreatedDate`, `strUserModified`, `dtLastModified`,`strClientCode`) " + " value( '" + code + "' ,'"
							+ lastNo + "', '" + resultSet.getString(2) + "' , '" + resultSet.getString(3) + "','" + resultSet.getString(4) + "'," + " '" + resultSet.getDouble(5) + "','" + resultSet.getDouble(6) + "','" + resultSet.getString(7) + "','" + resultSet.getString(8) + "','" + resultSet.getString(9) + "','" + resultSet.getString(10) + "','" + resultSet.getString(11) + "','"
							+ resultSet.getString(12) + "','" + resultSet.getString(13) + "'," + " '" + user + "', '" + currDate + "', '" + user + "', '" + currDate + "' ,'" + clientCode + "' ) ";

					objGlobalFunctionsService.funExcuteQuery(mysql);
					mDocCode.put(resultSet.getString(1), code + "," + resultSet.getString(1));
					String docSql = " INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strClientCode`,`strTableType`) " + " value(  '" + resultSet.getString(1) + "','" + code + "' ,'" + tableName + "'  , '" + clientCode + "','HD' ) ";
					objGlobalFunctionsService.funExcuteQuery(docSql);

					lastNo++;
					flgHDSave = true;
				}

				if (flgHDSave) {

					sqlMs = "Select strTaxCode,strType,dblFrom,dblTo,dblValue" + " from tblTaxDtl; ";
					resultSet = st.executeQuery(sqlMs);
					blankTable = " TRUNCATE `tbltaxdtl`; ";
					objGlobalFunctionsService.funExcuteQuery(blankTable);
					while (resultSet.next()) {
						String doc = mDocCode.get(resultSet.getString(1));
						if (doc != null) {
							String codes[] = doc.split(",");
							String newCode = codes[0];
							String oldCode = codes[1];
							if (oldCode.equals(resultSet.getString(1))) {
								String mysql = " INSERT INTO `tbltaxdtl` (`strTaxCode`, `strType`, `dblFrom`, `dblTo`, `dblValue` ) " + " value( '" + newCode + "' , '" + resultSet.getString(2) + "' , '" + resultSet.getString(3) + "','" + resultSet.getDouble(4) + "'," + " '" + resultSet.getDouble(5) + "' ) ";

								objGlobalFunctionsService.funExcuteQuery(mysql);

								String docSql = " INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strClientCode`,`strTableType`) " + " value(  '" + resultSet.getString(1) + "','" + newCode + "' ,'" + tableName + "'  , '" + clientCode + "','Dtl' ) ";
								objGlobalFunctionsService.funExcuteQuery(docSql);
								lastNo++;
							}
						}

					}
					mDocCode.clear();
				}

				break;

			// case "tblTaxDtl":
			//
			// sqlMs = "Select strTaxCode,strType,dblFrom,dblTo,dblValue"
			// + " from tblTaxDtl; ";
			// resultSet=st.executeQuery(sqlMs);
			// blankTable=" TRUNCATE `tbltaxdtl`; ";
			// objGlobalFunctionsService.funExcuteQuery(blankTable);
			// while(resultSet.next())
			// {
			// String doc =mDocCode.get(resultSet.getString(1));
			// String codes[] = doc.split(",");
			// String newCode =codes[0];
			// String oldCode =codes[1];
			// if(oldCode.equals(resultSet.getString(1)))
			// {
			// String mysql =
			// " INSERT INTO `tbltaxdtl` (`strTaxCode`, `strType`, `dblFrom`, `dblTo`, `dblValue` ) "
			// +
			// " value( '"+newCode+"' , '"+resultSet.getString(2)+"' , '"+resultSet.getString(3)+"','"+resultSet.getDouble(4)+"',"
			// + " '"+resultSet.getDouble(5)+"' ) ";
			//
			// objGlobalFunctionsService.funExcuteQuery(mysql);
			//
			// String
			// docSql=" INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strClientCode`) "
			// +
			// " value(  '"+resultSet.getString(1)+"','"+newCode+"' ,'"+tableName+"'  , '"+clientCode+"' ) ";
			// objGlobalFunctionsService.funExcuteQuery(docSql);
			// lastNo++;
			// }
			//
			// }
			// mDocCode.clear();
			// break;

			case "tblTaxSettlementMaster":

				sqlMs = "Select strTaxCode,strSettlementCode" + " from tblTaxSettlementMaster; ";
				resultSet = st.executeQuery(sqlMs);
				blankTable = " TRUNCATE `tbltaxdtl`; ";
				objGlobalFunctionsService.funExcuteQuery(blankTable);
				while (resultSet.next()) {

					String mysql = " INSERT INTO `tbltaxsettlementmaster` (`intId`,`strSettlementCode`,`strTaxCode` ) " + " value( '" + lastNo + "','" + resultSet.getString(2) + "' , '" + resultSet.getString(1) + "' ) ";

					objGlobalFunctionsService.funExcuteQuery(mysql);

					String docSql = " INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strClientCode`) " + " value(  '" + resultSet.getString(1) + "','" + resultSet.getString(1) + "' ,'" + tableName + "'  , '" + clientCode + "' ) ";
					objGlobalFunctionsService.funExcuteQuery(docSql);

					lastNo++;
				}
				break;

			case "tblBOMMasterHd":

				sqlMs = "Select strBOMCode,strParentCode ,strProcessCode,dtValidFrom,dtValidTo" + " from tblBOMMasterHd; ";
				resultSet = st.executeQuery(sqlMs);
				blankTable = " TRUNCATE `tblbommasterhd`; ";
				objGlobalFunctionsService.funExcuteQuery(blankTable);
				while (resultSet.next()) {
					lastNo = objGlobalFunctionsService.funGetLastNo("tblbommasterhd", "BOMMaster", "intId", clientCode);
					String code = "B" + String.format("%07d", lastNo);

					String mysql = " INSERT INTO `tblbommasterhd` (`strBOMCode`,`intId`,`strParentCode`,`strProcessCode`,`dtValidFrom`,`dtValidTo`,`strUserCreated`,`dtCreatedDate`,`strUserModified`,`dtLastModified`,`strClientCode`  ) " + " value( '" + code + "','" + lastNo + "','" + resultSet.getString(2) + "' , '" + resultSet.getString(3) + "' , '" + resultSet.getString(4) + "' , '"
							+ resultSet.getString(5) + "', " + " '" + user + "', '" + currDate + "', '" + user + "', '" + currDate + "' ,'" + clientCode + "' ) ";

					objGlobalFunctionsService.funExcuteQuery(mysql);

					String docSql = " INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strSubCode1`,`strSubCode2`,`strClientCode`,`strTableType`) " + " value(  '" + resultSet.getString(1) + "','" + code + "' ,'" + tableName + "'  ,'" + resultSet.getString(2) + "','" + resultSet.getString(3) + "', '" + clientCode + "','hd' ) ";
					objGlobalFunctionsService.funExcuteQuery(docSql);

					lastNo++;
					mDocCode.put(resultSet.getString(1), code + "," + resultSet.getString(1));
					flgHDSave = true;
				}

				if (flgHDSave) {
					sqlMs = "Select strBOMCode,strChildCode ,dblQty,dblWeight " + " from tblBOMMasterDtl; ";
					resultSet = st.executeQuery(sqlMs);
					blankTable = " TRUNCATE `tblbommasterdtl`; ";
					objGlobalFunctionsService.funExcuteQuery(blankTable);
					lastNo = 1;
					while (resultSet.next()) {

						String doc = mDocCode.get(resultSet.getString(1));
						if (doc != null) {
							String codes[] = doc.split(",");
							String newCode = codes[0];
							String oldCode = codes[1];
							if (oldCode.equals(resultSet.getString(1))) {
								String mysql = " INSERT INTO `tblbommasterdtl` (`strBOMCode`,`intId`,`strChildCode`,`dblQty`,`dblWeight`,`strClientCode` ) " + " value( '" + newCode + "','" + lastNo + "','" + resultSet.getString(2) + "' , '" + resultSet.getString(3) + "' , '" + resultSet.getString(4) + "' , '" + clientCode + "' ) ";

								objGlobalFunctionsService.funExcuteQuery(mysql);

								String docSql = " INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strSubCode1`,`strClientCode`,`strTableType`) " + " value(  '" + resultSet.getString(1) + "','" + newCode + "' ,'" + tableName + "'  ,'" + resultSet.getString(2) + "', '" + clientCode + "','dtl' ) ";
								objGlobalFunctionsService.funExcuteQuery(docSql);

								lastNo++;

							}

						}
					}

				}
				mDocCode.clear();
				break;

			// case "tblBOMMasterDtl":
			//
			// sqlMs = "Select strBOMCode,strChildCode ,dblQty,dblWeight "
			// + " from tblBOMMasterDtl; ";
			// resultSet=st.executeQuery(sqlMs);
			// blankTable=" TRUNCATE `tblbommasterdtl`; ";
			// objGlobalFunctionsService.funExcuteQuery(blankTable);
			// lastNo=1;
			// while(resultSet.next())
			// {
			// String mysql =
			// " INSERT INTO `tblbommasterdtl` (`strBOMCode`,`intId`,`strChildCode`,`dblQty`,`dblWeight`,`strClientCode` ) "
			// +
			// " value( '"+resultSet.getString(1)+"','"+lastNo+"','"+resultSet.getString(2)+"' , '"+resultSet.getString(3)+"' , '"+resultSet.getString(4)+"' , '"+clientCode+"' ) ";
			//
			//
			// objGlobalFunctionsService.funExcuteQuery(mysql);
			//
			// String
			// docSql=" INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strSubCode1`,`strClientCode`,`strTableType`) "
			// +
			// " value(  '"+resultSet.getString(1)+"','' ,'"+tableName+"'  ,'"+resultSet.getString(2)+"', '"+clientCode+"','dtl' ) ";
			// objGlobalFunctionsService.funExcuteQuery(docSql);
			//
			// lastNo++;
			// }
			// lastNo=0;
			// break;

			// // transcation table////////////

			case "tblDeliveryChallanHd":

				sqlMs = "Select strDCCode,dtDCDate ,strAgainst,strSOCode,strCustCode,strPONo,strNarration,strPackNo,strLocCode,strVehNo,strMInBy,strTimeInOut,strNo " + " from tblDeliveryChallanHd; ";
				resultSet = st.executeQuery(sqlMs);
				blankTable = " TRUNCATE `tbldeliverychallanhd`; ";
				objGlobalFunctionsService.funExcuteQuery(blankTable);
				lastNo = 1;
				while (resultSet.next()) {
					lastNo = objGlobalFunctionsService.funGetLastNo("tbldeliverychallanhd", "DCCode", "intId", clientCode);

					String year = objGlobal.funGetSplitedDate(startDate)[2];
					String cd = objGlobal.funGetTransactionCode("DC", propCode, year);
					String strDCCode = cd + String.format("%06d", lastNo);

					String mysql = " INSERT INTO `tbldeliverychallanhd` (`strDCCode`,`intId`,`dteDCDate`,`strAgainst`,`strSOCode`,`strCustCode`,`strPONo`," + " `strNarration`,`strPackNo`,`strLocCode`,`strVehNo`,`strMInBy`,`strTimeInOut`,`strSerialNo`," + " `strUserCreated`,`dteCreatedDate`,`strUserModified`,`dteLastModified`,`strClientCode` ) " + " value( '" + strDCCode + "','" + lastNo + "','"
							+ resultSet.getString(2) + "' , '" + resultSet.getString(3) + "' , '" + resultSet.getString(4) + "' ," + " '" + resultSet.getString(5) + "','" + resultSet.getString(6) + "','" + resultSet.getString(7) + "','" + resultSet.getString(8) + "'," + " '" + resultSet.getString(9) + "','" + resultSet.getString(10) + "','" + resultSet.getString(11) + "','"
							+ resultSet.getString(12) + "'," + " '" + resultSet.getString(13) + "'," + " '" + user + "', '" + currDate + "', '" + user + "', '" + currDate + "' ,'" + clientCode + "' ) ";

					objGlobalFunctionsService.funExcuteQuery(mysql);

					String docSql = " INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strSubCode1`,`strClientCode`,`strTableType`) " + " value(  '" + resultSet.getString(1) + "','' ,'" + tableName + "'  ,'" + resultSet.getString(2) + "', '" + clientCode + "','hd' ) ";
					objGlobalFunctionsService.funExcuteQuery(docSql);

					mDocCode.put(resultSet.getString(1), strDCCode + "," + resultSet.getString(1));
					flgHDSave = true;
					lastNo++;
				}

				if (flgHDSave) {
					sqlMs = "Select strDCCode,strProdCode ,dblQty,dblPrice,dblWeight " + " from tblDeliveryChallanDtl; ";
					resultSet = st.executeQuery(sqlMs);
					blankTable = " TRUNCATE `tbldeliverychallandtl`; ";
					objGlobalFunctionsService.funExcuteQuery(blankTable);
					lastNo = 1;
					while (resultSet.next()) {
						String doc = mDocCode.get(resultSet.getString(1));
						if (doc != null) {
							String codes[] = doc.split(",");
							String newCode = codes[0];
							String oldCode = codes[1];
							if (oldCode.equals(resultSet.getString(1))) {
								String mysql = " INSERT INTO `tbldeliverychallandtl` (`strDCCode`,`intId`,`strProdCode`,`dblQty`,`dblPrice`,`dblWeight`,`strClientCode`,`strSerialNo` ) " + " value( '" + newCode + "','" + lastNo + "','" + resultSet.getString(2) + "' , '" + resultSet.getString(3) + "' , '" + resultSet.getString(4) + "' , '" + resultSet.getString(5) + "','" + clientCode + "','' ) ";

								objGlobalFunctionsService.funExcuteQuery(mysql);

								String docSql = " INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strSubCode1`,`strClientCode`,`strTableType`) " + " value(  '" + resultSet.getString(1) + "','" + newCode + "' ,'" + tableName + "'  ,'" + resultSet.getString(2) + "', '" + clientCode + "','dtl' ) ";
								objGlobalFunctionsService.funExcuteQuery(docSql);

								lastNo++;
							}
						}
					}

				}
				mDocCode.clear();

				break;

			case "tblProductionHd":

				sqlMs = "Select strPDCode,dtPDDate ,strLocCode,strNarration,strWOCode  " + " from tblProductionHd; ";
				resultSet = st.executeQuery(sqlMs);
				blankTable = " TRUNCATE `tblproductionhd`; ";
				objGlobalFunctionsService.funExcuteQuery(blankTable);
				lastNo = 1;
				while (resultSet.next()) {
					String code = objGlobal.funGenerateDocumentCode("frmProduction", docDate, request);
					String mysql = " INSERT INTO `tblproductionhd` (`strPDCode`,`intId`,`dtPDDate`,`strLocCode`,`strNarration`,`strWOCode`,`strClientCode`, " + " `strUserCreated`,`dteCreatedDate`,`strUserModified`,`dteLastModified`,`strClientCode` ) " + " value( '" + code + "','" + lastNo + "','" + resultSet.getString(2) + "' , '" + resultSet.getString(3) + "' , '" + resultSet.getString(4)
							+ "' ," + " '" + resultSet.getString(5) + "','" + user + "', '" + currDate + "', '" + user + "', '" + currDate + "' ,'" + clientCode + "' ) ";

					objGlobalFunctionsService.funExcuteQuery(mysql);

					String docSql = " INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strSubCode1`,`strClientCode`,`strTableType`) " + " value(  '" + resultSet.getString(1) + "','" + code + "' ,'" + tableName + "'  ,'" + resultSet.getString(3) + "', '" + clientCode + "','hd' ) ";
					objGlobalFunctionsService.funExcuteQuery(docSql);

					lastNo++;
					flgHDSave = true;
					mDocCode.put(resultSet.getString(1), code + "," + resultSet.getString(1));
				}

				if (flgHDSave) {
					sqlMs = "Select strPDCode,strProdCode ,strProcessCode,dblQtyProd,dblQtyRej,dblWeight,dblPrice,strProdChar,strMacCode " + " from tblProductionDtl; ";
					resultSet = st.executeQuery(sqlMs);
					blankTable = " TRUNCATE `tblproductiondtl`; ";
					objGlobalFunctionsService.funExcuteQuery(blankTable);
					lastNo = 1;
					while (resultSet.next()) {
						String doc = mDocCode.get(resultSet.getString(1));
						if (doc != null) {
							String codes[] = doc.split(",");
							String newCode = codes[0];
							String oldCode = codes[1];
							if (oldCode.equals(resultSet.getString(1))) {
								String mysql = " INSERT INTO `tblproductiondtl` (`strPDCode`,`intId`,`strProdCode`,`strProcessCode`,`dblQtyProd`,`dblQtyRej`,`dblWeight`," + " `dblPrice`,`strProdChar`,`strMacCode` ) " + " value( '" + newCode + "','" + lastNo + "','" + resultSet.getString(2) + "' , '" + resultSet.getString(3) + "' , '" + resultSet.getString(4) + "' ," + " '"
										+ resultSet.getString(5) + "','" + resultSet.getString(6) + "','" + resultSet.getString(7) + "','" + resultSet.getString(8) + "'," + " '" + resultSet.getString(9) + "' ) ";

								objGlobalFunctionsService.funExcuteQuery(mysql);

								String docSql = " INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strSubCode1`,`strClientCode`,`strTableType`) " + " value(  '" + resultSet.getString(1) + "','' ,'" + tableName + "'  ,'" + resultSet.getString(2) + "', '" + clientCode + "','Dtl' ) ";
								objGlobalFunctionsService.funExcuteQuery(docSql);

								lastNo++;
							}
						}
					}
				}
				mDocCode.clear();
				break;

			case "tblDeliveryNoteHd":

				sqlMs = "Select strDNCode,dtDNDate ,strSCCode,strFrom,strLocCode," + " strSCAdd1,strSCAdd2,strSCCity,strSCState,strSCCountry," + " strSCPin,strFAdd1,strFAdd2,strFCity,strFState," + " strFCountry,strFPin,dtExpDate,strExpDet,strNarration," + " strDNType,strVehNo,strMInBy,strTimeInOut,strTypeCode," + " dblTotal,strAgainst,strGRNCode,strDNDuty " + " from tblDeliveryNoteHd; ";
				resultSet = st.executeQuery(sqlMs);
				blankTable = " TRUNCATE `tbldeliverynotehd`; ";
				objGlobalFunctionsService.funExcuteQuery(blankTable);
				lastNo = 1;
				while (resultSet.next()) {
					String code = objGlobal.funGenerateDocumentCode("frmDN", docDate, request);
					String mysql = " INSERT INTO `tbldeliverynotehd` (`strDNCode`,`intId`,`dteDNDate`,`strSCCode`,`strFrom`, `strLocCode`," + " `strSCAdd1`, `strSCAdd2`,`strSCCity`,`strSCState`,`strSCCountry`, `strSCPin`, " + " `strFAdd1`,`strFAdd2`,`strFCity`,`strFState`,`strFCountry`,`strFPin`," + " `dteExpDate`,`strExpDet`,`strNarration`,`strDNType`,`strVehNo`,`strMInBy`,"
							+ " `strTimeInOut`, `strTypeCode`,`dblTotal`, `strTypeAgainst`,`strGRNCode`,`strJACode`, " + " `strUserCreated`,`dteCreatedDate`,`strUserModified`,`dteLastModified`,`strClientCode` ) " + " value( '"
							+ code
							+ "','"
							+ lastNo
							+ "','"
							+ resultSet.getString(2)
							+ "' , '"
							+ resultSet.getString(3)
							+ "' , '"
							+ resultSet.getString(4)
							+ "' ,'"
							+ resultSet.getString(5)
							+ "', "
							+ " '"
							+ resultSet.getString(6)
							+ "' ,'"
							+ resultSet.getString(7)
							+ "','"
							+ resultSet.getString(8)
							+ "','"
							+ resultSet.getString(9)
							+ "','"
							+ resultSet.getString(10)
							+ "','"
							+ resultSet.getString(11)
							+ "',"
							+ " '"
							+ resultSet.getString(12)
							+ "','"
							+ resultSet.getString(13)
							+ "','"
							+ resultSet.getString(14)
							+ "','"
							+ resultSet.getString(15)
							+ "','"
							+ resultSet.getString(16)
							+ "','"
							+ resultSet.getString(17)
							+ "',"
							+ " '"
							+ resultSet.getString(18)
							+ "','"
							+ resultSet.getString(19)
							+ "','"
							+ resultSet.getString(20)
							+ "','"
							+ resultSet.getString(21)
							+ "','"
							+ resultSet.getString(22) + "','" + resultSet.getString(23) + "'," + " '" + resultSet.getString(24) + "','" + resultSet.getString(25) + "','" + resultSet.getString(26) + "','" + resultSet.getString(27) + "','" + resultSet.getString(28) + "','NA', " + " '" + user + "', '" + currDate + "', '" + user + "', '" + currDate + "' ,'" + clientCode + "' ) ";

					objGlobalFunctionsService.funExcuteQuery(mysql);

					String docSql = " INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strSubCode1`,`strClientCode`,`strTableType`) " + " value(  '" + resultSet.getString(1) + "','" + code + "' ,'" + tableName + "'  ,'" + resultSet.getString(3) + "', '" + clientCode + "','hd' ) ";
					objGlobalFunctionsService.funExcuteQuery(docSql);

					lastNo++;
					flgHDSave = true;
					mDocCode.put(resultSet.getString(1), code + "," + resultSet.getString(1));
				}
				if (flgHDSave) {
					sqlMs = "Select strDNCode,strProdCode ,strProcessCode,dblQty,dblWeight,dblRate,strProdChar,strRemarks " + " from tblDeliveryNoteDtl; ";
					resultSet = st.executeQuery(sqlMs);
					blankTable = " TRUNCATE `tbldeliverynotedtl`; ";
					objGlobalFunctionsService.funExcuteQuery(blankTable);
					lastNo = 1;

					while (resultSet.next()) {

						String doc = mDocCode.get(resultSet.getString(1));
						if (doc != null) {
							String codes[] = doc.split(",");
							String newCode = codes[0];
							String oldCode = codes[1];
							if (oldCode.equals(resultSet.getString(1))) {

								String mysql = " INSERT INTO `tbldeliverynotedtl` (`strDNCode`,`intId`,`strProdCode`,`strProcessCode`,`dblQty`,`dblWeight`,`dblRate`, `strProdChar`,`strRemarks`,`strClientCode` ) " + " value( '" + newCode + "','" + lastNo + "','" + resultSet.getString(2) + "' , '" + resultSet.getString(3) + "' , '" + resultSet.getString(4) + "' ," + " '" + resultSet.getString(5)
										+ "','" + resultSet.getString(6) + "','" + resultSet.getString(7) + "','" + resultSet.getString(8) + "', '" + clientCode + "' ) ";

								objGlobalFunctionsService.funExcuteQuery(mysql);

								String docSql = " INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strSubCode1`,`strClientCode`,`strTableType`) " + " value(  '" + resultSet.getString(1) + "','' ,'" + tableName + "'  ,'" + resultSet.getString(2) + "', '" + clientCode + "','Dtl' ) ";
								objGlobalFunctionsService.funExcuteQuery(docSql);

								lastNo++;
							}
						}
					}

				}
				mDocCode.clear();
				break;

			case "tblPurchaseOrderHd":

				sqlMs = "Select strPOCode,dtPODate ,strSuppCode,strAgainst,strSOCode," + " dblTotal,strVAddress1,strVAddress2,strVCity,strVState," + " strVCountry,strVPin,strSAddress1,strSAddress2,strSCity," + " strSState,strSCountry,strSPin,strYourRef,strPerRef," + " strEOE,strCode,strclosePO " + " from tblpurchaseorderhd; ";
				resultSet = st.executeQuery(sqlMs);
				blankTable = " TRUNCATE `tblpurchaseorderhd`; ";
				objGlobalFunctionsService.funExcuteQuery(blankTable);
				lastNo = 1;
				while (resultSet.next()) {
					String code = objGlobal.funGenerateDocumentCode("frmPurchaseOrder", docDate, request);
					String mysql = " INSERT INTO `tblpurchaseorderhd` (`strPOCode`,`intId`,`dtPODate`,`strSuppCode`,`strAgainst`,`strSOCode`," + " `dblTotal`,`strVAddress1`,`strVAddress2`,`strVCity`,`strVState`," + " `strVCountry`,`strVPin`,`strSAddress1`,`strSAddress2`,`strSCity`," + " `strSState`,`strSCountry`,`strSPin`,`strYourRef`,`strPerRef`,"
							+ " `strEOE`,`strCode`,`strclosePO`,`dtDelDate`,`dtPayDate`, " + " `strUserCreated`,`dtDateCreated`,`strUserModified`,`dtLastModified`,`strClientCode` ) " + " value( '"
							+ code
							+ "','"
							+ lastNo
							+ "','"
							+ resultSet.getString(2)
							+ "' , '"
							+ resultSet.getString(3)
							+ "' , '"
							+ resultSet.getString(4)
							+ "' ,'"
							+ resultSet.getString(5)
							+ "', "
							+ " '"
							+ resultSet.getString(6)
							+ "','"
							+ funCheckJunkChar(resultSet.getString(7))
							+ "','"
							+ funCheckJunkChar(resultSet.getString(8))
							+ "','"
							+ resultSet.getString(9)
							+ "','"
							+ resultSet.getString(10)
							+ "',"
							+ " '"
							+ resultSet.getString(11)
							+ "','"
							+ resultSet.getString(12)
							+ "','"
							+ funCheckJunkChar(resultSet.getString(13))
							+ "','"
							+ funCheckJunkChar(resultSet.getString(14))
							+ "','"
							+ resultSet.getString(15)
							+ "',"
							+ " '"
							+ resultSet.getString(16)
							+ "','"
							+ resultSet.getString(17)
							+ "','"
							+ resultSet.getString(18)
							+ "','"
							+ resultSet.getString(19)
							+ "','"
							+ resultSet.getString(20) + "', " + " '" + resultSet.getString(21) + "','" + resultSet.getString(22) + "','" + resultSet.getString(23) + "','" + currDate + "', '" + currDate + "',  " + " '" + user + "', '" + currDate + "', '" + user + "', '" + currDate + "' ,'" + clientCode + "') ";

					mDteDoc.put(resultSet.getString(1), resultSet.getString(2));
					objGlobalFunctionsService.funExcuteQuery(mysql);

					String docSql = " INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strSubCode1`,`strClientCode`,`strTableType`) " + " value(  '" + resultSet.getString(1) + "','" + code + "' ,'" + tableName + "'  ,'" + resultSet.getString(3) + "', '" + clientCode + "','hd' ) ";
					objGlobalFunctionsService.funExcuteQuery(docSql);

					lastNo++;
					mDocCode.put(resultSet.getString(1), code + "," + resultSet.getString(1));
					flgHDSave = true;
				}

				if (flgHDSave) {

					sqlMs = "Select strPOCode,strProdCode,dblOrdQty,dblPrice,strProdChar,strProcessCode,strRemarks " + " from tblpurchaseorderdtl; ";
					resultSet = st.executeQuery(sqlMs);
					blankTable = " TRUNCATE `tblpurchaseindenddtl`; ";
					objGlobalFunctionsService.funExcuteQuery(blankTable);
					lastNo = 1;
					while (resultSet.next()) {
						String doc = mDocCode.get(resultSet.getString(1));
						if (doc != null) {
							String codes[] = doc.split(",");
							String newCode = codes[0];
							String oldCode = codes[1];
							if (oldCode.equals(resultSet.getString(1))) {
								String podate = mDteDoc.get(resultSet.getString(1));
								if (podate.length() > 0) {

									String mysql = " INSERT INTO `tblpurchaseorderdtl` (`strPOCode`,`intId`,`strProdCode`,`dblOrdQty`,`dblPrice`,`strProdChar`,`strProcessCode`,`strRemarks`,`strClientCode`,`dtDelDate` ) " + " value( '" + newCode + "','" + lastNo + "','" + resultSet.getString(2) + "' , '" + resultSet.getString(3) + "' , '" + resultSet.getString(4) + "' ," + " '"
											+ resultSet.getString(5) + "','" + resultSet.getString(6) + "','" + resultSet.getString(7) + "', '" + clientCode + "','" + podate + "' ) ";

									objGlobalFunctionsService.funExcuteQuery(mysql);

									String docSql = " INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strSubCode1`,`strClientCode`,`strTableType`) " + " value(  '" + resultSet.getString(1) + "','' ,'" + tableName + "'  ,'" + resultSet.getString(2) + "', '" + clientCode + "','Dtl' ) ";
									objGlobalFunctionsService.funExcuteQuery(docSql);

									lastNo++;

								}
							}
						}

					}

				}
				mDocCode.clear();
				break;

			case "tblPurchaseReturnHd":

				sqlMs = " Select strPRCode,dtPRDate,strSuppCode,strAgainst,strGRNCode," + " dblSubTotal,dblDisRate,dblDisAmt,dblTaxAmt,dblExtra," + " dblTotal,strNarration,strLocCode,strVehNo,strMInBy " + " strTimeInOut,strPurCode " + " from tblPurchaseReturnHd; ";
				resultSet = st.executeQuery(sqlMs);
				blankTable = " TRUNCATE `tblpurchasereturnhd`; ";
				objGlobalFunctionsService.funExcuteQuery(blankTable);
				lastNo = 1;
				while (resultSet.next()) { // totPrice=resultSet.getDouble(3)*resultSet.getDouble(9);
					String code = objGlobal.funGenerateDocumentCode("frmPurchaseReturn", docDate, request);
					String mysql = " INSERT INTO `tblpurchasereturnhd` (`strPRCode`,`intid`,`dtPRDate`,`strSuppCode`,`strAgainst`,`strGRNCode`," + " `dblSubTotal`,`dblDisRate`,`dblDisAmt`,`dblTaxAmt`,`dblExtra`," + " `dblTotal`,`strNarration`,`strLocCode`,`strVehNo`,`strMInBy`," + " `strTimeInOut`,`strPurCode`,"
							+ " `strClientCode`,`strUserCreated`,`dtDateCreated`,`strUserModified`,`dtLastModified` ) " + " value( '"
							+ resultSet.getString(1)
							+ "','"
							+ lastNo
							+ "','"
							+ resultSet.getString(2)
							+ "' , '"
							+ resultSet.getDouble(3)
							+ "' , '"
							+ resultSet.getDouble(4)
							+ "' ,"
							+ " '"
							+ resultSet.getString(5)
							+ "','"
							+ resultSet.getDouble(6)
							+ "','"
							+ resultSet.getDouble(7)
							+ "','"
							+ resultSet.getDouble(8)
							+ "','"
							+ resultSet.getDouble(9)
							+ "',"
							+ " '"
							+ resultSet.getDouble(10)
							+ "','"
							+ resultSet.getString(11) + "','" + resultSet.getString(12) + "','" + resultSet.getString(13) + "','" + resultSet.getString(14) + "'," + " '" + resultSet.getString(15) + "','" + resultSet.getString(16) + "','" + resultSet.getString(17) + "'," + " '" + currDate + "','" + user + "','" + currDate + "','" + user + "','" + clientCode + "' ) ";

					objGlobalFunctionsService.funExcuteQuery(mysql);

					String docSql = " INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strSubCode1`,`strClientCode`,`strTableType`) " + " value(  '" + resultSet.getString(1) + "','" + code + "' ,'" + tableName + "'  ,'" + resultSet.getString(2) + "', '" + clientCode + "','Dtl' ) ";
					objGlobalFunctionsService.funExcuteQuery(docSql);

					lastNo++;
				}

				if (flgHDSave) {
					sqlMs = " delete from tblPurchaseReturnDtl where strPRCode=''; ";
					resultSet = st.executeQuery(sqlMs);

					sqlMs = " Select strPRCode,strProdCode,dblQty,dblDiscount,strTaxType," + " dblTaxableAmt,dblTax,dblTaxAmt,dblUnitPrice,dblWeight," + " strProdChar " + " from tblPurchaseReturnDtl; ";
					resultSet = st.executeQuery(sqlMs);
					blankTable = " TRUNCATE `tblpurchasereturndtl`; ";
					objGlobalFunctionsService.funExcuteQuery(blankTable);
					lastNo = 1;
					double totPrice = 0.00, totWt = 0.00;
					while (resultSet.next()) {

						String doc = mDocCode.get(resultSet.getString(1));
						if (doc != null) {
							String codes[] = doc.split(",");
							String newCode = codes[0];
							String oldCode = codes[1];
							if (oldCode.equals(resultSet.getString(1))) {

								totPrice = resultSet.getDouble(3) * resultSet.getDouble(9);
								totWt = resultSet.getDouble(3) * resultSet.getDouble(10);
								String mysql = " INSERT INTO `tblpurchasereturndtl` (`strPRCode`,`intId`,`strProdCode`,`dblQty`,`dblDiscount`," + "`strTaxType`,`dblTaxableAmt`,`dblTax`,`dblTaxAmt`,`dblUnitPrice`," + " `dblWeight`,`strProdChar`,`dblTotalPrice`,`dblTotalWt`,`strClientCode` ) " + " value( '" + newCode + "','" + lastNo + "','" + resultSet.getString(2) + "' , '"
										+ resultSet.getDouble(3) + "' , '" + resultSet.getDouble(4) + "' ," + " '" + resultSet.getString(5) + "','" + resultSet.getDouble(6) + "','" + resultSet.getDouble(7) + "','" + resultSet.getDouble(8) + "','" + resultSet.getDouble(9) + "'," + " '" + resultSet.getDouble(10) + "','" + resultSet.getString(11) + "','" + totPrice + "','" + totWt + "','"
										+ clientCode + "' ) ";

								objGlobalFunctionsService.funExcuteQuery(mysql);

								String docSql = " INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strSubCode1`,`strClientCode`,`strTableType`) " + " value(  '" + resultSet.getString(1) + "','" + newCode + "' ,'" + tableName + "'  ,'" + resultSet.getString(2) + "', '" + clientCode + "','Dtl' ) ";
								objGlobalFunctionsService.funExcuteQuery(docSql);

								lastNo++;
							}
						}
					}

				}
				mDocCode.clear();
				break;

			case "tblSalesOrderHd":

				sqlMs = "Select strSOCode,dtSODate,strCustCode,strCustPONo,strStatus,dtFulmtDate," + " strLocCode,strPayMode,strBAdd1,strBAdd2,strBCity," + " strBState,strBCountry,strBPin,strSAdd1,strSAdd2," + " strSCity,strSState,strSCountry,strSPin,dblSubTotal," + " dblDisRate,dblDisAmt,dblTaxAmt,dblExtra,dblTotal," + " dtfulfilled,strNarration from tblSalesOrderHd; ";
				resultSet = st.executeQuery(sqlMs);
				blankTable = " TRUNCATE `tblsalesorderhd`; ";
				objGlobalFunctionsService.funExcuteQuery(blankTable);
				lastNo = 1;
				while (resultSet.next()) {
					lastNo = objGlobalFunctionsService.funGetLastNo("tblsalesorderhd", "SOCode", "intId", clientCode);

					String year = objGlobal.funGetSplitedDate(startDate)[2];
					String cd = objGlobal.funGetTransactionCode("SO", propCode, year);
					String code = cd + String.format("%06d", lastNo);

					String mysql = " INSERT INTO `tblsalesorderhd` (`strSOCode`,`intid`,`dteSODate`,`strCustCode`,`strCustPONo`,`strStatus`,`dteFulmtDate`" + " ,`strLocCode`,`strPayMode`,`strBAdd1`,`strBAdd2`,`strBCity`," + " `strBState`,`strBCountry`,`strBPin`,`strSAdd1`,`strSAdd2`," + " `strSCity`,`strSState`,`strSCountry`,`strSPin`,`dblSubTotal`,"
							+ " `dblDisRate`,`dblDisAmt`,`dblTaxAmt,`dblExtra`,`dblTotal`,`strNarration` " + " `strUserCreated`,`dteCreatedDate`,`strUserModified`,`dteLastModified`,`strClientCode`,`intwarmonth` ) " + " value( '"
							+ code
							+ "','"
							+ lastNo
							+ "','"
							+ resultSet.getString(2)
							+ "' , '"
							+ resultSet.getString(3)
							+ "' , '"
							+ resultSet.getString(4)
							+ "' ,'"
							+ resultSet.getString(5)
							+ "','"
							+ resultSet.getString(6)
							+ "',"
							+ " '"
							+ resultSet.getString(7)
							+ "','"
							+ resultSet.getString(8)
							+ "','"
							+ resultSet.getString(9)
							+ "','"
							+ resultSet.getString(10)
							+ "','"
							+ resultSet.getString(11)
							+ "',"
							+ " '"
							+ resultSet.getString(12)
							+ "','"
							+ resultSet.getString(13)
							+ "','"
							+ resultSet.getString(14)
							+ "','"
							+ resultSet.getString(15)
							+ "','"
							+ resultSet.getString(16)
							+ "',"
							+ " '"
							+ resultSet.getString(17)
							+ "','"
							+ resultSet.getString(18)
							+ "','"
							+ resultSet.getString(19)
							+ "','"
							+ resultSet.getString(20)
							+ "','"
							+ resultSet.getString(21)
							+ "',"
							+ " '"
							+ resultSet.getString(22) + "','" + resultSet.getString(24) + "','" + resultSet.getString(25) + "','" + resultSet.getString(26) + "','" + resultSet.getString(28) + "' " + " '" + user + "', '" + currDate + "', '" + user + "', '" + currDate + "' ,'" + clientCode + "', '1' ) ";

					objGlobalFunctionsService.funExcuteQuery(mysql);

					String docSql = " INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strSubCode1`,`strClientCode`,`strTableType`) " + " value(  '" + resultSet.getString(1) + "','" + code + "' ,'" + tableName + "'  ,'" + resultSet.getString(3) + "', '" + clientCode + "','hd' ) ";
					objGlobalFunctionsService.funExcuteQuery(docSql);

					lastNo++;
					flgHDSave = true;
					mDocCode.put(resultSet.getString(1), code + "," + resultSet.getString(1));
				}

				if (flgHDSave) {
					sqlMs = "Select strSOCode,strProdCode,dblQty,dblDiscount,strTaxType,dblTaxableAmt," + " dblTax,dblTaxAmt,dblUnitPrice,dblWeight,strProdChar," + "  from tblSalesOrderDtl; ";
					resultSet = st.executeQuery(sqlMs);
					blankTable = " TRUNCATE `tblsalesorderdtl`; ";
					objGlobalFunctionsService.funExcuteQuery(blankTable);
					lastNo = 1;
					while (resultSet.next()) {
						String doc = mDocCode.get(resultSet.getString(1));
						if (doc != null) {
							String codes[] = doc.split(",");
							String newCode = codes[0];
							String oldCode = codes[1];
							if (oldCode.equals(resultSet.getString(1))) {

								String mysql = " INSERT INTO `tblsalesorderdtl` (`strSOCode`,`intId`,`strProdCode`,`dblQty`,`dblDiscount`,`strTaxType`,`dblTaxableAmt`" + ",`dblTax`,`dblTaxAmt`,`dblUnitPrice`,`dblWeight`,`strProdChar`,`intindex`,`strClientCode` ) " + " value( '" + newCode + "','" + lastNo + "','" + resultSet.getString(2) + "' , '" + resultSet.getString(3) + "' , '"
										+ resultSet.getString(4) + "' ,'" + resultSet.getString(5) + "','" + resultSet.getString(6) + "'," + " '" + resultSet.getString(7) + "','" + resultSet.getString(8) + "','" + resultSet.getString(9) + "','" + resultSet.getString(10) + "','" + resultSet.getString(11) + "'," + " '1','" + clientCode + "' ) ";
								objGlobalFunctionsService.funExcuteQuery(mysql);

								String docSql = " INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strSubCode1`,`strClientCode`,`strTableType`) " + " value(  '" + resultSet.getString(1) + "','" + newCode + "' ,'" + tableName + "'  ,'" + resultSet.getString(3) + "', '" + clientCode + "','Dtl' ) ";
								objGlobalFunctionsService.funExcuteQuery(docSql);

								lastNo++;
							}
						}
					}

				}
				mDocCode.clear();

				break;

			case "tblSCReturnHd":

				sqlMs = "Select strSRCode,dtSRDate,strSCCode,strSCDCCode,strVerRemark,strDispAction," + " strPartDel,strLocCode,strJWCode,strVehNo,strMInBy," + " strTimeInOut,strAgainst,strNo,strInRefNo,dtInRefDate," + " dtSCDCDate from tblSCReturnHd; ";
				resultSet = st.executeQuery(sqlMs);
				blankTable = " TRUNCATE `tblscreturnhd`; ";
				objGlobalFunctionsService.funExcuteQuery(blankTable);
				lastNo = 1;
				while (resultSet.next()) {
					lastNo = objGlobalFunctionsService.funGetLastNo("tblscreturnhd", "SRCode", "intId", clientCode);

					String year = objGlobal.funGetSplitedDate(startDate)[2];
					String cd = objGlobal.funGetTransactionCode("SR", propCode, year);
					String code = cd + String.format("%06d", lastNo);

					String mysql = " INSERT INTO `tblscreturnhd` (`strSRCode`,`intid`,`dteSRDate`,`strSCCode`,`strSCDNCode`,`strVerRemark`,`strDispAction`" + " ,`strPartDel`,`strLocCode`,`strJWCode`,`strVehNo`,`strMInBy`," + " `strTimeInOut`,`strAgainst`,`strNo`,`strInRefNo`,`dteInRefDate`,"
							+ " `dteSCDCDate`,`strUserCreated`,`dteCreatedDate`,`strUserModified`,`dteLastModified`,`strClientCode` ) " + " value( '"
							+ code
							+ "','"
							+ lastNo
							+ "','"
							+ resultSet.getString(2)
							+ "' , '"
							+ resultSet.getString(3)
							+ "' , '"
							+ resultSet.getString(4)
							+ "' ,'"
							+ resultSet.getString(5)
							+ "','"
							+ resultSet.getString(6)
							+ "',"
							+ " '"
							+ resultSet.getString(7)
							+ "','"
							+ resultSet.getString(8)
							+ "','"
							+ resultSet.getString(9)
							+ "','"
							+ resultSet.getString(10)
							+ "','"
							+ resultSet.getString(11)
							+ "'," + " '" + resultSet.getString(12) + "','" + resultSet.getString(13) + "','" + resultSet.getString(14) + "','" + resultSet.getString(15) + "','" + resultSet.getString(16) + "'," + " '" + resultSet.getString(17) + "','" + user + "', '" + currDate + "', '" + user + "', '" + currDate + "' ,'" + clientCode + "' ) ";

					objGlobalFunctionsService.funExcuteQuery(mysql);

					String docSql = " INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strSubCode1`,`strClientCode`,`strTableType`) " + " value(  '" + resultSet.getString(1) + "','" + code + "' ,'" + tableName + "'  ,'" + resultSet.getString(3) + "', '" + clientCode + "','hd' ) ";
					objGlobalFunctionsService.funExcuteQuery(docSql);

					lastNo++;
					flgHDSave = true;
					mDocCode.put(resultSet.getString(1), code + "," + resultSet.getString(1));
				}

				if (flgHDSave) {
					sqlMs = "Select strSRCode,strProdCode,dblQty,dblQtyRej,dblWeight,dblPrice," + " strProdChar,strDNProdCode,strDNProdName,strDNProcess,dblDNQty, " + " dblDNWeight,strDNProdChar,dblScrap,strDNCode,dblDCQty, " + " dblExpQty,intExpIndex,dblDCWt,strRemarks,dblQtyRbl,dblRblWt " + "  from tblSCReturnDtl; ";
					resultSet = st.executeQuery(sqlMs);
					blankTable = " TRUNCATE `tblscreturndtl`; ";
					objGlobalFunctionsService.funExcuteQuery(blankTable);
					lastNo = 1;
					while (resultSet.next()) {
						String doc = mDocCode.get(resultSet.getString(1));
						if (doc != null) {
							String codes[] = doc.split(",");
							String newCode = codes[0];
							String oldCode = codes[1];
							if (oldCode.equals(resultSet.getString(1))) {

								String mysql = " INSERT INTO `tblSCReturnDtl` (`strSRCode`,`intId`,`strProdCode`,`dblQty`,`dblQtyRej`,`dblWeight`,`dblPrice`," + " `strProdChar`,`strDNProdCode`,`strDNProdName`,`strDNProcess`,`dblDNQty`, " + " `dblDNWeight`,`strDNProdChar`,`dblScrap`,`strDNCode`,`dblDCQty`, "
										+ " `dblExpQty`,`intExpIndex`,`dblDCWt`,`strRemarks`,`dblQtyRbl`,`dblRblWt`,`strClientCode` ) " + " value( '"
										+ newCode
										+ "','"
										+ lastNo
										+ "','"
										+ resultSet.getString(2)
										+ "' , '"
										+ resultSet.getString(3)
										+ "' , '"
										+ resultSet.getString(4)
										+ "' ,'"
										+ resultSet.getString(5)
										+ "','"
										+ resultSet.getString(6)
										+ "',"
										+ " '"
										+ resultSet.getString(7)
										+ "','"
										+ resultSet.getString(8)
										+ "','"
										+ resultSet.getString(9)
										+ "','"
										+ resultSet.getString(10)
										+ "','"
										+ resultSet.getString(11)
										+ "',"
										+ " '"
										+ resultSet.getString(12)
										+ "','"
										+ resultSet.getString(13)
										+ "','"
										+ resultSet.getString(14)
										+ "','"
										+ resultSet.getString(15)
										+ "','"
										+ resultSet.getString(16)
										+ "', "
										+ " '"
										+ resultSet.getString(17)
										+ "','"
										+ resultSet.getString(18)
										+ "','"
										+ resultSet.getString(19)
										+ "','"
										+ resultSet.getString(20)
										+ "','"
										+ resultSet.getString(21) + "', " + " '" + resultSet.getString(22) + "','" + clientCode + "' ) ";
								objGlobalFunctionsService.funExcuteQuery(mysql);

								String docSql = " INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strSubCode1`,`strClientCode`,`strTableType`) " + " value(  '" + resultSet.getString(1) + "','" + newCode + "' ,'" + tableName + "'  ,'" + resultSet.getString(3) + "', '" + clientCode + "','Dtl' ) ";
								objGlobalFunctionsService.funExcuteQuery(docSql);

								lastNo++;
							}
						}
					}

				}
				mDocCode.clear();
				break;

			case "tblStockAdjustmentHd":

				sqlMs = "Select strSACode,dtSADate,strLocCode,strNarration,strAuthorise" + " from tblStockAdjustmentHd; ";
				resultSet = st.executeQuery(sqlMs);
				blankTable = " TRUNCATE `tblstockadjustmenthd`; ";
				objGlobalFunctionsService.funExcuteQuery(blankTable);
				lastNo = 1;
				while (resultSet.next()) {
					String code = objGlobal.funGenerateDocumentCode("frmStockAdjustment", docDate, request);

					String mysql = " INSERT INTO `tblstockadjustmenthd` (`strSACode`,`intid`,`dtSADate`,`strLocCode`,`strNarration`,`strAuthorise` " + " ,`strUserCreated`,`dtCreatedDate`,`strUserModified`,`dtLastModified`,`strClientCode` ) " + " value( '" + code + "','" + lastNo + "','" + resultSet.getString(2) + "' , '" + resultSet.getString(3) + "' , '" + resultSet.getString(4) + "' ,'"
							+ resultSet.getString(5) + "' " + " ,'" + user + "', '" + currDate + "', '" + user + "', '" + currDate + "' ,'" + clientCode + "' ) ";

					objGlobalFunctionsService.funExcuteQuery(mysql);

					String docSql = " INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strSubCode1`,`strClientCode`,`strTableType`) " + " value(  '" + resultSet.getString(1) + "','" + code + "' ,'" + tableName + "'  ,'" + resultSet.getString(3) + "', '" + clientCode + "','hd' ) ";
					objGlobalFunctionsService.funExcuteQuery(docSql);

					lastNo++;
					flgHDSave = true;
					mDocCode.put(resultSet.getString(1), code + "," + resultSet.getString(1));
				}
				if (flgHDSave) {
					sqlMs = "Select strSACode,strProdCode,dblQty,strType,dblPrice,dblWeight," + " strProdChar from tblstockadjustmentdtl; ";
					resultSet = st.executeQuery(sqlMs);
					blankTable = " TRUNCATE `tblstockadjustmentdtl`; ";
					objGlobalFunctionsService.funExcuteQuery(blankTable);
					lastNo = 1;
					while (resultSet.next()) {
						String doc = mDocCode.get(resultSet.getString(1));
						if (doc != null) {
							String codes[] = doc.split(",");
							String newCode = codes[0];
							String oldCode = codes[1];
							if (oldCode.equals(resultSet.getString(1))) {

								String mysql = " INSERT INTO `tblstockadjustmentdtl` (`strSACode`,`intId`,`strProdCode`,`dblQty`,`strType`,`dblPrice`,`dblWeight`," + " `strProdChar`,`strClientCode` ) " + " value( '" + newCode + "','" + lastNo + "','" + resultSet.getString(2) + "' , '" + resultSet.getString(3) + "' , '" + resultSet.getString(4) + "' ,'" + resultSet.getString(5) + "','"
										+ resultSet.getString(6) + "'," + " '" + resultSet.getString(7) + "','" + clientCode + "' ) ";
								objGlobalFunctionsService.funExcuteQuery(mysql);

								String docSql = " INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strSubCode1`,`strClientCode`,`strTableType`) " + " value(  '" + resultSet.getString(1) + "','" + newCode + "' ,'" + tableName + "'  ,'" + resultSet.getString(3) + "', '" + clientCode + "','Dtl' ) ";
								objGlobalFunctionsService.funExcuteQuery(docSql);

								lastNo++;
							}
						}

					}

				}
				mDocCode.clear();
				break;

			case "tblStockPostingHd":

				sqlMs = "Select strPSCode,dtPSDate,strLocCode,strSACode " + " from tblStockPostingHd; ";
				resultSet = st.executeQuery(sqlMs);
				blankTable = " TRUNCATE `tblstockpostinghd`; ";
				objGlobalFunctionsService.funExcuteQuery(blankTable);
				lastNo = 1;
				while (resultSet.next()) {
					String code = objGlobal.funGenerateDocumentCode("frmPhysicalStkPosting", docDate, request);

					String mysql = " INSERT INTO `tblstockpostinghd` (`strPSCode`,`intid`,`dtPSDate`,`strLocCode`,`strSACode`,`strAuthorise` " + " ,`strUserCreated`,`dtCreatedDate`,`strUserModified`,`dtLastModified`,`strClientCode` ) " + " value( '" + code + "','" + lastNo + "','" + resultSet.getString(2) + "' , '" + resultSet.getString(3) + "' , '" + resultSet.getString(4) + "' ,'N' " + " ,'"
							+ user + "', '" + currDate + "', '" + user + "', '" + currDate + "' ,'" + clientCode + "' ) ";

					objGlobalFunctionsService.funExcuteQuery(mysql);

					String docSql = " INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strSubCode1`,`strClientCode`,`strTableType`) " + " value(  '" + resultSet.getString(1) + "','" + code + "' ,'" + tableName + "'  ,'" + resultSet.getString(3) + "', '" + clientCode + "','hd' ) ";
					objGlobalFunctionsService.funExcuteQuery(docSql);

					lastNo++;
					flgHDSave = true;
					mDocCode.put(resultSet.getString(1), code + "," + resultSet.getString(1));
				}
				if (flgHDSave) {
					sqlMs = "Select strPSCode,strProdCode,dblCStock,dblPStock,dblPrice,dblWeight," + " strProdChar from tblStockPostingDtl; ";
					resultSet = st.executeQuery(sqlMs);
					blankTable = " TRUNCATE `tblstockpostingdtl`; ";
					objGlobalFunctionsService.funExcuteQuery(blankTable);
					lastNo = 1;
					while (resultSet.next()) {
						String doc = mDocCode.get(resultSet.getString(1));
						if (doc != null) {
							String codes[] = doc.split(",");
							String newCode = codes[0];
							String oldCode = codes[1];
							if (oldCode.equals(resultSet.getString(1))) {

								String mysql = " INSERT INTO `tblstockpostingdtl` (`strPSCode`,`intId`,`strProdCode`,`dblCStock`,`dblPStock`,`dblPrice`,`dblWeight`," + " `strProdChar`,`strClientCode` ) " + " value( '" + newCode + "','" + lastNo + "','" + resultSet.getString(2) + "' , '" + resultSet.getString(3) + "' , '" + resultSet.getString(4) + "' ,'" + resultSet.getString(5) + "','"
										+ resultSet.getString(6) + "'," + " '" + resultSet.getString(7) + "','" + clientCode + "' ) ";
								objGlobalFunctionsService.funExcuteQuery(mysql);

								String docSql = " INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strSubCode1`,`strClientCode`,`strTableType`) " + " value(  '" + resultSet.getString(1) + "','" + newCode + "' ,'" + tableName + "'  ,'" + resultSet.getString(3) + "', '" + clientCode + "','Dtl' ) ";
								objGlobalFunctionsService.funExcuteQuery(docSql);

								lastNo++;
							}
						}

					}

				}
				mDocCode.clear();
				break;

			case "tblStockTransferHd":

				sqlMs = "Select strSTCode,dtSTDate,strFromLocCode,strToLocCode,strNarration,strAuthorise," + " strNo " + " from tblStockTransferHd; ";
				resultSet = st.executeQuery(sqlMs);
				blankTable = " TRUNCATE `tblstocktransferhd`; ";
				objGlobalFunctionsService.funExcuteQuery(blankTable);
				lastNo = 1;
				while (resultSet.next()) {
					String code = objGlobal.funGenerateDocumentCode("frmStockTransfer", docDate, request);

					String mysql = " INSERT INTO `tblstocktransferhd` (`strSTCode`,`intid`,`dtSTDate`,`strFromLocCode`,`strToLocCode`,`strNarration`,`strAuthorise`," + " `strNo` " + " ,`strUserCreated`,`dtCreatedDate`,`strUserModified`,`dtLastModified`,`strClientCode` ) " + " value( '" + code + "','" + lastNo + "','" + resultSet.getString(2) + "' , '" + resultSet.getString(3) + "' , '"
							+ resultSet.getString(4) + "' ,'" + resultSet.getString(5) + "' , '" + resultSet.getString(6) + "' ," + " '" + resultSet.getString(7) + "' " + " ,'" + user + "', '" + currDate + "', '" + user + "', '" + currDate + "' ,'" + clientCode + "' ) ";

					objGlobalFunctionsService.funExcuteQuery(mysql);

					String docSql = " INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strSubCode1`,`strClientCode`,`strTableType`) " + " value(  '" + resultSet.getString(1) + "','" + code + "' ,'" + tableName + "'  ,'" + resultSet.getString(3) + "', '" + clientCode + "','hd' ) ";
					objGlobalFunctionsService.funExcuteQuery(docSql);

					lastNo++;
					flgHDSave = true;
					mDocCode.put(resultSet.getString(1), code + "," + resultSet.getString(1));
				}
				if (flgHDSave) {
					sqlMs = "Select strSTCode,strProdCode,dblQty,dblWeight,dblPrice,strProdChar,intProdIndex " + "  from tblStockTransferDtl; ";
					resultSet = st.executeQuery(sqlMs);
					blankTable = " TRUNCATE `tblStockTransferDtl`; ";
					objGlobalFunctionsService.funExcuteQuery(blankTable);
					lastNo = 1;
					while (resultSet.next()) {
						String doc = mDocCode.get(resultSet.getString(1));
						if (doc != null) {
							String codes[] = doc.split(",");
							String newCode = codes[0];
							String oldCode = codes[1];
							if (oldCode.equals(resultSet.getString(1))) {
								String intProdIndex = "0";
								if (resultSet.getString(7) != null) {
									intProdIndex = resultSet.getString(7);
								}

								String mysql = " INSERT INTO `tblstocktransferdtl` (`strSTCode`,`intId`,`strProdCode`,`dblQty`,`dblWeight`,`dblPrice`,`strProdChar`," + " `intProdIndex`,`strClientCode` ) " + " value( '" + newCode + "','" + lastNo + "','" + resultSet.getString(2) + "' , '" + resultSet.getString(3) + "' , '" + resultSet.getString(4) + "' ,'" + resultSet.getString(5) + "','"
										+ resultSet.getString(6) + "'," + " '" + intProdIndex + "','" + clientCode + "' ) ";
								objGlobalFunctionsService.funExcuteQuery(mysql);

								String docSql = " INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strSubCode1`,`strClientCode`,`strTableType`) " + " value(  '" + resultSet.getString(1) + "','" + newCode + "' ,'" + tableName + "'  ,'" + resultSet.getString(3) + "', '" + clientCode + "','Dtl' ) ";
								objGlobalFunctionsService.funExcuteQuery(docSql);

								lastNo++;
							}
						}

					}

				}
				mDocCode.clear();
				break;

			case "tblWorkOrderHd":

				sqlMs = "Select strWOCode,dtWODate,strSOCode,strProdCode,dblQty,strParentWOCode," + " strStatus " + " from tblWorkOrderHd; ";
				resultSet = st.executeQuery(sqlMs);
				blankTable = " TRUNCATE `tblworkorderhd`; ";
				objGlobalFunctionsService.funExcuteQuery(blankTable);
				lastNo = 1;
				while (resultSet.next()) {
					String code = objGlobal.funGenerateDocumentCode("frmWorkOrder", docDate, request);

					String mysql = " INSERT INTO `tblworkorderhd` (`strWOCode`,`intid`,`dtWODate`,`strSOCode`,`strProdCode`,`dblQty`,`strParentWOCode`, " + " `strStatus`,`strUserCreated`,`dtDateCreated`,`strUserModified`,`dtLastModified`,`strClientCode` ) " + " value( '" + code + "','" + lastNo + "','" + resultSet.getString(2) + "' , '" + resultSet.getString(3) + "' , '" + resultSet.getString(4)
							+ "' ,'" + resultSet.getString(5) + "' , '" + resultSet.getString(6) + "' ," + " '" + resultSet.getString(7) + "' " + " ,'" + user + "', '" + currDate + "', '" + user + "', '" + currDate + "' ,'" + clientCode + "' ) ";

					objGlobalFunctionsService.funExcuteQuery(mysql);

					String docSql = " INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strSubCode1`,`strClientCode`,`strTableType`) " + " value(  '" + resultSet.getString(1) + "','" + code + "' ,'" + tableName + "'  ,'" + resultSet.getString(3) + "', '" + clientCode + "','hd' ) ";
					objGlobalFunctionsService.funExcuteQuery(docSql);

					lastNo++;
					flgHDSave = true;
					mDocCode.put(resultSet.getString(1), code + "," + resultSet.getString(1));
				}
				if (flgHDSave) {
					sqlMs = "Select strWOCode,strStageName,strDesc " + "  from tblWorkOrderDtl; ";
					resultSet = st.executeQuery(sqlMs);
					blankTable = " TRUNCATE `tblworkorderdtl`; ";
					objGlobalFunctionsService.funExcuteQuery(blankTable);
					lastNo = 1;
					while (resultSet.next()) {
						String doc = mDocCode.get(resultSet.getString(1));
						if (doc != null) {
							String codes[] = doc.split(",");
							String newCode = codes[0];
							String oldCode = codes[1];
							if (oldCode.equals(resultSet.getString(1))) {

								String mysql = " INSERT INTO `tblworkorderdtl` (`strWOCode`,`strProcessCode`,`strStatus`,`strClientCode` " + "  ) " + " value( '" + newCode + "','" + resultSet.getString(2) + "' , '" + resultSet.getString(3) + "' ,'" + clientCode + "' ) ";
								objGlobalFunctionsService.funExcuteQuery(mysql);

								String docSql = " INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strSubCode1`,`strClientCode`,`strTableType`) " + " value(  '" + resultSet.getString(1) + "','" + newCode + "' ,'" + tableName + "'  ,'" + resultSet.getString(3) + "', '" + clientCode + "','Dtl' ) ";
								objGlobalFunctionsService.funExcuteQuery(docSql);

								lastNo++;
							}
						}

					}

				}
				mDocCode.clear();
				break;

			case "tblGRNHd":

				sqlMs = "Select strGRNCode,dtGRNDate,strSuppCode,strAgainst,strPONo,strBillNo," + " dtBillDate,dtDueDate,strPayMode,dblSubTotal,dblDisRate,dblDisAmt," + " dblTaxAmt,dblExtra,dblTotal,strNarration,strLocCode,strVehNo," + " strMInBy,strTimeInOut,strNo,strRefNo,dtRefDate " + " from tblgrnhd; ";
				resultSet = st.executeQuery(sqlMs);
				blankTable = " TRUNCATE `tblgrnhd`; ";
				objGlobalFunctionsService.funExcuteQuery(blankTable);
				lastNo = 1;
				while (resultSet.next()) {
					String code = objGlobal.funGenerateDocumentCode("frmGRN", docDate, request);

					String mysql = " INSERT INTO `tblgrnhd` (`strGRNCode`,`intId`,`dtGRNDate`,`strSuppCode`,`strAgainst`,`strPONo`,`strBillNo`, " + " `dtBillDate`, `dtDueDate`,`strPayMode`,`dblSubTotal`,`dblDisRate`,`dblDisAmt`, " + " `dblTaxAmt`,`dblExtra`,`dblTotal`,`strNarration`,`strLocCode`,`strVehNo`," + " `strMInBy`,`strTimeInOut`,`strNo`,`strRefNo`,`dtRefDate`, "
							+ " `strUserCreated`,`dtDateCreated`,`strUserModified`,`dtLastModified`,`strClientCode` ) " + " value( '"
							+ code
							+ "','"
							+ lastNo
							+ "','"
							+ resultSet.getString(2)
							+ "' , '"
							+ resultSet.getString(3)
							+ "' , '"
							+ resultSet.getString(4)
							+ "' ,'"
							+ resultSet.getString(5)
							+ "' , '"
							+ resultSet.getString(6)
							+ "' ,"
							+ " '"
							+ resultSet.getString(7)
							+ "','"
							+ resultSet.getString(8)
							+ "','"
							+ resultSet.getString(9)
							+ "','"
							+ resultSet.getString(10)
							+ "','"
							+ resultSet.getString(11)
							+ "','"
							+ resultSet.getString(12)
							+ "',"
							+ " '"
							+ resultSet.getString(13)
							+ "','"
							+ resultSet.getString(14)
							+ "','"
							+ resultSet.getString(15)
							+ "','"
							+ resultSet.getString(16)
							+ "','"
							+ resultSet.getString(17)
							+ "','"
							+ resultSet.getString(18)
							+ "',"
							+ " '"
							+ resultSet.getString(19)
							+ "','"
							+ resultSet.getString(20)
							+ "','"
							+ resultSet.getString(21)
							+ "','"
							+ resultSet.getString(22) + "','" + resultSet.getString(23) + "' " + " ,'" + user + "', '" + currDate + "', '" + user + "', '" + currDate + "' ,'" + clientCode + "' ) ";

					objGlobalFunctionsService.funExcuteQuery(mysql);

					String docSql = " INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strSubCode1`,`strClientCode`,`strTableType`) " + " value(  '" + resultSet.getString(1) + "','" + code + "' ,'" + tableName + "'  ,'" + resultSet.getString(3) + "', '" + clientCode + "','hd' ) ";
					objGlobalFunctionsService.funExcuteQuery(docSql);

					lastNo++;
					flgHDSave = true;
					mDocCode.put(resultSet.getString(1), code + "," + resultSet.getString(1));
				}
				if (flgHDSave) {
					sqlMs = "Select strGRNCode,strProdCode,dblQty,dblRejected,dblDiscount,strTaxType, " + " dblTaxableAmt,dblTax,dblTaxAmt,dblUnitPrice,dblWeight,strProdChar, " + "dblDCQty,dblDCWt,strRemarks,dblQtyRbl,strGRNProdChar,strPOWeight, " + " strCode " + "  from tblGRNDtl; ";
					resultSet = st.executeQuery(sqlMs);
					blankTable = " TRUNCATE `tblgrndtl`; ";
					objGlobalFunctionsService.funExcuteQuery(blankTable);
					lastNo = 1;
					while (resultSet.next()) {
						String doc = mDocCode.get(resultSet.getString(1));
						if (doc != null) {
							String codes[] = doc.split(",");
							String newCode = codes[0];
							String oldCode = codes[1];
							if (oldCode.equals(resultSet.getString(1))) {

								String mysql = " INSERT INTO `tblgrndtl` (`strGRNCode`,`strProdCode`,`dblQty`,`dblRejected`,`dblDiscount`,`strTaxType`," + " `dblTaxableAmt`,`dblTax`,`dblTaxAmt`,`dblUnitPrice`,`dblWeight`,`strProdChar`," + " `dblDCQty`,`dblDCWt`,`strRemarks`,`dblQtyRbl`,`strGRNProdChar`,`dblPOWeight`," + " `strCode`,`strClientCode` ) " + " value( '" + newCode + "','"
										+ resultSet.getString(2) + "' , '" + resultSet.getString(3) + "' ,'" + resultSet.getString(4) + "' ,'" + resultSet.getString(5) + "' ,'" + resultSet.getString(6) + "' ," + " '" + resultSet.getString(7) + "' ,'" + resultSet.getString(8) + "' ,'" + resultSet.getString(9) + "' ,'" + resultSet.getString(10) + "' ,'" + resultSet.getString(11) + "' ,'"
										+ resultSet.getString(12) + "' ," + " '" + resultSet.getString(13) + "' ,'" + resultSet.getString(14) + "' ,'" + resultSet.getString(15) + "' ,'" + resultSet.getString(16) + "' ,'" + resultSet.getString(17) + "' ,'" + resultSet.getString(18) + "' ," + " '" + resultSet.getString(19) + "' ," + " '" + clientCode + "' ) ";
								objGlobalFunctionsService.funExcuteQuery(mysql);

								String docSql = " INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strSubCode1`,`strClientCode`,`strTableType`) " + " value(  '" + resultSet.getString(1) + "','" + newCode + "' ,'" + tableName + "'  ,'" + resultSet.getString(3) + "', '" + clientCode + "','Dtl' ) ";
								objGlobalFunctionsService.funExcuteQuery(docSql);

								lastNo++;
							}
						}

					}

				}
				mDocCode.clear();
				break;

			case "tblInitialInventory":

				sqlMs = "Select strProdCode,strLocCode,dblQty,strUOM,dblCostPUnit,dblRevLvl," + " strLotNo,dtExpDate " + " from tblInitialInventory; ";
				resultSet = st.executeQuery(sqlMs);
				blankTable = " TRUNCATE `tblinitialinventory`; ";
				objGlobalFunctionsService.funExcuteQuery(blankTable);

				blankTable = " TRUNCATE `tblinitialinvdtl`; ";
				objGlobalFunctionsService.funExcuteQuery(blankTable);
				lastNo = 1;
				while (resultSet.next()) {
					lastNo = objGlobalFunctionsService.funGetLastNo("tblinitialinventory", "OPStkCode", "intId", clientCode);
					String year = objGlobal.funGetSplitedDate(startDate)[2];
					String cd = objGlobal.funGetTransactionCode("OP", propCode, year);
					String code = cd + String.format("%06d", lastNo);

					String mysql = " INSERT INTO `tblinitialinventory` (`strOpStkCode`,`intId`,`strLocCode`,`dtExpiryDate`, " + " `strUserCreated`,`dtCreatedDate`,`strUserModified`,`dtLastModified`,`strClientCode` ) " + " value( '" + code + "','" + lastNo + "','" + resultSet.getString(2) + "' , '" + resultSet.getString(8) + "' " + " ,'" + user + "', '" + currDate + "', '" + user + "', '"
							+ currDate + "' ,'" + clientCode + "' ) ";

					objGlobalFunctionsService.funExcuteQuery(mysql);

					String docSql = " INSERT INTO `tblimportdatadoc` (`strOldDocCode`, `strNewDocCode`, `strTableName`,`strSubCode1`,`strSubCode2`,`strClientCode`,`strTableType`) " + " value(  'NA','" + code + "' ,'" + tableName + "'  ,'" + resultSet.getString(1) + "', '" + resultSet.getString(2) + "','" + clientCode + "','hd-Dtl' ) ";
					objGlobalFunctionsService.funExcuteQuery(docSql);

					String mysqlDtl = "  INSERT INTO `tblinitialinvdtl` (`strOpStkCode`,`intId`,`strProdCode`,`dblQty`, " + " `strUOM`,`dblCostPerUnit`,`dblRevLvl`,`strLotNo`,`strClientCode` )   " + " value( '" + code + "','" + lastNo + "','" + resultSet.getString(1) + "' , '" + resultSet.getString(3) + "' , '" + resultSet.getString(4) + "' ,'" + resultSet.getString(5) + "' , '"
							+ resultSet.getString(6) + "' ," + " '" + resultSet.getString(7) + "'  ,'" + clientCode + "' ) ";
					objGlobalFunctionsService.funExcuteQuery(mysqlDtl);

					lastNo++;

				}
				break;

			default:
				break;
			}

			request.getSession().setAttribute("success", true);
			request.getSession().setAttribute("successMessage", "Product Saved : ".concat(""));
			con.close();
		} catch (SQLException ex) {
			ex.printStackTrace();

		}

		return new ModelAndView("redirect:/frmDatabaseDataImport.html?saddr=" + urlHits);

	}

	@SuppressWarnings("finally")
	@RequestMapping(value = "/loadDatabase", method = RequestMethod.GET)
	public @ResponseBody List funLoadPOSData(clsPOSItemMasterImportBean objBean, HttpServletRequest req) {
		ArrayList<String> listTables = new ArrayList<String>();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String param = req.getParameter("param").toString();
		String[] spParam = param.split(",");
		String IpAdd = spParam[0];
		String port = spParam[1];
		String dbName = spParam[2];
		String userName = spParam[3];
		String pass = spParam[4];
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			System.out.println("jdbc:sqlserver://" + IpAdd + ":" + port + ";user=" + userName + ";password=" + pass + ";database=" + dbName);
			con = DriverManager.getConnection("jdbc:sqlserver://" + IpAdd + ":" + port + ";user=" + userName + ";password=" + pass + ";database=" + dbName);
			st = con.createStatement();
			if (con != null) {
				System.out.println("Connection Created");
				String sqlShowTable = " SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE' ";
				resultSet = st.executeQuery(sqlShowTable);
				while (resultSet.next()) {
					listTables.add(resultSet.getString(3));
				}
			}
		} catch (Exception ex) {
			System.out.println("Connection Failed");
			ex.printStackTrace();

		} finally {
			return listTables;
		}
	}

	private String funCheckSymbol(String strWithSymbol) {
		String strChangeString = strWithSymbol;

		if (strWithSymbol.contains("'")) {
			strChangeString = strWithSymbol;
			strChangeString = strChangeString.replace("'", "");
		}
		return strChangeString;
	}

	private String funCheckJunkChar(String strJunk) {
		byte[] arrBt = strJunk.getBytes();
		byte[] tempBt = new byte[arrBt.length];

		for (int i = 0; i < arrBt.length; i++) {
			// System.out.println(arrBt[i]+"     "+String.valueOf(arrBt[i]));

			if (arrBt[i] < 0) {
			} else {
				tempBt[i] = arrBt[i];
			}
		}

		String ss = new String(tempBt);
		// System.out.println(ss);
		return ss.trim();
	}

}
