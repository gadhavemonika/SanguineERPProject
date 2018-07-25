package com.sanguine.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.bean.clsRouteMasterBean;
import com.sanguine.bean.clsTransporterBean;
import com.sanguine.model.clsRouteMasterModel;
import com.sanguine.model.clsRouteMasterModel_ID;
import com.sanguine.model.clsTransporterHdModel;
import com.sanguine.model.clsTransporterHdModel_ID;
import com.sanguine.model.clsTransporterModelDtl;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsTransporterMasterService;

@Controller
public class clsTransporterMasterController {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsTransporterMasterService objTransporterMasterService;

	clsGlobalFunctions objGlobal;

	@RequestMapping(value = "/frmTransporterMaster", method = RequestMethod.GET)
	public ModelAndView funOpenTransporterMaster(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmTransporterMaster_1", "command", new clsTransporterBean());
		} else {
			return new ModelAndView("frmTransporterMaster", "command", new clsTransporterBean());
		}

	}

	@RequestMapping(value = "/saveTransporter", method = RequestMethod.GET)
	public @ResponseBody clsTransporterBean funSaveTransporterMaster(@RequestParam("transName") String transName, @RequestParam("desc") String desc, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		String strTransCode = "";
		try {
			strTransCode = request.getParameter("transCode").toString();
		} catch (Exception e) {
			strTransCode = "";
		}

		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsTransporterBean obj = new clsTransporterBean();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		clsTransporterHdModel objHdModel = new clsTransporterHdModel();
		if (strTransCode.equals("")) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tbltransportermaster", "TranporterMaster", "intId", clientCode);
			String transCode = "T" + String.format("%06d", lastNo);
			objHdModel = new clsTransporterHdModel(new clsTransporterHdModel_ID(transCode, clientCode));
			objHdModel.setStrTransCode(transCode);
			objHdModel.setIntId(lastNo);
			objHdModel.setStrUserCreated(userCode);
			objHdModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

		} else {
			objHdModel = objTransporterMasterService.funGetTransporterMaster(strTransCode, clientCode);

		}
		objHdModel.setStrUserModified(userCode);
		objHdModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

		objHdModel.setStrTransName(transName);
		objHdModel.setStrDesc(desc);
		objHdModel.setStrClientCode(clientCode);
		String retResp = "";
		boolean flg = objTransporterMasterService.funAddUpdateHd(objHdModel);
		if (flg) {
			obj.setStrTransCode("Successfully Created Transporter " + objHdModel.getStrTransCode());
		} else {
			// retResp="Invalid Data";
			obj.setStrTransCode("Invalid Data");
		}
		return obj;
	}

	@RequestMapping(value = "/saveTransporterVehicle", method = RequestMethod.GET)
	public ModelAndView funSaveTransporterMaster(clsTransporterBean objBean, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		objGlobal = new clsGlobalFunctions();

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		clsTransporterHdModel objTransporterHdModel = funPrepareModel(objBean, userCode, clientCode);
		objTransporterMasterService.funAddUpdateHd(objTransporterHdModel);

		request.getSession().setAttribute("success", true);
		request.getSession().setAttribute("successMessage", " Transporter and Vehicle are Linked : ".concat(objTransporterHdModel.getStrTransCode()));
		return new ModelAndView("redirect:/frmTransporterMaster.html?saddr=" + urlHits);

	}

	// Convert bean to model function
	private clsTransporterHdModel funPrepareModel(clsTransporterBean objBean, String userCode, String clientCode) {
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsTransporterHdModel objModel = null;

		if (objBean.getStrTransCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tbltransportermaster", "transportMaster", "intId", clientCode);
			String docCode = "TR" + String.format("%06d", lastNo);
			objModel = new clsTransporterHdModel(new clsTransporterHdModel_ID(docCode, clientCode));
			objModel.setIntId(lastNo);
			objModel.setStrUserCreated(userCode);
			objModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			clsTransporterHdModel objTrasnModel = objTransporterMasterService.funGetTransporterMaster(objBean.getStrTransCode(), clientCode);
			if (null == objTrasnModel) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tbltransportermaster", "transportMaster", "intId", clientCode);
				String docCode = "TR" + String.format("%06d", lastNo);
				objModel = new clsTransporterHdModel(new clsTransporterHdModel_ID(docCode, clientCode));
				objModel.setIntId(lastNo);
				objModel.setStrUserCreated(userCode);
				objModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			} else {
				objModel = new clsTransporterHdModel(new clsTransporterHdModel_ID(objBean.getStrTransCode(), clientCode));
			}
		}
		objModel.setStrTransName(objBean.getStrTransName().toUpperCase());
		objModel.setStrDesc(objBean.getStrDesc());
		objModel.setStrUserModified(userCode);
		objModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		List<clsTransporterModelDtl> listTransporterDtl = objBean.getListclsTransporterModelDtl();
		objModel.setListTransportDtlModel(listTransporterDtl);

		return objModel;
	}

	// Load Master Data On Form
	@RequestMapping(value = "/LoadTransporterMaster", method = RequestMethod.GET)
	public @ResponseBody clsTransporterBean funLoadMasterData(HttpServletRequest request) {
		objGlobal = new clsGlobalFunctions();
		String sql = "";
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String docCode = request.getParameter("docCode").toString();
		clsTransporterBean objTransBean = new clsTransporterBean();
		clsTransporterHdModel objHdModel = objTransporterMasterService.funGetTransporterMaster(docCode, clientCode);
		objTransBean.setStrTransCode(objHdModel.getStrTransCode());
		objTransBean.setStrTransName(objHdModel.getStrTransName());
		objTransBean.setStrDesc(objHdModel.getStrDesc());

		List listDtl = objTransporterMasterService.funGetTransporterMasterDtl(docCode, clientCode);
		List<clsTransporterModelDtl> listDtlModel = new ArrayList();
		for (int i = 0; i < listDtl.size(); i++) {
			clsTransporterModelDtl objModelDtl = new clsTransporterModelDtl();
			Object[] objDtlData = (Object[]) listDtl.get(i);
			objModelDtl.setStrVehCode(objDtlData[0].toString());
			objModelDtl.setStrVehNo(objDtlData[1].toString());
			listDtlModel.add(objModelDtl);
		}
		objTransBean.setListclsTransporterModelDtl(listDtlModel);
		return objTransBean;

	}

}
