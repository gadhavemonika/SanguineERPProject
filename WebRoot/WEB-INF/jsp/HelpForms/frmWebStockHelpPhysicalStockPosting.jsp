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


<table border="1" >
  <tr>
    <td height="20" colspan="2"><div align="center" class="style7">Physical Stock Posting</div></td>
  </tr>
  <tr>
    <td height="20" colspan="2"><div align="left" class="style4">Physical Stock Posting  form is located at Store &gt; Physical Stock Posting.<br />
Physical Stock Posting  form is used to check variance between Current Stock and Required Stock on a location. </div></td>
  </tr>
  <tr>
    <td height="28"><div align="left" class="style8">FIELDS</div></td>
    <td width="1117" align="left" valign="middle" class="style4"><div align="center" class="style8">DESCRIPTION</div></td>
  </tr>
  <tr>
    <td width="152" align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">
      <div align="left"><strong>Stock Posting  Code</strong></div>
    </div></td>
    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">
      <div align="left">Stock Adjustment Code uniquely identifies each stock posting entry throughout the system. </div>
    </div></td>
  </tr>
  <tr>
    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">
      <div align="left"><strong>Stock Posting Date</strong></div>
    </div></td>
    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">
      <div align="left">Represents date of creating Physical Stock entry</div>
    </div></td>
  </tr>
                  
                  <tr>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4"><strong>Conversion UOM </strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">Select Conversion UOM </div></td>
                  </tr>
                  <tr>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">
                        <div align="left"><strong>Location</strong></div>
                    </div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">
                        <div align="left">Select Location. </div>
                    </div></td>
                  </tr>
                  <tr>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">
                        <div align="left"><strong>Product Code</strong></div>
                    </div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">
                        <div align="left">Double Click and select new product to be added to table.</div>
                    </div></td>
                  </tr>
                  <tr>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">
                        <div align="left"><strong>Product Name </strong></div>
                    </div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">
                        <div align="left">Displays Product Name.</div>
                    </div></td>
                  </tr>
                  <tr>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">
                        <div align="left"><strong>Unit Price </strong></div>
                    </div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">
                        <div align="left">Enter Unit Price </div>
                    </div></td>
                  </tr>
                  <tr>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">
                        <div align="left"><strong>Stock </strong></div>
                    </div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">
                        <div align="left">Displays Stock of the selected Product</div>
                    </div></td>
                  </tr>
                  <tr>
                    <td height="26" align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4"><strong>Wt/Unit</strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">Enter Wt/Unit of the selected Product.</div></td>
                  </tr>
                  <tr>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">
                        <div align="left"><strong>Quantity</strong></div>
                    </div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">
                        <div align="left">Enter Quantity of the selected Product.</div>
                    </div></td>
                  </tr>
                  <tr>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">
                        <div align="left"><strong>Add Button </strong></div>
                    </div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">
                        <div align="left">Click to Add Product.to table.</div>
                    </div></td>
                  </tr>
                  

                  <tr>
                    <td height="20" colspan="2"><div align="center" class="style8">Table Information </div></td>
                  </tr>
                  
                  <tr>
                    <td bordercolor="#EBEBEB"><div align="left" class="style4"><strong>Product Code</strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">
                      <div align="left">Displays Product Code.</div>
                    </div></td>
                  </tr>
                  
                  <tr>
                    <td bordercolor="#EBEBEB"><div align="left" class="style4"><strong>Product Name </strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">
                      <div align="left">Displays Product Name.</div>
                    </div></td>
                  </tr>
                  
                  <tr>
                    <td bordercolor="#EBEBEB"><div align="left"><strong class="style4">Unit Price</strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left">Displays Unit Price</div></td>
                  </tr>
                  <tr>
                    <td bordercolor="#EBEBEB"><div align="left" class="style4"><strong>Unit Price  </strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">
                      <div align="left">Displays Unit Price </div>
                    </div></td>
                  </tr>
                  
                  <tr>
                    <td bordercolor="#EBEBEB"><div align="left" class="style4"><strong>Wt/Unit</strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">
                      <div align="left">Displays Wt/Unit of the Product. </div>
                    </div></td>
                  </tr>
                  
                  <tr>
                    <td bordercolor="#EBEBEB"><div align="left" class="style4"><strong>Current Stock</strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">
                      <div align="left">Displays Current Stock.</div>
                    </div></td>
                  </tr>
                  
                  
                  <tr>
                    <td bordercolor="#EBEBEB"><div align="left" class="style4"><strong>Qty</strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">
                      <div align="left">Displays Quantity of the Product. </div>
                    </div></td>
                  </tr>
                  
                  <tr>
                    <td bordercolor="#EBEBEB"><div align="left" class="style4"><strong>Loose Qty</strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">
                      <div align="left">Displays Loose Qty </div>
                    </div></td>
                  </tr>
                  
                  <tr>
                    <td bordercolor="#EBEBEB"><div align="left" class="style4"><strong>Variance</strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left">Displays variance between previous and current stock.</div></td>
                  </tr>
                  <tr>
                    <td bordercolor="#EBEBEB"><div align="left" class="style4"><strong>Adjusted Wt</strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left">Displays Adjusted Wt </div></td>
                  </tr>
                  <tr>
                    <td bordercolor="#EBEBEB"><div align="left" class="style4"><strong>Adjusted Value</strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left">Displays Adjusted Value</div></td>
                  </tr>
                  <tr>
                    <td bordercolor="#EBEBEB"><div align="left" class="style4"><strong>Delete</strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">
                      <div align="left">Click to delete an unwanted product from table. </div>
                    </div></td>
                  </tr>
                  
                  <tr>
                    <td bordercolor="#EBEBEB"><div align="left" class="style4"><strong>Total Amount </strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left">Displays total amount to be adjusted </div></td>
                  </tr>
                  <tr>
                    <td bordercolor="#EBEBEB"><div align="left" class="style4"><strong>Narration</strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left">Enter more information if any.</div></td>
                  </tr>
                  <tr>
                    <td bordercolor="#EBEBEB"><div align="left" class="style4"><strong>Submit</strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left"><span class="style4">Click to Generate the Code and/or Save changes.</span></div></td>
                  </tr>
                  <tr>
                    <td bordercolor="#EBEBEB"><div align="left" class="style4"><strong>Reset</strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">
                      <div align="left">Click  to Reset Form or for another selection. </div>
                    </div></td>
                  </tr>
                
</table>

		

		<p>
			<img src="../${pageContext.request.contextPath}/resources/images/webStockHelpImages/WebStockPhysicalStockPosting.jpg" alt="Mountain View" style="width:94%;height:60%;" />
		</p>
</form>
</body>

</html>
