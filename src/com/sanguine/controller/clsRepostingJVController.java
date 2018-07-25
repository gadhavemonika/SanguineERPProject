package com.sanguine.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import com.sanguine.base.service.intfBaseService;
import com.sanguine.bean.clsFormSearchElements;
import com.sanguine.crm.bean.clsInvoiceBean;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.util.clsReportBean;

@Controller
public class clsRepostingJVController {

	@Autowired
	intfBaseService objBaseService;
	@Autowired
	private clsGlobalFunctionsService objGlobalService;
	

	@Autowired
	clsJVGeneratorController objJVGenerator;
	
	@RequestMapping(value = "/frmWebStockRepostingJV", method = RequestMethod.GET)
	public ModelAndView funOpenForm( Map<String, Object> model, HttpServletRequest request) 
	{
	
	String urlHits = "1";
	String clientCode = request.getSession().getAttribute("clientCode").toString();
	try {
		urlHits = request.getParameter("saddr").toString();
	} catch (NullPointerException e) {
		urlHits = "1";
	}
	model.put("urlHits", urlHits);
	
	if ("2".equalsIgnoreCase(urlHits)) {
		return new ModelAndView("frmWebStockRepostingJV_1", "command", new clsReportBean());
	} else if ("1".equalsIgnoreCase(urlHits)) {
		return new ModelAndView("frmWebStockRepostingJV", "command", new clsReportBean());
	} else {
		return null;
	}

	}
	
	@RequestMapping(value = "/frmWebBookRepostingJV", method = RequestMethod.GET)
	public ModelAndView funOpenCRMJV( Map<String, Object> model, HttpServletRequest request) 
	{
	
		String urlHits = "1";
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWebBookRepostingJV_1", "command", new clsReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWebBookRepostingJV", "command", new clsReportBean());
		} else {
			return null;
		}

	}
	
	
	

	@SuppressWarnings({ "rawtypes", "unused" })
	@RequestMapping(value = "/loadDocListForReposting", method = RequestMethod.GET)
	public @ResponseBody List funLoadInvoiceForReposting(@RequestParam(value = "fromDate") String fDate, @RequestParam(value = "toDate") String toDate,  @RequestParam(value = "strFromName") String strFromName, HttpServletRequest request, HttpServletResponse resp)
	{
		String propCode = request.getSession().getAttribute("propertyCode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String sqlRepostFlash="";
		List<clsFormSearchElements> listofRepostFlash=new ArrayList<>();
		try{
			String dbWebBook=request.getSession().getAttribute("WebBooksDB").toString();
			switch(strFromName){
			case "Invoice":
				
				sqlRepostFlash = "SELECT a.strInvCode, DATE_FORMAT(a.dteInvDate,'%d-%m-%Y'),b.strPName, "
				+" a.dblSubTotalAmt,a.dblTaxAmt,a.dblGrandTotal,d.strVouchNo "
				+" ,ifnull(s.strDebtorCode,''), ifnull(s.strFirstName,''),ifnull(l.strWebBookAccCode,''),ifnull(l.strWebBookAccName,'') "
				+" FROM tblinvoicehd a "
				+" left outer join tbllinkup l on a.strCustCode=l.strMasterCode "
				+" left outer join "+dbWebBook+".tblsundarydebtormaster s on l.strAccountCode=s.strDebtorCode "
				+" ,tblpartymaster b,"+dbWebBook+".tbljvhd d "
				+" WHERE DATE(a.dteInvDate) BETWEEN '" + fDate + "' and '" + toDate + "'  AND a.strCustCode=b.strPCode " 
				+" AND a.strInvCode=d.strSourceDocNo AND a.strClientCode='" + clientCode + "' "
				+" order by if(l.strWebBookAccCode = '' or l.strWebBookAccCode is null,l.strWebBookAccCode,a.strInvCode); ";
								
				break;
			
			case "Sales Return" :
				
				sqlRepostFlash="SELECT a.strSRCode, DATE_FORMAT(a.dteSRDate,'%d-%m-%Y'),b.strPName, "
						+ " a.dblTotalAmt,a.dblTaxAmt,(a.dblTotalAmt+a.dblTaxAmt-a.dblDiscAmt)grandTotal,d.strVouchNo "
						+ " , IFNULL(s.strDebtorCode,''), "
  						+ " IFNULL(s.strFirstName,''), IFNULL(l.strWebBookAccCode,''), IFNULL(l.strWebBookAccName,'') "
						+ " FROM tblsalesreturnhd a "
						+ " LEFT OUTER "
						+ " JOIN tbllinkup l ON a.strCustCode=l.strMasterCode "
						+ " LEFT OUTER "
						+ " JOIN "+dbWebBook+".tblsundarydebtormaster s ON l.strAccountCode=s.strDebtorCode, "
						+ " tblpartymaster b, "+dbWebBook+".tbljvhd d "
						+ " WHERE DATE(a.dteSRDate) BETWEEN '" + fDate + "' and '" + toDate + "' AND a.strCustCode=b.strPCode " 
						+ " AND a.strSRCode=d.strSourceDocNo AND a.strClientCode='" + clientCode + "' "
						+ " ORDER BY IF(l.strWebBookAccCode = '' OR l.strWebBookAccCode IS NULL,l.strWebBookAccCode,a.strSRCode);"; 
			break;
				
			case "GRN":
				
				sqlRepostFlash=" SELECT a.strGRNCode, DATE_FORMAT(a.dtGRNDate,'%d-%m-%Y'),b.strPName, "
						+ " a.dblSubTotal,a.dblTaxAmt,a.dblTotal,d.strVouchNo, ifnull(s.strCreditorCode,''), ifnull(s.strCreditorFullName,''),ifnull(l.strWebBookAccCode,''),ifnull(l.strWebBookAccName,'') "
						+ " FROM tblgrnhd a left outer join tbllinkup l on a.strSuppCode=l.strMasterCode "
						+ " left outer join "+dbWebBook+".tblsundarycreditormaster s on l.strAccountCode=s.strCreditorCode "
						+ ",tblpartymaster b,"+dbWebBook+".tbljvhd d "
						+ " WHERE DATE(a.dtGRNDate) BETWEEN '" + fDate + "' AND '" + toDate + "' AND a.strSuppCode=b.strPCode AND a.strGRNCode=d.strSourceDocNo "
						+ " AND a.strClientCode='" + clientCode + "'"
						+ " order by if(l.strWebBookAccCode = '' or l.strWebBookAccCode is null,l.strWebBookAccCode,a.strGRNCode) ";
			
				break;
			
			case "Purchase Return" :
				sqlRepostFlash=" SELECT a.strPRCode, DATE_FORMAT(a.dtPRDate,'%d-%m-%Y'),b.strPName, "
				+" a.dblSubTotal,ifnull(a.dblTaxAmt,0),a.dblTotal,d.strVouchNo, "
				+" ifnull(s.strCreditorCode,''), ifnull(s.strCreditorFullName,'') ,ifnull(l.strWebBookAccCode,''),ifnull(l.strWebBookAccName,'') "
				+" FROM tblpurchasereturnhd a "
				+" left outer join tbllinkup l on a.strSuppCode=l.strMasterCode "
				+" left outer join "+dbWebBook+".tblsundarycreditormaster s on l.strAccountCode=s.strCreditorCode "
				+" ,tblpartymaster b, "
				+" "+dbWebBook+".tbljvhd d "
				+" WHERE DATE(a.dtPRDate) BETWEEN '" + fDate + "' AND '" + toDate + "'  "
				+" AND a.strSuppCode=b.strPCode AND a.strPRCode=d.strSourceDocNo " 
				+" AND a.strClientCode='" + clientCode + "' "
				+" order by if(l.strWebBookAccCode = '' or l.strWebBookAccCode is null,l.strWebBookAccCode,a.strGRNCode); ";
			break;
			
			}
			
			List listOfInvoice = objGlobalService.funGetList(sqlRepostFlash, "sql");
			
			if (!listOfInvoice.isEmpty()) {
				for (int i = 0; i < listOfInvoice.size(); i++) {
					Object[] objInvoice = (Object[]) listOfInvoice.get(i);
					clsFormSearchElements objBean = new clsFormSearchElements();
					objBean.setField1(objInvoice[0].toString());
					objBean.setField2(objInvoice[1].toString());
					objBean.setField3(objInvoice[2].toString());
					objBean.setField4(objInvoice[3].toString());
					objBean.setField5(objInvoice[4].toString());
					objBean.setField6(objInvoice[5].toString());
					objBean.setField7(objInvoice[6].toString());
					objBean.setField8(objInvoice[7].toString());
					objBean.setField9(objInvoice[8].toString());
					objBean.setField10(objInvoice[9].toString());
					objBean.setField11(objInvoice[10].toString());
					
					listofRepostFlash.add(objBean);
					
					}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		if(listofRepostFlash==null){
			listofRepostFlash=new ArrayList<>();
		}
		return listofRepostFlash;

	}

	@SuppressWarnings({ "rawtypes", "unused" })
	@RequestMapping(value = "/repostTransaction", method = RequestMethod.GET)
	public @ResponseBody String  funRepostTransaction(@RequestParam(value = "docCode") String docCode,@RequestParam(value = "strFromName") String strFromName, HttpServletRequest request, HttpServletResponse resp)
	{
		String res="Posting failed";
		try{
			String clientCode = request.getSession().getAttribute("clientCode").toString();
			String userCode = request.getSession().getAttribute("usercode").toString();
			String propCode = request.getSession().getAttribute("propertyCode").toString();
			if(docCode.length()>0){
			
				String [] selectedCodes=docCode.split(",");
				if(selectedCodes.length>0){
					switch(strFromName){
					case "Invoice":
						for(int i=0;i<selectedCodes.length;i++){
							objJVGenerator.funGenrateJVforInvoice(selectedCodes[i], clientCode, userCode, propCode, request);
							}
						break;
					case "Sales Return":
						for(int i=0;i<selectedCodes.length;i++){
							objJVGenerator.funGenrateJVforSalesReturn(selectedCodes[i], clientCode, userCode, propCode, request);
							}
						break;
					case "GRN":
						for(int i=0;i<selectedCodes.length;i++){
							objJVGenerator.funGenrateJVforGRN(selectedCodes[i], clientCode, userCode, propCode, request);
							}
						break;
				
					case "Purchase Return":
						for(int i=0;i<selectedCodes.length;i++){
							objJVGenerator.funGenrateJVforPurchaseReturn(selectedCodes[i], clientCode, userCode, propCode, request);
							}
						break;
						
					}
				}
			}
			res="Succesfully Reposted";
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return res;
	}

	

}
