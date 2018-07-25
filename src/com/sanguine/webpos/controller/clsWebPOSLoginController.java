package com.sanguine.webpos.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.webpos.bean.clsPOSGroupMasterBean;
import com.sanguine.webpos.bean.clsWebPOSLoginBean;

@Controller
public class clsWebPOSLoginController {

	@RequestMapping(value = "/frmWebPOSLogin", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		return new ModelAndView("frmWebPOSLogin", "command", new clsWebPOSLoginBean());
	}

	@RequestMapping(value = "/ConfrimLoginWebPOS", method = RequestMethod.POST)
	public ModelAndView funLoginCheck(@RequestParam("userName") String userCode, @RequestParam("password") String password, HttpServletRequest req) {

		try {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String webStockUserCode = req.getSession().getAttribute("usercode").toString();

			String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/APOSIntegration/funAuthenticateUser?strUserCode=" + "" + userCode + "&strPassword=" + password;
			System.out.println(posUrl);

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

			JSONObject jObjLoginStatus = (JSONObject) obj;
			JSONArray jArrLoginStatus = (JSONArray) jObjLoginStatus.get("LoginStatus");
			JSONObject jObj = (JSONObject) jArrLoginStatus.get(0);
			String status = jObj.get("Status").toString();
			String userName = jObj.get("UserName").toString();
			String userType = jObj.get("SuperType").toString();

			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Welocom : ");

			req.getSession().setAttribute("POSUserCode", userCode);
			req.getSession().setAttribute("POSUserName", userName);
			req.getSession().setAttribute("POSUserType", userType);

			Map<Integer, clsPOSMenu> tr = funGetPOSMenuMap(userCode, clientCode, "M", userType, "P01");
			req.getSession().setAttribute("POSTreeMap", tr);

			return new ModelAndView("redirect:/frmPOSMainMenu.html");
			// return new ModelAndView("frmPOSGroupMaster","command", new
			// clsPOSGroupMasterBean());
			// return new ModelAndView("frmMainMenu");
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	@RequestMapping(value = "/frmPOSMainMenu", method = RequestMethod.GET)
	private ModelAndView funModuleSelection(HttpServletRequest req) {
		return new ModelAndView("frmPOSGroupMaster");
	}

	private Map<Integer, clsPOSMenu> funGetPOSMenuMap(String userCode, String clientCode, String moduleType, String superUser, String POSCode) throws Exception {
		Map<Integer, clsPOSMenu> tmPOSMenu = new TreeMap<Integer, clsPOSMenu>();

		boolean superUserYN = false;
		if (superUser.equals("Super")) {
			superUserYN = true;
		}

		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/APOSIntegration/funGetMainMenu" + "?UserCode=" + userCode + "&ModuleType=" + moduleType + "&clientCode=" + clientCode + "&SuperUser=" + superUserYN + "&POSCode=" + POSCode;
		System.out.println(posUrl);

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
		JSONObject jObjPOSMenuList = (JSONObject) obj;
		JSONArray jArrMenuList = (JSONArray) jObjPOSMenuList.get("MainMenuList");
		// JSONObject jObjPOSDate=(JSONObject)jObjPOSMenuList.get("POSDate");

		for (int cnt = 0; cnt < jArrMenuList.size(); cnt++) {
			JSONObject jObj = (JSONObject) jArrMenuList.get(cnt);
			clsPOSMenu objPOSMenu = new clsPOSMenu();
			objPOSMenu.setFormName(jObj.get("FormName").toString() + ".html");
			objPOSMenu.setImageName(jObj.get("ImageName").toString());
			objPOSMenu.setModuleName(jObj.get("ModuleName").toString());
			tmPOSMenu.put(Integer.parseInt(jObj.get("Seq").toString()), objPOSMenu);
		}

		return tmPOSMenu;
	}

}

class clsPOSMenu {
	private String formName;

	private String moduleName;

	private String imageName;

	private int sequence;

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
}
