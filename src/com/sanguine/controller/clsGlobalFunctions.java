package com.sanguine.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.DriverManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysql.jdbc.Connection;
import com.sanguine.base.service.intfBaseService;
import com.sanguine.bean.clsProductTaxDtl;
import com.sanguine.bean.clsStockAdjustmentBean;
import com.sanguine.model.clsCompanyMasterModel;
import com.sanguine.model.clsLinkUpHdModel;
import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.model.clsStkAdjustmentDtlModel;
import com.sanguine.model.clsStkAdjustmentHdModel;
import com.sanguine.model.clsStkAdjustmentHdModel_ID;
import com.sanguine.model.clsStkPostingDtlModel;
import com.sanguine.model.clsStkPostingHdModel;
import com.sanguine.model.clsTCMasterModel;
import com.sanguine.model.clsTCTransModel;
import com.sanguine.model.clsUserMasterModel;
import com.sanguine.service.clsCharacteristicsMasterService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsLinkUpService;
import com.sanguine.service.clsLocationMasterService;
import com.sanguine.service.clsProductMasterService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.service.clsStkAdjustmentService;
import com.sanguine.service.clsStkPostingService;
import com.sanguine.service.clsUserMasterService;
import com.sanguine.webbooks.bean.clsCreditorOutStandingReportBean;
import com.sanguine.webbooks.bean.clsWebBooksAccountMasterBean;
import com.sanguine.webbooks.model.clsLedgerSummaryModel;

@Controller
public class clsGlobalFunctions {
	public String currentDateTime, currentDate;
	
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	
	@Autowired
	private clsStkAdjustmentService objStkAdjService;
	
	@Autowired
	private clsCharacteristicsMasterService objCharService;
	
	@Autowired
	clsProductMasterService objProductMasterService;

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@Autowired
	private clsLocationMasterService objLocationMasterService;
	
	@Autowired
	private intfBaseService objBaseService;

	
	@Autowired
	private clsStkPostingService objStkPostService;
	
	@Autowired
	private clsLinkUpService objLinkupService;
	
	@Autowired
	private clsUserMasterService objUserMasterService;
	
	@Autowired
	private clsReportsController objReportsController;

	
	
	public final String NARRATION = "strNarration";
	public final String COMPANY_NAME = "strCompanyName";
	public final String USER_CODE = "strUserCode";
	public final String AGAINST = "strAgainst";
	public final String AUTHORISE = "strAuthorise";
	public final String FROM_LOCATION = "strFromLoc";
	public final String TO_LOCATION = "strToLoc";
	public final String FROM_DATE = "dtFromDate";
	public final String TO_DATE = "dtToDate";
	Map<String, Double> hmChildNodes;

	
	public static String conUrl;
	public static String urlExcise;
	public static String urlCRM;
	public static String urlwebbooks;
	public static String urlwebclub;
	public static String urlwebpms;
	public static String urluser;
	public static String urlPassword;
	public static String urlSqlDump;
	public List listChildNodes1;

	public static String POSWSURL = "http://localhost:8080/prjSanguineWebService";

	static {

		try {

			Properties prop = new Properties();
			Resource resource = new ClassPathResource("resources/database.properties");
			InputStream input = resource.getInputStream();
			// load a properties file
			prop.load(input);
			// get the property value and print it out
			conUrl = prop.getProperty("database.urlWebStocks");
			urlExcise = prop.getProperty("database.urlExcise");
			urlCRM = prop.getProperty("database.urlCRM");
			urlwebbooks = prop.getProperty("database.urlwebbooks");
			urlwebclub = prop.getProperty("database.urlwebclub");
			urlwebpms = prop.getProperty("database.urlwebpms");
			urluser = prop.getProperty("database.user");
			urlPassword = prop.getProperty("database.password");
			urlSqlDump = prop.getProperty("sqldump");
			input.close();
			resource.exists();

		} catch (Exception e) {
			System.out.println("Property File Not Found in Global Functions");
			e.printStackTrace();
		}
	}

	
	public String funTrimDBNameFromURL(String fullString)
	{
		String dbName="";
		int ind=fullString.lastIndexOf("/");
		dbName=fullString.substring(ind+1, fullString.length());
		return dbName;
	}
	
	
	
	public long funGetLastNo(String tableName, String masterName, String columnName) {
		return objGlobalFunctionsService.funGetLastNo(tableName, masterName, columnName, "");
	}

	public long funCompareTime(Date d2, Date d1) {
		long diff = 0;
		try {
			diff = d2.getTime() - d1.getTime();
			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			String time = diffHours + ":" + diffMinutes + ":" + diffSeconds;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return diff;
		}
	}

	public String funGetCurrentDateTime(String pattern) {
		Date dt = new Date();
		if (pattern.equals("yyyy-MM-dd")) {
			currentDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dt);
		} else {
			currentDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(dt);
		}
		return currentDateTime;
	}

	public String funGetCurrentDate(String pattern) {
		Date dt = new Date();
		currentDate = new SimpleDateFormat(pattern).format(dt);
		return currentDate;
	}

	public String funGetDate(String pattern, String date) {
		String retDate = date;

		if (pattern.equals("yyyy-MM-dd")) // From JSP to Database yyyy-MM-dd
		{
			if(date.contains(" "))
			{
				date=date.split(" ")[0];
			}
			String[] spDate = date.split("-");
			retDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0];
		} else if (pattern.equals("yyyy/MM/dd")) // From Database to JSP
		{
			String[] sp = date.split(" ");
			String[] spDate = sp[0].split("-");
			retDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0];
		} else if (pattern.equals("dd-MM-yyyy")) // For Jasper Report show
		{
			String[] sp = date.split(" ");
			String[] spDate = sp[0].split("-");
			retDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0];
		}
		return retDate;
	}

	
	public String funGetDateAndTime(String pattern, String date) {
		String retDate = date;

		Date dt=new Date();
		String time=dt.getHours()+":"+dt.getMinutes()+":"+dt.getSeconds();
		
		if (pattern.equals("yyyy-MM-dd")) // From JSP to Database yyyy-MM-dd
		{
			if(date.contains(" "))
			{
				date=date.split(" ")[0];
			}
			String[] spDate = date.split("-");
			retDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0]+" "+time;
		} else if (pattern.equals("yyyy/MM/dd")) // From Database to JSP
		{
			String[] sp = date.split(" ");
			String[] spDate = sp[0].split("-");
			retDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0]+" "+time;
		} else if (pattern.equals("dd-MM-yyyy")) // For Jasper Report show
		{
			String[] sp = date.split(" ");
			String[] spDate = sp[0].split("-");
			retDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0]+" "+time;
		}
		return retDate;
	}
	
	
	public long funGetMonth() {
		return new Date().getMonth();
	}

	public String funGetAlphabet(int no) {
		String[] alphabets = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
		return alphabets[no];
	}

	public String[] funGetAlphabetSet() {
		String[] alphabetSet = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
		return alphabetSet;
	}

	

	@SuppressWarnings("deprecation")
	public String funGetTransactionCode(String initCode, String propCode, String startYear) {
		String transCode = "";
		transCode = propCode;
		Date dt = new Date();
		
	//	String years = String.valueOf((dt.getYear() + 1900) - Integer.parseInt(startYear));
		String transYear="A";
		List<clsCompanyMasterModel> listClsCompanyMasterModel = objSetupMasterService.funGetListCompanyMasterModel();
		if (listClsCompanyMasterModel.size() > 0) {
			clsCompanyMasterModel objCompanyMasterModel = listClsCompanyMasterModel.get(listClsCompanyMasterModel.size() - 1);
			transYear=objCompanyMasterModel.getStrYear();
		}
		transCode = propCode + initCode + transYear + funGetAlphabet(dt.getMonth());
		return transCode;
	}





	/**
	 * Generate Document Code
	 * 
	 * @param strTransType
	 * @param dtTransDate
	 * @param req
	 * @return
	 */

	public String funGenerateDocumentCode(String strTransType, String dtTransDate, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		String[] spDate = dtTransDate.split("-");
		Date dt = new Date();
		// String
		// years=String.valueOf((dt.getYear()+1900)-Integer.parseInt(spDate[2]));
		String transYear="A";
		List<clsCompanyMasterModel> listClsCompanyMasterModel = objSetupMasterService.funGetListCompanyMasterModel();
		if (listClsCompanyMasterModel.size() > 0) {
			clsCompanyMasterModel objCompanyMasterModel = listClsCompanyMasterModel.get(listClsCompanyMasterModel.size() - 1);
			transYear=objCompanyMasterModel.getStrYear();
		}
		
		//String years = String.valueOf(Integer.parseInt(spDate[2]));
		//String transYear = funGetAlphabet((Integer.parseInt(years) % 26));
		String transMonth = funGetAlphabet(Integer.parseInt(spDate[1])-1);
		String strDocLiteral = "";
		String sql = "";
		String sqlAudit = "";

		String strDocNo = "";
		switch (strTransType) {
		case "frmGRN":

			strDocLiteral = "GR";
			sql = "select ifnull(max(MID(a.strGRNCode,7,6)),'' )as strGRNCode " + " from tblgrnhd a where MID(a.strGRNCode,5,1) = '" + transYear + "' " + " and MID(a.strGRNCode,6,1) = '" + transMonth + "' " + " and MID(a.strGRNCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";

			sqlAudit = " select ifnull(max(MID(a.strTransCode,7,6)),'' ) " + " from tblaudithd a where MID(a.strTransCode,5,1) = '" + transYear + "' " + " and MID(a.strTransCode,6,1) = '" + transMonth + "' " + " and MID(a.strTransCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' " + "and a.strTransType='GRN(Good Receiving Note)' ;  ";

			break;

		case "frmMaterialReturn":

			strDocLiteral = "MR";
			sql = "select ifnull(max(MID(a.strMRetCode,7,6)),'' ) as strMRetCode" + " from tblmaterialreturnhd a where MID(a.strMRetCode,5,1) = '" + transYear + "' " + " and MID(a.strMRetCode,6,1) = '" + transMonth + "' " + " and MID(a.strMRetCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";

			sqlAudit = " select ifnull(max(MID(a.strTransCode,7,6)),'' ) " + " from tblaudithd a where MID(a.strTransCode,5,1) = '" + transYear + "' " + " and MID(a.strTransCode,6,1) = '" + transMonth + "' " + " and MID(a.strTransCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' " + "and a.strTransType='Material Return' ;  ";

			break;

		case "frmOpeningStock":

			strDocLiteral = "OP";
			sql = "select ifnull(max(MID(a.strOpStkCode,7,6)),'' ) as strOpStkCode" + " from tblinitialinventory a where MID(a.strOpStkCode,5,1) = '" + transYear + "' " + " and MID(a.strOpStkCode,6,1) = '" + transMonth + "' " + " and MID(a.strOpStkCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";

			sqlAudit = " select ifnull(max(MID(a.strTransCode,7,6)),'' ) " + " from tblaudithd a where MID(a.strTransCode,5,1) = '" + transYear + "' " + " and MID(a.strTransCode,6,1) = '" + transMonth + "' " + " and MID(a.strTransCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' " + "and a.strTransType='Opening Stock' ;  ";

			break;

		case "frmPhysicalStkPosting":

			strDocLiteral = "PS";
			sql = "select ifnull(max(MID(a.strPSCode,7,6)),'' ) " + " from tblstockpostinghd a where MID(a.strPSCode,5,1) = '" + transYear + "' " + " and MID(a.strPSCode,6,1) = '" + transMonth + "' " + " and MID(a.strPSCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";

			sqlAudit = " select ifnull(max(MID(a.strTransCode,7,6)),'' ) " + " from tblaudithd a where MID(a.strTransCode,5,1) = '" + transYear + "' " + " and MID(a.strTransCode,6,1) = '" + transMonth + "' " + " and MID(a.strTransCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' " + "and a.strTransType='Physical Stk Posting' ;  ";

			break;

		case "frmPurchaseIndent":

			strDocLiteral = "PI";
			sql = "select ifnull(max(MID(a.strPIcode,7,6)),'' ) " + " from tblpurchaseindendhd a where MID(a.strPIcode,5,1) = '" + transYear + "' " + " and MID(a.strPIcode,6,1) = '" + transMonth + "' " + " and MID(a.strPIcode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";

			sqlAudit = " select ifnull(max(MID(a.strTransCode,7,6)),'' ) " + " from tblaudithd a where MID(a.strTransCode,5,1) = '" + transYear + "' " + " and MID(a.strTransCode,6,1) = '" + transMonth + "' " + " and MID(a.strTransCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' " + "and a.strTransType='Purchase Indent' ;  ";

			break;

		case "frmPurchaseOrder":

			strDocLiteral = "PO";
			sql = "select ifnull(max(MID(a.strPOCode,7,6)),'' ) " + " from tblpurchaseorderhd a where MID(a.strPOCode,5,1) = '" + transYear + "' " + " and MID(a.strPOCode,6,1) = '" + transMonth + "' " + " and MID(a.strPOCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";

			sqlAudit = " select ifnull(max(MID(a.strTransCode,7,6)),'' ) " + " from tblaudithd a where MID(a.strTransCode,5,1) = '" + transYear + "' " + " and MID(a.strTransCode,6,1) = '" + transMonth + "' " + " and MID(a.strTransCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' " + "and a.strTransType='Purchase Order' ;  ";

			break;

		case "frmPurchaseReturn":

			strDocLiteral = "PR";
			sql = "select ifnull(max(MID(a.strPRCode,7,6)),'' ) " + " from tblpurchasereturnhd a where MID(a.strPRCode,5,1) = '" + transYear + "' " + " and MID(a.strPRCode,6,1) = '" + transMonth + "' " + " and MID(a.strPRCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";

			sqlAudit = " select ifnull(max(MID(a.strTransCode,7,6)),'' ) " + " from tblaudithd a where MID(a.strTransCode,5,1) = '" + transYear + "' " + " and MID(a.strTransCode,6,1) = '" + transMonth + "' " + " and MID(a.strTransCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' " + "and a.strTransType='Purchase Return' ;  ";

			break;

		case "frmStockAdjustment":

			strDocLiteral = "SA";
			sql = "select ifnull(max(MID(a.strSACode,7,6)),'' ) " + " from tblstockadjustmenthd a where MID(a.strSACode,5,1) = '" + transYear + "' " + " and MID(a.strSACode,6,1) = '" + transMonth + "' " + " and MID(a.strSACode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";

			sqlAudit = " select ifnull(max(MID(a.strTransCode,7,6)),'' ) " + " from tblaudithd a where MID(a.strTransCode,5,1) = '" + transYear + "' " + " and MID(a.strTransCode,6,1) = '" + transMonth + "' " + " and MID(a.strTransCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' " + "and a.strTransType='Stock Adjustment' ;  ";

			break;

		case "frmWorkOrder":

			strDocLiteral = "WO";
			sql = "select ifnull(max(MID(a.strWOCode,7,6)),'' ) " + " from tblworkorderhd a where MID(a.strWOCode,5,1) = '" + transYear + "' " + " and MID(a.strWOCode,6,1) = '" + transMonth + "' " + " and MID(a.strWOCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";

			sqlAudit = " select ifnull(max(MID(a.strTransCode,7,6)),'' ) " + " from tblaudithd a where MID(a.strTransCode,5,1) = '" + transYear + "' " + " and MID(a.strTransCode,6,1) = '" + transMonth + "' " + " and MID(a.strTransCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' " + "and a.strTransType='Work Order' ;  ";

			break;

		case "frmStockTransfer":

			strDocLiteral = "ST";
			sql = "select ifnull(max(MID(a.strSTCode,7,6)),'' ) " + " from tblstocktransferhd a where MID(a.strSTCode,5,1) = '" + transYear + "' " + " and MID(a.strSTCode,6,1) = '" + transMonth + "' " + " and MID(a.strSTCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";

			sqlAudit = " select ifnull(max(MID(a.strTransCode,7,6)),'' ) " + " from tblaudithd a where MID(a.strTransCode,5,1) = '" + transYear + "' " + " and MID(a.strTransCode,6,1) = '" + transMonth + "' " + " and MID(a.strTransCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' " + "and a.strTransType='Stock Transfer' ;  ";

			break;

		case "frmMIS":

			strDocLiteral = "MI";
			sql = "select ifnull(max(MID(a.strMISCode,7,6)),'' ) " + " from tblmishd a where MID(a.strMISCode,5,1) = '" + transYear + "' " + " and MID(a.strMISCode,6,1) = '" + transMonth + "' " + " and MID(a.strMISCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";

			sqlAudit = " select ifnull(max(MID(a.strTransCode,7,6)),'' ) " + " from tblaudithd a where MID(a.strTransCode,5,1) = '" + transYear + "' " + " and MID(a.strTransCode,6,1) = '" + transMonth + "' " + " and MID(a.strTransCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' " + "and a.strTransType='Material Issue Slip' ;  ";

			break;

		case "frmMaterialReq":

			strDocLiteral = "RE";
			sql = "select ifnull(max(MID(a.strReqCode,7,6)),'' ) " + " from tblreqhd a where MID(a.strReqCode,5,1) = '" + transYear + "' " + " and MID(a.strReqCode,6,1) = '" + transMonth + "' " + " and MID(a.strReqCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";

			sqlAudit = " select ifnull(max(MID(a.strTransCode,7,6)),'' ) " + " from tblaudithd a where MID(a.strTransCode,5,1) = '" + transYear + "' " + " and MID(a.strTransCode,6,1) = '" + transMonth + "' " + " and MID(a.strTransCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' " + "and a.strTransType='Material Requisition' ;  ";

			break;

		case "frmProduction":

			strDocLiteral = "PD";
			sql = "select ifnull(max(MID(a.strPDCode,7,6)),'' ) " + " from tblproductionhd a where MID(a.strPDCode,5,1) = '" + transYear + "' " + " and MID(a.strPDCode,6,1) = '" + transMonth + "' " + " and MID(a.strPDCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";

			sqlAudit = " select ifnull(max(MID(a.strTransCode,7,6)),'' ) " + " from tblaudithd a where MID(a.strTransCode,5,1) = '" + transYear + "' " + " and MID(a.strTransCode,6,1) = '" + transMonth + "' " + " and MID(a.strTransCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' " + "and a.strTransType='Material Production' ;  ";
			break;

		case "frmBillPassing":

			strDocLiteral = "BP";
			sql = "select ifnull(max(MID(a.strBillPassNo,7,6)),'' ) " + " from tblbillpasshd a where MID(a.strBillPassNo,5,1) = '" + transYear + "' " + " and MID(a.strBillPassNo,6,1) = '" + transMonth + "' " + " and MID(a.strBillPassNo,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";

			sqlAudit = " select ifnull(max(MID(a.strTransCode,7,6)),'' ) " + " from tblaudithd a where MID(a.strTransCode,5,1) = '" + transYear + "' " + " and MID(a.strTransCode,6,1) = '" + transMonth + "' " + " and MID(a.strTransCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' " + "and a.strTransType='Bill Passing' ;  ";
			break;

		case "frmProductionOrder":

			strDocLiteral = "MP";
			sql = "select ifnull(max(MID(a.strOPCode,7,6)),'' ) " + " from tblproductionorderhd a where MID(a.strOPCode,5,1) = '" + transYear + "' " + " and MID(a.strOPCode,6,1) = '" + transMonth + "' " + " and MID(a.strOPCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";

			sqlAudit = " select ifnull(max(MID(a.strTransCode,7,6)),'' ) " + " from tblaudithd a where MID(a.strTransCode,5,1) = '" + transYear + "' " + " and MID(a.strTransCode,6,1) = '" + transMonth + "' " + " and MID(a.strTransCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' " + "and a.strTransType='Production Order' ;  ";
			break;

		case "frmJV":

			strDocLiteral = "JV";
			sql = "select ifnull(max(MID(a.strVouchNo,7,6)),'' ) " + " from tbljvhd a where MID(a.strVouchNo,5,1) = '" + transYear + "' " + " and MID(a.strVouchNo,6,1) = '" + transMonth + "' " + " and MID(a.strVouchNo,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";

			sqlAudit = " select ifnull(max(MID(a.strTransCode,7,6)),'' ) " + " from tblaudithd a where MID(a.strTransCode,5,1) = '" + transYear + "' " + " and MID(a.strTransCode,6,1) = '" + transMonth + "' " + " and MID(a.strTransCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' " + "and a.strTransType='JV' ;  ";

			break;

		case "frmDN":

			strDocLiteral = "DN";
			sql = "select ifnull(max(MID(a.strDNCode,7,6)),'' ) " + " from tbldeliverynotehd a where MID(a.strDNCode,5,1) = '" + transYear + "' " + " and MID(a.strDNCode,6,1) = '" + transMonth + "' " + " and MID(a.strDNCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";

			sqlAudit = " select ifnull(max(MID(a.strTransCode,7,6)),'' ) " + " from tblaudithd a where MID(a.strTransCode,5,1) = '" + transYear + "' " + " and MID(a.strTransCode,6,1) = '" + transMonth + "' " + " and MID(a.strTransCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' " + "and a.strTransType='DN' ;  ";
			break;

		case "frmExciseBillGneneration":

			strDocLiteral = "EX";
			sql = "select ifnull(max(MID(a.strBillNo,7,6)),'' )as strGRNCode " + " from tblgrnhd a where MID(a.strBillNo,5,1) = '" + transYear + "' " + " and MID(a.strBillNo,6,1) = '" + transMonth + "' " + " and MID(a.strBillNo,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";

			sqlAudit = " select ifnull(max(MID(a.strTransCode,7,6)),'' ) " + " from tblaudithd a where MID(a.strTransCode,5,1) = '" + transYear + "' " + " and MID(a.strTransCode,6,1) = '" + transMonth + "' " + " and MID(a.strTransCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' " + "and a.strTransType='ExciseBillGeneration' ;  ";

			break;

		case "frmSalesReturn":

			strDocLiteral = "SR";
			sql = "select ifnull(max(MID(a.strSRCode,7,6)),'' ) " + " from tblsalesreturnhd a where MID(a.strSRCode,5,1) = '" + transYear + "' " + " and MID(a.strSRCode,6,1) = '" + transMonth + "' " + " and MID(a.strSRCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";

			sqlAudit = " select ifnull(max(MID(a.strTransCode,7,6)),'' ) " + " from tblaudithd a where MID(a.strTransCode,5,1) = '" + transYear + "' " + " and MID(a.strTransCode,6,1) = '" + transMonth + "' " + " and MID(a.strTransCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' " + "and a.strTransType='Sales Return' ;  ";

			break;

		case "frmInvoice":

			strDocLiteral = "IV";
			sql = "select ifnull(max(MID(a.strInvCode,7,6)),'' ) " + " from tblinvoicehd a where MID(a.strInvCode,5,1) = '" + transYear + "' " + " and MID(a.strInvCode,6,1) = '" + transMonth + "' " + " and MID(a.strInvCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";

			sqlAudit = " select ifnull(max(MID(a.strTransCode,7,6)),'' ) " + " from tblaudithd a where MID(a.strTransCode,5,1) = '" + transYear + "' " + " and MID(a.strTransCode,6,1) = '" + transMonth + "' " + " and MID(a.strTransCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' " + "and a.strTransType='Invoice' ;  ";
			break;

		case "frmReceipt":

			strDocLiteral = "RV";
			sql = "select ifnull(max(MID(a.strVouchNo,7,6)),'' ) " + " from tblreceipthd a where MID(a.strVouchNo,5,1) = '" + transYear + "' " + " and MID(a.strVouchNo,6,1) = '" + transMonth + "' " + " and MID(a.strVouchNo,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";

			sqlAudit = " select ifnull(max(MID(a.strTransCode,7,6)),'' ) " + " from tblaudithd a where MID(a.strTransCode,5,1) = '" + transYear + "' " + " and MID(a.strTransCode,6,1) = '" + transMonth + "' " + " and MID(a.strTransCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' " + "and a.strTransType='Recipt' ;  ";
			break;

		case "frmSC":

			strDocLiteral = "SC";
			sql = "select ifnull(max(MID(a.strVoucherNo,7,6)),'' ) " + " from tblscbillhd a where MID(a.strVoucherNo,5,1) = '" + transYear + "' " + " and MID(a.strVoucherNo,6,1) = '" + transMonth + "' " + " and MID(a.strVoucherNo,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";

			sqlAudit = " select ifnull(max(MID(a.strTransCode,7,6)),'' ) " + " from tblaudithd a where MID(a.strTransCode,5,1) = '" + transYear + "' " + " and MID(a.strTransCode,6,1) = '" + transMonth + "' " + " and MID(a.strTransCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' " + "and a.strTransType='Sub Contracted' ;  ";
			break;

		case "frmRetailBilling":

			strDocLiteral = "RB";
			sql = "select ifnull(max(MID(a.strInvCode,7,6)),'' ) " + " from tblinvoicehd a where MID(a.strInvCode,5,1) = '" + transYear + "' " + " and MID(a.strInvCode,6,1) = '" + transMonth + "' " + " and MID(a.strInvCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";

			sqlAudit = " select ifnull(max(MID(a.strTransCode,7,6)),'' ) " + " from tblaudithd a where MID(a.strTransCode,5,1) = '" + transYear + "' " + " and MID(a.strTransCode,6,1) = '" + transMonth + "' " + " and MID(a.strTransCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' " + "and a.strTransType='Invoice' ;  ";
			break;

		case "frmDS":

			strDocLiteral = "DS";
			sql = "select ifnull(max(MID(a.strDSCode,7,6)),'' ) " + " from tbldeliveryschedulehd a where MID(a.strDSCode,5,1) = '" + transYear + "' " + " and MID(a.strDSCode,6,1) = '" + transMonth + "' " + " and MID(a.strDSCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";

			sqlAudit = " select ifnull(max(MID(a.strTransCode,7,6)),'' ) " + " from tblaudithd a where MID(a.strTransCode,5,1) = '" + transYear + "' " + " and MID(a.strTransCode,6,1) = '" + transMonth + "' " + " and MID(a.strTransCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' " + "and a.strTransType='Invoice' ;  ";
			break;

		case "frmBudgetMaster":

			strDocLiteral = "BM";
			sql = "select ifnull(max(MID(a.strBudgetCode,7,6)),'' ) " + " from tblbudgetmasterhd a where MID(a.strBudgetCode,5,1) = '" + transYear + "' " + " and MID(a.strBudgetCode,6,1) = '" + transMonth + "' " + " and MID(a.strBudgetCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";

			sqlAudit = " select ifnull(max(MID(a.strTransCode,7,6)),'' ) " + " from tblaudithd a where MID(a.strTransCode,5,1) = '" + transYear + "' " + " and MID(a.strTransCode,6,1) = '" + transMonth + "' " + " and MID(a.strTransCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' " + "and a.strTransType='Invoice' ;  ";
			break;
			
		case "frmProFormaInvoice":

			strDocLiteral = "PIV";
			sql = "select ifnull(max(MID(a.strInvCode,7,6)),'' ) " + " from tblproformainvoicehd a where MID(a.strInvCode,5,1) = '" + transYear + "' " + " and MID(a.strInvCode,6,1) = '" + transMonth + "' " + " and MID(a.strInvCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";
			sqlAudit = " select ifnull(max(MID(a.strTransCode,7,6)),'' ) " + " from tblaudithd a where MID(a.strTransCode,5,1) = '" + transYear + "' " + " and MID(a.strTransCode,6,1) = '" + transMonth + "' " + " and MID(a.strTransCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' " + "and a.strTransType='Invoice' ;  ";
			break;
			
		case "frmSalesOrder":

			strDocLiteral = "SO";
			sql = "select ifnull(max(MID(a.strSOCode,7,6)),'' )as strSOCode " + " from tblsalesorderhd a where MID(a.strSOCode,5,1) = '" + transYear + "' " + " and MID(a.strSOCode,6,1) = '" + transMonth + "' " + " and MID(a.strSOCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";

			sqlAudit = " select ifnull(max(MID(a.strTransCode,7,6)),'' ) " + " from tblaudithd a where MID(a.strTransCode,5,1) = '" + transYear + "' " + " and MID(a.strTransCode,6,1) = '" + transMonth + "' " + " and MID(a.strTransCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' " + "and a.strTransType='Sales Order' ;  ";

			break;
			
		}
		List listAudit = objGlobalFunctionsService.funGetListModuleWise(sqlAudit, "sql");
		long lastnoAudit;
		if (listAudit != null && !listAudit.isEmpty() && !listAudit.contains("")) {
			lastnoAudit = Integer.parseInt(listAudit.get(0).toString());

		} else {
			lastnoAudit = 0;
		}
		List list = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
		long lastnoLive;
		if (list != null && !list.isEmpty() && !list.contains("")) {
			lastnoLive = Integer.parseInt(list.get(0).toString());

		} else {
			lastnoLive = 0;
		}

		if (lastnoLive > lastnoAudit) {
			strDocNo = propCode + strDocLiteral + transYear + transMonth + String.format("%06d", lastnoLive + 1);
		} else {
			strDocNo = propCode + strDocLiteral + transYear + transMonth + String.format("%06d", lastnoAudit + 1);
		}
		return strDocNo;
	}




	public String funGenerateDocumentCodeForLocSeries(String strTransType, String dtTransDate, String locCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		String[] spDate = dtTransDate.split("-");
		Date dt = new Date();
		// String
		// years=String.valueOf((dt.getYear()+1900)-Integer.parseInt(spDate[2]));
		String years = String.valueOf(Integer.parseInt(spDate[2]));
		String transYear = funGetAlphabet((Integer.parseInt(years) % 26));
		String transMonth = funGetAlphabet(Integer.parseInt(spDate[1]));
		String strDocLiteral = "";
		String sql = "";
		String sqlAudit = "";

		String locNo = locCode.substring(1);
		int num = new Integer(locNo);
		if (num < 10) {
			locNo = String.valueOf(num);
			locNo = "0" + locNo;
		} else {
			if (num > 10) {
				num = num % 100;
				if (num < 10) {
					locNo = String.valueOf(num);
					locNo = "0" + locNo;
				} else {
					locNo = String.valueOf(num);
				}

			}

		}

		String strDocNo = "";
		switch (strTransType) {
		case "frmRetailBilling":

			strDocLiteral = "RB";
			sql = "select ifnull(max(MID(a.strInvCode,7,6)),'' )as strInvCode " + " from tblinvoicehd a where MID(a.strInvCode,5,1) = '" + transYear + "' " + " and MID(a.strInvCode,6,1) = '" + transMonth + "' " + " and MID(a.strInvCode,1,2) = '" + locNo + "' and strClientCode='" + clientCode + "' ";

			sqlAudit = " select ifnull(max(MID(a.strTransCode,7,6)),'' ) " + " from tblaudithd a where MID(a.strTransCode,5,1) = '" + transYear + "' " + " and MID(a.strTransCode,6,1) = '" + transMonth + "' " + " and MID(a.strTransCode,1,2) = '" + locNo + "' and strClientCode='" + clientCode + "' " + "and a.strTransType='GRN(Good Receiving Note)' ;  ";

		}
		List listAudit = objGlobalFunctionsService.funGetListModuleWise(sqlAudit, "sql");
		long lastnoAudit;
		if (listAudit != null && !listAudit.isEmpty() && !listAudit.contains("")) {
			lastnoAudit = Integer.parseInt(listAudit.get(0).toString());

		} else {
			lastnoAudit = 0;
		}
		List list = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
		long lastnoLive;
		if (list != null && !list.isEmpty() && !list.contains("")) {
			lastnoLive = Integer.parseInt(list.get(0).toString());

		} else {
			lastnoLive = 0;
		}

		if (lastnoLive > lastnoAudit) {
			strDocNo = locNo + strDocLiteral + transYear + transMonth + String.format("%06d", lastnoLive + 1);
		} else {
			strDocNo = locNo + strDocLiteral + transYear + transMonth + String.format("%06d", lastnoAudit + 1);
		}
		return strDocNo;
	}

	public String funGenerateDocumentCodeWebBook(String strTransType, String dtTransDate, HttpServletRequest req) {

		String strDocNo = "";
		try
		{
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String propCode = req.getSession().getAttribute("propertyCode").toString();
			String startDate= req.getSession().getAttribute("startDate").toString();
			
			String transYear="A";
			List<clsCompanyMasterModel> listClsCompanyMasterModel = objSetupMasterService.funGetListCompanyMasterModel();
			if (listClsCompanyMasterModel.size() > 0) {
				clsCompanyMasterModel objCompanyMasterModel = listClsCompanyMasterModel.get(listClsCompanyMasterModel.size() - 1);
				transYear=objCompanyMasterModel.getStrYear();
			}
			
			
			if(dtTransDate.contains(" "))
			{
				dtTransDate=dtTransDate.split(" ")[0];
			}
			String[] spDate = dtTransDate.split("-");
			
			String transMonth = funGetAlphabet(Integer.parseInt(spDate[1])-1);
			
			String strDocLiteral = "";
			String sql = "";
			String sqlAudit = "";
	
			switch (strTransType) {
			case "frmPayment":
	
				strDocLiteral = "PT";
				sql = "select ifnull(max(MID(a.strVouchNo,7,6)),'0' )as strVouchNo  " + " from tblpaymenthd a where MID(a.strVouchNo,6,1) = '" + transMonth + "' " + " and MID(a.strVouchNo,5,1) = '" + transYear + "'  " + " and MID(a.strVouchNo,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";
	
				sqlAudit = "select ifnull(max(MID(a.strTransNo,7,6)),'0' )as strVouchNo  " + " from tblaudithd a where MID(a.strTransNo,6,1) = '" + transMonth + "' " + " and MID(a.strTransNo,5,1) = '" + transYear + "'  " + " and MID(a.strTransNo,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' " + " and strTransType='frmPayment' ";
				break;
	
			case "frmJV":
	
				strDocLiteral = "JV";
				sql = "select ifnull(max(MID(a.strVouchNo,7,6)),'0' )as strVouchNo  " + " from tbljvhd a where MID(a.strVouchNo,6,1) = '" + transMonth + "' " + " and MID(a.strVouchNo,5,1) = '" + transYear + "'  " + " and MID(a.strVouchNo,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";
	
				sqlAudit = "select ifnull(max(MID(a.strTransNo,7,6)),'0' )as strVouchNo  " + " from tblaudithd a where MID(a.strTransNo,6,1) = '" + transMonth + "' " + " and MID(a.strTransNo,5,1) = '" + transYear + "'  " + " and MID(a.strTransNo,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' " + " and strTransType='frmJV' ";
				break;
	
			case "frmReceipt":
	
				strDocLiteral = "RT";
				sql = "select ifnull(max(MID(a.strVouchNo,7,6)),'0' )as strVouchNo  " + " from tblreceipthd a where MID(a.strVouchNo,6,1) = '" + transMonth + "' " + " and MID(a.strVouchNo,5,1) = '" + transYear + "'  " + " and MID(a.strVouchNo,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";
	
				sqlAudit = "select ifnull(max(MID(a.strTransNo,7,6)),'0' )as strVouchNo  " + " from tblaudithd a where MID(a.strTransNo,6,1) = '" + transMonth + "' " + " and MID(a.strTransNo,5,1) = '" + transYear + "'  " + " and MID(a.strTransNo,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' " + " and strTransType='frmReceipt' ";
				break;
	
			}
			
			StringBuilder sbSql=new StringBuilder();
			sbSql.append(sqlAudit);
			List listAudit = objBaseService.funGetListModuleWise(sbSql, "sql","WebBooks");
			long lastnoAudit;
			if (listAudit != null && !listAudit.isEmpty() && !listAudit.contains("")) {
				lastnoAudit = Integer.parseInt(listAudit.get(0).toString());
	
			} else {
				lastnoAudit = 0;
			}
			
			sbSql.setLength(0);
			sbSql.append(sql);
			List list = objBaseService.funGetListModuleWise(sbSql, "sql","WebBooks");
			long lastnoLive;
			if (list != null && !list.isEmpty() && !list.contains("")) {
				lastnoLive = Integer.parseInt(list.get(0).toString());
	
			} else {
				lastnoLive = 0;
			}
	
			if (lastnoLive > lastnoAudit) {
				strDocNo = propCode + strDocLiteral + transYear + transMonth + String.format("%06d", lastnoLive + 1);
			} else {
				strDocNo = propCode + strDocLiteral + transYear + transMonth + String.format("%06d", lastnoAudit + 1);
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return strDocNo;
	}


	public long funGenerateDocumentCodeLastNo(String strTransType, String dtTransDate, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		String[] spDate = dtTransDate.split("-");
		Date dt = new Date();
		// String
		// years=String.valueOf((dt.getYear()+1900)-Integer.parseInt(spDate[2]));
		String transYear="A";
		List<clsCompanyMasterModel> listClsCompanyMasterModel = objSetupMasterService.funGetListCompanyMasterModel();
		if (listClsCompanyMasterModel.size() > 0) {
			clsCompanyMasterModel objCompanyMasterModel = listClsCompanyMasterModel.get(listClsCompanyMasterModel.size() - 1);
			transYear=objCompanyMasterModel.getStrYear();
		}
		
		//String years = String.valueOf(Integer.parseInt(spDate[2]));
		//String transYear = funGetAlphabet((Integer.parseInt(years) % 26));
		String transMonth = funGetAlphabet(Integer.parseInt(spDate[1]));
		String strDocLiteral = "";
		String sql = "";
		String sqlAudit = "";
		long lastno = 0;
		switch (strTransType) {
		case "frmGRN":

			strDocLiteral = "GR";
			sql = "select ifnull(max(MID(a.strGRNCode,7,6)),'' )as strGRNCode " + " from tblgrnhd a where MID(a.strGRNCode,5,1) = '" + transYear + "' " + " and MID(a.strGRNCode,6,1) = '" + transMonth + "' " + " and MID(a.strGRNCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";

			sqlAudit = " select ifnull(max(MID(a.strTransCode,7,6)),'' ) " + " from tblaudithd a where MID(a.strTransCode,5,1) = '" + transYear + "' " + " and MID(a.strTransCode,6,1) = '" + transMonth + "' " + " and MID(a.strTransCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' " + "and a.strTransType='GRN(Good Receiving Note)' ;  ";
			break;

		case "frmMaterialReturn":

			strDocLiteral = "MR";
			sql = "select ifnull(max(MID(a.strMRetCode,7,6)),'' ) as strMRetCode" + " from tblmaterialreturnhd a where MID(a.strMRetCode,5,1) = '" + transYear + "' " + " and MID(a.strMRetCode,6,1) = '" + transMonth + "' " + " and MID(a.strMRetCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";

			sqlAudit = " select ifnull(max(MID(a.strTransCode,7,6)),'' ) " + " from tblaudithd a where MID(a.strTransCode,5,1) = '" + transYear + "' " + " and MID(a.strTransCode,6,1) = '" + transMonth + "' " + " and MID(a.strTransCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' " + "and a.strTransType='Material Return' ;  ";
			break;

		case "frmOpeningStock":

			strDocLiteral = "OP";
			sql = "select ifnull(max(MID(a.strOpStkCode,7,6)),'' ) as strOpStkCode" + " from tblinitialinventory a where MID(a.strOpStkCode,5,1) = '" + transYear + "' " + " and MID(a.strOpStkCode,6,1) = '" + transMonth + "' " + " and MID(a.strOpStkCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";

			sqlAudit = " select ifnull(max(MID(a.strTransCode,7,6)),'' ) " + " from tblaudithd a where MID(a.strTransCode,5,1) = '" + transYear + "' " + " and MID(a.strTransCode,6,1) = '" + transMonth + "' " + " and MID(a.strTransCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' " + "and a.strTransType='Opening Stock' ;  ";

			break;

		case "frmPhysicalStkPosting":

			strDocLiteral = "PS";
			sql = "select ifnull(max(MID(a.strPSCode,7,6)),'' ) " + " from tblstockpostinghd a where MID(a.strPSCode,5,1) = '" + transYear + "' " + " and MID(a.strPSCode,6,1) = '" + transMonth + "' " + " and MID(a.strPSCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";

			sqlAudit = " select ifnull(max(MID(a.strTransCode,7,6)),'' ) " + " from tblaudithd a where MID(a.strTransCode,5,1) = '" + transYear + "' " + " and MID(a.strTransCode,6,1) = '" + transMonth + "' " + " and MID(a.strTransCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' " + "and a.strTransType='Physical Stk Posting' ;  ";

			break;

		case "frmPurchaseIndent":

			strDocLiteral = "PI";
			sql = "select ifnull(max(MID(a.strPIcode,7,6)),'' ) " + " from tblpurchaseindendhd a where MID(a.strPIcode,5,1) = '" + transYear + "' " + " and MID(a.strPIcode,6,1) = '" + transMonth + "' " + " and MID(a.strPIcode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";

			sqlAudit = " select ifnull(max(MID(a.strTransCode,7,6)),'' ) " + " from tblaudithd a where MID(a.strTransCode,5,1) = '" + transYear + "' " + " and MID(a.strTransCode,6,1) = '" + transMonth + "' " + " and MID(a.strTransCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' " + "and a.strTransType='Purchase Indent' ;  ";
			break;

		case "frmPurchaseOrder":

			strDocLiteral = "PO";
			sql = "select ifnull(max(MID(a.strPOCode,7,6)),'' ) " + " from tblpurchaseorderhd a where MID(a.strPOCode,5,1) = '" + transYear + "' " + " and MID(a.strPOCode,6,1) = '" + transMonth + "' " + " and MID(a.strPOCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";

			sqlAudit = " select ifnull(max(MID(a.strTransCode,7,6)),'' ) " + " from tblaudithd a where MID(a.strTransCode,5,1) = '" + transYear + "' " + " and MID(a.strTransCode,6,1) = '" + transMonth + "' " + " and MID(a.strTransCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' " + "and a.strTransType='Purchase Order' ;  ";

			break;

		case "frmPurchaseReturn":

			strDocLiteral = "PR";
			sql = "select ifnull(max(MID(a.strPRCode,7,6)),'' ) " + " from tblpurchasereturnhd a where MID(a.strPRCode,5,1) = '" + transYear + "' " + " and MID(a.strPRCode,6,1) = '" + transMonth + "' " + " and MID(a.strPRCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";

			sqlAudit = " select ifnull(max(MID(a.strTransCode,7,6)),'' ) " + " from tblaudithd a where MID(a.strTransCode,5,1) = '" + transYear + "' " + " and MID(a.strTransCode,6,1) = '" + transMonth + "' " + " and MID(a.strTransCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' " + "and a.strTransType='Purchase Return' ;  ";

			break;

		case "frmStockAdjustment":

			strDocLiteral = "SA";
			sql = "select ifnull(max(MID(a.strSACode,7,6)),'' ) " + " from tblstockadjustmenthd a where MID(a.strSACode,5,1) = '" + transYear + "' " + " and MID(a.strSACode,6,1) = '" + transMonth + "' " + " and MID(a.strSACode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";

			sqlAudit = " select ifnull(max(MID(a.strTransCode,7,6)),'' ) " + " from tblaudithd a where MID(a.strTransCode,5,1) = '" + transYear + "' " + " and MID(a.strTransCode,6,1) = '" + transMonth + "' " + " and MID(a.strTransCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' " + "and a.strTransType='Stock Adjustment' ;  ";

			break;

		case "frmWorkOrder":

			strDocLiteral = "WO";
			sql = "select ifnull(max(MID(a.strWOCode,7,6)),'' ) " + " from tblworkorderhd a where MID(a.strWOCode,5,1) = '" + transYear + "' " + " and MID(a.strWOCode,6,1) = '" + transMonth + "' " + " and MID(a.strWOCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";

			break;

		case "frmStockTransfer":

			strDocLiteral = "ST";
			sql = "select ifnull(max(MID(a.strSTCode,7,6)),'' ) " + " from tblstocktransferhd a where MID(a.strSTCode,5,1) = '" + transYear + "' " + " and MID(a.strSTCode,6,1) = '" + transMonth + "' " + " and MID(a.strSTCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";

			sqlAudit = " select ifnull(max(MID(a.strTransCode,7,6)),'' ) " + " from tblaudithd a where MID(a.strTransCode,5,1) = '" + transYear + "' " + " and MID(a.strTransCode,6,1) = '" + transMonth + "' " + " and MID(a.strTransCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' " + "and a.strTransType='Stock Transfer' ;  ";

			break;

		case "frmMIS":

			strDocLiteral = "MI";
			sql = "select ifnull(max(MID(a.strMISCode,7,6)),'' ) " + " from tblmishd a where MID(a.strMISCode,5,1) = '" + transYear + "' " + " and MID(a.strMISCode,6,1) = '" + transMonth + "' " + " and MID(a.strMISCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";

			sqlAudit = " select ifnull(max(MID(a.strTransCode,7,6)),'' ) " + " from tblaudithd a where MID(a.strTransCode,5,1) = '" + transYear + "' " + " and MID(a.strTransCode,6,1) = '" + transMonth + "' " + " and MID(a.strTransCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' " + "and a.strTransType='Material Issue Slip' ;  ";

			break;

		case "frmMaterialReq":

			strDocLiteral = "RE";
			sql = "select ifnull(max(MID(a.strReqCode,7,6)),'' ) " + " from tblreqhd a where MID(a.strReqCode,5,1) = '" + transYear + "' " + " and MID(a.strReqCode,6,1) = '" + transMonth + "' " + " and MID(a.strReqCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";

			sqlAudit = " select ifnull(max(MID(a.strTransCode,7,6)),'' ) " + " from tblaudithd a where MID(a.strTransCode,5,1) = '" + transYear + "' " + " and MID(a.strTransCode,6,1) = '" + transMonth + "' " + " and MID(a.strTransCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' " + "and a.strTransType='Material Requisition' ;  ";

			break;

		case "frmProduction":

			strDocLiteral = "PD";
			sql = "select ifnull(max(MID(a.strPDCode,7,6)),'' ) " + " from tblproductionhd a where MID(a.strPDCode,5,1) = '" + transYear + "' " + " and MID(a.strPDCode,6,1) = '" + transMonth + "' " + " and MID(a.strPDCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";

			break;

		case "frmBillPassing":

			strDocLiteral = "BP";
			sql = "select ifnull(max(MID(a.strBillPassNo,7,6)),'' ) " + " from tblbillpasshd a where MID(a.strBillPassNo,5,1) = '" + transYear + "' " + " and MID(a.strBillPassNo,6,1) = '" + transMonth + "' " + " and MID(a.strBillPassNo,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";
			break;

		case "frmProductionOrder":

			strDocLiteral = "MP";
			sql = "select ifnull(max(MID(a.strOPCode,7,6)),'' ) " + " from tblproductionorderhd a where MID(a.strOPCode,5,1) = '" + transYear + "' " + " and MID(a.strOPCode,6,1) = '" + transMonth + "' " + " and MID(a.strOPCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";
			break;

		case "frmJV":

			strDocLiteral = "JV";
			sql = "select ifnull(max(MID(a.strVouchNo,7,6)),'' ) " + " from tbljvhd a where MID(a.strVouchNo,5,1) = '" + transYear + "' " + " and MID(a.strVouchNo,6,1) = '" + transMonth + "' " + " and MID(a.strVouchNo,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";
			break;

		case "frmDN":

			strDocLiteral = "DN";
			sql = "select ifnull(max(MID(a.strDNCode,7,6)),'' ) " + " from tbldeliverynotehd a where MID(a.strDNCode,5,1) = '" + transYear + "' " + " and MID(a.strDNCode,6,1) = '" + transMonth + "' " + " and MID(a.strDNCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";
			break;

		case "frmExciseBillGneneration":

			strDocLiteral = "EX";
			sql = "select ifnull(max(MID(a.strBillNo,7,6)),'' )as strGRNCode " + " from tblgrnhd a where MID(a.strBillNo,5,1) = '" + transYear + "' " + " and MID(a.strBillNo,6,1) = '" + transMonth + "' " + " and MID(a.strBillNo,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";

			break;

		case "frmSalesReturn":

			strDocLiteral = "SR";
			sql = "select ifnull(max(MID(a.strSRCode,7,6)),'' ) " + " from tblsalesreturnhd a where MID(a.strSRCode,5,1) = '" + transYear + "' " + " and MID(a.strSRCode,6,1) = '" + transMonth + "' " + " and MID(a.strSRCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";

			break;

		case "frmInvoice":

			strDocLiteral = "IV";
			sql = "select ifnull(max(MID(a.strInvCode,7,6)),'' ) " + " from tblinvoicehd a where MID(a.strInvCode,5,1) = '" + transYear + "' " + " and MID(a.strInvCode,6,1) = '" + transMonth + "' " + " and MID(a.strInvCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";
			break;

		case "frmSC":

			strDocLiteral = "SC";
			sql = "select ifnull(max(MID(a.strVoucherNo,7,6)),'' ) " + " from tblscbillhd a where MID(a.strVoucherNo,5,1) = '" + transYear + "' " + " and MID(a.strVoucherNo,6,1) = '" + transMonth + "' " + " and MID(a.strVoucherNo,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";
			break;

		case "frmDS":

			strDocLiteral = "DS";
			sql = "select ifnull(max(MID(a.strDSCode,7,6)),'' ) " + " from tbldeliveryschedulehd a where MID(a.strDSCode,5,1) = '" + transYear + "' " + " and MID(a.strDSCode,6,1) = '" + transMonth + "' " + " and MID(a.strDSCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";

			sqlAudit = " select ifnull(max(MID(a.strTransCode,7,6)),'' ) " + " from tblaudithd a where MID(a.strTransCode,5,1) = '" + transYear + "' " + " and MID(a.strTransCode,6,1) = '" + transMonth + "' " + " and MID(a.strTransCode,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' " + "and a.strTransType='Invoice' ;  ";
			break;

		}
		List listAudit = objGlobalFunctionsService.funGetListModuleWise(sqlAudit, "sql");
		long lastnoAudit = 0;
		long retLastNo = 0;
		if (listAudit != null && !listAudit.isEmpty() && !listAudit.contains("")) {
			lastnoAudit = Integer.parseInt(listAudit.get(0).toString());

		}
		List list = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
		if (list != null && !list.isEmpty() && !list.contains("")) {
			lastno = Integer.parseInt(list.get(0).toString());
		}

		if (lastno > lastnoAudit) {
			retLastNo = lastno + 1;
		} else {
			retLastNo = lastnoAudit + 1;
		}

		return retLastNo;
	}
	
	/**
	 * Generate Document Code
	 * 
	 * @param strTransType
	 * @param dtTransDate
	 * @param req
	 * @return
	 */


	public String funGeneratePMSDocumentCode(String strTransType, String dtTransDate, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		String[] spDate = dtTransDate.split("-");
		Date dt = new Date();
		//String years = String.valueOf((dt.getYear() + 1900) - Integer.parseInt(spDate[2]));
		//String transYear = funGetAlphabet(Integer.parseInt(years));
		String transYear="A";
		List<clsCompanyMasterModel> listClsCompanyMasterModel = objSetupMasterService.funGetListCompanyMasterModel();
		if (listClsCompanyMasterModel.size() > 0) {
			clsCompanyMasterModel objCompanyMasterModel = listClsCompanyMasterModel.get(listClsCompanyMasterModel.size() - 1);
			transYear=objCompanyMasterModel.getStrYear();
		}
		
		String transMonth = funGetAlphabet(Integer.parseInt(spDate[1]) - 1);
		String strDocLiteral = "";
		String sql = "";
		String strDocNo = "";

		switch (strTransType) {
		case "frmCheckIn":
			strDocLiteral = "CH";
			sql = "select ifnull(max(MID(a.strCheckInNo,7,6)),'' )as strCheckInNo " + " from tblcheckinhd a where MID(a.strCheckInNo,5,1) = '" + transYear + "' " + " and MID(a.strCheckInNo,6,1) = '" + transMonth + "' " + " and MID(a.strCheckInNo,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";
			break;

		case "frmCheckInRegNo":
			strDocLiteral = "RG";
			sql = "select ifnull(max(MID(a.strRegistrationNo,7,6)),'' )as strRegistrationNo " + " from tblcheckinhd a where MID(a.strRegistrationNo,5,1) = '" + transYear + "' " + " and MID(a.strRegistrationNo,6,1) = '" + transMonth + "' " + " and MID(a.strRegistrationNo,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";
			break;

		case "frmReservation":
			strDocLiteral = "RS";
			sql = "select ifnull(max(MID(a.strReservationNo,7,6)),'' )as strReservationNo " + " from tblreservationhd a where MID(a.strReservationNo,5,1) = '" + transYear + "' " + " and MID(a.strReservationNo,6,1) = '" + transMonth + "' " + " and MID(a.strReservationNo,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";
			break;

		case "frmWalkin":
			strDocLiteral = "WK";
			sql = "select ifnull(max(MID(a.strWalkinNo,7,6)),'' )as strWalkinNo " + " from tblwalkinhd a where MID(a.strWalkinNo,5,1) = '" + transYear + "' " + " and MID(a.strWalkinNo,6,1) = '" + transMonth + "' " + " and MID(a.strWalkinNo,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";
			break;

		case "frmFolio":
			strDocLiteral = "FL";
			sql = "select ifnull(max(MID(a.strFolioNo,7,6)),'' )as strFolioNo " + " from tblfoliohd a where MID(a.strFolioNo,5,1) = '" + transYear + "' " + " and MID(a.strFolioNo,6,1) = '" + transMonth + "' " + " and MID(a.strFolioNo,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";
			break;

		case "frmBillHd":
			strDocLiteral = "B";
			sql = "select ifnull(max(MID(a.strBillNo,6,6)),'' )as strBillNo " + " from tblbillhd a where MID(a.strBillNo,4,1) = '" + transYear + "' " + " and MID(a.strBillNo,5,1) = '" + transMonth + "' " + " and MID(a.strBillNo,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";
			break;

		case "frmPMSPayment":
			strDocLiteral = "PR";
			sql = "select ifnull(max(MID(a.strReceiptNo,7,6)),'' )as strReceiptNo " + " from tblreceipthd a where MID(a.strReceiptNo,5,1) = '" + transYear + "' " + " and MID(a.strReceiptNo,6,1) = '" + transMonth + "' " + " and MID(a.strReceiptNo,1,2) = '" + propCode + "' and strClientCode='" + clientCode + "' ";
			break;
		}
		List list = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
		if (list != null && !list.isEmpty() && !list.contains("")) {
			long lastno = Integer.parseInt(list.get(0).toString());
			strDocNo = propCode + strDocLiteral + transYear + transMonth + String.format("%06d", lastno + 1);
		} else {
			strDocNo = propCode + strDocLiteral + transYear + transMonth + String.format("%06d", 1);
		}
		return strDocNo;
	}





	
	public String funGetTime(String inputTime, String pattern) {
		String time = "";

		if (pattern.equals("hh:mm:ss")) {
			String[] arrTime = inputTime.split(":");
			int hours = Integer.parseInt(arrTime[0]);
			int min = Integer.parseInt(arrTime[1].substring(1, 2));
			String ampm = arrTime[1].substring(2, 4);
			if (ampm.equalsIgnoreCase("pm")) {
				hours += 12;
			}
		}

		return time;
	}

	public String[] funGetSplitedDate(String date) {
		return date.split("/");
	}

	public String funIfNull(String input, String defaultValue, String assignedValue) {
		String op = "notnull";
		if (null == input) {
			op = defaultValue;
		} else {
			op = assignedValue;
		}
		return op;
	}

	public Date funStringToDate(String strDate) {

		Date date = new Date();
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			date = formatter.parse(strDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * Auto save Stock Adjustment
	 * 
	 * @param strphyStkpostCode
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@SuppressWarnings("rawtypes")
	public String funGenerateStkAdjustement(String strphyStkpostCode, HttpServletRequest request) throws ParseException {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String propCode = request.getSession().getAttribute("propertyCode").toString();
		// String
		// startDate=request.getSession().getAttribute("startDate").toString();
		long lastNo = 0;
		String strSACode = null;
		clsStkAdjustmentHdModel objHdModel = new clsStkAdjustmentHdModel();
		clsStkPostingHdModel ModelStkPostHd = objStkPostService.funGetModelObject(strphyStkpostCode, clientCode);
		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propCode, clientCode);
		if (ModelStkPostHd != null) {
			if (ModelStkPostHd.getStrAuthorise().equals("Yes")) {
				Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(ModelStkPostHd.getDtPSDate());
				String PSDate = new SimpleDateFormat("dd-MM-yyyy").format(date);

				strSACode = funGenerateDocumentCode("frmStockAdjustment", PSDate, request);
				objHdModel = new clsStkAdjustmentHdModel(new clsStkAdjustmentHdModel_ID(strSACode, clientCode));
				objHdModel.setIntId(lastNo);

				objHdModel.setStrUserCreated(ModelStkPostHd.getStrUserCreated());
				objHdModel.setDtCreatedDate(funGetCurrentDateTime("yyyy-MM-dd"));
				objHdModel.setDtSADate(ModelStkPostHd.getDtPSDate());
				objHdModel.setStrLocCode(ModelStkPostHd.getStrLocCode());
				objHdModel.setStrNarration("Auto Generated By Physical Stock Posting Code :" + strphyStkpostCode);
				objHdModel.setStrAuthorise("Yes");
				objHdModel.setStrReasonCode(objSetup.getStrStkAdjReason());
				objHdModel.setDblTotalAmt(ModelStkPostHd.getDblTotalAmt());
				objHdModel.setStrUserModified(ModelStkPostHd.getStrUserModified());
				objHdModel.setDtLastModified(ModelStkPostHd.getDtLastModified());
				objHdModel.setStrConversionUOM(ModelStkPostHd.getStrConversionUOM());
				objStkAdjService.funDeleteDtl(strSACode, clientCode);

				objStkAdjService.funAddUpdate(objHdModel);

				List listStkDtl = objStkPostService.funGetDtlList(strphyStkpostCode, clientCode);
				for (int i = 0; i < listStkDtl.size(); i++) {
					Object[] ob1 = (Object[]) listStkDtl.get(i);
					clsStkPostingDtlModel stkPostDtl = (clsStkPostingDtlModel) ob1[0];
					clsStkAdjustmentDtlModel objStkAdjDtl = new clsStkAdjustmentDtlModel();
					objStkAdjDtl.setStrSACode(strSACode);
					objStkAdjDtl.setStrProdCode(stkPostDtl.getStrProdCode());
					objStkAdjDtl.setStrProdChar(stkPostDtl.getStrProdChar());
					objStkAdjDtl.setDblRate(stkPostDtl.getDblPrice());
					objStkAdjDtl.setDblWeight(stkPostDtl.getDblWeight());
					double PSQty = stkPostDtl.getDblPStock();
					double CStk = stkPostDtl.getDblCStock();
					double stkAdjQty = PSQty - CStk;
					if (stkAdjQty > 0) {
						objStkAdjDtl.setStrType("IN");
						objStkAdjDtl.setDblQty(stkAdjQty);
					} else {
						objStkAdjDtl.setStrType("OUT");
						objStkAdjDtl.setDblQty(-1 * (stkAdjQty));
					}
					objStkAdjDtl.setStrRemark("Auto Adjustment by Physical Stock Posting");
					objStkAdjDtl.setIntIndex(0);
					objStkAdjDtl.setDblPrice(stkPostDtl.getDblPrice() * objStkAdjDtl.getDblQty());
					objStkAdjDtl.setStrClientCode(stkPostDtl.getStrClientCode());
					objStkAdjDtl.setStrDisplayQty(stkPostDtl.getStrDisplyQty());
					objStkAdjDtl.setStrWSLinkedProdCode("");
					objStkAdjService.funAddUpdateDtl(objStkAdjDtl);
				}
				String hql = "UPDATE clsStkPostingHdModel SET strSACode='" + strSACode + "' WHERE strPSCode='" + strphyStkpostCode + "' and strClientCode='" + clientCode + "' ";
				objGlobalFunctionsService.funUpdate(hql, "hql");
			}
		}
		return strSACode;
	}

	public String funUpdateStkAdjustement(String strphyStkpostCode, String beanSACode, HttpServletRequest request) throws ParseException {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String propCode = request.getSession().getAttribute("propertyCode").toString();
		// String
		// startDate=request.getSession().getAttribute("startDate").toString();
		long lastNo = 0;
		String strSACode = beanSACode;
		clsStkAdjustmentHdModel objHdModel = new clsStkAdjustmentHdModel();
		clsStkPostingHdModel ModelStkPostHd = objStkPostService.funGetModelObject(strphyStkpostCode, clientCode);
		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propCode, clientCode);
		if (ModelStkPostHd != null) {
			if (ModelStkPostHd.getStrAuthorise().equals("Yes")) {
				Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(ModelStkPostHd.getDtPSDate());
				String PSDate = new SimpleDateFormat("dd-MM-yyyy").format(date);
				strSACode = funSaveUpdateStkAdjEntry(strSACode, clientCode, ModelStkPostHd, objSetup.getStrStkAdjReason());
			}
		}
		return beanSACode;
	}

	private String funSaveUpdateStkAdjEntry(String tempSACode, String clientCode, clsStkPostingHdModel objStkPostingHdModel, String reason) {
		String strSACode = tempSACode;

		objStkAdjService.funDeleteHd(strSACode, clientCode);
		objStkAdjService.funDeleteDtl(strSACode, clientCode);

		long lastNo = Long.parseLong(strSACode.substring(6, strSACode.length()));
		clsStkAdjustmentHdModel objHdModel = new clsStkAdjustmentHdModel(new clsStkAdjustmentHdModel_ID(strSACode, clientCode));
		objHdModel.setIntId(lastNo);
		objHdModel.setStrUserCreated(objStkPostingHdModel.getStrUserCreated());
		objHdModel.setDtCreatedDate(funGetCurrentDateTime("yyyy-MM-dd"));
		objHdModel.setDtSADate(objStkPostingHdModel.getDtPSDate());
		objHdModel.setStrLocCode(objStkPostingHdModel.getStrLocCode());
		objHdModel.setStrNarration("Auto Generated By Physical Stock Posting Code :" + objStkPostingHdModel.getStrPSCode());
		objHdModel.setStrAuthorise("Yes");
		objHdModel.setStrReasonCode(reason);
		objHdModel.setDblTotalAmt(objStkPostingHdModel.getDblTotalAmt());
		objHdModel.setStrUserModified(objStkPostingHdModel.getStrUserModified());
		objHdModel.setDtLastModified(objStkPostingHdModel.getDtLastModified());
		objHdModel.setStrConversionUOM(objStkPostingHdModel.getStrConversionUOM());
		objStkAdjService.funAddUpdate(objHdModel);

		List listStkDtl = objStkPostService.funGetDtlList(objStkPostingHdModel.getStrPSCode(), clientCode);
		for (int i = 0; i < listStkDtl.size(); i++) {
			Object[] ob1 = (Object[]) listStkDtl.get(i);
			clsStkPostingDtlModel stkPostDtl = (clsStkPostingDtlModel) ob1[0];
			clsStkAdjustmentDtlModel objStkAdjDtl = new clsStkAdjustmentDtlModel();
			objStkAdjDtl.setStrSACode(strSACode);
			objStkAdjDtl.setStrProdCode(stkPostDtl.getStrProdCode());
			objStkAdjDtl.setStrProdChar(stkPostDtl.getStrProdChar());
			objStkAdjDtl.setDblRate(stkPostDtl.getDblPrice());
			objStkAdjDtl.setDblWeight(stkPostDtl.getDblWeight());
			double PSQty = stkPostDtl.getDblPStock();
			double CStk = stkPostDtl.getDblCStock();
			double stkAdjQty = PSQty - CStk;
			if (stkAdjQty > 0) {
				objStkAdjDtl.setStrType("IN");
				objStkAdjDtl.setDblQty(stkAdjQty);
			} else {
				objStkAdjDtl.setStrType("OUT");
				objStkAdjDtl.setDblQty(-1 * (stkAdjQty));
			}
			objStkAdjDtl.setStrRemark("Auto Adjustment by Physical Stock Posting");
			objStkAdjDtl.setIntIndex(0);
			objStkAdjDtl.setDblPrice(stkPostDtl.getDblPrice() * objStkAdjDtl.getDblQty());
			objStkAdjDtl.setStrClientCode(stkPostDtl.getStrClientCode());
			objStkAdjDtl.setStrDisplayQty(stkPostDtl.getStrDisplyQty());
			objStkAdjDtl.setStrWSLinkedProdCode("");
			objStkAdjService.funAddUpdateDtl(objStkAdjDtl);
		}
		String hql = "UPDATE clsStkPostingHdModel SET strSACode='" + strSACode + "' WHERE strPSCode='" + objStkPostingHdModel.getStrPSCode() + "' and strClientCode='" + clientCode + "' ";
		objGlobalFunctionsService.funUpdate(hql, "hql");

		return strSACode;

	}

	public clsProductMasterModel funGetTaxAmount(String prodCode, String clientCode) {
		return objGlobalFunctionsService.funGetTaxAmount(prodCode, clientCode);
	}

	@SuppressWarnings("deprecation")
	public void funInvokeStockFlash(String startDate, String locCode, String fromDate, String toDate, String clientCode, String userCode, String stockableItem, HttpServletRequest req, HttpServletResponse resp) {
		funDeleteAndInsertStkTempTable(clientCode, userCode, locCode, stockableItem);
		if (!startDate.equals(fromDate)) {
			String tempFromDate = fromDate.split("-")[2] + "-" + fromDate.split("-")[1] + "-" + fromDate.split("-")[0];
			SimpleDateFormat obj = new SimpleDateFormat("dd-MM-yyyy");
			Date dt1;

			try {
				dt1 = obj.parse(tempFromDate);
				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime(dt1);
				cal.add(Calendar.DATE, -1);
				String newToDate = (cal.getTime().getYear() + 1900) + "-" + (cal.getTime().getMonth() + 1) + "-" + (cal.getTime().getDate());

				funProcessStock(locCode, startDate, newToDate, clientCode, userCode, req, resp);

				String sql = "Update tblcurrentstock set dblOpeningStk=dblClosingStk, dblGRN=0, dblSCGRN=0" + ", dblStkTransIn=0, dblStkAdjIn=0, dblMISIn=0, dblMaterialReturnIn=0, dblQtyProduced=0" + ", dblStkTransOut=0, dblStkAdjOut=0, dblMISOut=0, dblQtyConsumed=0, dblSales=0" + ", dblMaterialReturnOut=0, dblDeliveryNote=0, dblPurchaseReturn=0 " + " where strUserCode='" + userCode
						+ "' and strClientCode='" + clientCode + "' ";
				objGlobalFunctionsService.funUpdate(sql, "sql");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		funProcessStock(locCode, fromDate, toDate, clientCode, userCode, req, resp);
	}

	public void funDeleteAndInsertStkTempTable(String clientCode, String userCode, String locCode, String stockableItem) {
		objGlobalFunctionsService.funDeleteCurrentStock(clientCode, userCode);
		objGlobalFunctionsService.funAddCurrentStock(clientCode, userCode, locCode, stockableItem);
	}

	
	@SuppressWarnings("rawtypes")
	public int funProcessStock(String locCode, String fromDate, String toDate, String clientCode, String userCode, HttpServletRequest req, HttpServletResponse resp) {
		
		String propertyCode = "";
		String sql = "select strPropertyCode from tbllocationmaster " + " where strLocCode='" + locCode + "' and strClientCode='" + clientCode + "'";
		List listProperty = objGlobalFunctionsService.funGetList(sql, "sql");
		Iterator itrPropCode = listProperty.iterator();
		if (itrPropCode.hasNext()) {
			propertyCode = itrPropCode.next().toString();
		}

		Map<String, String> hmAuthorisedForms = new HashMap<String, String>();
		sql = "select strFormName from tblworkflowforslabbasedauth " + "where strPropertyCode='" + propertyCode + "' and strClientCode='" + clientCode + "'";
		List list = objGlobalFunctionsService.funGetList(sql, "sql");
		Iterator itr = list.iterator();
		while (itr.hasNext()) {
			String formName = itr.next().toString();
			hmAuthorisedForms.put(formName, formName);
		}

		objGlobalFunctionsService.funDeleteTempItemStock(clientCode, userCode);
		String hql = "insert into clsTempItemStockModel(strProdCode,dblQty,strClientCode,strUserCode) " + "select b.strProdCode,sum(b.dblQty-b.dblRejected),'" + clientCode + "','" + userCode + "' " + "from clsGRNHdModel a,clsGRNDtlModel b " + "where a.strGRNCode=b.strGRNCode and date(a.dtGRNDate) between '" + fromDate + "' and '" + toDate + "' ";
		if (!locCode.equalsIgnoreCase("All")) {
			hql += "and a.strLocCode='" + locCode + "' ";
		}
		if (null != hmAuthorisedForms.get("frmGRN")) {
			hql += "and a.strAuthorise='Yes' ";
		}
		hql += "group by b.strProdCode";
		objGlobalFunctionsService.funAddTempItemStock(hql, "hql");
		/*
		 * hql=
		 * "Update clsCurrentStockModel a Set a.dblGRN = IFNULL((select b.dblQty from clsTempItemStockModel b "
		 * +"where a.strProdCode = b.strProdCode and b.dblQty),0)";
		 */
		hql = "Update tblcurrentstock a Set a.dblGRN = a.dblGRN + IFNULL((select b.dblQty from tbltempitemstock b " + "where a.strProdCode = b.strProdCode and b.strClientCode='" + clientCode + "' and b.strUserCode='" + userCode + "' and b.dblQty ),0)";
		objGlobalFunctionsService.funUpdateCurrentStock(hql);

		// Delete TempItemStock Table
		objGlobalFunctionsService.funDeleteTempItemStock(clientCode, userCode);
		// Insert Opening Stock Qty into tblTempItemStock Table
		hql = "insert into clsTempItemStockModel(strProdCode,dblQty,strClientCode,strUserCode) " + "select b.strProdCode,sum(b.dblQty),'" + clientCode + "','" + userCode + "' " + "from clsInitialInventoryModel a,clsOpeningStkDtl b " + "where a.strOpStkCode=b.strOpStkCode and date(a.dtCreatedDate) between '" + fromDate + "' and '" + toDate + "' ";
		if (!locCode.equalsIgnoreCase("All")) {
			hql += "and a.strLocCode='" + locCode + "' ";
		}
		if (null != hmAuthorisedForms.get("frmOpeningStock")) {
			hql += "and a.strAuthorise='Yes' ";
		}

		hql += "group by b.strProdCode";
		objGlobalFunctionsService.funAddTempItemStock(hql, "hql");
		// Update Opening Stock Qty in tblCurrentStock table
		hql = "Update tblcurrentstock a Set a.dblOpeningStk = a.dblOpeningStk + IFNULL((select b.dblQty from tbltempitemstock b " + "where a.strProdCode = b.strProdCode and b.strClientCode='" + clientCode + "' and b.strUserCode='" + userCode + "' and b.dblQty),0)";
		objGlobalFunctionsService.funUpdateCurrentStock(hql);

		if (!locCode.equalsIgnoreCase("All")) {
			// Delete TempItemStock Table
			objGlobalFunctionsService.funDeleteTempItemStock(clientCode, userCode);
			// Insert Stock Transfer In Qty into tblTempItemStock Table
			hql = "insert into clsTempItemStockModel(strProdCode,dblQty,strClientCode,strUserCode) " + "select b.strProdCode,sum(b.dblQty),'" + clientCode + "','" + userCode + "' " + "from clsStkTransferHdModel a,clsStkTransferDtlModel b " + "where a.strSTCode=b.strSTCode and date(a.dtSTDate) between '" + fromDate + "' and '" + toDate + "' " + "and a.strToLocCode='" + locCode + "' ";
			if (null != hmAuthorisedForms.get("frmStockTransfer")) {
				hql += "and a.strAuthorise='Yes' ";
			}
			hql += "group by b.strProdCode";
			objGlobalFunctionsService.funAddTempItemStock(hql, "hql");
			// Update Stock Transfer In Qty in tblCurrentStock table
			hql = "Update tblcurrentstock a Set a.dblStkTransIn = a.dblStkTransIn + IFNULL((select b.dblQty from tbltempitemstock b " + "where a.strProdCode = b.strProdCode and b.strClientCode='" + clientCode + "' and b.strUserCode='" + userCode + "' and b.dblQty),0)";
			objGlobalFunctionsService.funUpdateCurrentStock(hql);

			// Delete TempItemStock Table
			objGlobalFunctionsService.funDeleteTempItemStock(clientCode, userCode);
			// Insert MIS In Qty into tblTempItemStock Table
			hql = "insert into tbltempitemstock(strProdCode,dblQty,strClientCode,strUserCode) " + "select b.strProdCode,sum(b.dblQty),'" + clientCode + "','" + userCode + "' " + "from tblmishd a,tblmisdtl b " + "where a.strMISCode=b.strMISCode and date(a.dtMISDate) between '" + fromDate + "' and '" + toDate + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				hql += "and a.strLocTo='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmMIS")) {
				hql += "and a.strAuthorise='Yes' ";
			}
			hql += "group by b.strProdCode";
			objGlobalFunctionsService.funAddTempItemStock(hql, "sql");
			// Update MIS In Qty in tblCurrentStock table
			hql = "Update tblcurrentstock a Set a.dblMISIn = a.dblMISIn + IFNULL((select b.dblQty from tbltempitemstock b " + "where a.strProdCode = b.strProdCode and b.strClientCode='" + clientCode + "' and b.strUserCode='" + userCode + "' and b.dblQty),0)";
			objGlobalFunctionsService.funUpdateCurrentStock(hql);

			// Delete TempItemStock Table
			objGlobalFunctionsService.funDeleteTempItemStock(clientCode, userCode);
			// Insert SalesReturn Qty into tblTempItemStock Table
			hql = "insert into tbltempitemstock(strProdCode,dblQty,strClientCode,strUserCode) " + "select b.strProdCode,sum(b.dblQty),'" + clientCode + "','" + userCode + "' " + "from tblsalesreturnhd a,tblsalesreturndtl b " + "where a.strSRCode=b.strSRCode and date(a.dteSRDate) between '" + fromDate + "' and '" + toDate + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				hql += "and a.strLocCode='" + locCode + "' ";
			}
			hql += "group by b.strProdCode";
			objGlobalFunctionsService.funAddTempItemStock(hql, "sql");
			// Update SalesReturn Qty in tblCurrentStock table
			hql = "Update tblcurrentstock a Set a.dblSalesReturn = a.dblSalesReturn + IFNULL((select b.dblQty from tbltempitemstock b " + "where a.strProdCode = b.strProdCode and b.strClientCode='" + clientCode + "' and b.strUserCode='" + userCode + "' and b.dblQty),0)";
			objGlobalFunctionsService.funUpdateCurrentStock(hql);

			// Delete TempItemStock Table
			objGlobalFunctionsService.funDeleteTempItemStock(clientCode, userCode);
			// Insert Material In Qty into tblTempItemStock Table
			hql = "insert into clsTempItemStockModel(strProdCode,dblQty,strClientCode,strUserCode) " + "select b.strProdCode,sum(b.dblQty),'" + clientCode + "','" + userCode + "' " + "from clsMaterialReturnHdModel a,clsMaterialReturnDtlModel b " + "where a.strMRetCode=b.strMRetCode and date(a.dtMRetDate) between '" + fromDate + "' and '" + toDate + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				hql += "and a.strLocTo='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmMaterialReturn")) {
				hql += "and a.strAuthorise='Yes' ";
			}
			hql += "group by b.strProdCode";
			objGlobalFunctionsService.funAddTempItemStock(hql, "hql");
			// Update Material In Qty in tblCurrentStock table
			hql = "Update tblcurrentstock a Set a.dblMaterialReturnIn = a.dblMaterialReturnIn + IFNULL((select b.dblQty from tbltempitemstock b " + "where a.strProdCode = b.strProdCode and b.strClientCode='" + clientCode + "' and b.strUserCode='" + userCode + "' and b.dblQty),0)";
			objGlobalFunctionsService.funUpdateCurrentStock(hql);

			// Delete TempItemStock Table
			objGlobalFunctionsService.funDeleteTempItemStock(clientCode, userCode);
			// Insert Stock Transfer Out Qty into tblTempItemStock Table
			hql = "insert into clsTempItemStockModel(strProdCode,dblQty,strClientCode,strUserCode) " + "select b.strProdCode,sum(b.dblQty),'" + clientCode + "','" + userCode + "' " + "from clsStkTransferHdModel a,clsStkTransferDtlModel b " + "where a.strSTCode=b.strSTCode and date(a.dtSTDate) between '" + fromDate + "' and '" + toDate + "' " + "and a.strFromLocCode='" + locCode + "' ";
			if (null != hmAuthorisedForms.get("frmStockTransfer")) {
				hql += "and a.strAuthorise='Yes' ";
			}
			hql += "group by b.strProdCode";
			objGlobalFunctionsService.funAddTempItemStock(hql, "hql");
			// Update Stock Transfer Out Qty in tblCurrentStock table
			hql = "Update tblcurrentstock a Set a.dblStkTransOut = a.dblStkTransOut + IFNULL((select b.dblQty from tbltempitemstock b " + "where a.strProdCode = b.strProdCode and b.strClientCode='" + clientCode + "' and b.strUserCode='" + userCode + "' and b.dblQty),0)";
			objGlobalFunctionsService.funUpdateCurrentStock(hql);

			// Delete TempItemStock Table
			objGlobalFunctionsService.funDeleteTempItemStock(clientCode, userCode);
			// Insert MIS Out Qty into tblTempItemStock Table
			hql = "insert into tbltempitemstock(strProdCode,dblQty,strClientCode,strUserCode) " + "select b.strProdCode,sum(b.dblQty),'" + clientCode + "','" + userCode + "' " + "from tblmishd a,tblmisdtl b " + "where a.strMISCode=b.strMISCode and date(a.dtMISDate) between '" + fromDate + "' and '" + toDate + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				hql += "and a.strLocFrom='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmMIS")) {
				hql += "and a.strAuthorise='Yes' ";
			}
			hql += "group by b.strProdCode";
			objGlobalFunctionsService.funAddTempItemStock(hql, "sql");
			// Update MIS Out Qty in tblCurrentStock table
			hql = "Update tblcurrentstock a Set a.dblMISOut = a.dblMISOut + IFNULL((select b.dblQty from tbltempitemstock b " + "where a.strProdCode = b.strProdCode and b.strClientCode='" + clientCode + "' and b.strUserCode='" + userCode + "' and b.dblQty),0)";
			objGlobalFunctionsService.funUpdateCurrentStock(hql);

			// Delete TempItemStock Table
			objGlobalFunctionsService.funDeleteTempItemStock(clientCode, userCode);
			// Insert Material Out Qty into tblTempItemStock Table
			hql = "insert into clsTempItemStockModel(strProdCode,dblQty,strClientCode,strUserCode) " + "select b.strProdCode,sum(b.dblQty),'" + clientCode + "','" + userCode + "' " + "from clsMaterialReturnHdModel a,clsMaterialReturnDtlModel b " + "where a.strMRetCode=b.strMRetCode and date(a.dtMRetDate) between '" + fromDate + "' and '" + toDate + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				hql += "and a.strLocFrom='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmMaterialReturn")) {
				hql += "and a.strAuthorise='Yes' ";
			}
			hql += "group by b.strProdCode";
			objGlobalFunctionsService.funAddTempItemStock(hql, "hql");
			// Update Material In Qty in tblCurrentStock table
			hql = "Update tblcurrentstock a Set a.dblMaterialReturnOut = a.dblMaterialReturnOut + IFNULL((select b.dblQty from tbltempitemstock b " + "where a.strProdCode = b.strProdCode and b.strClientCode='" + clientCode + "' and b.strUserCode='" + userCode + "' and b.dblQty),0)";
			objGlobalFunctionsService.funUpdateCurrentStock(hql);
		}

		// Delete TempItemStock Table
		objGlobalFunctionsService.funDeleteTempItemStock(clientCode, userCode);
		// Insert Stock Adjustment In Qty into tblTempItemStock Table
		hql = "insert into clsTempItemStockModel(strProdCode,dblQty,strClientCode,strUserCode) " + "select b.strProdCode,sum(b.dblQty),'" + clientCode + "','" + userCode + "' " + "from clsStkAdjustmentHdModel a,clsStkAdjustmentDtlModel b " + "where a.strSACode=b.strSACode and date(a.dtSADate) between '" + fromDate + "' and '" + toDate + "' and b.strType='In' ";
		if (!locCode.equalsIgnoreCase("All")) {
			hql += "and a.strLocCode='" + locCode + "' ";
		}
		if (null != hmAuthorisedForms.get("frmStockAdjustment")) {
			hql += "and a.strAuthorise='Yes' ";
		}
		hql += "group by b.strProdCode";
		objGlobalFunctionsService.funAddTempItemStock(hql, "hql");
		// Update Stock Adjustment In Qty in tblCurrentStock table
		hql = "Update tblcurrentstock a Set a.dblStkAdjIn = a.dblStkAdjIn + IFNULL((select b.dblQty from tbltempitemstock b " + "where a.strProdCode = b.strProdCode and b.strClientCode='" + clientCode + "' and b.strUserCode='" + userCode + "' and b.dblQty),0)";
		objGlobalFunctionsService.funUpdateCurrentStock(hql);

		// Delete TempItemStock Table
		objGlobalFunctionsService.funDeleteTempItemStock(clientCode, userCode);
		// Insert Stock Adjustment Out Qty into tblTempItemStock Table
		hql = "insert into clsTempItemStockModel(strProdCode,dblQty,strClientCode,strUserCode) " + "select b.strProdCode,sum(b.dblQty),'" + clientCode + "','" + userCode + "' " + "from clsStkAdjustmentHdModel a,clsStkAdjustmentDtlModel b " + "where a.strSACode=b.strSACode and date(a.dtSADate) between '" + fromDate + "' and '" + toDate + "' and b.strType='Out' ";
		if (!locCode.equalsIgnoreCase("All")) {
			hql += "and a.strLocCode='" + locCode + "' ";
		}
		if (null != hmAuthorisedForms.get("frmStockAdjustment")) {
			hql += "and a.strAuthorise='Yes' ";
		}
		hql += "group by b.strProdCode";
		objGlobalFunctionsService.funAddTempItemStock(hql, "hql");
		// Update Stock Adjustment Out Qty in tblCurrentStock table
		hql = "Update tblcurrentstock a Set a.dblStkAdjOut = a.dblStkAdjOut + IFNULL((select b.dblQty from tbltempitemstock b " + "where a.strProdCode = b.strProdCode and b.strClientCode='" + clientCode + "' and b.strUserCode='" + userCode + "' and b.dblQty),0)";
		objGlobalFunctionsService.funUpdateCurrentStock(hql);

		// Delete TempItemStock Table
		objGlobalFunctionsService.funDeleteTempItemStock(clientCode, userCode);
		// Insert Produced Qty into tblTempItemStock Table
		hql = "insert into clsTempItemStockModel(strProdCode,dblQty,strClientCode,strUserCode) " + "select b.strProdCode,sum(b.dblQtyProd),'" + clientCode + "','" + userCode + "' " + "from clsProductionHdModel a,clsProductionDtlModel b " + "where a.strPDCode=b.strPDCode and date(a.dtPDDate) between '" + fromDate + "' and '" + toDate + "' ";
		if (!locCode.equalsIgnoreCase("All")) {
			hql += "and a.strLocCode='" + locCode + "' ";
		}
		if (null != hmAuthorisedForms.get("frmProduction")) {
			hql += "and a.strAuthorise='Yes' ";
		}
		hql += "group by b.strProdCode";
		objGlobalFunctionsService.funAddTempItemStock(hql, "hql");
		// Update Produced Qty in tblCurrentStock table
		hql = "Update tblcurrentstock a Set a.dblQtyProduced = a.dblQtyProduced + IFNULL((select b.dblQty from tbltempitemstock b " + "where a.strProdCode = b.strProdCode and b.strClientCode='" + clientCode + "' and b.strUserCode='" + userCode + "' and b.dblQty),0)";
		objGlobalFunctionsService.funUpdateCurrentStock(hql);

		// Delete TempItemStock Table
		objGlobalFunctionsService.funDeleteTempItemStock(clientCode, userCode);
		// Insert Purchase Return In Qty into tblTempItemStock Table
		hql = "insert into clsTempItemStockModel(strProdCode,dblQty,strClientCode,strUserCode) " + "select b.strProdCode,sum(b.dblQty),'" + clientCode + "','" + userCode + "' " + "from clsPurchaseReturnHdModel a,clsPurchaseReturnDtlModel b " + "where a.strPRCode=b.strPRCode and date(a.dtPRDate) between '" + fromDate + "' and '" + toDate + "' ";
		if (!locCode.equalsIgnoreCase("All")) {
			hql += "and a.strLocCode='" + locCode + "' ";
		}
		if (null != hmAuthorisedForms.get("frmPurchaseReturn")) {
			hql += "and a.strAuthorise='Yes' ";
		}
		hql += "group by b.strProdCode";
		objGlobalFunctionsService.funAddTempItemStock(hql, "hql");
		// Update Purchase Return In Qty in tblCurrentStock table
		hql = "Update tblcurrentstock a Set a.dblPurchaseReturn = a.dblPurchaseReturn + IFNULL((select b.dblQty from tbltempitemstock b " + "where a.strProdCode = b.strProdCode and b.strClientCode='" + clientCode + "' and b.strUserCode='" + userCode + "' and b.dblQty),0)";
		objGlobalFunctionsService.funUpdateCurrentStock(hql);

		// Delete TempItemStock Table
		objGlobalFunctionsService.funDeleteTempItemStock(clientCode, userCode);
		// Insert SCGRN Qty into tblTempItemStock Table
		hql = "insert into clsTempItemStockModel(strProdCode,dblQty,strClientCode,strUserCode) " + "select b.strProdCode,sum(b.dblQty),'" + clientCode + "','" + userCode + "' " + "from clsSubContractorGRNModelHd a,clsSubContractorGRNModelDtl b " + "where a.strSRCode=b.strSRCode and date(a.dteSRDate) between '" + fromDate + "' and '" + toDate + "' ";
		if (!locCode.equalsIgnoreCase("All")) {
			hql += "and a.strLocCode='" + locCode + "' ";
		}
		if (null != hmAuthorisedForms.get("frmSubContractorGRN")) {
			hql += "and a.strAuthorise='Yes' ";
		}
		hql += "group by b.strProdCode";
		objGlobalFunctionsService.funAddTempItemStock(hql, "hql");
		// Update SCGRN Qty in tblCurrentStock table
		hql = "Update tblcurrentstock a Set a.dblSCGRN = a.dblSCGRN + IFNULL((select b.dblQty from tbltempitemstock b " + "where a.strProdCode = b.strProdCode and b.strClientCode='" + clientCode + "' and b.strUserCode='" + userCode + "' and b.dblQty),0)";
		objGlobalFunctionsService.funUpdateCurrentStock(hql);

		// Delete TempItemStock Table
		objGlobalFunctionsService.funDeleteTempItemStock(clientCode, userCode);
		// Insert Delivery Note Qty into tblTempItemStock Table
		hql = "insert into clsTempItemStockModel(strProdCode,dblQty,strClientCode,strUserCode) " + "select b.strProdCode,sum(b.dblQty),'" + clientCode + "','" + userCode + "' " + "from clsDeliveryNoteHdModel a,clsDeliveryNoteDtlModel b " + "where a.strDNCode=b.strDNCode and date(a.dteDNDate) between '" + fromDate + "' and '" + toDate + "' ";
		if (!locCode.equalsIgnoreCase("All")) {
			hql += "and a.strLocCode='" + locCode + "' ";
		}
		if (null != hmAuthorisedForms.get("frmDeliveryNote")) {
			hql += "and a.strAuthorise='Yes' ";
		}
		hql += "group by b.strProdCode";
		objGlobalFunctionsService.funAddTempItemStock(hql, "hql");
		// Update Delivery Note Qty in tblCurrentStock table
		hql = "Update tblcurrentstock a Set a.dblDeliveryNote = a.dblDeliveryNote + IFNULL((select b.dblQty from tbltempitemstock b " + "where a.strProdCode = b.strProdCode and b.strClientCode='" + clientCode + "' and b.strUserCode='" + userCode + "' and b.dblQty),0)";
		objGlobalFunctionsService.funUpdateCurrentStock(hql);

		// Delete TempItemStock Table
		objGlobalFunctionsService.funDeleteTempItemStock(clientCode, userCode);
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propCode, clientCode);
		if (objSetup.getStrEffectOfInvoice().equalsIgnoreCase("Invoice")) {
			// Insert Invoice chaaln Qty into tblTempItemStock Table when
			// Industry type is Retaling
			String sqlForRetail = "insert into tbltempitemstock(strProdCode,dblQty,strClientCode,strUserCode) " + " select b.strProdCode,sum(b.dblQty),'" + clientCode + "','" + userCode + "' " + " FROM tblinvoicehd a,tblinvoicedtl b " + " WHERE a.strInvCode=b.strInvCode AND DATE(a.dteInvDate)  between '" + fromDate + "' and '" + toDate + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sqlForRetail += "and a.strLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmInovice")) {
				sqlForRetail += "and a.strAuthorise='Yes' ";
			}
			sqlForRetail += "group by b.strProdCode";

			objGlobalFunctionsService.funExcuteQuery(sqlForRetail);
		} else {
			// Insert Delivery Challan (Sale) Qty into tblTempItemStock Table
			hql = "insert into clsTempItemStockModel(strProdCode,dblQty,strClientCode,strUserCode) " + "select b.strProdCode,sum(b.dblQty),'" + clientCode + "','" + userCode + "' " + "from clsDeliveryChallanHdModel a,clsDeliveryChallanModelDtl b " + "where a.strDCCode=b.strDCCode and date(a.dteDCDate) between '" + fromDate + "' and '" + toDate + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				hql += "and a.strLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmDeliveryChallan")) {
				hql += "and a.strAuthorise='Yes' ";
			}
			hql += "group by b.strProdCode";
			objGlobalFunctionsService.funAddTempItemStock(hql, "hql");
		}

		// Update Delivery Note Qty in tblCurrentStock table
		hql = "Update tblcurrentstock a Set a.dblSales = a.dblSales + IFNULL((select b.dblQty from tbltempitemstock b " + "where a.strProdCode = b.strProdCode and b.strClientCode='" + clientCode + "' and b.strUserCode='" + userCode + "' and b.dblQty),0)";
		objGlobalFunctionsService.funUpdateCurrentStock(hql);

		// Delete TempItemStock Table
		objGlobalFunctionsService.funDeleteTempItemStock(clientCode, userCode);
		// Insert Consumed Qty into tblTempItemStock Table
	
		hql = " select d.strChildCode,sum((d.dblQty/e.dblRecipeConversion)*b.dblQtyProd),'" + clientCode + "','" + userCode + "' " + "	from clsProductionHdModel a,clsProductionDtlModel b,clsBomHdModel c,clsBomDtlModel d, clsProductMasterModel e " + "	where a.strPDCode=b.strPDCode and b.strProdCode=c.strParentCode and c.strBOMCode=d.strBOMCode " + "	and d.strChildCode=e.strProdCode "
				+ " and date(a.dtPDDate) between '" + fromDate + "' and '" + toDate + "' ";

		if (!locCode.equalsIgnoreCase("All")) {
			hql += "and a.strLocCode='" + locCode + "' ";
		}
		if (null != hmAuthorisedForms.get("frmProduction")) {
			hql += "and a.strAuthorise='Yes' ";
		}
		hql += "group by d.strChildCode";

		List listProd = objGlobalFunctionsService.funGetList(hql, "hql");
		listChildNodes1 = new ArrayList<String>();
		hmChildNodes = new HashMap<String, Double>();
		for (int i = 0; i < listProd.size(); i++) {
			Object[] ob = (Object[]) listProd.get(i);

			if (hmChildNodes.containsKey(ob[0].toString())) {
				double qtyChild = (double) hmChildNodes.get(ob[0].toString());
				qtyChild = Double.parseDouble(ob[1].toString()) + qtyChild;
				hmChildNodes.put(ob[0].toString(), qtyChild);
			} else {

				hmChildNodes.put(ob[0].toString(), Double.parseDouble(ob[1].toString()));
			}
			funGetBOMNodes(ob[0].toString(), 0, Double.parseDouble(ob[1].toString()));
		}
		String restHql2 = "";
		if (!hmChildNodes.isEmpty()) {
			for (Map.Entry<String, Double> entry : hmChildNodes.entrySet()) {

				String prodCode = entry.getKey();
				double reqdQty = entry.getValue();

				restHql2 += " ('" + prodCode + "','" + reqdQty + "','" + clientCode + "','" + userCode + "'),";
			}
			restHql2 = restHql2.substring(1, (restHql2.length() - 1));
			String restHql1 = " insert into tbltempitemstock(strProdCode,dblQty,strClientCode,strUserCode) values  ";

			objGlobalFunctionsService.funAddTempItemStock(restHql1 + restHql2, "sql");
		}
		

		// Update Consumed Qty in tblCurrentStock table
		hql = "Update tblcurrentstock a Set a.dblQtyConsumed = a.dblQtyConsumed + IFNULL((select b.dblQty from tbltempitemstock b " + "where a.strProdCode = b.strProdCode and b.strClientCode='" + clientCode + "' and b.strUserCode='" + userCode + "' and b.dblQty),0)";
		objGlobalFunctionsService.funUpdateCurrentStock(hql);

		// Update dblClosingStock field in tblCurrentStock table
		hql = "update tblcurrentstock set dblClosingStk=(dblOpeningStk+dblGRN+dblSCGRN+dblStkTransIn+dblStkAdjIn+" + "dblMISIn+dblMaterialReturnIn+dblSalesReturn+dblQtyProduced-dblStkTransOut-dblStkAdjOut-dblMISOut-dblQtyConsumed" + "-dblSales-dblMaterialReturnOut-dblDeliveryNote-dblPurchaseReturn) where strClientCode='" + clientCode + "' and strUserCode='" + userCode + "'";
		objGlobalFunctionsService.funUpdateCurrentStock(hql);
		objGlobalFunctionsService.funDeleteTempItemStock(clientCode, userCode);
		return 1;
	}

	public void funGetStocks(String locCode, String fromDate, String toDate, String clientCode, String userCode, String stockableItem, HttpServletRequest req, HttpServletResponse resp) {
		funDeleteAndInsertStkTempTable(clientCode, userCode, locCode, stockableItem);
		funProcessStock(locCode, fromDate, toDate, clientCode, userCode, req, resp);
	}

	@SuppressWarnings("rawtypes")
	public double funGetStock(String productCode, String locCode, String fromDate, String toDate, String clientCode, String userCode, String propertyCode, String stockableItem, HttpServletRequest req, HttpServletResponse resp) {
		double closingStock = 0.0000;
		funDeleteAndInsertStkTempTable(clientCode, userCode, locCode, stockableItem);

		funProcessStock(locCode, fromDate, toDate, clientCode, userCode, req, resp);
		List list = objGlobalFunctionsService.funGetCurrentStock(productCode, clientCode, userCode);
		// clsCurrentStockModel objStock=(clsCurrentStockModel)list.get(0);
		if (list.get(0) != null) {
			BigDecimal stock = (BigDecimal) list.get(0);
			closingStock = stock.doubleValue();
		}
		System.out.println("Stock=" + closingStock);
		return closingStock;
	}

	@RequestMapping(value = "/getProductStock", method = RequestMethod.GET)
	public @ResponseBody double funGetStockForProduct(@RequestParam("prodCode") String prodCode, HttpServletRequest request) {
		String startDate = request.getSession().getAttribute("startDate").toString();
		String[] sp = startDate.split(" ");
		String[] spDate = sp[0].split("/");
		startDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0];

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String locCode = request.getParameter("locCode").toString();
		String strEndDate = funGetCurrentDate("yyyy-MM-dd");
		if (request.getParameter("strMISDate") != null) {
			strEndDate = request.getParameter("strMISDate").toString();
			strEndDate = funGetDate("yyyy-MM-dd", strEndDate);
		}
		double stock = funCalculateStockForTrans(prodCode, locCode, startDate, strEndDate, clientCode, userCode, 0.00);
		clsProductMasterModel objModel = objProductMasterService.funGetObject(prodCode, clientCode);
		double issueConversion = objModel.getDblIssueConversion();
		stock = stock * issueConversion;
		return stock;
	}

	@RequestMapping(value = "/getProductStockInUOM", method = RequestMethod.GET)
	public @ResponseBody double funGetStockForProductRecUOM(@RequestParam("prodCode") String prodCode, HttpServletRequest request) {
		String startDate = request.getSession().getAttribute("startDate").toString();
		String[] sp = startDate.split(" ");
		String[] spDate = sp[0].split("/");
		startDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0];
		String strUOM = request.getParameter("strUOM").toString();

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String locCode = request.getParameter("locCode").toString();
		String strEndDate = funGetCurrentDate("yyyy-MM-dd");
		if (request.getParameter("strTransDate") != null) {
			strEndDate = request.getParameter("strTransDate").toString();
			strEndDate = funGetDate("yyyy-MM-dd", strEndDate);
		}
		double stock = funCalculateStockForTrans(prodCode, locCode, startDate, strEndDate, clientCode, userCode, 0.00);
		clsProductMasterModel objModel = objProductMasterService.funGetObject(prodCode, clientCode);
		if (strUOM.equals("RecipeUOM")) {
			double recipeConversion = objModel.getDblRecipeConversion();
			double issueConversion = objModel.getDblIssueConversion();
			stock = stock * recipeConversion * issueConversion;
		} else if (strUOM.equals("IssueUOM")) {
			double issueConversion = objModel.getDblIssueConversion();
			stock = stock * issueConversion;
		}
		return stock;
	}

	public double funGetCurrentStockForProduct(String prodCode, String locCode, String clientCode, String userCode, String startDate, String endDate) {
		double stock = funCalculateStockForTrans(prodCode, locCode, startDate, endDate, clientCode, userCode, 0.00);
		return stock;
	}

	@SuppressWarnings("rawtypes")
	public double funCalculateStockForTrans(String prodCode, String locCode, String fromDate, String toDate, String clientCode, String userCode, double opStk) {
		double finalStock = 0, grnQty = 0, opStkQty = 0, stkTransInQty = 0, stkAdjInQty = 0, misInQty = 0, matReturnInQty = 0;
		double stkTransOutQty = 0, stkAdjOutQty = 0, misOutQty = 0, matReturnOutQty = 0, qtyProduced = 0, purchaseReturnQty = 0, saleQty = 0, productionQty = 0, reciptQty = 0, issueQty = 0, salesReturnQty = 0;
		double scGRNQty = 0, deliveryNoteQty = 0;
		clsLocationMasterModel locModelProperty = objLocationMasterService.funGetObject(locCode, clientCode);
		clsPropertySetupModel setupModel = objSetupMasterService.funGetObjectPropertySetup(locModelProperty.getStrPropertyCode(), clientCode);

		// Calculate GRN Qty for Product

		String hql_GRN = "select sum(b.dblQty-b.dblRejected) " + "from clsGRNHdModel a,clsGRNDtlModel b " + "where a.strGRNCode=b.strGRNCode and b.strProdCode='" + prodCode + "' " + "and date(a.dtGRNDate) between '" + fromDate + "' and '" + toDate + "' ";
		if (!locCode.equalsIgnoreCase("All")) {
			hql_GRN += "and a.strLocCode='" + locCode + "' ";
		}
		hql_GRN += "group by b.strProdCode";

		List list_GRNProductQty = objGlobalFunctionsService.funGetProdQtyForStock(hql_GRN, "hql");
		if (list_GRNProductQty.size() > 0) {
			grnQty = (Double) list_GRNProductQty.get(0);
		}
		// System.out.println("GRN="+grnQty);

		// Calculate Opening Stock Qty for Product

		String hql_OpStk = "select sum(b.dblQty) " + "from clsInitialInventoryModel a,clsOpeningStkDtl b " + "where a.strOpStkCode=b.strOpStkCode and b.strProdCode='" + prodCode + "' " + "and date(a.dtCreatedDate) between '" + fromDate + "' and '" + toDate + "' ";
		if (!locCode.equalsIgnoreCase("All")) {
			hql_OpStk += "and a.strLocCode='" + locCode + "' ";
		}
		hql_OpStk += "group by b.strProdCode";

		List list_OpStkProductQty = objGlobalFunctionsService.funGetProdQtyForStock(hql_OpStk, "hql");
		if (list_OpStkProductQty.size() > 0) {
			opStkQty = (Double) list_OpStkProductQty.get(0);
		}
		// System.out.println("Op Stk="+opStkQty);

		// Calculate Stock Transfer In Qty for Product

		String hql_StkTransIn = "select sum(b.dblQty) " + "from clsStkTransferHdModel a,clsStkTransferDtlModel b " + "where a.strSTCode=b.strSTCode and b.strProdCode='" + prodCode + "' " + "and date(a.dtSTDate) between '" + fromDate + "' and '" + toDate + "' ";
		if (!locCode.equalsIgnoreCase("All")) {
			hql_StkTransIn += "and a.strToLocCode='" + locCode + "' ";
		}
		hql_StkTransIn += "group by b.strProdCode";

		List list_StkTransProductQty = objGlobalFunctionsService.funGetProdQtyForStock(hql_StkTransIn, "hql");
		if (list_StkTransProductQty.size() > 0) {
			stkTransInQty = (Double) list_StkTransProductQty.get(0);
		}
		// System.out.println("Stk Trans In="+stkTransInQty);

		// Calculate MIS In Qty for Product

		String hql_MISInQty = "select sum(b.dblQty) " + "from tblmishd a,tblmisdtl b " + "where a.strMISCode=b.strMISCode and b.strProdCode='" + prodCode + "' " + "and date(a.dtMISDate) between '" + fromDate + "' and '" + toDate + "' ";
		if (!locCode.equalsIgnoreCase("All")) {
			hql_MISInQty += "and a.strLocTo='" + locCode + "' ";
		}
		hql_MISInQty += "group by b.strProdCode";
		List list_MISInProductQty = objGlobalFunctionsService.funGetProdQtyForStock(hql_MISInQty, "sql");
		if (list_MISInProductQty.size() > 0) {
			BigDecimal big = (BigDecimal) list_MISInProductQty.get(0);
			misInQty = Double.parseDouble(big.toString());
			// misInQty=(Double)list_MISInProductQty.get(0);
		}
		// System.out.println("MIS In="+misInQty);

		// Calculate Material Return In Qty for Product

		String hql_MatReturnInQty = "select sum(b.dblQty) " + "from clsMaterialReturnHdModel a,clsMaterialReturnDtlModel b " + "where a.strMRetCode=b.strMRetCode and b.strProdCode='" + prodCode + "' " + "and date(a.dtMRetDate) between '" + fromDate + "' and '" + toDate + "' ";
		if (!locCode.equalsIgnoreCase("All")) {
			hql_MatReturnInQty += "and a.strLocTo='" + locCode + "' ";
		}
		hql_MatReturnInQty += "group by b.strProdCode";
		List list_MatReturnInProductQty = objGlobalFunctionsService.funGetProdQtyForStock(hql_MatReturnInQty, "hql");
		if (list_MatReturnInProductQty.size() > 0) {
			matReturnInQty = (Double) list_MatReturnInProductQty.get(0);
		}
		// System.out.println("Mat Return In="+matReturnInQty);

		// Calculate Stock Adjustment In Qty for Product

		String hql_StkAdjInQty = "select sum(b.dblQty) " + "from clsStkAdjustmentHdModel a,clsStkAdjustmentDtlModel b " + "where a.strSACode=b.strSACode and b.strProdCode='" + prodCode + "' " + "and date(a.dtSADate) between '" + fromDate + "' and '" + toDate + "' and b.strType='In'";
		if (!locCode.equalsIgnoreCase("All")) {
			hql_StkAdjInQty += "and a.strLocCode='" + locCode + "' ";
		}
		hql_StkAdjInQty += "group by b.strProdCode";
		List list_StkAdjInProductQty = objGlobalFunctionsService.funGetProdQtyForStock(hql_StkAdjInQty, "hql");
		if (list_StkAdjInProductQty.size() > 0) {
			stkAdjInQty = (Double) list_StkAdjInProductQty.get(0);
		}
		// System.out.println("Stk Adj In="+stkAdjInQty);

		// Calculate Stock Transfer Out Qty for Product

		String hql_StkTransOut = "select sum(b.dblQty) " + "from clsStkTransferHdModel a,clsStkTransferDtlModel b " + "where a.strSTCode=b.strSTCode and b.strProdCode='" + prodCode + "' " + "and date(a.dtSTDate) between '" + fromDate + "' and '" + toDate + "' ";
		if (!locCode.equalsIgnoreCase("All")) {
			hql_StkTransOut += "and a.strFromLocCode='" + locCode + "' ";
		}
		hql_StkTransOut += "group by b.strProdCode";

		List list_StkTransOutProductQty = objGlobalFunctionsService.funGetProdQtyForStock(hql_StkTransOut, "hql");
		if (list_StkTransOutProductQty.size() > 0) {
			stkTransOutQty = (Double) list_StkTransOutProductQty.get(0);
		}

		// Calculate MIS Out Qty for Product

		String hql_MISOutQty = "select sum(b.dblQty) " + "from tblmishd a,tblmisdtl b " + "where a.strMISCode=b.strMISCode and b.strProdCode='" + prodCode + "' " + "and date(a.dtMISDate) between '" + fromDate + "' and '" + toDate + "' ";
		if (!locCode.equalsIgnoreCase("All")) {
			hql_MISOutQty += "and a.strLocFrom='" + locCode + "' ";
		}
		hql_MISOutQty += "group by b.strProdCode";
		List list_MISOutProductQty = objGlobalFunctionsService.funGetProdQtyForStock(hql_MISOutQty, "sql");
		if (list_MISOutProductQty.size() > 0) {
			BigDecimal big = (BigDecimal) list_MISOutProductQty.get(0);
			misOutQty = Double.parseDouble(big.toString());
		}

		// Calculate Material Return Out Qty for Product

		String hql_MatReturnOutQty = "select sum(b.dblQty) " + "from clsMaterialReturnHdModel a,clsMaterialReturnDtlModel b " + "where a.strMRetCode=b.strMRetCode and b.strProdCode='" + prodCode + "' " + "and date(a.dtMRetDate) between '" + fromDate + "' and '" + toDate + "' ";
		if (!locCode.equalsIgnoreCase("All")) {
			hql_MatReturnOutQty += "and a.strLocFrom='" + locCode + "' ";
		}
		hql_MatReturnOutQty += "group by b.strProdCode";

		List list_MatReturnOutProductQty = objGlobalFunctionsService.funGetProdQtyForStock(hql_MatReturnOutQty, "hql");
		if (list_MatReturnOutProductQty.size() > 0) {
			matReturnOutQty = (Double) list_MatReturnOutProductQty.get(0);
		}

		// Calculate Stock Adjustment Out Qty for Product

		String hql_StkAdjOutQty = "select sum(b.dblQty) " + "from clsStkAdjustmentHdModel a,clsStkAdjustmentDtlModel b " + "where a.strSACode=b.strSACode and b.strProdCode='" + prodCode + "' " + "and date(a.dtSADate) between '" + fromDate + "' and '" + toDate + "' and b.strType='Out'";
		if (!locCode.equalsIgnoreCase("All")) {
			hql_StkAdjOutQty += "and a.strLocCode='" + locCode + "' ";
		}
		hql_StkAdjOutQty += "group by b.strProdCode";
		List list_StkAdjOutProductQty = objGlobalFunctionsService.funGetProdQtyForStock(hql_StkAdjOutQty, "hql");
		if (list_StkAdjOutProductQty.size() > 0) {
			stkAdjOutQty = (Double) list_StkAdjOutProductQty.get(0);
		}
		

		// Calculate Purchase Return Qty for Product

		String hql_PurchaseReturnQty = "select sum(b.dblQty) " + "from clsPurchaseReturnHdModel a,clsPurchaseReturnDtlModel b " + "where a.strPRCode = b.strPRCode and b.strProdCode='" + prodCode + "' " + "and date(a.dtPRDate) between '" + fromDate + "' and '" + toDate + "' ";
		if (!locCode.equalsIgnoreCase("All")) {
			hql_PurchaseReturnQty += "and a.strLocCode='" + locCode + "' ";
		}
		hql_PurchaseReturnQty += "group by b.strProdCode";

		List list_PurchaseReturnQty = objGlobalFunctionsService.funGetProdQtyForStock(hql_PurchaseReturnQty, "hql");
		if (list_PurchaseReturnQty.size() > 0) {
			purchaseReturnQty = (Double) list_PurchaseReturnQty.get(0);
		}
		// System.out.println("Pur return="+purchaseReturnQty);

		String hql_SalesReturnQty = "select ifnull(sum(b.dblQty),0) " + "from tblsalesreturnhd a,tblsalesreturndtl b " + "where a.strSRCode=b.strSRCode and b.strProdCode='" + prodCode + "' " + "and date(a.dteSRDate) between '" + fromDate + "' and '" + toDate + "' ";
		if (!locCode.equalsIgnoreCase("All")) {
			hql_SalesReturnQty += "and a.strLocCode='" + locCode + "' ";
		}
		hql_SalesReturnQty += "group by b.strProdCode";
		List list_SalesReturnQty = objGlobalFunctionsService.funGetProdQtyForStock(hql_SalesReturnQty, "sql");
		if (list_SalesReturnQty.size() > 0) {
			salesReturnQty = ((BigDecimal) list_SalesReturnQty.get(0)).doubleValue();
		}

		if (setupModel.getStrEffectOfInvoice().equals("Invoice")) {
			String sql_InvoiceQty = "select sum(b.dblQty) " + "from tblinvoicehd a,tblinvoicedtl b " + "where a.strInvCode = b.strInvCode and b.strProdCode='" + prodCode + "' " + "and date(a.dteInvDate) between '" + fromDate + "' and '" + toDate + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql_InvoiceQty += "and a.strLocCode='" + locCode + "' ";
			}
			sql_InvoiceQty += "group by b.strProdCode";
			List list_InvoiceQty = objGlobalFunctionsService.funGetProdQtyForStock(sql_InvoiceQty, "sql");
			if (list_InvoiceQty.size() > 0) {
				saleQty = ((BigDecimal) list_InvoiceQty.get(0)).doubleValue();
			}

		} else {
			String sql_DCQty = "select sum(b.dblQty) " + "from tbldeliverychallanhd a,tbldeliverychallandtl b " + "where a.strDCCode = b.strDCCode and b.strProdCode='" + prodCode + "' " + "and date(a.dteDCDate) between '" + fromDate + "' and '" + toDate + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql_DCQty += "and a.strLocCode='" + locCode + "' ";
			}
			sql_DCQty += "group by b.strProdCode";
			List list_DCQty = objGlobalFunctionsService.funGetProdQtyForStock(sql_DCQty, "sql");
			if (list_DCQty.size() > 0) {
				saleQty = ((BigDecimal) list_DCQty.get(0)).doubleValue();
			}
		}

		String sql = "SELECT b.strChildCode,b.dblQty,a.strParentCode,c.dblRecipeConversion from tblbommasterhd a ,tblbommasterdtl b,tblproductmaster c " + " where a.strBOMCode=b.strBOMCode and b.strChildCode='" + prodCode + "' and b.strChildCode=c.strProdCode  ";

		List listChildProdStkLedger = objGlobalFunctionsService.funGetProdQtyForStock(sql, "sql");
		for (int cnt = 0; cnt < listChildProdStkLedger.size(); cnt++) {
			Object objParent[] = (Object[]) listChildProdStkLedger.get(cnt);

			sql = "select  ifnull(sum(b.dblQtyProd),0) " + "from tblproductionhd a, tblproductiondtl b " + "where a.strPDCode  = b.strPDCode " + "and b.strProdCode = '" + objParent[2].toString() + "' and date(a.dtPDDate) between'" + fromDate + "' and  '" + toDate + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode='" + locCode + "' ";
			}

			sql += "group by b.strProdCode ";

			List listProductionStkLedger = objGlobalFunctionsService.funGetProdQtyForStock(sql, "sql");
			if (!listProductionStkLedger.isEmpty()) {
				Object obj = (Object) listProductionStkLedger.get(0);
				issueQty += Double.parseDouble(objParent[1].toString()) * Double.parseDouble(obj.toString()) / Double.parseDouble(objParent[3].toString());
			}
		}
		sql = "select  ifnull(sum(b.dblQtyProd),0) " + "from tblproductionhd a, tblproductiondtl b " + "where a.strPDCode  = b.strPDCode " + "and b.strProdCode = '" + prodCode + "' and date(a.dtPDDate) between'" + fromDate + "' and  '" + toDate + "' ";
		if (!locCode.equalsIgnoreCase("All")) {
			sql += "and a.strLocCode='" + locCode + "' ";
		}

		sql += "group by b.strProdCode ";

		List listProductionStkLedger = objGlobalFunctionsService.funGetProdQtyForStock(sql, "sql");
		if (!listProductionStkLedger.isEmpty()) {
			Object obj = (Object) listProductionStkLedger.get(0);
			reciptQty += Double.parseDouble(obj.toString());
		}

		productionQty = reciptQty - issueQty;

		sql = "select IFNULL(SUM(b.dblQty),0) " + " from tblscreturnhd a,tblscreturndtl b  " + "where a.strSRCode=b.strSRCode and b.strProdCode= '" + prodCode + "'  AND DATE(a.dteSRDate) between '" + fromDate + "'  AND  '" + toDate + "' ";
		if (!locCode.equalsIgnoreCase("All")) {
			sql += "and a.strLocCode = '" + locCode + "' ";
		}
		sql += "  GROUP BY b.strProdCode ";

		List list_SCGRN = objGlobalFunctionsService.funGetProdQtyForStock(sql, "sql");

		if (list_SCGRN.size() > 0) {
			scGRNQty = ((BigDecimal) list_SCGRN.get(0)).doubleValue();
		}

		sql = "select  IFNULL(SUM(b.dblQty),0)  Issue " + " from tbldeliverynotehd a,tbldeliverynotedtl b 	" + " where a.strDNCode=b.strDNCode  and b.strProdCode= '" + prodCode + "' AND DATE(a.dteDNDate) between'" + fromDate + "' " + " AND  '" + toDate + "' ";

		if (!locCode.equalsIgnoreCase("All")) {
			sql += "and a.strLocCode = '" + locCode + "' ";
		}
		sql += "   GROUP BY b.strProdCode ";

		List list_DN = objGlobalFunctionsService.funGetProdQtyForStock(sql, "sql");

		if (list_DN.size() > 0) {
			deliveryNoteQty = ((BigDecimal) list_DN.get(0)).doubleValue();
		}

		finalStock = (opStkQty + grnQty + stkTransInQty + stkAdjInQty + misInQty + matReturnInQty + qtyProduced + reciptQty + scGRNQty + salesReturnQty) - (stkTransOutQty + stkAdjOutQty + misOutQty + matReturnOutQty + purchaseReturnQty + saleQty + issueQty + deliveryNoteQty);

		return finalStock;

	}

	// Function to convert Model into Bean of Stock Adjustment
	@SuppressWarnings("rawtypes")
	public clsStockAdjustmentBean funPrepareStockAdjBean(List listStkAdjHd) {
		clsStockAdjustmentBean objBean = new clsStockAdjustmentBean();
		Object[] ob = (Object[]) listStkAdjHd.get(0);
		clsStkAdjustmentHdModel stkAdjHd = (clsStkAdjustmentHdModel) ob[0];
		clsLocationMasterModel locMaster = (clsLocationMasterModel) ob[1];
		objBean.setStrSACode(stkAdjHd.getStrSACode());
		objBean.setDtSADate(funGetDate("yyyy/MM/dd", stkAdjHd.getDtSADate()));
		objBean.setStrLocCode(stkAdjHd.getStrLocCode());
		objBean.setStrLocName(locMaster.getStrLocName());
		objBean.setStrNarration(stkAdjHd.getStrNarration());
		objBean.setStrAuthorise(stkAdjHd.getStrAuthorise());
		objBean.setStrReasonCode(stkAdjHd.getStrReasonCode());
		objBean.setDblTotalAmt(stkAdjHd.getDblTotalAmt());
		objBean.setStrConversionUOM(stkAdjHd.getStrConversionUOM());
		return objBean;
	}

	@SuppressWarnings("rawtypes")
	public List funGetList(String query) {
		return objGlobalFunctionsService.funGetList(query);
	}

	public Object funSetDefaultValue(Object value, Object defaultValue) {
		if (value != null && (value instanceof String && value.toString().length() > 0)) {
			return value;
		} else if (value != null && !(value instanceof String)) {
			return value;
		} else {
			return defaultValue;
		}
		// return value !=null && (value instanceof String &&
		// value.toString().length()>0) ? value : defaultValue;
	}

	public List<String> funGetSetUpProcess(String strForm, String StrPropertyCode, String clientCode) {
		@SuppressWarnings("unchecked")
		List<String> ProcessList = objGlobalFunctionsService.funGetSetUpProcess(strForm, StrPropertyCode, clientCode);

		return ProcessList;
	}

	public List<clsTCTransModel> funPrepareTCTransModel(List<clsTCMasterModel> listTCMasterModel, String transCode, String userCode, String clientCode, String transType) {
		List<clsTCTransModel> listTCTransModel = new ArrayList<clsTCTransModel>();

		if (null != listTCMasterModel) {

			for (int cnt = 0; cnt < listTCMasterModel.size(); cnt++) {
				clsTCTransModel objTCTransModel = new clsTCTransModel();
				clsTCMasterModel objTCMasterModel = listTCMasterModel.get(cnt);
				if (null != objTCMasterModel.getStrTCCode()) {
					// if(objTCMasterModel.getStrTCDesc().trim().length()>0)
					// {
					objTCTransModel.setStrTCCode(objTCMasterModel.getStrTCCode());
					objTCTransModel.setStrTCDesc(objTCMasterModel.getStrTCDesc());
					objTCTransModel.setStrTransCode(transCode);
					objTCTransModel.setStrTransType(transType);
					objTCTransModel.setStrClientCode(clientCode);
					listTCTransModel.add(objTCTransModel);
					// }
				}
			}
		}
		return listTCTransModel;
	}

	public int funCheckFromInWorkflowforSlabbasedAuth(String strForm, String StrPropertyCode) {
		int formCount = objGlobalFunctionsService.funCheckFromInWorkflowforSlabbasedAuth(strForm, StrPropertyCode);

		return formCount;
	}

	/**
	 * Report Connection
	 * 
	 * @return
	 */
	

	// you are login in which model that will connect you of that database
	public Connection funGetConnection(HttpServletRequest req) {
		Connection con = null;
		String modelno = req.getSession().getAttribute("moduleNo").toString();

		try {
			switch (modelno) {
			case "1":
				con = (Connection) DriverManager.getConnection(conUrl + "?user=" + urluser + "&password=" + urlPassword);
				break;

			case "2":
				con = (Connection) DriverManager.getConnection(urlExcise + "?user=" + urluser + "&password=" + urlPassword);
				// con = (Connection)
				// DriverManager.getConnection("jdbc:mysql://localhost:3306/dbwebexcise?user=root&password=root");
				break;

			case "3":
				con = (Connection) DriverManager.getConnection(urlwebpms + "?user=" + urluser + "&password=" + urlPassword);
				break;

			case "4":
				con = (Connection) DriverManager.getConnection(urlwebclub + "?user=" + urluser + "&password=" + urlPassword);
				break;

			case "5":
				con = (Connection) DriverManager.getConnection(urlwebbooks + "?user=" + urluser + "&password=" + urlPassword);
				break;

			case "6":
				con = (Connection) DriverManager.getConnection(conUrl + "?user=" + urluser + "&password=" + urlPassword);
				break;

			// case "7":
			// con = (Connection)
			// DriverManager.getConnection("jdbc:mysql://localhost:3306/dbwebmms?user=root&password=root");
			// break;

			default:
				con = (Connection) DriverManager.getConnection(conUrl + "?user=" + urluser + "&password=" + urlPassword);
				break;
			}
		} catch (Exception e) {
			System.out.println("Error in conection");
		}
		return con;
	}

	@SuppressWarnings("rawtypes")
	public boolean funCheckName(String Name, String strClientCode, String formName) {
		List countList = objGlobalFunctionsService.funCheckName(Name, strClientCode, formName);
		int count = Integer.parseInt(countList.get(0).toString());
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	@RequestMapping(value = "/getTaxDtlForProduct", method = RequestMethod.GET)
	public @ResponseBody Map<String, String> funCalculateTax(@RequestParam("prodCode") String prodDetails, @RequestParam("taxType") String taxType, @RequestParam("transDate") String transDate, @RequestParam("CIFAmt") String CIFAmt, HttpServletRequest req) {
		Map<String, String> hmProductTaxDtl1 = new HashMap<String, String>();
		List listTaxDtl = new ArrayList<String>();
		List listTaxes = new ArrayList();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		Map<String, clsTaxDtl> hmTaxCalDtl = new HashMap<String, clsTaxDtl>();
		Map<String, clsTaxDtl> hmTaxCalDtlTemp = new HashMap<String, clsTaxDtl>();
		Map<String, Map<String, clsTaxDtl>> hmProdTaxCalDtl = new HashMap<String, Map<String, clsTaxDtl>>();
		String shortName = "";
		try {
			Map<String, clsProductTaxDtl> hmProductTaxDtl = new HashMap<String, clsProductTaxDtl>();
			String supplierCodeForForeignTax = "";
			boolean foreignSupplier = false, comesaRegionSupplier = false, checkComesaRegionTax = false;
			double CIFAmountForTaxCal = Double.parseDouble(CIFAmt);

			if (clientCode.equals("226.001") && taxType.equalsIgnoreCase("Purchase")) {
				String[] spProduct1 = prodDetails.split("!");
				String productCode1 = "";
				for (int cn = 0; cn < spProduct1.length; cn++) {
					String[] sp1 = spProduct1[cn].split(",");
					supplierCodeForForeignTax = sp1[2];
				}

				if (!supplierCodeForForeignTax.trim().isEmpty()) {
					String sqlTax = "select strPartyType,strComesaRegion from tblpartymaster where strPCode='" + supplierCodeForForeignTax + "' and strPartyType='Foreign' ";
					List list = objGlobalFunctionsService.funGetList(sqlTax, "sql");
					if (list.size() > 0) {
						foreignSupplier = true;
						Object[] arrObj = (Object[]) list.get(0);
						if (arrObj[1].equals("Y"))
							comesaRegionSupplier = true;
					}
				}
			}

			if (CIFAmountForTaxCal > 0 && foreignSupplier) {
				String[] spProduct1 = prodDetails.split("!");
				for (int cn = 0; cn < spProduct1.length; cn++) {
					String[] sp1 = spProduct1[cn].split(",");
					supplierCodeForForeignTax = sp1[2];
					break;
				}
				clsProductTaxDtl objOtherTaxDtl = new clsProductTaxDtl();
				objOtherTaxDtl.setStrProductCode("OtherTaxCharges");
				objOtherTaxDtl.setStrSupplierCode(supplierCodeForForeignTax);
				objOtherTaxDtl.setStrTaxIndicator(" ");
				objOtherTaxDtl.setDblUnitPrice(CIFAmountForTaxCal);
				objOtherTaxDtl.setDblMRP(0);
				objOtherTaxDtl.setDblQty(1);
				objOtherTaxDtl.setDblDiscountAmt(0);
				objOtherTaxDtl.setStrPickMRPForTaxCal("N");
				objOtherTaxDtl.setStrExcisable("N");
				hmProductTaxDtl.put("OtherTaxCharges", objOtherTaxDtl);
			}

			if (clientCode.equals("226.001") && foreignSupplier && taxType.equalsIgnoreCase("Purchase")) {
				if (comesaRegionSupplier) {
					String products = "";
					String[] spProduct = prodDetails.split("!");
					for (int cn = 0; cn < spProduct.length; cn++) {
						String[] sp1 = spProduct[cn].split(",");
						if (cn == 0)
							products = "('" + sp1[cn] + "'";
						else
							products += ",'" + sp1[cn] + "'";
					}
					products = products + ")";

					StringBuilder sbSqlProducts = new StringBuilder();
					sbSqlProducts.append("select strProdCode from tblproductmaster where strProdcode in " + products + " and strComesaItem='Y'");
					List list = objBaseService.funGetList(sbSqlProducts, "sql");
					if (list.size() > 0)
						checkComesaRegionTax = true;
				}
			} else {
				String[] spProduct = prodDetails.split("!");
				String supplierCode = "", productCode = "";
				for (int cn = 0; cn < spProduct.length; cn++) {
					String[] sp1 = spProduct[cn].split(",");
					supplierCode = sp1[2];
					productCode = sp1[0];
					double unitPrice = Double.parseDouble(sp1[1]);
					double qty = Double.parseDouble(sp1[3]);
					double discAmt = Double.parseDouble(sp1[4]);
					String sqlTaxIndicator = "select strProdCode,strTaxIndicator,dblMRP,strPickMRPForTaxCal,strExciseable " + " from tblproductmaster where strProdCode='" + productCode + "'";
					listTaxDtl = objGlobalFunctionsService.funGetList(sqlTaxIndicator, "sql");
					for (int cnt = 0; cnt < listTaxDtl.size(); cnt++) {
						double taxableAmt = unitPrice * qty;
						Object[] arrObj = (Object[]) listTaxDtl.get(0);

						clsProductTaxDtl objProdTaxDtl = new clsProductTaxDtl();
						if (hmProductTaxDtl.containsKey(productCode)) {
							objProdTaxDtl = hmProductTaxDtl.get(productCode);
							objProdTaxDtl.setDblQty(objProdTaxDtl.getDblQty() + qty);
						} else {
							String sqlMarginDisc = "select a.dblDiscount,b.dblMargin " + " from tblpartymaster a,tblprodsuppmaster b " + " where a.strPCode=b.strSuppCode and a.strPCode='" + supplierCode + "' " + " and b.strProdCode='" + productCode + "' ";
							List listMarginDisc = objGlobalFunctionsService.funGetList(sqlMarginDisc, "sql");
							if (listMarginDisc.size() > 0) {
								Object[] arrObjMarginDisc = (Object[]) listMarginDisc.get(0);
								objProdTaxDtl.setDblDiscountPer(Double.parseDouble(arrObjMarginDisc[0].toString()));
								objProdTaxDtl.setDblMarginPer(Double.parseDouble(arrObjMarginDisc[1].toString()));
							} else {
								objProdTaxDtl.setDblDiscountPer(0);
								objProdTaxDtl.setDblMarginPer(0);
							}

							objProdTaxDtl.setStrProductCode(productCode);
							objProdTaxDtl.setStrSupplierCode(supplierCode);
							objProdTaxDtl.setStrTaxIndicator(arrObj[1].toString());
							objProdTaxDtl.setDblUnitPrice(unitPrice);
							objProdTaxDtl.setDblMRP(Double.parseDouble(arrObj[2].toString()));
							objProdTaxDtl.setDblQty(qty);
							objProdTaxDtl.setDblDiscountAmt(discAmt);
							objProdTaxDtl.setStrPickMRPForTaxCal(arrObj[3].toString());
							objProdTaxDtl.setStrExcisable(arrObj[4].toString());
						}
						hmProductTaxDtl.put(productCode, objProdTaxDtl);
					}
				}
			}

			StringBuilder sbSql = new StringBuilder();

			for (Map.Entry<String, clsProductTaxDtl> entry : hmProductTaxDtl.entrySet()) {
				System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
				sbSql.setLength(0);

				sbSql.append("select a.strPCode,b.strTaxCode,b.strTaxDesc,b.strTaxType,b.dblPercent,b.strTaxOnTax,b.strTaxOnTaxCode " 
						+ " ,b.strTaxCalculation,b.strTaxOnSubGroup,b.strTaxRounded,b.strCalTaxOnMRP,b.strTaxOnGD,b.dblAbatement " 
						+ " ,b.strExcisable,strTOTOnMRPItems,b.dblAmount,ifNull(b.strShortName ,'')" 
						+ " from tblpartytaxdtl a,tbltaxhd b "
						+ " where a.strTaxCode=b.strTaxCode and a.strPCode='" + entry.getValue().getStrSupplierCode() + "' and strTaxOnSP='" + taxType + "' " 
						+ " and (b.strTaxIndicator='" + entry.getValue().getStrTaxIndicator() + "' or b.strTaxIndicator='') " 
						+ " and date(b.dtValidFrom) <= '" + transDate + "' and b.dtValidTo >= '" + transDate + "' and b.strPropertyCode='" + propCode + "' "
						+ " and b.strTaxReversal='N' ");
				if (checkComesaRegionTax)
					sbSql.append(" and strNotApplicableForComesa='N' ");

				sbSql.append(" order by b.strTaxOnTax,b.strTaxCode; ");
				System.out.println(sbSql);
				List listTax = objGlobalFunctionsService.funGetList(sbSql.toString(), "sql");
				for (int cnt = 0; cnt < listTax.size(); cnt++) {
					boolean flgEligibleForTax = false;
					Object[] arrObj = (Object[]) listTax.get(cnt);
					double taxPer = Double.parseDouble(arrObj[4].toString());
					String taxCode = arrObj[1].toString();
					String taxDesc = arrObj[2].toString();
					String taxOnTaxCode = arrObj[6].toString();
					String taxOnSubGroup = arrObj[8].toString();
					String taxRounded = arrObj[9].toString();
					String calTaxOnMRP = arrObj[10].toString();
					String taxOnGD = arrObj[11].toString();
					double abatement = Double.parseDouble(arrObj[12].toString());
					String exciseable = arrObj[13].toString();
					String totOnMRPItems = arrObj[14].toString();
					double taxableAmt = entry.getValue().getDblUnitPrice() * entry.getValue().getDblQty();
					double fixAmt = Double.parseDouble(arrObj[15].toString());
					shortName = arrObj[16].toString();
					// Code to calculate taxable amt.

					if (taxType.equalsIgnoreCase("Sales")) // Code to
															// calculatetaxable
															// amt for sales
															// taxes.
					{
						if (exciseable.equals("Y")) // Excisable field from
													// taxmaster
						{
							if (entry.getValue().getStrExcisable().equals("Y")) // Excisable
																				// field/
																				// from
																				// product
																				// master
							{
								flgEligibleForTax = true;
								if (entry.getValue().getStrPickMRPForTaxCal().equals("Y")) // Check
																							// if
																							// excisewill
																							// be
																							// calculated
																							// on
																							// Product
																							// MRP
								{
									taxableAmt = (entry.getValue().getDblMRP() * entry.getValue().getDblQty());
									if (abatement > 0) {
										taxableAmt = taxableAmt * (abatement / 100);
									}
								} else // excise will be calculated on Product
										// Unit Price
								{
									double marginAmt = taxableAmt * (entry.getValue().getDblMarginPer() / 100);
									taxableAmt -= marginAmt;
									double discountAmt = taxableAmt * (entry.getValue().getDblDiscountPer() / 100);
									taxableAmt -= discountAmt;
								}
							}
						} else // Code for non excisable taxes.
						{
							if (entry.getValue().getStrPickMRPForTaxCal().equals("Y")) {
								taxableAmt = (entry.getValue().getDblMRP() * entry.getValue().getDblQty());
								if (abatement > 0) {
									taxableAmt = taxableAmt * (abatement / 100);
								}
							}
							double marginAmt = taxableAmt * (entry.getValue().getDblMarginPer() / 100);
							taxableAmt -= marginAmt;
							double discountAmt = taxableAmt * (entry.getValue().getDblDiscountPer() / 100);
							taxableAmt -= discountAmt;
							flgEligibleForTax = true;

						}
					} else // Code to calculate taxable amt for Purchase taxes.
					{
						if (exciseable.equals("Y")) // Excisable field from tax
													// master.
						{
							if (entry.getValue().getStrExcisable().equals("Y")) // Excisable
																				// fieldfrom
																				// product/
																				// master.
							{
								flgEligibleForTax = true;
								if (entry.getValue().getStrPickMRPForTaxCal().equals("Y")) // Calculate
																							// Excise
																							// on
																							// Product
																							// MRP
								{
									taxableAmt = (entry.getValue().getDblMRP() * entry.getValue().getDblQty());
									if (abatement > 0) {
										taxableAmt = taxableAmt * (abatement / 100);
									}
								} else // Calculate Excise on Product Unit Price
								{
									taxableAmt = entry.getValue().getDblUnitPrice() * entry.getValue().getDblQty();
								}
							}
						} else {
							flgEligibleForTax = true;
						}
					}

					// Reduce discount amt from taxable amt if tax is on
					// Discount
					if (taxOnGD.equals("Discount")) {
						taxableAmt -= entry.getValue().getDblDiscountAmt();
					}
					String taxDtl = taxableAmt + "#" + arrObj[1] + "#" + arrObj[2] + "#" + arrObj[3] + "#" + arrObj[4] + "#" + arrObj[16];

					if (funCheckTaxOnSubGroup(taxOnSubGroup, entry.getKey(), taxCode) && flgEligibleForTax) {
						if (arrObj[5].toString().equals("Y"))// Tax On Tax
						{
							double taxAmt = 0;

							if (taxType.equalsIgnoreCase("Sales")) // Tax on
																	// Sales
							{
								if (entry.getValue().getStrPickMRPForTaxCal().equals("Y")) {
									if (totOnMRPItems.equalsIgnoreCase("Y")) {
										if (arrObj[7].toString().equalsIgnoreCase("Backword")) {
											taxableAmt -= funGetTaxAmtForTaxOnTaxCal(hmProdTaxCalDtl.get(entry.getKey()), taxOnTaxCode);
											taxAmt = (taxableAmt / (taxPer + 100)) * taxPer;
										} else {
											taxableAmt += funGetTaxAmtForTaxOnTaxCal(hmProdTaxCalDtl.get(entry.getKey()), taxOnTaxCode);
											taxAmt = (taxPer / 100) * taxableAmt;
										}
									} else {
										taxAmt = (taxPer / 100) * taxableAmt;
									}
								} else {
									if (arrObj[7].toString().equalsIgnoreCase("Backword")) {
										taxableAmt -= funGetTaxAmtForTaxOnTaxCal(hmProdTaxCalDtl.get(entry.getKey()), taxOnTaxCode);
										taxAmt = (taxableAmt / (taxPer + 100)) * taxPer;
									} else {
										taxableAmt += funGetTaxAmtForTaxOnTaxCal(hmProdTaxCalDtl.get(entry.getKey()), taxOnTaxCode);
										taxAmt = (taxPer / 100) * taxableAmt;
									}
								}
							} else //
							{
								if (arrObj[7].toString().equalsIgnoreCase("Backword")) {
									taxableAmt -= funGetTaxAmtForTaxOnTaxCal(hmProdTaxCalDtl.get(entry.getKey()), taxOnTaxCode);
									taxAmt = (taxableAmt / (taxPer + 100)) * taxPer;
								} else {
									taxableAmt += funGetTaxAmtForTaxOnTaxCal(hmProdTaxCalDtl.get(entry.getKey()), taxOnTaxCode);
									taxAmt = (taxPer / 100) * taxableAmt;
								}
							}

							if (taxRounded.equals("Y")) {
								taxAmt = Math.rint(taxAmt);
							}
							clsTaxDtl objTaxDtl = new clsTaxDtl();
							if (hmTaxCalDtl.containsKey(taxCode)) {
								objTaxDtl = hmTaxCalDtl.get(taxCode);
								objTaxDtl.setTaxableAmt(objTaxDtl.getTaxableAmt() + taxableAmt);
								objTaxDtl.setTaxAmt(objTaxDtl.getTaxAmt() + taxAmt);
								objTaxDtl.setStrTaxShorName(shortName);
							} else {
								objTaxDtl.setTaxCode(taxCode);
								objTaxDtl.setTaxName(taxDesc);
								objTaxDtl.setTaxableAmt(taxableAmt);
								objTaxDtl.setTaxAmt(taxAmt);
								objTaxDtl.setTaxPer(taxPer);
								objTaxDtl.setStrTaxShorName(shortName);
							}
							hmTaxCalDtl.put(taxCode, objTaxDtl);

							objTaxDtl = new clsTaxDtl();
							objTaxDtl.setTaxCode(taxCode);
							objTaxDtl.setTaxName(taxDesc);
							objTaxDtl.setTaxableAmt(taxableAmt);
							objTaxDtl.setTaxAmt(taxAmt);
							objTaxDtl.setTaxPer(taxPer);
							hmTaxCalDtlTemp.put(taxCode, objTaxDtl);
							hmProdTaxCalDtl.put(entry.getKey(), hmTaxCalDtlTemp);
						} else {
							double taxAmt = 0;
							if (arrObj[7].toString().equalsIgnoreCase("Backword")) {
								taxAmt = (taxableAmt / (taxPer + 100)) * taxPer;
								if (arrObj[3].toString().equalsIgnoreCase("Fixed Amount")) {
									taxAmt = fixAmt;
								}
							} else {
								taxAmt = (taxPer / 100) * taxableAmt;
								if (arrObj[3].toString().equalsIgnoreCase("Fixed Amount")) {
									taxAmt = fixAmt;
								}
							}

							if (taxRounded.equals("Y")) {
								taxAmt = Math.rint(taxAmt);
							}
							clsTaxDtl objTaxDtl = new clsTaxDtl();
							if (hmTaxCalDtl.containsKey(taxCode)) {
								objTaxDtl = hmTaxCalDtl.get(taxCode);
								objTaxDtl.setTaxableAmt(objTaxDtl.getTaxableAmt() + taxableAmt);
								objTaxDtl.setTaxAmt(objTaxDtl.getTaxAmt() + taxAmt);
								objTaxDtl.setStrTaxShorName(shortName);
							} else {
								objTaxDtl.setTaxCode(taxCode);
								objTaxDtl.setTaxName(taxDesc);
								objTaxDtl.setTaxableAmt(taxableAmt);
								objTaxDtl.setTaxAmt(taxAmt);
								objTaxDtl.setTaxPer(taxPer);
								objTaxDtl.setStrTaxShorName(shortName);
							}
							hmTaxCalDtl.put(taxCode, objTaxDtl);

							objTaxDtl = new clsTaxDtl();
							objTaxDtl.setTaxCode(taxCode);
							objTaxDtl.setTaxName(taxDesc);
							objTaxDtl.setTaxableAmt(taxableAmt);
							objTaxDtl.setTaxAmt(taxAmt);
							objTaxDtl.setTaxPer(taxPer);
							hmTaxCalDtlTemp.put(taxCode, objTaxDtl);
							hmProdTaxCalDtl.put(entry.getKey(), hmTaxCalDtlTemp);
						}
					}

					// order-> taxable amt,Tax code,tax desc,tax type,tax per
					hmProductTaxDtl1.put(arrObj[1].toString(), taxDtl);
				}

				// Start For Customer taxes are not add in Customer tax tab and
				// also wants to calcutate taxes
				if (false) // No need For Customer taxes are not add in Customer
							// tax tab and also wants to calcutate taxes
				{
					String sql = "select b.strTaxCode,b.strTaxDesc,b.strTaxType,b.dblPercent,b.strTaxOnTax,b.strTaxOnTaxCode " + " ,b.strTaxCalculation,b.strTaxOnSubGroup,b.strTaxRounded,b.strCalTaxOnMRP,b.strTaxOnGD,b.dblAbatement " + " ,b.strExcisable,strTOTOnMRPItems,b.dblAmount,ifNull(b.strShortName ,'')" + " from tbltaxhd b " + " where  strTaxOnSP='" + taxType + "' "
							+ " and (b.strTaxIndicator='" + entry.getValue().getStrTaxIndicator() + "' or b.strTaxIndicator='') " + " and date(b.dtValidFrom) <= '" + transDate + "' and b.dtValidTo >= '" + transDate + "' and b.strPropertyCode='" + propCode + "' " + " order by b.strTaxOnTax,b.strTaxCode; ";

					if (listTax.size() == 0) {
						listTax = objGlobalFunctionsService.funGetList(sql, "sql");

						for (int cnt = 0; cnt < listTax.size(); cnt++) {
							boolean flgEligibleForTax = false;
							Object[] arrObj = (Object[]) listTax.get(cnt);
							double taxPer = Double.parseDouble(arrObj[3].toString());
							String taxCode = arrObj[0].toString();
							String taxDesc = arrObj[1].toString();
							String taxOnTaxCode = arrObj[5].toString();
							String taxOnSubGroup = arrObj[7].toString();
							String taxRounded = arrObj[8].toString();
							String calTaxOnMRP = arrObj[9].toString();
							String taxOnGD = arrObj[10].toString();
							double abatement = Double.parseDouble(arrObj[11].toString());
							String exciseable = arrObj[12].toString();
							String totOnMRPItems = arrObj[13].toString();
							double taxableAmt = entry.getValue().getDblUnitPrice() * entry.getValue().getDblQty();
							double fixAmt = Double.parseDouble(arrObj[14].toString());
							shortName = arrObj[15].toString();
							// Code to calculate taxable amt.

							if (taxType.equalsIgnoreCase("Sales")) // Code to/ calculatetaxable/ amt for sales taxes.
							{
								if (exciseable.equals("Y")) // Excisable field from tax master
								{
									if (entry.getValue().getStrExcisable().equals("Y")) // Excisable field from/ productmaster
									{
										flgEligibleForTax = true;
										if (entry.getValue().getStrPickMRPForTaxCal().equals("Y")) // Check ifexcisewill be calculated on Product MRP
										{
											taxableAmt = (entry.getValue().getDblMRP() * entry.getValue().getDblQty());
											if (abatement > 0) {
												taxableAmt = taxableAmt * (abatement / 100);
											}
										} else // excise will be calculated on
												// Product Unit Price
										{
											double marginAmt = taxableAmt * (entry.getValue().getDblMarginPer() / 100);
											taxableAmt -= marginAmt;
											double discountAmt = taxableAmt * (entry.getValue().getDblDiscountPer() / 100);
											taxableAmt -= discountAmt;
										}
									}
								} else // Code for non excisable taxes.
								{
									if (entry.getValue().getStrPickMRPForTaxCal().equals("Y")) {
										taxableAmt = (entry.getValue().getDblMRP() * entry.getValue().getDblQty());
										if (abatement > 0) {
											taxableAmt = taxableAmt * (abatement / 100);
										}
									}
									double marginAmt = taxableAmt * (entry.getValue().getDblMarginPer() / 100);
									taxableAmt -= marginAmt;
									double discountAmt = taxableAmt * (entry.getValue().getDblDiscountPer() / 100);
									taxableAmt -= discountAmt;
									flgEligibleForTax = true;
								}
							} else // Code to calculate taxable amt for Purchase
									// taxes.
							{
								if (exciseable.equals("Y")) // Excisable field
															// from tax master.
								{
									if (entry.getValue().getStrExcisable().equals("Y")) // Excisable field from product master.
									{
										flgEligibleForTax = true;
										if (entry.getValue().getStrPickMRPForTaxCal().equals("Y")) // Calculate Excise on Product MRP
										{
											taxableAmt = (entry.getValue().getDblMRP() * entry.getValue().getDblQty());
											if (abatement > 0) {
												taxableAmt = taxableAmt * (abatement / 100);
											}
										} else // Calculate Excise on Product
												// Unit Price
										{
											taxableAmt = entry.getValue().getDblUnitPrice() * entry.getValue().getDblQty();
										}
									}
								} else {
									flgEligibleForTax = true;
								}
							}

							// Reduce discount amt from taxable amt if tax is on
							// Discount
							if (taxOnGD.equals("Discount")) {
								taxableAmt -= entry.getValue().getDblDiscountAmt();
							}
							String taxDtl = taxableAmt + "#" + arrObj[0] + "#" + arrObj[1] + "#" + arrObj[2] + "#" + arrObj[3] + "#" + arrObj[15];

							if (funCheckTaxOnSubGroup(taxOnSubGroup, entry.getKey(), taxCode) && flgEligibleForTax) {
								if (arrObj[4].toString().equals("Y"))// Tax On
																		// Tax
								{
									double taxAmt = 0;

									if (taxType.equalsIgnoreCase("Sales")) // Tax on Sales
									{
										if (entry.getValue().getStrPickMRPForTaxCal().equals("Y")) {
											if (totOnMRPItems.equalsIgnoreCase("Y")) {
												if (arrObj[6].toString().equalsIgnoreCase("Backword")) {
													taxableAmt -= funGetTaxAmtForTaxOnTaxCal(hmProdTaxCalDtl.get(entry.getKey()), taxOnTaxCode);
													taxAmt = (taxableAmt / (taxPer + 100)) * taxPer;
												} else {
													taxableAmt += funGetTaxAmtForTaxOnTaxCal(hmProdTaxCalDtl.get(entry.getKey()), taxOnTaxCode);
													taxAmt = (taxPer / 100) * taxableAmt;
												}
											} else {
												taxAmt = (taxPer / 100) * taxableAmt;
											}
										} else {
											if (arrObj[6].toString().equalsIgnoreCase("Backword")) {
												taxableAmt -= funGetTaxAmtForTaxOnTaxCal(hmProdTaxCalDtl.get(entry.getKey()), taxOnTaxCode);
												taxAmt = (taxableAmt / (taxPer + 100)) * taxPer;
											} else {
												taxableAmt += funGetTaxAmtForTaxOnTaxCal(hmProdTaxCalDtl.get(entry.getKey()), taxOnTaxCode);
												taxAmt = (taxPer / 100) * taxableAmt;
											}
										}
									} else //
									{
										if (arrObj[6].toString().equalsIgnoreCase("Backword")) {
											taxableAmt -= funGetTaxAmtForTaxOnTaxCal(hmProdTaxCalDtl.get(entry.getKey()), taxOnTaxCode);
											taxAmt = (taxableAmt / (taxPer + 100)) * taxPer;
										} else {
											taxableAmt += funGetTaxAmtForTaxOnTaxCal(hmProdTaxCalDtl.get(entry.getKey()), taxOnTaxCode);
											taxAmt = (taxPer / 100) * taxableAmt;
										}
									}

									if (taxRounded.equals("Y")) {
										taxAmt = Math.rint(taxAmt);
									}
									clsTaxDtl objTaxDtl = new clsTaxDtl();
									if (hmTaxCalDtl.containsKey(taxCode)) {
										objTaxDtl = hmTaxCalDtl.get(taxCode);
										objTaxDtl.setTaxableAmt(objTaxDtl.getTaxableAmt() + taxableAmt);
										objTaxDtl.setTaxAmt(objTaxDtl.getTaxAmt() + taxAmt);
										objTaxDtl.setStrTaxShorName(shortName);
									} else {
										objTaxDtl.setTaxCode(taxCode);
										objTaxDtl.setTaxName(taxDesc);
										objTaxDtl.setTaxableAmt(taxableAmt);
										objTaxDtl.setTaxAmt(taxAmt);
										objTaxDtl.setTaxPer(taxPer);
										objTaxDtl.setStrTaxShorName(shortName);
									}
									hmTaxCalDtl.put(taxCode, objTaxDtl);

									objTaxDtl = new clsTaxDtl();
									objTaxDtl.setTaxCode(taxCode);
									objTaxDtl.setTaxName(taxDesc);
									objTaxDtl.setTaxableAmt(taxableAmt);
									objTaxDtl.setTaxAmt(taxAmt);
									objTaxDtl.setTaxPer(taxPer);
									hmTaxCalDtlTemp.put(taxCode, objTaxDtl);
									hmProdTaxCalDtl.put(entry.getKey(), hmTaxCalDtlTemp);
								} else {
									double taxAmt = 0;
									if (arrObj[6].toString().equalsIgnoreCase("Backword")) {
										taxAmt = (taxableAmt / (taxPer + 100)) * taxPer;
										if (arrObj[2].toString().equalsIgnoreCase("Fixed Amount")) {
											taxAmt = fixAmt;
										}
									} else {
										taxAmt = (taxPer / 100) * taxableAmt;
										if (arrObj[2].toString().equalsIgnoreCase("Fixed Amount")) {
											taxAmt = fixAmt;
										}
									}

									if (taxRounded.equals("Y")) {
										taxAmt = Math.rint(taxAmt);
									}
									clsTaxDtl objTaxDtl = new clsTaxDtl();
									if (hmTaxCalDtl.containsKey(taxCode)) {
										objTaxDtl = hmTaxCalDtl.get(taxCode);
										objTaxDtl.setTaxableAmt(objTaxDtl.getTaxableAmt() + taxableAmt);
										objTaxDtl.setTaxAmt(objTaxDtl.getTaxAmt() + taxAmt);
										objTaxDtl.setStrTaxShorName(shortName);
									} else {
										objTaxDtl.setTaxCode(taxCode);
										objTaxDtl.setTaxName(taxDesc);
										objTaxDtl.setTaxableAmt(taxableAmt);
										objTaxDtl.setTaxAmt(taxAmt);
										objTaxDtl.setTaxPer(taxPer);
										objTaxDtl.setStrTaxShorName(shortName);
									}
									hmTaxCalDtl.put(taxCode, objTaxDtl);

									objTaxDtl = new clsTaxDtl();
									objTaxDtl.setTaxCode(taxCode);
									objTaxDtl.setTaxName(taxDesc);
									objTaxDtl.setTaxableAmt(taxableAmt);
									objTaxDtl.setTaxAmt(taxAmt);
									objTaxDtl.setTaxPer(taxPer);
									hmTaxCalDtlTemp.put(taxCode, objTaxDtl);
									hmProdTaxCalDtl.put(entry.getKey(), hmTaxCalDtlTemp);
								}
							}
							// order-> taxable amt,Tax code,tax desc,tax
							// type,tax per
							hmProductTaxDtl1.put(arrObj[0].toString(), taxDtl);
						}
					}
				}// no need to calculate for customer taxes are not add in
					// Customer tax tab and also wants to calcutate taxes
					// End For Customer taxes are not add in Customer tax tab
					// and also wants to calcutate taxes
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		hmProductTaxDtl1.clear();
		for (Map.Entry<String, clsTaxDtl> entry : hmTaxCalDtl.entrySet()) {
			// order-> taxable amt,Tax code,tax desc,tax type,tax per,tax amt

			String taxDtl = entry.getValue().getTaxableAmt() + "#" + entry.getValue().getTaxCode() + "#" + entry.getValue().getTaxName() + "#NA" + "#" + entry.getValue().getTaxPer() + "#" + entry.getValue().getTaxAmt() + "#" + entry.getValue().getStrTaxShorName();
			hmProductTaxDtl1.put(entry.getKey(), taxDtl);
		}

		return hmProductTaxDtl1;
	}

	private boolean funCheckTaxOnSubGroup(String taxOnSubGroup, String productCode, String taxCode) {
		boolean flgTaxOnSG = false;

		if (taxOnSubGroup.equalsIgnoreCase("N")) {
			flgTaxOnSG = true;
		} else {
			String sql = "select strSGCode from tblproductmaster " + " where strProdCode='" + productCode + "' ";
			List listProdSG = objGlobalFunctionsService.funGetList(sql, "sql");
			if (listProdSG.size() > 0) {
				String SGCode = (String) listProdSG.get(0);
				sql = "select * from tbltaxsubgroupdtl " + " where strTaxCode='" + taxCode + "' and strSGCode='" + SGCode + "' ";
				List listTaxSG = objGlobalFunctionsService.funGetList(sql, "sql");
				if (listTaxSG.size() > 0) {
					flgTaxOnSG = true;
				}
			}
		}

		return flgTaxOnSG;
	}

	private double funGetTaxAmtForTaxOnTaxCal(Map<String, clsTaxDtl> hmTaxDtl, String taxOnTaxCode) {
		double taxAmtForTaxOnTax = 0;

		if (null != hmTaxDtl) {
			String[] spArrTaxOnTaxCode = taxOnTaxCode.split(",");

			for (int cnt = 0; cnt < spArrTaxOnTaxCode.length; cnt++) {
				if (null != hmTaxDtl.get(spArrTaxOnTaxCode[cnt])) {
					clsTaxDtl objTaxDtl = hmTaxDtl.get(spArrTaxOnTaxCode[cnt]);
					taxAmtForTaxOnTax += objTaxDtl.getTaxAmt();
				}
			}
		}

		return taxAmtForTaxOnTax;
	}

	// Function used to sort HashMap on hashmap values

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static HashMap funSortByValues(HashMap map) {
		List list = new LinkedList(map.entrySet());
		// Defined Custom Comparator here
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
			}
		});

		// Here I am copying the sorted list in HashMap
		// using LinkedHashMap to preserve the insertion order
		HashMap sortedHashMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedHashMap.put(entry.getKey(), entry.getValue());
		}
		return sortedHashMap;
	}

	/***
	 * Update UOM in Product Master
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/updateUOMData", method = RequestMethod.GET)
	public @ResponseBody String funUpdateUOM(HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String strProdCode = req.getParameter("strProdCode").toString();
		String strUOM = req.getParameter("strUOM").toString();
		String transaction = req.getParameter("transaction").toString();
		String sql = "";
		String status = "";
		int res = 0;
		clsProductMasterModel objModel = objProductMasterService.funGetObject(strProdCode, clientCode);
		if (transaction.equalsIgnoreCase("Req") || transaction.equalsIgnoreCase("MIS")) {
			if (objModel.getStrUOM().equals("") || objModel.getStrIssueUOM().equals("")) {
				sql = "Update tblproductmaster set strIssueUOM = '" + strUOM + "',strUOM='" + strUOM + "' where strProdCode = '" + strProdCode + "' and strClientCode='" + clientCode + "'";
				res = objGlobalFunctionsService.funUpdate(sql, "sql");
				if (res > 0) {
					status = "Updated";
				} else {
					status = "updatefail";
				}
			}
		}
		if (transaction.equals("PO")) {
			// clsProductMasterModel
			// objModel=objProductMasterService.funGetObject(strProdCode,clientCode);
			if (objModel.getStrReceivedUOM().equals("")) {
				sql = "Update tblproductmaster set strReceivedUOM = '" + strUOM + "' where strProdCode = '" + strProdCode + "' and strClientCode='" + clientCode + "'";
				res = objGlobalFunctionsService.funUpdate(sql, "sql");
				if (res > 0) {
					status = "Updated";
				} else {
					status = "updatefail";
				}
			}
		}
		return status;
	}

	/**
	 * Checking Form is Audit Mode or Not
	 * 
	 * @param fromName
	 * @param request
	 * @return true or false
	 */
	public boolean funCheckAuditFrom(String fromName, HttpServletRequest request) {
		String code = request.getSession().getAttribute("propertyCode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		boolean flag = false;
		if ("Y".equalsIgnoreCase(request.getSession().getAttribute("audit").toString())) {
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(code, clientCode);
			if (objSetup != null && null != objSetup.getStrAuditFrom()) {
				String strForms = objSetup.getStrAuditFrom();
				String trmp[] = strForms.split(",");
				for (int i = 0; i < trmp.length; i++) {
					if (fromName.equalsIgnoreCase(trmp[i].toString())) {
						flag = true;
					}
				}

			}
		}
		return flag;
	}

	/**
	 * Display Pending Requisition Notification
	 * 
	 * @param req
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getNotification", method = RequestMethod.GET)
	public @ResponseBody List funGetPendingRequisitionNotification(HttpServletRequest req,HttpServletResponse resp) {
		List list = new ArrayList();
		if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("1-WebStocks")) {

			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String strLocCode = req.getSession().getAttribute("locationCode").toString();
			String strUserCode=req.getSession().getAttribute("usercode").toString();
			String locationName= req.getSession().getAttribute("locationName").toString();
			clsUserMasterModel objModel = objUserMasterService.funGetObject(strUserCode, clientCode);
			
			String dteCurrDate = funGetCurrentDate("yyyy-MM-dd");
			
			String sql = "select a.strReqCode,b.strLocName as Locationby,a.strNarration,a.strUserCreated,'MIS' as FormName ,b.strLocCode as strLocOn " 
			        + " from tblreqhd a left outer join tbllocationmaster b on  b.strLocCode=a.strLocBy " + " left outer join tbllocationmaster c on  c.strLocCode=a.strLocOn " + " inner join tbllocationmaster d on c.strLocCode=d.strLocCode and  d.strLocCode='" + strLocCode + "' "
					+ " Where a.strReqCode IN (select a.strReqCode from tblreqdtl a left outer join (select b.strReqCode, b.strProdCode, SUM(dblQty) ReqQty " + " from tblmishd a, tblmisdtl b Where a.strMISCode = b.strMISCode group by b.strReqCode, b.strProdCode) b " + " on a.strReqCode  = b.strReqCode and a.strProdCode = b.strProdCode  where  a.dblQty > ifnull(b.ReqQty,0)) "
					+ " and a.strAuthorise='Yes' and a.strClientCode='"+ clientCode+ "' and a.dtReqDate='"+ funGetCurrentDate("yyyy-MM-dd")+ "' "
					+ " group by a.strLocBy,a.strReqCode,b.strLocName,a.strNarration,a.strUserCreated,'MIS' "
					
					+ " union all " 
					
					+ " select a.strPIcode,'' as Locationby,a.strNarration,a.strUserCreated,'PurchaseOrder' as FormName,'' as strLocOn " 
					+ " from tblpurchaseindendhd a Where a.strPIcode IN ( " + " select a.strPIcode from tblpurchaseindenddtl a left outer join "
					+ " (select b.strPIcode, b.strProdCode, SUM(dblOrdQty) ReqQty " + " from tblpurchaseorderhd a, tblpurchaseorderdtl b "
					+ " Where a.strPOCode = b.strPOCode  group by b.strPIcode,b.strPOCode, b.strProdCode) b " + " on a.strPIcode  = b.strPIcode and a.strProdCode = b.strProdCode " + " where  a.dblQty > ifnull(b.ReqQty,0))  and a.strAuthorise='Yes' and a.strClosePI='No'  " + " and a.strClientCode='" + clientCode + "' and a.dtPIDate='" + funGetCurrentDate("yyyy-MM-dd") + "' "
					+ " group by a.strLocCode,a.strPIcode,a.strNarration,a.strUserCreated,'PurchaseOrder' ";
				
			if(null!=objModel){
					if(objModel.getStrReorderLevel().equalsIgnoreCase("Y")){
						objReportsController.funStockFlash(dteCurrDate, strLocCode, dteCurrDate, dteCurrDate, clientCode, strUserCode, "Y", req, resp);
						
						sql=sql+
					 " union all "
					 
					 + " SELECT Count(*),'"+locationName+"' as Locationby,'' as strNarration,'"+strUserCode+"' as strUserCreated,'Reorder Level' as FormName,'' as strLocOn "
					 + " FROM tblproductmaster a "
					 + " INNER JOIN tblreorderlevel b ON a.strProdCode = b.strProdCode AND b.strClientCode='226.001' "
					 + " INNER JOIN tblcurrentstock c ON b.strProdCode = c.strProdCode AND c.strClientCode='226.001' AND c.strUserCode = 'SANGUINE' "
					 + " LEFT OUTER "
					 + " JOIN ( "
					 + " SELECT a.strProdCode, (a.dblQty - IFNULL(d.dblQty,0)) AS pendingQty "
					 + " FROM tblreqdtl a "
					 + " LEFT OUTER "
					 + " JOIN tblreqhd b ON a.strReqCode = b.strReqCode AND b.strClientCode='"+clientCode+"' "
					 + " LEFT OUTER "
					 + " JOIN tblmisdtl d ON a.strReqCode=d.strReqCode AND a.strProdCode = d.strProdCode AND d.strClientCode='"+clientCode+"' "
					 + " WHERE b.strLocby ='"+strLocCode+"' AND a.strClientCode='"+clientCode+"' "
					 + " GROUP BY a.strProdCode) d ON a.strProdCode = d.strProdCode "
					 + " INNER JOIN tblsubgroupmaster e ON a.strSGCode=e.strSGCode  AND e.strClientCode='"+clientCode+"' "
					 + " INNER JOIN tblgroupmaster f ON e.strGCode=f.strGCode  AND f.strClientCode='"+clientCode+"' "
					 + " WHERE b.strLocationCode='"+strLocCode+"' AND c.dblClosingStk <= b.dblReOrderLevel AND b.dblReOrderLevel > 0 AND a.strClientCode='"+clientCode+"'" ; 
						
					}
				}
			sql=sql+";";
			list = objGlobalFunctionsService.funGetListReportQuery(sql);
		}
		return list;

	}

	/**
	 * Excise Conversion ML Data into Peg
	 * 
	 */
	public String funConversionMLPeg(String brandCode, String qty, String type, String clientCode, HttpServletRequest req) {

		String isBrandGlobal = "Custom";
		String isSizeGlobal = "Custom";
		String tempSizeClientCode = clientCode;
		try {
			isSizeGlobal = req.getSession().getAttribute("strSizeMaster").toString();
		} catch (Exception e) {
			isSizeGlobal = "Custom";
		}
		if (isSizeGlobal.equalsIgnoreCase("All")) {
			tempSizeClientCode = "All";
		}

		String tempBrandClientCode = clientCode;
		try {
			isBrandGlobal = req.getSession().getAttribute("strBrandMaster").toString();
		} catch (Exception e) {
			isBrandGlobal = "Custom";
		}
		if (isBrandGlobal.equalsIgnoreCase("All")) {
			tempBrandClientCode = "All";
		}
		int intPegSize = 0, intQty = 0;
		String conversion = null;
		double dblqty = Double.parseDouble(qty);
		String sizeSql = "select a.strBrandCode,a.strSizeCode,a.strShortName,b.intQty,a.intPegSize " + "  from tblbrandmaster a, tblsizemaster b " + " where a.strBrandCode='" + brandCode + "' and a.strSizeCode=b.strSizeCode  " + " and  a.strClientCode='" + tempBrandClientCode + "' and b.strClientCode='" + tempSizeClientCode + "'  ";

		List result = objGlobalFunctionsService.funGetListModuleWise(sizeSql, "sql");

		if (!result.isEmpty()) {

			Object[] obj = (Object[]) result.get(0);
			String strBrandCode = obj[0].toString();
			intPegSize = Integer.parseInt(obj[4].toString());
			intQty = Integer.parseInt(obj[3].toString());

		}
		switch (type) {
		case "peg": {
			if (qty.contains(".")) {
				String[] qty1 = qty.split("\\.");
				int btlInMl = (Integer.parseInt(qty1[0])) * intQty;
				int pegInMl = (Integer.parseInt(qty1[1])) * intPegSize;
				int qtyInMl = btlInMl + pegInMl;
				Double a = (double) qtyInMl / (double) intQty;
				long btl = (new Double(a)).longValue();
				Double peg = (a) - btl;
				int peg1 = (int) ((peg * intQty) / intPegSize);

				String btlInPeg = String.valueOf(btl);
				String pegInPeg = Integer.toString(peg1);
				conversion = btlInPeg + "." + pegInPeg;
			} else {
				long btl = (long) (dblqty / intQty);
				Double peg = (dblqty / intQty) - btl;
				peg = (peg * intQty) / intPegSize;
				String btlInPeg = String.valueOf(btl);
				String pegInPeg = Double.toString(peg);
				conversion = btlInPeg + "." + pegInPeg;

			}
			break;
		}
		case "ml": {
			if (qty.contains(".")) {
				String[] qty1 = qty.split("\\.");
				int btlInMl = (Integer.parseInt(qty1[0])) * intQty;
				int pegInMl = (Integer.parseInt(qty1[1])) * intPegSize;
				dblqty = btlInMl + pegInMl;
			}
			conversion = Double.toString(dblqty);
			break;
		}
		}

		return conversion;
	}

	public String funGetModuleName(String moduleNo) {
		String moduleName = "";
		switch (moduleNo) {

		case "1":
			moduleName = "WebStocks";
			break;

		case "2":
			moduleName = "WebExcise";
			break;

		case "3":
			moduleName = "WebPMS";
			break;

		case "4":
			moduleName = "WebClub";
			break;

		case "5":
			moduleName = "WebBook";
			break;

		case "6":
			moduleName = "WebCRM";
			break;

		case "7":
			moduleName = "WebPOS";
			break;

		}
		return moduleName;
	}

	private class clsTaxDtl {
		private String taxCode;

		private String taxName;

		private String taxCal;

		private double taxableAmt;

		private double taxAmt;

		private double taxPer;

		private String strTaxShorName;

		public String getTaxCode() {
			return taxCode;
		}

		public void setTaxCode(String taxCode) {
			this.taxCode = taxCode;
		}

		public String getTaxName() {
			return taxName;
		}

		public void setTaxName(String taxName) {
			this.taxName = taxName;
		}

		public String getTaxCal() {
			return taxCal;
		}

		public void setTaxCal(String taxCal) {
			this.taxCal = taxCal;
		}

		public double getTaxableAmt() {
			return taxableAmt;
		}

		public void setTaxableAmt(double taxableAmt) {
			this.taxableAmt = taxableAmt;
		}

		public double getTaxAmt() {
			return taxAmt;
		}

		public void setTaxAmt(double taxAmt) {
			this.taxAmt = taxAmt;
		}

		public double getTaxPer() {
			return taxPer;
		}

		public void setTaxPer(double taxPer) {
			this.taxPer = taxPer;
		}

		public String getStrTaxShorName() {
			return strTaxShorName;
		}

		public void setStrTaxShorName(String strTaxShorName) {
			this.strTaxShorName = strTaxShorName;
		}

	}

	// Load Account Master Dtl Data On Form
	@RequestMapping(value = "/getAccountMasterDtl", method = RequestMethod.GET)
	public @ResponseBody clsWebBooksAccountMasterBean funAccountMasterDtl(@RequestParam("accountCode") String accountCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		List list = objGlobalFunctionsService.funGetWebBooksAccountDtl(accountCode, clientCode);

		clsWebBooksAccountMasterBean objACMaster = new clsWebBooksAccountMasterBean();
		if (list.size() > 0) {
			Object[] obj = (Object[]) list.get(0);
			objACMaster.setStrAccountCode(obj[0].toString());
			objACMaster.setStrAccountName(obj[1].toString());
		} else {
			objACMaster.setStrAccountCode("Invalid Code");
			objACMaster.setStrAccountName("Invalid ");
		}
		return objACMaster;
	}

	public JSONObject funGETMethodUrlJosnObjectData(String strUrl) {
		JSONObject jObj = null;
		;
		try {

			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output = "", op = "";
			while ((output = br.readLine()) != null) {
				op += output;
			}
			System.out.println("Obj=" + op);
			conn.disconnect();

			JSONParser parser = new JSONParser();
			Object obj = parser.parse(op);
			jObj = (JSONObject) obj;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jObj;

	}

	public JSONObject funPOSTMethodUrlJosnObjectData(String strUrl, JSONObject objRows) {
		JSONObject josnObjRet = new JSONObject();

		try {
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(objRows.toString().getBytes());
			os.flush();
			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output = "", op = "";

			while ((output = br.readLine()) != null) {
				op += output;
			}
			System.out.println("Result= " + op);
			conn.disconnect();

			JSONParser parser = new JSONParser();
			Object obj = parser.parse(op);
			josnObjRet = (JSONObject) obj;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return josnObjRet;
		}

	}

	public void funGetChildNodes1(String strProdCode, double qty, String clientCode, String userCode) {
		Map<String, List> hmChildNodes = new HashMap<String, List>();
		// List retlist =new ArrayList<>();
		listChildNodes1 = new ArrayList<String>();

		funGetBOMNodes(strProdCode, 0, qty);

		for (int cnt = 0; cnt < listChildNodes1.size(); cnt++) {
			List arrListBOMProducts = new ArrayList<String>();
			String temp = (String) listChildNodes1.get(cnt);
			String prodCode = temp.split(",")[0];
			double reqdQty = Double.parseDouble(temp.split(",")[1]);
		}

		// return retlist;

	}

	public List funGetBOMNodes(String parentProdCode, double bomQty, double qty) {
		double finalQty = 0;
		double qtyChild = 0.0;
		List listTemp = new ArrayList<String>();
		String sql = "select b.strChildCode from  tblbommasterhd a,tblbommasterdtl b " + "where a.strBOMCode=b.strBOMCode and a.strParentCode='" + parentProdCode + "' ";
		listTemp = objGlobalFunctionsService.funGetList(sql, "sql");
		if (listTemp.size() > 0) {
			for (int cnt = 0; cnt < listTemp.size(); cnt++) {
				String childNode = (String) listTemp.get(cnt);
				bomQty = funGetBOMQty(childNode, parentProdCode);
				if (hmChildNodes.containsKey(childNode)) {
					qtyChild = (double) hmChildNodes.get(childNode);
					qty = qty * bomQty + qtyChild;
					hmChildNodes.put(childNode, qty);
				} else {
					qty = qty * bomQty;
					hmChildNodes.put(childNode, qty);
				}
				funGetBOMNodes(childNode, bomQty, qty);
			}
			finalQty = qty;
		} else {
			listChildNodes1.add(parentProdCode + "," + (double) hmChildNodes.get(parentProdCode));
		}
		return listChildNodes1;
	}

	public double funGetBOMQty(String childCode, String parentCode) {
		double bomQty = 0;
		try {
			String sql = "select ifnull(left(((c.dblReceiveConversion/c.dblIssueConversion)/c.dblRecipeConversion),6) * b.dblQty,0) as BOMQty " + "from tblbommasterhd a,tblbommasterdtl b,tblproductmaster c " + "where a.strBOMCode=b.strBOMCode and a.strParentCode=c.strProdCode and a.strParentCode='" + parentCode + "' " + "and b.strChildCode='" + childCode + "'";
			List listChildQty = objGlobalFunctionsService.funGetList(sql, "sql");
			if (listChildQty.size() > 0) {
				bomQty = (Double) listChildQty.get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bomQty;
	}

	public void funDeleteAndInsertWebBookLedgerTable(String clientCode, String userCode, String propertyCode) {
		objGlobalFunctionsService.funDeleteWebBookLedgerSummary(clientCode, userCode, propertyCode);

	}

	public void funInvokeWebBookGeneralLedger(String acCode, String startDate, String propertyCode, String fromDate, String toDate, String clientCode, double conversionRate
			, String userCode, HttpServletRequest req, HttpServletResponse resp, String strCrOrDr) {
			funDeleteAndInsertWebBookLedgerTable(clientCode, userCode, propertyCode);
			if (!startDate.equals(fromDate)){

				String tempFromDate = fromDate.split("-")[2] + "-" + fromDate.split("-")[1] + "-" + fromDate.split("-")[0];
				SimpleDateFormat obj = new SimpleDateFormat("dd-MM-yyyy");
				Date dt1;
				try {
					dt1 = obj.parse(tempFromDate);
					GregorianCalendar cal = new GregorianCalendar();
					cal.setTime(dt1);
					cal.add(Calendar.DATE, -1);
					String newToDate = (cal.getTime().getYear() + 1900) + "-" + (cal.getTime().getMonth() + 1) + "-" + (cal.getTime().getDate());

					funProcessWebBookGeneralLedgerSummary(acCode, startDate, newToDate,  clientCode, userCode, propertyCode, req, resp, strCrOrDr, conversionRate);

					// both opening creditor or debtor opening balance and from date
					// not eaqual to date opening table
					String sql = " SELECT ifnull(dteVochDate,''),'Opening','Op','',ifnull(billDate,''), ifnull(SUM(dblDebitAmt),0.00), ifnull(SUM(dblCreditAmt),0.0), ifnull(SUM(dblBalanceAmt),0.0) from  "

					+ "( SELECT  dteVochDate,'Opening','Op','',dteBillDate billDate, SUM(dblDebitAmt) dblDebitAmt, " + " SUM(dblCreditAmt) dblCreditAmt, SUM(dblBalanceAmt) dblBalanceAmt " + " FROM tblledgersummary " + " WHERE strUserCode='" + userCode + "' AND strPropertyCode='" + propertyCode + "' AND strClientCode='" + clientCode + "' "
							+ " GROUP BY strUserCode,strPropertyCode,strClientCode "

					 + " UNION  All  "
					 +" select DATE_FORMAT(DATE(a.dteCreatedDate),'%d-%m-%Y') dteVochDate,'Opening','Op','', " 
					 +" DATE_FORMAT(DATE(a.dteCreatedDate),'%d-%m-%Y') billDate, "
					 + "IF(a.strCrDr='Dr',a.intOpeningBal,0) dblDebitAmt, IF(a.strCrDr='Cr',a.intOpeningBal,0) dblCreditAmt, (IF(a.strCrDr='Dr',a.intOpeningBal,0) - IF(a.strCrDr='Cr',a.intOpeningBal,0)) dblBalanceAmt  from tblacmaster a where a.strAccountCode='"+acCode+"' "
				     +" and a.strClientCode='"+clientCode+"' and a.strPropertyCode='" + propertyCode + "')opening ";
					
					StringBuilder sbSql=new StringBuilder(sql);
					List list=new ArrayList();
					try {
						list = objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
					} catch (Exception e) {
						// TODO Auto-generated catch block
					}
//					List list = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
					if (list.size() > 0) {
						funDeleteAndInsertWebBookLedgerTable(clientCode, userCode, propertyCode);
				for (int i = 0; i < list.size(); i++) {
							
							clsLedgerSummaryModel objSummaryledger = new clsLedgerSummaryModel();
							Object[] objArr = (Object[]) list.get(i);
							double amt=Double.parseDouble(objArr[5].toString())-Double.parseDouble(objArr[6].toString());
							if(Double.parseDouble(objArr[5].toString())!=0 || Double.parseDouble(objArr[6].toString())!=0)
							{
								
								objSummaryledger.setStrVoucherNo("Opening");
								objSummaryledger.setStrTransType("Opening");
								objSummaryledger.setStrChequeBillNo("");
								objSummaryledger.setStrTransTypeForOrderBy("0");
								objSummaryledger.setStrNarration("");
								objSummaryledger.setStrClientCode(clientCode);
								objSummaryledger.setStrUserCode(userCode);
								objSummaryledger.setStrPropertyCode(propertyCode);
								
								if(amt<0)
								{
									objSummaryledger.setDblDebitAmt(0);
									objSummaryledger.setDblCreditAmt(amt*(-1));
								}else{
									objSummaryledger.setDblDebitAmt(amt);
									objSummaryledger.setDblCreditAmt(0);
								}
															
								objSummaryledger.setDblBalanceAmt(Double.parseDouble(objArr[7].toString()));
								objSummaryledger.setDteBillDate( fromDate);
								objSummaryledger.setDteVochDate( fromDate);
								if (Double.parseDouble(objArr[6].toString()) <= 0) {
									objSummaryledger.setStrBalCrDr("Dr");
								} else {
									objSummaryledger.setStrBalCrDr("Cr");
								}
								objSummaryledger.setStrDebtorName("");
								objGlobalFunctionsService.funAddUpdateLedgerSummary(objSummaryledger);
							}
						}
				
					}
					funProcessWebBookGeneralLedgerSummary(acCode, fromDate, toDate,  clientCode, userCode, propertyCode, req, resp, strCrOrDr, conversionRate);
					
				}catch (Exception e) {
					// TODO Auto-generated catch block
				}
			}else {
						String sql="";
							sql += " select DATE_FORMAT(DATE(a.dteCreatedDate),'%d-%m-%Y') dteVochDate,'Opening','Op','', " 
									+" DATE_FORMAT(DATE(a.dteCreatedDate),'%d-%m-%Y') billDate, "
									+ "IF(a.strCrDr='Dr',a.intOpeningBal,0) dblDebitAmt, IF(a.strCrDr='Cr',a.intOpeningBal,0) dblCreditAmt, (IF(a.strCrDr='Dr',a.intOpeningBal,0) - IF(a.strCrDr='Cr',a.intOpeningBal,0)) dblBalanceAmt  from tblacmaster a where a.strAccountCode='"+acCode+"' "
									+" and a.strClientCode='"+clientCode+"' and a.strPropertyCode='" + propertyCode + "'";
					
						 StringBuilder sbSql=new StringBuilder(sql);
						 List list=new ArrayList();
						try {
							list = objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
						} catch (Exception e) {
							// TODO Auto-generated catch block
						}
//						List list = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
						if (list.size() > 0) {
							funDeleteAndInsertWebBookLedgerTable(clientCode, userCode, propertyCode);
							for (int i = 0; i < list.size(); i++) {
								clsLedgerSummaryModel objSummaryledger = new clsLedgerSummaryModel();
								Object[] objArr = (Object[]) list.get(i);
								objSummaryledger.setStrVoucherNo("Opening");
								objSummaryledger.setStrTransType("Opening");
								objSummaryledger.setStrChequeBillNo("");
								objSummaryledger.setStrTransTypeForOrderBy("0");
								objSummaryledger.setStrNarration("");
								objSummaryledger.setStrClientCode(clientCode);
								objSummaryledger.setStrUserCode(userCode);
								objSummaryledger.setStrPropertyCode(propertyCode);
								objSummaryledger.setDblDebitAmt(Double.parseDouble(objArr[5].toString()));
								objSummaryledger.setDblCreditAmt(Double.parseDouble(objArr[6].toString()));
								;
								objSummaryledger.setDblBalanceAmt(Double.parseDouble(objArr[7].toString()));
								objSummaryledger.setDteBillDate( fromDate);
								objSummaryledger.setDteVochDate( fromDate);
								if (Double.parseDouble(objArr[6].toString()) <= 0) {
									objSummaryledger.setStrBalCrDr("Dr");
								} else {
									objSummaryledger.setStrBalCrDr("Cr");
								}
								objSummaryledger.setStrDebtorName("");
								objGlobalFunctionsService.funAddUpdateLedgerSummary(objSummaryledger);

							}
						} else {
//							clsLedgerSummaryModel objSummaryledger = new clsLedgerSummaryModel();
//							objSummaryledger.setStrVoucherNo("Opening");
//							objSummaryledger.setStrTransType("Op");
//							objSummaryledger.setStrChequeBillNo("");
//							objSummaryledger.setStrTransTypeForOrderBy("0");
//							objSummaryledger.setStrNarration("");
//							objSummaryledger.setStrClientCode(clientCode);
//							objSummaryledger.setStrUserCode(userCode);
//							objSummaryledger.setStrPropertyCode(propertyCode);
//							objSummaryledger.setDblDebitAmt(0.00);
//							objSummaryledger.setDblCreditAmt(0.00);
//							;
//							objSummaryledger.setDblBalanceAmt(0.00);
//							objSummaryledger.setDteBillDate(funGetDate("dd-MM-yyyy", toDate));
//							objSummaryledger.setDteVochDate(funGetDate("dd-MM-yyyy", toDate));
//							objSummaryledger.setStrBalCrDr("Dr");
//							objGlobalFunctionsService.funAddUpdateLedgerSummary(objSummaryledger);
						}
						funProcessWebBookGeneralLedgerSummary(acCode, fromDate, toDate, clientCode, userCode, propertyCode, req, resp, strCrOrDr, conversionRate);
//						funProcessWebBookLedgerSummaryNotFromStartDate(acCode, detorCretditorCode, fromDate, toDate, clientCode, userCode, propertyCode, req, resp, strCrOrDr,conversionRate);
					}
				}
	@SuppressWarnings("rawtypes")
	public int funProcessWebBookLedgerSummary(String acCode, String detorCretditorCode, String fromDate, String toDate, String clientCode, String userCode, String propertyCode, HttpServletRequest req, HttpServletResponse resp, String strCrOrDr,String currency) {
		String sql = "";
		
		String webStockDB=req.getSession().getAttribute("WebStockDB").toString();
	

		sql = " SELECT DATE(a.dteVouchDate),a.strVouchNo,'JV'  ,ifnull(a.strSourceDocNo,'') , DATE(c.dteInvoiceDate) , "
				+ " b.dblDrAmt ,b.dblCrAmt ,b.dblDrAmt - b.dblCrAmt ,'Cr',ifnull(a.strNarration,''),'1','" + userCode + "','" + propertyCode + "','" + clientCode + "' ," 
				+ " if((e.strCurrencyCode is null) or (e.strCurrencyCode ='NA' ) or (e.strCurrencyCode ='' ), "
				+ " (select dblConvToBaseCurr from "+webStockDB+".tblcurrencymaster "
				+ " where strCurrencyCode='"+currency+"') ,a.dblConversion) as conv2 "
				+ " FROM  tbljvdtl b,tbljvdebtordtl c, "
		        + " tbljvhd a left outer join "+webStockDB+".tblcurrencymaster e on a.strCurrency=e.strCurrencyCode and a.strCurrency='"+currency+"' "
				+ " WHERE a.strVouchNo=b.strVouchNo AND a.strVouchNo=c.strVouchNo " 
				+ " AND DATE(a.dteVouchDate) BETWEEN '" + fromDate + "' AND '" + toDate + "' " 
				+ " AND b.strAccountCode='" + acCode + "' and c.strDebtorCode='" + detorCretditorCode + "' "
				+ " AND a.strPropertyCode=b.strPropertyCode AND a.strPropertyCode='" + propertyCode + "' AND a.strClientCode='" + clientCode + "' ";
		
		StringBuilder sbSql=new StringBuilder(sql);
		sbSql=new StringBuilder(sql);
		List listjv=new ArrayList();
		try {
			listjv = objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
//		List listjv = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
		if (listjv.size() > 0) {
			for (int i = 0; i < listjv.size(); i++) {
				clsLedgerSummaryModel objSummaryledger = new clsLedgerSummaryModel();
				Object[] objArr = (Object[]) listjv.get(i);
				double con=Double.parseDouble(objArr[14].toString());
				objSummaryledger.setDteVochDate(objArr[0].toString());
				objSummaryledger.setStrVoucherNo(objArr[1].toString());
				objSummaryledger.setStrTransType(objArr[2].toString());
				objSummaryledger.setStrChequeBillNo(objArr[3].toString());
				objSummaryledger.setDteBillDate(objArr[4].toString());
				objSummaryledger.setDblDebitAmt(Double.parseDouble(objArr[5].toString())/con);
				objSummaryledger.setDblCreditAmt(Double.parseDouble(objArr[6].toString())/con);
				;
				objSummaryledger.setDblBalanceAmt(Double.parseDouble(objArr[7].toString())/con);
				objSummaryledger.setStrBalCrDr(objArr[8].toString());
				objSummaryledger.setStrNarration(objArr[9].toString());
				objSummaryledger.setStrTransTypeForOrderBy(objArr[10].toString());
				objSummaryledger.setStrClientCode(clientCode);
				objSummaryledger.setStrUserCode(userCode);
				objSummaryledger.setStrPropertyCode(propertyCode);
				

				objGlobalFunctionsService.funAddUpdateLedgerSummary(objSummaryledger);
			}
		}
		

		sql = " SELECT DATE(a.dteVouchDate) ,a.strVouchNo,'Payment', a.strVouchNo, DATE(a.dteChequeDate) ,b.dblDrAmt , b.dblCrAmt ,b.dblDrAmt - b.dblCrAmt ,'Dr',ifnull(a.strNarration,''),'2','" + userCode + "','" + propertyCode + "','" + clientCode + "' ," 
				+ " if((e.strCurrencyCode is null) or (e.strCurrencyCode ='NA' ) or (e.strCurrencyCode ='' ), "
				+ " (select dblConvToBaseCurr from "+webStockDB+".tblcurrencymaster "
				+ " where strCurrencyCode='"+currency+"') ,a.dblConversion) as conv2 "
				+ " FROM  tblpaymentdtl b,tblpaymentdebtordtl c ,tblpaymenthd a "
				+ "left outer join "+webStockDB+".tblcurrencymaster e on a.strCurrency=e.strCurrencyCode and a.strCurrency='"+currency+"'"
				
				+ " WHERE a.strVouchNo=b.strVouchNo and a.strVouchNo=c.strVouchNo "
			    + " AND DATE(a.dteVouchDate) BETWEEN '" + fromDate + "' AND '" + toDate + "' " 
				+ " AND b.strAccountCode='" + acCode + "' and c.strDebtorCode='" + detorCretditorCode + "' AND a.strPropertyCode=b.strPropertyCode " 
			    + " AND a.strPropertyCode='" + propertyCode + "' AND a.strClientCode='" + clientCode + "'  ";

		sbSql.length();
		sbSql=new StringBuilder(sql);
		List listPay=new ArrayList();
		try {
			listPay = objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
//		List listPay = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
		if (listPay.size() > 0) {
			for (int i = 0; i < listPay.size(); i++) {
				clsLedgerSummaryModel objSummaryledger = new clsLedgerSummaryModel();
				Object[] objArr = (Object[]) listPay.get(i);
				double con=Double.parseDouble(objArr[14].toString());
				objSummaryledger.setDteVochDate(objArr[0].toString());
				objSummaryledger.setStrVoucherNo(objArr[1].toString());
				objSummaryledger.setStrTransType(objArr[2].toString());
				objSummaryledger.setStrChequeBillNo(objArr[3].toString());
				objSummaryledger.setDteBillDate(objArr[4].toString());
				objSummaryledger.setDblDebitAmt(Double.parseDouble(objArr[5].toString())/con);
				objSummaryledger.setDblCreditAmt(Double.parseDouble(objArr[6].toString())/con);
				;
				objSummaryledger.setDblBalanceAmt(Double.parseDouble(objArr[7].toString())/con);
				objSummaryledger.setStrBalCrDr(objArr[8].toString());
				objSummaryledger.setStrNarration(objArr[9].toString());
				objSummaryledger.setStrTransTypeForOrderBy(objArr[10].toString());
				objSummaryledger.setStrClientCode(clientCode);
				objSummaryledger.setStrUserCode(userCode);
				objSummaryledger.setStrPropertyCode(propertyCode);

				objGlobalFunctionsService.funAddUpdateLedgerSummary(objSummaryledger);
			}
		}

		// receipt
				sql = "  SELECT DATE(a.dteVouchDate) ,a.strVouchNo,'Receipt', a.strVouchNo, " 
				    + " DATE(a.dteChequeDate) ,  b.dblDrAmt , b.dblCrAmt ,b.dblDrAmt - b.dblCrAmt ,b.strCrDr,ifnull(a.strNarration,''),'3','" + userCode + "','" + propertyCode + "','" + clientCode + "', "
					+ " if((e.strCurrencyCode is null) or (e.strCurrencyCode ='NA' ) or (e.strCurrencyCode ='' ), "
					+ " (select dblConvToBaseCurr from "+webStockDB+".tblcurrencymaster "
					+ " where strCurrencyCode='"+currency+"') ,a.dblConversion) as conv2 "
				    + " FROM  tblreceiptdtl b,tblreceiptdebtordtl c ,tblreceipthd a "
				    + " left outer join "+webStockDB+".tblcurrencymaster e on a.strCurrency=e.strCurrencyCode and a.strCurrency='"+currency+"'"
				    + " WHERE a.strVouchNo=b.strVouchNo and a.strVouchNo=c.strVouchNo "
				    + " AND DATE(a.dteVouchDate) BETWEEN '" + fromDate + "' AND '" + toDate + "' " + " and  b.strAccountCode='" + acCode + "'  and c.strDebtorCode='" + detorCretditorCode + "'  "
					+ " AND a.strPropertyCode=b.strPropertyCode " 
				    + " AND a.strPropertyCode='"+ propertyCode + "' AND a.strClientCode='" + clientCode + "'  ";

		sbSql.length();
		sbSql=new StringBuilder(sql);
		List listRecept=new ArrayList();
		try {
			listRecept = objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
//		List listRecept = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
		if (listRecept.size() > 0) {
			for (int i = 0; i < listRecept.size(); i++) {
				clsLedgerSummaryModel objSummaryledger = new clsLedgerSummaryModel();
				Object[] objArr = (Object[]) listRecept.get(i);
				double con=Double.parseDouble(objArr[14].toString());
				objSummaryledger.setDteVochDate(objArr[0].toString());
				objSummaryledger.setStrVoucherNo(objArr[1].toString());
				objSummaryledger.setStrTransType(objArr[2].toString());
				objSummaryledger.setStrChequeBillNo(objArr[3].toString());
				objSummaryledger.setDteBillDate(objArr[4].toString());
				objSummaryledger.setDblDebitAmt(Double.parseDouble(objArr[5].toString())/con);
				objSummaryledger.setDblCreditAmt(Double.parseDouble(objArr[6].toString())/con);
				;
				objSummaryledger.setDblBalanceAmt(Double.parseDouble(objArr[7].toString())/con);
				objSummaryledger.setStrBalCrDr(objArr[8].toString());
				objSummaryledger.setStrNarration(objArr[9].toString());
				objSummaryledger.setStrTransTypeForOrderBy(objArr[10].toString());
				objSummaryledger.setStrClientCode(clientCode);
				objSummaryledger.setStrUserCode(userCode);
				objSummaryledger.setStrPropertyCode(propertyCode);

				objGlobalFunctionsService.funAddUpdateLedgerSummary(objSummaryledger);
			}
		}
		
		return 1;
	}



	
	public void funInvokeWebBookLedger(String acCode, String detorCretditorCode, String startDate, String propertyCode, String fromDate, String toDate, String clientCode, String userCode, HttpServletRequest req, HttpServletResponse resp, String strCrOrDr,String currency) {
		funDeleteAndInsertWebBookLedgerTable(clientCode, userCode, propertyCode);

		String webStockDB=req.getSession().getAttribute("WebStockDB").toString();
		if (!startDate.equals(fromDate)) {
			String tempFromDate = fromDate.split("-")[2] + "-" + fromDate.split("-")[1] + "-" + fromDate.split("-")[0];
			SimpleDateFormat obj = new SimpleDateFormat("dd-MM-yyyy");
			Date dt1;
			try {
				dt1 = obj.parse(tempFromDate);
				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime(dt1);
				cal.add(Calendar.DATE, -1);
				String newToDate = (cal.getTime().getYear() + 1900) + "-" + (cal.getTime().getMonth() + 1) + "-" + (cal.getTime().getDate());

				funProcessWebBookLedgerSummary(acCode, detorCretditorCode, startDate, newToDate, clientCode, userCode, propertyCode, req, resp, strCrOrDr,currency);

				// both opening creditor or debtor opening balance and from date
				// not eaqual to date opening table
				String sql = " SELECT ifnull(dteVochDate,''),'Op','Opening','',ifnull(billDate,''), ifnull(SUM(dblDebitAmt),0.00), ifnull(SUM(dblCreditAmt),0.0), ifnull(SUM(dblBalanceAmt),0.0),sum(conv2) from  "

				+ "( SELECT dteVochDate dteVochDate,'Op','Opening','',dteBillDate billDate, SUM(dblDebitAmt) dblDebitAmt, " + " SUM(dblCreditAmt) dblCreditAmt, SUM(dblBalanceAmt) dblBalanceAmt ,0 as conv2 " + " FROM tblledgersummary " + " WHERE strUserCode='" + userCode + "' AND strPropertyCode='" + propertyCode + "' AND strClientCode='" + clientCode + "' "
						+ " GROUP BY strUserCode,strPropertyCode,strClientCode "

						+ " UNION  All  ";
				if (strCrOrDr.equals("Debtor")) {
					sql += "SELECT DATE_FORMAT(DATE(a.dteDateCreated),'%d-%m-%Y') billDate,'Op','Opening','', " 
				       + " DATE_FORMAT(DATE(a.dteDateCreated),'%d-%m-%Y'), IF(b.strCrDr='Dr',b.dblOpeningbal,0) dblDebitAmt, "
					   + " IF(b.strCrDr='Cr',b.dblOpeningbal,0) dblCreditAmt, " + " (IF(b.strCrDr='Dr',b.dblOpeningbal,0) - IF(b.strCrDr='Cr',b.dblOpeningbal,0)) dblBalanceAmt, "
					   + " if((e.strCurrencyCode is null) or (e.strCurrencyCode ='NA' ) or (e.strCurrencyCode ='' ), "
					   + " (select dblConvToBaseCurr from "+webStockDB+".tblcurrencymaster "
					   + " where strCurrencyCode='"+currency+"') ,a.dblConversion) as conv2 "
					   + " FROM tblsundarydebtoropeningbalance b,tblsundarydebtormaster a "
					   + "  left outer join "+webStockDB+".tblcurrencymaster e on a.strCurrencyType=e.strCurrencyCode and a.strCurrencyType='"+currency+"' " 
					   + " WHERE a.strDebtorCode=b.strDebtorCode AND b.strAccountCode='" + acCode + "' " 
					   + " AND b.strDebtorCode='" + detorCretditorCode + "' AND a.strPropertyCode='" + propertyCode + "' " 
					   + " AND a.strClientCode=b.strClientCode AND a.strClientCode='" + clientCode + "' ) opening ";
				} 
				else if(strCrOrDr.equals("Creditor")) {
					sql += "SELECT DATE_FORMAT(DATE(a.dteDateCreated),'%d-%m-%Y') billDate,'Op','Opening','', "
				        + " DATE_FORMAT(DATE(a.dteDateCreated),'%d-%m-%Y'), IF(b.strCrDr='Dr',b.dblOpeningbal,0) dblDebitAmt, " 
						+ " IF(b.strCrDr='Cr',b.dblOpeningbal,0) dblCreditAmt, " + " (IF(b.strCrDr='Dr',b.dblOpeningbal,0) - IF(b.strCrDr='Cr',b.dblOpeningbal,0)) dblBalanceAmt, "
						+ " if((e.strCurrencyCode is null) or (e.strCurrencyCode ='NA' ) or (e.strCurrencyCode ='' ), "
						+ " (select dblConvToBaseCurr from "+webStockDB+".tblcurrencymaster "
						+ " where strCurrencyCode='"+currency+"') ,a.dblConversion) as conv2 "
						+ " FROM tblsundarycreditoropeningbalance b,tblsundarycreditormaster a " 
						+ "  left outer join "+webStockDB+".tblcurrencymaster e on a.strCurrencyType=e.strCurrencyCode and a.strCurrencyType='"+currency+"' " 
						+ " WHERE a.strCreditorCode=b.strCreditorCode AND b.strAccountCode='" + acCode + "' " 
						+ " AND b.strCreditorCode='" + detorCretditorCode + "' AND a.strPropertyCode='" + propertyCode + "' " 
						+ " AND a.strClientCode=b.strClientCode AND a.strClientCode='" + clientCode + "' ) opening ";
				}else if(strCrOrDr.equals("Employee")){
					sql += "SELECT DATE_FORMAT(DATE(a.dteCreatedDate),'%d-%m-%Y') billDate,'Op','Opening','', "
					        + " DATE_FORMAT(DATE(a.dteCreatedDate),'%d-%m-%Y'), IF(b.strCrDr='Dr',b.dblOpeningbal,0) dblDebitAmt, " 
							+ " IF(b.strCrDr='Cr',b.dblOpeningbal,0) dblCreditAmt, " + " (IF(b.strCrDr='Dr',b.dblOpeningbal,0) - IF(b.strCrDr='Cr',b.dblOpeningbal,0)) dblBalanceAmt, "
							+ " if((e.strCurrencyCode is null) or (e.strCurrencyCode ='NA' ) or (e.strCurrencyCode ='' ), "
							+ " (select dblConvToBaseCurr from "+webStockDB+".tblcurrencymaster "
							+ " where strCurrencyCode='"+currency+"') ,a.dblConversion) as conv2 "
							+ " FROM tblemployeeopeningbalance b,tblemployeemaster a " 
							+ " left outer join "+webStockDB+".tblcurrencymaster e on a.strCurrencyType=e.strCurrencyCode and a.strCurrencyType='"+currency+"' "
							+ " WHERE a.strEmployeeCode=b.strEmployeeCode AND b.strAccountCode='" + acCode + "' " 
							+ " AND b.strEmployeeCode='" + detorCretditorCode + "' AND a.strPropertyCode='" + propertyCode + "' " 
							+ " AND a.strClientCode=b.strClientCode AND a.strClientCode='" + clientCode + "' ) opening ";
					
				}
				
				StringBuilder sbSql=new StringBuilder(sql);
				List list=new ArrayList();
				try {
					list = objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
				} catch (Exception e) {
					// TODO Auto-generated catch block
				}
//				List list = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
				if (list.size() > 0) {
					funDeleteAndInsertWebBookLedgerTable(clientCode, userCode, propertyCode);
					for (int i = 0; i < list.size(); i++) {
						
						clsLedgerSummaryModel objSummaryledger = new clsLedgerSummaryModel();
						Object[] objArr = (Object[]) list.get(i);
						double amt=Double.parseDouble(objArr[5].toString())-Double.parseDouble(objArr[6].toString());
						double curr=Double.parseDouble(objArr[8].toString());
						if(curr==0)
						{
							curr=1;
						}
						if(Double.parseDouble(objArr[5].toString())!=0 || Double.parseDouble(objArr[6].toString())!=0)
						{
							
						objSummaryledger.setStrVoucherNo("Opening");
						objSummaryledger.setStrTransType("Opening");
						objSummaryledger.setStrChequeBillNo("");
						objSummaryledger.setStrTransTypeForOrderBy("0");
						objSummaryledger.setStrNarration("");
						objSummaryledger.setStrClientCode(clientCode);
						objSummaryledger.setStrUserCode(userCode);
						objSummaryledger.setStrPropertyCode(propertyCode);
						
						if(amt<0)
						{
							objSummaryledger.setDblDebitAmt(0);
							objSummaryledger.setDblCreditAmt((amt*(-1))/curr);
						}else{
							objSummaryledger.setDblDebitAmt(amt/curr);
							objSummaryledger.setDblCreditAmt(0);
						}
					
						
						objSummaryledger.setDblBalanceAmt(Double.parseDouble(objArr[7].toString())/curr);
						objSummaryledger.setDteBillDate( fromDate);
						objSummaryledger.setDteVochDate( fromDate);
						if (Double.parseDouble(objArr[6].toString()) <= 0) {
							objSummaryledger.setStrBalCrDr("Dr");
						} else {
							objSummaryledger.setStrBalCrDr("Cr");
						}
						objGlobalFunctionsService.funAddUpdateLedgerSummary(objSummaryledger);
						}
					}
				} else {
//					clsLedgerSummaryModel objSummaryledger = new clsLedgerSummaryModel();
//					objSummaryledger.setStrVoucherNo("Opening");
//					objSummaryledger.setStrTransType("Op");
//					objSummaryledger.setStrChequeBillNo("");
//					objSummaryledger.setStrTransTypeForOrderBy("0");
//					objSummaryledger.setStrNarration("");
//					objSummaryledger.setStrClientCode(clientCode);
//					objSummaryledger.setStrUserCode(userCode);
//					objSummaryledger.setStrPropertyCode(propertyCode);
//					objSummaryledger.setDblDebitAmt(0.00);
//					objSummaryledger.setDblCreditAmt(0.00);
//					;
//					objSummaryledger.setDblBalanceAmt(0.00);
//					objSummaryledger.setDteBillDate(funGetDate("dd-MM-yyyy", toDate));
//					objSummaryledger.setDteVochDate(funGetDate("dd-MM-yyyy", toDate));
//					objSummaryledger.setStrBalCrDr("Dr");
//					objGlobalFunctionsService.funAddUpdateLedgerSummary(objSummaryledger);
				}

				funProcessWebBookLedgerSummary(acCode, detorCretditorCode, fromDate, toDate, clientCode, userCode, propertyCode, req, resp, strCrOrDr,currency);

			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			// opening balance from for Sundry debtor or creditor opening table

			String sql = "";
			if (strCrOrDr.equals("Debtor")) {
				sql += "SELECT DATE_FORMAT(DATE(a.dteDateCreated),'%d-%m-%Y') billDate,'Op','Opening','', " + " DATE_FORMAT(DATE(a.dteDateCreated),'%d-%m-%Y'), IF(b.strCrDr='Dr',b.dblOpeningbal,0) dblDebitAmt, " + " IF(b.strCrDr='Cr',b.dblOpeningbal,0) dblCreditAmt, " + " (IF(b.strCrDr='Dr',b.dblOpeningbal,0) - IF(b.strCrDr='Cr',b.dblOpeningbal,0)) dblBalanceAmt,"
					+ " if((e.strCurrencyCode is null) or (e.strCurrencyCode ='NA' ) or (e.strCurrencyCode ='' ), "
					+ " (select dblConvToBaseCurr from "+webStockDB+".tblcurrencymaster "
					+ " where strCurrencyCode='"+currency+"') ,a.dblConversion) as conv2 "
					+ " FROM tblsundarydebtormaster a"
					+ " left outer join "+webStockDB+".tblcurrencymaster e on a.strCurrencyType=e.strCurrencyCode and a.strCurrencyType='"+currency+"' "
					+ ",tblsundarydebtoropeningbalance b " + " WHERE a.strDebtorCode=b.strDebtorCode AND b.strAccountCode='" + acCode + "' " + " AND b.strDebtorCode='" + detorCretditorCode + "' AND a.strPropertyCode='" + propertyCode + "' " + " AND a.strClientCode=b.strClientCode AND a.strClientCode='" + clientCode + "'  ";
			}else if(strCrOrDr.equals("Creditor")) {
				sql += "SELECT DATE_FORMAT(DATE(a.dteDateCreated),'%d-%m-%Y') billDate,'Op','Opening','', " + " DATE_FORMAT(DATE(a.dteDateCreated),'%d-%m-%Y'), IF(b.strCrDr='Dr',b.dblOpeningbal,0) dblDebitAmt, " + " IF(b.strCrDr='Cr',b.dblOpeningbal,0) dblCreditAmt, " + " (IF(b.strCrDr='Dr',b.dblOpeningbal,0) - IF(b.strCrDr='Cr',b.dblOpeningbal,0)) dblBalanceAmt, "
					+ " if((e.strCurrencyCode is null) or (e.strCurrencyCode ='NA' ) or (e.strCurrencyCode ='' ), "
					+ " (select dblConvToBaseCurr from "+webStockDB+".tblcurrencymaster "
					+ " where strCurrencyCode='"+currency+"') ,a.dblConversion) as conv2 "
					+ " FROM tblsundarycreditoropeningbalance b ,tblsundarycreditormaster a " 
					+ " left outer join "+webStockDB+".tblcurrencymaster e on a.strCurrencyType=e.strCurrencyCode and a.strCurrencyType='"+currency+"' "
					+ " WHERE a.strCreditorCode=b.strCreditorCode AND b.strAccountCode='" + acCode + "' " + " AND b.strCreditorCode='" + detorCretditorCode + "' AND a.strPropertyCode='" + propertyCode + "' " + " AND a.strClientCode=b.strClientCode AND a.strClientCode='" + clientCode + "'  ";
			}
			else if(strCrOrDr.equals("Employee")){
				sql += "SELECT DATE_FORMAT(DATE(a.dteCreatedDate),'%d-%m-%Y') billDate,'Op','Opening','', " 
					+ " DATE_FORMAT(DATE(a.dteCreatedDate),'%d-%m-%Y'), IF(b.strCrDr='Dr',b.dblOpeningbal,0) dblDebitAmt, " 
					+ " IF(b.strCrDr='Cr',b.dblOpeningbal,0) dblCreditAmt, " 
					+ " (IF(b.strCrDr='Dr',b.dblOpeningbal,0) - IF(b.strCrDr='Cr',b.dblOpeningbal,0)) dblBalanceAmt, "
					+ " if((e.strCurrencyCode is null) or (e.strCurrencyCode ='NA' ) or (e.strCurrencyCode ='' ), "
					+ " (select dblConvToBaseCurr from "+webStockDB+".tblcurrencymaster "
					+ " where strCurrencyCode='"+currency+"') ,a.dblConversion) as conv2 "
					+ " FROM tblemployeemaster a"
					+ " left outer join "+webStockDB+".tblcurrencymaster e on a.strCurrencyType=e.strCurrencyCode and a.strCurrencyType='"+currency+"' "
					+ ",tblemployeeopeningbalance b " 
					+ " WHERE a.strEmployeeCode=b.strEmployeeCode AND b.strAccountCode='" + acCode + "' "
					+ " AND b.strEmployeeCode='" + detorCretditorCode + "' AND a.strPropertyCode='" + propertyCode + "' " 
					+ " AND a.strClientCode=b.strClientCode AND a.strClientCode='" + clientCode + "'  ";
				
			}
			StringBuilder sbSql=new StringBuilder(sql);
			List list=new ArrayList();
			try {
				list = objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
			} catch (Exception e) {
				// TODO Auto-generated catch block
			}
//			List list = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
			if (list.size() > 0) {
				funDeleteAndInsertWebBookLedgerTable(clientCode, userCode, propertyCode);
				for (int i = 0; i < list.size(); i++) {
					clsLedgerSummaryModel objSummaryledger = new clsLedgerSummaryModel();
					Object[] objArr = (Object[]) list.get(i);
					double curr=Double.parseDouble(objArr[8].toString());
					objSummaryledger.setStrVoucherNo("Opening");
					objSummaryledger.setStrTransType("Opening");
					objSummaryledger.setStrChequeBillNo("");
					objSummaryledger.setStrTransTypeForOrderBy("0");
					objSummaryledger.setStrNarration("");
					objSummaryledger.setStrClientCode(clientCode);
					objSummaryledger.setStrUserCode(userCode);
					objSummaryledger.setStrPropertyCode(propertyCode);
					objSummaryledger.setDblDebitAmt(Double.parseDouble(objArr[5].toString())/curr);
					objSummaryledger.setDblCreditAmt(Double.parseDouble(objArr[6].toString())/curr);
					objSummaryledger.setDblBalanceAmt(Double.parseDouble(objArr[7].toString())/curr);
					objSummaryledger.setDteBillDate( fromDate);
					objSummaryledger.setDteVochDate( fromDate);
					if (Double.parseDouble(objArr[6].toString()) <= 0) {
						objSummaryledger.setStrBalCrDr("Dr");
					} else {
						objSummaryledger.setStrBalCrDr("Cr");
					}
					objGlobalFunctionsService.funAddUpdateLedgerSummary(objSummaryledger);

				}
			} else {
//				clsLedgerSummaryModel objSummaryledger = new clsLedgerSummaryModel();
//				objSummaryledger.setStrVoucherNo("Opening");
//				objSummaryledger.setStrTransType("Op");
//				objSummaryledger.setStrChequeBillNo("");
//				objSummaryledger.setStrTransTypeForOrderBy("0");
//				objSummaryledger.setStrNarration("");
//				objSummaryledger.setStrClientCode(clientCode);
//				objSummaryledger.setStrUserCode(userCode);
//				objSummaryledger.setStrPropertyCode(propertyCode);
//				objSummaryledger.setDblDebitAmt(0.00);
//				objSummaryledger.setDblCreditAmt(0.00);
//				;
//				objSummaryledger.setDblBalanceAmt(0.00);
//				objSummaryledger.setDteBillDate(funGetDate("dd-MM-yyyy", toDate));
//				objSummaryledger.setDteVochDate(funGetDate("dd-MM-yyyy", toDate));
//				objSummaryledger.setStrBalCrDr("Dr");
//				objGlobalFunctionsService.funAddUpdateLedgerSummary(objSummaryledger);
			}

			funProcessWebBookLedgerSummaryNotFromStartDate(acCode, detorCretditorCode, fromDate, toDate, clientCode, userCode, propertyCode, req, resp, strCrOrDr,currency);
		}
	}
	
	



	@SuppressWarnings("rawtypes")
public int funProcessWebBookGeneralLedgerSummary(String acCode, String fromDate, String toDate, String clientCode, String userCode, String propertyCode
	, HttpServletRequest req, HttpServletResponse resp, String strCrOrDr, double conversionRate) {
String sql = "";
StringBuilder sbSql=new StringBuilder(sql);
sbSql.setLength(0);

/*sbSql.append(" SELECT DATE(a.dteVouchDate) ,ifnull(c.strDebtorName,''),'JV'  ,ifnull(a.strSourceDocNo,'') , ifnull(DATE(a.dteVouchDate),DATE(a.dteVouchDate)) , " 
	+ " b.dblDrAmt ,b.dblCrAmt ,b.dblDrAmt - b.dblCrAmt ,'Cr','','1','" + userCode + "','" + propertyCode + "','" + clientCode + "',c.strDebtorName " 
	+ " FROM tbljvdtl b,tbljvhd a left outer join tbljvdebtordtl c on a.strVouchNo=c.strVouchNo "
	+ " WHERE a.strVouchNo=b.strVouchNo  " + " AND DATE(a.dteVouchDate) BETWEEN '" + fromDate + "' AND '" + toDate + "' " 
	+ " AND b.strAccountCode='" + acCode + "'  " + " AND a.strPropertyCode=b.strPropertyCode " + " AND a.strPropertyCode='" + propertyCode + "' AND a.strClientCode='" + clientCode + "' ");
*/

sbSql.append(" SELECT DATE(a.dteVouchDate) ,ifnull(c.strDebtorName,''),'JV'  ,ifnull(a.strSourceDocNo,'') , ifnull(DATE(a.dteVouchDate),DATE(a.dteVouchDate)) , " 
		+ " b.dblDrAmt ,b.dblCrAmt ,b.dblDrAmt - b.dblCrAmt ,b.strCrDr,'','1','" + userCode + "','" + propertyCode + "','" + clientCode + "',c.strDebtorName,a.strVouchNo "  
			+" FROM tbljvhd a ,tbljvdtl b left outer join tbljvdebtordtl c on b.strVouchNo=c.strVouchNo "  
			+" and b.strAccountCode = c.strAccountCode and b.strCrDr = c.strCrDr "
			+" WHERE a.strVouchNo=b.strVouchNo  AND DATE(a.dteVouchDate) BETWEEN '" + fromDate + "' AND '" + toDate + "' " 
			+" AND b.strAccountCode='" + acCode + "'   AND a.strPropertyCode=b.strPropertyCode  AND a.strPropertyCode='" + propertyCode + "' AND a.strClientCode='" + clientCode + "' "); 
	List listjv=new ArrayList();
	try {
		listjv = objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
//List listjv = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
if (listjv.size() > 0) {
	for (int i = 0; i < listjv.size(); i++) {

		clsLedgerSummaryModel objSummaryledger = new clsLedgerSummaryModel();
		Object[] objArr = (Object[]) listjv.get(i);
		objSummaryledger.setDteVochDate(objArr[0].toString());
		objSummaryledger.setStrVoucherNo(objArr[15].toString());
		objSummaryledger.setStrDebtorName(objArr[1].toString());
		objSummaryledger.setStrTransType(objArr[2].toString());
		objSummaryledger.setStrChequeBillNo(objArr[3].toString());
		objSummaryledger.setDteBillDate(objArr[4].toString());
		objSummaryledger.setDblDebitAmt(Double.parseDouble(objArr[5].toString()));
		objSummaryledger.setDblCreditAmt(Double.parseDouble(objArr[6].toString()));
		objSummaryledger.setDblBalanceAmt(Double.parseDouble(objArr[7].toString()));
		objSummaryledger.setStrBalCrDr(objArr[8].toString());
		objSummaryledger.setStrNarration(objArr[9].toString());
		objSummaryledger.setStrTransTypeForOrderBy(objArr[10].toString());
		objSummaryledger.setStrClientCode(clientCode);
		objSummaryledger.setStrUserCode(userCode);
		objSummaryledger.setStrPropertyCode(propertyCode);

		objGlobalFunctionsService.funAddUpdateLedgerSummary(objSummaryledger);
	}
}
 sbSql.setLength(0);
sbSql.append( " SELECT DATE(a.dteVouchDate) ,ifnull(c.strDebtorName,''),'Payment', a.strVouchNo,  DATE(a.dteChequeDate) ,b.dblDrAmt , b.dblCrAmt ,b.dblDrAmt - b.dblCrAmt ,'Dr','','2','" + userCode + "','" + propertyCode + "','" + clientCode + "' " 
		+ " FROM tblpaymentdtl b,tblpaymenthd a left outer join tblpaymentdebtordtl c on a.strVouchNo=c.strVouchNo "
		+ " WHERE a.strVouchNo=b.strVouchNo  "
		+ " AND DATE(a.dteVouchDate) BETWEEN '" + fromDate + "' AND '" + toDate + "' " + " AND b.strAccountCode='" + acCode + "'  AND a.strPropertyCode=b.strPropertyCode " + " AND a.strPropertyCode='" + propertyCode + "' AND a.strClientCode='" + clientCode + "'  ");


	List listPay=new ArrayList();
	try {
		listPay = objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
//List listPay = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
if (listPay.size() > 0) {
	funFillLedgerSummaryModel(listPay,clientCode,userCode,propertyCode);
}

// receipt
 sbSql.setLength(0);
	sbSql.append( "  SELECT DATE(a.dteVouchDate) ,c.strDebtorName,'Recepit', a.strVouchNo, " + "DATE(a.dteChequeDate), "
		+ " b.dblDrAmt , b.dblCrAmt ,b.dblDrAmt - b.dblCrAmt ,b.strCrDr,'','3','" + userCode + "','" + propertyCode + "','" + clientCode + "' "
		+ " FROM tblreceiptdtl b,tblreceipthd a left outer join tblreceiptdebtordtl c on c.strVouchNo=a.strVouchNo"
		+ "  WHERE a.strVouchNo=b.strVouchNo  " + " AND DATE(a.dteVouchDate) BETWEEN '" + fromDate + "' AND '" + toDate + "' "
		+ " and  b.strAccountCode='" + acCode + "'  " 
		+ " AND a.strPropertyCode=b.strPropertyCode " 
		+ " AND a.strPropertyCode='" 
		+ propertyCode + "' AND a.strClientCode='" + clientCode
		+ "'  ");


	List listRecept=new ArrayList();
	try {
		listRecept = objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
//List listRecept = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
if (listRecept.size() > 0) {
	
	funFillLedgerSummaryModel(listRecept,clientCode,userCode,propertyCode);
}

return 1;

}
	
	
	private void funFillLedgerSummaryModel(List list, String clientCode,String userCode, String propertyCode)
	{
		for (int i = 0; i < list.size(); i++) {

			clsLedgerSummaryModel objSummaryledger = new clsLedgerSummaryModel();
			Object[] objArr = (Object[]) list.get(i);
			objSummaryledger.setDteVochDate(objArr[0].toString());
			objSummaryledger.setStrVoucherNo(objArr[3].toString());
			objSummaryledger.setStrDebtorName(objArr[1].toString());
			objSummaryledger.setStrTransType(objArr[2].toString());
			objSummaryledger.setStrChequeBillNo(objArr[3].toString());
			objSummaryledger.setDteBillDate(objArr[4].toString());
			objSummaryledger.setDblDebitAmt(Double.parseDouble(objArr[5].toString()));
			objSummaryledger.setDblCreditAmt(Double.parseDouble(objArr[6].toString()));
			objSummaryledger.setDblBalanceAmt(Double.parseDouble(objArr[7].toString()));
			objSummaryledger.setStrBalCrDr(objArr[8].toString());
			objSummaryledger.setStrNarration(objArr[9].toString());
			objSummaryledger.setStrTransTypeForOrderBy(objArr[10].toString());
			objSummaryledger.setStrClientCode(clientCode);
			objSummaryledger.setStrUserCode(userCode);
			objSummaryledger.setStrPropertyCode(propertyCode);
	
			objGlobalFunctionsService.funAddUpdateLedgerSummary(objSummaryledger);
		}
	}

	
	

	@SuppressWarnings("rawtypes")
	public int funProcessWebBookLedgerSummaryNotFromStartDate(String acCode, String detorCretditorCode, String fromDate, String toDate, String clientCode, String userCode, String propertyCode, HttpServletRequest req, HttpServletResponse resp, String strCrOrDr,String currency){
		String sql = "";
		String webStockDB=req.getSession().getAttribute("WebStockDB").toString();
		sql = " SELECT DATE(a.dteVouchDate) ,a.strVouchNo,'JV'  ,ifnull(a.strSourceDocNo,'')  , ifnull(DATE(c.dteInvoiceDate),DATE(a.dteVouchDate)) , " 
			+ " b.dblDrAmt ,b.dblCrAmt ,b.dblDrAmt - b.dblCrAmt ,'Cr',ifnull(a.strNarration,''),'1','" + userCode + "','" + propertyCode + "','" + clientCode + "', "
			+ " if((e.strCurrencyCode is null) or (e.strCurrencyCode ='NA' ) or (e.strCurrencyCode ='' ), "
			+ " (select dblConvToBaseCurr from "+webStockDB+".tblcurrencymaster "
			+ " where strCurrencyCode='"+currency+"') ,a.dblConversion) as conv2 "
			+ " FROM  tbljvdtl b,tbljvdebtordtl c, "
			+"  tbljvhd a left outer join "+webStockDB+".tblcurrencymaster e on a.strCurrency=e.strCurrencyCode and a.strCurrency='"+currency+"' "
			+ " WHERE a.strVouchNo=b.strVouchNo AND a.strVouchNo=c.strVouchNo " + " AND DATE(a.dteVouchDate) BETWEEN '" + fromDate + "' AND '" + toDate + "' " + " AND b.strAccountCode='" + acCode + "'  " + " AND a.strPropertyCode=b.strPropertyCode " + " AND c.strDebtorCode='" + detorCretditorCode + "' AND a.strPropertyCode='" + propertyCode + "' AND a.strClientCode='" + clientCode + "' ";
//		List listjv = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
		StringBuilder sbSql=new StringBuilder(sql);
		List listjv=new ArrayList();
		try {
			listjv = objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		if (listjv.size() > 0) {
			for (int i = 0; i < listjv.size(); i++) {
				clsLedgerSummaryModel objSummaryledger = new clsLedgerSummaryModel();
				Object[] objArr = (Object[]) listjv.get(i);
				double con=Double.parseDouble(objArr[14].toString());
				objSummaryledger.setDteVochDate(objArr[0].toString());
				objSummaryledger.setStrVoucherNo(objArr[1].toString());
				objSummaryledger.setStrTransType(objArr[2].toString());
				objSummaryledger.setStrChequeBillNo(objArr[3].toString());
				objSummaryledger.setDteBillDate(objArr[4].toString());
				objSummaryledger.setDblDebitAmt(Double.parseDouble(objArr[5].toString())/con);
				objSummaryledger.setDblCreditAmt(Double.parseDouble(objArr[6].toString())/con);
				
				objSummaryledger.setDblBalanceAmt(Double.parseDouble(objArr[7].toString())/con);
				objSummaryledger.setStrBalCrDr(objArr[8].toString());
				objSummaryledger.setStrNarration(objArr[9].toString());
				objSummaryledger.setStrTransTypeForOrderBy(objArr[10].toString());
				objSummaryledger.setStrClientCode(clientCode);
				objSummaryledger.setStrUserCode(userCode);
				objSummaryledger.setStrPropertyCode(propertyCode);

				objGlobalFunctionsService.funAddUpdateLedgerSummary(objSummaryledger);
			}
		}


		sql = " SELECT DATE(a.dteVouchDate) ,a.strVouchNo,'Payment', a.strVouchNo,  DATE(a.dteChequeDate) ,b.dblDrAmt , b.dblCrAmt ,b.dblDrAmt - b.dblCrAmt ,'Dr',ifnull(a.strNarration,''),'2','" + userCode + "','" + propertyCode + "','" + clientCode + "' ," 
				+ " if((e.strCurrencyCode is null) or (e.strCurrencyCode ='NA' ) or (e.strCurrencyCode ='' ), "
				+ " (select dblConvToBaseCurr from "+webStockDB+".tblcurrencymaster "
				+ " where strCurrencyCode='"+currency+"') ,a.dblConversion) as conv2 "
				+ " FROM  tblpaymentdtl b,tblpaymentdebtordtl c, "
				+" tblpaymenthd a left outer join "+webStockDB+".tblcurrencymaster e on a.strCurrency=e.strCurrencyCode and a.strCurrency='"+currency+"' "
				+ " WHERE a.strVouchNo=b.strVouchNo and a.strVouchNo=c.strVouchNo " 
				+ " AND DATE(a.dteVouchDate) BETWEEN '" + fromDate + "' AND '" + toDate + "' " 
				+ " AND b.strAccountCode='" + acCode + "'  AND a.strPropertyCode=b.strPropertyCode " 
				+ " AND c.strDebtorCode='" + detorCretditorCode + "'  " 
				+ " AND a.strPropertyCode='" + propertyCode + "' AND a.strClientCode='" + clientCode + "'  ";
		 sbSql.setLength(0);
		 sbSql=new StringBuilder(sql);
		List listPay=new ArrayList();
		try {
			listPay = objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		List listPay = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
		if (listPay.size() > 0) {
			for (int i = 0; i < listPay.size(); i++) {
				clsLedgerSummaryModel objSummaryledger = new clsLedgerSummaryModel();
				Object[] objArr = (Object[]) listPay.get(i);
				double con=Double.parseDouble(objArr[14].toString());
				objSummaryledger.setDteVochDate(objArr[0].toString());
				objSummaryledger.setStrVoucherNo(objArr[1].toString());
				objSummaryledger.setStrTransType(objArr[2].toString());
				objSummaryledger.setStrChequeBillNo(objArr[3].toString());
				objSummaryledger.setDteBillDate(objArr[4].toString());
				objSummaryledger.setDblDebitAmt(Double.parseDouble(objArr[5].toString())/con);
				objSummaryledger.setDblCreditAmt(Double.parseDouble(objArr[6].toString())/con);
				
				objSummaryledger.setDblBalanceAmt(Double.parseDouble(objArr[7].toString())/con);
				objSummaryledger.setStrBalCrDr(objArr[8].toString());
				objSummaryledger.setStrNarration(objArr[9].toString());
				objSummaryledger.setStrTransTypeForOrderBy(objArr[10].toString());
				objSummaryledger.setStrClientCode(clientCode);
				objSummaryledger.setStrUserCode(userCode);
				objSummaryledger.setStrPropertyCode(propertyCode);

				objGlobalFunctionsService.funAddUpdateLedgerSummary(objSummaryledger);
			}
		}

		// receipt
				sql = "  SELECT DATE(a.dteVouchDate) ,a.strVouchNo,'Receipt', a.strVouchNo, " 
						+ "DATE(a.dteChequeDate) ,  b.dblDrAmt , b.dblCrAmt ,b.dblDrAmt - b.dblCrAmt ,b.strCrDr,ifnull(a.strNarration,''),'3','" + userCode + "','" + propertyCode + "','" + clientCode + "', "
						+ " if((e.strCurrencyCode is null) or (e.strCurrencyCode ='NA' ) or (e.strCurrencyCode ='' ), "
						+ " (select dblConvToBaseCurr from "+webStockDB+".tblcurrencymaster "
						+ " where strCurrencyCode='"+currency+"') ,a.dblConversion) as conv2 "
						+ " FROM tblreceiptdtl b,tblreceiptdebtordtl c,  "
						+"  tblreceipthd a left outer join "+webStockDB+".tblcurrencymaster e on a.strCurrency=e.strCurrencyCode and a.strCurrency='"+currency+"' "
						+ " WHERE a.strVouchNo=b.strVouchNo and a.strVouchNo=c.strVouchNo "
						+ " AND DATE(a.dteVouchDate) BETWEEN '" + fromDate + "' AND '" + toDate + "' " 
						+ " and  b.strAccountCode='" + acCode + "'  " + " AND a.strPropertyCode=b.strPropertyCode " 
						+ " AND c.strDebtorCode='" + detorCretditorCode + "'  " + " AND a.strPropertyCode='"
						+ propertyCode + "' AND a.strClientCode='" + clientCode + "'  ";
		 sbSql.setLength(0);
		 sbSql=new StringBuilder(sql);
			List listRecept=new ArrayList();
			try {
				listRecept = objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//		List listRecept = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
		if (listRecept.size() > 0) {
			for (int i = 0; i < listRecept.size(); i++) {
				clsLedgerSummaryModel objSummaryledger = new clsLedgerSummaryModel();
				Object[] objArr = (Object[]) listRecept.get(i);
				double con=Double.parseDouble(objArr[14].toString());
				objSummaryledger.setDteVochDate(objArr[0].toString());
				objSummaryledger.setStrVoucherNo(objArr[1].toString());
				objSummaryledger.setStrTransType(objArr[2].toString());
				objSummaryledger.setStrChequeBillNo(objArr[3].toString());
				objSummaryledger.setDteBillDate(objArr[4].toString());
				objSummaryledger.setDblDebitAmt(Double.parseDouble(objArr[5].toString())/con);
				objSummaryledger.setDblCreditAmt(Double.parseDouble(objArr[6].toString())/con);
				
				objSummaryledger.setDblBalanceAmt(Double.parseDouble(objArr[7].toString())/con);
				objSummaryledger.setStrBalCrDr(objArr[8].toString());
				objSummaryledger.setStrNarration(objArr[9].toString());
				objSummaryledger.setStrTransTypeForOrderBy(objArr[10].toString());
				objSummaryledger.setStrClientCode(clientCode);
				objSummaryledger.setStrUserCode(userCode);
				objSummaryledger.setStrPropertyCode(propertyCode);

				objGlobalFunctionsService.funAddUpdateLedgerSummary(objSummaryledger);
			}
		}

		return 1;
	}




	public String funCheckFormAuthorization(String formName, HttpServletRequest request)
	{
		String authorize="Yes";
		if (null != request.getSession().getAttribute("hmAuthorization")) {
			HashMap<String, Boolean> hmAuthorization = (HashMap) request.getSession().getAttribute("hmAuthorization");
			if (hmAuthorization.containsKey(formName)) {
				authorize="No";
			}
		}
		return authorize;
	}
	
	public String funGetAuthLevelColumnName(int userLevel)
	{
		String authLevelColumnName = "";

		switch (userLevel)
		{
		case 1:
			authLevelColumnName = "strAuthLevel1";
			break;

		case 2:
			authLevelColumnName = "strAuthLevel2";
			break;

		case 3:
			authLevelColumnName = "strAuthLevel3";
			break;

		case 4:
			authLevelColumnName = "strAuthLevel4";
			break;

		case 5:
			authLevelColumnName = "strAuthLevel5";
			break;
		}

		return authLevelColumnName;
	}
	
	@RequestMapping(value = "/loadBalanceAmtCreditorDetor", method = RequestMethod.GET)
	public @ResponseBody double funGetBalanceAmtCreditorDetor(@RequestParam("type") String type,@RequestParam("custSuppCode")String custSuppCode,@RequestParam("toDate")String toDate,@RequestParam("currency")String currency,HttpServletRequest req,HttpServletResponse resp)
	{
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		String accCode="";
		String detorCretditorCode="";
		double balanceAmt=0.0;
		String webStockDB=req.getSession().getAttribute("WebStockDB").toString();
		
		StringBuilder sbSql=new StringBuilder(); 
		String userCode = req.getSession().getAttribute("usercode").toString();
		
		String startDate = req.getSession().getAttribute("startDate").toString();
		startDate = startDate.split("/")[2] + "-" + startDate.split("/")[1] + "-" + startDate.split("/")[0];
		sbSql.setLength(0);
//		double conversionRate=0.0;
		toDate=funGetDate("yyyy/MM/dd", toDate);
//		sbSql.append("select dblConvToBaseCurr from "+webStockDB+".tblcurrencymaster where strCurrencyCode='"+currency+"' and strClientCode='"+clientCode+"' ");
//		try
//		{
//			List list = objBaseService.funGetList(sbSql,"sql");
//			if(list.size()>0){
//			conversionRate=Double.parseDouble(list.get(0).toString());
//			}
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
		
		if(type.equalsIgnoreCase("Supplier"))
		{
			clsLinkUpHdModel objLinkCust = objLinkupService.funGetARLinkUp(custSuppCode, clientCode, propCode, "Supplier", "Purchase");
			if(null!=objLinkCust)
			{
			List<String>list=funGetDebotCreditorAccountDetails("Creditor",objLinkCust.getStrAccountCode(),clientCode,propCode);
			detorCretditorCode=objLinkCust.getStrAccountCode();
			if(list.size()>0){
				accCode=list.get(0);
			}
			}
			funInvokeWebBookLedger( accCode,  detorCretditorCode,  startDate,  propCode,  startDate,  toDate,  clientCode,  userCode,  req,  resp,  "Creditor", currency);
		}
		else if(type.equalsIgnoreCase("Customer"))
		{
			clsLinkUpHdModel objLinkCust =  objLinkCust = objLinkupService.funGetARLinkUp(custSuppCode, clientCode, propCode,  "Customer", "Sale");
			if(null!=objLinkCust)
			{
			List<String>list=funGetDebotCreditorAccountDetails("Debtor",objLinkCust.getStrAccountCode(),clientCode,propCode);
			detorCretditorCode=objLinkCust.getStrAccountCode();
			if(list.size()>0){
				accCode=list.get(0);
			}
			}
			funInvokeWebBookLedger( accCode,  detorCretditorCode,  startDate,  propCode,  startDate,  toDate,  clientCode,  userCode,  req,  resp,  "Debtor", currency);
		}else if(type.equalsIgnoreCase("Debtor"))
		{
			List<String>list=funGetDebotCreditorAccountDetails("Debtor",custSuppCode,clientCode,propCode);
			detorCretditorCode=custSuppCode;
			if(list.size()>0){
				accCode=list.get(0);
			}
			funInvokeWebBookLedger( accCode,  detorCretditorCode,  startDate,  propCode,  startDate,  toDate,  clientCode,  userCode,  req,  resp,  "Debtor", currency);
		}else if(type.equalsIgnoreCase("Creditor")){
			List<String>list=funGetDebotCreditorAccountDetails("Creditor",custSuppCode,clientCode,propCode);
			detorCretditorCode=custSuppCode;
			if(list.size()>0){
				accCode=list.get(0);
			}
			funInvokeWebBookLedger( accCode,  detorCretditorCode,  startDate,  propCode,  startDate,  toDate,  clientCode,  userCode,  req,  resp,  "Creditor", currency);
		}
		else if(type.equalsIgnoreCase("Employee")){
			sbSql.setLength(0);
			sbSql.append(" select a.strAccountCode,a.strAccountName "
					+ " from tblacmaster a "
					+ " where a.strType='GL Code' and a.strEmployee='Yes' "
					+ " and a.strClientCode='"+clientCode+"' and a.strPropertyCode='"+propCode+"' ");
			
			List listAccDtl = null;
			try {
				listAccDtl = objBaseService.funGetListModuleWise(sbSql, "sql", "WebStocks");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(listAccDtl.size()>0)
			{
				Object[] arrObj=(Object[])listAccDtl.get(0);
				accCode=arrObj[0].toString();
				
			}
		
			funInvokeWebBookLedger( accCode,  custSuppCode,  startDate,  propCode,  startDate,  toDate,  clientCode,  userCode,  req,  resp,  "Employee", currency);
		}
		String hql = " select dblDebitAmt,dblCreditAmt from clsLedgerSummaryModel a where a.strUserCode='" + userCode + "' and a.strPropertyCode='" + propCode + "' and a.strClientCode='" + clientCode + "' order by a.dteVochDate, a.strTransTypeForOrderBy ";
		sbSql.setLength(0);
		sbSql=new StringBuilder(hql); 
		List listBillLedger=new ArrayList();
		try {
			listBillLedger = objBaseService.funGetListModuleWise(sbSql, "hql", "WebBooks");
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}

		if(listBillLedger.size()>0 && null!=listBillLedger)
		{
			for(int i=0;i<listBillLedger.size();i++)
			{
			    Object[] obj=(Object[])listBillLedger.get(i);
			    balanceAmt=balanceAmt+Double.parseDouble(obj[0].toString())-Double.parseDouble(obj[1].toString());
			}
		}
		return balanceAmt;
	}
	
	
	
	public List<String> funGetDebotCreditorAccountDetails(String dcType, String dcCode, String clientCode,String propCode)
	{
		List<String> listDCAccountDetails=new ArrayList<String>();
		StringBuilder sbSql=new StringBuilder();
		sbSql.setLength(0);
		sbSql.append("select a.strWebBookAccCode,a.strWebBookAccName from tbllinkup a where a.strAccountCode='"+dcCode+"' "
			+ "  and a.strClientCode='"+clientCode+"' and a.strPropertyCode='"+propCode+"' ");
		try
		{
			List listAccDtl=objBaseService.funGetListModuleWise(sbSql, "sql", "WebStocks");
			if(listAccDtl.size()>0)
			{
				Object[] arrObj=(Object[])listAccDtl.get(0);
				listDCAccountDetails.add(arrObj[0].toString());
				listDCAccountDetails.add(arrObj[1].toString());
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			return listDCAccountDetails;
		}
	}
	
	
	public List<String> funGetCustSupplierAccountDetails(String masterType, String masterCode, String clientCode,String propCode)
	{
		List<String> listDCAccountDetails=new ArrayList<String>();
		StringBuilder sbSql=new StringBuilder();
		sbSql.setLength(0);
		sbSql.append("select a.strAccountCode,a.strMasterName,a.strWebBookAccCode,a.strWebBookAccName "
			+ " from tbllinkup a where a.strMasterCode='"+masterCode+"' and a.strOperationType='"+masterType+"' "
			+ " and a.strClientCode='"+clientCode+"' and a.strPropertyCode='"+propCode+"' ");
		try
		{
			List listAccDtl=objBaseService.funGetListModuleWise(sbSql, "sql", "WebStocks");
			if(listAccDtl.size()>0)
			{
				Object[] arrObj=(Object[])listAccDtl.get(0);
				listDCAccountDetails.add(arrObj[0].toString());
				listDCAccountDetails.add(arrObj[1].toString());
				listDCAccountDetails.add(arrObj[2].toString());
				listDCAccountDetails.add(arrObj[3].toString());
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			return listDCAccountDetails;
		}
	}
	
	
	public Map<String,clsCreditorOutStandingReportBean> funCalCreditorDebtorOPBalance(String glCode,String clientCode, String fromDate
		, String toDate,String DCType, HttpServletRequest req) throws Exception
	{
		StringBuilder sbSql=new StringBuilder();
		
		Map<String,clsCreditorOutStandingReportBean> hmOutstanding =new HashMap<String,clsCreditorOutStandingReportBean>();

	// Fill map with debtors and creditors
		
		String webStockDB=req.getSession().getAttribute("WebStockDB").toString();
		
		if(DCType.equals("Debtor"))
		{
			sbSql.setLength(0);
			sbSql.append("select sd.strDebtorCode,sd.strDebtorFullName,0.00 "
				+ " from tblsundarydebtormaster sd,"+webStockDB+".tbllinkup link "
				+ " where sd.strDebtorCode=link.strAccountCode and sd.strClientCode='"+clientCode+"' ");
			List listDebtors = objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
			if (listDebtors != null && listDebtors.size() > 0) {
				for (int j = 0; j < listDebtors.size(); j++) {
					Object[] arrObj = (Object[]) listDebtors.get(j);
					clsCreditorOutStandingReportBean objOutStBean = new clsCreditorOutStandingReportBean();
					objOutStBean.setStrDebtorCode(arrObj[0].toString());
					objOutStBean.setStrDebtorName(arrObj[1].toString());
					objOutStBean.setDblBalAmt(Double.parseDouble(arrObj[2].toString()));
					objOutStBean.setDblCrAmt(0.00);
					objOutStBean.setDblDrAmt(0.00);
					objOutStBean.setDblOpnAmt(Double.parseDouble(arrObj[2].toString()));
					
					hmOutstanding.put(arrObj[0].toString(),objOutStBean);
				}
			}
			
		// Opening Balance	
			sbSql.setLength(0);
			sbSql.append("select sd.strDebtorCode,sd.strDebtorFullName, ifnull(op.dblOpeningbal,0.00) as Op_Bal "
				+ " from tblsundarydebtormaster sd left outer join tblsundarydebtoropeningbalance op on sd.strDebtorCode=op.strDebtorCode "
				+ " where op.strAccountCode='"+glCode+"' and op.strClientCode='"+clientCode+"' ");
			List listOpeningBalance = objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
			if (listOpeningBalance != null && listOpeningBalance.size() > 0) {
				for (int j = 0; j < listOpeningBalance.size(); j++) {
					Object[] arrObj = (Object[]) listOpeningBalance.get(j);
					clsCreditorOutStandingReportBean objOutStBean = new clsCreditorOutStandingReportBean();
					
					if(hmOutstanding.containsKey(arrObj[0].toString()))
					{
						objOutStBean=hmOutstanding.get(arrObj[0].toString());
						objOutStBean.setDblBalAmt(Double.parseDouble(arrObj[2].toString()));
						objOutStBean.setDblDrAmt(Double.parseDouble(arrObj[2].toString()));
					}
					else
					{
						objOutStBean.setStrDebtorCode(arrObj[0].toString());
						objOutStBean.setStrDebtorName(arrObj[1].toString());
						objOutStBean.setDblBalAmt(Double.parseDouble(arrObj[2].toString()));
						objOutStBean.setDblCrAmt(0.00);
						objOutStBean.setDblDrAmt(Double.parseDouble(arrObj[2].toString()));
						objOutStBean.setDblOpnAmt(Double.parseDouble(arrObj[2].toString()));
					}
					hmOutstanding.put(arrObj[0].toString(),objOutStBean);
				}
			}
		}
		else
		{
			sbSql.setLength(0);
			sbSql.append("select sd.strCreditorCode,sd.strCreditorFullName,0.00 "
				+ " from tblsundarycreditormaster sd,"+webStockDB+".tbllinkup link "
				+ " where sd.strCreditorCode=link.strAccountCode and sd.strClientCode='"+clientCode+"' ");
			List listCreditors = objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
			if (listCreditors != null && listCreditors.size() > 0) {
				for (int j = 0; j < listCreditors.size(); j++) {
					Object[] arrObj = (Object[]) listCreditors.get(j);
					clsCreditorOutStandingReportBean objOutStBean = new clsCreditorOutStandingReportBean();
					objOutStBean.setStrDebtorCode(arrObj[0].toString());
					objOutStBean.setStrDebtorName(arrObj[1].toString());
					objOutStBean.setDblBalAmt(Double.parseDouble(arrObj[2].toString()));
					objOutStBean.setDblCrAmt(0.00);
					objOutStBean.setDblDrAmt(0.00);
					objOutStBean.setDblOpnAmt(Double.parseDouble(arrObj[2].toString()));
					
					hmOutstanding.put(arrObj[0].toString(),objOutStBean);
				}
			}
			
		// Opening Balance	
			sbSql.setLength(0);
			sbSql.append("select sd.strCreditorCode,sd.strCreditorFullName, ifnull(op.dblOpeningbal,0.00) as Op_Bal "
				+ " from tblsundarycreditormaster sd left outer join tblsundarycreditoropeningbalance op on sd.strCreditorCode=op.strCreditorCode "
				+ " where op.strAccountCode='"+glCode+"' and op.strClientCode='"+clientCode+"' ");
			List listOpBalCreditor = objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
			if (listOpBalCreditor != null && listOpBalCreditor.size() > 0) {
				
				for (int j = 0; j < listOpBalCreditor.size(); j++) {
					Object[] arrObj = (Object[]) listOpBalCreditor.get(j);
					clsCreditorOutStandingReportBean objOutStBean = new clsCreditorOutStandingReportBean();
			
					if(arrObj[0].toString().equals("C00000001"))
					{
						System.out.println(arrObj[0].toString());
					}
					
					if(hmOutstanding.containsKey(arrObj[0].toString()))
					{
						objOutStBean=hmOutstanding.get(arrObj[0].toString());
						objOutStBean.setDblBalAmt(Double.parseDouble(arrObj[2].toString().replaceAll(",","")));
						objOutStBean.setDblCrAmt(Double.parseDouble(arrObj[2].toString().replaceAll(",","")));
					}
					else
					{
						objOutStBean.setStrDebtorCode(arrObj[0].toString());
						objOutStBean.setStrDebtorName(arrObj[1].toString());
						objOutStBean.setDblBalAmt(Double.parseDouble(arrObj[2].toString().replaceAll(",","")));
						objOutStBean.setDblCrAmt(Double.parseDouble(arrObj[2].toString().replaceAll(",","")));
						objOutStBean.setDblDrAmt(0.00);
						objOutStBean.setDblOpnAmt(Double.parseDouble(arrObj[2].toString().replaceAll(",","")));
					}
					hmOutstanding.put(arrObj[0].toString(),objOutStBean);
				}
			}
		}
		

		
	// JV Amount	
		sbSql.setLength(0);
		sbSql.append("select debtor.strDebtorCode,'',debtor.strCrDr, sum(ifnull(debtor.dblAmt,0.00)) "
			+ " from tbljvdebtordtl debtor inner join tbljvhd hd on hd.strVouchNo=debtor.strVouchNo "
			+ " where debtor.strAccountCode='"+glCode+"' and date(hd.dteVouchDate) between '" + fromDate + "' AND '" + toDate + "' "
			+ " and hd.strClientCode='"+clientCode+"' "
			+ " group by debtor.strCrDr,debtor.strDebtorCode ");
		List listJVAmt = objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
		if (listJVAmt != null && listJVAmt.size() > 0) {
			for (int j = 0; j < listJVAmt.size(); j++) {
				Object[] arrObj = (Object[]) listJVAmt.get(j);
				clsCreditorOutStandingReportBean objOutStBean = new clsCreditorOutStandingReportBean();
				objOutStBean.setDblDrAmt(0.00);
				objOutStBean.setDblCrAmt(0.00);
				
				if(arrObj[0].toString().equals("C00000001"))
				{
					System.out.println(arrObj[0].toString());
				}
				
				double debitAmt=0.00,creditAmt=0.00;
				if(arrObj[2].toString().equals("Dr"))
				{
					debitAmt=Double.parseDouble(arrObj[3].toString());
				}
				else
				{
					creditAmt=Double.parseDouble(arrObj[3].toString());
				}
				double netAmt=(debitAmt-creditAmt);
				if(!DCType.equals("Debtor"))
				{
					netAmt=(creditAmt-debitAmt);
				}
				
				if(hmOutstanding.containsKey(arrObj[0].toString()))
				{
					objOutStBean=hmOutstanding.get(arrObj[0].toString());
					netAmt+=objOutStBean.getDblBalAmt();
					objOutStBean.setDblBalAmt(netAmt);
					if(DCType.equals("Debtor"))
					{
						objOutStBean.setDblDrAmt(objOutStBean.getDblBalAmt());
					}
					else
					{
						objOutStBean.setDblCrAmt(objOutStBean.getDblBalAmt());
					}
				}
				else
				{
					objOutStBean.setStrDebtorCode(arrObj[0].toString());
					objOutStBean.setStrDebtorName(arrObj[1].toString());
					objOutStBean.setDblBalAmt(netAmt);
					objOutStBean.setDblOpnAmt(0.00);
				}
				
				hmOutstanding.put(arrObj[0].toString(),objOutStBean);
			}
		}
		

	// Payment Amount	
		sbSql.setLength(0);
		sbSql.append("select debtor.strDebtorCode,'',debtor.strCrDr, sum(ifnull(debtor.dblAmt,0.00)) "
			+ " from tblpaymentdebtordtl debtor inner join tblpaymenthd hd on hd.strVouchNo=debtor.strVouchNo "
			+ " where debtor.strAccountCode='"+glCode+"' and date(hd.dteVouchDate) between '" + fromDate + "' AND '" + toDate + "' "
			+ " and hd.strClientCode='"+clientCode+"' "
			+ " group by debtor.strCrDr,debtor.strDebtorCode ");
		List listPaymentAmt = objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
		if (listPaymentAmt != null && listPaymentAmt.size() > 0) {
			for (int j = 0; j < listPaymentAmt.size(); j++) {
				Object[] arrObj = (Object[]) listPaymentAmt.get(j);
				clsCreditorOutStandingReportBean objOutStBean = new clsCreditorOutStandingReportBean();
				
				double debitAmt=0.00,creditAmt=0.00;
				objOutStBean.setDblDrAmt(0.00);
				objOutStBean.setDblCrAmt(0.00);
				
				if(arrObj[2].toString().equals("Dr"))
				{
					debitAmt=Double.parseDouble(arrObj[3].toString());
				}
				else
				{
					creditAmt=Double.parseDouble(arrObj[3].toString());
				}
				double netAmt=(debitAmt-creditAmt);
				if(!DCType.equals("Debtor"))
				{
					netAmt=(creditAmt-debitAmt);
				}
				
				if(hmOutstanding.containsKey(arrObj[0].toString()))
				{
					objOutStBean=hmOutstanding.get(arrObj[0].toString());
					netAmt+=objOutStBean.getDblBalAmt();
					objOutStBean.setDblBalAmt(netAmt);
					
					if(DCType.equals("Debtor"))
					{
						objOutStBean.setDblDrAmt(objOutStBean.getDblBalAmt());
					}
					else
					{
						objOutStBean.setDblCrAmt(objOutStBean.getDblBalAmt());
					}
				}
				else
				{
					objOutStBean.setStrDebtorCode(arrObj[0].toString());
					objOutStBean.setStrDebtorName(arrObj[1].toString());
					objOutStBean.setDblBalAmt(netAmt);
					objOutStBean.setDblOpnAmt(0.00);
				}
				
				hmOutstanding.put(arrObj[0].toString(),objOutStBean);
			}
		}
		
		
	// Receipt Amount	
		sbSql.setLength(0);
		sbSql.append("select debtor.strDebtorCode,'',debtor.strCrDr, sum(ifnull(debtor.dblAmt,0.00)) "
			+ " from tblreceiptdebtordtl debtor inner join tblreceipthd hd on hd.strVouchNo=debtor.strVouchNo "
			+ " where debtor.strAccountCode='"+glCode+"' and date(hd.dteVouchDate) between '" + fromDate + "' AND '" + toDate + "' "
			+ " and hd.strClientCode='"+clientCode+"' "
			+ " group by debtor.strCrDr,debtor.strDebtorCode ");
		List listReceiptAmt = objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
		if (listReceiptAmt != null && listReceiptAmt.size() > 0) {
			for (int j = 0; j < listReceiptAmt.size(); j++) {
				Object[] arrObj = (Object[]) listReceiptAmt.get(j);
				clsCreditorOutStandingReportBean objOutStBean = new clsCreditorOutStandingReportBean();
				
				objOutStBean.setDblDrAmt(0.00);
				objOutStBean.setDblCrAmt(0.00);
				
				double debitAmt=0.00,creditAmt=0.00;
				if(arrObj[2].toString().equals("Dr"))
				{
					debitAmt=Double.parseDouble(arrObj[3].toString());
				}
				else
				{
					creditAmt=Double.parseDouble(arrObj[3].toString());
				}
				double netAmt=(debitAmt-creditAmt);
				if(!DCType.equals("Debtor"))
				{
					netAmt=(creditAmt-debitAmt);
				}
				
				if(hmOutstanding.containsKey(arrObj[0].toString()))
				{
					objOutStBean=hmOutstanding.get(arrObj[0].toString());
					netAmt+=objOutStBean.getDblBalAmt();
					objOutStBean.setDblBalAmt(netAmt);
					
					if(DCType.equals("Debtor"))
					{
						objOutStBean.setDblDrAmt(objOutStBean.getDblBalAmt());
					}
					else
					{
						objOutStBean.setDblCrAmt(objOutStBean.getDblBalAmt());
					}
				}
				else
				{
					objOutStBean.setStrDebtorCode(arrObj[0].toString());
					objOutStBean.setStrDebtorName(arrObj[1].toString());
					objOutStBean.setDblBalAmt(netAmt);
					objOutStBean.setDblOpnAmt(0.00);
				}
				
				hmOutstanding.put(arrObj[0].toString(),objOutStBean);
			}
		}
		
		return hmOutstanding;
	}
	
	
	
	@RequestMapping(value = "/loadBankBalanceAmt", method = RequestMethod.GET)
	public @ResponseBody double funGetBankBalanceAmt(@RequestParam("accType") String accType,@RequestParam("accCode")String accCode,@RequestParam("toDate")String toDate,@RequestParam("currency")String currency,HttpServletRequest req,HttpServletResponse resp)
	{
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		double balanceAmt=0.0;
		String webStockDB=req.getSession().getAttribute("WebStockDB").toString();
		
		StringBuilder sbSql=new StringBuilder(); 
		String userCode = req.getSession().getAttribute("usercode").toString();
		String startDate = req.getSession().getAttribute("startDate").toString();
		
		startDate = startDate.split("/")[2] + "-" + startDate.split("/")[1] + "-" + startDate.split("/")[0];
		sbSql.setLength(0);
		double conversionRate=0.0;
		toDate=funGetDate("yyyy/MM/dd", toDate);
		sbSql.append("select dblConvToBaseCurr from "+webStockDB+".tblcurrencymaster where strCurrencyCode='"+currency+"' and strClientCode='"+clientCode+"' ");
		try
		{
			List list = objBaseService.funGetList(sbSql,"sql");
			conversionRate=Double.parseDouble(list.get(0).toString());
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		 // when we need to load balance on account type  -
		 if(accType.equalsIgnoreCase(accType)){ 
			//funInvokeWebBookLedger( accCode, startDate,  propCode,  startDate,  toDate,  clientCode,  userCode,  req,  resp,  "Creditor", conversionRate);

			 funDeleteAndInsertWebBookLedgerTable(clientCode, userCode, propCode);

			 funProcessWebBookLedgerSummaryForBankAcc(accCode,startDate, toDate, clientCode, userCode, propCode, req, resp, accType,conversionRate);
		}
		
		
		
		String hql = " select dblDebitAmt,dblCreditAmt from clsLedgerSummaryModel a where a.strUserCode='" + userCode + "' and a.strPropertyCode='" + propCode + "' and a.strClientCode='" + clientCode + "' order by a.dteVochDate, a.strTransTypeForOrderBy ";
		sbSql.setLength(0);
		sbSql=new StringBuilder(hql); 
		List listBillLedger=new ArrayList();
		try {
			listBillLedger = objBaseService.funGetListModuleWise(sbSql, "hql", "WebBooks");
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
//		List listBillLedger = objGlobalFunctionsService.funGetListModuleWise(hql, "hql");
		if(listBillLedger.size()>0 && null!=listBillLedger)
		{
			for(int i=0;i<listBillLedger.size();i++)
			{
			    Object[] obj=(Object[])listBillLedger.get(i);
			    balanceAmt=balanceAmt+Double.parseDouble(obj[0].toString())-Double.parseDouble(obj[1].toString());
			}
		}
		return balanceAmt;
	}
	

	@SuppressWarnings("rawtypes")
	public int funProcessWebBookLedgerSummaryForBankAcc(String acCode, String fromDate, String toDate, String clientCode, String userCode, String propertyCode, HttpServletRequest req, HttpServletResponse resp, String strCrOrDr,double conversionRate){
		StringBuilder sbSql=new StringBuilder();
		sbSql.setLength(0);
		sbSql.append("select DATE_FORMAT(DATE(a.dteCreatedDate),'%d-%m-%Y') dteVochDate,'Opening','Op', " 
				 +" DATE_FORMAT(DATE(a.dteCreatedDate),'%d-%m-%Y') billDate, "
				 + "IF(a.strCrDr='Dr',a.intOpeningBal,0) dblDebitAmt, IF(a.strCrDr='Cr',a.intOpeningBal,0) dblCreditAmt, (IF(a.strCrDr='Dr',a.intOpeningBal,0) - IF(a.strCrDr='Cr',a.intOpeningBal,0)) dblBalanceAmt,ifnull(strCrDr,'')  from tblacmaster a where a.strAccountCode='"+acCode+"' "
			     +" and a.strClientCode='"+clientCode+"' and a.strPropertyCode='" + propertyCode + "' ");
		List listop=new ArrayList();
		try {
			listop = objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (listop.size() > 0) {
			for (int i = 0; i < listop.size(); i++) {
				clsLedgerSummaryModel objSummaryledger = new clsLedgerSummaryModel();
				Object[] objArr = (Object[]) listop.get(i);
				objSummaryledger.setDteVochDate(fromDate);
				objSummaryledger.setStrVoucherNo(objArr[1].toString());
				objSummaryledger.setStrTransType(objArr[2].toString());
				objSummaryledger.setStrChequeBillNo("");
				objSummaryledger.setDteBillDate(fromDate);
				objSummaryledger.setDblDebitAmt(Double.parseDouble(objArr[4].toString())*conversionRate);
				objSummaryledger.setDblCreditAmt(Double.parseDouble(objArr[5].toString())*conversionRate);
				
				objSummaryledger.setDblBalanceAmt(Double.parseDouble(objArr[6].toString())*conversionRate);
				objSummaryledger.setStrBalCrDr(objArr[7].toString());
				objSummaryledger.setStrNarration("");
				objSummaryledger.setStrTransTypeForOrderBy("");
				objSummaryledger.setStrClientCode(clientCode);
				objSummaryledger.setStrUserCode(userCode);
				objSummaryledger.setStrPropertyCode(propertyCode);

				objGlobalFunctionsService.funAddUpdateLedgerSummary(objSummaryledger);
			}
		}
		
		sbSql.setLength(0);
		sbSql.append(" SELECT DATE(a.dteVouchDate) ,a.strVouchNo,'JV'  , DATE(a.dteVouchDate), " + " b.dblDrAmt ,b.dblCrAmt ,b.dblDrAmt - b.dblCrAmt ,'Cr','','1','" + userCode + "','" + propertyCode + "','" + clientCode + "' " + " FROM tbljvhd a, tbljvdtl b"
				+ " WHERE a.strVouchNo=b.strVouchNo " + " AND DATE(a.dteVouchDate) BETWEEN '" + fromDate + "' AND '" + toDate + "' " + " AND b.strAccountCode='" + acCode + "'  " + " AND a.strPropertyCode=b.strPropertyCode " + " AND a.strPropertyCode='" + propertyCode + "' AND a.strClientCode='" + clientCode + "' ");
		List listjv=new ArrayList();
		try {
			listjv = objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (listjv.size() > 0) {
			for (int i = 0; i < listjv.size(); i++) {
				clsLedgerSummaryModel objSummaryledger = new clsLedgerSummaryModel();
				Object[] objArr = (Object[]) listjv.get(i);
				objSummaryledger.setDteVochDate(objArr[0].toString());
				objSummaryledger.setStrVoucherNo(objArr[1].toString());
				objSummaryledger.setStrTransType(objArr[2].toString());
				objSummaryledger.setStrChequeBillNo("");
				objSummaryledger.setDteBillDate(objArr[0].toString());
				objSummaryledger.setDblDebitAmt(Double.parseDouble(objArr[4].toString())*conversionRate);
				objSummaryledger.setDblCreditAmt(Double.parseDouble(objArr[5].toString())*conversionRate);
				
				objSummaryledger.setDblBalanceAmt(Double.parseDouble(objArr[6].toString())*conversionRate);
				objSummaryledger.setStrBalCrDr(objArr[7].toString());
				objSummaryledger.setStrNarration(objArr[8].toString());
				objSummaryledger.setStrTransTypeForOrderBy(objArr[9].toString());
				objSummaryledger.setStrClientCode(clientCode);
				objSummaryledger.setStrUserCode(userCode);
				objSummaryledger.setStrPropertyCode(propertyCode);

				objGlobalFunctionsService.funAddUpdateLedgerSummary(objSummaryledger);
			}
		}
		sbSql.setLength(0);
		sbSql.append(" SELECT DATE(a.dteVouchDate) ,a.strVouchNo,'Payment', a.strChequeNo,  DATE(a.dteChequeDate) ,b.dblDrAmt , b.dblCrAmt ,b.dblDrAmt - b.dblCrAmt ,'Dr','','2','" + userCode + "','" + propertyCode + "','" + clientCode + "' " + " FROM tblpaymenthd a, tblpaymentdtl b "
				+ " WHERE a.strVouchNo=b.strVouchNo  AND DATE(a.dteVouchDate) BETWEEN '" + fromDate + "' AND '" + toDate + "' " + " AND b.strAccountCode='" + acCode + "'  AND a.strPropertyCode=b.strPropertyCode AND a.strPropertyCode='" + propertyCode + "' AND a.strClientCode='" + clientCode + "'  ");

		List listPay=new ArrayList();
		try {
			listPay = objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (listPay.size() > 0) {
			for (int i = 0; i < listPay.size(); i++) {
				clsLedgerSummaryModel objSummaryledger = new clsLedgerSummaryModel();
				Object[] objArr = (Object[]) listPay.get(i);
				objSummaryledger.setDteVochDate(objArr[0].toString());
				objSummaryledger.setStrVoucherNo(objArr[1].toString());
				objSummaryledger.setStrTransType(objArr[2].toString());
				objSummaryledger.setStrChequeBillNo(objArr[3].toString());
				objSummaryledger.setDteBillDate(objArr[4].toString());
				objSummaryledger.setDblDebitAmt(Double.parseDouble(objArr[5].toString()));
				objSummaryledger.setDblCreditAmt(Double.parseDouble(objArr[6].toString()));
				
				objSummaryledger.setDblBalanceAmt(Double.parseDouble(objArr[7].toString()));
				objSummaryledger.setStrBalCrDr(objArr[8].toString());
				objSummaryledger.setStrNarration(objArr[9].toString());
				objSummaryledger.setStrTransTypeForOrderBy(objArr[10].toString());
				objSummaryledger.setStrClientCode(clientCode);
				objSummaryledger.setStrUserCode(userCode);
				objSummaryledger.setStrPropertyCode(propertyCode);

				objGlobalFunctionsService.funAddUpdateLedgerSummary(objSummaryledger);
			}
		}

		// receipt
		sbSql.setLength(0);
		sbSql.append("  SELECT DATE(a.dteVouchDate) ,a.strVouchNo,'Receipt', a.strChequeNo, " 
			+ "DATE(a.dteChequeDate) ,  b.dblDrAmt , b.dblCrAmt ,b.dblDrAmt - b.dblCrAmt ,b.strCrDr,'','3','" + userCode + "','" + propertyCode + "','" + clientCode + "' "
			+ " FROM tblreceipthd a, tblreceiptdtl b WHERE a.strVouchNo=b.strVouchNo "
			+ " AND DATE(a.dteVouchDate) BETWEEN '" + fromDate + "' AND '" + toDate + "' " 
			+ " and  b.strAccountCode='" + acCode + "'  " + " AND a.strPropertyCode=b.strPropertyCode " 
			+ " AND a.strPropertyCode='"+ propertyCode + "' AND a.strClientCode='" + clientCode + "'  ");
		 
		List listRecept=new ArrayList();
		try {
			listRecept = objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (listRecept.size() > 0) {
			for (int i = 0; i < listRecept.size(); i++) {
				clsLedgerSummaryModel objSummaryledger = new clsLedgerSummaryModel();
				Object[] objArr = (Object[]) listRecept.get(i);
				objSummaryledger.setDteVochDate(objArr[0].toString());
				objSummaryledger.setStrVoucherNo(objArr[1].toString());
				objSummaryledger.setStrTransType(objArr[2].toString());
				objSummaryledger.setStrChequeBillNo(objArr[3].toString());
				objSummaryledger.setDteBillDate(objArr[4].toString());
				objSummaryledger.setDblDebitAmt(Double.parseDouble(objArr[5].toString()));
				objSummaryledger.setDblCreditAmt(Double.parseDouble(objArr[6].toString()));
				
				objSummaryledger.setDblBalanceAmt(Double.parseDouble(objArr[7].toString()));
				objSummaryledger.setStrBalCrDr(objArr[8].toString());
				objSummaryledger.setStrNarration(objArr[9].toString());
				objSummaryledger.setStrTransTypeForOrderBy(objArr[10].toString());
				objSummaryledger.setStrClientCode(clientCode);
				objSummaryledger.setStrUserCode(userCode);
				objSummaryledger.setStrPropertyCode(propertyCode);

				objGlobalFunctionsService.funAddUpdateLedgerSummary(objSummaryledger);
			}
		}

		return 1;
	}
	
	@RequestMapping(value = "/loadRecordsInTransaction", method = RequestMethod.GET)
	public @ResponseBody boolean funLoadRecordsInTransactionOfWebBooks(String type, String docCode,HttpServletRequest req)
	{
	
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		StringBuilder sbSql=new StringBuilder();
		boolean flag=false;
		try
		{
		 if(type.equalsIgnoreCase("Account"))
		  {
		    sbSql.setLength(0);
		    sbSql.append("select a.strVouchNo from tbljvdtl a where a.strAccountCode='"+docCode+"' and a.strClientCode='"+clientCode+"'  ");
	
			List listAccDtl=objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
			if(listAccDtl.size()>0)
			{
				flag=true;
			}
			
			sbSql.setLength(0);
			sbSql.append("select a.strVouchNo from tblpaymentdtl a where a.strAccountCode='"+docCode+"' and a.strClientCode='"+clientCode+"'  ");
		
			listAccDtl=objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
			if(listAccDtl.size()>0)
			{
				flag=true;
			}
				
			sbSql.setLength(0);
			sbSql.append("select a.strVouchNo from tblreceiptdtl a where a.strAccountCode='"+docCode+"' and a.strClientCode='"+clientCode+"'  ");
		
			listAccDtl=objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
			if(listAccDtl.size()>0)
			{
				flag=true;
			}
			
			 if(!flag)
			 {
	    			sbSql.setLength(0);
					sbSql.append("delete from tblacmaster where strAccountCode='"+docCode+"' and strClientCode='"+clientCode+"'  ");
					objBaseService.funExcecteUpdateModuleWise(sbSql, "sql", "WebBooks");
					
					sbSql.setLength(0);
					sbSql.append("delete from tbllinkup where strAccountCode='"+docCode+"' and strClientCode='"+clientCode+"'  ");
					objBaseService.funExcecteUpdateModuleWise(sbSql, "sql", "WebStocks");
				 
			 }
		 }
		 
		 if(type.equalsIgnoreCase("Debtor"))
		  {
		    sbSql.setLength(0);
		    sbSql.append("select a.strVouchNo from tbljvdebtordtl a where a.strDebtorCode='"+docCode+"' and a.strClientCode='"+clientCode+"'  ");
	
			List listAccDtl=objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
			if(listAccDtl.size()>0)
			{
				flag=true;
			}
			
			sbSql.setLength(0);
			sbSql.append("select a.strVouchNo from tblpaymentdebtordtl a where a.strDebtorCode='"+docCode+"' and a.strClientCode='"+clientCode+"'  ");
	
			listAccDtl=objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
			if(listAccDtl.size()>0)
			{
				flag=true;
			}
			
			sbSql.setLength(0);
			sbSql.append("select a.strVouchNo from tblreceiptdebtordtl a where a.strDebtorCode='"+docCode+"' and a.strClientCode='"+clientCode+"'  ");
		
			listAccDtl=objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
			if(listAccDtl.size()>0)
			{
				flag=true;
			}
			
			 if(!flag)
			 {
	    			sbSql.setLength(0);
					sbSql.append("delete from tblsundarydebtormaster where strDebtorCode='"+docCode+"' and strClientCode='"+clientCode+"'  ");
					objBaseService.funExcecteUpdateModuleWise(sbSql, "sql", "WebBooks");
					
					sbSql.setLength(0);
					sbSql.append("delete from tbllinkup where strAccountCode='"+docCode+"' and strClientCode='"+clientCode+"'  ");
					objBaseService.funExcecteUpdateModuleWise(sbSql, "sql", "WebStocks");
				 
			 }
		 }
		 
		 if(type.equalsIgnoreCase("Creditor"))
		  {
		    sbSql.setLength(0);
		    sbSql.append("select a.strVouchNo from tbljvdebtordtl a where a.strDebtorCode='"+docCode+"' and a.strClientCode='"+clientCode+"'  ");
	
			List listAccDtl=objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
			if(listAccDtl.size()>0)
			{
				flag=true;
			}
			
			sbSql.setLength(0);
			sbSql.append("select a.strVouchNo from tblpaymentdebtordtl a where a.strDebtorCode='"+docCode+"' and a.strClientCode='"+clientCode+"'  ");
	
			listAccDtl=objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
			if(listAccDtl.size()>0)
			{
				flag=true;
			}
			
			sbSql.setLength(0);
			sbSql.append("select a.strVouchNo from tblreceiptdebtordtl a where a.strDebtorCode='"+docCode+"' and a.strClientCode='"+clientCode+"'  ");
		
			listAccDtl=objBaseService.funGetListModuleWise(sbSql, "sql", "WebBooks");
			if(listAccDtl.size()>0)
			{
				flag=true;
			}
			
			 if(!flag)
			 {
				    sbSql.setLength(0);
					sbSql.append("delete from tblsundarycreditoropeningbalance where strCreditorCode='"+docCode+"' and strClientCode='"+clientCode+"'  ");
					objBaseService.funExcecteUpdateModuleWise(sbSql, "sql", "WebBooks");
					
				 
	    			sbSql.setLength(0);
					sbSql.append("delete from tblsundarycreditormaster where strCreditorCode='"+docCode+"' and strClientCode='"+clientCode+"'  ");
					objBaseService.funExcecteUpdateModuleWise(sbSql, "sql", "WebBooks");
					
					sbSql.setLength(0);
					sbSql.append("delete from tbllinkup where strAccountCode='"+docCode+"' and strClientCode='"+clientCode+"'  ");
					objBaseService.funExcecteUpdateModuleWise(sbSql, "sql", "WebStocks");
				 
			 }
		 }
		 
		
		 
		 
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			return flag;
		}
	}
	
	public List<String> funGetDebotCreditorCurrency(String dcCode, String clientCode,String propCode)
	{
		List<String> listDCAccountDetails=new ArrayList<String>();
		
		StringBuilder sbSql=new StringBuilder();
		sbSql.setLength(0);
		sbSql.append("select a.strWebBookAccCode,a.strWebBookAccName,ifnull(b.strCurrency,'') from tbllinkup a,tblpartymaster b where a.strAccountCode='"+dcCode+"' "
			+ " and b.strPCode=a.strMasterCode and a.strClientCode='"+clientCode+"' and a.strPropertyCode='"+propCode+"' ");
		try
		{
			List listAccDtl=objBaseService.funGetListModuleWise(sbSql, "sql", "WebStocks");
			if(listAccDtl.size()>0)
			{
				Object[] arrObj=(Object[])listAccDtl.get(0);
				listDCAccountDetails.add(arrObj[2].toString());
				
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			return listDCAccountDetails;
		}
	}
	
	


}
