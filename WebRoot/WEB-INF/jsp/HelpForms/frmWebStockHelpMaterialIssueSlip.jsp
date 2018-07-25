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


<!--
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
/* .style5 {font-size: 14} */
/* .style7 { */
/* 	color: #0000FF; */
/* 	font-weight: bold; */
/* 	font-size: 16px; */
/* } */
/* .style8 { */
/* 	font-size: 15px; */
/* 	font-weight: bold; */
/* } */
-->
</style></head>

<body>

<form action="" style="background-color: #C0E4FF;border: 1px solid black;">

<table>
  <tr>
    <td height="20"   style="background-color: #C0E4FF;border: 1px solid black;" colspan="2" style="background-color: #C0E4FF;border: 1px solid black;"><div align="center" class="style7">Material Issue Slip </div></td>
  </tr>
  <tr>
    <td height="20"    colspan="2" style="background-color: #C0E4FF;border: 1px solid black;"><div align="left"><span class="style4"  >Material Issue Slip form is located at Store &gt;Material Issue Slip.<br />
    Material Issue Slip form is used by Store to transfer material   from one location to other location. MIS can be Direct or against   Requisition. </span></div></td>
  </tr>
  <tr>
    <td height="28" style="background-color: #C0E4FF;border: 1px solid black;"><div align="left" class="style8">FIELDS</div></td>
    <td align="left" valign="middle" class="style4"  style="background-color: #C0E4FF;border: 1px solid black;" style="background-color: #C0E4FF;border: 1px solid black;"><div align="center" class="style8">DESCRIPTION</div></td>
  </tr>
  <tr>
    <td height="20"   style="background-color: #C0E4FF;border: 1px solid black;" style="background-color: #C0E4FF;border: 1px solid black;"><strong>MIS Code</strong> </td>
    <td align="left" valign="middle" class="style4"  style="background-color: #C0E4FF;border: 1px solid black;" style="background-color: #C0E4FF;border: 1px solid black;">MIS Code uniquely identifies each MIS throughout the system </td>
  </tr>
  <tr>
    <td width="158" height="20"   style="background-color: #C0E4FF;border: 1px solid black;" style="background-color: #C0E4FF;border: 1px solid black;"><strong>MIS Date</strong> </td>
    <td width="1003" align="left" valign="middle" class="style1" style="background-color: #C0E4FF;border: 1px solid black;"><span class="style4" >Represents Date of creating MIS </span></td>
  </tr>
                  
                  <tr>
                    <td height="20"   style="background-color: #C0E4FF;border: 1px solid black;" style="background-color: #C0E4FF;border: 1px solid black;"><strong>Location By</strong></td>
                    <td align="left" valign="middle" class="style4"  style="background-color: #C0E4FF;border: 1px solid black;" style="background-color: #C0E4FF;border: 1px solid black;">Selects From Location. </td>
                  </tr>
                  
                  <tr>
                    <td height="20"   style="background-color: #C0E4FF;border: 1px solid black;" style="background-color: #C0E4FF;border: 1px solid black;"><strong>Location To </strong></td>
                    <td align="left" valign="middle" class="style4"  style="background-color: #C0E4FF;border: 1px solid black;" style="background-color: #C0E4FF;border: 1px solid black;">Selects To Location. </td>
                  </tr>
                  <tr>
                    <td height="20"   style="background-color: #C0E4FF;border: 1px solid black;" style="background-color: #C0E4FF;border: 1px solid black;"><strong>Against</strong></td>
                    <td align="left" valign="middle" class="style4"  style="background-color: #C0E4FF;border: 1px solid black;" style="background-color: #C0E4FF;border: 1px solid black;">Select MIS is Direct or MIS against Work Order. </td>
                  </tr>
                  
                  <tr>
                    <td height="20"   style="background-color: #C0E4FF;border: 1px solid black;" style="background-color: #C0E4FF;border: 1px solid black;"><strong>Fill</strong></td>
                    <td align="left" valign="middle" class="style4"  style="background-color: #C0E4FF;border: 1px solid black;" style="background-color: #C0E4FF;border: 1px solid black;">Click to fill materials in table, required to make the Product in Work Order.</td>
                  </tr>
                  
                  <tr>
                    <td height="20"   style="background-color: #C0E4FF;border: 1px solid black;" style="background-color: #C0E4FF;border: 1px solid black;"><strong>Product Code</strong></td>
                    <td align="left" valign="middle" class="style4"  style="background-color: #C0E4FF;border: 1px solid black;" style="background-color: #C0E4FF;border: 1px solid black;"> Double Click and select new product to be added to table.</td>
                  </tr>
                  
                  <tr>
                    <td height="20"   style="background-color: #C0E4FF;border: 1px solid black;" style="background-color: #C0E4FF;border: 1px solid black;"><strong>Product Name </strong></td>
                    <td align="left" valign="middle" class="style4"  style="background-color: #C0E4FF;border: 1px solid black;" style="background-color: #C0E4FF;border: 1px solid black;">Displays Product Name of the selected product </td>
                  </tr>
                  
                  <tr>
                    <td height="20"   style="background-color: #C0E4FF;border: 1px solid black;" style="background-color: #C0E4FF;border: 1px solid black;"><strong>UOM</strong></td>
                    <td align="left" valign="middle" class="style4"  style="background-color: #C0E4FF;border: 1px solid black;" style="background-color: #C0E4FF;border: 1px solid black;">Displays Unit of Measure.</td>
                  </tr>
                  
                  <tr>
                    <td height="20"   style="background-color: #C0E4FF;border: 1px solid black;" style="background-color: #C0E4FF;border: 1px solid black;"><strong>Stock</strong> </td>
                    <td align="left" valign="middle" class="style4"  style="background-color: #C0E4FF;border: 1px solid black;" style="background-color: #C0E4FF;border: 1px solid black;">Displays Stock of the Product on Current Location.</td>
                  </tr>
                  
                  <tr>
                  
                    <td height="20"   style="background-color: #C0E4FF;border: 1px solid black;" style="background-color: #C0E4FF;border: 1px solid black;"><strong>Issue Qty</strong></td>
                    <td align="left" valign="middle" class="style4"  style="background-color: #C0E4FF;border: 1px solid black;" style="background-color: #C0E4FF;border: 1px solid black;">Enter Issue Quantity of the selected Product.</td>
                  </tr>
                  <tr>
                    <td height="20"   style="background-color: #C0E4FF;border: 1px solid black;" style="background-color: #C0E4FF;border: 1px solid black;"><strong>Unit Price</strong> </td>
                    <td align="left" valign="middle" class="style4"  style="background-color: #C0E4FF;border: 1px solid black;" style="background-color: #C0E4FF;border: 1px solid black;">Displays Unit Price. </td>
                  </tr>
                  
                  <tr>
                    <td height="20"   style="background-color: #C0E4FF;border: 1px solid black;" style="background-color: #C0E4FF;border: 1px solid black;"><strong>Remarks</strong></td>
                    <td align="left" valign="middle" class="style4"  style="background-color: #C0E4FF;border: 1px solid black;" style="background-color: #C0E4FF;border: 1px solid black;">Enter Remarks on the selected Product.</td>
                  </tr>
                  
                  <tr>
                    <td height="20"   style="background-color: #C0E4FF;border: 1px solid black;" style="background-color: #C0E4FF;border: 1px solid black;"><strong>Add Button</strong> </td>
                    <td align="left" valign="middle"><div align="left" style="background-color: #C0E4FF;border: 1px solid black;"><span class="style4"  >Click to Add Product.to table.<br />
                    </span></div></td>
                  </tr>
                  
                  <tr>
                    <td height="20"   style="background-color: #C0E4FF;border: 1px solid black;" colspan="2" style="background-color: #C0E4FF;border: 1px solid black;"><div align="center" class="style8">Table Information </div></td>
                  </tr>
                  
                  <tr>
                    <td height="20"   style="background-color: #C0E4FF;border: 1px solid black;" style="background-color: #C0E4FF;border: 1px solid black;"><strong>Product Code</strong></td>
                    <td align="left" valign="middle" class="style4"  style="background-color: #C0E4FF;border: 1px solid black;" style="background-color: #C0E4FF;border: 1px solid black;">Displays Product Code.</td>
                  </tr>
                  
                  <tr>
                    <td height="20"   style="background-color: #C0E4FF;border: 1px solid black;" style="background-color: #C0E4FF;border: 1px solid black;"><strong>Prod Name </strong></td>
                    <td align="left" valign="middle" class="style4"  style="background-color: #C0E4FF;border: 1px solid black;" style="background-color: #C0E4FF;border: 1px solid black;">Displays Product Name.</td>
                  </tr>
                  
                  <tr>
                    <td height="20"   style="background-color: #C0E4FF;border: 1px solid black;" style="background-color: #C0E4FF;border: 1px solid black;"><strong>UOM</strong></td>
                    <td align="left" valign="middle" class="style4"  style="background-color: #C0E4FF;border: 1px solid black;" style="background-color: #C0E4FF;border: 1px solid black;">Displays UOM.</td>
                  </tr>
                  
                  <tr>
                    <td height="20"   style="background-color: #C0E4FF;border: 1px solid black;" style="background-color: #C0E4FF;border: 1px solid black;"><strong>Stock</strong></td>
                    <td align="left" valign="middle" class="style4"  style="background-color: #C0E4FF;border: 1px solid black;" style="background-color: #C0E4FF;border: 1px solid black;">Displays Stock of Product.</td>
                  </tr>
                  
                  <tr>
                    <td height="20"   style="background-color: #C0E4FF;border: 1px solid black;" style="background-color: #C0E4FF;border: 1px solid black;"><strong>Issue Qty</strong></td>
                    <td align="left" valign="middle" class="style4"  style="background-color: #C0E4FF;border: 1px solid black;">Displays Issue Qty of Product. </td>
                  </tr>
                  
                  
                  <tr>
                    <td height="20"   style="background-color: #C0E4FF;border: 1px solid black;" style="background-color: #C0E4FF;border: 1px solid black;"><strong>Pending Qty</strong> </td>
                    <td align="left" valign="middle" class="style4"  style="background-color: #C0E4FF;border: 1px solid black;">Displays Pending Qty. </td>
                  </tr>
                  
                  <tr>
                    <td height="20"   style="background-color: #C0E4FF;border: 1px solid black;"><strong>Unit Price </strong></td>
                    <td align="left" valign="middle" class="style4"  style="background-color: #C0E4FF;border: 1px solid black;">Displays Unit Price.</td>
                  </tr>
                  
                  <tr>
                    <td height="20"   style="background-color: #C0E4FF;border: 1px solid black;"><strong>Total Price</strong> </td>
                    <td align="left" valign="middle" class="style4"  style="background-color: #C0E4FF;border: 1px solid black;">Displays Total Price.</td>
                  </tr>
                  
                  <tr>
                    <td height="20"   style="background-color: #C0E4FF;border: 1px solid black;"><strong>MR Code</strong> </td>
                    <td align="left" valign="middle" class="style4"  style="background-color: #C0E4FF;border: 1px solid black;">Displays MR Code </td>
                  </tr>
                  
                  <tr>
                    <td height="20"   style="background-color: #C0E4FF;border: 1px solid black;"><strong>Remarks</strong></td>
                    <td align="left" valign="middle" class="style4"  style="background-color: #C0E4FF;border: 1px solid black;">Displays Remarks.</td>
                  </tr>
                  
                  <tr>
                    <td height="20"   style="background-color: #C0E4FF;border: 1px solid black;"><strong>Delete</strong></td>
                    <td align="left" valign="middle" class="style4" style="background-color: #C0E4FF;border: 1px solid black;" ><span class="style4"  >Click to delete an unwanted product from table. </span></td>
                  </tr>
                  
                  <tr>
                    <td height="20"   style="background-color: #C0E4FF;border: 1px solid black;"><strong>Total Amount </strong></td>
                    <td align="left" valign="middle" class="style4"  style="background-color: #C0E4FF;border: 1px solid black;">Displays total price of products. </td>
                  </tr>
                  <tr>
                    <td height="20"   style="background-color: #C0E4FF;border: 1px solid black;"><strong>Narration</strong></td>
                    <td align="left" valign="middle" class="style4"  style="background-color: #C0E4FF;border: 1px solid black;"><span class="style5">Enter more information if any.</span></td>
                  </tr>
                  <tr>
                    <td height="20"   style="background-color: #C0E4FF;border: 1px solid black;"><strong>Submit</strong></td>
                    <td align="left" valign="middle" class="style4"  style="background-color: #C0E4FF;border: 1px solid black;"><div align="left"><span class="style5">Click to Generate the Code and/or Save changes.</span></div></td>
                  </tr>
                  <tr>
                    <td height="20"   style="background-color: #C0E4FF;border: 1px solid black;"><strong>Reset</strong></td>
                    <td align="left" valign="middle" class="style4"  style="background-color: #C0E4FF;border: 1px solid black;">Click  to Reset Form or for another selection.</td>
                  </tr>
                 
</table>

		<p>
			<img src="../${pageContext.request.contextPath}/resources/images/webStockHelpImages/WebStockMaterialIssueSlip.jpg" alt="Mountain View" style="width:94%;height:60%;" />
		</p>
</form>
</body>

</html>
