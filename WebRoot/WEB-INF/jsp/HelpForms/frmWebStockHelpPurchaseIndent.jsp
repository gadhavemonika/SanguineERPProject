<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="sp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>WebStocks</title>
<style type="text/css">

<style type="text/css">

</style></head>

<body>

<form action="" style="background-color: #C0E4FF;border: 1px solid black;">

<table border="1" >
  <tr>
    <td height="20" colspan="2"><div align="center" class="style7">Purchase Indent </div></td>
  </tr>
  <tr>
    <td height="20" colspan="2"><div align="left"><span class="style4">Pruchase Indent form is located at Store &gt; Purchase Indent <br />
    Purchase Indent form is used by Store to request material from Purchase Department. </span></div></td>
  </tr>
  <tr>
    <td height="28"><div align="left" class="style8">FIELDS</div></td>
    <td align="left" valign="middle" class="style4"><div align="center" class="style8">DESCRIPTION</div></td>
  </tr>
  <tr>
    <td height="20" class="style4"><div align="left"><span class="style4"><strong>PI Code </strong> </span></div></td>
    <td align="left" valign="middle" class="style4"><div align="left">PI Code uniquely identifies each Purchase Indent throughout the system </div></td>
  </tr>
  <tr>
    <td width="158" height="20" class="style4"><div align="left"><strong> Date</strong> </div></td>
    <td width="1003" align="left" valign="middle" class="style4"><div align="left"><span class="style4">Represents Date of creating Purchase Indent </span></div></td>
  </tr>
                  
                  <tr>
                    <td height="20" class="style4"><div align="left"><span class="style4"><strong>Location</strong></span></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Select Location. </div></td>
                  </tr>
                  
                  <tr>
                    <td height="20" class="style4"><div align="left"><span class="style4"><strong>Product Code</strong></span></div></td>
                    <td align="left" valign="middle" class="style4"> <div align="left">Double Click and select new product to be added to table.</div></td>
                  </tr>
                  
                  <tr>
                  
                    <td height="20" class="style4"><div align="left"><span class="style4"><strong> Qty</strong></span></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Enter Required Quantity of the selected Product.</div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><span class="style4"><strong>Unit Price</strong> </span></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Enter  Unit Price. </div></td>
                  </tr>
                  
                  <tr>
                    <td height="20" class="style4"><div align="left"><span class="style4"><strong>Min Level </strong></span></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays Minimum Level </div></td>
                  </tr>
                  
                  <tr>
                    <td height="20" class="style4"><div align="left" class="style9">Purpose</div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Enter Purpose </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left" class="style9">Required Date </div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Select date product required on. </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><span class="style4"><strong>Add Button</strong> </span></div></td>
                    <td align="left" valign="middle"><div align="left"><span class="style4">Click to Add Product.to table.<br />
                    </span></div></td>
                  </tr>
                  
                  <tr>
                    <td height="20" colspan="2"><div align="center" class="style8">Table Information </div></td>
                  </tr>
                  
                  <tr>
                    <td height="20" class="style4"><div align="left"><span class="style4"><strong>Product Code</strong></span></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays Product Code.</div></td>
                  </tr>
                  
                  <tr>
                    <td height="20" class="style4"><div align="left"><span class="style4"><strong>Prod Name </strong></span></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays Product Name.</div></td>
                  </tr>
                  
                  
                  <tr>
                    <td height="20" class="style4"><div align="left"><span class="style4"><strong>Qty</strong> </span></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays Qty. </div></td>
                  </tr>
                  
                  <tr>
                    <td height="20" class="style4"><div align="left"><span class="style4"><strong>Unit Price </strong></span></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays Unit Price.</div></td>
                  </tr>
                  
                  <tr>
                    <td height="20" class="style4"><div align="left"><span class="style4"><strong>Amount</strong> </span></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays Amount.</div></td>
                  </tr>
                  
                  <tr>
                    <td height="20" class="style4"><div align="left"><span class="style4"><strong>Purpose</strong> </span></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays Purpose</div></td>
                  </tr>
                  
                  <tr>
                    <td height="20" class="style4"><div align="left"><span class="style4"><strong>Required Date </strong></span></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays Required Date </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><span class="style4"><strong>Avail Stock </strong></span></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays Available Stock.</div></td>
                  </tr>
                  
                  <tr>
                    <td height="20" class="style4"><div align="left"><span class="style4"><strong>Min Level </strong></span></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left"><span class="style4">DIsplays Minimum Level </span></div></td>
                  </tr>
                  
                  <tr>
                    <td height="20" class="style4"><div align="left"><span class="style4"><strong>Against</strong></span></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays Purchase Indent against which document. </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style9"><div align="left">Delete</div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Click to delete a product from table </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style9"><div align="left">Close Purchase Indent </div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Select to Short Close Purchase Indent </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><span class="style4"><strong>Narration</strong></span></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left"><span class="style5">Enter more information if any.</span></div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Total</strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays Total Amount </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><span class="style4"><strong>Submit</strong></span></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left"><span class="style5">Click to Generate the Code and/or Save changes.</span></div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><span class="style4"><strong>Reset</strong></span></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Click  to Reset Form or for another selection.</div></td>
                  </tr>
                  <tr>
                    <td height="20" colspan="2" align="left" valign="top"><img src="Screen Shots/PurchaseIndent.jpg" alt="" width="1277" height="776" align="top" /></td>
                  </tr>
</table>

		

		<p>
			<img src="../${pageContext.request.contextPath}/resources/images/webStockHelpImages/WebStockPurchaseIndent.jpg" alt="Mountain View" style="width:94%;height:60%;" />
		</p>
</form>
</body>

</html>
