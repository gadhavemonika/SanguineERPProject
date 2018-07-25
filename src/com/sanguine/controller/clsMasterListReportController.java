package com.sanguine.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.Connection;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.util.clsReportBean;

@Controller
public class clsMasterListReportController {
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@Autowired
	private ServletContext servletContext;

	// open CategoryWisesale Form
	@RequestMapping(value = "/frmMasterList", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {

		// String
		// selectedModuleNo=request.getSession().getAttribute("moduleNo").toString();
		// String
		// masterSql="select strFormDesc from tbltreemast where strRootNode='Master' and strModule='"+selectedModuleNo+"' ";
		//
		// List listMasterName=
		// objGlobalFunctionsService.funGetDataList(masterSql, "sql");
		// model.put("moduleName", listMasterName);

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmMasterList_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmMasterList", "command", new clsReportBean());
		}
	}

	@RequestMapping(value = "/rptMasterList", method = RequestMethod.GET)
	private void funMasterList(clsReportBean objBean, HttpServletRequest req, HttpServletResponse res) {

		String fromDate = objBean.getDtFromDate();
		String toDate = objBean.getDtToDate();
		String tblName = objBean.getStrAgainst();
		funCallMasterList(fromDate, toDate, tblName, req, res);
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public void funCallMasterList(String fromDate, String toDate, String tblName, HttpServletRequest req, HttpServletResponse res) {
		try {
			objGlobal = new clsGlobalFunctions();
			Connection con = objGlobal.funGetConnection(req);
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}

			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptMasterList.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
			String code = null, Name = null, masterName = null;
			switch (tblName) {

			case "tblattributemaster": {
				code = "strAttCode";
				Name = "strAttName";
				masterName = "Attribulte Master";
				break;
			}
			case "tbludcategory": {
				code = "strUDCCode";
				Name = "strUDCName";
				masterName = "Ud category Master";
				break;
			}
			case "tblcharacteristics": {
				code = "strCharCode";
				Name = "strCharName";
				masterName = "Characterstics Master";
				break;
			}
			case "tblgroupmaster": {
				code = "strGCode";
				Name = "strGName";
				masterName = "Group Master";
				break;
			}
			case "tbllocationmaster": {
				code = "strLocCode";
				Name = "strLocName";
				masterName = "Location Master";
				break;
			}
			case "tblpropertymaster": {
				code = "strPropertyCode";
				Name = "strPropertyName";
				masterName = "Property Master";
				break;
			}
			case "tblreasonmaster": {
				code = "strReasonCode";
				Name = "strReasonName";
				masterName = "Reason Master";
				break;
			}
			case "tblsubgroupmaster": {
				code = "strSGCode";
				Name = "strSGName";
				masterName = "Sub Group Master";
				break;
			}
			case "tblprodsuppmaster": {
				code = "strSGCode";
				Name = "strSGName";
				masterName = "Supplier Master";
				break;
			}
			case "tbltaxhd": {
				code = "strTaxCode";
				Name = "strTaxDesc";
				masterName = "Tax Master";
				break;
			}
			case "tblprocessmaster": {
				code = "strProcessCode";
				Name = "strProcessName";
				masterName = "Process Master";
				break;
			}
			case "tbluserhd": {
				code = "strUserCode";
				Name = "strUserName";
				masterName = "User Master";
				break;
			}
			case "tbltcmaster": {
				code = "strTCCode";
				Name = "strTCName";
				masterName = "TC Master";
				break;
			}
			}

			/*
			 * case"":{ code=; Name=; }
			 */

			String MasterSql = "select\t" + code + " as code," + Name + " as Name, date(dtCreatedDate) as CreateDate from " + tblName + " a where a.strClientCode='" + clientCode + "' and  " + "date(a.dtCreatedDate) between'" + fromDate + "'and'" + toDate + "'";

			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery newQuery = new JRDesignQuery();
			newQuery.setText(MasterSql);
			jd.setQuery(newQuery);

			JasperReport jr = JasperCompileManager.compileReport(jd);
			HashMap hm = new HashMap();
			hm.put("strCompanyName", companyName);
			hm.put("strUserCode", userCode);
			hm.put("strImagePath", imagePath);
			hm.put("strAddr1", objSetup.getStrAdd1());
			hm.put("strAddr2", objSetup.getStrAdd2());
			hm.put("strCity", objSetup.getStrCity());
			hm.put("strState", objSetup.getStrState());
			hm.put("strCountry", objSetup.getStrCountry());
			hm.put("strPin", objSetup.getStrPin());
			hm.put("masterName", masterName);

			JasperPrint p = JasperFillManager.fillReport(jr, hm, con);
			ServletOutputStream servletOutputStream = res.getOutputStream();
			byte[] bytes = null;
			bytes = JasperRunManager.runReportToPdf(jr, hm, con);
			res.setContentType("application/pdf");
			res.setContentLength(bytes.length);
			servletOutputStream.write(bytes, 0, bytes.length);
			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
