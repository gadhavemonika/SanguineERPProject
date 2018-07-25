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


</style></head>

<body>

<form action="" style="background-color: #C0E4FF;border: 1px solid black;">

<table border="1" >
  <tr>
    <td height="20" colspan="2"><div align="center" class="style7">Stock Adjustment </div></td>
  </tr>
  <tr>
    <td height="20" colspan="2" align="left" valign="middle"><div align="left" class="style4">Stock Adjustment   form is located at Store &gt; Stock Adjustment.<br />
Stock Adjustment form is used to tally system stock with physical stock on a location.</div></td>
  </tr>
  <tr>
    <td height="28"><div align="left" class="style8">FIELDS</div></td>
    <td width="1117" align="left" valign="middle" class="style4"><div align="center" class="style8">DESCRIPTION</div></td>
  </tr>
  <tr>
    <td width="152" align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">
      <div align="left"><strong>SA  Code</strong></div>
    </div></td>
    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">
      <div align="left">Stock Adjustment Code uniquely identifies each stock posting entry throughout the system. </div>
    </div></td>
  </tr>
  <tr>
    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">
      <div align="left"><strong>SA Date</strong></div>
    </div></td>
    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">
      <div align="left">Represents date of creating Stock Adjustment entry </div>
    </div></td>
  </tr>
                  
                  <tr>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4"><strong>Location</strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">Select Location. </div></td>
                  </tr>
                  <tr>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">
                        <div align="left"><strong>Reason</strong></div>
                    </div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">
                        <div align="left">Select Reason </div>
                    </div></td>
                  </tr>
                  <tr>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">
                        <div align="left"><strong>Conversion UOM </strong></div>
                    </div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">
                        <div align="left">Select Conversion UOM </div>
                    </div></td>
                  </tr>
                  <tr>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left"><strong>Product Code</strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left">Double Click and select new product to be added to table.</div></td>
                  </tr>
                  <tr>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left"><strong>Product Name </strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left">Displays Product Name.</div></td>
                  </tr>
                  <tr>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left"><strong>Stock </strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left">Displays Stock of the selected Product</div></td>
                  </tr>
                  <tr>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left"><strong>Quantity</strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left">Enter Quantity of the selected Product.</div></td>
                  </tr>
                  <tr>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left"><strong>Unit Price</strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left">Enter Unit Price </div></td>
                  </tr>
                  <tr>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left"><strong>Wt/Unit</strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left">Enter Wt/Unit of the selected Product.</div></td>
                  </tr>
                  <tr>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left"><strong>Type</strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left">Select Type </div></td>
                  </tr>
                  <tr>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left"><strong>Remark</strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left">Enter Remarks</div></td>
                  </tr>
                  <tr>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">
                        <div align="left"><strong>Add Button </strong></div>
                    </div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">
                        <div align="left">Click to Add Product to table.</div>
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
                    <td bordercolor="#EBEBEB" class="style4"><div align="left" class="style4"><strong>Product Type</strong> </div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left">Displays Product Type </div></td>
                  </tr>
                  <tr>
                    <td bordercolor="#EBEBEB" class="style4"><div align="left" class="style4"><strong>UOM</strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left">Displays UOM </div></td>
                  </tr>
                  <tr>
                    <td bordercolor="#EBEBEB" class="style4"><div align="left"><span class="style4"><strong>Quantity</strong></span></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left">Displays Quantity </div></td>
                  </tr>
                  <tr>
                    <td bordercolor="#EBEBEB"><div align="left" class="style4"><strong>Loose Qty</strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">
                        <div align="left">Displays Loose Qty </div>
                    </div></td>
                  </tr>
                  <tr>
                    <td bordercolor="#EBEBEB"><div align="left" class="style4"><strong>Unit Price  </strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">
                      <div align="left">Displays Unit Price.</div>
                    </div></td>
                  </tr>
                  
                  <tr>
                    <td bordercolor="#EBEBEB"><div align="left" class="style4"><strong>Total Price </strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left">Displays Total Price </div></td>
                  </tr>
                  <tr>
                    <td bordercolor="#EBEBEB"><div align="left" class="style4"><strong>Wt/Unit</strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">
                      <div align="left">Displays Wt/Unit of the Product.</div>
                    </div></td>
                  </tr>
                  
                  <tr>
                    <td bordercolor="#EBEBEB"><div align="left" class="style4"><strong>Total Wt </strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">
                      <div align="left">Displays Total Weight </div>
                    </div></td>
                  </tr>
                  
                  
                  <tr>
                    <td bordercolor="#EBEBEB"><div align="left" class="style4"><strong>Type</strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">
                      <div align="left">Displays Type. </div>
                    </div></td>
                  </tr>
                  <tr>
                    <td bordercolor="#EBEBEB"><div align="left" class="style4"><strong>Remark</strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left" class="style4">
                      <div align="left">Displays Remark </div>
                    </div></td>
                  </tr>
                  
                  <tr>
                    <td bordercolor="#EBEBEB"><div align="left"><strong class="style4">Delete</strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left">Click to delete a product from table. </div></td>
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
                  <tr>
                    <td height="20" colspan="2" align="left" valign="top"><img src="Screen Shots/StockAdjustment.jpg" alt="" width="1277" height="799" /></td>
                  </tr>
</table>

		

		<p>
			<img src="../${pageContext.request.contextPath}/resources/images/webStockHelpImages/WebStockStockAdjustment.jpg" alt="Mountain View" style="width:94%;height:60%;" />
		</p>
</form>
</body>

</html>