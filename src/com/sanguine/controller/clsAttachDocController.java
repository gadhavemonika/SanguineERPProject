package com.sanguine.controller;

import java.io.OutputStream;
import java.sql.Blob;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.bean.clsAttachDocBean;
import com.sanguine.model.clsAttachDocModel;
import com.sanguine.service.clsAttachDocService;

@Controller
public class clsAttachDocController {
	@Autowired
	clsAttachDocService objAttDocService;
	@Autowired
	clsGlobalFunctions objGlobal;

	@RequestMapping(value = "/attachDoc", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") clsAttachDocBean bean, BindingResult result, HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		// String docCode=request.getSession().getAttribute("code").toString();
		String docCode = request.getParameter("code").toString();
		String formTitle = request.getParameter("formName").toString();
		String transactionName = request.getParameter("transName").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		model.put("documentList", objAttDocService.funListDocs(docCode, clientCode));
		model.put("docCode", docCode);
		model.put("formTitle", formTitle);
		model.put("transactionName", transactionName);
		return new ModelAndView("frmAttachDocuments", model);
	}

	@RequestMapping(value = "/attachDoc1", method = RequestMethod.POST)
	public ModelAndView funOpenFormWithDocs(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		String docCode = request.getSession().getAttribute("code").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		model.put("documentList", objAttDocService.funListDocs(docCode, clientCode));
		return new ModelAndView("frmAttachDocuments", "document", model);
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("document") clsAttachDocBean objBean, @RequestParam("file") MultipartFile file, HttpServletRequest req) {
		
		String docCode = req.getParameter("code").toString();
		String formTitle = req.getParameter("formName").toString();

		String moduleNo = req.getSession().getAttribute("moduleNo").toString();
		String strModuleName = objGlobal.funGetModuleName(moduleNo);
		String transactionName = req.getParameter("transactionName").toString();
		try {
			Blob blob = Hibernate.createBlob(file.getInputStream());
			if (blob.length() > 0) {
				clsAttachDocModel objModel = new clsAttachDocModel();
				// objModel.setStrActualFileName(objBean.getStrActualFileName());
				objModel.setStrChangedFileName(objGlobal.funGetCurrentDate("dd-MM-yyyy").concat(file.getOriginalFilename()));
				objModel.setStrActualFileName(file.getOriginalFilename());
				objModel.setStrContentType(file.getContentType());

				objModel.setStrTrans(transactionName);
				objModel.setStrCode(docCode);
				objModel.setBinContent(blob);
				objModel.setStrUserCreated(req.getSession().getAttribute("usercode").toString());
				objModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objModel.setStrClientCode(req.getSession().getAttribute("clientCode").toString());
				objModel.setStrModuleName(strModuleName);
				objAttDocService.funSaveDoc(objModel);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/attachDoc.html?transName=" + transactionName + "&formName=" + formTitle + "&code=" + docCode);
	}

	@RequestMapping("/download/{docCode},{fileNo}")
	public void download(@PathVariable("docCode") String docCode, @PathVariable("fileNo") String fileNo, HttpServletResponse response, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		@SuppressWarnings("rawtypes")
		List listDoc = objAttDocService.funGetDoc(docCode, fileNo, clientCode);
		System.out.println(listDoc.size());

		clsAttachDocModel doc = (clsAttachDocModel) listDoc.get(0);
		System.out.println(doc.getStrActualFileName());
		try {
			response.setHeader("Content-Disposition", "inline;filename=\"" + doc.getStrActualFileName() + "\"");
			OutputStream out = response.getOutputStream();
			response.setContentType(doc.getStrContentType());
			IOUtils.copy(doc.getBinContent().getBinaryStream(), out);
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		// return null;
	}

	@RequestMapping(value = "/deleteAttachment", method = RequestMethod.POST)
	public @ResponseBody Boolean removeAttachment(@RequestParam("AttachmentName") String docName, @RequestParam("dcode") String code, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		objAttDocService.funDeleteAttachment(docName, code, clientCode);
		return true;
	}

}
