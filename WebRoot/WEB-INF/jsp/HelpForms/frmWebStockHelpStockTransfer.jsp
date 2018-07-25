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
    <td height="20" colspan="2"><div align="center" class="style7">Stock Transfer  </div></td>
  </tr>
  <tr>
    <td height="20" colspan="2"><div align="left"><span class="style4">Stock Transfer  form is located at Cost Center &gt; Stock Transfer.<br />
    Stock Transfer  form is used by Store to transfer material   from one location to other location.</span></div></td>
  </tr>
  <tr>
    <td height="28"><div align="left" class="style8">FIELDS</div></td>
    <td align="left" valign="middle" class="style4"><div align="center" class="style8">DESCRIPTION</div></td>
  </tr>
  <tr>
    <td height="20" class="style4"><div align="left"><span class="style4"><strong>ST Code</strong></span></div></td>
    <td align="left" valign="middle" class="style4"><div align="left">ST Code uniquely identifies each Stock Transfer throughout the system </div></td>
  </tr>
  <tr>
    <td width="158" height="20" class="style4"><div align="left"><span class="style4"><strong>No</strong></span></div></td>
    <td width="1003" align="left" valign="middle" class="style4"><div align="left">Enter Manual Referrence No </div></td>
  </tr>
                  
                  <tr>
                    <td height="20" class="style9"><div align="left">Date </div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Represents Date of creating Stock Transfer </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><span class="style4"><strong>From</strong></span></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Selects From Location. </div></td>
                  </tr>
                  
                  <tr>
                    <td height="20" class="style4"><div align="left"><span class="style4"><strong>To </strong></span></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Selects To Location. </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><span class="style4"><strong>Type</strong></span></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Select Product or Assembly as applicable . </div></td>
                  </tr>
                  
                  <tr>
                    <td height="20" class="style4"><div align="left"><span class="style4"><strong>Product Code</strong></span></div></td>
                    <td align="left" valign="middle" class="style4"> <div align="left">Double Click and select new product to be added to table.</div></td>
                  </tr>
                  
                  <tr>
                    <td height="20" class="style4"><div align="left"><span class="style4"><strong>Product Name </strong></span></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays Product Name of selected product</div></td>
                  </tr>
                  
                  <tr>
                    <td height="20" class="style9"><div align="left">Unit Price </div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Enter Unit Price </div></td>
                  </tr>
                  
                  <tr>
                    <td height="20" class="style4"><div align="left"><span class="style4"><strong>Stock</strong> </span></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays Stock of the Product on Current Location.</div></td>
                  </tr>
                  
                  <tr>
                  
                    <td height="20" class="style4"><div align="left"><span class="style4"><strong>Quantity</strong></span></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Enter Quantity of the selected Product.</div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><span class="style4"><strong>Wt/Unit</strong> </span></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Enter Wt/Unit </div></td>
                  </tr>
                  
                  <tr>
                    <td height="20" class="style4"><div align="left"><span class="style4"><strong>Remarks</strong></span></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Enter Remarks on the selected Product.</div></td>
                  </tr>
                  
                  <tr>
                    <td height="20" class="style4"><div align="left"><span class="style4"><strong>Add Button</strong> </span></div></td>
                    <td align="left" valign="middle"><div align="left"><span class="style4">Click to Add Product to table.<br />
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
                    <td height="20" bordercolor="#F0F0F0" class="style4"><div align="left"><span class="style4"><strong>Product Name </strong></span></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays Product Name.</div></td>
                  </tr>
                  
                  <tr bordercolor="#EBEBEB">
                    <td height="30" bordercolor="#F0F0F0" class="style4"><div align="left" class="style4"><strong>Product Type </strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left">Displays Product Type </div></td>
                  </tr>
                  
                  <tr>
                    <td height="20"><div align="left"><strong class="style4">UOM</strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays Unit of Measure</div></td>
                  </tr>
                  
                  <tr>
                    <td bordercolor="#EBEBEB" class="style4"><div align="left" class="style4"><strong>Quantity</strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays Quantity. </div></td>
                  </tr>
                  
                  
                  <tr>
                    <td bordercolor="#EBEBEB" class="style4"><div align="left" class="style4"><strong>Unit Price </strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays Unit Price.</div></td>
                  </tr>
                  
                  <tr>
                    <td bordercolor="#EBEBEB" class="style4"><div align="left" class="style4"><strong>Total Price </strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays Total Price</div></td>
                  </tr>
                  
                  <tr>
                    <td bordercolor="#EBEBEB" class="style4"><div align="left" class="style4"><strong>Wt/Unit</strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays Wt/Unit </div></td>
                  </tr>
                  
                  <tr>
                    <td bordercolor="#EBEBEB" class="style4"><div align="left" class="style4"><strong>Total Wt </strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays Total Wt </div></td>
                  </tr>
                  
                  <tr>
                    <td bordercolor="#EBEBEB" class="style4"><div align="left" class="style4"><strong>Remarks</strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays Remarks.</div></td>
                  </tr>
                  
                  <tr>
                    <td bordercolor="#EBEBEB" class="style4"><div align="left" class="style4"><strong>Delete</strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Click to delete an unwanted product from table.</div></td>
                  </tr>
                  
                  <tr>
                    <td bordercolor="#EBEBEB" class="style4"><div align="left" class="style4"><strong>Narration</strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left"><span class="style5">Enter more information if any.</span></div></td>
                  </tr>
                  <tr>
                    <td bordercolor="#EBEBEB" class="style4"><div align="left" class="style4"><strong>Submit</strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left"><span class="style5">Click to Generate the Code and/or Save changes.</span></div></td>
                  </tr>
                  <tr>
                    <td bordercolor="#EBEBEB" class="style4"><div align="left" class="style4"><strong>Reset</strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left"><span class="style5">Click  to Reset Form or for another selection.</span></div></td>
                  </tr>
                  <tr>
                    <td height="20" colspan="2" align="left" valign="top"><img src="Screen Shots/StockTransfer.jpg" alt="" width="1277" height="835" /></td>
                  </tr>
</table>

		

		<p>
			<img src="../${pageContext.request.contextPath}/resources/images/webStockHelpImages/WebStockStockTransfer.jpg" alt="Mountain View" style="width:94%;height:60%;" />
		</p>
</form>
</body>

</html>