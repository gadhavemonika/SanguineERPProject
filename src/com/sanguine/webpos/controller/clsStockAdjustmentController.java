package com.sanguine.webpos.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.webpos.bean.clsPOSPSPDtl;
import com.sanguine.webpos.bean.clsPOSPhysicalStockPostingBean;
import com.sanguine.webpos.bean.clsPOSReasonMasterBean;

@Controller
public class clsStockAdjustmentController {

	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobalFunctionsController;

	@Autowired
	private ServletContext servletContext;

	Map<String, String> map = new HashMap<String, String>();
	private Map<String, clsPOSPSPDtl> hmPSPDtl = null;
	final static Logger logger = Logger.getLogger(clsStockAdjustmentController.class);
	List<clsPOSReasonMasterBean> listReason = new ArrayList<clsPOSReasonMasterBean>();

	@RequestMapping(value = "/frmPOSStkAdjustment", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String strClientCode = request.getSession().getAttribute("clientCode").toString();
		String posCode = request.getSession().getAttribute("loginPOS").toString();
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		model.put("urlHits", urlHits);
		List typeList = new ArrayList();
		typeList.add("Sale");
		typeList.add("Stock In");
		typeList.add("Stock Out");
		model.put("typeList", typeList);
		if ("2".equalsIgnoreCase(urlHits)) {

			return new ModelAndView("frmPOSStkAdjustment_1", "command", new clsPOSPhysicalStockPostingBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSStkAdjustment", "command", new clsPOSPhysicalStockPostingBean());
		} else {
			return null;
		}

	}

}
