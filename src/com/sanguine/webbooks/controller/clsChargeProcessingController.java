package com.sanguine.webbooks.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JRViewer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lowagie.text.pdf.hyphenation.TernaryTree.Iterator;
import com.mysql.jdbc.Connection;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.webbooks.bean.clsChargeProcessingBean;
import com.sanguine.webbooks.bean.clsCreditorOutStandingReportBean;
import com.sanguine.webbooks.bean.clsJVDetailsBean;
import com.sanguine.webbooks.bean.clsParameterSetupBean;
import com.sanguine.webbooks.model.clsBankMasterModel;
import com.sanguine.webbooks.model.clsChargeMasterModel;
import com.sanguine.webbooks.model.clsChargeProcessingDtlModel;
import com.sanguine.webbooks.model.clsChargeProcessingHDModel;
import com.sanguine.webbooks.model.clsJVDebtorDtlModel;
import com.sanguine.webbooks.model.clsJVDtlModel;
import com.sanguine.webbooks.model.clsJVHdModel;
import com.sanguine.webbooks.model.clsParameterSetupModel;
import com.sanguine.webbooks.model.clsWebBooksAccountMasterModel;
import com.sanguine.webbooks.service.clsChargeMasterService;
import com.sanguine.webbooks.service.clsChargeProcessingService;
import com.sanguine.webbooks.service.clsJVService;
import com.sun.glass.ui.Application;

@Controller
public class clsChargeProcessingController
{

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsChargeMasterService objChargeMasterService;
	@Autowired
	private clsChargeProcessingService objChargeProcessingService;
	@Autowired
	private clsSetupMasterService objSetupMasterService;
	@Autowired
	private ServletContext servletContext;

	@Autowired
	private clsJVService objJVService;

	@Autowired
	private clsGlobalFunctions objGlobal;;

	// Open Charge Processing
	@RequestMapping(value = "/frmChargeProcessing", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request)
	{
		String urlHits = "1";
		try
		{
			urlHits = request.getParameter("saddr").toString();
		}
		catch (NullPointerException e)
		{
			urlHits = "1";
		}

		ArrayList<String> listOtherFunctions = new ArrayList<String>();
		listOtherFunctions.add("Process");
		listOtherFunctions.add("Charge Slip");

		model.put("urlHits", urlHits);
		model.put("listOtherFunctions", listOtherFunctions);

		if (urlHits.equalsIgnoreCase("1"))
		{
			return new ModelAndView("frmChargeProcessing", "command", new clsChargeProcessingBean());
		}
		else
		{
			return new ModelAndView("frmChargeProcessing_1", "command", new clsChargeProcessingBean());
		}
	}

	// Load Master Data On Form
	@RequestMapping(value = "/loadAllChargesFromChargeMaster", method = RequestMethod.GET)
	public @ResponseBody List<clsChargeMasterModel> funAssignFields(HttpServletRequest req)
	{
		List<clsChargeMasterModel> listChargeModel = null;

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();

		List<clsChargeMasterModel> listModel = objChargeMasterService.funGetAllChargesData(clientCode, propertyCode);

		return listModel;
	}

	// Load Master Data On Form
	@RequestMapping(value = "/loadMemberData", method = RequestMethod.GET)
	public @ResponseBody Object funAssignFields(@RequestParam("memberCode") String memberCode, HttpServletRequest req)
	{
		List listmemberData = null;

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();

		listmemberData = objGlobalFunctionsService.funGetDataList("select strDebtorCode,CONCAT_WS(' ',strPrefix,strFirstName,strMiddleName,strLastName)  as strdebtorFullName " + "from tblsundarydebtormaster where strDebtorCode='" + memberCode + "' ", "sql");

		Object[] obj = (Object[]) listmemberData.get(0);

		return obj;
	}

	@RequestMapping(value = "/saveChargeProcessing", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsChargeProcessingBean objBean, BindingResult result, HttpServletRequest req, HttpServletResponse response)
	{
		String urlHits = "1";
		try
		{
			urlHits = req.getParameter("saddr").toString();
		}
		catch (NullPointerException e)
		{
			urlHits = "1";
		}
		if (!result.hasErrors())
		{
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();

			funPreparedAndSaveModel(objBean, userCode, clientCode, propertyCode, req);

			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Charge Processing");

			return new ModelAndView("redirect:/frmChargeProcessing.html?saddr=" + urlHits);
		}
		else
		{
			return new ModelAndView("redirect:/frmChargeProcessing.html?saddr=" + urlHits);
		}
	}

	@SuppressWarnings(
	{ "unchecked", "unchecked", "unchecked" })
	@RequestMapping(value = "/rptChargeProcessingSlip", method = RequestMethod.GET)
	public void funGenerateJasperReport(@RequestParam("memberCode") String memberCode, @RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate, @RequestParam("generatedOnDate") String generatedOnDate, HttpServletRequest req, HttpServletResponse resp)
	{
		try
		{
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();

			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null)
			{
				objSetup = new clsPropertySetupModel();
			}
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webbooks/rptChargeProcessingSlip.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String sqlDtl = "select a.strMemberCode,a.strMemberName,b.strChargeCode,b.strChargeName,d.strAccountName,a.dblAmount,a.strType,a.strCrDr, " + "a.strNarration,a.dteFromDate,a.dteToDate,a.dteGeneratedOn,a.strClientCode,a.strPropertyCode " + "from " + "tblchargegenerationtemp a, " + "tblchargemaster b, " + "tblsundarydebtormaster c, " + "tblacmaster d " + "where a.strChargeCode=b.strChargeCode " + "and a.strMemberCode=c.strDebtorCode " + "and a.strAccountCode=d.strAccountCode ";
			if (!memberCode.equalsIgnoreCase("All"))
			{
				sqlDtl = sqlDtl + "and a.strMemberCode='" + memberCode + "'";
			}

			sqlDtl = sqlDtl + "and a.dteFromDate='" + fromDate + "'";
			sqlDtl = sqlDtl + "and a.dteToDate='" + toDate + "'";
			sqlDtl = sqlDtl + "and a.dteGeneratedOn='" + generatedOnDate + "'";

			sqlDtl = sqlDtl + "group by a.strMemberCode ,a.strChargeCode ";
			sqlDtl = sqlDtl + "order by a.strMemberCode ,a.strChargeCode ";

			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery subQuery = new JRDesignQuery();
			subQuery.setText(sqlDtl);
			jd.setQuery(subQuery);
			JasperReport jr = JasperCompileManager.compileReport(jd);

			@SuppressWarnings("rawtypes")
			HashMap hm = new HashMap();
			hm.put("strCompanyName", companyName);
			hm.put("strUserCode", userCode);
			hm.put("strImagePath", imagePath);

			hm.put("strAddr1", objSetup.getStrAdd1());
			hm.put("strAddr2", objSetup.getStrAdd2());
			hm.put("strCity", objSetup.getStrCity());
			hm.put("strState", objSetup.getStrState());
			hm.put("strCountry", objSetup.getStrCountry());
			hm.put("strPin", objSetup.getStrPin());
			hm.put("strclientCode", clientCode);
			/*
			 * hm.put("dteFromDate",objGlobal.funGetDate("yyyy-MM-dd",
			 * objBean.getDteFromDate()));
			 * hm.put("dteToDateDate",objGlobal.funGetDate("yyyy-MM-dd",
			 * objBean.getDteToDate()));
			 */
			// hm.put("dteGeneratedOn"*/,objGlobal.funGetDate("yyyy-MM-dd",
			// objBean.getDteGeneratedOn()));

			Connection con = null;
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				// replace hard code db connection
				// con = (Connection)
				// DriverManager.getConnection("jdbc:mysql://localhost:3306/dbwebbooks?user=root&password=root");
				con = (Connection) DriverManager.getConnection(clsGlobalFunctions.urlwebbooks + "?user=" + clsGlobalFunctions.urluser + "&password=" + clsGlobalFunctions.urlPassword);
			}
			catch (Exception e)
			{
				System.out.println("Error in conection");
			}
			String type = "pdf";

			JasperPrint p = JasperFillManager.fillReport(jr, hm, con);
			if (type.trim().equalsIgnoreCase("pdf"))
			{
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				byte[] bytes = null;
				bytes = JasperRunManager.runReportToPdf(jr, hm, con);
				resp.setContentType("application/pdf");
				resp.setContentLength(bytes.length);
				servletOutputStream.write(bytes, 0, bytes.length);
				servletOutputStream.flush();
				servletOutputStream.close();
			}
			else if (type.trim().equalsIgnoreCase("xls"))
			{
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptChargeProcessingSlip." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void funPreparedAndSaveModel(clsChargeProcessingBean objBean, String userCode, String clientCode, String propertyCode, HttpServletRequest req)
	{

		// String companyName =
		// req.getSession().getAttribute("companyName").toString();
		// String currencyCode = objBean.getStrCurrency();
		// double conversionRate = 1;
		// String webStockDB =
		// req.getSession().getAttribute("WebStockDB").toString();
		// StringBuilder sbSql = new StringBuilder();
		// String glCode = objBean.getStrAccountCode();
		// sbSql.append("select dblConvToBaseCurr from " + webStockDB +
		// ".tblcurrencymaster where strCurrencyCode='" + currencyCode + "' and
		// strClientCode='" + clientCode + "' ");
		// try
		// {
		// List list = objBaseService.funGetList(sbSql, "sql");
		// conversionRate = Double.parseDouble(list.get(0).toString());
		// }
		// catch (Exception e)
		// {
		// e.printStackTrace();
		// }
		//
		// double currValue =
		// Double.parseDouble(req.getSession().getAttribute("currValue").toString());

//		objGlobal = new clsGlobalFunctions();
		List<clsCreditorOutStandingReportBean> listMemberOutstanding = null;

		String debtorAccountCode = objBean.getStrAccountCode();
		String debtorAccountName = objBean.getStrAccountName();

		List<clsChargeProcessingDtlModel> listOfChargesToBeProcess = new ArrayList<clsChargeProcessingDtlModel>();

		java.util.Iterator<clsChargeProcessingDtlModel> it = objBean.getListChargeDtl().iterator();
		// to get only selected charges
		while (it.hasNext())
		{
			clsChargeProcessingDtlModel objDtlModel = it.next();
			if (objDtlModel.getIsProcessYN() != null && objDtlModel.getIsProcessYN().equalsIgnoreCase("Y"))
			{
				listOfChargesToBeProcess.add(objDtlModel);
			}
		}

		// calculate outstanding and clear temp table for particular member
		if (objBean.getStrMemberCode() != null && objBean.getStrMemberCode().length() > 0)
		{

			req.getSession().setAttribute("memberCode", objBean.getStrMemberCode());

			req.getSession().setAttribute("fromDate", objGlobal.funGetDate("yyyy-MM-dd", objBean.getDteFromDate()));
			req.getSession().setAttribute("toDate", objGlobal.funGetDate("yyyy-MM-dd", objBean.getDteToDate()));
			req.getSession().setAttribute("generatedOnDate", objGlobal.funGetDate("yyyy-MM-dd", objBean.getDteGeneratedOn()));

			// Debtor Controll AC Code '0015-01-00'
			listMemberOutstanding = objChargeProcessingService.funCalculateOutstanding(debtorAccountCode, objGlobal.funGetDate("yyyy-MM-dd", objBean.getDteFromDate()), objGlobal.funGetDate("yyyy-MM-dd", objBean.getDteToDate()), objBean.getStrMemberCode(), clientCode, propertyCode);

			objChargeProcessingService.funClearTblChargeGenerationTemp(objBean.getStrMemberCode());
		}
		else// calculate outstanding and clear temp table for All members
		{
			req.getSession().setAttribute("memberCode", "All");
			req.getSession().setAttribute("fromDate", objGlobal.funGetDate("yyyy-MM-dd", objBean.getDteFromDate()));
			req.getSession().setAttribute("toDate", objGlobal.funGetDate("yyyy-MM-dd", objBean.getDteToDate()));
			req.getSession().setAttribute("generatedOnDate", objGlobal.funGetDate("yyyy-MM-dd", objBean.getDteGeneratedOn()));

			// Debtor Controll AC Code '0015-01-00'
			listMemberOutstanding = objChargeProcessingService.funCalculateOutstanding(debtorAccountCode, objGlobal.funGetDate("yyyy-MM-dd", objBean.getDteFromDate()), objGlobal.funGetDate("yyyy-MM-dd", objBean.getDteToDate()), "All", clientCode, propertyCode);

			objChargeProcessingService.funClearTblChargeGenerationTemp("All");
		}
		Map<String, Double> mapMemOutstandingMembers = new HashMap<String, Double>();

		

		Map<String, String> mapChargeAccCodeName = new HashMap();

		Map<String, List<clsJVDebtorDtlModel>> mapJVDebtorDtlModelsAgainstCharges = new HashMap();

		for (int i = 0; i < listOfChargesToBeProcess.size(); i++)
		{
			String chargeCode = listOfChargesToBeProcess.get(i).getStrChargeCode();			
			String chargeAccCode = listOfChargesToBeProcess.get(i).getStrAccountCode();

			String sqlCheckOutstandingCondition = "select a.strChargeCode,a.strChargeName,a.strCriteria,a.strCondition,a.dblConditionValue,a.strAcctCode,b.strAccountName " + "from tblchargemaster a ,tblacmaster b " + "where a.strAcctCode=b.strAccountCode " + "and a.strChargeCode ='" + chargeCode + "' ";
			List listCheckOutstandingCondition = objGlobalFunctionsService.funGetDataList(sqlCheckOutstandingCondition, "sql");
			if (listCheckOutstandingCondition != null && listCheckOutstandingCondition.size() > 0)
			{

				Object[] arr = (Object[]) listCheckOutstandingCondition.get(0);

				String criteria = arr[2].toString();
				String condition = arr[3].toString();
				String chargeAccName = arr[6].toString();

				mapChargeAccCodeName.put(chargeAccCode, chargeAccName);

				double conditionValue = Double.parseDouble(arr[4].toString());

				for (clsCreditorOutStandingReportBean objOustandingMember : listMemberOutstanding)
				{
					String debtorCode = objOustandingMember.getStrDebtorCode();
					String debtorName = objOustandingMember.getStrDebtorName();
					double debtorOutstandingBalance = objOustandingMember.getDblBalAmt();

					boolean isApplicable = false;

					switch (condition)
					{
					case "<":
						if (debtorOutstandingBalance < conditionValue)
						{
							isApplicable = true;
						}
						break;

					case "<=":
						if (debtorOutstandingBalance <= conditionValue)
						{
							isApplicable = true;
						}
						break;

					case "=":
						if (debtorOutstandingBalance == conditionValue)
						{
							isApplicable = true;
						}
						break;

					case ">":
						if (debtorOutstandingBalance > conditionValue)
						{
							isApplicable = true;
						}
						break;

					case ">=":
						if (debtorOutstandingBalance >= conditionValue)
						{
							isApplicable = true;
						}
						break;
					}

					if (isApplicable)
					{

						clsChargeProcessingHDModel objHDModel = new clsChargeProcessingHDModel();

						objHDModel.setStrMemberCode(debtorCode);
						objHDModel.setStrMemberName(debtorName);
						objHDModel.setDteFromDate(objGlobal.funGetDate("yyyy-MM-dd", objBean.getDteFromDate()));
						objHDModel.setDteToDate(objGlobal.funGetDate("yyyy-MM-dd", objBean.getDteToDate()));
						objHDModel.setDteGeneratedOn(objGlobal.funGetDate("yyyy-MM-dd", objBean.getDteGeneratedOn()));
						objHDModel.setStrInstantJVYN(objBean.getStrInstantJVYN());
						objHDModel.setStrOtherFunctions(objBean.getStrOtherFunctions());
						objHDModel.setStrAnnualChargeProcessYN(objBean.getStrAnnualChargeProcessYN());

						objHDModel.setStrChargeCode(chargeCode);
						objHDModel.setStrAccountCode(chargeAccCode);

						objHDModel.setStrType(listOfChargesToBeProcess.get(i).getStrType());
						objHDModel.setStrCrDr(listOfChargesToBeProcess.get(i).getStrCrDr());
						objHDModel.setStrNarration(listOfChargesToBeProcess.get(i).getStrNarration());

						objHDModel.setStrClientCode(clientCode);
						objHDModel.setStrPropertyCode(propertyCode);

						String chargeType = listOfChargesToBeProcess.get(i).getStrType();
						double chargeAmt = listOfChargesToBeProcess.get(i).getDblAmount();
						if (chargeType.equalsIgnoreCase("Percentage"))
						{
							chargeAmt = (chargeAmt / 100) * debtorOutstandingBalance;

							objHDModel.setDblAmount(chargeAmt);
							objHDModel.setStrType("Amount");
						}
						else
						{
							objHDModel.setDblAmount(chargeAmt);
							objHDModel.setStrType("Amount");
						}

						if (objBean.getStrInstantJVYN().equalsIgnoreCase("Y"))
						{

							if (mapJVDebtorDtlModelsAgainstCharges.containsKey(chargeAccCode))
							{
								List<clsJVDebtorDtlModel> listJVDebtorDtlModel = mapJVDebtorDtlModelsAgainstCharges.get(chargeAccCode);

								clsJVDebtorDtlModel objJVDebtorDtlModel = new clsJVDebtorDtlModel();

								objJVDebtorDtlModel.setStrDebtorCode(debtorCode);
								objJVDebtorDtlModel.setStrDebtorName(debtorName);
								objJVDebtorDtlModel.setStrAccountCode(debtorAccountCode);
								objJVDebtorDtlModel.setStrNarration("Oustanding balance " + debtorOutstandingBalance + " is " + condition + " " + conditionValue);
								objJVDebtorDtlModel.setStrPropertyCode(propertyCode);
								objJVDebtorDtlModel.setStrCrDr("Dr");
								objJVDebtorDtlModel.setDblAmt(chargeAmt);
								objJVDebtorDtlModel.setStrBillNo("NA");
								objJVDebtorDtlModel.setStrInvoiceNo("NA");
								objJVDebtorDtlModel.setStrGuest("");
								objJVDebtorDtlModel.setStrPOSCode("NA");
								objJVDebtorDtlModel.setStrPOSName("NA");
								objJVDebtorDtlModel.setStrRegistrationNo("NA");
								objJVDebtorDtlModel.setStrCreditNo("");
								objJVDebtorDtlModel.setDteBillDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
								objJVDebtorDtlModel.setDteInvoiceDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
								objJVDebtorDtlModel.setDteDueDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

								listJVDebtorDtlModel.add(objJVDebtorDtlModel);
							}
							else
							{
								clsJVDebtorDtlModel objJVDebtorDtlModel = new clsJVDebtorDtlModel();

								objJVDebtorDtlModel.setStrDebtorCode(debtorCode);
								objJVDebtorDtlModel.setStrDebtorName(debtorName);
								objJVDebtorDtlModel.setStrAccountCode(debtorAccountCode);
								objJVDebtorDtlModel.setStrNarration("Oustanding balance " + debtorOutstandingBalance + " is " + condition + " " + conditionValue);
								objJVDebtorDtlModel.setStrPropertyCode(propertyCode);
								objJVDebtorDtlModel.setStrCrDr("Dr");
								objJVDebtorDtlModel.setDblAmt(chargeAmt);
								objJVDebtorDtlModel.setStrBillNo("NA");
								objJVDebtorDtlModel.setStrInvoiceNo("NA");
								objJVDebtorDtlModel.setStrGuest("");
								objJVDebtorDtlModel.setStrPOSCode("NA");
								objJVDebtorDtlModel.setStrPOSName("NA");
								objJVDebtorDtlModel.setStrRegistrationNo("NA");
								objJVDebtorDtlModel.setStrCreditNo("");
								objJVDebtorDtlModel.setDteBillDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
								objJVDebtorDtlModel.setDteInvoiceDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
								objJVDebtorDtlModel.setDteDueDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

								List<clsJVDebtorDtlModel> listJVDebtorDtlModel = new ArrayList<clsJVDebtorDtlModel>();

								listJVDebtorDtlModel.add(objJVDebtorDtlModel);

								mapJVDebtorDtlModelsAgainstCharges.put(chargeAccCode, listJVDebtorDtlModel);
							}
						}

						objChargeProcessingService.funAddUpdateChargeProcessing(objHDModel);
					}
				}
			}

		}

		if (objBean.getStrInstantJVYN().equalsIgnoreCase("Y"))
		{
			if (mapJVDebtorDtlModelsAgainstCharges.size() > 0)
			{
				for (Map.Entry<String, List<clsJVDebtorDtlModel>> jvDebtorDtlentry : mapJVDebtorDtlModelsAgainstCharges.entrySet())
				{

					String chargeAccCode = jvDebtorDtlentry.getKey();
					String chargeAccName = mapChargeAccCodeName.get(chargeAccCode);

					List<clsJVDebtorDtlModel> listJVDebtorDtlModel = jvDebtorDtlentry.getValue();
					
					clsJVHdModel objJVHDModel = new clsJVHdModel();
					List<clsJVDtlModel> listJVDtlModel = new ArrayList<clsJVDtlModel>();

					double totalDebitAmt = 0, totalCreditAmt = 0;

					for (clsJVDebtorDtlModel obJvDebtorDtlModel : listJVDebtorDtlModel)
					{
						totalDebitAmt = totalDebitAmt + obJvDebtorDtlModel.getDblAmt();
						totalCreditAmt = totalCreditAmt + obJvDebtorDtlModel.getDblAmt();
					}

					clsJVDtlModel objJVDtlModel = new clsJVDtlModel();

					objJVDtlModel.setStrAccountCode(chargeAccCode);
					objJVDtlModel.setStrAccountName(chargeAccName);
					objJVDtlModel.setStrCrDr("Dr");
					objJVDtlModel.setDblDrAmt(totalDebitAmt);
					objJVDtlModel.setDblCrAmt(0.00);
					objJVDtlModel.setStrNarration("charge processing");
					objJVDtlModel.setStrOneLine("");
					objJVDtlModel.setStrPropertyCode(propertyCode);

					listJVDtlModel.add(objJVDtlModel);

					objJVDtlModel = new clsJVDtlModel();

					objJVDtlModel.setStrAccountCode(debtorAccountCode);
					objJVDtlModel.setStrAccountName(debtorAccountName);
					objJVDtlModel.setStrCrDr("Cr");
					objJVDtlModel.setDblDrAmt(0.00);
					objJVDtlModel.setDblCrAmt(totalCreditAmt);
					objJVDtlModel.setStrNarration("charge processing");
					objJVDtlModel.setStrOneLine("");
					objJVDtlModel.setStrPropertyCode(propertyCode);

					listJVDtlModel.add(objJVDtlModel);

					String documentNo = objGlobal.funGenerateDocumentCodeWebBook("frmJV", objBean.getDteGeneratedOn(), req);
					objJVHDModel.setStrVouchNo(documentNo);
					long lastNo = 0;
					objJVHDModel.setIntVouchNum(String.valueOf(lastNo));
					objJVHDModel.setStrUserCreated(userCode);
					objJVHDModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

					objJVHDModel.setIntId(0);
					objJVHDModel.setStrNarration("JV Generated by charge processing");
					objJVHDModel.setStrType("NA");
					objJVHDModel.setStrSancCode("NA");
					objJVHDModel.setDteVouchDate(objGlobal.funGetDateAndTime("yyyy-MM-dd", objBean.getDteGeneratedOn()));
					objJVHDModel.setIntVouchMonth(objGlobal.funGetMonth());
					objJVHDModel.setDblAmt(totalCreditAmt);
					objJVHDModel.setStrUserEdited(userCode);
					objJVHDModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					objJVHDModel.setStrMasterPOS("");
					if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("8-WebBookAPGL"))
					{
						objJVHDModel.setStrModuleType("APGL");
					}
					else
					{
						objJVHDModel.setStrModuleType("AR");
					}
					objJVHDModel.setStrTransMode("R");
					objJVHDModel.setStrTransType("A");
					objJVHDModel.setStrPropertyCode(propertyCode);
					objJVHDModel.setStrClientCode(clientCode);
					objJVHDModel.setIntVouchNum("");

					objJVHDModel.setStrSource("Charge Processing");
					objJVHDModel.setStrSourceDocNo(chargeAccCode);

					objJVHDModel.setListJVDebtorDtlModel(listJVDebtorDtlModel);
					objJVHDModel.setListJVDtlModel(listJVDtlModel);
					
					objJVService.funAddUpdateJVHd(objJVHDModel);
				}
			}
		}

	}
}
