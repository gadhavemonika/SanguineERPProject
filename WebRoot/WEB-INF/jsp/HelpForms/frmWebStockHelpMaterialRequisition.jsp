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

<table  >
  <tr>
    <th height="37" align="left" valign="top" scope="col"><div align="center"><span class="style1" style="color: #0066ff">Material Requisiton</span></div></th>
  </tr>
  <tr>
    <td height="30" align="left" valign="top" scope="row"><span class="style4">Material Requisition form is located at Cost Center &gt; Material Requisition.<br />
    Material Requisition form is Used by Cost Center to request material from Stores. Requisition is created against Work Order or Direct..</span></td>
  </tr>
  <tr>
                <td align="center" valign="top" >
                <table border="1" >
                  <tr>
                    
                    <th height="29" align="left"  scope="col"><strong>FIELDS</strong></th>
                    <th width="836"  scope="col"><strong>DESCRIPTION</strong></th>
                  </tr>
                  <tr>
                    <th colspan="3" align="center" valign="top" scope="row">&nbsp;</th>
                  </tr>
                  <tr>
                    
                    <td width="140" height="30"><strong>Requisition Code</strong></td>
                    <td align="left" valign="middle" class="style1"><span class="style4">Requisition Code uniquely identifies each Requisition throughout the system.</span></td>
                  </tr>
                  <tr>
                    
                    <td height="30"><strong>Requisition Date</strong></td>
                    <td align="left" valign="middle" class="style1"><span class="style4">Represents Date of creating Requisition</span></td>
                  </tr>
                  <tr>
                     
                    <td height="30"><strong>Required Date </strong></td>
                    <td align="left" valign="middle" class="style4">Represents Date on which Requisition is Required </td>
                  </tr>
                  <tr>
                     
                    <td height="30"><strong>Location By</strong></td>
                    <td align="left" valign="middle" class="style4">Select From Location. </td>
                  </tr>
                  <tr>
                     
                    <td height="30"><strong>Location To </strong></td>
                    <td align="left" valign="middle" class="style4">Select To Location. </td>
                  </tr>
                  <tr>
                     
                    <td height="30"><strong>Against</strong></td>
                    <td align="left" valign="middle" class="style4">Select Requisition is Direct or Requisition against Work Order. </td>
                  </tr>
                  <tr>
                     
                    <td height="30"><strong>Qty</strong></td>
                    <td align="left" valign="middle" class="style4">Displays Quantity assigned to the Work Order. </td>
                  </tr>
                  <tr>
                     
                    <td height="30"><strong>Fill</strong></td>
                    <td align="left" valign="middle" class="style4">Click to fill materials in table, required to make the Product in Work Order.</td>
                  </tr>
                  <tr>
                     
                    <td height="30"><strong>Product Code</strong></td>
                    <td align="left" valign="middle" class="style4"> Double Click and select new product to be added to table.</td>
                  </tr>
                  <tr>
                     
                    <td height="30"><strong>Stock</strong> </td>
                    <td align="left" valign="middle" class="style4">Displays Stock of the Product on Current Location.</td>
                  </tr>
                  <tr>
                     
                    <td height="30"><strong>Qty</strong></td>
                    <td align="left" valign="middle" class="style4">Enter Quantity of the selected Product.</td>
                  </tr>
                  <tr>
                     
                    <td height="30"><strong>UOM</strong></td>
                    <td align="left" valign="middle" class="style4">Displays Unit of Measure.</td>
                  </tr>
                  <tr>
                     
                    <td height="30"><strong>Unit Price</strong> </td>
                    <td align="left" valign="middle" class="style4">Displays Unit Price. </td>
                  </tr>
                  <tr>
                     
                    <td height="30"><strong>Remark</strong></td>
                    <td align="left" valign="middle" class="style4">Enter Remarks on the selected Product.</td>
                  </tr>
                  <tr>
                     
                    <td height="30"><strong>Add Button</strong> </td>
                    <td align="left" valign="middle"><div align="left"><span class="style4">Click to Add Product.to table.<br />
                    </span></div></td>
                  </tr>
                  <tr>
                     
                    <td height="30" colspan="2"><div align="center"><strong>Table Information </strong></div></td>
                  </tr>
                  <tr>
                     
                    <td height="30"><strong>Product Code</strong></td>
                    <td align="left" valign="middle" class="style4">Displays Product Code.</td>
                  </tr>
                  <tr>
                     
                    <td height="30"><strong>Prod Name </strong></td>
                    <td align="left" valign="middle" class="style4">Displays Product Name.</td>
                  </tr>
                  <tr>
                     
                    <td height="30"><strong>UOM</strong></td>
                    <td align="left" valign="middle" class="style4">Displays UOM.</td>
                  </tr>
                  <tr>
                     
                    <td height="30"><strong>Qty</strong></td>
                    <td align="left" valign="middle" class="style4">Displays Qty of Product. </td>
                  </tr>
                  <tr>
                     
                    <td height="30"><strong>Unit Price </strong></td>
                    <td align="left" valign="middle" class="style4">Displays Unit Price.</td>
                  </tr>
                  <tr>
                     
                    <td height="30"><strong>Total Price</strong> </td>
                    <td align="left" valign="middle" class="style4">Displays Total Price.</td>
                  </tr>
                  <tr>
                     
                    <td height="30"><strong>Remarks</strong></td>
                    <td align="left" valign="middle" class="style4">Displays Remarks.</td>
                  </tr>
                  <tr>
                     
                    <td height="30"><strong>Delete</strong></td>
                    <td align="left" valign="middle" class="style4"><span class="style4">Click to delete an unwanted product from table. </span></td>
                  </tr>
                  <tr>
                     
                    <td height="30"><strong>Total Quantity </strong></td>
                    <td align="left" valign="middle" class="style4">Displays total quantity of products. </td>
                  </tr>
                  <tr>
                     
                    <td height="30"><strong>Total Amount </strong></td>
                    <td align="left" valign="middle" class="style4">Displays total price of products. </td>
                  </tr>
                  <tr>
                     
                    <td height="30"><strong>Close Requisition </strong></td>
                    <td align="left" valign="middle" class="style4">Check box to short close requisition. </td>
                  </tr>
                  <tr>
                     
                    <td height="30"><strong>Narration</strong></td>
                    <td align="left" valign="middle" class="style4"><span class="style5">Enter more information if any.</span></td>
                  </tr>
                  <tr>
                     
                    <td height="30"><strong>Submit</strong></td>
                    <td align="left" valign="middle" class="style4"><div align="left"><span class="style5">Click to Generate the Code and/or Save changes.</span></div></td>
                  </tr>
                  <tr>
                     
                    <td height="30"><strong>Reset</strong></td>
                    <td align="left" valign="middle" class="style4"><div align="left"><span class="style5">Click  to Reset Form or for another selection. </span></div></td>
                  </tr>
                </table>
                </table>

		<p>
			<img src="../${pageContext.request.contextPath}/resources/images/webStockHelpImages/WebStockMaterialRequisition.jpg" alt="Mountain View" style="width:94%;height:60%;" />
		</p>
</form>
</body>

</html>
