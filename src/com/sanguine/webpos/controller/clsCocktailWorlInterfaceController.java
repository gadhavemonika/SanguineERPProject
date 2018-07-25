package com.sanguine.webpos.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.webpos.bean.clsCocktailWorldInterfaceBean;
import com.sanguine.webpos.bean.clsPOSAreaMasterBean;

@Controller
public class clsCocktailWorlInterfaceController {

	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	Map map = new HashMap();

	@RequestMapping(value = "/frmPOSCocktailWorldInterface", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") @Valid clsCocktailWorldInterfaceBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		String posCode = request.getSession().getAttribute("loginPOS").toString();
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		List<Object> posList = new ArrayList<Object>();
		posList.add("ALL");

		JSONArray jArrList = new JSONArray();
		jArrList = objPOSGlobal.funGetAllPOSForMaster(clientCode);
		for (int i = 0; i < jArrList.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArrList.get(i);
			posList.add(josnObjRet.get("strPosName"));
			map.put(josnObjRet.get("strPosName"), josnObjRet.get("strPosCode"));
		}

		String posUrL = clsPOSGlobalFunctionsController.POSWSURL + "/APOSIntegration/funGetPOSDate" + "?POSCode=" + posCode;
		JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData(posUrL);

		String posDate = (String) jObj.get("POSDate");
		String[] output = posDate.split(" ");
		String POSDate = output[0];
		model.put("posDate", POSDate);
		model.put("posList", posList);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSCocktailWorldInterface_1");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSCocktailWorldInterface");
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/savePOSCWInterface", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsCocktailWorldInterfaceBean objBean, BindingResult result, HttpServletRequest req) {

		String posCode = "";
		try {

			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String webStockUserCode = req.getSession().getAttribute("usercode").toString();

			JSONObject jObjAreaMaster = new JSONObject();
			// String fromDate=objBean.getDteExpiryDt();
			jObjAreaMaster.put("date", objBean.getDteExpiryDt());
			// String reportType=objBean.getStrcmbReportType();
			jObjAreaMaster.put("reportType", objBean.getStrcmbReportType());

			String posName = objBean.getStrPOSName();

			if (posName.equalsIgnoreCase("ALL")) {
				posCode = "All";
			}
			if (map.containsKey(posName)) {
				posCode = (String) map.get(posName);

			}
			String POSCode = posCode;
			String posUrl;
			String stringDtl = "";

			jObjAreaMaster.put("POSName", posCode);
			jObjAreaMaster.put("User", webStockUserCode);
			jObjAreaMaster.put("ClientCode", clientCode);
			jObjAreaMaster.put("dteDateCreated", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			jObjAreaMaster.put("dteDateEdited", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			String posURL = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSTransactions/funSaveCocktailWorldInterface";
			URL url = new URL(posURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObjAreaMaster.toString().getBytes());
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

			return new ModelAndView("redirect:/frmPOSCocktailWorldInterface.html");
		} catch (Exception ex) {

			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	// public static final int BYTES_DOWNLOAD = 1024;
	// @RequestMapping(value = "/savePOSCWInterface", method =
	// RequestMethod.GET)
	// public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid
	// clsCocktailWorldInterfaceBean objBean,BindingResult
	// result,HttpServletRequest req,HttpServletResponse res)
	// {
	// String urlHits="1";
	// String posCode="";
	// try
	// {
	// urlHits=req.getParameter("saddr").toString();
	// String clientCode=req.getSession().getAttribute("clientCode").toString();
	// String
	// webStockUserCode=req.getSession().getAttribute("usercode").toString();
	//
	// JSONObject jObjAreaMaster=new JSONObject();
	// String fromDate=objBean.getDteExpiryDt();
	// //jObjAreaMaster.put("date", objBean.getDteExpiryDt());
	// String reportType=objBean.getStrcmbReportType();
	// //jObjAreaMaster.put("reportType", objBean.getStrcmbReportType());
	//
	// String posName=objBean.getStrPOSName();
	//
	// if(posName.equalsIgnoreCase("ALL"))
	// {
	// posCode="All";
	// }
	// if(map.containsKey(posName))
	// {
	// posCode=(String) map.get(posName);
	//
	// }
	// String POSCode= posCode;
	// String posUrl;
	// String stringDtl="";
	//
	// //jObjAreaMaster.put("POSName", posCode);
	// jObjAreaMaster.put("User", webStockUserCode);
	// jObjAreaMaster.put("ClientCode", clientCode);
	// jObjAreaMaster.put("dteDateCreated",
	// objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
	// jObjAreaMaster.put("dteDateEdited",
	// objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
	// // posUrl =
	// "http://localhost:8080/prjSanguineWebService/WebPOSTransactions/funGenerateSaleDataFile"
	// // + "?fromDate="+fromDate+"&POSCode="+POSCode;
	// // if(reportType.toString().equals("Sale Data File"))
	// // {
	// //
	// // posUrl =
	// "http://localhost:8080/prjSanguineWebService/WebPOSTransactions/funGenerateSaleDataFile"
	// // + "?fromDate="+fromDate+"&POSCode="+POSCode;
	// // JSONObject jObj1=objGlobal.funGETMethodUrlJosnObjectData(posUrl);
	// // JSONArray jArrUnsettleBillList=(JSONArray)
	// jObj1.get("listOfSaleData");
	// // if(jArrUnsettleBillList!=null)
	// // {
	// // for(int i =0 ;i<jArrUnsettleBillList.size();i++)
	// // {
	// // JSONObject josnObjRet = (JSONObject) jArrUnsettleBillList.get(i);
	// //
	// // stringDtl+=(String) josnObjRet.get("stringDetails");
	// //
	// // }
	// //
	// // }
	// // res.setContentType("text/plain");
	// // res.setHeader("Content-Disposition",
	// "attachment;filename=downloadname.txt");
	// //
	// //
	// // InputStream input = new
	// ByteArrayInputStream(stringDtl.getBytes("UTF8"));
	// //
	// // int read = 0;
	// //// String newLine="\n";
	// // byte[] bytes = new byte[BYTES_DOWNLOAD];
	// // OutputStream os = res.getOutputStream();
	// //
	// // //data form resultset
	// //
	// // while ((read = input.read(bytes)) != -1) {
	// // os.write(bytes, 0, read);
	// // os.write(System.getProperty("line.separator").getBytes());
	// // os.write("\n".getBytes());
	// //
	// //// os.write(newLine.getBytes("UTF8"));
	// // }
	// // os.flush();
	// // os.close();
	// //
	// // }
	// // else
	// // {
	// // posUrl =
	// "http://localhost:8080/prjSanguineWebService/WebPOSTransactions/funGenerateMenuItemCodeFile";
	// // JSONObject jObj1=objGlobal.funGETMethodUrlJosnObjectData(posUrl);
	// // JSONArray jArrUnsettleBillList=(JSONArray) jObj1.get("list");
	// // if(jArrUnsettleBillList!=null)
	// // {
	// // for(int i =0 ;i<jArrUnsettleBillList.size();i++)
	// // {
	// // JSONObject josnObjRet = (JSONObject) jArrUnsettleBillList.get(i);
	// //
	// // stringDtl+=(String) josnObjRet.get("stringDtl")+"\n";
	// //
	// // }
	// //
	// // }
	// // res.setContentType("text/plain");
	// // res.setHeader("Content-Disposition",
	// "attachment;filename=downloadname.txt");
	// //
	// //
	// // InputStream input = new
	// ByteArrayInputStream(stringDtl.getBytes("UTF8"));
	// //
	// // int read = 0;
	// //// String newLine="\n";
	// // byte[] bytes = new byte[BYTES_DOWNLOAD];
	// // OutputStream os = res.getOutputStream();
	// //// OutputStreamWriter oswriter = new
	// OutputStreamWriter(res.getOutputStream());
	// //// BufferedWriter bwriter = new BufferedWriter(oswriter);
	// // //data form resultset
	// //
	// // while ((read = input.read(bytes)) != -1) {
	// //// bwriter.write(read);
	// //// bwriter.write(13);
	// //// bwriter.newLine();
	// //// os.write(newLine.getBytes("UTF8"));
	// // os.write(bytes, 0, read);
	// // os.write(13);
	// // }
	// // os.flush();
	// // os.close();
	// // }
	// //
	//
	//
	// String posURL =
	// "http://localhost:8080/prjSanguineWebService/WebPOSTransactions/funSaveCocktailWorldInterface";
	// URL url = new URL(posURL);
	// HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	// conn.setDoOutput(true);
	// conn.setRequestMethod("POST");
	// conn.setRequestProperty("Content-Type", "application/json");
	// OutputStream os = conn.getOutputStream();
	// os.write(jObjAreaMaster.toString().getBytes());
	// os.flush();
	// if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED)
	// {
	// throw new RuntimeException("Failed : HTTP error code : " +
	// conn.getResponseCode());
	// }
	// BufferedReader br = new BufferedReader(new
	// InputStreamReader((conn.getInputStream())));
	// String output = "", op = "";
	//
	// while ((output = br.readLine()) != null)
	// {
	// op += output;
	// }
	// System.out.println("Result= " + op);
	// conn.disconnect();
	//
	// req.getSession().setAttribute("success", true);
	// req.getSession().setAttribute("successMessage"," "+op);
	//
	// return new
	// ModelAndView("redirect:/frmPOSAreaMaster.html?saddr="+urlHits);
	// }
	// catch(Exception ex)
	// {
	// urlHits="1";
	// ex.printStackTrace();
	// return new ModelAndView("redirect:/frmFail.html");
	// }
	//
	// }
}
