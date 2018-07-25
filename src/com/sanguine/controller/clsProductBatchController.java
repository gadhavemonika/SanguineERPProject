package com.sanguine.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.bean.clsGRNBean;
import com.sanguine.bean.clsProductBatchBean;
import com.sanguine.model.clsBatchHdModel;
import com.sanguine.model.clsBatchHdModel_ID;
import com.sanguine.model.clsGRNDtlModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsProductBatchService;

@Controller
public class clsProductBatchController {

	@Autowired
	private clsProductBatchService objBatchProcessService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	private clsGlobalFunctions objGlobal = null;

	/**
	 * Open Batch Form
	 * 
	 * @param objBean
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/frmBatchProcess", method = RequestMethod.GET)
	public ModelAndView funOpenBatchProcessForm(@ModelAttribute("setBatchAttribute") clsGRNBean objBean, HttpServletRequest request, Model model) {
		List<clsGRNDtlModel> BatchList = new ArrayList<clsGRNDtlModel>();
		if (request.getSession().getAttribute("BatchProcessList") != null) {
			BatchList = (List<clsGRNDtlModel>) request.getSession().getAttribute("BatchProcessList");
			model.addAttribute("BatchList", BatchList);
		}
		return new ModelAndView("frmBatchProcess");
	}

	/**
	 * Save Bath Form
	 * 
	 * @param objBean
	 * @param result
	 * @param request
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/saveBatchProcessing", method = RequestMethod.POST)
	public @ResponseBody String funSaveBatch(@ModelAttribute("setBatchAttribute") @Valid clsProductBatchBean objBean, BindingResult result, HttpServletRequest request) {
		objGlobal = new clsGlobalFunctions();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String propCode = request.getSession().getAttribute("propertyCode").toString();
		String returnvalue = "";
		try {
			if (!result.hasErrors()) {
				List<clsBatchHdModel> BatchList = objBean.getListBatchDtl();
				for (int i = 0; i < BatchList.size(); i++) {
					clsBatchHdModel tempBatchModel = BatchList.get(i);
					clsBatchHdModel BatchModel = new clsBatchHdModel(new clsBatchHdModel_ID(tempBatchModel.getStrTransCode(), tempBatchModel.getStrProdCode(), clientCode));
					BatchModel.setStrTransCode(tempBatchModel.getStrTransCode());
					BatchModel.setStrProdCode(tempBatchModel.getStrProdCode());
					BatchModel.setStrClientCode(clientCode);
					long lastNo = objGlobalFunctionsService.funGetLastNo("tblbatchhd", "BatchCode", "intSrNo", clientCode);
					String code = tempBatchModel.getStrProdCode() + String.format("%04d", lastNo);
					BatchModel.setIntSrNo(lastNo);
					BatchModel.setStrBatchCode(code);
					BatchModel.setDblQty(tempBatchModel.getDblQty());
					BatchModel.setDblPendingQty(tempBatchModel.getDblQty());
					BatchModel.setDtExpiryDate(objGlobal.funGetDate("yyyy-MM-dd", tempBatchModel.getDtExpiryDate()));
					BatchModel.setStrManuBatchCode(tempBatchModel.getStrManuBatchCode());
					BatchModel.setStrTransType("GRN");
					BatchModel.setStrPropertyCode(propCode);
					BatchModel.setStrToLocCode("");
					BatchModel.setStrFromLocCode("");
					BatchModel.setStrTransCodeforUpdate("");
					objBatchProcessService.funSaveOrUpdateBatch(BatchModel);
					returnvalue = "Inserted";
					request.getSession().removeAttribute("BatchProcessList");
				}
			}
		} catch (Exception e) {
			returnvalue = "Inserted fail";
			e.printStackTrace();
		} finally {
			return returnvalue;
		}

	}

	/**
	 * Load Product Batch
	 * 
	 * @param strProdCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/loadProdBatchData", method = RequestMethod.GET)
	public @ResponseBody List<clsBatchHdModel> funGetBatchData(@RequestParam("prodCode") String strProdCode, HttpServletRequest request) {
		objGlobal = new clsGlobalFunctions();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		List<clsBatchHdModel> list = objBatchProcessService.funGetList(clientCode, strProdCode);
		return list;
	}

}
