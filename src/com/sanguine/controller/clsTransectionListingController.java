package com.sanguine.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ibm.icu.text.DecimalFormat;
import com.sanguine.bean.clsStockFlashBean;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.service.clsStockFlashService;

@Controller
public class clsTransectionListingController {
	@Autowired
	private clsGlobalFunctionsService objGlobalService;
	@Autowired
	private clsStockFlashService objStkFlashService;
	@Autowired
	clsGlobalFunctions objGlobal;

	@Autowired
	clsMISController objMIS;

	@Autowired
	clsGRNController objGRN;

	@Autowired
	clsMaterialReturnController objMatReturn;

	@Autowired
	clsStkAdjustmentController objStkAdj;

	@Autowired
	clsMaterialReqController objMatReq;

	@Autowired
	clsStkTransferController objStkTransfer;

	@Autowired
	clsProductionController objProduction;

	@Autowired
	clsPurchaseReturnController objPurRet;

	@Autowired
	clsStockController objOpStk;

	@Autowired
	clsSetupMasterService objSetupMasterService;

	@RequestMapping(value = "/frmTransactionListing", method = RequestMethod.GET)
	private ModelAndView funOpenStockLedger(@ModelAttribute("command") clsStockFlashBean objPropBean, BindingResult result, HttpServletRequest req) {
		return funGetModelAndView(req);
	}

	private ModelAndView funGetModelAndView(HttpServletRequest req) {
		ModelAndView objModelView = new ModelAndView("frmTransactionListing");
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String locationCode = req.getSession().getAttribute("locationCode").toString();
		Map<String, String> mapProperty = objGlobalService.funGetPropertyList(clientCode);
		if (mapProperty.isEmpty()) {
			mapProperty.put("", "");
		}
		objModelView.addObject("listProperty", mapProperty);
		HashMap<String, String> mapLocation = objGlobalService.funGetLocationList(propertyCode, clientCode);
		if (mapLocation.isEmpty()) {
			mapLocation.put("", "");
		}

		String sqlGetAllTransaction = "select a.strFormName from tbltreemast a where  a.strType='T'  ";
		List listTrans = new ArrayList();
		listTrans.add("Opening Stk");
		listTrans.add("MIS Out");
		listTrans.add("MIS In");
		listTrans.add("GRN");
		listTrans.add("Stock Adjustment In");
		listTrans.add("StkAdj Out");
		listTrans.add("Stock Transfer In");
		listTrans.add("Material Return In");
		listTrans.add("Material Return Out");
		listTrans.add("Production");
		listTrans.add("Purchase Return");
		listTrans.add("Sales Ret");
		listTrans.add("Delivery challan");
		listTrans.add("Invoice");
		objModelView.addObject("listTransaction", listTrans);
		objModelView.addObject("LoggedInProp", propertyCode);
		objModelView.addObject("LoggedInLoc", locationCode);

		mapLocation = clsGlobalFunctions.funSortByValues(mapLocation);
		objModelView.addObject("listLocation", mapLocation);
		return objModelView;
	}

}
