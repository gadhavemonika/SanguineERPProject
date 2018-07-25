package com.sanguine.webpms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.webpms.bean.clsChangedRoomTypeDtlBean;
import com.sanguine.webpms.dao.clsWebPMSDBUtilityDao;

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
import net.sf.jasperreports.engine.xml.JRXmlLoader;


@Controller
public class clsChangedRoomTypeController 
{
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private clsSetupMasterService objSetupMasterService;
	
	@Autowired
	private clsWebPMSDBUtilityDao objWebPMSUtility;
	
	@RequestMapping(value = "/frmChangedRoomTypeReport", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";

		List<String> listAgainst = new ArrayList<>();
		listAgainst.add("Reservation");
		// listAgainst.add("Check-In");
		listAgainst.add("Folio-No");
		listAgainst.add("Bill");
		model.put("listAgainst", listAgainst);

		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if (urlHits.equalsIgnoreCase("1")) {

			return new ModelAndView("frmChangedRoomTypeReport", "command", new clsChangedRoomTypeDtlBean());
		} else {
			return new ModelAndView("frmChangedRoomTypeReport_1", "command", new clsChangedRoomTypeDtlBean());
		}
	}
	
	
	
	@RequestMapping(value = "/rptChangedRoomTypeReport", method = RequestMethod.GET)
	public void funGenerateChangedRoomTypeReport(@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate, HttpServletRequest req, HttpServletResponse resp) {
		try {
			boolean flgBillRecord = false;
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webpms/rptChangedRoomTypeReport.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			List<clsChangedRoomTypeDtlBean> dataList = new ArrayList<clsChangedRoomTypeDtlBean>();
			@SuppressWarnings("rawtypes")
			HashMap reportParams = new HashMap();

			reportParams.put("pCompanyName", companyName);
			reportParams.put("pAddress1", objSetup.getStrAdd1() + "," + objSetup.getStrAdd2() + "," + objSetup.getStrCity());
			reportParams.put("pAddress2", objSetup.getStrState() + "," + objSetup.getStrCountry() + "," + objSetup.getStrPin());
			reportParams.put("pContactDetails", "");
			reportParams.put("strImagePath", imagePath);
			reportParams.put("strUserCode", userCode);
			reportParams.put("pFromDate", fromDate);
			reportParams.put("pTtoDate", toDate);
			/*if(against.equalsIgnoreCase("Reservation"))
			{
				reportParams.put("pDocName", "Reservation No");	
			}
			else
			{
				reportParams.put("pDocName", "Check In No");
			}
			reportParams.put("pDocNo", docNo);	
			*/
			Map<String,clsChangedRoomTypeDtlBean> mapUpdatedRoomType=new TreeMap<>();	
			clsChangedRoomTypeDtlBean roomBean=null;
			// get bill details
			
				String sqlBillDtl = "SELECT a.strDocNo,a.strType,ifnull(b.strRoomDesc,''),CONCAT(c.strFirstName,' ',c.strMiddleName,' ',c.strLastName),d.strRoomTypeDesc,a.dteToDate "
						+ " from tblchangedroomtypedtl a left outer join tblroom b on a.strRoomNo=b.strRoomCode,tblguestmaster c,tblroomtypemaster d "
						+ " where a.strGuestCode=c.strGuestCode and a.strRoomType=d.strRoomTypeCode "
						+ " and a.strDocNo NOT In(select b.strReservationNo  from tblcheckinhd b )  "
						+ " order by a.strDocNo,a.dteToDate DESC ";
			
				List roomDtlList = objWebPMSUtility.funExecuteQuery(sqlBillDtl, "sql");
				for (int i = 0; i < roomDtlList.size(); i++) 
				{
					Object[] roomArr = (Object[]) roomDtlList.get(i);

					if(mapUpdatedRoomType.size()>0)
                    {
                    	if(mapUpdatedRoomType.containsKey(roomArr[0].toString()))
                    	{
                    		roomBean=mapUpdatedRoomType.get(roomArr[0].toString());
                    		roomBean.setStrNewRoomName(roomArr[2].toString());
  	                        roomBean.setStrNewRoomTypeDesc(roomArr[4].toString());
  	                        mapUpdatedRoomType.put(roomArr[0].toString(), roomBean);
                    	}else
                    	{
                    		if(!roomArr[5].toString().equals(""))
                        	{
                    			 roomBean = new clsChangedRoomTypeDtlBean(); 
                    			 roomBean.setStrDocDate(roomArr[5].toString());
	   	                         roomBean.setStrDocNo(roomArr[0].toString());
	   	                         roomBean.setStrOldRoomName(roomArr[2].toString());
	   	                         roomBean.setStrOldRoomTypeDesc(roomArr[4].toString());
	   	                         roomBean.setStrNewRoomName("");
	   	                         roomBean.setStrNewRoomTypeDesc("");
	   	                         roomBean.setStrType(roomArr[1].toString());
	   	                         roomBean.setStrGuestName(roomArr[3].toString());
	   	                         mapUpdatedRoomType.put(roomArr[0].toString(), roomBean);
                        	}
                    	}
                    }
                    else
                    {
                    	if(!roomArr[5].toString().equals(""))
                    	{
                    		 roomBean = new clsChangedRoomTypeDtlBean(); 
	                		 roomBean.setStrDocDate(roomArr[5].toString());
	                         roomBean.setStrDocNo(roomArr[0].toString());
	                         roomBean.setStrOldRoomName(roomArr[2].toString());
	                         roomBean.setStrOldRoomTypeDesc(roomArr[4].toString());
	                         roomBean.setStrNewRoomName("");
	                         roomBean.setStrNewRoomTypeDesc("");
	                         roomBean.setStrType(roomArr[1].toString());
	                         roomBean.setStrGuestName(roomArr[3].toString());
	                         mapUpdatedRoomType.put(roomArr[0].toString(), roomBean);
                    	}
                    }
					
				}
				
				if(mapUpdatedRoomType.size()>0)
                {
                	 for (Map.Entry<String,clsChangedRoomTypeDtlBean> entry : mapUpdatedRoomType.entrySet()) 
                	 {
                		 roomBean= entry.getValue();
                		 dataList.add(roomBean);
                	 }
                }
		
		
			JRDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(dataList);
			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			JasperPrint jp = JasperFillManager.fillReport(jr, reportParams, beanCollectionDataSource);
			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
			if (jp != null) {
				jprintlist.add(jp);
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				JRExporter exporter = new JRPdfExporter();
				resp.setContentType("application/pdf");
				exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
				exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
				exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
				resp.setHeader("Content-Disposition", "inline;filename=Bill.pdf");
				exporter.exportReport();
				servletOutputStream.flush();
				servletOutputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}	