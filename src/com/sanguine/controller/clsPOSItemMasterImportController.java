package com.sanguine.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
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
import com.sanguine.crm.model.clsInvoiceTaxDtlModel;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsLocationMasterService;
import com.sanguine.service.clsProductMasterService;

@Controller
public class clsPOSItemMasterImportController {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	private clsGlobalFunctions objGlobal = null;

	@Autowired
	private clsLocationMasterService objLocationMasterService;

	@Autowired
	private clsProductMasterService objProductMasterService;

	@RequestMapping(value = "/frmPOSItemMasterImport", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		List<String> listType = new ArrayList<>();
		List<String> listTypeLoc = new ArrayList<>();

		String sqlSGName = "select strSGName from tblsubgroupmaster where strClientCode='" + clientCode + "' ";
		listType = objGlobalFunctionsService.funGetDataList(sqlSGName, "sql");
		model.put("listType", listType);

		String sqlLocName = "select strLocName from tbllocationmaster where strClientCode='" + clientCode + "' ";
		listTypeLoc = objGlobalFunctionsService.funGetDataList(sqlLocName, "sql");
		model.put("listTypeLoc", listTypeLoc);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSItemMasterImport_1", "command", new clsPOSItemMasterImportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSItemMasterImport", "command", new clsPOSItemMasterImportBean());
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/loadPOSData", method = RequestMethod.GET)
	public @ResponseBody List funLoadPOSData(clsPOSItemMasterImportBean objBean, HttpServletRequest req) {
		List<clsProductMasterModel> listPOSitem = new LinkedList<clsProductMasterModel>();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String param = req.getParameter("param").toString();
		String[] spParam = param.split(",");
		String IpAdd = spParam[0];
		String port = spParam[1];
		String dbName = spParam[2];
		String userName = spParam[3];
		String pass = spParam[4];
		String sGName = spParam[5];
		String locName = spParam[6];

		String sqlGetCode = "select strSGCode from tblsubgroupmaster where strSGName='" + sGName + "'  and strClientCode='" + clientCode + "' ";
		List listGetCode = objGlobalFunctionsService.funGetDataList(sqlGetCode, "sql");
		String sGCode = listGetCode.get(0).toString();
		sqlGetCode = "";
		listGetCode.clear();

		sqlGetCode = "select strLocCode from tbllocationmaster where strLocName='" + locName + "'  and strClientCode='" + clientCode + "' ";
		listGetCode = objGlobalFunctionsService.funGetDataList(sqlGetCode, "sql");
		String locCode = listGetCode.get(0).toString();

		// String sql =
		// "select  strItemCode,strItemName from tblitemmaster where strWSProdCode='NA' or strWSProdCode='' and strClientCode='"
		// + clientCode + "'  ";

		// String
		// sql=" select a.strItemCode ,a.strItemName from tblitemmaster a  where a.strItemCode IN( select strItemCode from tblitemmasterlinkupdtl where strWSProductCode='' )    ";
		String sql = "  select a.strItemCode,a.strItemName ,b.strWSProductCode " + " from tblitemmaster a ,tblitemmasterlinkupdtl b " + " where a.strItemCode=b.strItemCode and b.strWSProductCode='' group by a.strItemCode";
		listPOSitem = dbConnectionForPOS(IpAdd, port, dbName, userName, pass, sql, clientCode, sGCode, locCode);

		return listPOSitem;
	}

	private List<clsProductMasterModel> dbConnectionForPOS(String IpAdd, String port, String dbName, String userName, String pass, String sql, String clientCode, String sGCode, String locCode) {
		/*
		 * HashMap<String, Object> mapPOSitem=new HashMap<String, Object>();
		 * LinkedList code = new LinkedList(); LinkedList value = new
		 * LinkedList(); try{
		 * 
		 * Class.forName("com.mysql.jdbc.Driver"); Connection
		 * con=DriverManager.getConnection
		 * ("jdbc:mysql://"+IpAdd+":"+port+"/"+dbName+"", userName, pass);
		 * System.out.println("connection sucecss");
		 * 
		 * Statement st=con.createStatement(); ResultSet
		 * rsPOS=st.executeQuery(sql);
		 * 
		 * while(rsPOS.next()) { code.add(rsPOS.getString(1).toString());
		 * value.add(rsPOS.getString(2).toString());
		 * 
		 * } mapPOSitem.put("Code", code); mapPOSitem.put("Value", value);
		 * con.close();
		 */

		LinkedList Datalist = new LinkedList();
		try {

			// Class.forName("com.mysql.jdbc.Driver");
			// Connection
			// con=DriverManager.getConnection("jdbc:mysql://"+IpAdd+":"+port+"/"+dbName+"",
			// userName, pass);

			Connection con = funConnection(IpAdd, port, dbName, userName, pass);

			Statement st = con.createStatement();
			ResultSet rsPOS = st.executeQuery(sql);

			while (rsPOS.next()) {
				clsProductMasterModel obj = null;
				obj = objProductMasterService.funGetImportedPOSItem(rsPOS.getString(1).toString(), rsPOS.getString(2).toString(), clientCode, sGCode, locCode);
				if (obj != null) {// ////////////////////////////////////////////////////////////////////////////////////////////////////
					obj.setStrPosItemCode(rsPOS.getString(1).toString());
					obj.setStrPOSItemName(rsPOS.getString(2).toString());
					Datalist.add(obj);
				} else {
					obj = new clsProductMasterModel();
					obj.setStrPosItemCode(rsPOS.getString(1).toString());
					obj.setStrPOSItemName(rsPOS.getString(2).toString());

					Datalist.add(obj);
				}

			}
			con.close();
		}

		catch (Exception ex) {
			ex.printStackTrace();
		}

		return Datalist;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/savePOSItemMasterImport", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSItemMasterImportBean objBean, BindingResult result, HttpServletRequest request) {
		String updatePrice = objBean.getStrShowLinked();
		if (updatePrice == null) {
			updatePrice = "N";
		}

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
			clsProductMasterModel objPurerModel = (clsProductMasterModel) objBean.getListPOSRecipe().get(0);
			System.out.println(objPurerModel.getStrSelectedPOSItem());
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		if (updatePrice.equalsIgnoreCase("N")) {

			if (!result.hasErrors()) {
				String clientCode = request.getSession().getAttribute("clientCode").toString();
				String userCode = request.getSession().getAttribute("usercode").toString();

				String sqlGetCode = "select strLocCode from tbllocationmaster where strLocName='" + objBean.getStrLocName() + "'  and strClientCode='" + clientCode + "' ";
				List listGetCode = objGlobalFunctionsService.funGetDataList(sqlGetCode, "sql");
				String locCode = listGetCode.get(0).toString();

				String posCode = "All";
				String sqlPOSCode = "select strExternalCode from tbllocationmaster where strLocCode='" + locCode + "' and strClientCode='" + clientCode + "'";
				List list1 = objGlobalFunctionsService.funGetList(sqlPOSCode, "sql");
				if (list1.size() > 0 && !list1.isEmpty() && !list1.get(0).toString().equals("")) {
					posCode = list1.get(0).toString();
					posCode = posCode.substring(8, posCode.length());
				}
				List<clsProductMasterModel> listObjModel = funPrepareModel(objBean, userCode, clientCode, request);
				int listSizeOfProductModel = listObjModel.size();
				for (int i = 0; i < listSizeOfProductModel; i++) {
					if (listObjModel.get(i) != null) {

						long lastNo = 0;
						String productCode = "";
						clsProductMasterModel objModel = new clsProductMasterModel();
						objModel = listObjModel.get(i);
						lastNo = objGlobalFunctionsService.funGetLastNo("tblproductmaster", "ProductMaster", "intId", clientCode);
						productCode = "P" + String.format("%07d", lastNo);

						objModel.setIntId(lastNo);
						objModel.setStrProdCode(productCode);
						objModel.setStrExciseable("N");
						objModel.setStrManufacturerCode("");
						objProductMasterService.funAddUpdateGeneral(objModel);

						// for POS Linkup of item

						Connection con = funConnection(objBean.getStrIPAddress(), objBean.getStrPortNo(), objBean.getStrDBName(), objBean.getStrUserName(), objBean.getStrPass());

						String sqlDel = " delete from tblitemmasterlinkupdtl where strItemCode='" + objModel.getStrPosItemCode() + "' and strPOSCode='" + posCode + "' and strWSProductCode ='" + productCode + "' and strClientCode='" + clientCode + "'  ";

						String sqlPOSItemMasterlinkup = "Insert tblitemmasterlinkupdtl (`strItemCode`,`strPOSCode`,`strWSProductCode`,`strWSProductName`,`strClientCode`,`strDataPostFlag`) values " + " ('" + objModel.getStrPosItemCode() + "' ,'" + posCode + "','" + productCode + "','" + objModel.getStrPOSItemName() + "','" + clientCode + "','N') ";
						String sqlPOSItemUpdate = " update tblitemmaster set strWSProdCode = '" + productCode + "' " + " where strItemCode='" + objModel.getStrPosItemCode() + "' and strClientCode = '" + clientCode + "' ";
						try {
							Statement st = con.createStatement();
							st.executeUpdate(sqlDel);
							st.executeUpdate(sqlPOSItemMasterlinkup);
							st.executeUpdate(sqlPOSItemUpdate);
							st.close();
							con.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				}

				request.getSession().setAttribute("success", true);
				request.getSession().setAttribute("successMessage", "Product Saved : ".concat(""));
				return new ModelAndView("redirect:/frmPOSItemMasterImport.html?saddr=" + urlHits);
			} else {
				return new ModelAndView("redirect:/frmPOSItemMasterImport.html?saddr=" + urlHits);
			}

		} else {

			String clientCode = request.getSession().getAttribute("clientCode").toString();
			String userCode = request.getSession().getAttribute("usercode").toString();
			String sqlGetCode = "select strLocCode from tbllocationmaster where strLocName='" + objBean.getStrLocName() + "'  and strClientCode='" + clientCode + "' ";
			List listGetCode = objGlobalFunctionsService.funGetDataList(sqlGetCode, "sql");
			String locCode = listGetCode.get(0).toString();

			String posCode = "";
			String sqlPOSCode = "select strExternalCode from tbllocationmaster where strLocCode='" + locCode + "' and strClientCode='" + clientCode + "'";
			List list1 = objGlobalFunctionsService.funGetList(sqlPOSCode, "sql");
			if (list1.size() > 0 && !list1.isEmpty() && !list1.get(0).toString().equals("")) {
				posCode = list1.get(0).toString();
				posCode = posCode.substring(8, posCode.length());
			}

			if (!result.hasErrors()) {

				Connection con = funConnection(objBean.getStrIPAddress(), objBean.getStrPortNo(), objBean.getStrDBName(), objBean.getStrUserName(), objBean.getStrPass());
				String sqlPCode = "select a.strPCode from tblpartymaster a where a.strManualCode='" + clientCode + "' ";
				String strPCode = "";

				List listPCode = objGlobalFunctionsService.funGetList(sqlPCode, "sql");
				if (listPCode.size() > 0 && !listPCode.isEmpty() && !listPCode.get(0).toString().equals("")) {
					strPCode = listPCode.get(0).toString();
				}

				List<clsProductMasterModel> list = objBean.getListPOSRecipe();
				clsProductMasterModel objModel = new clsProductMasterModel();
				for (int cnt = 0; cnt < list.size(); cnt++) {
					objModel = list.get(cnt);
					if (objModel.getStrSelectedPOSItem() != null) {
						if (objModel.getStrSelectedPOSItem().toString().equalsIgnoreCase("Tick")) {
							String sqlPOSPrice = " select a.strPriceMonday from jpos.tblmenuitempricingdtl a where a.strItemCode='" + objModel.getStrPosItemCode() + "' ";

							List listPOSPrice = objGlobalFunctionsService.funGetList(sqlPOSPrice, "sql");
							double dlbPrice = 0.0;
							if (listPOSPrice.size() > 0 && !listPOSPrice.isEmpty() && !listPOSPrice.get(0).toString().equals("")) {

								dlbPrice = Double.parseDouble(listPOSPrice.get(0).toString());
								String sqlPOSPriceUpdate = "update tblproductmaster a set a.dblMRP='" + dlbPrice + "' where a.strPartNo='" + objModel.getStrPosItemCode() + "' ";
								objGlobalFunctionsService.funUpdateAllModule(sqlPOSPriceUpdate, "sql");

								String sql = "select a.strProdCode  from tblproductmaster a where a.strPartNo='" + objModel.getStrPosItemCode() + "' ";
								List listProdCode = objGlobalFunctionsService.funGetList(sql, "sql");

								if (listProdCode.size() > 0 && !listProdCode.isEmpty() && !listProdCode.get(0).toString().equals("")) {
									String podCode = (String) listProdCode.get(0);
									String sqlProdPriceUpdateCRM = "update tblprodsuppmaster a  set a.dblLastCost='" + dlbPrice + "', dblStandingOrder='1' where   a.strSuppCode='" + strPCode + "' and a.strProdCode='" + podCode + "' ";
									objGlobalFunctionsService.funUpdateAllModule(sqlProdPriceUpdateCRM, "sql");

									String sqlDel = " delete from tblitemmasterlinkupdtl where strItemCode='" + objModel.getStrPosItemCode() + "' and strPOSCode='" + posCode + "' and strWSProductCode ='" + podCode + "' and strClientCode='" + clientCode + "'  ";

									String sqlPOSItemMasterlinkup = "Insert tblitemmasterlinkupdtl (`strItemCode`,`strPOSCode`,`strWSProductCode`,`strWSProductName`,`strClientCode`,`strDataPostFlag`) values " + " ('" + objModel.getStrPosItemCode() + "' ,'" + posCode + "','" + podCode + "','" + objModel.getStrPOSItemName() + "','" + clientCode + "','N') ";
									String sqlPOSItemUpdate = " update tblitemmaster set strWSProdCode = '" + podCode + "' " + " where strItemCode='" + objModel.getStrPosItemCode() + "' and strClientCode = '" + clientCode + "' ";

									try {
										Statement st = con.createStatement();
										st.executeUpdate(sqlDel);
										st.executeUpdate(sqlPOSItemMasterlinkup);
										st.executeUpdate(sqlPOSItemUpdate);
										st.close();
										con.close();
									} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								}

							}

						}
					}

				}
				request.getSession().setAttribute("success", true);
				request.getSession().setAttribute("successMessage", "Update Product Price ".concat(""));
				return new ModelAndView("redirect:/frmPOSItemMasterImport.html?saddr=" + urlHits);

			}

			else {
				return new ModelAndView("redirect:/frmPOSItemMasterImport.html?saddr=" + urlHits);
			}
		}
	}

	private List<clsProductMasterModel> funPrepareModel(clsPOSItemMasterImportBean objBean, String userCode, String clientCode, HttpServletRequest request) {

		int listSize = objBean.getListPOSRecipe().size();
		List<clsProductMasterModel> listObjbjModel = new ArrayList<clsProductMasterModel>();
		objGlobal = new clsGlobalFunctions();
		for (int i = 0; i < listSize; i++) {
			List<clsProductMasterModel> listModel = objBean.getListPOSRecipe();
			clsProductMasterModel objPurerModel = listModel.get(i);
			if (objPurerModel.getStrSelectedPOSItem() != null) {
				if (objPurerModel.getStrSelectedPOSItem().toString().equalsIgnoreCase("Tick")) {
					clsProductMasterModel objModel = new clsProductMasterModel();

					objModel.setStrUserCreated(userCode);
					objModel.setStrUserModified(userCode);
					objModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					objModel.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					objModel.setStrClientCode(clientCode);
					objModel.setStrPartNo(objPurerModel.getStrPosItemCode());
					objModel.setStrProdName(objPurerModel.getStrPOSItemName());

					String sqlGetCode = "select strSGCode from tblsubgroupmaster where strSGName='" + objBean.getStrSGName() + "'  and strClientCode='" + clientCode + "' ";
					List listGetCode = objGlobalFunctionsService.funGetDataList(sqlGetCode, "sql");
					objModel.setStrSGCode(listGetCode.get(0).toString());
					sqlGetCode = "";
					listGetCode.clear();

					sqlGetCode = "select strLocCode from tbllocationmaster where strLocName='" + objBean.getStrLocName() + "'  and strClientCode='" + clientCode + "' ";
					listGetCode = objGlobalFunctionsService.funGetDataList(sqlGetCode, "sql");
					objModel.setStrLocCode(listGetCode.get(0).toString());
					sqlGetCode = "";
					listGetCode.clear();
					objModel.setStrUOM("NOS");
					objModel.setStrProdType("Produced");
					objModel.setStrSaleNo("");
					objModel.setStrDesc("");
					objModel.setStrTaxIndicator("");
					objModel.setStrExceedPO("");
					objModel.setStrType("");
					objModel.setStrSpecification("");
					objModel.setStrExpDate("");
					objModel.setStrForSale("");
					objModel.setStrLocName(objBean.getStrLocName());
					objModel.setStrLotNo("");
					objModel.setStrNonStockableItem("");
					objModel.setStrNotInUse("N");
					objModel.setStrPosItemCode(objPurerModel.getStrPosItemCode());
					objModel.setStrProductImage(funBlankBlob());
					objModel.setStrRevLevel("");
					objModel.setStrSelectedPOSItem("");
					objModel.setStrSGName(objBean.getStrSGName());
					objModel.setStrSlNo("");
					objModel.setStrSuppPartDesc("");
					objModel.setStrSuppPartNo("");
					objModel.setStrPOSItemName(objPurerModel.getStrPOSItemName());
					objModel.setStrBomCal("");
					;
					objModel.setStrWtUOM("");
					;
					objModel.setStrBinNo("");
					objModel.setStrCalAmtOn("");
					objModel.setStrBinNo("");
					objModel.setStrClass("");
					objModel.setStrTariffNo("");
					objModel.setStrRemark("");
					objModel.setStrReceivedUOM("NOS");
					objModel.setStrIssueUOM("NOS");
					objModel.setStrRecipeUOM("NOS");
					objModel.setStrStagDel("");
					objModel.setStrBarCode("");
					objModel.setStrPickMRPForTaxCal("N");
					objModel.setStrProdNameMarathi("");
					objModel.setStrManufacturerCode("");
					listObjbjModel.add(objModel);
				}
			}

		}
		return listObjbjModel;
	}

	private Blob funBlankBlob() {
		Blob blob = new Blob() {

			@Override
			public void truncate(long len) throws SQLException {
				// TODO Auto-generated method stub

			}

			@Override
			public int setBytes(long pos, byte[] bytes, int offset, int len) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int setBytes(long pos, byte[] bytes) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public OutputStream setBinaryStream(long pos) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public long position(Blob pattern, long start) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public long position(byte[] pattern, long start) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public long length() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public byte[] getBytes(long pos, int length) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public InputStream getBinaryStream(long pos, long length) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public InputStream getBinaryStream() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void free() throws SQLException {
				// TODO Auto-generated method stub

			}
		};
		return blob;
	}

	@SuppressWarnings("finally")
	public Connection funConnection(String IpAdd, String port, String dbName, String userName, String pass) {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://" + IpAdd + ":" + port + "/" + dbName + "", userName, pass);

		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		} finally {
			return con;
		}

	}

	@RequestMapping(value = "/loadLinkProdPOSData", method = RequestMethod.GET)
	public @ResponseBody List funLoadLinkProdPOSData(clsPOSItemMasterImportBean objBean, HttpServletRequest req) {
		List<clsProductMasterModel> listPOSitem = new LinkedList<clsProductMasterModel>();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String param = req.getParameter("param").toString();
		String[] spParam = param.split(",");
		String IpAdd = spParam[0];
		String port = spParam[1];
		String dbName = spParam[2];
		String userName = spParam[3];
		String pass = spParam[4];
		String sGName = spParam[5];
		String locName = spParam[6];

		String sqlGetCode = "select strSGCode from tblsubgroupmaster where strSGName='" + sGName + "'  and strClientCode='" + clientCode + "' ";
		List listGetCode = objGlobalFunctionsService.funGetDataList(sqlGetCode, "sql");
		String sGCode = listGetCode.get(0).toString();
		sqlGetCode = "";
		listGetCode.clear();

		sqlGetCode = "select strLocCode from tbllocationmaster where strLocName='" + locName + "'  and strClientCode='" + clientCode + "' ";
		listGetCode = objGlobalFunctionsService.funGetDataList(sqlGetCode, "sql");
		String locCode = listGetCode.get(0).toString();

		String sql = "select  b.strItemCode,a.strItemName from tblitemmaster a,tblitemmasterlinkupdtl b where a.strItemCode=b.strItemCode   and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' ";
		listPOSitem = dbConnectionForPOS(IpAdd, port, dbName, userName, pass, sql, clientCode, sGCode, locCode);

		return listPOSitem;
	}

	@RequestMapping(value = "/testDBConnection", method = RequestMethod.GET)
	public @ResponseBody String funTestDBConnection(clsPOSItemMasterImportBean objBean, HttpServletRequest req) {
		String code = "500";
		String param = req.getParameter("param").toString();
		String[] spParam = param.split(",");
		String IpAdd = spParam[0];
		String port = spParam[1];
		String dbName = spParam[2];
		String userName = spParam[3];
		String pass = spParam[4];
		Connection con = funConnection(IpAdd, port, dbName, userName, pass);
		if (con != null) {
			code = "200";
		}
		return code;
	}

}
