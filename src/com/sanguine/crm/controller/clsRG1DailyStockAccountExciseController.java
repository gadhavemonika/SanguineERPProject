package com.sanguine.crm.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;

import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.util.clsRG1DailyStockAccountReportDtl;
import com.sanguine.util.clsReportBean;

@Controller
public class clsRG1DailyStockAccountExciseController extends AbstractExcelView {

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@RequestMapping(value = "/frmRG-1DailyStockAccount", method = RequestMethod.GET)
	public ModelAndView funOpenRG1DailyStockAccount(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmRG-1DailyStockAccount_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmRG-1DailyStockAccount", "command", new clsReportBean());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	@Override
	@RequestMapping(value = "/rptRG1DailyStockAccount", method = RequestMethod.GET)
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String fromDate = request.getParameter("dtFromDate").toString();
		String toDate = request.getParameter("dtToDate").toString();
		String companyName = request.getSession().getAttribute("companyName").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String propertyCode = request.getSession().getAttribute("propertyCode").toString();
		String locCode = request.getSession().getAttribute("locationCode").toString();
		String sql = "";
		String startDate = request.getSession().getAttribute("startDate").toString();
		String[] sp = startDate.split(" ");
		String[] spDate = sp[0].split("/");
		startDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0];
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "inline;filename=RG-1DailyStockAccount_" + fromDate + "_To_" + toDate + "_" + userCode + ".xls");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dtF = sdf.parse(fromDate);
		Date dtTo = sdf.parse(toDate);
		long diff = 0;
		diff = dtTo.getTime() - dtF.getTime();
		int diffDays = (int) (diff / (24 * 60 * 60 * 1000));

		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
		if (objSetup == null) {
			objSetup = new clsPropertySetupModel();
		}
		// create a new Excel sheet

		LocalDate lcfromDate = LocalDate.parse(fromDate);

		String SqlchaptoerNo = "select a.strExciseChapter,a.strSGDescHeader from tblsubgroupmaster a " + " where a.strClientCode='" + clientCode + "' group by a.strExciseChapter";
		List listchaptoerNo = objGlobalFunctionsService.funGetDataList(SqlchaptoerNo, "sql");

		// HashMap <String ,ArrayList<clsRG1DailyStockAccountReportDtl>>
		// hmRG1rpt=new
		// HashMap<String,ArrayList<clsRG1DailyStockAccountReportDtl>>();

		HashMap<String, clsRG1DailyStockAccountReportDtl> hmRG1rpt = new HashMap<String, clsRG1DailyStockAccountReportDtl>();

		ArrayList<String> arrChaptorNo = new ArrayList<String>();
		for (int k = 0; k < listchaptoerNo.size(); k++) {
			ArrayList<clsRG1DailyStockAccountReportDtl> listOBjModel = new ArrayList<clsRG1DailyStockAccountReportDtl>();
			Object[] Objchp = (Object[]) listchaptoerNo.get(k);
			String chp = (String) Objchp[0];
			String subGroupDesc = (String) Objchp[1];
			if (chp.trim().length() > 1) {

				// String
				// sqlProdChaptorWise=" select b.strProdCode,b.strProdName "
				// + " from tblsubgroupmaster a,tblproductmaster b "
				// +
				// " where a.strSGCode=b.strSGCode and a.strExciseChapter='"+chp+"' ";
				// List listProdChaptorWise =
				// objGlobalFunctionsService.funGetDataList(sqlProdChaptorWise,"sql");
				//
				HashMap<String, clsRG1DailyStockAccountReportDtl> hmMFG = new HashMap<String, clsRG1DailyStockAccountReportDtl>();

				HashMap<String, clsRG1DailyStockAccountReportDtl> hmINV = new HashMap<String, clsRG1DailyStockAccountReportDtl>();

				HashMap<String, clsRG1DailyStockAccountReportDtl> hmOpenging = new HashMap<String, clsRG1DailyStockAccountReportDtl>();

				clsRG1DailyStockAccountReportDtl objDummyModel = new clsRG1DailyStockAccountReportDtl();
				sql = "  select date(d.dtSTDate),b.strExciseChapter,sum(c.dblQty) " + "from tblproductmaster a " + " inner join tblsubgroupmaster b on a.strSGCode=b.strSGCode " + " left outer join tblstocktransferdtl c on a.strProdCode=c.strProdCode " + " left outer join tblstocktransferhd d on c.strSTCode=d.strSTCode " + " where b.strExciseChapter='" + chp + "' "
						+ " and date(d.dtSTDate) between '" + fromDate + "' and '" + toDate + "' " + " group by d.dtSTDate,b.strExciseChapter order by date(d.dtSTDate) ";
				List listMFGChaptorWise = objGlobalFunctionsService.funGetDataList(sql, "sql");

				for (int sr = 0; sr < listMFGChaptorWise.size(); sr++) {
					Object[] obj = (Object[]) listMFGChaptorWise.get(sr);
					clsRG1DailyStockAccountReportDtl objModel = new clsRG1DailyStockAccountReportDtl();
					objModel.setDateInv(obj[0].toString());
					objModel.setChaptorNo(obj[1].toString());
					objModel.setDblMfgQTY(obj[2].toString());

					hmMFG.put(obj[0].toString(), objModel);
					// hmRG1rpt.put(obj[0].toString(), objDummyModel);
				}

				sql = "   select date(d.dteInvDate),b.strExciseChapter,sum(c.dblQty),sum(c.dblAssValue) " + " from tblproductmaster a " + " inner join tblsubgroupmaster b on a.strSGCode=b.strSGCode " + " left outer join tblinvoicedtl c on a.strProdCode=c.strProdCode " + " left outer join tblinvoicehd d on c.strInvCode=d.strInvCode " + " where b.strExciseChapter='" + chp + "' and "
						+ " date(d.dteInvDate) between '" + fromDate + "' and '" + toDate + "' " + " group by date(d.dteInvDate),b.strExciseChapter  " + "order by date(d.dteInvDate); ";
				List listINVChaptorWise = objGlobalFunctionsService.funGetDataList(sql, "sql");

				for (int sr = 0; sr < listINVChaptorWise.size(); sr++) {
					Object[] obj = (Object[]) listINVChaptorWise.get(sr);
					clsRG1DailyStockAccountReportDtl objModel = new clsRG1DailyStockAccountReportDtl();
					objModel.setDateInv(obj[0].toString());
					objModel.setChaptorNo(obj[1].toString());
					objModel.setDblInvQty(obj[2].toString());
					objModel.setDblAssValue(obj[3].toString());

					hmINV.put(obj[0].toString(), objModel);
					// hmRG1rpt.put(obj[0].toString(), objDummyModel);
				}

				LocalDate lcQueryfromDate;
				String eachfDate = "";
				for (int days = 0; days <= diffDays; days++) {
					lcQueryfromDate = lcfromDate.plusDays(days);
					eachfDate = lcQueryfromDate.toString();
					objGlobalFunctions.funInvokeStockFlash(startDate, locCode, eachfDate, eachfDate, clientCode, userCode, "Both", request, response);

					sql = " select sum(a.dblOpeningStk) from tblcurrentstock a ,tblsubgroupmaster b,tblproductmaster c " + " where a.strProdCode=c.strProdCode " + " and c.strSGCode=b.strSGCode and a.strLocCode='" + locCode + "' and a.strLocCode='" + locCode + "' " + " and a.strUserCode='" + userCode + "' and b.strExciseChapter='" + chp + "' " + " group by a.strLocCode,a.strUserCode;  ";
					List listOpeningChaptorWise = objGlobalFunctionsService.funGetDataList(sql, "sql");
					for (int sr = 0; sr < listOpeningChaptorWise.size(); sr++) {
						BigDecimal opQty = (BigDecimal) listOpeningChaptorWise.get(sr);
						clsRG1DailyStockAccountReportDtl objModel = new clsRG1DailyStockAccountReportDtl();
						objModel.setDblOpeningStk(opQty.toString());
						hmOpenging.put(eachfDate, objModel);
						hmRG1rpt.put(eachfDate, objModel);
					}

				}

				// for (Map.Entry<String, clsRG1DailyStockAccountReportDtl>
				// entryOpeningMain : hmRG1rpt.entrySet())
				// {
				//
				// String keyOpeningMainOpeningMain
				// =entryOpeningMain.getKey().toString();
				// clsRG1DailyStockAccountReportDtl objMain
				// =entryOpeningMain.getValue();
				//
				// for (Map.Entry<String, clsRG1DailyStockAccountReportDtl>
				// entryMFG : hmMFG.entrySet())
				// {
				//
				// String keyMGF = entryMFG.getKey().toString();
				// clsRG1DailyStockAccountReportDtl objMFG =entryMFG.getValue();
				// objMain.setDblMfgQTY(objMFG.getDblMfgQTY());
				//
				// for (Map.Entry<String, clsRG1DailyStockAccountReportDtl>
				// entryINV : hmINV.entrySet())
				// {
				// String keyINV = entryINV.getKey().toString();
				// clsRG1DailyStockAccountReportDtl objINV =
				// entryINV.getValue();
				// objMain.setDblInvQty(objINV.getDblInvQty());
				//
				// objMain.setDateInv(lcForStockfromDate);
				// objMain.setDblOpeningStk(String.valueOf(opQty));
				// objMain.setDblMfgQTY(String.valueOf(mfgQty));
				// objMain.setDblTotalQty(String.valueOf(opQty+mfgQty));
				// objMain.setDblInvQty(String.valueOf(invQty));
				// objMain.setDblAssValue(String.valueOf(invAssvalue));
				//
				// objMain.setDblExportQty("0.00");
				// objMain.setDblExportValue("");
				// objMain.setDblExportQtyBound("0.00");
				// objMain.setDblExportBoundValue("");
				// objMain.setDblExportQtyFactoryBound("0.00");
				// objMain.setDblExportFactoryBoundValue("");
				// objMain.setPurpose("");
				// objMain.setDblpurposeQty("0.00");
				// }
				// }
				// }

				for (int dNo = 0; dNo <= diffDays; dNo++) {
					lcQueryfromDate = lcfromDate.plusDays(dNo);
					eachfDate = lcQueryfromDate.toString();
					double opQty = 0.00, mfgQty = 0.00, invQty = 0.00, invAssvalue = 0.00, totFirstQty = 0.00, restClosingbalance = 0.00;
					;
					// String keyOpeningMainOpeningMain
					// =entryOpeningMain.getKey().toString();
					clsRG1DailyStockAccountReportDtl objMain = hmRG1rpt.get(eachfDate);
					opQty = Double.parseDouble(objMain.getDblOpeningStk());

					clsRG1DailyStockAccountReportDtl objMFG = hmMFG.get(eachfDate);
					if (objMFG != null) {
						if (objMFG.getDblMfgQTY() != null) {
							mfgQty = Double.parseDouble(objMFG.getDblMfgQTY());
							// objMain.setDblMfgQTY(objMFG.getDblMfgQTY());
							// clsRG1DailyStockAccountReportDtl objINV
							// =hmINV.get(eachfDate);
						}
					}
					// mfgQty=Double.parseDouble(objMFG.getDblMfgQTY());
					objMain.setDblMfgQTY(String.valueOf(mfgQty));
					clsRG1DailyStockAccountReportDtl objINV = hmINV.get(eachfDate);

					if (objINV != null) {
						if (objINV.getDblInvQty() != null) {
							invQty = Double.parseDouble(objINV.getDblInvQty());
							invAssvalue = Double.parseDouble(objINV.getDblAssValue());
						}

					}

					// objMain.setDblInvQty(objINV.getDblInvQty());
					if (opQty > 0.00 || mfgQty > 0.00 || invQty > 0.00) {
						objMain.setDateInv(eachfDate);

						objMain.setDblTotalQty(String.valueOf(opQty + mfgQty));
						objMain.setDblInvQty(String.valueOf(invQty));
						objMain.setDblAssValue(String.valueOf(invAssvalue));

						objMain.setDblExportQty("0.00");
						objMain.setDblExportValue("");
						objMain.setDblExportQtyBound("0.00");
						objMain.setDblExportBoundValue("");
						objMain.setDblExportQtyFactoryBound("0.00");
						objMain.setDblExportFactoryBoundValue("");
						objMain.setPurpose("");
						objMain.setDblpurposeQty("0.00");

						String sqlInvExTaxes = " select a.strInvCode,d.dblPercent,sum(c.dblTaxAmt) from tblinvoicehd a, tblinvoicedtl b,tblinvtaxdtl c,  " + " tbltaxhd d ,tblproductmaster e ,tblsubgroupmaster f " + " where a.strInvCode=b.strInvCode and a.strInvCode = c.strInvCode " + " and d.strTaxCode=c.strTaxCode and d.strExcisable='Y' " + " and date(a.dteInvDate)= '" + eachfDate
								+ "' and b.strProdCode=e.strProdCode " + " and e.strSGCode=f.strSGCode and f.strExciseChapter='" + chp + "' " + "  group by  d.dblPercent ";
						List listInvTaxes = objGlobalFunctionsService.funGetDataList(sqlInvExTaxes, "sql");
						String invExTax = "";
						double invExTaxAmt = 0.00;
						for (int incnt = 0; incnt < listInvTaxes.size(); incnt++) {
							Object[] objTax = (Object[]) listInvTaxes.get(incnt);
							invExTax += objTax[1].toString() + ",";
							invExTaxAmt += Double.parseDouble(objTax[2].toString());
						}
						objMain.setExTaxPer(invExTax);
						objMain.setDblExTaxAmt(String.valueOf(invExTaxAmt));
						objMain.setDblclosingBalance(String.valueOf(opQty + mfgQty - invQty));
						objMain.setDblloseQty("0.00");

						sql = "  select a.strInvCode from tblinvoicehd a where  date(a.dteInvDate)= '" + eachfDate + "'	 ";
						List listInvCodes = objGlobalFunctionsService.funGetDataList(sql, "sql");
						String invCodes = "";
						for (int inv = 0; inv < listInvCodes.size(); inv++) {
							invCodes += (String) listInvCodes.get(inv) + ",";
						}
						objMain.setVoucherNo(invCodes);
						objMain.setDateInvRemark(eachfDate);
						listOBjModel.add(objMain);
					}
					// objMain.setDateInv(eachfDate);
					//
					// objMain.setDblTotalQty(String.valueOf(opQty+mfgQty));
					// objMain.setDblInvQty(String.valueOf(invQty));
					// objMain.setDblAssValue(objMFG.getDblAssValue());
					//
					// objMain.setDblExportQty("0.00");
					// objMain.setDblExportValue("");
					// objMain.setDblExportQtyBound("0.00");
					// objMain.setDblExportBoundValue("");
					// objMain.setDblExportQtyFactoryBound("0.00");
					// objMain.setDblExportFactoryBoundValue("");
					// objMain.setPurpose("");
					// objMain.setDblpurposeQty("0.00");
					//
					//
					//
					// String
					// sqlInvExTaxes=" select a.strInvCode,d.dblPercent,sum(c.dblTaxAmt) from tblinvoicehd a, tblinvoicedtl b,tblinvtaxdtl c,  "
					// + " tbltaxhd d ,tblproductmaster e ,tblsubgroupmaster f "
					// +
					// " where a.strInvCode=b.strInvCode and a.strInvCode = c.strInvCode "
					// +
					// " and d.strTaxCode=c.strTaxCode and d.strExcisable='Y' "
					// +
					// " and date(a.dteInvDate)= '"+eachfDate+"' and b.strProdCode=e.strProdCode "
					// +
					// " and e.strSGCode=f.strSGCode and f.strExciseChapter='"+chp+"' "
					// + "  group by  d.dblPercent " ;
					// List listInvTaxes =
					// objGlobalFunctionsService.funGetDataList(sqlInvExTaxes,"sql");
					// String invExTax="";
					// double invExTaxAmt=0.00;
					// for (int incnt = 0; incnt < listInvTaxes.size(); incnt++)
					// {
					// Object[] objTax = (Object[]) listInvTaxes.get(incnt);
					// invExTax+= objTax[1].toString()+",";
					// invExTaxAmt+= Double.parseDouble(objTax[2].toString());
					// }
					// objMain.setExTaxPer(invExTax);
					// objMain.setDblExTaxAmt(String.valueOf(invExTaxAmt));
					// objMain.setDblclosingBalance(String.valueOf(opQty+mfgQty-invQty));
					// objMain.setDblloseQty("0.00");
					//
					// sql="  select a.strInvCode from tblinvoicehd a where  date(a.dteInvDate)= '"+eachfDate+"'	 ";
					// List listInvCodes =
					// objGlobalFunctionsService.funGetDataList(sql,"sql");
					// String invCodes="";
					// for(int inv=0;inv<listInvCodes.size();inv++)
					// {
					// invCodes += (String)listInvCodes.get(inv)+",";
					// }
					// objMain.setVoucherNo(invCodes);
					// objMain.setDateInvRemark(eachfDate);
					// listOBjModel.add(objMain);

					// }
					// }
				}

				// for(int sr=0;sr<listProdChaptorWise.size();sr++)
				// {
				// int day=0;
				// int preday=-1;
				//
				// for(long days=0; days<diffDays;days++)
				// {
				// LocalDate lcQueryfromDate = lcfromDate.plusDays(day);
				// String QueryfromDate=lcQueryfromDate.toString();
				//
				// LocalDate lcprefromDate = lcfromDate.plusDays(preday);
				// String lcForStockfromDate=lcprefromDate.toString();
				//
				// if(! QueryfromDate.equals(toDate))
				// {
				// clsRG1DailyStockAccountReportDtl objRGModel=new
				// clsRG1DailyStockAccountReportDtl();
				// Object[] ObjChPr =(Object[]) listProdChaptorWise.get(sr);
				// String prodCode=ObjChPr[0].toString();
				// objRGModel.setStrProdCode(prodCode);
				// objRGModel.setStrProdName(ObjChPr[1].toString());
				//
				// double opQty=0.0;
				// double mfgQty=0.0;
				// double totQty=0.0;
				// double invQty=0.0;
				// double invAssvalue=0.0;
				// double balQty=0.0;
				// double openingStock=0.00;
				//
				// double totopQty=0.0;
				// double totmfgQty=0.0;
				// double tottotQty=0.0;
				// double totinvQty=0.0;
				// double totinvAssvalue=0.0;
				// double totbalQty=0.0;
				// double totopeningStock=0.00;
				//
				//
				//
				//
				//
				// System.out.println(sr+"Date :"+lcForStockfromDate);
				// boolean flgOp=false,flgMfg=false,flgInv=false;
				// openingStock=objGlobalFunctions.funCalculateStockForTrans(prodCode,
				// locCode,startDate,lcForStockfromDate,clientCode,
				// userCode,0.00);
				// //objRGModel.setDblOpeningStk(String.valueOf(openingStock));
				// opQty=openingStock;
				// flgOp=true;
				//
				//
				// sql=" select b.dblQty from tblstocktransferhd a , tblstocktransferdtl  b where a.strSTCode=b.strSTCode "
				// +
				// " and b.strProdCode='"+prodCode+"' and date(a.dtSTDate)='"+QueryfromDate+"' and a.strToLocCode='"+locCode+"';  ";
				// List listMfgQty =
				// objGlobalFunctionsService.funGetDataList(sql,"sql");
				// if(listMfgQty.size()>0)
				// {
				// objRGModel.setDblMfgQTY(listMfgQty.get(0).toString());
				// mfgQty=Double.parseDouble(listMfgQty.get(0).toString());
				// flgMfg=true;
				// }
				//
				// sql=
				// " select Sum(b.dblQty),sum(b.dblAssValue) from tblinvoicehd a,tblinvoicedtl b "
				// +
				// " where  a.strInvCode=b.strInvCode and  b.strProdCode='"+prodCode+"' "
				// + " and date(a.dteInvDate)='"+QueryfromDate+"'"
				// + " group by b.strProdCode ";
				// List listInvQty =
				// objGlobalFunctionsService.funGetDataList(sql,"sql");
				// if(listInvQty.size()>0)
				// {
				// Object []objInv =(Object[]) listInvQty.get(0);
				// objRGModel.setDblInvQty(objInv[0].toString());
				// invQty=Double.parseDouble(objInv[0].toString());
				// objRGModel.setDblAssValue(objInv[1].toString());
				// invAssvalue=Double.parseDouble(objInv[1].toString());
				// flgInv=true;
				// }
				//
				//
				// if( flgMfg || flgInv)
				// {
				//
				// objRGModel.setDateInv(lcForStockfromDate);
				// objRGModel.setDblOpeningStk(String.valueOf(opQty));
				// objRGModel.setDblMfgQTY(String.valueOf(mfgQty));
				// objRGModel.setDblTotalQty(String.valueOf(opQty+mfgQty));
				// objRGModel.setDblInvQty(String.valueOf(invQty));
				// objRGModel.setDblAssValue(String.valueOf(invAssvalue));
				//
				// objRGModel.setDblExportQty("0.00");
				// objRGModel.setDblExportValue("");
				// objRGModel.setDblExportQtyBound("0.00");
				// objRGModel.setDblExportBoundValue("");
				// objRGModel.setDblExportQtyFactoryBound("0.00");
				// objRGModel.setDblExportFactoryBoundValue("");
				// objRGModel.setPurpose("");
				// objRGModel.setDblpurposeQty("0.00");
				//
				//
				// String
				// sqlInvExTaxes="select a.strInvCode,d.dblPercent,c.dblTaxAmt from tblinvoicehd a, tblinvoicedtl b,tblinvtaxdtl c,tbltaxhd d "
				// +
				// " where a.strInvCode=b.strInvCode and a.strInvCode = c.strInvCode and d.strTaxCode=c.strTaxCode and d.strExcisable='Y' "
				// +
				// " and date(a.dteInvDate)= '"+QueryfromDate+"' and b.strProdCode='"+prodCode+"'"
				// ;
				// List listInvTaxes =
				// objGlobalFunctionsService.funGetDataList(sqlInvExTaxes,"sql");
				// String invExTax="";
				// double invExTaxAmt=0.00;
				// for (int incnt = 0; incnt < listInvTaxes.size(); incnt++)
				// {
				// Object[] objTax = (Object[]) listInvTaxes.get(incnt);
				// invExTax+= objTax[1].toString()+",";
				// invExTaxAmt+= Double.parseDouble(objTax[2].toString());
				// }
				//
				// if(listInvTaxes.size()>0)
				// {
				// objRGModel.setExTaxPer(invExTax);
				// objRGModel.setDblExTaxAmt(String.valueOf(invExTaxAmt));;
				// }
				//
				// objRGModel.setDblclosingBalance(String.valueOf(opQty+mfgQty-invQty));
				// objRGModel.setDblloseQty("");
				//
				// sql="  select a.strInvCode from tblinvoicehd a,tblinvoicedtl b where a.strInvCode=b.strInvCode "
				// +
				// " and date(a.dteInvDate)= '"+QueryfromDate+"' and b.strProdCode='"+prodCode+"' ; ";
				// List listInvCodes =
				// objGlobalFunctionsService.funGetDataList(sql,"sql");
				// String invCodes="";
				// for(int inv=0;inv<listInvCodes.size();inv++)
				// {
				// invCodes += (String)listInvCodes.get(inv)+",";
				// }
				// objRGModel.setVoucherNo(invCodes);
				// objRGModel.setDateInvRemark(lcForStockfromDate);
				//
				// listOBjModel.add(objRGModel);
				//
				//
				// }
				//
				//
				//
				// }
				// day++;
				// if(preday==-1)
				// {
				// preday=0;
				//
				// }else
				// {
				// preday+=1;
				//
				// }
				//
				// }
				//
				//
				//
				//
				// //
				// sql=" select a.dblOpeningStk from tblcurrentstock a where a.strProdCode='"+ObjChPr[0].toString()+"' and a.strClientCode='"+clientCode+"' and a.strLocCode='"+locCode+"' and a.strUserCode='"+userCode+"'";
				// // List listOpBal =
				// objGlobalFunctionsService.funGetDataList(sql,"sql");
				//
				// // hmRG1rpt.put(ObjChPr[0].toString(),objRGModel);
				// }
				// hmRG1rpt.put(chp, listOBjModel);

				List listStock = new ArrayList();
				HSSFSheet sheet = workbook.createSheet(subGroupDesc);
				sheet.setDefaultColumnWidth(15);
				// /Bold Font
				Font font = workbook.createFont();
				font.setFontName("Arial");
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				font.setColor(HSSFColor.BLACK.index);
				// /Normal Font

				Font normlFont = workbook.createFont();
				normlFont.setFontName("Arial");
				normlFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
				normlFont.setColor(HSSFColor.BLACK.index);

				// create style for header cells
				CellStyle style = workbook.createCellStyle();
				style.setFont(font);

				CellStyle styleNormlfont = workbook.createCellStyle();
				styleNormlfont.setFont(normlFont);

				// /////Create Style for set a text in middle of cell and table
				// border
				CellStyle styleOfAligment = workbook.createCellStyle();
				styleOfAligment.setFont(font);
				styleOfAligment.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				styleOfAligment.setBorderTop(HSSFCellStyle.BORDER_THIN);
				styleOfAligment.setBorderRight(HSSFCellStyle.BORDER_THIN);
				styleOfAligment.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				styleOfAligment.setAlignment(style.ALIGN_CENTER);

				CellStyle styleOfAligmentNormalFont = workbook.createCellStyle();
				styleOfAligmentNormalFont.setFont(normlFont);
				styleOfAligmentNormalFont.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				styleOfAligmentNormalFont.setBorderTop(HSSFCellStyle.BORDER_THIN);
				styleOfAligmentNormalFont.setBorderRight(HSSFCellStyle.BORDER_THIN);
				styleOfAligmentNormalFont.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				styleOfAligmentNormalFont.setAlignment(style.ALIGN_CENTER);

				// ////First Row////////////////////
				String firstRow = "" + companyName + ", , , , ,RG - 1 Daily Stock Account ";
				HSSFRow header = sheet.createRow(0);
				String[] data = firstRow.split(",");
				// ExportList.add(ExcelHeader);
				for (int i = 0; i < data.length; i++) {
					header.createCell(i).setCellValue(data[i]);

					header.getCell(i).setCellStyle(style);
				}

				sheet.addMergedRegion(new CellRangeAddress(// //Merging col 0to4
															// and row 0
						0, // first row (0-based)
						0, // last row (0-based)
						0, // first column (0-based)
						4 // last column (0-based)
				));

				// ///////// SecondRow/////////
				String scndRow = "" + objSetup.getStrAdd1().split(",")[0] + "'," + objSetup.getStrAdd1().split(",")[1] + ", , ,Self Removal Procedure (Rule 10 of C.Ex.2002) (Rule 11 of C.ex(2)Cenvat Rule 2002)";
				HSSFRow secndRowHeader = sheet.createRow(1);
				data = scndRow.split(",");
				for (int i = 0; i < data.length; i++) {
					secndRowHeader.createCell(i).setCellValue(data[i]);
					if (i > 3) {
						secndRowHeader.getCell(i).setCellStyle(style);
					} else {
						secndRowHeader.getCell(i).setCellStyle(styleNormlfont);
					}
				}

				sheet.addMergedRegion(new CellRangeAddress(// //Merging col 0to2
															// and row 1
						1, // first row (0-based)
						1, // last row (0-based)
						0, // first column (0-based)
						2 // last column (0-based)
				));

				// ////Third Row////////

				String thrdRow = "" + objSetup.getStrCity() + "- " + objSetup.getStrPin() + " , , , ,Description of Goods.," + subGroupDesc;
				HSSFRow ThirdRowheader = sheet.createRow(2);
				data = thrdRow.split(",");
				for (int i = 0; i < data.length; i++) {
					ThirdRowheader.createCell(i).setCellValue(data[i]);
					if (i == 4) {
						ThirdRowheader.getCell(i).setCellStyle(style);
					} else {
						ThirdRowheader.getCell(i).setCellStyle(styleNormlfont);
					}

				}
				sheet.addMergedRegion(new CellRangeAddress(// //Merging col 0to1
															// and row 2
						2, // first row (0-based)
						2, // last row (0-based)
						0, // first column (0-based)
						1 // last column (0-based)
				));

				// //Fourth Row///
				String fouthRow = "CH No., ," + chp;
				HSSFRow fouthRowHeader = sheet.createRow(3);
				data = fouthRow.split(",");
				for (int i = 0; i < data.length; i++) {
					fouthRowHeader.createCell(i + 4).setCellValue(data[i]);
					if (i == 0) {
						fouthRowHeader.getCell(i + 4).setCellStyle(style);
					} else {
						fouthRowHeader.getCell(i + 4).setCellStyle(styleNormlfont);
					}

				}
				// /////Fifth Row/////////
				String fifthRow = "ECC No,AAGCM7687NEM001 , ,  ,From, , " + fromDate + " to " + toDate + " ";
				HSSFRow fifthRowHeader = sheet.createRow(4);
				data = fifthRow.split(",");
				for (int i = 0; i < data.length; i++) {
					fifthRowHeader.createCell(i).setCellValue(data[i]);
					if (i == 0 || i == 4) {
						fifthRowHeader.getCell(i).setCellStyle(style);
					} else {
						fifthRowHeader.getCell(i).setCellStyle(styleNormlfont);
					}

				}

				// //////SixhRow/////////
				String sixthRow = "C.Ex.Regn.No.,AAGCM7687NEM001";
				HSSFRow sixthRowHeader = sheet.createRow(5);
				data = sixthRow.split(",");
				for (int i = 0; i < data.length; i++) {
					sixthRowHeader.createCell(i).setCellValue(data[i]);
					if (i == 0 || i == 6) {
						sixthRowHeader.getCell(i).setCellStyle(style);
					} else {
						sixthRowHeader.getCell(i).setCellStyle(styleNormlfont);
					}
				}
				// ///////Table Header////
				String tableData = "Date,Opening Balance,Qty MFG,Total,Removal From Factory, , , , , , , ,For Other Purpose, ,Excise Duty,,Closing Balance,Loose Qty,Remarks, ,Sign";
				HSSFRow tableHeader = sheet.createRow(6);
				String[] tblfirstRowData = tableData.split(",");
				for (int i = 0; i < tblfirstRowData.length; i++) {
					tableHeader.createCell(i).setCellValue(tblfirstRowData[i]);
					tableHeader.getCell(i).setCellStyle(styleOfAligment);
				}

				// ///////////table header 2nd row/////////
				String tblscndRow = " , , , , On Payment Duty, , , ,Without Payment of Duty, , , ,Purpose,Qty,Rate,Duty Am, , ,Invoice/Voucher No,Date, ";
				tableHeader = sheet.createRow(7);
				data = tblscndRow.split(",");
				for (int i = 0; i < data.length; i++) {
					tableHeader.createCell(i).setCellValue(data[i]);
					tableHeader.getCell(i).setCellStyle(styleOfAligment);
				}

				// ///////////table header 3rd row/////////
				String tblthirdRow = " , , , ,For Home Consumption,,Export,,For Export Bound,,To Othe Factory/Bond, , , , , , , , ,  ";
				tableHeader = sheet.createRow(8);
				data = tblthirdRow.split(",");
				for (int i = 0; i < data.length; i++) {
					tableHeader.createCell(i).setCellValue(data[i]);

					tableHeader.getCell(i).setCellStyle(styleOfAligment);
				}
				int col = 0;
				for (int i = 0; i < 4; i++) {

					sheet.addMergedRegion(new CellRangeAddress(// //Merging col
																// 4to9 and row
																// 8
							8, // first row (0-based)
							8, // last row (0-based)
							4 + col, // first column (0-based)
							5 + col // last column (0-based)

					));
					col = col + 2;
				}
				//
				// ///////////table header4th row/////////
				String tblfouthRow = " , , , ,Qty,Value,Qty,Value,Qty,Value,Qty,Value, , , , , , , , , ";
				tableHeader = sheet.createRow(9);
				data = tblfouthRow.split(",");
				for (int i = 0; i < data.length; i++) {
					tableHeader.createCell(i).setCellValue(data[i]);
					tableHeader.getCell(i).setCellStyle(styleOfAligment);
				}

				// /////////////Start Merging First row of table header////////
				for (int i = 0; i < 4; i++)// /////////
				{
					sheet.addMergedRegion(new CellRangeAddress(// //Merging row
																// 6to9and col
																// 1,2,2,4 Table
																// header
							6, // first row (0-based)
							9, // last row (0-based)
							i, // first column (0-based)
							i // last column (0-based)
					));
				}

				sheet.addMergedRegion(new CellRangeAddress(// /Merging col 4to
															// 11 and row no is
															// 6
						6, // first row (0-based)
						6, // last row (0-based)
						4, // first column (0-based)
						11 // last column (0-based)
				));

				sheet.addMergedRegion(new CellRangeAddress(// /Merging col 12to
															// 13 and row no is
															// 6
						6, // first row (0-based)
						6, // last row (0-based)
						12, // first column (0-based)
						13 // last column (0-based)
				));

				sheet.addMergedRegion(new CellRangeAddress(// Merging col 14to
															// 15 and row no is
															// 6
						6, // first row (0-based)
						6, // last row (0-based)
						14, // first column (0-based)
						15 // last column (0-based)
				));

				sheet.addMergedRegion(new CellRangeAddress(// Merging row 6 to 9
															// and col 16
						6, // first row (0-based)
						9, // last row (0-based)
						16, // first column (0-based)
						16 // last column (0-based)
				));

				sheet.addMergedRegion(new CellRangeAddress(// Merging row 6 to 9
															// and col 17
						6, // first row (0-based)
						9, // last row (0-based)
						17, // first column (0-based)
						17 // last column (0-based)
				));

				sheet.addMergedRegion(new CellRangeAddress(// Merging col 18 to
															// 19 and row 6
						6, // first row (0-based)
						6, // last row (0-based)
						18, // first column (0-based)
						19 // last column (0-based)
				));

				sheet.addMergedRegion(new CellRangeAddress(// Merging row 6 to 9
															// and col 20
						6, // first row (0-based)
						9, // last row (0-based)
						20, // first column (0-based)
						20 // last column (0-based)
				));

				// ////////End Merging First row of table header///

				// ////////strt Merging scnd row of table header///
				sheet.addMergedRegion(new CellRangeAddress(// Merging col 4th to
															// 7 and row no 7
						7, // first row (0-based)
						7, // last row (0-based)
						4, // first column (0-based)
						7 // last column (0-based)
				));
				sheet.addMergedRegion(new CellRangeAddress(// Merging col 8 to
															// 11 and row no 7
						7, // first row (0-based)
						7, // last row (0-based)
						8, // first column (0-based)
						11 // last column (0-based)
				));

				for (int i = 0; i < 4; i++) {
					sheet.addMergedRegion(new CellRangeAddress(// Merging row 7
																// to 9 and col
																// no 12,13,1415
							7, // first row (0-based)
							9, // last row (0-based)
							12 + i, // first column (0-based)
							12 + i // last column (0-based)
					));
				}

				sheet.addMergedRegion(new CellRangeAddress(// Merging row 7 to 9
															// and col no 18
						7, // first row (0-based)
						9, // last row (0-based)
						18, // first column (0-based)
						18 // last column (0-based)
				));
				sheet.addMergedRegion(new CellRangeAddress(// Merging row 7 to 9
															// and col no 19
						7, // first row (0-based)
						9, // last row (0-based)
						19, // first column (0-based)
						19 // last column (0-based)
				));

				// ////////End Merging scnd row of table header

				String tblfifthRow = "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21";
				tableHeader = sheet.createRow(10);
				String[] dataNo = tblfifthRow.split(",");
				for (int i = 0; i < dataNo.length; i++) {
					tableHeader.createCell(i).setCellValue(dataNo[i]);
					tableHeader.getCell(i).setCellStyle(styleOfAligment);
				}

				ArrayList<clsRG1DailyStockAccountReportDtl> listAllChaptorWiseRG1Rows = new ArrayList<clsRG1DailyStockAccountReportDtl>();

				for (clsRG1DailyStockAccountReportDtl objRG : listOBjModel) {
					List dataList = new ArrayList();
					dataList.add(objRG.getDateInv());
					// dataList.add(objRG.getStrProdCode());
					// dataList.add(objRG.getStrProdName());
					dataList.add(objRG.getDblOpeningStk());
					dataList.add(objRG.getDblMfgQTY());
					dataList.add(objRG.getDblTotalQty());
					dataList.add(objRG.getDblInvQty());
					dataList.add(objRG.getDblAssValue());
					dataList.add(objRG.getDblExportQty());
					dataList.add(objRG.getDblExportValue());
					dataList.add(objRG.getDblExportQtyBound());
					dataList.add(objRG.getDblExportBoundValue());

					dataList.add(objRG.getDblExportQtyFactoryBound());
					dataList.add(objRG.getDblExportFactoryBoundValue());
					dataList.add(objRG.getPurpose());
					dataList.add(objRG.getDblpurposeQty());
					dataList.add(objRG.getExTaxPer());
					dataList.add(objRG.getDblExTaxAmt());
					dataList.add(objRG.getDblclosingBalance());
					dataList.add(objRG.getDblloseQty());
					dataList.add(objRG.getVoucherNo());
					dataList.add(objRG.getDateInvRemark());
					listStock.add(dataList);
				}

				// String SqlchaptoerNo =
				// "select a.strExciseChapter from tblsubgroupmaster a "
				// +
				// " where a.strClientCode='"+clientCode+"' group by a.strExciseChapter";
				// List listchaptoerNo =
				// objGlobalFunctionsService.funGetDataList(SqlchaptoerNo,"sql");
				//
				// List listStock = new ArrayList();
				//
				// ArrayList<String> arrChaptorNo=new ArrayList<String>();
				// for(int k=0; k<listchaptoerNo.size();k++)
				// {
				// String chp=(String) listchaptoerNo.get(k);
				// if(chp.trim().length()>1)
				// {

				// String
				// sql="select DATE_FORMAT(f.dteInvDate,'%d/%m/%Y') as dateInv,a.strProdCode,a.strProdName,a.dblOpeningStk ,sum(e.dblQty) as mfgQTY , "
				// +
				// "(a.dblOpeningStk + sum(e.dblQty)) as TotalQty, sum(g.dblQty) as invQty,sum(g.dblAssValue) as assValue, "
				// +
				// " '0.00' as ExportQty ,'' as ExportValue, '0.00' as ExportQtyBound ,'' as ExportBoundValue, '0.00' as ExportQtyFactoryBound ,"
				// +
				// "'' as ExportFactoryBoundValue, '' as purpose ,'0.00' as purposeQty,"
				// +
				// "'exRate','exAmt', ((a.dblOpeningStk + sum(e.dblQty))-sum(g.dblQty)) as closingBalance,"
				// +
				// " '0.00' as loseQty,'invCodes' as voucherNo,DATE_FORMAT(f.dteInvDate,'%d/%m/%Y') as dateInv "
				// +
				// " from tblcurrentstock a ,tblsubgroupmaster b,tblproductmaster c, tblstocktransferhd d,tblstocktransferdtl e ,"
				// +
				// " tblinvoicehd f,tblinvoicedtl g where a.strProdCode=c.strProdCode "
				// +
				// " and a.strLocCode='"+locCode+"' and a.strUserCode='"+userCode+"' "
				// +
				// " and c.strSGCode=b.strSGCode and b.strExciseChapter='"+chp+"' "
				// +
				// " and d.strSTCode=e.strSTCode and a.strProdCode=e.strProdCode "
				// + " and d.strToLocCode='"+locCode+"'  and d.dtSTDate "
				// +
				// " between '"+fromDate+"' and '"+toDate+"' and f.strInvCode=g.strInvCode "
				// + " and a.strProdCode=g.strProdCode and f.dteInvDate "
				// + " between '"+fromDate+"' and '"+toDate+"' "
				// + " group by e.strProdCode";
				//
				//
				// List listProdDtl =
				// objGlobalFunctionsService.funGetDataList(sql,"sql");
				// for (int j = 0; j < listProdDtl.size(); j++)
				// {
				// List dataList = new ArrayList();
				// Object[] obj = (Object[]) listProdDtl.get(j);
				//
				// dataList.add(obj[0].toString());
				// dataList.add(obj[3].toString());
				// dataList.add(obj[4].toString());
				// dataList.add(obj[5].toString());
				// dataList.add(obj[6].toString());
				// dataList.add(obj[7].toString());
				// dataList.add(obj[8].toString());
				// dataList.add(obj[9].toString());
				// dataList.add(obj[10].toString());
				// dataList.add(obj[11].toString());
				// dataList.add(obj[12].toString());
				//
				// String
				// sqlInvExTaxes="select a.strInvCode,d.dblPercent,c.dblTaxAmt from tblinvoicehd a, tblinvoicedtl b,tblinvtaxdtl c,tbltaxhd d "
				// + " where a.strInvCode=b.strInvCode and "
				// +
				// " a.strInvCode = c.strInvCode and d.strTaxCode=c.strTaxCode "
				// + " and d.strExcisable='Y' "
				// +
				// " and a.dteInvDate= '"+obj[3].toString()+"' and b.strProdCode='"+obj[2].toString()+"' ";
				// List listInvTaxes =
				// objGlobalFunctionsService.funGetDataList(sqlInvExTaxes,"sql");
				// String invExTax="";
				// double invExTaxAmt=0.00;
				// for (int incnt = 0; incnt < listInvTaxes.size(); incnt++)
				// {
				// Object[] objTax = (Object[]) listInvTaxes.get(incnt);
				// invExTax+= objTax[0].toString();
				// invExTaxAmt+= Double.parseDouble(objTax[1].toString());
				// }
				// dataList.add(invExTax);
				// dataList.add(invExTaxAmt);
				//
				// dataList.add(obj[15].toString());
				// dataList.add(obj[16].toString());
				// dataList.add(obj[17].toString());
				// dataList.add(obj[18].toString());
				// dataList.add(obj[19].toString());
				//
				// String
				// sqlInvCode="select a.strInvCode from tblinvoicehd a, tblinvoicedtl b where a.strInvCode=b.strInvCode "
				// +
				// " and a.dteInvDate = '"+obj[3].toString()+"'  and b.strProdCode='"+obj[2].toString()+"' ";
				// List listInvCode =
				// objGlobalFunctionsService.funGetDataList(sqlInvCode,"sql");
				// String invCodes="";
				// for (int incnt = 0; incnt < listInvCode.size(); incnt++)
				// {
				// invCodes+=(String) listInvCode.get(incnt);
				// }
				// dataList.add(invCodes); //20 columns
				//
				// dataList.add(obj[21].toString());
				// listStock.add(dataList);
				//
				// }

				// /Start Dtl data of Table

				int ColrowCount = 11;// starting row no of Dtl data
				for (int rowCount = 0; rowCount < listStock.size(); rowCount++) {
					HSSFRow aRow = sheet.createRow(ColrowCount++);
					List arrObj = (List) listStock.get(rowCount);
					for (int Count = 0; Count < arrObj.size(); Count++) {
						if (null != arrObj.get(Count) && arrObj.get(Count).toString().length() > 0) {

							if (isNumeric(arrObj.get(Count).toString())) {
								aRow.createCell(Count).setCellValue(Double.parseDouble(arrObj.get(Count).toString()));
							} else {
								aRow.createCell(Count).setCellValue(arrObj.get(Count).toString());
							}
						} else {
							aRow.createCell(Count).setCellValue("");
						}
					}

				}

			}

		}
		//
		workbook.write(response.getOutputStream());

	}

	public static boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?"); // match a number with optional
												// '-' and decimal.
	}

}
