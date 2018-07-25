package com.sanguine.webpos.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
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
import com.sanguine.webpos.bean.clsPOSGroupMasterBean;
import com.sanguine.webpos.bean.clsPOSMenuHeadBean;

@Controller
public class clsPOSMenuHeadController {

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	@RequestMapping(value = "/frmPOSMenuHead", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		// return new ModelAndView("frmPOSMenuMaster");

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSMenuHead_1", "command", new clsPOSMenuHeadBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSMenuHead", "command", new clsPOSMenuHeadBean());
		} else
			return null;
	}

	@RequestMapping(value = "/saveMenuHeadMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSMenuHeadBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		// save data of Menu Head
		if (objBean.getStrSubMenuHeadName() != "") {
			try {
				urlHits = req.getParameter("saddr").toString();
				String clientCode = req.getSession().getAttribute("clientCode").toString();
				String webStockUserCode = req.getSession().getAttribute("usercode").toString();

				JSONObject jObjMenuHead = new JSONObject();
				jObjMenuHead.put("SubMenuHeadCode", objBean.getStrSubMenuHeadCode());
				jObjMenuHead.put("SubMenuHeadName", objBean.getStrSubMenuHeadName());
				jObjMenuHead.put("Operational", objBean.getStrSubMenuOperational());
				jObjMenuHead.put("MenuCode", objBean.getStrMenuHeadCode());
				jObjMenuHead.put("SubMenuHeadShortName", objBean.getStrSubMenuHeadShortName());
				// String
				// MenuCode=objSubMenuHeadMaster.getString("MenuCode");strSubMenuHeadShortName
				jObjMenuHead.put("User", webStockUserCode);
				jObjMenuHead.put("ClientCode", clientCode);
				jObjMenuHead.put("dteDateCreated", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				jObjMenuHead.put("dteDateEdited", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				jObjMenuHead.put("OperationType", objBean.getStrOperationType());

				System.out.println("objBean.getStrOperationType" + objBean.getStrOperationType());

				String posURL = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funSaveSubMenuHead";
				URL url = new URL(posURL);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				OutputStream os = conn.getOutputStream();
				os.write(jObjMenuHead.toString().getBytes());
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

				return new ModelAndView("redirect:/frmPOSMenuHead.html?saddr=" + urlHits);
			} catch (Exception ex) {
				urlHits = "1";
				ex.printStackTrace();
				return new ModelAndView("redirect:/frmFail.html");
			}

		}
		// save for Sub menu code
		else if (objBean.getStrMenuHeadName() != "") {
			try {
				urlHits = req.getParameter("saddr").toString();
				String clientCode = req.getSession().getAttribute("clientCode").toString();
				String webStockUserCode = req.getSession().getAttribute("usercode").toString();

				JSONObject jObjMenuHead = new JSONObject();
				jObjMenuHead.put("MenuHeadCode", objBean.getStrMenuHeadCode());
				jObjMenuHead.put("MenuHeadName", objBean.getStrMenuHeadName());
				jObjMenuHead.put("Operational", objBean.getStrOperational());
				jObjMenuHead.put("User", webStockUserCode);
				jObjMenuHead.put("ClientCode", clientCode);
				jObjMenuHead.put("dteDateCreated", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				jObjMenuHead.put("dteDateEdited", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				jObjMenuHead.put("OperationType", objBean.getStrOperationType());

				System.out.println("objBean.getStrOperationType" + objBean.getStrOperationType());

				String posURL = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funSaveMenuHeadMaster";
				URL url = new URL(posURL);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				OutputStream os = conn.getOutputStream();
				os.write(jObjMenuHead.toString().getBytes());
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

				return new ModelAndView("redirect:/frmPOSMenuHead.html?saddr=" + urlHits);
			} catch (Exception ex) {
				urlHits = "1";
				ex.printStackTrace();
				return new ModelAndView("redirect:/frmFail.html");
			}

		} else {
			return new ModelAndView("redirect:/frmFail.html");
		}

	}

	@RequestMapping(value = "/loadPOSMenuHeadMasterData", method = RequestMethod.GET)
	public @ResponseBody clsPOSMenuHeadBean funSetSearchFields(@RequestParam("POSMenuHeadCode") String menuHeadCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsPOSMenuHeadBean objPOSMenuHeadBean = null;

		JSONObject jObjSearchDetails = new JSONObject();
		String posUrl = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funGetMenuHeadMasterData" + "?menuHeadCode=" + menuHeadCode + "&clientCode=" + clientCode;
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
		JSONArray jArrSearchList = (JSONArray) jObjSearchDetails.get("POSMenuHeadMaster");
		if (null != jArrSearchList) {
			objPOSMenuHeadBean = new clsPOSMenuHeadBean();
			objPOSMenuHeadBean.setStrMenuHeadCode((String) jArrSearchList.get(0));
			objPOSMenuHeadBean.setStrMenuHeadName((String) jArrSearchList.get(1));
			objPOSMenuHeadBean.setStrOperational((String) jArrSearchList.get(2));
			objPOSMenuHeadBean.setStrOperationType("U");
		}
		if (null == objPOSMenuHeadBean) {
			objPOSMenuHeadBean = new clsPOSMenuHeadBean();
			objPOSMenuHeadBean.setStrMenuHeadCode("Invalid Code");
		}

		return objPOSMenuHeadBean;
	}

	@RequestMapping(value = "/loadPOSSubMenuHeadMasterData", method = RequestMethod.GET)
	public @ResponseBody clsPOSMenuHeadBean funSetSubMenuFields(@RequestParam("POSSubMenuHeadCode") String subMenuHeadCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsPOSMenuHeadBean objPOSMenuHeadBean = null;

		JSONObject jObjSearchDetails = new JSONObject();
		String posUrl = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funGetSubMenuHeadMasterData" + "?subMenuHeadCode=" + subMenuHeadCode + "&clientCode=" + clientCode;
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

		JSONArray jArrSearchList = (JSONArray) jObjSearchDetails.get("POSSubMenuHeadMaster");
		if (null != jArrSearchList) {
			objPOSMenuHeadBean = new clsPOSMenuHeadBean();
			objPOSMenuHeadBean.setStrSubMenuHeadCode((String) jArrSearchList.get(0));
			objPOSMenuHeadBean.setStrSubMenuHeadName((String) jArrSearchList.get(1));
			objPOSMenuHeadBean.setStrSubMenuHeadShortName((String) jArrSearchList.get(2));
			objPOSMenuHeadBean.setStrMenuHeadCode((String) jArrSearchList.get(3));
			objPOSMenuHeadBean.setStrSubMenuOperational((String) jArrSearchList.get(4));

			objPOSMenuHeadBean.setStrOperationType("U");
		}
		if (null == objPOSMenuHeadBean) {
			objPOSMenuHeadBean = new clsPOSMenuHeadBean();
			objPOSMenuHeadBean.setStrMenuHeadCode("Invalid Code");
		}

		return objPOSMenuHeadBean;
	}

	@RequestMapping(value = "/checkMenuName", method = RequestMethod.GET)
	public @ResponseBody boolean funCheckMenuName(@RequestParam("menuName") String menuName, @RequestParam("menuCode") String menuCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		int count = objPOSGlobal.funCheckName(menuName, menuCode, clientCode, "POSMenuHead");
		if (count > 0)
			return false;
		else
			return true;

	}

	@RequestMapping(value = "/checkSubMenuName", method = RequestMethod.GET)
	public @ResponseBody boolean checkSubMenuName(@RequestParam("subMenuName") String subMenuName, @RequestParam("subMenuCode") String subMenuCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		int count = objPOSGlobal.funCheckName(subMenuName, subMenuCode, clientCode, "POSSubMenuHead");
		if (count > 0)
			return false;
		else
			return true;

	}

	@RequestMapping(value = "/loadMenuHeadData", method = RequestMethod.GET)
	public @ResponseBody List<clsPOSMenuHeadBean> funGetMenuHeadData(HttpServletRequest req) {
		List<clsPOSMenuHeadBean> lstMenuDtl = new ArrayList<clsPOSMenuHeadBean>();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsPOSMenuHeadBean objPOSMenuHeadBean = null;

		JSONObject jObjSearchDetails = new JSONObject();
		String posUrl = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funLoadMenuHeadMaster" + "?masterName=" + "POSMenuHeadMaster" + "&clientCode=" + clientCode;

		/*
		 * "http://localhost:8080/prjSanguineWebService/APOSSearchIntegration/funGetPOSMenuDtl"
		 * + "?masterName="+"POSMenuHeadMaster"+"&clientCode="+clientCode;
		 */
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

		JSONArray jArrSearchList = (JSONArray) jObjSearchDetails.get("POSMenuHeadMaster");
		JSONObject subJsonObject = new JSONObject();
		if (null != jArrSearchList) {
			for (int i = 0; i < jArrSearchList.size(); i++) {
				subJsonObject = (JSONObject) jArrSearchList.get(i);

				objPOSMenuHeadBean = new clsPOSMenuHeadBean();

				objPOSMenuHeadBean.setStrMenuHeadCode((String) subJsonObject.get("strMenuCode"));
				objPOSMenuHeadBean.setStrMenuHeadName((String) subJsonObject.get("strMenuName"));

				lstMenuDtl.add(objPOSMenuHeadBean);
				// objPOSMenuHeadBean.setListMenuMasterDtl();
			}
		}
		if (null == objPOSMenuHeadBean) {
			objPOSMenuHeadBean = new clsPOSMenuHeadBean();
			objPOSMenuHeadBean.setStrMenuHeadCode("Data not found");
		}

		return lstMenuDtl;
	}

	@RequestMapping(value = "/loadMenuHeadDtlData", method = RequestMethod.GET)
	public @ResponseBody List<clsPOSMenuHeadBean> funLoadMenuHeadDtlData(HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		JSONArray jArrMenuHeadList = null;
		List<clsPOSMenuHeadBean> listMenuHedaData = new ArrayList<clsPOSMenuHeadBean>();
		JSONObject jObjMenuHeadData = new JSONObject();
		String posUrl = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funGetAllMenuHeadForMaster" + "?clientCode=" + clientCode;

		jObjMenuHeadData = objGlobal.funGETMethodUrlJosnObjectData(posUrl);

		jArrMenuHeadList = (JSONArray) jObjMenuHeadData.get("MenuList");

		if (null != jArrMenuHeadList) {
			for (int cnt = 0; cnt < jArrMenuHeadList.size(); cnt++) {
				JSONObject jobj = (JSONObject) jArrMenuHeadList.get(cnt);
				clsPOSMenuHeadBean objMenuHeadDtl = new clsPOSMenuHeadBean();
				objMenuHeadDtl.setStrMenuHeadCode((String) jobj.get("strMenuCode"));
				objMenuHeadDtl.setStrMenuHeadName((String) jobj.get("strMenuName"));
				if ((boolean) jobj.get("Operational")) {
					objMenuHeadDtl.setStrOperational("Y");
				} else {
					objMenuHeadDtl.setStrOperational("N");
				}

				listMenuHedaData.add(objMenuHeadDtl);
			}
		}
		return listMenuHedaData;
	}

}
