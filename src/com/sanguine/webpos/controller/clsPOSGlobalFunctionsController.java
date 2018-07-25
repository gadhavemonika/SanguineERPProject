package com.sanguine.webpos.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.controller.clsUserController;

@Controller
public class clsPOSGlobalFunctionsController {
	@Autowired
	clsGlobalFunctions objWSGlobalFunctions;

	/*
	 * public static String wsServerIp;
	 * 
	 * public static String wsServerPortNo;
	 */

	public static String wsServerIp;

	public static String wsServerPortNo;

	@Value("${wsServerIp}")
	public void setWsServerIp(String wsServerIp) {
		clsPOSGlobalFunctionsController.wsServerIp = wsServerIp;
		// setPOSWSURL("http://"+wsServerIp+":"+wsServerPortNo+"/prjSanguineWebService");
		setPOSWSURL("http://" + wsServerIp + "/prjSanguineWebService");
	}

	/*
	 * @Value("${wsServerPortNo}") public void setWsServerPortNo(String
	 * wsServerPortNo) { clsPOSGlobalFunctionsController.wsServerPortNo =
	 * wsServerPortNo;
	 * setPOSWSURL("http://"+wsServerIp+":"+wsServerPortNo+"/prjSanguineWebService"
	 * ); }
	 */

	public void setPOSWSURL(String pOSWSURL) {
		clsPOSGlobalFunctionsController.POSWSURL = pOSWSURL;
	}

	/*
	 * // Properties file serverip and port no assigning
	 * 
	 * @Value("${wsServerIp}") public void setWsServerIp(String wsServerIp) {
	 * clsUserController.wsServerIp = wsServerIp;
	 * setPOSWSURL("http://"+wsServerIp
	 * +":"+wsServerPortNo+"/prjSanguineWebService"); }
	 * 
	 * @Value("${wsServerPortNo}") public void setWsServerPortNo(String
	 * wsServerPortNo) { clsUserController.wsServerPortNo = wsServerPortNo;
	 * setPOSWSURL
	 * ("http://"+wsServerIp+":"+wsServerPortNo+"/prjSanguineWebService"); }
	 * 
	 * public void setPOSWSURL(String pOSWSURL) { POSWSURL = pOSWSURL; }
	 */

	public static String POSWSURL;

	public static Map<Object, Object> hmPOSSetupValues = new HashMap<Object, Object>();

	// End

	public JSONArray funGetAllPOSForMaster(String clientCode) {

		String posUrl = POSWSURL + "/APOSMastersIntegration/funGetAllPOSForMaster?clientCode=" + clientCode;
		System.out.println(posUrl);
		JSONArray jArrList = null;
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
			JSONObject jObjPOSList = (JSONObject) obj;
			jArrList = (JSONArray) jObjPOSList.get("POSList");

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return jArrList;

	}

	public JSONArray funGetAllMenuHeadForMaster(String clientCode) {

		String posUrl = POSWSURL + "/APOSMastersIntegration/funGetAllMenuHeadForMaster?clientCode=" + clientCode;
		System.out.println(posUrl);
		JSONArray jArrList = null;
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
			JSONObject jObjPOSList = (JSONObject) obj;
			jArrList = (JSONArray) jObjPOSList.get("MenuList");

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return jArrList;

	}

	public JSONArray funGetAllSubMenuHeadForMaster(String clientCode) {

		String posUrl = POSWSURL + "/APOSMastersIntegration/funGetAllSubMenuHeadForMaster?clientCode=" + clientCode;
		System.out.println(posUrl);
		JSONArray jArrList = null;
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
			JSONObject jObjPOSList = (JSONObject) obj;
			jArrList = (JSONArray) jObjPOSList.get("SubMenuList");

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return jArrList;
	}

	public JSONArray funGetAllAreaForMaster(String clientCode) {

		String posUrl = POSWSURL + "/APOSMastersIntegration/funGetAllAreaForMaster?clientCode=" + clientCode;
		System.out.println(posUrl);
		JSONArray jArrList = null;
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
			JSONObject jObjPOSList = (JSONObject) obj;
			jArrList = (JSONArray) jObjPOSList.get("AreaList");

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return jArrList;

	}

	public JSONArray funGetAllWaiterForMaster(String clientCode) {

		String posUrl = POSWSURL + "/APOSMastersIntegration/funGetWaiterList" + "?clientCode=" + clientCode;
		System.out.println(posUrl);
		JSONArray jArrList = null;
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
			JSONObject jObjPOSList = (JSONObject) obj;
			jArrList = (JSONArray) jObjPOSList.get("waiterList");

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return jArrList;

	}

	public JSONArray funGetAllTaxForMaster(String clientCode) {

		String posUrl = POSWSURL + "/APOSMastersIntegration/funGetAllTaxForMaster?clientCode=" + clientCode;
		System.out.println(posUrl);
		JSONArray jArrList = null;
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
			JSONObject jObjTaxList = (JSONObject) obj;
			jArrList = (JSONArray) jObjTaxList.get("TaxList");

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return jArrList;

	}

	public JSONArray funGetAllCityForMaster(String clientCode) {

		String posUrl = POSWSURL + "/APOSMastersIntegration/funGetAllCityForMaster?clientCode=" + clientCode;
		System.out.println(posUrl);
		JSONArray jArrList = null;
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
			JSONObject jObjPOSList = (JSONObject) obj;
			jArrList = (JSONArray) jObjPOSList.get("cityList");

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return jArrList;

	}

	public JSONArray funGetAllStateForMaster(String clientCode) {

		String posUrl = POSWSURL + "/APOSMastersIntegration/funGetAllStateForMaster?clientCode=" + clientCode;
		System.out.println(posUrl);
		JSONArray jArrList = null;
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
			JSONObject jObjPOSList = (JSONObject) obj;
			jArrList = (JSONArray) jObjPOSList.get("stateList");

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return jArrList;

	}

	public JSONArray funGetAllCountryForMaster(String clientCode) {

		String posUrl = POSWSURL + "/APOSMastersIntegration/funGetAllCountryForMaster?clientCode=" + clientCode;
		System.out.println(posUrl);
		JSONArray jArrList = null;
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
			JSONObject jObjPOSList = (JSONObject) obj;
			jArrList = (JSONArray) jObjPOSList.get("countryList");

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return jArrList;

	}

	public JSONArray funGetAllReasonMaster(String clientCode) {

		String posUrl = POSWSURL + "/APOSMastersIntegration/funGetAllReasonMaster?clientCode=" + clientCode;
		System.out.println(posUrl);
		JSONArray jArrList = null;
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
			JSONObject jObjPOSList = (JSONObject) obj;
			jArrList = (JSONArray) jObjPOSList.get("ReasonList");

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return jArrList;

	}

	public JSONArray funGetAllCostCentersForMaster(String clientCode) {

		String costCenterUrl = POSWSURL + "/APOSMastersIntegration/funGetAllCostCentersForMaster?clientCode=" + clientCode;
		JSONObject jObj = objWSGlobalFunctions.funGETMethodUrlJosnObjectData(costCenterUrl);

		return (JSONArray) jObj.get("CostCenterList");
	}

	public int funCheckName(String name, String code, String clientCode, String formName) {

		String posURL = null;
		try {
			posURL = POSWSURL + "/APOSMastersIntegration/funCheckName" + "?masterName=" + formName + "&name=" + URLEncoder.encode(name, "UTF-8") + "&clientCode=" + clientCode + "&code=" + URLEncoder.encode(code, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject jObj = objWSGlobalFunctions.funGETMethodUrlJosnObjectData(posURL);

		return Integer.parseInt(jObj.get("count").toString());
	}

	public JSONArray funGetAllPromotionMaster(String clientCode) {

		String posUrl = POSWSURL + "/APOSMastersIntegration/funGetAllPromotionMaster?clientCode=" + clientCode;
		System.out.println(posUrl);
		JSONArray jArrList = null;
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
			JSONObject jObjPOSList = (JSONObject) obj;
			jArrList = (JSONArray) jObjPOSList.get("PromotionMaster");

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return jArrList;

	}

	public JSONArray funGetAllDeliveryBoy(String clientCode) {

		String posUrl = POSWSURL + "/APOSMastersIntegration/funGetAllDeliveryBoy?clientCode=" + clientCode;
		System.out.println(posUrl);
		JSONArray jArrList = null;
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
			JSONObject jObjPOSList = (JSONObject) obj;
			jArrList = (JSONArray) jObjPOSList.get("DeliveryBoy");

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return jArrList;

	}

	public JSONArray funGetAllUsersDetails(String clientCode) {

		String OperatorUrl = POSWSURL + "/APOSIntegration/funGetAllUserName";
		System.out.println(OperatorUrl);
		JSONArray jArrList = null;
		try {
			URL url = new URL(OperatorUrl);
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
			JSONObject jObjPOSList = (JSONObject) obj;
			jArrList = (JSONArray) jObjPOSList.get("userList");

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return jArrList;

	}

	public JSONArray funGetSettlementDetails(String clientCode) {

		String posUrl = POSWSURL + "/APOSMastersIntegration/funGetSettlementDtl?clientCode=" + clientCode;

		JSONArray jArrList = null;
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
			JSONObject jObjPOSList = (JSONObject) obj;
			jArrList = (JSONArray) jObjPOSList.get("SettlementDtl");

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return jArrList;
	}

	@RequestMapping(value = "/getPOSDate", method = RequestMethod.GET)
	public @ResponseBody JSONObject funGetPOSDate(HttpServletRequest req) {

		String strPosCode = req.getSession().getAttribute("loginPOS").toString();
		String posURL = POSWSURL + "/APOSIntegration/funGetPOSDate" + "?POSCode=" + strPosCode;
		JSONObject jObj = objWSGlobalFunctions.funGETMethodUrlJosnObjectData(posURL);

		return (jObj);
	}

	public JSONArray funGetTableList(String posCode, String clientCode) {

		String posURL = POSWSURL + "/APOSMastersIntegration/funGetTableList" + "?posCode=" + posCode + "&clientCode=" + clientCode;
		JSONObject jObj = objWSGlobalFunctions.funGETMethodUrlJosnObjectData(posURL);

		return (JSONArray) jObj.get("TableList");
	}

	public JSONArray funGetAllForm(String clientCode) {

		String posUrl = POSWSURL + "/APOSMastersIntegration/funGetAllForm?clientCode=" + clientCode;
		System.out.println(posUrl);
		JSONArray jArrList = null;
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
			JSONObject jObjFormList = (JSONObject) obj;
			jArrList = (JSONArray) jObjFormList.get("tblFormsDtl");

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return jArrList;

	}

	public JSONArray funGetAllDebitCardForMaster(String clientCode) {

		String posUrl = POSWSURL + "/APOSMastersIntegration/funGetAllDebitCardForMaster?clientCode=" + clientCode;
		System.out.println(posUrl);
		JSONArray jArrList = null;
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
			JSONObject jObjCardList = (JSONObject) obj;
			jArrList = (JSONArray) jObjCardList.get("CardTypeList");

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return jArrList;

	}

	public JSONArray funGetAllSubGroup(String clientCode) {

		String posURL = null;
		JSONArray jArrList = null;
		posURL = POSWSURL + "/WebPOSSGMaster/funGetSGCodeName?clientCode=" + clientCode;
		JSONObject jObj = objWSGlobalFunctions.funGETMethodUrlJosnObjectData(posURL);
		JSONObject jObjPOSList = (JSONObject) jObj;
		jArrList = (JSONArray) jObjPOSList.get("SGNameCode");
		return jArrList;
	}

	public JSONArray funGetAllGroup(String strClientCode) {
		List sglist = new ArrayList<String>();
		JSONArray jArry = new JSONArray();
		String posUrl = POSWSURL + "/APOSMastersIntegration/funGetAllGroup";
		try {
			JSONObject objRows = new JSONObject();
			objRows.put("strClientCode", strClientCode);

			JSONObject jObj = objWSGlobalFunctions.funPOSTMethodUrlJosnObjectData(posUrl, objRows);
			jArry = (JSONArray) jObj.get("allGroupData");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return jArry;
	}

	public JSONArray funGetAllRevenueHead(String strClientCode) {
		List sglist = new ArrayList<String>();
		JSONArray jArry = new JSONArray();
		String posUrl = POSWSURL + "/APOSMastersIntegration/funGetAllRevenueHead";
		try {
			JSONObject objRows = new JSONObject();
			objRows.put("strClientCode", strClientCode);

			JSONObject jObj = objWSGlobalFunctions.funPOSTMethodUrlJosnObjectData(posUrl, objRows);
			jArry = (JSONArray) jObj.get("RevenueHead");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return jArry;
	}

	public void funStartSocketBat() {
		try {
			funStopSocket();
			BufferedWriter objToken = new BufferedWriter(new FileWriter("SocketServer.bat"));
			String Path = System.getProperty("user.dir");
			String Script = "\"" + Path + "\\JioMoneySocket.exe" + "\"" + " /Protocol IPv4 /Port " + "5150";
			objToken.write(Script);
			objToken.close();
			Runtime.getRuntime().exec("cmd /c start SocketServer.bat");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void funStopSocket() {
		try {
			Runtime.getRuntime().exec("taskkill /f /im cmd.exe");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String funMakeTransaction(String Data, String requestType, String mid, String tid, String Amount, String Environment, String IP, String PORT, String posCode, String secretKey) {
		// Socket Base transaction start
		try

		{
			String host = IP; // IP address of the server
			int port = Integer.parseInt(PORT); // Port on which the socket is
												// going to connect

			// BufferedReader br = new BufferedReader(new
			// InputStreamReader(System.in));
			clsJioMoneyEncryption objJio = new clsJioMoneyEncryption();
			String response = "";
			StringBuilder Res = new StringBuilder();
			String encryptedData = objJio.encrypt(Data, secretKey);
			/*
			 * String finalRequest = encryptedData.toString() + "|" +
			 * request.getMid() + "|" + request.getTid() + "|" +
			 * request.getAmount() + "|" + request.getRequestType() + "|" +
			 * "TESTING" + "|" + null;
			 */
			String SendData = encryptedData + "|" + mid + "|" + tid + "|" + Amount + "|" + requestType + "|" + Environment + "|" + null;
			System.out.println("Request String:" + SendData);
			try (Socket s = new Socket(host, port)) // Creating socket class
			{
				DataOutputStream dout = new DataOutputStream(s.getOutputStream()); // creating
																					// outputstream
																					// to
																					// send
																					// data
																					// to
																					// server
				DataInputStream din = new DataInputStream(s.getInputStream()); // creating
																				// inputstream
																				// to
																				// receive
																				// data
																				// from
																				// server
				dout.writeUTF(SendData); // Sending the finalRequest String to
											// server
				// System.out.println("Request sent to Server...");
				dout.flush(); // Flush the streams

				byte[] bs = new byte[10024];
				din.read(bs);

				char c;
				for (byte b : bs) {
					c = (char) b;
					response = Res.append("").append(c).toString();
					// response = c+;
					// System.out.println("Server Response:\n" + response);
				}

				dout.close(); // Closing the output stream
				din.close(); // Closing the input stream
			} // creating outputstream to send data to server

			String strRes = response.trim();
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(strRes);
			String token = (String) jsonObject.get("newToken");
			if (token != null) {
				clsJioMoneyEncryption objDecpt = new clsJioMoneyEncryption();
				String getToken = objDecpt.Decrypt(token, secretKey);

				String posURL = POSWSURL + "/WebPOSSetup/funSetToken" + "?token=" + token + "&posCode=" + posCode + "&mid=" + mid;

				objWSGlobalFunctions.funGETMethodUrlJosnObjectData(posURL);
			}
			return response;
		} catch (Exception e) {
			System.out.println("Exception:" + e);
			return null;
		}
		// //Socket Base transaction end
	}

	public JSONArray funFillCustTypeCombo(String clientCode) {

		String posUrl = POSWSURL + "/APOSMastersIntegration/funFillCustTypeCombo?clientCode=" + clientCode;
		System.out.println(posUrl);
		JSONArray jArrList = null;
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
			JSONObject jObjPOSList = (JSONObject) obj;
			jArrList = (JSONArray) jObjPOSList.get("CustomerTypeList");

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return jArrList;

	}

	public JSONArray funGetAllCustomerAreaForMaster(String clientCode) {

		String posUrl = POSWSURL + "/APOSMastersIntegration/funGetAllCustomerAreaForMaster?clientCode=" + clientCode;
		System.out.println(posUrl);
		JSONArray jArrList = null;
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
			JSONObject jObjPOSList = (JSONObject) obj;
			jArrList = (JSONArray) jObjPOSList.get("CustomerAreaList");

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return jArrList;

	}

	public JSONArray funGetAllZoneForMaster(String clientCode) {

		String posUrl = POSWSURL + "/APOSMastersIntegration/funGetAllZoneForMaster?clientCode=" + clientCode;
		System.out.println(posUrl);
		JSONArray jArrList = null;
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
			JSONObject jObjZoneList = (JSONObject) obj;
			jArrList = (JSONArray) jObjZoneList.get("ZoneList");

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return jArrList;

	}

	public JSONArray funFillCustomerAreaForMaster(String clientCode, String zoneCode) {

		String posUrl = POSWSURL + "/APOSMastersIntegration/funFillCustomerAreaForMaster?clientCode=" + clientCode + "&zoneCode=" + zoneCode;
		System.out.println(posUrl);
		JSONArray jArrList = null;
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
			JSONObject jObjPOSList = (JSONObject) obj;
			jArrList = (JSONArray) jObjPOSList.get("CustomerAreaList");

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return jArrList;

	}

	public JSONArray funGetAllCustomerType(String clientCode) {

		JSONArray jArrList = null;
		String posURL = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funGetAllCustomerType?clientCode=" + clientCode;
		// JSONObject
		// jObj=objWSGlobalFunctions.funGETMethodUrlJosnObjectData(posURL);
		// JSONObject jObjPOSList = (JSONObject) jObj;
		// jArrList = (JSONArray) jObjPOSList.get("CustomerTypeList");

		try {
			URL url = new URL(posURL);
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
			JSONObject jObjCardList = (JSONObject) obj;
			jArrList = (JSONArray) jObjCardList.get("CustomerTypeList");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return jArrList;
	}

	public JSONArray funGetPOSName(String posCode) {

		String posURL = null;
		JSONArray jArrList = null;
		posURL = POSWSURL + "/APOSMastersIntegration/funGetPOSName?posCode=" + posCode;
		JSONObject jObj = objWSGlobalFunctions.funGETMethodUrlJosnObjectData(posURL);
		JSONObject jObjPOSList = (JSONObject) jObj;
		jArrList = (JSONArray) jObjPOSList.get("POSList");
		return jArrList;

	}

	public JSONArray funFillItemTable(String clientCode) {

		String posURL = null;
		JSONArray jArrList = null;
		posURL = POSWSURL + "/APOSMastersIntegration/funFillItemTable?clientCode=" + clientCode;
		JSONObject jObj = objWSGlobalFunctions.funGETMethodUrlJosnObjectData(posURL);
		JSONObject jObjPOSList = (JSONObject) jObj;
		jArrList = (JSONArray) jObjPOSList.get("itemList");
		return jArrList;

	}

}
