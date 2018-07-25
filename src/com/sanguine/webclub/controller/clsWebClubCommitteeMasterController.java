package com.sanguine.webclub.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.bean.clsPOSItemMasterImportBean;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webclub.bean.clsWebClubCommitteeMasterBean;
import com.sanguine.webclub.model.clsWebClubCommitteeMasterDtl;
import com.sanguine.webclub.model.clsWebClubCommitteeMasterModel;
import com.sanguine.webclub.model.clsWebClubCommitteeMasterModel_ID;
import com.sanguine.webclub.model.clsWebClubCommitteeMemberRoleMasterModel;
import com.sanguine.webclub.model.clsWebClubPreMemberProfileModel;
import com.sanguine.webclub.model.clsWebClubPreMemberProfileModel_ID;
import com.sanguine.webclub.service.clsWebClubCommitteeMasterService;
import com.sanguine.webclub.service.clsWebClubCommitteeMemberRoleMasterService;

@Controller
public class clsWebClubCommitteeMasterController {

	@Autowired
	private clsWebClubCommitteeMasterService objWebClubCommitteeMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@Autowired
	private clsWebClubCommitteeMemberRoleMasterService objCommitteeMemberRoleService;

	private clsGlobalFunctions objGlobal = null;

	// Open WebClubCommitteeMaster
	@RequestMapping(value = "/frmWebClubCommitteeMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		String sqlRoleDesc = "select strRoleDesc from tblcommitteememberrolemaster where strClientCode='" + clientCode + "' ";
		List listType = objGlobalFunctionsService.funGetDataList(sqlRoleDesc, "sql");
		if (listType.size() > 0) {
			model.put("listRoleDesc", listType);
		}

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWebClubCommitteeMaster_1", "command", new clsWebClubCommitteeMasterBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWebClubCommitteeMaster", "command", new clsWebClubCommitteeMasterBean());
		} else {
			return null;
		}

	}

	// Load Master Data On Form
	@RequestMapping(value = "/frmWebClubCommitteeMaster1", method = RequestMethod.POST)
	public @ResponseBody clsWebClubCommitteeMasterModel funLoadMasterData(HttpServletRequest request) {
		objGlobal = new clsGlobalFunctions();
		String sql = "";
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		clsWebClubCommitteeMasterBean objBean = new clsWebClubCommitteeMasterBean();
		String docCode = request.getParameter("docCode").toString();
		List listModel = objGlobalFunctionsService.funGetList(sql);
		clsWebClubCommitteeMasterModel objWebClubCommitteeMaster = new clsWebClubCommitteeMasterModel();
		return objWebClubCommitteeMaster;
	}

	// Save or Update WebClubCommitteeMaster
	@RequestMapping(value = "/saveWebClubCommitteeMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsWebClubCommitteeMasterBean objBean, BindingResult result, HttpServletRequest req) {
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propCode = req.getSession().getAttribute("propertyCode").toString();
			clsWebClubCommitteeMasterModel objModel = funPrepareHdModel(objBean, userCode, clientCode, req);
			// clsWebClubCommitteeMasterDtl objModelDtl
			// =funPrepareDtlModel(objBean,userCode,objModel.getStrCommitteeCode(),clientCode,req);

			objWebClubCommitteeMasterService.funAddUpdateWebClubCommitteeMaster(objModel);
			for (clsWebClubCommitteeMasterDtl obj : objBean.getListCommitteeMasterDtl()) {
				clsWebClubCommitteeMasterDtl objModelDtl = new clsWebClubCommitteeMasterDtl();
				objModelDtl.setStrCommitteeCode(objModel.getStrCommitteeCode());
				objModelDtl.setIntId(Integer.parseInt(objModel.getStrCommitteeCode()));
				objModelDtl.setStrMemberCode(obj.getStrMemberCode());
				objModelDtl.setStrMemberName(obj.getStrMemberName());

				clsWebClubCommitteeMemberRoleMasterModel objrole = objCommitteeMemberRoleService.funGetWebClubCommitteeMemberRoleName(obj.getStrRoleName(), clientCode);
				objModelDtl.setStrRoleCode(objrole.getStrRoleCode());
				objModelDtl.setStrPropertyCode(propCode);
				objModelDtl.setStrClientCode(clientCode);
				objWebClubCommitteeMasterService.funAddUpdateCommittteeMasterDtl(objModelDtl);
			}

			return new ModelAndView("redirect:/frmWebClubCommitteeMaster.html");
		} else {
			return new ModelAndView("frmWebClubCommitteeMaster");
		}
	}

	// Convert bean to model function
	private clsWebClubCommitteeMasterModel funPrepareHdModel(clsWebClubCommitteeMasterBean objBean, String userCode, String clientCode, HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();

		clsWebClubCommitteeMasterModel objModel;

		String propCode = req.getSession().getAttribute("propertyCode").toString();
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;

		if (objBean.getStrCommitteeCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tblcommitteemaster", "CommitteeMaster", "strCommitteeCode", clientCode);
			String committeeCode = String.format("%08d", lastNo);
			objModel = new clsWebClubCommitteeMasterModel(new clsWebClubCommitteeMasterModel_ID(committeeCode, clientCode));
			objModel.setStrUserCreated(userCode);
			objModel.setStrUserModified(userCode);
			objModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			objModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

		} else {

			clsWebClubCommitteeMasterModel objComModele = objWebClubCommitteeMasterService.funGetWebClubCommitteeMaster(objBean.getStrCommitteeCode(), clientCode);
			if (null == objComModele) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblcommitteemaster", "CommitteeMaster", "intId", clientCode);
				String committeeCode = String.format("%08d", lastNo);
				objModel = new clsWebClubCommitteeMasterModel(new clsWebClubCommitteeMasterModel_ID(committeeCode, clientCode));
				objModel.setStrUserCreated(userCode);
				objModel.setStrUserModified(userCode);
				objModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

			} else {
				objModel = new clsWebClubCommitteeMasterModel(new clsWebClubCommitteeMasterModel_ID(objBean.getStrCommitteeCode(), clientCode));
				objModel.setStrUserModified(userCode);
				objModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			}
		}

		objModel.setStrCommitteeName(objBean.getStrCommitteeName());
		objModel.setStrPropertyID(propCode);
		objModel.setStrType("");
		return objModel;

	}

	private clsWebClubCommitteeMasterDtl funPrepareDtlModel(clsWebClubCommitteeMasterBean objBean, String userCode, String strCommitteeCode, String clientCode, HttpServletRequest req) {
		clsWebClubCommitteeMasterDtl objModelDtl = new clsWebClubCommitteeMasterDtl();
		for (clsWebClubCommitteeMasterDtl obj : objBean.getListCommitteeMasterDtl()) {
			objModelDtl.setStrCommitteeCode(strCommitteeCode);
		}

		return objModelDtl;

	}

}
