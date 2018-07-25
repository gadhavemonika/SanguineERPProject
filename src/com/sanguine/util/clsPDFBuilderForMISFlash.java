package com.sanguine.util;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class clsPDFBuilderForMISFlash extends AbstractView
{
	public clsPDFBuilderForMISFlash()
	{
		setContentType("application/pdf");
	}

	@Override
	protected boolean generatesDownloadContent()
	{
		return true;
	}

	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		List Datalist = (List) model.get("PDFMISFlashData");

		String reportName = (String) Datalist.get(0);
		reportName="MIS Month Product Wise Report";

		List tilte1 = (List) Datalist.get(1);
		List tilte2 = (List) Datalist.get(2);
		List tilte3 = (List) Datalist.get(3);

		List listDate = new ArrayList();
		try
		{
			listDate = (List) Datalist.get(4);
		}
		catch (Exception e)
		{
			listDate = new ArrayList();
		}

		String[] headerList = (String[]) Datalist.get(5);
		 response.setContentType("application/pdf");
//		 response.setHeader("Content-disposition", "attachment; filename=" + reportName.trim() + ".pdf");

		List listData = new ArrayList();
		try
		{
			listData = (List) Datalist.get(6);
		}
		catch (Exception e)
		{
			listData = new ArrayList();
		}

		// Fonts
		Font titleTextFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 22, Font.BOLD);
		Font boldTextFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.BOLD);
		Font normalTextFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.NORMAL);
		Font underlineFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.BOLD);

		Font questionTextFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.BOLD);
		Font paragraphFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL);
		Font paragraphFontBold = FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, Font.BOLD);
		Font frontTitleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.NORMAL);
		Font frontAuthorFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, Font.NORMAL);
		
		Font arialNormalFont = FontFactory.getFont("Arial",8,Font.NORMAL);
		Font arialBoldFont = FontFactory.getFont("Arial",8,Font.BOLD);

		// 1.Title

		
		
		
		Paragraph reportNameParagraph=new Paragraph();
		Chunk reportTitle = new Chunk(reportName, titleTextFont);
		reportNameParagraph.add(reportTitle);
		document.add(reportNameParagraph);

		Paragraph titleParagraph=new Paragraph();
		for (int rowtitile = 0; rowtitile < tilte1.size(); rowtitile++)
		{
			Chunk title1 = new Chunk(tilte1.get(rowtitile).toString(), normalTextFont);
			titleParagraph.add(title1);
		}
		document.add(titleParagraph);

		Paragraph title2Paragraph=new Paragraph();
		for (int rowtitile = 0; rowtitile < tilte2.size(); rowtitile++)
		{
			Chunk title2 = new Chunk(tilte2.get(rowtitile).toString(), normalTextFont);
			title2Paragraph.add(title2);			
		}
		document.add(title2Paragraph);

		Paragraph title3Paragraph=new Paragraph();
		for (int rowtitile = 0; rowtitile < tilte3.size(); rowtitile++)
		{
			Chunk title3 = new Chunk(tilte3.get(rowtitile).toString(), normalTextFont);
			title3Paragraph.add(title3);			
		}
		document.add(title3Paragraph);

		Paragraph dateParagraph=new Paragraph();
		for (int rowtitile = 0; rowtitile < listDate.size(); rowtitile++)
		{
			Chunk title = new Chunk(listDate.get(rowtitile).toString(), normalTextFont);
			dateParagraph.add(title);			
		}
		document.add(dateParagraph);
						
		document.add(Chunk.NEWLINE);		
		

		PdfPTable pdfTable = new PdfPTable(headerList.length);
		pdfTable.getDefaultCell().setBorder(0);
		
		float[]columnWidths=new float[headerList.length];
		columnWidths[0]=8;
		for (int rowtitile = 1; rowtitile < headerList.length; rowtitile++)
		{
			columnWidths[rowtitile]=3F;
		}
		pdfTable.setWidths(columnWidths);
		pdfTable.setHorizontalAlignment(Element.ALIGN_LEFT);
		

		for (int rowtitile = 0; rowtitile < headerList.length; rowtitile++)
		{
			PdfPCell headerCell = new PdfPCell();
			// headerCell.set
			if(rowtitile>1)
			{
				headerCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			}
			headerCell.setPhrase(new Phrase(headerList[rowtitile], arialBoldFont));
			headerCell.setBorder(Rectangle.BOTTOM | Rectangle.TOP);

			pdfTable.addCell(headerCell);

		}

		for (int rowCount = 0; rowCount < listData.size(); rowCount++)
		{
			List arrObj = (List) listData.get(rowCount);
			for (int Count = 0; Count < arrObj.size(); Count++)
			{
				if (null != arrObj.get(Count))
				{
					if (Count == 0 && (arrObj.get(Count+1).toString().length()==0))
					{
						Phrase categoryName = new Phrase(arrObj.get(Count).toString(), arialBoldFont);
						PdfPCell c=new PdfPCell(categoryName);
						c.setBorder(Rectangle.BOTTOM);
						c.setGrayFill(0.9f);;
						
						pdfTable.addCell(c);
					}
					else
					{
						Phrase name = new Phrase(arrObj.get(Count).toString(), arialNormalFont);
						PdfPCell c=new PdfPCell(name);
						c.setBorder(Rectangle.NO_BORDER);
						if(Count>1)
						{
							c.setHorizontalAlignment(Element.ALIGN_RIGHT);
						}
						pdfTable.addCell(c);
						
					}
				}
			}
		}

		
		document.setMargins(5, 5, 5, 5);
		document.add(pdfTable);

	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// IE workaround: write into byte array first.
		ByteArrayOutputStream baos = createTemporaryOutputStream();

		// Apply preferences and build metadata.
		Document document = newDocument();
		document.setPageSize(new Rectangle(1000,600));
		
		PdfWriter writer = newWriter(document, baos);
		prepareWriter(model, writer, request);
		buildPdfMetadata(model, document, request);

		// Build PDF document.
		document.open();
		buildPdfDocument(model, document, writer, request, response);
		document.close();
		response.setContentType("application/pdf");
		// Flush to HTTP response.
//		response.setHeader("Content-Disposition", "attachment");
		writeToResponse(response, baos);

	}

	protected Document newDocument()
	{
		return new Document(new Rectangle(612, 861),5,5,5,5);
	}

	protected PdfWriter newWriter(Document document, OutputStream os) throws DocumentException
	{
		return PdfWriter.getInstance(document, os);
	}

	protected void prepareWriter(Map<String, Object> model, PdfWriter writer, HttpServletRequest request) throws DocumentException
	{

		writer.setViewerPreferences(getViewerPreferences());
	}

	protected int getViewerPreferences()
	{
		return PdfWriter.ALLOW_PRINTING | PdfWriter.PageLayoutSinglePage;
	}

	protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request)
	{
	}

	public static boolean isNumeric(String str)
	{
		return str.matches("-?\\d+(\\.\\d+)?"); // match a number with optional
												// '-' and decimal.
	}

}
