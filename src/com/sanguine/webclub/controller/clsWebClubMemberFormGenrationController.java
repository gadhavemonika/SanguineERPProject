package com.sanguine.webclub.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webclub.bean.clsWebClubMemberFormGenrationBean;
import com.sanguine.webclub.model.clsWebClubMemberFormGenerationModel;
import com.sanguine.webclub.model.clsWebClubMemberFormGenerationModel_ID;
import com.sanguine.webclub.service.clsWebClubMemberFormGenerationService;

@Controller
public class clsWebClubMemberFormGenrationController {
	@Autowired
	private clsWebClubMemberFormGenerationService objMemFormGenService;
	// private clsMemberFormGenerationService objMemFormGenService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	clsGlobalFunctions objGlobal;
	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	// Open MemberProfile
	@RequestMapping(value = "/frmMembershipFormGenration", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmMembershipFormGenration_1", "command", new clsWebClubMemberFormGenrationBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmMembershipFormGenration", "command", new clsWebClubMemberFormGenrationBean());
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/saveMembershipFormGenration", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsWebClubMemberFormGenrationBean memFormGenBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		if (!result.hasErrors()) {

			clsWebClubMemberFormGenerationModel objMemFormGenModel = funPrepareModel(memFormGenBean, req);
			objMemFormGenService.funAddUpdateWebClubMemberFormGeneration(objMemFormGenModel);

			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Form No : " + objMemFormGenModel.getStrFormNo());
			return new ModelAndView("redirect:/frmMembershipFormGenration.html?saddr=" + urlHits);
		}
		return new ModelAndView("redirect:/frmMembershipFormGenration.html?saddr=" + urlHits);

	}

	@RequestMapping(value = "/loadMemberFormNoData", method = RequestMethod.GET)
	public @ResponseBody clsWebClubMemberFormGenerationModel funAssignFields(@RequestParam("formNo") String strFormNo, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsWebClubMemberFormGenerationModel objMemFormGen = objMemFormGenService.funGetWebClubMemberFormGeneration(strFormNo, clientCode);
		if (null == objMemFormGen) {
			objMemFormGen = new clsWebClubMemberFormGenerationModel();
			objMemFormGen.setStrFormNo("invalid Code");
		}

		return objMemFormGen;
	}

	private clsWebClubMemberFormGenerationModel funPrepareModel(clsWebClubMemberFormGenrationBean memFormGenBean, HttpServletRequest req) {
		// TODO Auto-generated method stub
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		objGlobal = new clsGlobalFunctions();
		long lastNo = 1;

		clsWebClubMemberFormGenerationModel meberFormGen;
		if (memFormGenBean.getStrFormNo().trim().length() == 0) {
			//
			lastNo = objGlobalFunctionsService.funGetLastNo("tblmemberformgeneration", "FormNo", "intFormNo", clientCode);
			String strFormNo = String.format("%07d", lastNo);
			meberFormGen = new clsWebClubMemberFormGenerationModel(new clsWebClubMemberFormGenerationModel_ID(strFormNo, clientCode));
			meberFormGen.setIntGId(lastNo);
			meberFormGen.setStrUserCreated(userCode);
			meberFormGen.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			clsWebClubMemberFormGenerationModel objMemGen = objMemFormGenService.funGetWebClubMemberFormGeneration(memFormGenBean.getStrFormNo(), clientCode);
			if (null == objMemGen) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblmemberformgeneration", "FormNo", "intFormNo", clientCode);
				String intFormNo = String.format("%07d", lastNo);
				meberFormGen = new clsWebClubMemberFormGenerationModel(new clsWebClubMemberFormGenerationModel_ID(intFormNo, clientCode));
				meberFormGen.setIntGId(lastNo);
				meberFormGen.setStrUserCreated(userCode);
				meberFormGen.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			} else {
				meberFormGen = new clsWebClubMemberFormGenerationModel(new clsWebClubMemberFormGenerationModel_ID(memFormGenBean.getStrFormNo(), clientCode));
				meberFormGen.setDteCreatedDate(objMemGen.getDteCreatedDate());
				meberFormGen.setStrUserCreated(objMemGen.getStrUserCreated());
			}

		}
		meberFormGen.setIntGId(Integer.parseInt(meberFormGen.getStrFormNo()));
		meberFormGen.setStrPrint("Y");
		meberFormGen.setStrRePrint("Y");
		meberFormGen.setStrMemberCode("");
		meberFormGen.setIntReprintCount(0);

		meberFormGen.setStrPropertyCode(propCode);
		meberFormGen.setDtePrintDate(memFormGenBean.getDteFormIssue());
		meberFormGen.setStrProspectName(memFormGenBean.getStrProspectName().toUpperCase());
		meberFormGen.setStrCategoryCode("");
		meberFormGen.setStrUserModified(userCode);
		meberFormGen.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		return meberFormGen;
	}

}
