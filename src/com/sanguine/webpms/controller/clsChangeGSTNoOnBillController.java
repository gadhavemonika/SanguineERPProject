package com.sanguine.webpms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.webpms.bean.clsTaxCalculation;
import com.sanguine.webpms.bean.clsTaxProductDtl;
import com.sanguine.webpms.bean.clsVoidBillBean;
import com.sanguine.webpms.model.clsBillDtlModel;
import com.sanguine.webpms.model.clsBillHdModel;
import com.sanguine.webpms.model.clsBillTaxDtlModel;
import com.sanguine.webpms.model.clsVoidBillDtlModel;
import com.sanguine.webpms.model.clsVoidBillHdModel;
import com.sanguine.webpms.model.clsVoidBillTaxDtlModel;
import com.sanguine.webpms.service.clsVoidBillService;

@Transactional
@Controller
public class clsChangeGSTNoOnBillController {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	
	@Autowired
	private clsGlobalFunctions objGlobal;
	
	@Autowired 
	private clsVoidBillService objVoidBillService;
	
	@Autowired
	clsPMSUtilityFunctions objPMSUtility;
	
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private clsSetupMasterService objSetupMasterService;
	
	@RequestMapping(value="/frmChangeGSTNoOnBill",method=RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String,Object> model,HttpServletRequest request){
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String sql="select strReasonCode,strReasonDesc from tblreasonmaster where strClientCode='"+clientCode+"'";
		List listReason = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
		Map<String,String> hmReason=new HashMap<>();
		if(null!=listReason){
			if(listReason.size()>0){
				for(int i=0;i<listReason.size();i++){
					Object ob[]=(Object[]) listReason.get(i);	
					hmReason.put(ob[0].toString(), ob[1].toString());
				}
			}
		}
		model.put("listReason", listReason);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmChangeGSTNoOnBill_1", "command", new clsVoidBillBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmChangeGSTNoOnBill", "command", new clsVoidBillBean());
		} else {
			return null;
		}
	}
	
	
	
	@RequestMapping(value = "/updateGSTNoOnBill", method = RequestMethod.POST)
	public ModelAndView funUpdateGSTNoOnBill(@ModelAttribute("command") @Valid clsVoidBillBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String PMSDate = objGlobal.funGetDate("yyyy-MM-dd", req.getSession().getAttribute("PMSDate").toString());
		String strSuccessMsg="";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		String strBillNo="";
		if (!result.hasErrors()) {
			if(null!=objBean)
			{
				//String voidType=objBean.getStrVoidType();
				strBillNo=objBean.getStrBillNo();
				List<clsVoidBillBean> listVoidBilldtlBean =objBean.getListVoidBilldtl();
				
				clsBillHdModel objBillModel=objVoidBillService.funGetBillData(objBean.getStrRoomNo(), strBillNo, clientCode);
				if(null!=listVoidBilldtlBean)
				{
					
						if(objBillModel!=null)
						{
							objBillModel.setStrGSTNo(objBean.getStrGSTNo());
							objBillModel.setStrCompanyName(objBean.getStrCompanyName());
							double grandTotalVoidBill = 0;
							double grandTotalBillHd = 0;
							List<clsBillDtlModel> listBillDtlModels=new ArrayList();
				
							String sql = " Select *  from tblbilldtl a where a.strBillNo='" + strBillNo + "' and a.strClientCode='" + clientCode + "' ";
							List listBillDtl = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");

							clsBillDtlModel objBillDtlModel=new clsBillDtlModel();
							clsTaxProductDtl objTaxProductDtl = new clsTaxProductDtl();
							clsVoidBillBean objVoidListBean = new clsVoidBillBean();
							
							List<clsTaxProductDtl> listTaxProdDtl = new ArrayList<clsTaxProductDtl>();
							List<clsBillTaxDtlModel> listBillTaxModels =new ArrayList();//objBillModel.getListBillTaxDtlModels();
							
							for (int j = 0; j < listBillDtl.size(); j++) {
								Object[] objMdl = (Object[]) listBillDtl.get(j);
								
								for(int k=0;k<listVoidBilldtlBean.size();k++)
								{
									objVoidListBean = listVoidBilldtlBean.get(k);
									if(objMdl[6].toString().equals(objVoidListBean.getStrRevenueCode()))
									{
										//Bill data
										objBillDtlModel=new clsBillDtlModel();
										grandTotalBillHd += Double.parseDouble(objMdl[7].toString());
										objBillDtlModel.setStrFolioNo(objMdl[1].toString());
										objBillDtlModel.setDteDocDate(objMdl[2].toString());
										objBillDtlModel.setStrDocNo(objMdl[3].toString());
										objBillDtlModel.setStrPerticulars(objMdl[4].toString());
										objBillDtlModel.setStrRevenueType(objMdl[5].toString());
										objBillDtlModel.setStrRevenueCode(objMdl[6].toString());
										objBillDtlModel.setDblDebitAmt(Double.parseDouble(objMdl[7].toString()));
										objBillDtlModel.setDblCreditAmt(Double.parseDouble(objMdl[8].toString()));
										objBillDtlModel.setDblBalanceAmt(Double.parseDouble(objMdl[9].toString()));
										
										listBillDtlModels.add(objBillDtlModel);
										//hmBillDtl.put(objMdl[6].toString(), objBillDtlModel);
										
										objTaxProductDtl = new clsTaxProductDtl();
										objTaxProductDtl.setStrTaxProdCode(objBillDtlModel.getStrRevenueCode());
										objTaxProductDtl.setStrTaxProdName(objBillDtlModel.getStrPerticulars());
										objTaxProductDtl.setDblTaxProdAmt(objBillDtlModel.getDblDebitAmt());
										
										listTaxProdDtl.add(objTaxProductDtl);
										Map<String, List<clsTaxCalculation>> hmTaxCalDtl = objPMSUtility.funCalculatePMSTax(listTaxProdDtl, "Income Head");
										
										if (hmTaxCalDtl.size() > 0) {
											List<clsTaxCalculation> listTaxCal = hmTaxCalDtl.get(objBillDtlModel.getStrRevenueCode());
											for (clsTaxCalculation objTaxCal : listTaxCal) {
												clsBillTaxDtlModel objBillTaxDtl = new clsBillTaxDtlModel();
												objBillTaxDtl.setStrDocNo(objBillDtlModel.getStrDocNo());
												objBillTaxDtl.setStrTaxCode(objTaxCal.getStrTaxCode());
												objBillTaxDtl.setStrTaxDesc(objTaxCal.getStrTaxDesc());
												objBillTaxDtl.setDblTaxableAmt(objTaxCal.getDblTaxableAmt());
												objBillTaxDtl.setDblTaxAmt(objTaxCal.getDblTaxAmt());
												listBillTaxModels.add(objBillTaxDtl);
											}
										}
									}
								}
							}
							objBillModel.setListBillTaxDtlModels(listBillTaxModels);
							objBillModel.setListBillDtlModels(listBillDtlModels);
							objBillModel.setDblGrandTotal(grandTotalBillHd);
							objVoidBillService.funUpdateBillData(objBillModel);
							strSuccessMsg="Bill Successfully Updated: ";
						}
                }
		    }
		}
		req.getSession().setAttribute("success", true);
		req.getSession().setAttribute("successMessage", strSuccessMsg.concat(strBillNo));

		return new ModelAndView("redirect:/frmChangeGSTNoOnBill.html?saddr=" + urlHits);
	  }
	
}	