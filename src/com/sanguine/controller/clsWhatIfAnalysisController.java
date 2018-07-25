package com.sanguine.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.bean.clsMISBean;
import com.sanguine.bean.clsWhatIfAnalysisBean;
import com.sanguine.bean.clsWorkOrderBean;
import com.sanguine.model.clsBomDtlModel;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.model.clsWhatIfAnalysisModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsProductMasterService;
import com.sanguine.controller.clsWhatIfAnalysisController;

@Controller
public class clsWhatIfAnalysisController {
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	clsGlobalFunctions objGlobal;

	@Autowired
	clsGlobalFunctions objGlobalFunction;

	@Autowired
	clsProductMasterService objProductMasterService;

	// List listChildNodes;
	Map<String, List<String>> mapChildNodes;
	List<List<String>> listChildNodes;
	public List listChildNodes1;
	List<clsWhatIfAnalysisModel> listWhatIfAnalysis;

	@RequestMapping(value = "/frmWhatIfAnalysis", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		clsWhatIfAnalysisBean objBean = new clsWhatIfAnalysisBean();
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWhatIfAnalysis_1", "command", objBean);
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWhatIfAnalysis", "command", objBean);
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/saveWhatIfAnanlysis", method = RequestMethod.POST)
	public ModelAndView funSaveWhatIf(@ModelAttribute("command") clsWhatIfAnalysisBean objBean, Model model, BindingResult result, HttpServletRequest req) {
		mapChildNodes = new HashMap<String, List<String>>();
		listChildNodes = new ArrayList<List<String>>();
		listWhatIfAnalysis = new ArrayList<clsWhatIfAnalysisModel>();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		for (String product : objBean.getListProduct()) {
			System.out.println("prod=" + product + "\tqty=" + product.split(",")[3]);
			List list = funGetAllChildNodes(product.split(",")[0], clientCode, Double.parseDouble(product.split(",")[3]));
		}
		return new ModelAndView("frmWhatIfAnalysisFlash");
	}

	@RequestMapping(value = "/loadProductFromRecipe", method = RequestMethod.GET)
	public @ResponseBody List funLoadProduct(HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		objGlobal = new clsGlobalFunctions();
		String prodCode = request.getParameter("prodCode").toString();
		String sql = "select a.strParentCode,b.strPartNo,b.strProdName,b.dblCostRM " + "from clsBomHdModel a, clsProductMasterModel b " + "where a.strParentCode=b.strProdCode and a.strParentCode='" + prodCode + "' " + "and a.strClientCode='" + clientCode + "'";
		List listRecipeProduct = objGlobalFunctionsService.funGetList(sql, "hql");
		return listRecipeProduct;
	}

	@RequestMapping(value = "/getChildNodes", method = RequestMethod.GET)
	public @ResponseBody List<List<String>> funGetChildNodes(HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		objGlobal = new clsGlobalFunctions();
		String param = request.getParameter("prodCode").toString();
		String[] sp = param.split(",");
		mapChildNodes = new HashMap<String, List<String>>();
		listChildNodes = new ArrayList<List<String>>();
		List list = funGetAllChildNodes(sp[0], clientCode, Double.parseDouble(sp[1]));
		// return mapChildNodes;
		return listChildNodes;
	}

	@RequestMapping(value = "/getChildNodes1", method = RequestMethod.GET)
	public @ResponseBody Map<String, List> funGetChildNodes1(HttpServletRequest request) {
		Map<String, List> hmChildNodes = new HashMap<String, List>();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		listChildNodes1 = new ArrayList<String>();
		String param = request.getParameter("prodCode").toString();
		System.out.println(param);
		param = param.substring(1, param.length());
		String[] sp = param.split(",");
		for (int cn = 0; cn < sp.length; cn++) {
			String sp1[] = sp[cn].split("!");
			double reqdQty = Double.parseDouble(sp1[1]);
			funGetBOMNodes(sp1[0], 0, reqdQty);
		}

		for (int cnt = 0; cnt < listChildNodes1.size(); cnt++) {
			List arrListBOMProducts = new ArrayList<String>();
			String temp = (String) listChildNodes1.get(cnt);
			String prodCode = temp.split(",")[0];
			double reqdQty = Double.parseDouble(temp.split(",")[1]);
			double openPOQty = funGetOpenPOQty(prodCode, clientCode);
			String startDate = request.getSession().getAttribute("startDate").toString();
			String[] fmDate = startDate.split(" ");
			String[] spDate = fmDate[0].split("/");
			startDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0];
			String toDate = objGlobal.funGetCurrentDateTime("yyyy-MM-dd");
			String locationCode = request.getSession().getAttribute("locationCode").toString();
			double currentStock = objGlobalFunction.funGetCurrentStockForProduct(prodCode, locationCode, clientCode, userCode, startDate, toDate);
			// double currentStock=0;
			double orderQty = reqdQty - (openPOQty + currentStock);
			if (orderQty < 0)
				orderQty = 0;
			System.out.println("Prod=" + prodCode + "\topenPO=" + openPOQty + "\tstk=" + currentStock + "\tOrder Qty=" + orderQty);
			String productInfo = funGetProdInfo(prodCode);
			String leadTime = "0", prodName = "", uom = "", suppCode = "", suppName = "";

			if (productInfo.trim().length() > 0) {
				String[] spProd = productInfo.split("#");
				prodName = spProd[0];
				uom = spProd[1];
				suppCode = spProd[2];
				suppName = spProd[3];
				if (spProd.length > 4) {
					leadTime = spProd[4];
				}

			} else {
				clsProductMasterModel objModel = objProductMasterService.funGetObject(prodCode, clientCode);
				prodName = objModel.getStrProdName();
			}

			Date today = new Date();
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(leadTime));
			Date expDate = cal.getTime();
			String expectedDate = expDate.getDate() + "/" + (expDate.getMonth() + 1) + "/" + (expDate.getYear() + 1900);

			if (null != hmChildNodes.get(prodCode)) {
				List arrListTemp = hmChildNodes.get(prodCode);
				reqdQty = reqdQty + Double.parseDouble(arrListTemp.get(3).toString());
				currentStock = currentStock + Double.parseDouble(arrListTemp.get(4).toString());
				openPOQty = openPOQty + Double.parseDouble(arrListTemp.get(5).toString());
				orderQty = orderQty + Double.parseDouble(arrListTemp.get(6).toString());

				System.out.println(Double.parseDouble(arrListTemp.get(3).toString()));
				System.out.println(Double.parseDouble(arrListTemp.get(4).toString()));
				hmChildNodes.remove(prodCode);
			}

			double bomrate = funGetBOMRate(prodCode, clientCode);

			arrListBOMProducts.add(prodCode);
			arrListBOMProducts.add(prodName);
			arrListBOMProducts.add(uom);
			arrListBOMProducts.add(reqdQty);
			arrListBOMProducts.add(currentStock);
			arrListBOMProducts.add(openPOQty);
			arrListBOMProducts.add(orderQty);
			arrListBOMProducts.add(suppCode);
			arrListBOMProducts.add(suppName);
			arrListBOMProducts.add(leadTime);
			arrListBOMProducts.add(bomrate * reqdQty);
			arrListBOMProducts.add(expectedDate);

			hmChildNodes.put(prodCode, arrListBOMProducts);
		}

		return hmChildNodes;
	}

	public String funGetProdInfo(String prodCode) {
		String prodInfo = "";
//		String sql = " select ifnull(a.strProdName,''),ifnull(a.strReceivedUOM,''),ifnull(b.strSuppCode,''),ifnull(c.strPName,''),ifnull(b.strLeadTime,'0') " + " from tblproductmaster a " + " left outer join tblprodsuppmaster b  on a.strProdCode=b.strProdCode  and b.strDefault='Y' " + " left outer join tblpartymaster c on b.strSuppCode=c.strPCode " + " where  a.strProdCode='" + prodCode + "'  ";
		String sql = " select ifnull(a.strProdName,''),if(IFNULL(a.strRecipeUOM,'')='',IFNULL(a.strReceivedUOM,''),a.strRecipeUOM),ifnull(b.strSuppCode,''),ifnull(c.strPName,''),ifnull(b.strLeadTime,'0') " + " from tblproductmaster a " + " left outer join tblprodsuppmaster b  on a.strProdCode=b.strProdCode  and b.strDefault='Y' " + " left outer join tblpartymaster c on b.strSuppCode=c.strPCode " + " where  a.strProdCode='" + prodCode + "'  ";

		List list = objGlobalFunctionsService.funGetList(sql, "sql");
		if (list.size() > 0) {
			Object[] arrObj = (Object[]) list.get(0);
			prodInfo = arrObj[0] + "#" + arrObj[1] + "#" + arrObj[2] + "#" + arrObj[3] + "#" + arrObj[4];
		}

		return prodInfo;
	}

	public double funGetOpenPOQty(String prodCode, String clientCode) {
		BigDecimal openPOQty = new BigDecimal(0);
		double dblPOQty = 0;
		String sql = "select a.dblOrdQty - ifnull(b.POQty,0) as BalanceQty " + "from tblpurchaseorderdtl a left outer join (SELECT b.strCode AS POCode, b.strProdCode, SUM(b.dblQty) AS POQty " + "FROM tblgrnhd a INNER JOIN tblgrndtl b ON a.strGRNCode = b.strGRNCode " + "WHERE (a.strAgainst = 'Purchase Order') GROUP BY POCode, b.strProdCode) b on a.strPOCode = b.POCode "
				+ "and a.strProdCode = b.strProdCode left outer join tblproductmaster c on a.strProdCode=c.strProdCode " + "where a.dblOrdQty > ifnull(b.POQty,0) and a.strProdCode='" + prodCode + "' and a.strClientCode='" + clientCode + "'";
		List list = objGlobalFunctionsService.funGetList(sql, "sql");
		if (list.size() > 0) {
			// openPOQty = (double) list.get(0);
			Object ob = list.get(0);
			openPOQty = (BigDecimal) ob;
			dblPOQty = openPOQty.doubleValue();
		}

		return dblPOQty;
	}

	public List funGetBOMNodes(String parentProdCode, double bomQty, double qty) {
		double finalQty = 0;
		List listTemp = new ArrayList<String>();
		String sql = "select b.strChildCode from  tblbommasterhd a,tblbommasterdtl b " + "where a.strBOMCode=b.strBOMCode and a.strParentCode='" + parentProdCode + "' ";
		listTemp = objGlobalFunctionsService.funGetList(sql, "sql");
		if (listTemp.size() > 0) {
			for (int cnt = 0; cnt < listTemp.size(); cnt++) {
				String childNode = (String) listTemp.get(cnt);
				bomQty = funGetBOMQty(childNode, parentProdCode);
				// qty=qty*bomQty;
				// System.out.println(childNode+"\tbom="+bomQty+"\tQty="+qty);
				funGetBOMNodes(childNode, bomQty, qty * bomQty);
			}
			finalQty = qty;
		} else {
			listChildNodes1.add(parentProdCode + "," + qty);
		}
		return listChildNodes1;
	}

	public double funGetBOMQty(String childCode, String parentCode) {
		double bomQty = 0;
		try {
			String sql = "select ifnull(left(((c.dblReceiveConversion/c.dblIssueConversion)/c.dblRecipeConversion),6) * b.dblQty,0) as BOMQty " + "from tblbommasterhd a,tblbommasterdtl b,tblproductmaster c ,tblproductmaster d " + "where a.strBOMCode=b.strBOMCode and a.strParentCode=d.strProdCode and a.strParentCode='" + parentCode + "' and b.strChildCode=c.strProdCode " + "and b.strChildCode='"
					+ childCode + "'";
			List listChildQty = objGlobalFunctionsService.funGetList(sql, "sql");
			if (listChildQty.size() > 0) {
				bomQty = (Double) listChildQty.get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bomQty;
	}

	public double funGetBOMRate(String childCode, String clientCode) {
		double bomRate = 0;
		try {
			String sql = "select ifNull(a.dblCostRM,0) as Rate  from tblproductmaster a where a.strProdCode='" + childCode + "' and a.strClientCode='" + clientCode + "' ";
			List listChildRate = objGlobalFunctionsService.funGetList(sql, "sql");
			if (listChildRate.size() > 0) {
				System.out.println(listChildRate.get(0));
				bomRate = Double.parseDouble(listChildRate.get(0).toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bomRate;
	}

	private List funGetAllChildNodes(String prodCode, String clientCode, double qty) {
		List list = new ArrayList<String>();

		/*
		 * String sql=
		 * "select a.strParentCode,b.strChildCode, c.strProdName, c.strPartNo, b.dblQty "
		 * + "from clsBomHdModel a ,clsBomDtlModel b ,clsProductMasterModel c "
		 * +
		 * "where a.strBOMCode=b.strBOMCode and b.strChildCode=c.strProdCode and a.strParentCode='"
		 * +prodCode+"' " + "and a.strClientCode='"+clientCode+"'";
		 */

		String sql = "select b.strChildCode, c.strProdName, c.strPartNo, b.dblQty as Quantity " + ",ifnull(d.BalanceQty,0) as PurchaseQty " + ",b.dblQty-(ifnull(d.BalanceQty,0)) as OrderQty " + ",left(((c.dblReceiveConversion/c.dblIssueConversion)/c.dblRecipeConversion),6) as Conversion "
				+ ",left(((c.dblReceiveConversion/c.dblIssueConversion)/c.dblRecipeConversion),6)*(b.dblQty-(ifnull(d.BalanceQty,0))) as FinalQty " + ",ifnull(e.strSuppCode,''),ifnull(f.strPName,''),ifnull(e.strLeadTime,0),ifnull(e.dblLastCost,0)" + ",c.strReceivedUOM "
				+ "from tblbommasterhd a ,tblbommasterdtl b left outer join (select a.strProdCode as Product,a.dblOrdQty - ifnull(b.POQty,0) as BalanceQty " + "from tblpurchaseorderdtl a left outer join (SELECT b.strCode AS POCode, b.strProdCode, SUM(b.dblQty) AS POQty " + "FROM tblgrnhd a INNER JOIN tblgrndtl b ON a.strGRNCode = b.strGRNCode WHERE (a.strAgainst = 'Purchase Order') "
				+ "GROUP BY POCode, b.strProdCode) b on a.strPOCode = b.POCode and a.strProdCode = b.strProdCode " + "left outer join tblproductmaster c on a.strProdCode=c.strProdCode " + "where a.dblOrdQty > ifnull(b.POQty,0) and a.strProdCode='" + prodCode + "' and a.strClientCode='" + clientCode + "' ) d " + "on b.strChildCode=d.Product, tblproductmaster c "
				+ "left outer join tblprodsuppmaster e on c.strProdCode=e.strProdCode and e.strDefault='Y' " + "left outer join tblpartymaster f on e.strSuppCode=f.strPCode " + "where a.strBOMCode=b.strBOMCode and b.strChildCode=c.strProdCode " + "and a.strParentCode='" + prodCode + "' and a.strClientCode='" + clientCode + "'";
		System.out.println(sql);
		List listChildProducts = objGlobalFunctionsService.funGetList(sql, "sql");
		if (listChildProducts.size() > 0) {
			for (int cnt = 0; cnt < listChildProducts.size(); cnt++) {
				Object[] arrObjNodes = (Object[]) listChildProducts.get(cnt);
				double reqQty = Double.parseDouble(arrObjNodes[7].toString());
				reqQty = reqQty * qty;
				// System.out.println("Init Qty="+qty+"\tReq Qty="+reqQty);
				List listTemp = funGetAllChildNodes(arrObjNodes[0].toString(), clientCode, reqQty);

				if (listTemp.size() == 0) {
					List listNodes = new ArrayList<String>();
					// System.out.println("PROD="+arrObjNodes[0].toString()+"\tFinal Qty="+reqQty);

					listNodes.add(arrObjNodes[0].toString()); // Prod Code

					listNodes.add(arrObjNodes[1].toString()); // Prod Name

					listNodes.add(arrObjNodes[12].toString()); // Received UOM

					listNodes.add(reqQty); // Required Qty

					listNodes.add("0"); // Current Stock

					listNodes.add(arrObjNodes[5].toString()); // Open PO

					listNodes.add(arrObjNodes[7].toString()); // Order Qty

					listNodes.add(arrObjNodes[8].toString()); // Supplier Code

					listNodes.add(arrObjNodes[9].toString()); // Supplier Name

					String leadTime = objGlobal.funIfNull(arrObjNodes[10].toString(), "0", arrObjNodes[10].toString());
					listNodes.add(leadTime); // Lead Time

					double rate = Double.parseDouble(arrObjNodes[11].toString());
					double orderQty = Double.parseDouble(arrObjNodes[7].toString());
					double value = rate * orderQty;
					listNodes.add(arrObjNodes[11].toString()); // Rate
					Date today = new Date();
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(leadTime));
					Date expDate = cal.getTime();
					String expectedDate = expDate.getDate() + "/" + (expDate.getMonth() + 1) + "/" + (expDate.getYear() + 1900);
					listNodes.add(expectedDate); // Expected Date
					listNodes.add(value); // Value

					clsWhatIfAnalysisModel objWhatIfModel = new clsWhatIfAnalysisModel();
					objWhatIfModel.setStrProductCode(arrObjNodes[0].toString());
					objWhatIfModel.setStrProductName(arrObjNodes[1].toString());
					objWhatIfModel.setStrUOM(arrObjNodes[2].toString());
					objWhatIfModel.setDblCurrentStk(0);
					objWhatIfModel.setDblOpenPOQty(Double.parseDouble(arrObjNodes[5].toString()));
					objWhatIfModel.setDblOrderQty(Double.parseDouble(arrObjNodes[7].toString()));
					objWhatIfModel.setStrSuppCode(arrObjNodes[8].toString());
					objWhatIfModel.setStrSuppName(arrObjNodes[9].toString());
					objWhatIfModel.setDblRate(Double.parseDouble(arrObjNodes[11].toString()));
					objWhatIfModel.setDblLeadTime(Double.parseDouble(leadTime));
					objWhatIfModel.setStrExpectedDate(expectedDate);

					mapChildNodes.put(arrObjNodes[0].toString(), listNodes);
					listChildNodes.add(listNodes);
				}
			}
		}

		return listChildProducts;
	}

}
