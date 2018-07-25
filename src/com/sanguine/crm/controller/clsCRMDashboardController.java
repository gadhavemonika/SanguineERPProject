package com.sanguine.crm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.sanguine.crm.bean.clsBillItemDtl;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.util.clsPOSDashboardBean;
import com.sanguine.webpos.bean.clsWebPOSReportBean;
import com.sanguine.webpos.controller.clsPOSGlobalFunctionsController;


@Controller
public class clsCRMDashboardController 
{
	@Autowired
	private clsGlobalFunctionsService objGlobalService;
	
	@RequestMapping(value = "/frmCRMDashboard", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") @Valid clsPOSDashboardBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest request) {
		String strClientCode = request.getSession().getAttribute("clientCode").toString();
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCRMDashboard_1", "command", new clsPOSDashboardBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCRMDashboard", "command", new clsPOSDashboardBean());
		} else {
			return null;
		}
	}
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = { "/loadCRMSaleVSPurchaseDtl" }, method = RequestMethod.GET)
	@ResponseBody
	public clsPOSDashboardBean FunLoadCRMSaleVSPurchaseDtl(HttpServletRequest req) 
	{
		LinkedHashMap resMap = new LinkedHashMap();
		clsPOSDashboardBean objBean = new clsPOSDashboardBean();

		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String fromDate = req.getParameter("fromDate");

		String toDate = req.getParameter("toDate");

		String strReportType = req.getParameter("strReportTypedata");
		objBean = FunGetData(clientCode, fromDate, toDate,strReportType);

		return objBean;
	}

	private clsPOSDashboardBean FunGetData(String clientCode, String fromDate, String toDate,String reportType) {
		clsPOSDashboardBean objBean = new clsPOSDashboardBean();

		String fromDate1 = fromDate.split("-")[2] + "-" + fromDate.split("-")[1] + "-" + fromDate.split("-")[0];
		String toDate1 = toDate.split("-")[2] + "-" + toDate.split("-")[1] + "-" + toDate.split("-")[0];

		Map hmData = new HashMap<>();
		hmData.put("fromDate", fromDate1);
		hmData.put("toDate", toDate1);
		List<clsWebPOSReportBean> arrGraphList = new ArrayList<clsWebPOSReportBean>();
		
		
		if(reportType.equalsIgnoreCase("Group Wise"))
		{
			List jArrSearchList=funGetCRMDashboardDtl(fromDate1,toDate1,reportType);
			if (null != jArrSearchList) {
				for (int i = 0; i < jArrSearchList.size(); i++) {
					Map hmObject = (Map) jArrSearchList.get(i);
					clsWebPOSReportBean objPOSSaleBean = new clsWebPOSReportBean();
					objPOSSaleBean.setStrPOSCode(hmObject.get("GroupCode").toString());
					objPOSSaleBean.setStrPOSName(hmObject.get("GroupName").toString());
					objPOSSaleBean.setDblSettlementAmt(Math.rint(Double.parseDouble(hmObject.get("SettleAmt").toString())));
					arrGraphList.add(objPOSSaleBean);
				}
				objBean.setArrGraphList(arrGraphList);
			}
		}
		else if(reportType.equalsIgnoreCase("Region Wise"))
		{
			List jArrSearchList=funGetCRMDashboardDtl(fromDate1,toDate1,reportType);
			if (null != jArrSearchList) {
				for (int i = 0; i < jArrSearchList.size(); i++) {
					Map hmObject = (Map) jArrSearchList.get(i);
					clsWebPOSReportBean objPOSSaleBean = new clsWebPOSReportBean();
					objPOSSaleBean.setStrPOSCode(hmObject.get("GroupCode").toString());
					objPOSSaleBean.setStrPOSName(hmObject.get("GroupName").toString());
					objPOSSaleBean.setDblSettlementAmt(Math.rint(Double.parseDouble(hmObject.get("SettleAmt").toString())));
					arrGraphList.add(objPOSSaleBean);
				}
				objBean.setArrGraphList(arrGraphList);
			}
		}
		else if (reportType.equalsIgnoreCase("Month Wise")) 
		{
			HashMap<String, clsWebPOSReportBean> mapLineGraphData = new LinkedHashMap();
			clsWebPOSReportBean objPOSSaleBean = null;
			List jArrSearchList=funGetCRMDashboardDtl(fromDate1,toDate1,reportType);
			if (null != jArrSearchList) {
				for (int i = 0; i < jArrSearchList.size(); i++) {
					Map hmObject = (Map) jArrSearchList.get(i);
					objPOSSaleBean = new clsWebPOSReportBean();
					objPOSSaleBean.setStrItemCode(hmObject.get("MonthName").toString());
					objPOSSaleBean.setStrItemName(hmObject.get("MonthName").toString());
					objPOSSaleBean.setDblSettlementAmt(Math.rint(Double.parseDouble(hmObject.get("TotalAmt").toString())));
					arrGraphList.add(objPOSSaleBean);
				}
				objBean.setArrGraphList(arrGraphList);
			}

		}
		else if (reportType.equalsIgnoreCase("MonthWise Profit")) 
		{
			Map mapData = funGetSalePurchaseComparisonDtl(fromDate1,toDate1);
			List jArrSearchList = (ArrayList) mapData.get("jArr");
			clsWebPOSReportBean objPOSSaleBean = null;

			if (null != jArrSearchList) {
				for (int i = 0; i < jArrSearchList.size(); i++) {
					Map hmObject =  (Map) jArrSearchList.get(i);
					objPOSSaleBean = new clsWebPOSReportBean();

					objPOSSaleBean.setStrItemCode(hmObject.get("MonthName").toString());
					objPOSSaleBean.setStrItemName(hmObject.get("MonthName").toString());
					objPOSSaleBean.setDblAmount(Math.rint(Double.parseDouble(hmObject.get("TotalSaleAmt").toString()))-(Math.rint(Double.parseDouble(hmObject.get("TotalPurchaseAmt").toString())))); // purchase																																// Amount
					//objPOSSaleBean.setDblSettlementAmt(Math.rint(Double.parseDouble(hmObject.get("TotalSaleAmt").toString()))); // sale																															// Amount

					arrGraphList.add(objPOSSaleBean);
				}
				objBean.setArrGraphList(arrGraphList);
			}
			

		}
		else if(reportType.equalsIgnoreCase("Group Wise Profit"))
		{
			List jArrSearchList=funGetGroupWiseProfitDtl(fromDate1,toDate1);
			if (null != jArrSearchList) {
				for (int i = 0; i < jArrSearchList.size(); i++) {
					Map hmObject = (Map) jArrSearchList.get(i);
					clsWebPOSReportBean objPOSSaleBean = new clsWebPOSReportBean();
					objPOSSaleBean.setStrItemCode(hmObject.get("GroupCode").toString());
					objPOSSaleBean.setStrItemName(hmObject.get("GroupName").toString());
					objPOSSaleBean.setDblAmount(Math.rint(Double.parseDouble(hmObject.get("SettleAmt").toString())));
					arrGraphList.add(objPOSSaleBean);
				}
				objBean.setArrGraphList(arrGraphList);
			}
		}
		else
		{
			Map mapData = funGetSalePurchaseComparisonDtl(fromDate1,toDate1);
			List jArrSearchList = (ArrayList) mapData.get("jArr");
			Map hmTotal = (Map) mapData.get("mapDataTotal");
			clsWebPOSReportBean objPOSSaleBean = null;

			if (null != jArrSearchList) {
				for (int i = 0; i < jArrSearchList.size(); i++) {
					Map hmObject =  (Map) jArrSearchList.get(i);
					objPOSSaleBean = new clsWebPOSReportBean();

					objPOSSaleBean.setStrItemCode(hmObject.get("MonthName").toString());
					objPOSSaleBean.setStrItemName(hmObject.get("MonthName").toString());
					objPOSSaleBean.setDblAmount((Math.rint(Double.parseDouble(hmObject.get("TotalPurchaseAmt").toString())))); // purchase																																// Amount
					objPOSSaleBean.setDblSettlementAmt(Math.rint(Double.parseDouble(hmObject.get("TotalSaleAmt").toString()))); // sale																															// Amount

					arrGraphList.add(objPOSSaleBean);
				}
				objBean.setArrGraphList(arrGraphList);
			}
		}

		return objBean;
	}
	
	public Map funGetSalePurchaseComparisonDtl(String fromDate,String toDate)
	{		
		 StringBuilder sbSql = new StringBuilder();

	     Map mapDataRet=new HashMap<>();
	     List listRet =new ArrayList();
		 StringBuilder sbSqlLive = new StringBuilder();
	     StringBuilder sbSqlQFile = new StringBuilder();
	     StringBuilder sbSqlFilters = new StringBuilder();
	     List listData =new ArrayList<>();    
	     double total=0.0,grandTotal=0.0;

	     sbSqlLive.setLength(0);
	     sbSqlQFile.setLength(0);
	     sbSqlFilters.setLength(0);

	     int colCount=4;
	     try
	     {
	    	
    	 	HashMap<String,clsBillItemDtl> mapMonthWiseSales=new LinkedHashMap<>();
        	Map<String, Double> map =new HashMap<String, Double>();
        	       	        	
		    sbSqlLive.append(" SELECT SUM(a.dblGrandTotal) as invoiceTotal,MONTHNAME(a.dteInvDate) AS invoiceMonth, YEAR(a.dteInvDate) AS invoiceYear,grn.grnTotal, grn.grnMonth, grn.grnYear "
		    		+ " FROM tblinvoicehd a, tblsettlementmaster b,tblpartymaster c, ("
		    		+ " SELECT SUM(dblTotal) AS grnTotal, MONTHNAME(dtGRNDate) AS grnMonth, YEAR(dtGRNDate) AS grnYear "
		    		+ " FROM tblgrnhd WHERE YEAR(dtGRNDate) BETWEEN '"+fromDate+"' and '"+toDate+"'  GROUP BY MONTH(dtGRNDate), YEAR(dtGRNDate) "
		    		+ " ORDER BY YEAR(dtGRNDate) ASC, MONTH(dtGRNDate) ASC) grn "
		    		+ " WHERE a.strSettlementCode=b.strSettlementCode AND a.strCustCode=c.strPCode  "
		    		+ " AND DATE(a.dteInvDate) BETWEEN '"+fromDate+"' and '"+toDate+"'   "
		    		+ " AND MONTHNAME(a.dteInvDate) = grn.grnMonth AND YEAR(a.dteInvDate) = grn.grnYear "
		    		+ " GROUP BY MONTH(a.dteInvDate),YEAR(a.dteInvDate)");
          
	 	   	List listSqlFile = objGlobalService.funGetList(sbSqlLive.toString(), "sql");
 	   		 if(listSqlFile.size()>0)
 		      {
 		    	
 		    	for(int i=0 ;i<listSqlFile.size();i++ )
 		    	{
 		    		Object[] obj = (Object[]) listSqlFile.get(i);
 		    		String monthName=obj[1].toString();
 		    		if(monthName.equals("January"))
 					{
 		    			 monthName="JAN";
 					}
 					else if(monthName.equals("February"))
 					{
 						monthName="FEB";
 					}
 					else if(monthName.equals("March"))
 					{
 						monthName="MAR";
 					}
 					else if(monthName.equals("April"))
 					{
 						monthName="APR";
 					}
 					else if(monthName.equals("May"))
 					{
 						monthName="MAY";
 					}
 					else if(monthName.equals("June"))
 					{
 						monthName="JUN";
 					}
 					else if(monthName.equals("July"))
 					{
 						monthName="JUL";
 					}
 					else if(monthName.equals("August"))
 					{
 						monthName="AUG";
 					}
 					else if(monthName.equals("September"))
 					{
 						monthName="SEP";
 					}
 					else if(monthName.equals("October"))
 					{
 						monthName="OCT";
 					}
 					else if(monthName.equals("November"))
 					{
 						monthName="NOV";
 					}
 					else if(monthName.equals("December"))
 					{
 						monthName="DEC";
 					}
 		    				    		
 		    		clsBillItemDtl objItemDtl = new clsBillItemDtl(monthName+"("+obj[2].toString()+")", "","", 0, Double.parseDouble(obj[3].toString()), 0, monthName+"("+obj[2].toString()+")", Double.parseDouble(obj[0].toString()));
                    mapMonthWiseSales.put(monthName+"("+obj[2].toString()+")", objItemDtl);
 		    	}
 		     }
	 		     
	 		  if(mapMonthWiseSales.size()>0)
			  {
				   Iterator<Map.Entry<String, clsBillItemDtl>> itemIterator = mapMonthWiseSales.entrySet().iterator();
				   while (itemIterator.hasNext())
	                {
	                	Map.Entry<String,clsBillItemDtl> itemEntry=itemIterator.next();
	                    clsBillItemDtl objBillItemDtl = itemEntry.getValue();

	                    Map hmPOS= new HashMap<>();
	                    List arrPOSDtls = new ArrayList<>();
	                    hmPOS.put("MonthName", objBillItemDtl.getPosName());
	                    hmPOS.put("TotalPurchaseAmt",objBillItemDtl.getAmount());
	                    hmPOS.put("TotalSaleAmt",objBillItemDtl.getSubTotal());
		               listData.add(hmPOS);
			        }
			   }
	   

	        Map mapDataTotal =new HashMap<>();
	 		    
	        mapDataTotal.put("total", total);
	        mapDataRet.put("jArr", listData);
	        mapDataRet.put("mapDataTotal", mapDataTotal);
		 }
		        
	     catch(Exception ex)
	     {
	    	 ex.printStackTrace();
	     }
	     	    
	    return 	mapDataRet;
		
	}
	
	
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = { "/loadCRMDashboardGraph" }, method = RequestMethod.GET)
	@ResponseBody
	public clsPOSDashboardBean FunLoadPOSWiseSalesReport(HttpServletRequest req) {
		LinkedHashMap resMap = new LinkedHashMap();
		clsPOSDashboardBean objBean = new clsPOSDashboardBean();

		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String fromDate = req.getParameter("fromDate");

		String toDate = req.getParameter("toDate");

		String strReportType = req.getParameter("strReportTypedata");
	
		
		objBean = FunGetData(clientCode, fromDate, toDate, strReportType);

		return objBean;
	}
	
	
    
	public List funGetCRMDashboardDtl(String fromDate,String toDate,String strReportType)
	{
		HashMap<String,clsBillItemDtl> mapGroupWiseSales=new HashMap<String,clsBillItemDtl>();
		List listData=new ArrayList<>();
		StringBuilder sbSqlLive = new StringBuilder();
		
		if(strReportType.equals("Group Wise"))
		{
			sbSqlLive.append(" SELECT e.strGCode,e.strGName,SUM(d.dblQty*d.dblUnitPrice)"
		    		+ " FROM tblinvoicehd a,tblproductmaster b,tblsubgroupmaster c,tblinvoicedtl d,tblgroupmaster e,tblpartymaster f"
		    		+ " WHERE a.strInvCode=d.strInvCode AND d.strProdCode=b.strProdCode "
		    		+ " AND b.strSGCode=c.strSGCode AND c.strGCode=e.strGCode AND a.strCustCode=f.strPCode "
		    		+ " AND DATE(a.dteInvDate) BETWEEN '"+fromDate+"' AND '"+toDate+"' "
		    		+ " GROUP BY e.strGCode ");
	   
		    List listSqlFile = objGlobalService.funGetList(sbSqlLive.toString(), "sql");
		    if(listSqlFile.size()>0)
		    {
		    	for(int i=0 ;i<listSqlFile.size();i++ )
		    	{
		    	  Object[] obj = (Object[]) listSqlFile.get(i);
		    	  clsBillItemDtl objBean=new clsBillItemDtl();
		    	  objBean.setGroupCode(obj[0].toString());
		    	  objBean.setGroupName(obj[1].toString());
		    	  objBean.setAmount(Double.parseDouble(obj[2].toString()));
		    	  mapGroupWiseSales.put(obj[0].toString(), objBean);
		    	}
		  
		    }
		    
		    double total=0;
			   if(mapGroupWiseSales.size()>0)
			   {
				   for (Map.Entry<String, clsBillItemDtl> entry : mapGroupWiseSales.entrySet())
			        {
					   clsBillItemDtl objBean = entry.getValue();
			           Map hmData= new HashMap<>();
			           hmData.put("GroupCode", entry.getKey());
			           hmData.put("GroupName", objBean.getGroupName());
			           hmData.put("SettleAmt",(long)objBean.getAmount());
			           total+=(long)objBean.getAmount();
			           listData.add(hmData);
			        }
			   }
		}
		else if(strReportType.equals("Region Wise"))
		{
			sbSqlLive.append(" SELECT c.strRegionCode,c.strRegionDesc, SUM(b.dblGrandTotal)"
					+ " FROM tblpartymaster a,tblinvoicehd b,tblregionmaster c"
					+ " WHERE b.strCustCode=a.strPCode AND a.strRegion=c.strRegionCode  "
					+ " AND DATE(b.dteInvDate) BETWEEN '"+fromDate+"' AND '"+toDate+"' "
					+ " GROUP BY a.strRegion ORDER BY SUM(b.dblGrandTotal) DESC ");
	   
		    List listSqlFile = objGlobalService.funGetList(sbSqlLive.toString(), "sql");
		    if(listSqlFile.size()>0)
		    {
		    	for(int i=0 ;i<listSqlFile.size();i++ )
		    	{
		    	  Object[] obj = (Object[]) listSqlFile.get(i);
		    	  clsBillItemDtl objBean=new clsBillItemDtl();
		    	  objBean.setGroupCode(obj[0].toString());
		    	  objBean.setGroupName(obj[1].toString());
		    	  objBean.setAmount(Double.parseDouble(obj[2].toString()));
		    	  mapGroupWiseSales.put(obj[0].toString(), objBean);
		    	}
		  
		    }
		    
		    double total=0;
			   if(mapGroupWiseSales.size()>0)
			   {
				   for (Map.Entry<String, clsBillItemDtl> entry : mapGroupWiseSales.entrySet())
			        {
					   clsBillItemDtl objBean = entry.getValue();
			           Map hmData= new HashMap<>();
			           hmData.put("GroupCode", entry.getKey());
			           hmData.put("GroupName", objBean.getGroupName());
			           hmData.put("SettleAmt",(long)objBean.getAmount());
			           total+=(long)objBean.getAmount();
			           listData.add(hmData);
			        }
			   }
		}
		else if(strReportType.equalsIgnoreCase("Month Wise"))
	    {
			HashMap<String,Double> mapMonthWiseSales=new LinkedHashMap<>();
		        sbSqlLive.append(" select CASE { fn MONTH(a.dteInvDate) } "
		        		+ " when 1 then 'JAN' "
		        		+ " when 2 then 'FEB' "
		        		+ " when 3 then 'MAR' "
		        		+ " when 4 then 'APR' "
		        		+ " when 5 then 'MAY' "
		        		+ " when 6 then 'JUN' "
		        		+ " when 7 then 'JUL' "
		        		+ " when 8 then 'AUG' "
		        		+ " when 9 then 'SEP' "
		        		+ " when 10 then 'OCT' "
		        		+ " when 11 then 'NOV' "
		        		+ " when 12 then 'DEC' END  AS MonthName,sum(a.dblGrandTotal),year(a.dteInvDate) "
		        		+ " from tblinvoicehd a, tblsettlementmaster b,tblpartymaster c   "
		        		+ " where a.strSettlementCode=b.strSettlementCode  and a.strCustCode=c.strPCode"
		        		+ " AND DATE(a.dteInvDate) BETWEEN '"+fromDate+"' AND '"+toDate+"' "
		        		+ " GROUP BY MONTH(a.dteInvDate),YEAR(a.dteInvDate)  ;  ");
	
		        List listSqlFile = objGlobalService.funGetList(sbSqlLive.toString(), "sql");
			    if(listSqlFile.size()>0)
			    {
			    	for(int i=0 ;i<listSqlFile.size();i++ )
			    	{
			    	    Object[] obj = (Object[]) listSqlFile.get(i);
		 		    	if (mapMonthWiseSales.containsKey(obj[0].toString()+"("+obj[2].toString()+")"))
		                {
		 		    		 double amt=mapMonthWiseSales.get((obj[0].toString()+"("+obj[2].toString()+")"));
		                     amt=amt+Double.parseDouble(obj[1].toString());
		                     mapMonthWiseSales.put(obj[2].toString()+"("+obj[2].toString()+")", amt);
		                }
		                else
		                {
		                	 mapMonthWiseSales.put(obj[0].toString()+"("+obj[2].toString()+")", Double.parseDouble(obj[1].toString()));
		                }
			       }
			  
			    }
			    
			    		   
			  if(mapMonthWiseSales.size()>0)
				{
				   Iterator<Map.Entry<String, Double>> itemIterator = mapMonthWiseSales.entrySet().iterator();
				   while (itemIterator.hasNext())
		           {
		           	   Map.Entry<String,Double> itemEntry=itemIterator.next();
		           	   double amt= itemEntry.getValue();
	
		               Map hmData= new HashMap<>();
		               hmData.put("MonthName", itemEntry.getKey());
		               hmData.put("TotalAmt",amt);
		               listData.add(hmData);
		            }
				}    
	   }  
	   return listData;
	
	 }

	
	
	
	public List funGetGroupWiseProfitDtl(String fromDate,String toDate)
	{		
		 StringBuilder sbSql = new StringBuilder();
	     List listData =new ArrayList<>();    
	     double total=0.0,grandTotal=0.0;

	     sbSql.setLength(0);
	     try
	     {
	    	
	    	HashMap<String,clsBillItemDtl> mapGroupWiseSales=new HashMap<String,clsBillItemDtl>();
        	Map<String, Double> map =new HashMap<String, Double>();
        	       	        	
		    
        	sbSql.append(" SELECT e.strGCode as invoiceGroupCode,e.strGName as invoiceGroupName, SUM(d.dblQty*d.dblUnitPrice) as invoiceAmount,"
		    		+ " grn.grnGroupCode,grn.grnGroupName,grn.grnAmount"
		    		+ " FROM tblinvoicehd a,tblproductmaster b,tblsubgroupmaster c,tblinvoicedtl d,tblgroupmaster e,tblpartymaster f,"
		    		+ " ( SELECT e.strGCode as grnGroupCode ,e.strGName as grnGroupName, SUM(d.dblQty*d.dblUnitPrice) as grnAmount"
		    		+ " FROM tblgrnhd a,tblproductmaster b,tblsubgroupmaster c,tblgrndtl d,tblgroupmaster e "
		    		+ " WHERE a.strGRNCode =d.strGRNCode AND d.strProdCode=b.strProdCode AND b.strSGCode=c.strSGCode "
		    		+ " AND c.strGCode=e.strGCode  AND DATE(a.dtGRNDate) BETWEEN '"+fromDate+"' and '"+toDate+"' "
		    		+ " GROUP BY e.strGCode )grn"
		    		+ " WHERE a.strInvCode=d.strInvCode AND d.strProdCode=b.strProdCode AND b.strSGCode=c.strSGCode "
		    		+ " AND c.strGCode=e.strGCode AND a.strCustCode=f.strPCode AND DATE(a.dteInvDate) BETWEEN '"+fromDate+"' and '"+toDate+"' "
		    		+ " and e.strGCode=grn.grnGroupCode"
		    		+ " GROUP BY e.strGCode");
          
	 	    List listSqlFile = objGlobalService.funGetList(sbSql.toString(), "sql");
		    if(listSqlFile.size()>0)
		     {
		    	for(int i=0 ;i<listSqlFile.size();i++ )
		    	{
		    	  Object[] obj = (Object[]) listSqlFile.get(i);
		    	  clsBillItemDtl objBean=new clsBillItemDtl();
		    	  objBean.setGroupCode(obj[0].toString());
		    	  objBean.setGroupName(obj[1].toString());
		    	  objBean.setAmount(Double.parseDouble(obj[2].toString())-Double.parseDouble(obj[5].toString()));
		    	  mapGroupWiseSales.put(obj[0].toString(), objBean);
		    	}
		    }
		    
		    
		   if(mapGroupWiseSales.size()>0)
		   {
			   for (Map.Entry<String, clsBillItemDtl> entry : mapGroupWiseSales.entrySet())
		        {
				   clsBillItemDtl objBean = entry.getValue();
		           Map hmData= new HashMap<>();
		           hmData.put("GroupCode", entry.getKey());
		           hmData.put("GroupName", objBean.getGroupName());
		           hmData.put("SettleAmt",(long)objBean.getAmount());
		           total+=(long)objBean.getAmount();
		           listData.add(hmData);
		        }
		   }
	        
		 }
		        
	     catch(Exception ex)
	     {
	    	 ex.printStackTrace();
	     }
	     	    
	    return 	listData;
		
	}


	

}
