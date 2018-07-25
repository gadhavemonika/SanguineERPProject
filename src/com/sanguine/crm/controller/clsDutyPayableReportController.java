package com.sanguine.crm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.model.clsCompanyMasterModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsStockFlashService;
import com.sanguine.util.clsReportBean;

@Controller
public class clsDutyPayableReportController {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctionsService objGlobalService;

	@Autowired
	private clsStockFlashService objStkFlashService;

	@Autowired
	private clsGlobalFunctions objGlobal;

	@RequestMapping(value = "/frmDutyPayableReport", method = RequestMethod.GET)
	public ModelAndView funOpenDailyProductionReport(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmDutyPayableReport_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmDutyPayableReport", "command", new clsReportBean());
		}
	}

	@RequestMapping(value = "/rptDutyPayableReport", method = RequestMethod.GET)
	public ModelAndView funOpenRptDutyPayableReport(clsReportBean objBean, HttpServletRequest req, HttpServletResponse resp) {

		String dteFromDate = objBean.getDtFromDate();
		String dteToDate = objBean.getDtToDate();

		List ExportList = new ArrayList();

		String companyName = req.getSession().getAttribute("companyName").toString();

		String locCode = req.getSession().getAttribute("locationCode").toString();
		String startDate = req.getSession().getAttribute("startDate").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();

		String[] sp = startDate.split(" ");
		String[] spDate = sp[0].split("/");
		startDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0];
		String repotfileName = "DutyPayableReport_" + dteFromDate + "_To_" + dteToDate;
		ExportList.add(repotfileName);

		objGlobal.funInvokeStockFlash(startDate, locCode, dteFromDate, dteToDate, clientCode, userCode, "Both", req, resp);

		List<String> listTitelName = new ArrayList<String>();
		listTitelName.add("");
		listTitelName.add(companyName);
		for (int i = 0; i < 7; i++) {
			listTitelName.add("");
		}
		ExportList.add(listTitelName);
		listTitelName = new ArrayList<String>();

		listTitelName.add("");
		listTitelName.add("Monthly Duty Payable Report");
		for (int i = 0; i < 7; i++) {
			listTitelName.add("");
		}
		ExportList.add(listTitelName);

		String sqlHeader = " select a.dblPercent,a.strTaxCode from tbltaxhd a  " + "	 where a.strExcisable='Y' and a.strTaxOnSP ='sales'  " + "	 and a.dblPercent>0 order by a.dblPercent DESC; ";

		List<String> listHeader = new ArrayList<String>();

		listHeader.add("Chapter Heading"); // for Header srno
		listHeader.add("Name of Products");
		listHeader.add("O/B");
		listHeader.add("Mfg");
		listHeader.add("Sale");
		listHeader.add("C/B");
		listHeader.add("Asseble Value");

		List listVatPer = objGlobalService.funGetList(sqlHeader);
		// List listVattaxCode=new ArrayList();
		List<String> listExTaxCode = new ArrayList<String>();
		for (int cnt = 0; cnt < listVatPer.size(); cnt++) {
			Object[] arrObjVat = (Object[]) listVatPer.get(cnt);
			listHeader.add(arrObjVat[0].toString() + "%");
			listExTaxCode.add(arrObjVat[1].toString());
		}
		String[] ExcelHeader = new String[listHeader.size()];
		ExcelHeader = listHeader.toArray(ExcelHeader);
		ExportList.add(ExcelHeader);

		List listDtl = new ArrayList();
		List listChapterwise = new ArrayList();

		String sqlListdata = " select b.strProdCode,d.strExciseChapter ,sum(b.dblQty) ,sum(b.dblAssValue)  " + "from tblinvoicehd a,tblinvoicedtl b ,tblproductmaster c,tblsubgroupmaster d " + " where a.strInvCode=b.strInvCode and b.strProdCode=c.strProdCode and c.strExciseable='Y' " + " and c.strSGCode=d.strSGCode " + " and date(a.dteInvDate) between '" + dteFromDate + "' and '" + dteToDate
				+ "' " + " group by d.strExciseChapter ; ";
		List listSqlDtlData = objGlobalService.funGetList(sqlListdata);

		String sqlStk = " select sum(a.dblOpeningStk),sum(a.dblClosingStk) ,c.strExciseChapter " + "  from tblcurrentstock a ,tblproductmaster b,tblsubgroupmaster c " + " where a.strProdCode=b.strProdCode and b.strSGCode=c.strSGCode  " + " and  a.strLocCode='" + locCode + "' and c.strExciseChapter <>'' and b.strExciseable='Y' " + " group by c.strExciseChapter";

		List listSqlStkData = objGlobalService.funGetList(sqlStk);

		String sqlData = " select b.strProdCode ,sum(c.dblValue),e.strExciseChapter  " + " from tblinvoicehd a,tblinvoicedtl b,tblinvprodtaxdtl c,tblproductmaster d,tblsubgroupmaster e " + " where a.strInvCode=b.strInvCode and b.strInvCode=c.strInvCode and b.strProdCode= c.strProdCode " + " and  b.strProdCode=d.strProdCode and d.strSGCode=e.strSGCode " + " and c.strDocNo='" + listExTaxCode.get(0)
				+ "' and e.strExciseChapter <> '' and d.strExciseable='Y' " + " and  date(a.dteInvDate) between '" + dteFromDate + "' and '" + dteToDate + "' " + " group by e.strExciseChapter ;";
		List listSqlTax1Value = objGlobalService.funGetList(sqlData);

		sqlData = "select b.strProdCode ,sum(c.dblValue),e.strExciseChapter  " + " from tblinvoicehd a,tblinvoicedtl b,tblinvprodtaxdtl c,tblproductmaster d,tblsubgroupmaster e " + " where a.strInvCode=b.strInvCode and b.strInvCode=c.strInvCode and b.strProdCode= c.strProdCode and " + "  b.strProdCode=d.strProdCode and d.strSGCode=e.strSGCode " + " and c.strDocNo='" + listExTaxCode.get(1)
				+ "' and e.strExciseChapter <> '' and d.strExciseable='Y' " + " and  date(a.dteInvDate) between '" + dteFromDate + "' and '" + dteToDate + "' " + " group by e.strExciseChapter ; ";
		List listSqlTax2Value = objGlobalService.funGetList(sqlData);

		String sqlMfg = "select b.strProdCode,d.strExciseChapter ,sum(b.dblQty) " + " from tblstocktransferhd a,tblstocktransferdtl b ,tblproductmaster c,tblsubgroupmaster d  " + " where a.strSTCode=b.strSTCode and b.strProdCode=c.strProdCode and c.strExciseable='Y'  " + " and c.strSGCode=d.strSGCode and a.strToLocCode='" + locCode + "' " + " and date(a.dtSTDate) between '" + dteFromDate
				+ "' and '" + dteToDate + "' " + " group by d.strExciseChapter ;";
		List listSqlMfg = objGlobalService.funGetList(sqlMfg);
		HashMap<String, Object> hmChapterWise = new HashMap<String, Object>();

		double totAssValue = 0.00;
		double totExTax1 = 0.00;
		double totExTax2 = 0.00;
		for (int cnt = 0; cnt < listSqlDtlData.size(); cnt++) {
			Object[] arrObjSqlData = (Object[]) listSqlDtlData.get(cnt);
			String chapterNo = arrObjSqlData[1].toString();
			listChapterwise = new ArrayList();
			listChapterwise.add(arrObjSqlData[1].toString());

			String sqlGroupNames = "select a.strSGDescHeader from tblsubgroupmaster a  " + "where  a.strExciseChapter='" + arrObjSqlData[1].toString() + "' group  by a.strSGDescHeader;";

			List listSqlGroupName = objGlobalService.funGetList(sqlGroupNames);
			String groupNames = "";
			for (int g = 0; g < listSqlGroupName.size(); g++) {
				groupNames += listSqlGroupName.get(g).toString() + ",";
			}

			listChapterwise.add(groupNames);
			groupNames = "";

			// for(int cnt=0;cnt<listSqlDtlData.size();cnt++ )
			// {
			// Object[] arrObjSqlData=(Object[]) listSqlDtlData.get(cnt);
			// String chapterNo=arrObjSqlData[1].toString();
			// listChapterwise=new ArrayList();
			// listChapterwise.add(arrObjSqlData[1].toString());
			//
			// // String sqlGroupNames =
			// " select b.strGName from tblsubgroupmaster a ,tblgroupmaster b  "
			// // +
			// "where  a.strGCode=b.strGCode and  a.strExciseChapter='"+arrObjSqlData[1].toString()+"' "
			// // + " group  by b.strGName; ";
			//
			// String sqlGroupNames =
			// "select a.strSGDescHeader from tblsubgroupmaster a  "
			// +
			// "where  a.strExciseChapter='"+arrObjSqlData[1].toString()+"' group  by a.strSGDescHeader;";
			//
			// List listSqlGroupName=objGlobalService.funGetList(sqlGroupNames);
			// String groupNames="";
			// for(int g=0;g<listSqlGroupName.size();g++)
			// {
			// groupNames+=listSqlGroupName.get(g).toString()+",";
			// }
			//
			// listChapterwise.add(groupNames);
			// groupNames="";

			for (int stk = 0; stk < listSqlStkData.size(); stk++) {

				Object[] arrObjStkData = (Object[]) listSqlStkData.get(stk);
				double closebalance = 0.0;
				if (arrObjStkData[2].toString().equals(chapterNo)) {
					if (Double.parseDouble(arrObjStkData[0].toString()) >= 0) {
						listChapterwise.add(arrObjStkData[0].toString()); // openning
																			// balance
						closebalance = Double.parseDouble(arrObjStkData[0].toString());
					} else {
						listChapterwise.add("0"); // openning balance
						closebalance = 0;

					}
					for (int cntMfg = 0; cntMfg < listSqlMfg.size(); cntMfg++) {
						Object[] arrObjMfg = (Object[]) listSqlMfg.get(cntMfg);
						if (arrObjMfg[1].toString().equals(chapterNo)) {
							listChapterwise.add(arrObjMfg[2].toString()); // Mfg
							closebalance = closebalance + Double.parseDouble(arrObjMfg[2].toString());
						} else {
							listChapterwise.add(0);
							closebalance = closebalance + 0;
							break;
						}
					}

					// listChapterwise.add("MFG");
					listChapterwise.add(arrObjSqlData[2].toString()); // sales
					if (closebalance - Double.parseDouble(arrObjSqlData[2].toString()) >= 0) {
						listChapterwise.add(closebalance - Double.parseDouble(arrObjSqlData[2].toString()));// closing
																											// balance
					} else {
						listChapterwise.add("0");// closing balance
					}
					listChapterwise.add(arrObjSqlData[3].toString()); // assiablevalue
					totAssValue += Double.parseDouble(arrObjSqlData[3].toString());
					boolean flgAddTax1 = false;
					for (int tax1 = 0; tax1 < listSqlTax1Value.size(); tax1++) {
						Object[] arrObjTaxValue = (Object[]) listSqlTax1Value.get(tax1);
						if (arrObjTaxValue[2].toString().equals(chapterNo)) {
							listChapterwise.add(arrObjTaxValue[1].toString()); // 12.5
																				// %
																				// tax
																				// value
							totExTax1 += Double.parseDouble(arrObjTaxValue[1].toString());
							flgAddTax1 = true;
						}
					}
					if (!flgAddTax1) {
						listChapterwise.add("0");
					}
					boolean flgAddTax2 = false;
					for (int tax2 = 0; tax2 < listSqlTax2Value.size(); tax2++) {
						Object[] arrObjTax2Value = (Object[]) listSqlTax2Value.get(tax2);
						if (arrObjTax2Value[2].toString().equals(chapterNo)) {
							listChapterwise.add(arrObjTax2Value[1].toString()); // 5.5
																				// %
																				// tax
																				// value
							totExTax2 += Double.parseDouble(arrObjTax2Value[1].toString());
							flgAddTax2 = true;
						}
					}
					if (!flgAddTax2) {
						listChapterwise.add("0");
					}

				}

			}
			listDtl.add(listChapterwise);
		}
		listDtl.add(funBlankListforExport("", "", "", "", "", "", 0, 0, 0));
		listDtl.add(funBlankListforExport("", "", "", "", "", "", 0, 0, 0));
		listDtl.add(funBlankListforExport("", "", "", "", "", "", 0, 0, 0));
		listDtl.add(funBlankListforExport("", "", "", "", "", "", 0, 0, 0));
		listDtl.add(funBlankListforExport("", "", "", "", "", "", 0, 0, 0));
		listDtl.add(funBlankListforExport("", "", "", "", "", "", totAssValue, totExTax1, totExTax2));

		ExportList.add(listDtl);

		return new ModelAndView("styleExcelTitleCellBorderView", "sheetlist", ExportList);
	}

	private List funBlankListforExport(String str0, String str1, String str2, String str3, String str4, String str5, double dbl6, double dbl7, double dbl8) {
		List listRow = new ArrayList();

		listRow.add(str0);
		listRow.add(str1);
		listRow.add(str2);
		listRow.add(str3);
		listRow.add(str4);
		listRow.add(str5);
		listRow.add(dbl6);
		listRow.add(dbl7);
		listRow.add(dbl8);

		return listRow;

	}

}
