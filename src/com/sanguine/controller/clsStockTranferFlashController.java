package com.sanguine.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.bean.clsStockFlashBean;
import com.sanguine.model.clsStockFlashModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsGroupMasterService;
import com.sanguine.service.clsStockFlashService;
import com.sanguine.util.clsReportBean;

@Controller
public class clsStockTranferFlashController {
	@Autowired
	private clsGlobalFunctionsService objGlobalService;
	@Autowired
	private clsStockFlashService objStkFlashService;
	@Autowired
	clsGlobalFunctions objGlobal;
	@Autowired
	private clsGroupMasterService objGrpMasterService;

	@RequestMapping(value = "/frmStockTransferFlash", method = RequestMethod.GET)
	private ModelAndView funLoadPropertySelection(@ModelAttribute("command") clsReportBean objPropBean, BindingResult result, HttpServletRequest req, Map<String, Object> model) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		return funGetModelAndView(req);
	}

	@RequestMapping(value = "/frmStockTransferFlashDetailReport", method = RequestMethod.GET)
	private @ResponseBody List funShowStockTransferData(@RequestParam(value = "param1") String param1, @RequestParam(value = "fDate") String fDate, @RequestParam(value = "tDate") String tDate, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String[] spParam1 = param1.split(",");
		String fromLocCode = spParam1[1];
		String toLocCode = spParam1[2];

		String fromDate = objGlobal.funGetDate("yyyy-MM-dd", fDate);
		String toDate = objGlobal.funGetDate("yyyy-MM-dd", tDate);

		String sql = " select b.strProdCode,c.strProdName,sum(b.dblQty) from tblstocktransferhd a,tblstocktransferdtl b,tblproductmaster c " + " where a.strSTCode=b.strSTCode " + " and a.strFromLocCode='" + fromLocCode + "' and a.strToLocCode='" + toLocCode + "' " + " and Date(a.dtSTDate) between '" + fromDate + "' and '" + toDate + "'  " + " and b.strProdCode=c.strProdCode "
				+ " group by b.strProdCode " + " order by c.strProdName ";

		System.out.println(sql);
		List list = objGlobalService.funGetList(sql);
		List<clsStockFlashModel> listStockTransferFlashModel = new ArrayList<clsStockFlashModel>();

		for (int cnt = 0; cnt < list.size(); cnt++) {
			Object[] arrObj = (Object[]) list.get(cnt);
			clsStockFlashModel objStkTransferFlashModel = new clsStockFlashModel();
			objStkTransferFlashModel.setStrProdCode(arrObj[0].toString());
			objStkTransferFlashModel.setStrProdName(arrObj[1].toString());
			objStkTransferFlashModel.setDblIssue(arrObj[2].toString());
			listStockTransferFlashModel.add(objStkTransferFlashModel);

		}

		ModelAndView objModelView = funGetModelAndView(req);
		objModelView.addObject("stkFlashList", listStockTransferFlashModel);

		List stkFlashList = new ArrayList();
		stkFlashList.add(listStockTransferFlashModel);

		return stkFlashList;
	}

	@SuppressWarnings("unchecked")
	private ModelAndView funGetModelAndView(HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		// String usercode=req.getSession().getAttribute("usercode").toString();
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		ModelAndView objModelView = null;
		if ("2".equalsIgnoreCase(urlHits)) {
			objModelView = new ModelAndView("frmStockTransferFlash_1");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			objModelView = new ModelAndView("frmStockTransferFlash");
		}
		// Map<String, String> mapProperty=
		// objGlobalService.funGetPropertyList(clientCode);
		// if(mapProperty.isEmpty())
		// {
		// mapProperty.put("", "");
		// }
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		String locationCode = req.getSession().getAttribute("locationCode").toString();

		// objModelView.addObject("propertyCode",propertyCode);
		objModelView.addObject("LoggedInProp", propertyCode);
		// objModelView.addObject("LoggedInLoc",locationCode);

		HashMap<String, String> mapFromLocation = (HashMap<String, String>) objGlobalService.funGetLocationList(propertyCode, clientCode);
		if (mapFromLocation.isEmpty()) {
			mapFromLocation.put("", "");
		}
		mapFromLocation = clsGlobalFunctions.funSortByValues(mapFromLocation);
		objModelView.addObject("listFromLocation", mapFromLocation);
		objModelView.addObject("listToLocation", mapFromLocation);
		// Map<String,String>
		// mapGroup=objGrpMasterService.funGetGroups(clientCode);
		// mapGroup.put("ALL", "ALL");
		// Map<String,String> mapSubGroup=new HashMap<String, String>();
		// mapSubGroup.put("ALL", "ALL");
		// objModelView.addObject("listSubGroup",mapSubGroup);
		// objModelView.addObject("listGroup",mapGroup);

		return objModelView;
	}

	private String funGetDecimalValue(String strValue) {
		String strVal = "";
		if (strValue.contains("BTL") || strValue.contains("ML")) {
			if (strValue.contains("BTL")) {
				if (strValue.contains("ML")) {
					String[] splValue = strValue.split("\\.");
					String btlValue = splValue[0];
					String mlValue = splValue[1];

					String ml = " ML";
					if (mlValue.length() > 3) {
						mlValue = mlValue.substring(0, 3);
					}
					strVal = btlValue + "." + mlValue + ml;
				} else {
					strVal = strValue;
				}

			} else {
				if (strValue.contains("ML")) {
					if (strValue.contains("ML.")) {
						String[] splMlOnlyValue = strValue.split(" ML");
						String deciVal = splMlOnlyValue[0];
						String ml = " ML";
						if (deciVal.length() > 3) {
							deciVal = deciVal.substring(0, 3);
						}

						strValue = deciVal + ml;

					}

					if (strValue.contains(".")) {
						String[] splValue = strValue.split("\\.");
						String btlValue = splValue[0];
						// String mlValue=splValue[1];

						String ml = " ML";
						if (btlValue.length() > 3) {
							btlValue = btlValue.substring(0, 3);
						}

						strVal = btlValue + ml;
					} else {
						strVal = strValue;
					}

				}
			}
			strValue = strVal;
		} else {
			strVal = strValue;
		}

		return strVal;
	}

	@RequestMapping(value = "/downloadStockTransferExcel", method = RequestMethod.GET)
	public ModelAndView downloadExcel(@RequestParam(value = "param1") String param1, @RequestParam(value = "fDate") String fDate, @RequestParam(value = "tDate") String tDate, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String[] spParam1 = param1.split(",");
		String fromLocCode = spParam1[1];
		String toLocCode = spParam1[2];

		String fromDate = objGlobal.funGetDate("yyyy-MM-dd", fDate);
		String toDate = objGlobal.funGetDate("yyyy-MM-dd", tDate);
		List listStock = new ArrayList();
		String[] ExcelHeader = { "Product Code", "Product Name", "Transfer Qty" };
		listStock.add(ExcelHeader);

		String sql = " select b.strProdCode,c.strProdName,sum(b.dblQty) from tblstocktransferhd a,tblstocktransferdtl b,tblproductmaster c " + " where a.strSTCode=b.strSTCode " + " and a.strFromLocCode='" + fromLocCode + "' and a.strToLocCode='" + toLocCode + "' " + " and Date(a.dtSTDate) between '" + fromDate + "' and '" + toDate + "'  " + " and b.strProdCode=c.strProdCode "
				+ " group by b.strProdCode " + " order by c.strProdName ";

		System.out.println(sql);
		List list = objGlobalService.funGetList(sql);
		List listStockFlashModel = new ArrayList();
		for (int cnt = 0; cnt < list.size(); cnt++) {
			Object[] arrObj = (Object[]) list.get(cnt);
			List DataList = new ArrayList<>();
			DataList.add(arrObj[0].toString());
			DataList.add(arrObj[1].toString());
			DataList.add(arrObj[2].toString());
			listStockFlashModel.add(DataList);
		}
		listStock.add(listStockFlashModel);
		// return a view which will be resolved by an excel view resolver
		return new ModelAndView("excelView", "stocklist", listStock);

	}

}
