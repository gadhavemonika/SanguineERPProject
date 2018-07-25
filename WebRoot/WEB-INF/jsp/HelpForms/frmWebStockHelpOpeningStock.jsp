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


/* body,td,th { */
/* 	font-family: Arial, Helvetica, sans-serif; */
/* 	font-size: 13px; */
/* 	color: #333333; */
/* } */
/* body { */
/* 	margin-left: 0px; */
/* 	margin-top: 15px; */
/* 	margin-right: 0px; */
/* 	margin-bottom: 0px; */
/* } */
/* .style1 { */
/* 	font-size: 16px */
/* } */
/* .style4 {font-size: 14px} */
/* .style7 { */
/* 	color: #0000FF; */
/* 	font-weight: bold; */
/* 	font-size: 16px; */
/* } */
/* .style8 { */
/* 	font-size: 15px; */
/* 	font-weight: bold; */
/* } */



</style></head>

<body>

<form action="" style="background-color: #C0E4FF;border: 1px solid black;">
<table border="1" >
  <tr>
    <td height="20" colspan="2"><div align="center" class="style7">Opening  Stock</div></td>
  </tr>
  <tr>
    <td height="20" colspan="2"><div align="left"><span class="style4">Opening  Stock form is located at Store &gt; Opening  Stock.<br />
    Opening  Stock form is used to open stock on a location. </span></div></td>
  </tr>
  <tr>
    <td height="28"><div align="left" class="style8">FIELDS</div></td>
    <td align="left" valign="middle" class="style4"><div align="center" class="style8">DESCRIPTION</div></td>
  </tr>
  <tr>
    <td height="20"><strong>OpStk Code</strong> </td>
    <td align="left" valign="middle" class="style4">OpStk Code uniquely identifies each Opening Stock entry throughout the system </td>
  </tr>
  <tr>
    <td width="158" height="20"><strong>Expiry  Date</strong> </td>
    <td width="1003" align="left" valign="middle" class="style1"><span class="style4">Select Expiry Date. </span></td>
  </tr>
                  
                  <tr>
                    <td height="20"><strong>Conversion UOM </strong></td>
                    <td align="left" valign="middle" class="style4">Select Conversion UOM. </td>
                  </tr>
                  
                  <tr>
                    <td height="20"><strong>Location Code </strong></td>
                    <td align="left" valign="middle" class="style4">Select  Location. </td>
                  </tr>
                  
                  <tr>
                    <td height="20"><strong>Product Code</strong></td>
                    <td align="left" valign="middle" class="style4"> Double Click and select new product to be added to table.</td>
                  </tr>
                  
                  <tr>
                    <td height="20"><strong>Product Name </strong></td>
                    <td align="left" valign="middle" class="style4">Displays Product Name of the selected product </td>
                  </tr>
                  
                  <tr>
                    <td height="20"><strong>UOM</strong></td>
                    <td align="left" valign="middle" class="style4">Displays Unit of Measure.</td>
                  </tr>
                  
                  <tr>
                    <td height="20"><strong>Qty</strong> </td>
                    <td align="left" valign="middle" class="style4">Enter Quantity of the selected Product.</td>
                  </tr>
                  
                  <tr>
                  
                    <td height="20"><strong>Cost Per Unit</strong></td>
                    <td align="left" valign="middle" class="style4">Enter Cost Per Unit of the selected Product.</td>
                  </tr>
                  <tr>
                    <td height="20"><strong>Revision Level</strong></td>
                    <td align="left" valign="middle" class="style4">Enter Revision Level</td>
                  </tr>
                  
                  <tr>
                    <td height="20"><strong>Lot No</strong></td>
                    <td align="left" valign="middle" class="style4">Enter Lot No</td>
                  </tr>
                  
                  <tr>
                    <td height="20"><strong>Add Button</strong> </td>
                    <td align="left" valign="middle"><div align="left"><span class="style4">Click to Add Product.to table.<br />
                    </span></div></td>
                  </tr>
                  
                  <tr>
                    <td height="20" colspan="2"><div align="center" class="style8">Table Information </div></td>
                  </tr>
                  
                  <tr>
                    <td height="20"><strong>Product Code</strong></td>
                    <td align="left" valign="middle" class="style4">Displays Product Code.</td>
                  </tr>
                  
                  <tr>
                    <td height="20"><strong>Prod Name </strong></td>
                    <td align="left" valign="middle" class="style4">Displays Product Name.</td>
                  </tr>
                  
                  <tr>
                    <td height="20"><strong>Qty</strong></td>
                    <td align="left" valign="middle" class="style4">Displays Qty</td>
                  </tr>
                  <tr>
                    <td height="20"><strong>Loose Qty </strong></td>
                    <td align="left" valign="middle" class="style4">Displays Loose Qty </td>
                    <blockquote>&nbsp;</blockquote>
                  </tr>
                  <tr>
                    <td height="20"><strong>UOM</strong></td>
                    <td align="left" valign="middle" class="style4">Displays UOM </td>
                  </tr>
                  <tr>
                    <td height="20"><strong>Cost Per Unit </strong></td>
                    <td align="left" valign="middle" class="style4">Displays Cost Per Unit </td>
                  </tr>
                  <tr>
                    <td height="20"><strong>Revision Level </strong></td>
                    <td align="left" valign="middle" class="style4">Displays Revision Level </td>
                  </tr>
                  <tr height="25">
                    <td height="25"><strong>Lot    No </strong></td>
                    <td><span class="style4">Displays Lot No</span></td>
                  </tr>
                  <tr height="25">
                    <td height="25"><strong>Delete</strong></td>
                    <td align="left" valign="middle"><span class="style4">Click to delete an unwanted product from table. </span></td>
                  </tr>
                  
                  <tr>
                    <td height="20"><strong>Lot No </strong></td>
                    <td align="left" valign="middle" class="style4">Displays Lot No </td>
                  </tr>
                  
                  <tr>
                    <td height="20"><strong>Delete</strong></td>
                    <td align="left" valign="middle" class="style4"><span class="style4">Click to delete an unwanted product from table. </span></td>
                  </tr>
                  <tr>
                    <td height="20"><strong>Submit</strong></td>
                    <td align="left" valign="middle" class="style4"><div align="left" class="style4">Click to Generate the Code and/or Save changes.</div></td>
                  </tr>
                  <tr>
                    <td height="20"><strong>Reset</strong></td>
                    <td align="left" valign="middle" class="style4">Click  to Reset Form or for another selection.</td>
                  </tr>
                  <tr>
                    <td height="20" colspan="2" align="left" valign="top"><img src="Screen Shots/OpeningStock.jpg" alt="" width="1273" height="739" align="top" /></td>
                  </tr>
</table>


		

		<p>
			<img src="../${pageContext.request.contextPath}/resources/images/webStockHelpImages/WebStockOpeningStock.jpg" alt="Mountain View" style="width:94%;height:60%;" />
		</p>
</form>
</body>

</html>
