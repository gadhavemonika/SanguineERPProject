package com.sanguine.util;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
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

public class clsPDFBuilderMISLocationWiseCategoryWiseReport extends AbstractView
{
	public clsPDFBuilderMISLocationWiseCategoryWiseReport()
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

		List Datalist = (List) model.get("listWithReportName");

//		String reportName = Datalist.get(1);
		String reportName = "MIS Location Wise Category Wise Report";

		String[] HeaderList = (String[]) Datalist.get(3);
//		 response.setContentType("application/pdf");
//		 response.setHeader("Content-disposition", "attachment; filename=" + reportName.trim() + ".pdf");

		List listData = new ArrayList();
		try
		{
			listData = (List) Datalist.get(4);
		}
		catch (Exception e)
		{
			listData = new ArrayList();
		}

		String companyName = Datalist.get(5).toString();
//		reportName = Datalist.get(1).toString();
		String reportingFor = Datalist.get(6).toString();
		String periodFor="",printedOn="";
		List filterData = (List) Datalist.get(2);
		for(int i=0;i<filterData.size();i++)
		{
			String[] obj =   filterData.get(i).toString().split(",");
			if(i==0)
			{
				periodFor = obj[0];
			}
			else if(i==1)
			{
				periodFor = periodFor+"  :"+obj[0];
			}
			else if(i==2)
			{
				printedOn = obj[0]; 
			}
			else
			{
				printedOn = printedOn +" :"+ obj[0]; 
			}
		}
//		
//		String periodFor = Datalist.get(6).toString();
//		String printedOn = Datalist.get(7).toString();

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

		Font arialNormalFont = FontFactory.getFont("Arial", 10, Font.NORMAL);
		Font arialBoldFont = FontFactory.getFont("Arial", 8, Font.BOLD);

		// 1.Title

		Paragraph reportNameParagraph = new Paragraph();
		Chunk reportTitle = new Chunk(reportName, titleTextFont);
		reportNameParagraph.add(reportTitle);
		document.add(reportNameParagraph);

		Paragraph titleParagraph = new Paragraph();
		Chunk title1 = new Chunk(companyName, normalTextFont);
		titleParagraph.add(title1);
		document.add(titleParagraph);

		Paragraph title2Paragraph = new Paragraph();
		Chunk title2 = new Chunk(reportingFor, normalTextFont);
		title2Paragraph.add(title2);
		document.add(title2Paragraph);

		Paragraph title3Paragraph = new Paragraph();
		Chunk title3 = new Chunk(periodFor, normalTextFont);
		title3Paragraph.add(title3);
		document.add(title3Paragraph);

		Paragraph dateParagraph = new Paragraph();
		Chunk title = new Chunk(printedOn, normalTextFont);
		dateParagraph.add(title);
		document.add(dateParagraph);

		document.add(Chunk.NEWLINE);

		PdfPTable pdfTable = new PdfPTable(HeaderList.length);
		pdfTable.getDefaultCell().setBorder(0);
		pdfTable.setTotalWidth(PageSize.A4.rotate().getWidth()+120);
		pdfTable.setLockedWidth(true);

		float[] columnWidths = new float[HeaderList.length];
		columnWidths[0] = 200;
		columnWidths[1] = 300;
		columnWidths[2] = 100;
		for (int rowtitile = 3; rowtitile < HeaderList.length; rowtitile++)
		{
			columnWidths[rowtitile] = 150F;
		}
		pdfTable.setWidths(columnWidths);
		pdfTable.setHorizontalAlignment(Element.ALIGN_LEFT);

		for (int rowtitile = 0; rowtitile < HeaderList.length; rowtitile++)
		{
			PdfPCell headerCell = new PdfPCell();
			// headerCell.set

			headerCell.setPhrase(new Phrase(HeaderList[rowtitile], arialBoldFont));
			headerCell.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
			headerCell.setColspan(1);

			pdfTable.addCell(headerCell);

		}


		DecimalFormat decimalFormat2=new DecimalFormat("0.00");

		// create data rows
		// aRow is add Row
		int ColrowCount = 1;
		for (int rowCount = 0; rowCount < listData.size(); rowCount++)
		{			
			List arrObj = (List) listData.get(rowCount);
			if(arrObj.get(0).toString().isEmpty()&& arrObj.get(2).toString().isEmpty())
			{
				continue;
			}
			boolean isTotal=false;
			if(arrObj.get(2).toString().equalsIgnoreCase("Total"))
			{
				isTotal=true;
			}
			
			for (int Count = 0; Count < arrObj.size(); Count++)
			{
				if (null != arrObj.get(Count))
				{

					if (Count>2)
					{
						PdfPCell c = new PdfPCell();
						if(isTotal)
						{
							Phrase name = new Phrase(decimalFormat2.format(Double.parseDouble(arrObj.get(Count).toString())), arialNormalFont);
							c = new PdfPCell(name);
							c.setBorder(Rectangle.NO_BORDER);
							c.setColspan(1);
							c.setGrayFill(0.9f);;
													
						}
						else
						{
							Phrase name = new Phrase(decimalFormat2.format(Double.parseDouble(arrObj.get(Count).toString())), arialBoldFont);
							c = new PdfPCell(name);
							c.setBorder(Rectangle.NO_BORDER);
							c.setColspan(1);
						}
						if (Count > 1)
						{
							c.setHorizontalAlignment(Element.ALIGN_RIGHT);
						}
						pdfTable.addCell(c);
					}
					else
					{
						PdfPCell c = new PdfPCell();
						if(isTotal)
						{
							Phrase name = new Phrase(arrObj.get(Count).toString(), arialBoldFont);
							c = new PdfPCell(name);
							c.setBorder(Rectangle.NO_BORDER);
							c.setColspan(1);
							c.setGrayFill(0.9f);;
						}
						else
						{
							Phrase name = new Phrase(arrObj.get(Count).toString(), arialNormalFont);
							c = new PdfPCell(name);
							c.setBorder(Rectangle.NO_BORDER);
							c.setColspan(1);
						}
						if (Count > 1)
						{
							c.setHorizontalAlignment(Element.ALIGN_LEFT);
						}
						
						pdfTable.addCell(c);
					}
				}				
			}

		}

		//document.setMargins(5, 5, 5, 5);
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

		String reportName="MIS Location Wise Category Wise Report";
		// Flush to HTTP response.
		 response.setContentType("application/pdf");
//		 response.setHeader("Content-disposition", "attachment; filename=" + reportName.trim() + ".pdf");
		writeToResponse(response, baos);

	}

	protected Document newDocument()
	{
		return new Document(PageSize.A4.rotate(),5,5,5,5);
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
