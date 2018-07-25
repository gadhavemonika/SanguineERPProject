package com.sanguine.webbooks.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.sanguine.base.service.intfBaseService;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webbooks.bean.clsUserDefineReportBean;
import com.sanguine.webbooks.bean.clsUserDefinedReportDtlBean;
import com.sanguine.webbooks.model.clsUserDefinedReportDtlModel;
import com.sanguine.webbooks.model.clsUserDefinedReportHdModel;
import com.sanguine.webbooks.model.clsUserDefinedReportHdModel_ID;

@Controller
public class clsUserDefineReportController 
{ 

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	
	@Autowired
	clsGlobalFunctions objGlobal;
	
	@Autowired
	private intfBaseService objBaseService;
	
	
	// Open Buget Form
	@RequestMapping(value = "/frmUserDefineReport", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) 
	{
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
	
		
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmUserDefineReport_1", "command", new clsUserDefineReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmUserDefineReport", "command", new clsUserDefineReportBean());
		} else {
			return null;
		}

	}
	
		
	
	// Save or Update User Defined Report Master
		@SuppressWarnings({ "unused", "rawtypes" })
		@RequestMapping(value = "/saveUserDefinedReportMaster", method = RequestMethod.POST)
		public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsUserDefineReportBean objBean, BindingResult result, HttpServletRequest req) throws Exception
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
			
			if (!result.hasErrors())
			{
				String clientCode = req.getSession().getAttribute("clientCode").toString();
				String userCode = req.getSession().getAttribute("usercode").toString();
				clsUserDefinedReportHdModel objUserModel=funPrepareModel(objBean, userCode, clientCode);
				objBaseService.funSaveForWebBooks(objUserModel);
				
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "UserDefinedReport Code : ".concat(objUserModel.getStrReportId()));
				return new ModelAndView("redirect:/frmUserDefineReport.html?saddr=" + urlHits);
			}
			else
			{
				return new ModelAndView("redirect:/frmUserDefineReport.html?saddr=" + urlHits);
		    }
			
		}
		
		
		
		private clsUserDefinedReportHdModel funPrepareModel(clsUserDefineReportBean objBean, String userCode, String clientCode) 
		{
			long lastNo = 0;
			clsUserDefinedReportHdModel objUserModel=null;
			
			if(objBean.getStrReportId().trim().length() == 0)
			{
				lastNo = objGlobalFunctionsService.funGetLastNo("tbluserdefinedreporthd", "UserDefinedReportMaster", "intid", clientCode);
				String userDefinedRptCode = "UD" + String.format("%06d", lastNo);
				objUserModel = new clsUserDefinedReportHdModel(new clsUserDefinedReportHdModel_ID(userDefinedRptCode, clientCode));
				objUserModel.setIntid(lastNo);
				objUserModel.setStrUserCreated(userCode);
				objUserModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			}
			else
			{
				objUserModel = new clsUserDefinedReportHdModel(new clsUserDefinedReportHdModel_ID(objBean.getStrReportId(), clientCode));
			}
			objUserModel.setStrReportName(objBean.getStrReportName());
			objUserModel.setDteUserDefDate(objGlobal.funGetDate("yyyy-MM-dd", objBean.getDteUserDefDate()));
			objUserModel.setStrUserModified(userCode);
			objUserModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			
			if(objBean.getListUserDefRptDtlBean()!=null && objBean.getListUserDefRptDtlBean().size()>0)
			{
				List<clsUserDefinedReportDtlModel> listUserDefRptDtlModel = new ArrayList<clsUserDefinedReportDtlModel>();
				for(int cnt=0;cnt<objBean.getListUserDefRptDtlBean().size();cnt++)
				{
					clsUserDefinedReportDtlBean objDtlBean=objBean.getListUserDefRptDtlBean().get(cnt);
					if(objDtlBean.getIntSrNo()!=0)
					{
						clsUserDefinedReportDtlModel objDtlModel=new clsUserDefinedReportDtlModel();
						objDtlModel.setIntSrNo(objDtlBean.getIntSrNo());
						objDtlModel.setStrType(objDtlBean.getStrType());
						objDtlModel.setStrColumn(objDtlBean.getStrColumn());
						objDtlModel.setStrOperator(objDtlBean.getStrOperator());
						objDtlModel.setStrFGroup(objGlobal.funIfNull(objDtlBean.getStrFGroup(),"",objDtlBean.getStrFGroup()));
						objDtlModel.setStrTGroup(objGlobal.funIfNull(objDtlBean.getStrTGroup(),"",objDtlBean.getStrTGroup()));
						objDtlModel.setStrFAccount(objGlobal.funIfNull(objDtlBean.getStrFAccount(),"",objDtlBean.getStrFAccount()));
						objDtlModel.setStrTAccount(objGlobal.funIfNull(objDtlBean.getStrTAccount(),"",objDtlBean.getStrTAccount()));
						objDtlModel.setStrDescription(objDtlBean.getStrDescription());
						objDtlModel.setStrConstant(objDtlBean.getStrConstant());
						objDtlModel.setStrFormula(objDtlBean.getStrFormula());
						objDtlModel.setStrPrint(objDtlBean.getStrPrint());
						listUserDefRptDtlModel.add(objDtlModel);
					}
					
				}	
				objUserModel.setListUserDefRptDtlModel(listUserDefRptDtlModel);
			}
				
			return objUserModel;
		}
		
		
		
		

		// Load Master Data On Form
		@RequestMapping(value = "/loadUserDefinedReportMasterData", method = RequestMethod.GET)
		public @ResponseBody clsUserDefinedReportHdModel funAssignFields(@RequestParam("userDefinedCode") String userDefinedCode, HttpServletRequest req)
		{

			clsUserDefinedReportHdModel objUserDefModel = null;

			try
			{

				String clientCode = req.getSession().getAttribute("clientCode").toString();	
				StringBuilder sbSql = new StringBuilder("from clsUserDefinedReportHdModel where strReportId='" + userDefinedCode + "' and strClientCode='" + clientCode + "'");
				List list = null;
				list = objBaseService.funGetListForWebBooks(sbSql, "hql");
                if(list!=null && list.size()>0)
                {
                	objUserDefModel=(clsUserDefinedReportHdModel) list.get(0);
                	objUserDefModel.getListUserDefRptDtlModel().size();
                }
                else
                {
                	objUserDefModel=new clsUserDefinedReportHdModel(); 
                }
	
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
			return objUserDefModel;

		}
	
	
}
