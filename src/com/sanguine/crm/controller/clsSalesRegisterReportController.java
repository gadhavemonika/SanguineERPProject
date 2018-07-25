package com.sanguine.crm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.util.clsReportBean;

@Controller
public class clsSalesRegisterReportController {

	@Autowired
	private clsGlobalFunctionsService objGlobalService;

	@RequestMapping(value = "/frmSalesRegisterReport", method = RequestMethod.GET)
	public ModelAndView funSalesRegisterReport(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		// List<String> strAgainst = new ArrayList<>();
		// strAgainst.add("Direct");
		// strAgainst.add("Delivery Challan");
		// strAgainst.add("Sales Order");
		// model.put("againstList", strAgainst);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmSalesRegisterReport_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmSalesRegisterReport", "command", new clsReportBean());
		}
	}

	@RequestMapping(value = "/rptSalesRegisterExcelReport", method = RequestMethod.GET)
	public ModelAndView loadExcel(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {

		String locCode = req.getSession().getAttribute("locationCode").toString();
		String startDate = req.getSession().getAttribute("startDate").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();

		List ExportList = new ArrayList();
		List listDtl1 = new ArrayList();

		String[] ExcelHeader = { "Date", "Inv Code", "Party Name", "Chapter No", "Description", "Qty.", "Rate", "Ass. Value", "Discount", "Tax Charges", "MRP Value", "Tax Name", "Sales Tax", "Non. Taxable", "Duty %", "Exsice Amt.", "Final Dis", "Total" };
		ExportList.add(ExcelHeader);

		String sqlInvCode = "select strInvCOde from tblinvoicehd where date(dteInvDate)='" + objBean.getDtFromDate() + "' ";
		List listInvCodes = objGlobalService.funGetList(sqlInvCode);
		for (int invC = 0; invC < listInvCodes.size(); invC++) {
			String invCode = (String) listInvCodes.get(invC);
			// String
			// sqlMainSalesData=" select DATE_FORMAT(a.dteInvDate,'%d/%m/%Y'),a.strInvCode,c.strPName,i.strExciseChapter,d.strProdName as ProdName, "
			// +
			// " b.dblQty as Qty,(b.dblAssValue/b.dblQty) as unitAssValue,b.dblAssValue as totAssValue ,e.dblValue as DisAmt, "
			// +
			// " '0.00' as TaxCharges,'0.00' as MRP ,h.strTaxDesc, f.dblValue as TaxAmt, '0.00' as NonTaxableAmt,"
			// +
			// " ifnull(l.strTaxDesc,0.00) as Duty,ifnull(j.dblValue,0.00) as ExciseAmt,'0.00' as FinalDis,"
			// +
			// " (b.dblAssValue+f.dblValue) as Total,b.strProdCode,a.strCustCode "
			// + " from tblinvoicehd a "
			// +
			// " left outer join tblinvoicedtl b on a.strInvCode=b.strInvCode "
			// +
			// " left outer join  tblpartymaster c on a.strCustCode=c.strPCode "
			// +
			// " left outer join  tblproductmaster d on  b.strProdCode=d.strProdCode "
			// +
			// " left outer join  tbltaxsubgroupdtl k on d.strSGCode=k.strSGCode "
			// +
			// " left outer join  tblinvprodtaxdtl e on a.strCustCode=e.strCustCode  and b.strProdCode=e.strProdCode and e.strDocNo='Disc' "
			// +
			// " left outer join  tblinvprodtaxdtl f on a.strInvCode=f.strInvCode  and a.strCustCode=f.strCustCode and "
			// + "  b.strProdCode=f.strProdCode "
			// + " left outer join  tbltaxhd h on f.strDocNo=h.strTaxCode "
			// +
			// " left outer join  tblsubgroupmaster i on i.strSGCode=d.strSGCode "
			// +
			// " left outer join  tblinvprodtaxdtl j on a.strInvCode=j.strInvCode  and a.strCustCode=j.strCustCode and "
			// +
			// " b.strProdCode=j.strProdCode and j.strDocNo=h.strTaxCode  and  "
			// + " h.strExcisable='Y' and j.strDocNo=k.strTaxCode "
			// + " left outer join  tbltaxhd l on j.strDocNo=l.strTaxCode  "
			// +
			// " where  date(a.dteInvDate)='"+objBean.getDtFromDate()+"' and a.strInvCode='"+invCode+"' and a.strInvCode=e.strInvCode   and h.strExcisable='N'   ";

			String sqlMainSalesData = " Select DATE_FORMAT(a.dteInvDate,'%d/%m/%Y'),a.strInvCode,c.strPName,i.strExciseChapter,d.strProdName as ProdName,   " + " b.dblQty as Qty,(b.dblAssValue/b.dblQty) as unitAssValue,b.dblAssValue as totAssValue ,  " + " e.dblValue as DisAmt,  '0.00' as TaxCharges,if(d.strPickMRPForTaxCal='Y',d.dblMRP,0.00) ,b.strProdCode,a.strCustCode "
					+ " from tblinvoicehd a   " + " left outer join tblinvoicedtl b on a.strInvCode=b.strInvCode  " + " left outer join  tblpartymaster c on a.strCustCode=c.strPCode  " + " left outer join  tblproductmaster d on  b.strProdCode=d.strProdCode  " + " left outer join  tbltaxsubgroupdtl k on d.strSGCode=k.strSGCode  "
					+ " left outer join  tblinvprodtaxdtl e on a.strCustCode=e.strCustCode  and b.strProdCode=e.strProdCode and e.strDocNo='Disc'  " + " left outer join  tblsubgroupmaster i on i.strSGCode=d.strSGCode  " + " where  date(a.dteInvDate)='" + objBean.getDtFromDate() + "' and a.strInvCode='" + invCode + "' and a.strInvCode=e.strInvCode   ";

			List listsqlMainData = objGlobalService.funGetList(sqlMainSalesData);

			boolean flgInvCustRow = true;

			double Qty = 0.00;
			double rate = 0.00;
			double totAssValue = 0.00;
			double disAmt = 0.00;
			double taxCharges = 0.00;

			double mrp = 0.00;
			double saleTax = 0.00;
			double nonTaxable = 0.00;
			double duty = 0.00;

			for (int i = 0; i < listsqlMainData.size(); i++) {
				Object[] arrObj = (Object[]) listsqlMainData.get(i);

				String invcode = arrObj[1].toString();
				String prodCode = arrObj[11].toString();
				String custCode = arrObj[12].toString();
				// double finalTotal =0.00;
				double total = 0.00;

				Qty += Double.parseDouble(arrObj[5].toString());
				rate += Double.parseDouble(arrObj[6].toString());
				totAssValue += Double.parseDouble(arrObj[7].toString());

				total = totAssValue;

				if (flgInvCustRow) {
					List listSalesDtl1 = new ArrayList();
					listSalesDtl1.add(arrObj[0].toString());
					listSalesDtl1.add(arrObj[1].toString());
					listSalesDtl1.add(arrObj[2].toString());
					for (int cnt = 3; cnt < 18; cnt++) {
						listSalesDtl1.add("");
					}
					listDtl1.add(listSalesDtl1);
					flgInvCustRow = false;
				}

				// with out Query Execution in

				List listSalesDtl1 = new ArrayList();
				listSalesDtl1.add("");
				listSalesDtl1.add("");
				listSalesDtl1.add("");
				listSalesDtl1.add(arrObj[3].toString());
				listSalesDtl1.add(arrObj[4].toString());
				listSalesDtl1.add(arrObj[5].toString());
				listSalesDtl1.add(arrObj[6].toString());
				listSalesDtl1.add(arrObj[7].toString());
				listSalesDtl1.add(arrObj[8].toString());
				listSalesDtl1.add(arrObj[9].toString());
				listSalesDtl1.add(arrObj[10].toString());

				// taxname ,Sales Tax
				String sqlvat = " select b.strTaxDesc as taxname,a.dblValue as SalesTax " + " from tblinvprodtaxdtl a ,tbltaxhd b " + " where a.strInvCode='" + invcode + "' and a.strCustCode='" + custCode + "' " + " and a.strProdCode='" + prodCode + "' and " + " b.strTaxOnSP='Sales' and a.strDocNo=b.strTaxCode and b.strExcisable='N' ";
				List listvat = objGlobalService.funGetList(sqlvat);

				if (!listvat.isEmpty()) {
					Object[] vatObj = (Object[]) listvat.get(0);
					listSalesDtl1.add(vatObj[0].toString());
					saleTax = Double.parseDouble(vatObj[1].toString());
					listSalesDtl1.add(saleTax);
					listSalesDtl1.add("0.00");
					total += saleTax;

				} else {
					listSalesDtl1.add("0.00");
					listSalesDtl1.add("0.00");
					listSalesDtl1.add("0.00");
				}
				// //////////////////////////////
				// Duty % ,Exsice Amt.
				String sqlDuty = " select b.strTaxDesc,a.dblValue " + " from tblinvprodtaxdtl a ,tbltaxhd b " + " where a.strInvCode='" + invcode + "' and a.strCustCode='" + custCode + "' " + " and a.strProdCode='" + prodCode + "' and " + " b.strTaxOnSP='Sales' and a.strDocNo=b.strTaxCode and b.strExcisable='Y' ";
				List listDuty = objGlobalService.funGetList(sqlDuty);
				if (!listDuty.isEmpty()) {
					Object[] dutyObj = (Object[]) listDuty.get(0);
					listSalesDtl1.add(dutyObj[0].toString());
					listSalesDtl1.add(dutyObj[1].toString());
					total += Double.parseDouble(dutyObj[1].toString());
				} else {
					listSalesDtl1.add("0.00");
					listSalesDtl1.add("0.00");
				}
				// //////////////////////////////
				// listSalesDtl1.add(arrObj[14].toString());
				// listSalesDtl1.add(arrObj[15].toString());

				listSalesDtl1.add("0.00");
				listSalesDtl1.add(total);

				listDtl1.add(listSalesDtl1);

			}

			List listInvRowsTotal = new ArrayList();
			listInvRowsTotal.add("Total :");
			listInvRowsTotal.add("");
			listInvRowsTotal.add("");
			listInvRowsTotal.add("Total :");
			listInvRowsTotal.add("");
			listInvRowsTotal.add(Qty);
			listInvRowsTotal.add(rate);
			listInvRowsTotal.add(totAssValue);
			listInvRowsTotal.add(disAmt);
			listInvRowsTotal.add(taxCharges);
			listInvRowsTotal.add(mrp);
			listInvRowsTotal.add("0.00");
			listInvRowsTotal.add(saleTax);
			listInvRowsTotal.add(nonTaxable);
			listInvRowsTotal.add(mrp);
			listInvRowsTotal.add(duty);

			listInvRowsTotal.add("");
			listInvRowsTotal.add("");
			listInvRowsTotal.add("");
			listDtl1.add(listInvRowsTotal);

		}
		ExportList.add(listDtl1);
		ExportList.add("SalesRegister" + "_" + objBean.getDtFromDate());

		return new ModelAndView("excelView", "stocklist", ExportList);
	}

}
