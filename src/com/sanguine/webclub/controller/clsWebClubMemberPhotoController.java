package com.sanguine.webclub.controller;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;
import javax.validation.Valid;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webclub.bean.clsWebClubMemberPhotoBean;
import com.sanguine.webclub.model.clsWebClubMemberPhotoModel;
import com.sanguine.webclub.model.clsWebClubMemberPhotoModel_ID;
import com.sanguine.webclub.service.clsWebClubMemberPhotoService;

@Controller
public class clsWebClubMemberPhotoController {

	@Autowired
	private clsWebClubMemberPhotoService objWebClubMemberPhotoService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;

	// Open WebClubLockerMaster
	@RequestMapping(value = "/frmMemberPhoto", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmMemberPhoto_1", "command", new clsWebClubMemberPhotoBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmMemberPhoto", "command", new clsWebClubMemberPhotoBean());
		} else {
			return null;
		}

	}

	// Save or Update WebClubLockerMaster
	@RequestMapping(value = "/saveWebClubMemberPhoto", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsWebClubMemberPhotoBean objBean, BindingResult result, HttpServletRequest req, @RequestParam("memberImage") MultipartFile file) throws IOException {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propCode = req.getSession().getAttribute("propertyCode").toString();
			clsWebClubMemberPhotoModel objModel = funPrepareModel(objBean, userCode, propCode, clientCode, file);
			objWebClubMemberPhotoService.funAddUpdateWebClubMemberPhoto(objModel);

			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Member Code : ".concat(objModel.getStrMemberCode()));
			return new ModelAndView("redirect:/frmMemberPhoto.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("redirect:/frmMemberPhoto.html?saddr=" + urlHits);
		}

	}

	// Convert bean to model function
	@SuppressWarnings("unused")
	private clsWebClubMemberPhotoModel funPrepareModel(clsWebClubMemberPhotoBean objBean, String userCode, String propCode, String clientCode, MultipartFile file) throws IOException {
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsWebClubMemberPhotoModel objModel;
		objModel = new clsWebClubMemberPhotoModel(new clsWebClubMemberPhotoModel_ID(objBean.getStrMemberCode(), clientCode));

		objModel.setStrPropertyCode(propCode);
		objModel.setStrUserCreated(userCode);
		objModel.setStrUserModified(userCode);
		objModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrMemberName(objBean.getStrMemberName());

		if (file.getSize() != 0) {
			System.out.println(file.getOriginalFilename());
			File imgFolder = new File(System.getProperty("user.dir") + "\\ProductIcon");
			if (!imgFolder.exists()) {
				if (imgFolder.mkdir()) {
					System.out.println("Directory is created! " + imgFolder.getAbsolutePath());
				} else {
					System.out.println("Failed to create directory!");
				}
			}
			File fileImageIcon = new File(System.getProperty("user.dir") + "\\ProductIcon\\" + file.getOriginalFilename());
			String formatName = "jpg";
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			BufferedImage bufferedImage = ImageIO.read(funInputStreamToBytearrayInputStrean(file.getInputStream()));
			String path = fileImageIcon.getPath().toString();
			ImageIO.write(bufferedImage, "jpg", new File(path));
			BufferedImage bfImg = scaleImage(150, 155, path);
			ImageIO.write(bfImg, "jpg", byteArrayOutputStream);
			byte[] imageBytes = byteArrayOutputStream.toByteArray();
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);

			Blob blobProdImage = Hibernate.createBlob(byteArrayInputStream);
			objModel.setStrMemberImage(blobProdImage);

			if (fileImageIcon.exists()) {
				fileImageIcon.delete();
			}
		} else {
			objModel.setStrMemberImage(funBlankBlob());
		}

		return objModel;
	}

	private Blob funBlankBlob() {
		Blob blob = new Blob() {

			@Override
			public void truncate(long len) throws SQLException {
				// TODO Auto-generated method stub

			}

			@Override
			public int setBytes(long pos, byte[] bytes, int offset, int len) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int setBytes(long pos, byte[] bytes) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public OutputStream setBinaryStream(long pos) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public long position(Blob pattern, long start) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public long position(byte[] pattern, long start) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public long length() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public byte[] getBytes(long pos, int length) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public InputStream getBinaryStream(long pos, long length) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public InputStream getBinaryStream() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void free() throws SQLException {
				// TODO Auto-generated method stub

			}
		};
		return blob;
	}

	public BufferedImage scaleImage(int WIDTH, int HEIGHT, String filename) {
		BufferedImage bi = null;
		try {
			ImageIcon ii = new ImageIcon(filename);// path to image
			bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
			Graphics2D gra2d = (Graphics2D) bi.createGraphics();
			gra2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
			gra2d.drawImage(ii.getImage(), 0, 0, WIDTH, HEIGHT, null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return bi;
	}

	@SuppressWarnings("finally")
	private ByteArrayInputStream funInputStreamToBytearrayInputStrean(InputStream ins) {
		ByteArrayInputStream byteArrayInputStream = null;
		try {
			byte[] buff = new byte[8000];

			int bytesRead = 0;

			ByteArrayOutputStream bao = new ByteArrayOutputStream();

			while ((bytesRead = ins.read(buff)) != -1) {
				bao.write(buff, 0, bytesRead);
			}

			byte[] data = bao.toByteArray();

			byteArrayInputStream = new ByteArrayInputStream(data);

		} catch (Exception ex) {
			ex.printStackTrace();

		} finally {
			return byteArrayInputStream;
		}
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadWebClubMemberPhoto", method = RequestMethod.GET)
	public @ResponseBody void funAssignFields(@RequestParam("docCode") String memberCode, HttpServletRequest req, HttpServletResponse response) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsWebClubMemberPhotoModel objmemPhotoModel = objWebClubMemberPhotoService.funGetWebClubMemberPhoto(memberCode, clientCode);
		try {
			if (null == objmemPhotoModel) {
				objmemPhotoModel = new clsWebClubMemberPhotoModel();
				objmemPhotoModel.setStrMemberCode("Invalid Code");
			}
			Blob image = null;
			byte[] imgData = null;
			image = objmemPhotoModel.getStrMemberImage();
			if (null != image && image.length() > 0) {
				imgData = image.getBytes(1, (int) image.length());
				response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
				OutputStream o = response.getOutputStream();
				o.write(imgData);
				o.flush();
				o.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
