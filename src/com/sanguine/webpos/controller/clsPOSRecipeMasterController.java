package com.sanguine.webpos.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.sanguine.webpos.bean.clsChildMenuItemBean;
import com.sanguine.webpos.bean.clsDeliveryBoyChargesBean;
import com.sanguine.webpos.bean.clsDeliveryBoyMasterBean;
import com.sanguine.webpos.bean.clsPOSAreaMasterBean;
import com.sanguine.webpos.bean.clsPOSRecipeMasterBean;

@Controller
public class clsPOSRecipeMasterController {
	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	Map map = new HashMap();

	@RequestMapping(value = "/frmPOSRecipeMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") @Valid clsPOSRecipeMasterBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		String clientCode = request.getSession().getAttribute("clientCode").toString();

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSRecipeMaster_1");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSRecipeMaster");
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/savePOSRecipeMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSRecipeMasterBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";

		try {
			urlHits = req.getParameter("saddr").toString();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String webStockUserCode = req.getSession().getAttribute("usercode").toString();
			String strPosCode = req.getSession().getAttribute("loginPOS").toString();

			JSONObject jObjSettlementMaster = new JSONObject();
			jObjSettlementMaster.put("RecipeCode", objBean.getStrRecipeCode());
			jObjSettlementMaster.put("ItemCode", objBean.getStrItemCode());

			map.put(objBean.getStrItemCode(), objBean.getStrItemName());

			jObjSettlementMaster.put("FromDate", objBean.getDteFromDate());
			jObjSettlementMaster.put("ToDate", objBean.getDteToDate());
			jObjSettlementMaster.put("User", webStockUserCode);
			jObjSettlementMaster.put("ClientCode", clientCode);
			jObjSettlementMaster.put("strPosCode", strPosCode);

			// Recipe Details Data

			List<clsChildMenuItemBean> list = objBean.getListChildItemDtl();
			JSONArray jArrList = new JSONArray();
			for (int i = 0; i < list.size(); i++) {
				clsChildMenuItemBean obj = new clsChildMenuItemBean();
				obj = (clsChildMenuItemBean) list.get(i);

				JSONObject jObjData = new JSONObject();

				jObjData.put("ItemCode", obj.getStrItemCode());
				jObjData.put("Quantity", obj.getDblQuantity());
				jArrList.add(jObjData);
				map.put(obj.getStrItemCode(), obj.getStrItemName());

			}

			jObjSettlementMaster.put("ChildItemDtl", jArrList);

			String posURL = clsPOSGlobalFunctionsController.POSWSURL + "/APOSMastersIntegration/funSaveRecipeMaster";
			URL url = new URL(posURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObjSettlementMaster.toString().getBytes());
			os.flush();
			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output = "", op = "";

			while ((output = br.readLine()) != null) {
				op += output;
			}
			System.out.println("Result= " + op);
			conn.disconnect();

			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", " " + op);

			return new ModelAndView("redirect:/frmPOSRecipeMaster.html?saddr=" + urlHits);
		} catch (Exception ex) {
			urlHits = "1";
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	@RequestMapping(value = "/loadPOSRecipeMasterData", method = RequestMethod.GET)
	public @ResponseBody clsPOSRecipeMasterBean funSetSearchFields(@RequestParam("recipeCode") String settlementCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsPOSRecipeMasterBean objPOSWaiterMaster = null;
		List<clsChildMenuItemBean> listSettleData = new ArrayList<clsChildMenuItemBean>();
		JSONObject jObjSearchDetails = new JSONObject();
		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/APOSMastersIntegration/funGetRecipeMasterData" + "?recipeCode=" + settlementCode + "&clientCode=" + clientCode;
		System.out.println(posUrl);

		try {
			URL url = new URL(posUrl);

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

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (null != jObjSearchDetails) {
			objPOSWaiterMaster = new clsPOSRecipeMasterBean();
			objPOSWaiterMaster.setStrRecipeCode((String) jObjSearchDetails.get("strRecipeCode"));
			objPOSWaiterMaster.setStrItemCode((String) jObjSearchDetails.get("strItemCode"));

			String itemCode = (String) jObjSearchDetails.get("strItemCode");
			String itemName = "";
			if (map.containsKey(itemCode)) {
				itemName = (String) map.get(itemCode);

			}
			objPOSWaiterMaster.setStrItemName(itemName);
			objPOSWaiterMaster.setDteFromDate((String) jObjSearchDetails.get("dteFromDate"));
			objPOSWaiterMaster.setDteToDate((String) jObjSearchDetails.get("dteToDate"));

			JSONArray jArrDelChargesList = (JSONArray) jObjSearchDetails.get("RecipeDtl");
			if (null != jArrDelChargesList) {
				for (int cnt = 0; cnt < jArrDelChargesList.size(); cnt++) {
					JSONObject jobj = (JSONObject) jArrDelChargesList.get(cnt);
					clsChildMenuItemBean objSettlementDtl = new clsChildMenuItemBean();
					objSettlementDtl.setStrItemCode((String) jobj.get("ItemCode"));

					String childItemCode = (String) jobj.get("ItemCode");
					String childItemName = "";
					if (map.containsKey(childItemCode)) {
						childItemName = (String) map.get(childItemCode);
					}
					objSettlementDtl.setStrItemName(childItemName);
					objSettlementDtl.setDblQuantity((long) jobj.get("Quantity"));

					listSettleData.add(objSettlementDtl);
				}
			}
			objPOSWaiterMaster.setListChildItemDtl(listSettleData);

		}

		if (null == objPOSWaiterMaster) {
			objPOSWaiterMaster = new clsPOSRecipeMasterBean();
			objPOSWaiterMaster.setStrRecipeCode("Invalid Code");
		}

		return objPOSWaiterMaster;
	}

}
