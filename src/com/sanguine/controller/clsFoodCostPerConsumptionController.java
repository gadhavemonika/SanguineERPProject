package com.sanguine.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.Connection;
import com.sanguine.bean.clsAvgConsumptionReportBean;
import com.sanguine.bean.clsFoodCostBean;
import com.sanguine.crm.bean.clsProductionComPilationBean;
import com.sanguine.model.clsCompanyMasterModel;
import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsGroupMasterService;
import com.sanguine.service.clsLocationMasterService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.service.clsSubGroupMasterService;
import com.sanguine.util.clsReportBean;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@Controller
public class clsFoodCostPerConsumptionController
{

	@Autowired
	private clsGlobalFunctionsService objGlobalService;
	@Autowired
	private clsSetupMasterService objSetupMasterService;
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsGlobalFunctions objGlobalFunctions;
	@Autowired
	private clsGroupMasterService objGrpMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsSubGroupMasterService objSubGroupMasterService;
	@Autowired
	private clsLocationMasterService objLocationMasterService;

	@RequestMapping(value = "/frmFoodCostPerConsumption", method = RequestMethod.GET)
	public ModelAndView funOpenCategoryWiseSalesOrderForm(Map<String, Object> model, HttpServletRequest request)
	{
		request.getSession().setAttribute("formName", "frmFoodCostPerConsumption");
		String urlHits = "1";
		try
		{
			urlHits = request.getParameter("saddr").toString();
		}
		catch (NullPointerException e)
		{
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		Map<String, String> mapItemType = new LinkedHashMap();
		mapItemType.put("BOTH", "BOTH");
		mapItemType.put("FOOD", "FOOD");
		mapItemType.put("LIQUOR", "LIQUOR");

		model.put("mapItemType", mapItemType);

		if ("2".equalsIgnoreCase(urlHits))
		{
			return new ModelAndView("frmFoodCostPerConsumption_1", "command", new clsReportBean());
		}
		else
		{
			return new ModelAndView("frmFoodCostPerConsumption", "command", new clsReportBean());
		}

	}

	@RequestMapping(value = "/getFoodCostPerConsumptionData", method = RequestMethod.GET)
	private @ResponseBody List<clsFoodCostBean> funGetFoodCostPerConsumptionData(@RequestParam(value = "formDate") String fromDate, @RequestParam(value = "toDate") String toDate, HttpServletResponse resp, HttpServletRequest req)
	{
			
		String urlHits = "1";
		try
		{
			urlHits = req.getParameter("saddr").toString();
		}
		catch (NullPointerException e)
		{
			urlHits = "1";
		}

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String dteCurrDateTime = objGlobal.funGetCurrentDateTime("yyyy-MM-dd");
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();

		/*String[] arrDate = fromDate.split("-");

		String year = String.format("%04d", Integer.parseInt(arrDate[0]));
		String month = String.format("%02d", Integer.parseInt(arrDate[1]));
		String day = String.format("%02d", Integer.parseInt(arrDate[2]));

		fromDate = year + "-" + month + "-" + day;

		DateTimeFormatter yyyyMMddDateFormater = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		LocalDate ld = LocalDate.parse(fromDate, yyyyMMddDateFormater);
		int lengthOfMonth = ld.lengthOfMonth();

		String toDate = year + "-" + month + "-" + lengthOfMonth;*/
		
		fromDate=objGlobal.funGetDate("yyyy-MM-dd",fromDate);
		toDate=objGlobal.funGetDate("yyyy-MM-dd",toDate);
		
		List<clsFoodCostBean> dataList=new LinkedList<>();
		
		dataList=funGetDataList(fromDate,toDate);
		
		
		return dataList;
		
	}
	
	private List<clsFoodCostBean> funGetDataList(String fromDate, String toDate)
	{
		String sqlCost = "select day(a.dtMISDate)day,date(a.dtMISDate),e.strGName\r\n" + ",sum(b.dblQty),sum(b.dblTotalPrice)\r\n" + "from tblmishd a,tblmisdtl b,tblproductmaster c,tblsubgroupmaster d,tblgroupmaster e\r\n" + "where a.strMISCode=b.strMISCode\r\n" + "and b.strProdCode=c.strProdCode\r\n" + "and c.strSGCode=d.strSGCode\r\n" + "and d.strGCode=e.strGCode\r\n" + "and date(a.dtMISDate) between '" + fromDate + "' and '" + toDate + "'\r\n" + "group by date(a.dtMISDate),e.strGName\r\n" + "order by date(a.dtMISDate),e.strGName ";
		List listCost = objGlobalFunctionsService.funGetList(sqlCost, "sql");

		String sqlSale = "select day(a.dteBillDate)day,date(a.dteBillDate),d.strGName\r\n" + ",a.dblRate,sum(a.dblQuantity)dblQuantity,sum(a.dblAmount)SubTotal\r\n" + ",a.dblPercent DiscPer,sum(a.dblPercentAmt) DiscAmt,sum(a.dblAmount-a.dblPercentAmt )NetTotal\r\n" + "from tblpossalesdtl a,tblproductmaster b,tblsubgroupmaster c,tblgroupmaster d\r\n" + "where a.strWSItemCode=b.strProdCode\r\n" + "and b.strSGCode=c.strSGCode\r\n" + "and c.strGCode=d.strGCode\r\n" + "and date(a.dteBillDate) between '" + fromDate + "' and '" + toDate + "'\r\n" + "group by date(a.dteBillDate),d.strGName\r\n" + "order by date(a.dteBillDate),d.strGName ";
		List listSale = objGlobalFunctionsService.funGetList(sqlSale, "sql");

		

		Map<String, clsFoodCostBean> mapGroups=new HashMap<>();
		
		
		if (listCost != null && listCost.size() > 0)
		{
			for (int i = 0; i < listCost.size(); i++)
			{
				Object[] arr = (Object[]) listCost.get(i);

				String dayNo = arr[0].toString();
				String groupName = arr[2].toString();
				double totalCost = Double.parseDouble(arr[4].toString());
				
				if (mapGroups.containsKey(groupName))
				{
					clsFoodCostBean objGroup = mapGroups.get(groupName);
					objGroup.setDblCostValue(objGroup.getDblCostValue()+totalCost);
					
					mapGroups.put(groupName, objGroup);
				}
				else
				{
					clsFoodCostBean objGroup = new clsFoodCostBean();

					objGroup.setStrGroupName(groupName);
					objGroup.setDblCostValue(totalCost);
					
					mapGroups.put(groupName, objGroup);

				}
			}
		}
		
		if (listSale != null && listSale.size() > 0)
		{
			for (int i = 0; i < listSale.size(); i++)
			{
				Object[] arr = (Object[]) listSale.get(i);

				String dayNo = arr[0].toString();
				String groupName = arr[2].toString();
				double totalSale = Double.parseDouble(arr[8].toString());//netTotal
				
				if (mapGroups.containsKey(groupName))
				{
					clsFoodCostBean objGroup = mapGroups.get(groupName);
					objGroup.setDblSaleValue(objGroup.getDblSaleValue()+totalSale);
					
					mapGroups.put(groupName, objGroup);
				}
				else
				{
					clsFoodCostBean objGroup = new clsFoodCostBean();
					objGroup.setStrGroupName(groupName);
					objGroup.setDblSaleValue(totalSale);

					
					mapGroups.put(groupName, objGroup);

				}
			}
		}

		
		
		
		List<clsFoodCostBean> dataList=new LinkedList<>();
		double totalCost=0,totalSale=0,totalFCPer=0;
		
		for(Map.Entry<String,clsFoodCostBean>entryDays:mapGroups.entrySet())
		{
						
			String groupName=entryDays.getKey();
			
			clsFoodCostBean obj=entryDays.getValue();
			if(obj.getDblSaleValue()>0)
			{
				obj.setDblFCPer((obj.getDblCostValue()/obj.getDblSaleValue())*100);							
			}
			
			totalCost=totalCost+obj.getDblCostValue();
			totalSale=totalSale+obj.getDblSaleValue();
			
			dataList.add(obj);
			
		}
		
		
		
		
		Comparator<clsFoodCostBean>groupNameSorting=new Comparator<clsFoodCostBean>()
		{

			@Override
			public int compare(clsFoodCostBean o1, clsFoodCostBean o2)
			{
				return o1.getStrGroupName().compareTo(o2.getStrGroupName());
			}
			
		};
		
		
		Collections.sort(dataList,groupNameSorting);
		
		
		if(totalSale>0)
		{
			totalFCPer=(totalCost/totalSale)*100;
		}
		clsFoodCostBean objTotal=new clsFoodCostBean();
		objTotal.setStrGroupName("TOTAL");
		objTotal.setDblCostValue(totalCost);
		objTotal.setDblSaleValue(totalSale);
		objTotal.setDblFCPer(totalFCPer);
		
		dataList.add(objTotal);
		
		
		return dataList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/exportFoodCostPerConsumptionData", method = RequestMethod.GET)
	private ModelAndView funExportFoodCostPerConsumptionData(@RequestParam(value = "fromDate") String fromDate, @RequestParam(value = "toDate") String toDate, HttpServletResponse resp, HttpServletRequest req)
	{

		fromDate = objGlobal.funGetDate("yyyy-MM-dd", fromDate);
		toDate = objGlobal.funGetDate("yyyy-MM-dd", toDate);
		
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		
		String[] monthName = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

		String[] frmMonth = fromDate.split("-");
		String[] toMonth = toDate.split("-");
		int intfrmMonth = Integer.parseInt(frmMonth[1]) - 1;
		int inttoMonth = Integer.parseInt(toMonth[1]) - 1;
		String fromDateToDisplay = frmMonth[2] + "-" + monthName[intfrmMonth] + "-" + frmMonth[0];
		String toDateToDisplay = toMonth[2] + "-" + monthName[inttoMonth] + "-" + toMonth[0];

		
		clsCompanyMasterModel objCompModel = objSetupMasterService.funGetObject(clientCode);
		String companyName = objCompModel.getStrCompanyName();

		List ExportList = new ArrayList();
		ExportList.add("FOOD COST(PER CONSUMPTION)" + fromDate + "to" + toDate);

		List titleData = new ArrayList<>();
		titleData.add(companyName);
		ExportList.add(titleData);

		List titleData1 = new ArrayList<>();
		titleData1.add("FOOD COST(PER CONSUMPTION)");
		ExportList.add(titleData1);

		List<String> titleData2 = new ArrayList<String>();
		
		ExportList.add(titleData2);

		List filterData = new ArrayList<>();
		String periodFromDate = fromDateToDisplay + "  -  " + toDateToDisplay;

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String printedOnDate = dtf.format(now);

		filterData.add("Period From:");
		filterData.add(periodFromDate);
		filterData.add(" ");
		filterData.add("Printed On:");
		filterData.add(printedOnDate);
		Map<String, List> hmProd = new LinkedHashMap<String, List>();
		ExportList.add(filterData);
		
		String[]header=new String[0];
		
		ExportList.add(header);
		
		
		List<clsFoodCostBean> dataList=funGetDataList(fromDate,toDate);
		
		List dataRow = new ArrayList<>();
		
		List dataCol = new ArrayList<>();
		dataCol.add("GROUP NAME");
		dataCol.add("COST VALUE");
		dataCol.add("SALE VALUE");
		dataCol.add("FOOD COST %");
		
		dataRow.add(dataCol);
		
		DecimalFormat decimalFormat=new DecimalFormat("0.00");
		
		for(int i=0;i<dataList.size();i++)
		{
			dataCol = new ArrayList<>();
			
			clsFoodCostBean obj=dataList.get(i);
			
			dataCol.add(obj.getStrGroupName());
			dataCol.add(decimalFormat.format(obj.getDblCostValue()));
			dataCol.add(decimalFormat.format(obj.getDblSaleValue()));
			dataCol.add(decimalFormat.format(obj.getDblFCPer()));
			
			dataRow.add(dataCol);
		}
		
		ExportList.add(dataRow);
		
		
		return new ModelAndView("excelViewFromDateTodateCompanyNameWithReportName", "listFromDateTodateWithReportName", ExportList);
		
	}
	
	
	
	
	
	
	
	
	
	
}
