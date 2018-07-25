package com.sanguine.excise.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
import com.sanguine.controller.clsUserController;
import com.sanguine.excise.bean.clsExciseRecipeMasterBean;
import com.sanguine.excise.model.clsBrandMasterModel;
import com.sanguine.excise.model.clsExciseRecipeMasterDtlModel;
import com.sanguine.excise.model.clsExciseRecipeMasterHdModel;
import com.sanguine.excise.service.clsExciseRecipeMasterService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsUOMService;
import com.sanguine.webpos.controller.clsPOSGlobalFunctionsController;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Controller
public class clsExciseRecipeMasterController {

	@Autowired
	private clsExciseRecipeMasterService objclsExciseRecipeMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;

	@Autowired
	private clsUOMService objclsUOMService;

	// Open BrandMaster
	@RequestMapping(value = "/frmExciseRecipeMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {

		// String
		// clientCode=request.getSession().getAttribute("clientCode").toString();

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		List listType = new ArrayList<>();
		listType.add("Text");
		listType.add("Integer");
		listType.add("List");

		List<String> uomList = new ArrayList<String>();

		model.put("uomList", uomList);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExciseRecipeMaster_1", "command", new clsExciseRecipeMasterBean());
		} else {
			return new ModelAndView("frmExciseRecipeMaster", "command", new clsExciseRecipeMasterBean());
		}
	}

	@RequestMapping(value = "/uomListMaster", method = RequestMethod.GET)
	public @ResponseBody List funUOMList(HttpServletRequest request) {

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		List ls = new ArrayList();
		List<String> uomList = new ArrayList<String>();
		uomList = objclsUOMService.funGetUOMList(clientCode);
		ls = uomList;
		return ls;
	}

	// Save or Update Transport
	@RequestMapping(value = "/saveExciseRecipeMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsExciseRecipeMasterBean objBean, BindingResult result, HttpServletRequest req) {
		boolean dtlSuccess = false;
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			clsExciseRecipeMasterHdModel objclsRecipeModel = funPrepareRecipeHdModel(objBean, userCode, clientCode);
			List<clsExciseRecipeMasterDtlModel> listRecipedtl = objBean.getObjclsExciseRecipeMasterDtlModel();
			if (null != listRecipedtl && listRecipedtl.size() > 0) {
				boolean sucess = objclsExciseRecipeMasterService.funAddUpdateRecipeMaster(objclsRecipeModel);
				if (sucess) {
					String strRecipeCode = objclsRecipeModel.getStrRecipeCode();
					objclsExciseRecipeMasterService.funDeleteDtl(strRecipeCode, clientCode);

					for (clsExciseRecipeMasterDtlModel objRecipeDtl : listRecipedtl) {
						if (objRecipeDtl.getStrBrandCode() != null) {
							if (objRecipeDtl.getDblQty() != 0.0) {
								long lastNo = objGlobalFunctionsService.funGetCount("tblexciserecipermasterdtl", "intId");
								objRecipeDtl.setStrRecipeCode(strRecipeCode);
								objRecipeDtl.setStrClientCode(clientCode);
								objRecipeDtl.setIntId(lastNo);
								dtlSuccess = objclsExciseRecipeMasterService.funAddUpdateRecipeDtl(objRecipeDtl);
							}
						}
					}

				}

			}
			if (dtlSuccess) {
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Recipe : ".concat(objclsRecipeModel.getStrRecipeCode()));
				return new ModelAndView("redirect:/frmExciseRecipeMaster.html?saddr=" + urlHits);
			} else {
				return new ModelAndView("frmExciseRecipeMaster?saddr=" + urlHits);
			}
		} else {
			return new ModelAndView("frmExciseRecipeMaster?saddr=" + urlHits);
		}
	}

	private clsExciseRecipeMasterHdModel funPrepareRecipeHdModel(clsExciseRecipeMasterBean objBean, String userCode, String clientCode) {
		objGlobal = new clsGlobalFunctions();

		long lastNo = 0;
		clsExciseRecipeMasterHdModel objModel = new clsExciseRecipeMasterHdModel();

		if (objBean != null) {
			if (objBean.getObjclsExciseRecipeMasterDtlModel() != null && objBean.getObjclsExciseRecipeMasterDtlModel().size() > 0) {
				if (!(objBean.getStrRecipeCode().isEmpty())) {
					List recipeList = objclsExciseRecipeMasterService.funGetObject(objBean.getStrRecipeCode(), clientCode);
					Object obj = (Object) recipeList.get(0);
					clsExciseRecipeMasterHdModel objModel1 = (clsExciseRecipeMasterHdModel) obj;

					if (objModel1 != null) {
						objModel.setStrRecipeCode(objModel1.getStrRecipeCode());
						objModel.setStrUserCreated(objModel1.getStrUserCreated());
						objModel.setDteDateCreated(objModel1.getDteDateCreated());
						objModel.setIntId(objModel1.getIntId());
					}
				} else {
					lastNo = objGlobalFunctionsService.funGetCount("tblexciserecipermasterhd", "intId");
					String recipeCode = "R" + String.format("%06d", lastNo);

					objModel.setStrRecipeCode(recipeCode);
					objModel.setStrUserCreated(userCode);
					objModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					objModel.setIntId(lastNo);
				}

				objModel.setStrParentName(objBean.getStrParentName());
				objModel.setStrParentCode(objBean.getStrParentCode());
				objModel.setDtValidFrom(objBean.getDtValidFrom());
				objModel.setDtValidTo(objBean.getDtValidTo());
				objModel.setStrUserModified(userCode);
				objModel.setStrClientCode(clientCode);
				objModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			}
		}
		return objModel;
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadExciseRecipeMasterData", method = RequestMethod.GET)
	public @ResponseBody clsExciseRecipeMasterBean funAssignFieldsForForm(@RequestParam("recipeCode") String recipeCode, HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		clsExciseRecipeMasterBean objBean = null;
		List objList = objclsExciseRecipeMasterService.funGetObject(recipeCode, clientCode);

		if (objList.size() > 0) {
			Object obj = (Object) objList.get(0);
			clsExciseRecipeMasterHdModel objModel = (clsExciseRecipeMasterHdModel) obj;
			// clsBrandMasterModel brandModel = (clsBrandMasterModel) obj[1];
			objBean = funPreapareBean(objModel);
			// String
			// sql_SizeList="select strSizeCode,strSizeName from tblsizemaster where "
			// +
			// " strSizeCode='"+brandModel.getStrSizeCode()+"' and strClientCode='"+clientCode+"' ";
			//
			// Object sizeData[]=(Object[])
			// objGlobalFunctionsService.funGetDataList(sql_SizeList,
			// "sql").get(0);
			// objBean.setStrParentSize(sizeData[1].toString());

			List objDtlList = objclsExciseRecipeMasterService.funGetRecipeDtlList(recipeCode, clientCode);
			List<clsExciseRecipeMasterDtlModel> listRecipeDtl = new ArrayList<clsExciseRecipeMasterDtlModel>();
			for (int i = 0; i < objDtlList.size(); i++) {
				Object[] ob = (Object[]) objDtlList.get(i);
				clsExciseRecipeMasterDtlModel recipeDtl = (clsExciseRecipeMasterDtlModel) ob[0];
				clsBrandMasterModel brandMaster = (clsBrandMasterModel) ob[1];
				recipeDtl.setStrBrandName(brandMaster.getStrBrandName());
				listRecipeDtl.add(recipeDtl);
			}
			objBean.setObjclsExciseRecipeMasterDtlModel(listRecipeDtl);
		} else {
			objBean = new clsExciseRecipeMasterBean();
			objBean.setStrRecipeCode("Invalid Code");

		}

		return objBean;
	}

	public clsExciseRecipeMasterBean funPreapareBean(clsExciseRecipeMasterHdModel objModel) {
		clsExciseRecipeMasterBean objBean = new clsExciseRecipeMasterBean();

		objBean.setStrRecipeCode(objModel.getStrRecipeCode());
		objBean.setStrParentCode(objModel.getStrParentCode());
		objBean.setStrParentName(objModel.getStrParentName());
		objBean.setDtValidFrom(objModel.getDtValidFrom());
		objBean.setDtValidTo(objModel.getDtValidTo());
		objBean.setIntId(objModel.getIntId());
		objBean.setStrClientCode(objModel.getStrClientCode());
		objBean.setStrUserCreated(objModel.getStrUserCreated());
		objBean.setStrUserModified(objModel.getStrUserModified());

		return objBean;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/loadPOSItemMasterData", method = RequestMethod.GET)
	@ResponseBody
	public List funLoaditemMasterData(@RequestParam("itemCode") String itemCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String strUrl = clsPOSGlobalFunctionsController.POSWSURL + "/ExciseIntegration/funLoadPOSItemSearch" + "?itemCode=" + itemCode + "&clientCode=" + clientCode;
		JSONObject jObjSearchDetails = new JSONObject();
		List set = new ArrayList();
		try {
			URL url = new URL(strUrl);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output = "", op = "";
			while ((output = br.readLine()) != null) {
				op += output;
			}
			System.out.println("Obj=" + op);
			conn.disconnect();

			JSONParser parser = new JSONParser();
			Object obj = parser.parse(op);
			jObjSearchDetails = (JSONObject) obj;

			JSONArray jArr = (JSONArray) jObjSearchDetails.get(itemCode);
			for (int i = 0; i < jArr.size(); i++) {
				JSONArray jArrItemData = (JSONArray) jArr.get(i);
				set.add(jArrItemData.get(0));
				set.add(jArrItemData.get(1));

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return set;

	}
}
