package com.sanguine.crm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.util.clsReportBean;

@Controller
public class clsDailyProductionReportItemWiseController {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@RequestMapping(value = "/frmDailyProductionReportItemWise", method = RequestMethod.GET)
	public ModelAndView funOpenDailyProductionReport(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmDailyProductionReportItemWise_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmDailyProductionReportItemWise", "command", new clsReportBean());
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/rptDailyProductionReportItemWise", method = RequestMethod.GET)
	public ModelAndView funRptDailyProductionReport(clsReportBean objBean, HttpServletRequest request) {

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String dteFromDate = objBean.getDtFromDate();
		String dteToDate = objBean.getDtToDate();
		String strLocCode = objBean.getStrLocationCode();
		String companyName = request.getSession().getAttribute("companyName").toString();
		String header = "Sr No., Voucher No. , Date,Sub Group,Item Description,Chapter No,Quantity,Unit";
		List ExportList = new ArrayList();

		String reportFileName = "DailyProduction(ItemWise)" + dteFromDate + "_To_" + dteToDate + "_" + userCode;
		ExportList.add(reportFileName);
		List finalList = new ArrayList();
		String[] ExcelHeader = header.split(",");
		ExportList.add(ExcelHeader);
		// String
		// sqpProd="select  a.strPDCode,a.dtPDDate ,c.strProdName,d.strExciseChapter,b.dblQtyProd,c.strUOM from tblproductionhd a,tblproductiondtl b,tblproductmaster c,tblsubgroupmaster d "
		// +" where  a.strPDCode=b.strPDCode and  b.strProdCode=c.strProdCode and c.strSGCode=d.strSGCode "
		// +" and a.dtPDDate between '"+dteFromDate+"' and'"+dteToDate+"' and a.strClientCode='"+clientCode+"'  and a.strClientCode=b.strClientCode ";

		String sqpProd = " select  a.strSTCode,DATE_FORMAT(a.dtSTDate,'%d-%m-%Y'),d.strSGName,c.strProdName,d.strExciseChapter," + " b.dblQty,c.strUOM from tblstocktransferhd a,tblstocktransferdtl b,tblproductmaster c,tblsubgroupmaster d  " + " where  a.strSTCode=b.strSTCode and  b.strProdCode=c.strProdCode " + " and c.strSGCode=d.strSGCode  and a.dtSTDate between '" + dteFromDate + "' and'"
				+ dteToDate + "' " + " and a.strToLocCode='" + strLocCode + "'  and a.strClientCode='" + clientCode + "' " + "  and a.strClientCode=b.strClientCode order by a.dtSTDate ,d.strExciseChapter ";

		List listofProd = objGlobalFunctionsService.funGetList(sqpProd, "sql");
		for (int cnt = 0; cnt < listofProd.size(); cnt++) {
			Object[] ob = (Object[]) listofProd.get(cnt);

			List DataList = new ArrayList();
			DataList.add(cnt + 1);
			DataList.add(ob[0].toString());
			DataList.add(ob[1].toString());
			// DataList.add(funFirstCharacterOfString(ob[2].toString()));
			DataList.add(ob[2].toString());
			DataList.add(ob[3].toString());
			DataList.add(ob[4].toString());
			DataList.add(ob[5].toString());
			DataList.add(ob[6].toString());
			finalList.add(DataList);
		}
		List<String> lstRowList = new ArrayList<String>();
		lstRowList.add("For," + companyName);
		lstRowList.add("Authorised Signatory");

		finalList.add(lstRowList);

		ExportList.add(finalList);

		// return new ModelAndView("excelView", "stocklist", ExportList);
		return new ModelAndView("excelViewWithReportName", "listWithReportName", ExportList);
	}

	private String funFirstCharacterOfString(String strProdName) {
		StringBuilder strName = new StringBuilder();
		String[] splitProdName = strProdName.split(" ");
		for (String oneWord : splitProdName) {
			if (!oneWord.isEmpty()) {
				if (Character.isLetter(oneWord.charAt(0)))
					strName.append(oneWord.charAt(0));
			}
		}

		return strName.toString();
	}

}
