package com.sanguine.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.bean.clsProductBatchBean;
import com.sanguine.model.clsBatchHdModel;
import com.sanguine.model.clsBatchHdModel_ID;
import com.sanguine.service.clsProductBatchService;
import com.sanguine.service.clsGlobalFunctionsService;

@Controller
public class clsManualBatch {

	final static Logger logger = Logger.getLogger(clsManualBatch.class);
	@Autowired
	clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsProductBatchService objBatchProcessService;
	clsGlobalFunctions objGlobal = null;

	/**
	 * Open Batch Form
	 * 
	 * @param objBean
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/frmManualBatch", method = RequestMethod.GET)
	public ModelAndView funOpenBatchProcessForm(@ModelAttribute("setManualBatch") clsProductBatchBean objBean, HttpServletRequest request, Model model) {
		List<clsBatchHdModel> BatchList = new ArrayList<clsBatchHdModel>();
		if (request.getSession().getAttribute("ManualBatchItemList") != null) {
			BatchList = (List<clsBatchHdModel>) request.getSession().getAttribute("ManualBatchItemList");
			model.addAttribute("BatchList", BatchList);
		}
		return new ModelAndView("frmManualBatch", "command", objBean);
	}

	/**
	 * Load Batch Data
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "finally" })
	@RequestMapping(value = "/loadBatchData", method = RequestMethod.GET)
	public @ResponseBody clsBatchHdModel funLoadBatchData(HttpServletRequest request) {
		List BatchList = new ArrayList();
		clsBatchHdModel BatchModel = null;
		try {
			String strBatchCode = request.getParameter("BatchCode").toString();
			String clientCode = request.getSession().getAttribute("clientCode").toString();
			// String
			// propCode=request.getSession().getAttribute("propertyCode").toString();
			String sql = "from clsBatchHdModel where strBatchCode='" + strBatchCode + "' and strClientCode='" + clientCode + "'";
			BatchList = objGlobalFunctionsService.funGetList(sql, "hql");
			BatchModel = (clsBatchHdModel) BatchList.get(0);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
			return BatchModel;
		}
	}

	/**
	 * Save Batch Data
	 * 
	 * @param objBean
	 * @param result
	 * @param request
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/saveManualBatch", method = RequestMethod.POST)
	public @ResponseBody String funSaveBatch(@ModelAttribute("setBatchAttribute") @Valid clsProductBatchBean objBean, BindingResult result, HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String propCode = request.getSession().getAttribute("propertyCode").toString();
		String returnvalue = "";
		objGlobal = new clsGlobalFunctions();
		try {
			if (!result.hasErrors()) {
				List<clsBatchHdModel> BatchList = objBean.getListBatchDtl();
				for (int i = 0; i < BatchList.size(); i++) {
					clsBatchHdModel tempBatchModel = BatchList.get(i);
					clsBatchHdModel BatchModel = new clsBatchHdModel(new clsBatchHdModel_ID(objBean.getStrMISCode(), tempBatchModel.getStrProdCode(), clientCode));
					BatchModel.setStrTransCode(objBean.getStrMISCode());
					BatchModel.setStrProdCode(tempBatchModel.getStrProdCode());
					BatchModel.setStrClientCode(clientCode);
					BatchModel.setStrBatchCode(tempBatchModel.getStrBatchCode());
					BatchModel.setDblQty(tempBatchModel.getDblQty());
					BatchModel.setDblPendingQty(tempBatchModel.getDblPendingQty());
					BatchModel.setDtExpiryDate(tempBatchModel.getDtExpiryDate());
					BatchModel.setStrManuBatchCode(tempBatchModel.getStrManuBatchCode());
					BatchModel.setStrTransType("MIS");
					BatchModel.setStrPropertyCode(propCode);
					BatchModel.setStrToLocCode("");
					BatchModel.setStrFromLocCode("");
					BatchModel.setStrTransCodeforUpdate("");
					objBatchProcessService.funSaveOrUpdateBatch(BatchModel);
					String sql = "update tblbatchhd set dblPendingQty='" + tempBatchModel.getDblPendingQty() + "' where strBatchCode='" + tempBatchModel.getStrBatchCode() + "' ";
					objGlobalFunctionsService.funUpdate(sql, "sql");
					returnvalue = "Inserted";
					request.getSession().removeAttribute("rptMISCode");
					request.getSession().removeAttribute("ManualBatchItemList");
				}
			}
		} catch (Exception e) {
			returnvalue = "Inserted fail";
			logger.error("Sorry, something wrong!", e);
			e.printStackTrace();
		} finally {
			return returnvalue;
		}

	}
}
