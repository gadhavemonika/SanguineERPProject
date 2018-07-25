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

/*  body,td,th { */
/* 	font-family: Arial, Helvetica, sans-serif;  */
/*  	font-size: 13px;  */
/*  	color: #333333;  */
/*  }  */
/*  body {  */
/*  	margin-left: 0px;  */
/*  	margin-top: 15px;  */
/*  	margin-right: 0px;  */
/*  	margin-bottom: 0px;  */
/*  }  */
/*  .style1 {  */
/*  	font-size: 16px  */
/*  }  */
/*  .style4 {font-size: 14px}  */
/*  .style5 {font-size: 14}  */
/*  .style7 {  */
/*  	color: #0000FF;  */
/*  	font-weight: bold;  */
/*  	font-size: 16px;  */
/*  }  */
/*  .style8 {  */
/*  	font-size: 15px;  */
/*  	font-weight: bold;  */
/*  }  */

</style></head>

<body>

<form action="" style="background-color: #C0E4FF;border: 1px solid black;">


<table border="1" >
  <tr>
    <td height="20" colspan="2"><div align="center" class="style7">Material Return </div></td>
  </tr>
  <tr>
    <td height="20" colspan="2"><div align="left"><span class="style4">Material Return form is located at Cost Center &gt; Material Return.<br />
Material Return form is Used by Cost Center to Return Material to a location. Material Return is created Direct or against MIS. </span></div></td>
  </tr>
  <tr>
    <td height="28"><div align="left" class="style8">FIELDS</div></td>
    <td width="1149" align="left" valign="middle" class="style4"><div align="center" class="style8">DESCRIPTION</div></td>
  </tr>
  <tr>
    <td width="120" bordercolor="#EBEBEB"><div align="left"><strong>MR Code</strong></div></td>
    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style1"><div align="left"><span class="style4">MR Code  uniquely identifies each Material Return entry throughout the system.</span></div></td>
  </tr>
  <tr>
    <td bordercolor="#EBEBEB"><div align="left"><strong>MR Date</strong></div></td>
    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style1"><div align="left"><span class="style4">Represents Date of creating Material Return </span></div></td>
  </tr>
                  
                  <tr>
                    <td bordercolor="#EBEBEB"><div align="left"><strong>Location By</strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left">Select From Location. </div></td>
                  </tr>
                  
                  <tr>
                    <td bordercolor="#EBEBEB"><div align="left"><strong>Location To </strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left">Select To Location. </div></td>
                  </tr>
                  <tr>
                    <td bordercolor="#EBEBEB"><div align="left"><strong>Against</strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left">Select Material Return is Direct or against MIS </div></td>
                  </tr>
                  
                  <tr>
                    <td bordercolor="#EBEBEB"><div align="left"><strong>Product Code</strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left">Double Click and select new product to be added to table.</div></td>
                  </tr>
                  
                  <tr>
                    <td bordercolor="#EBEBEB"><div align="left"><strong>Product Name</strong> </div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left">Displays Product Name.</div></td>
                  </tr>
                  
                  <tr>
                    <td bordercolor="#EBEBEB"><div align="left"><strong>POS Item Code </strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left">Displays POS Item Code </div></td>
                  </tr>
                  
                  <tr>
                    <td bordercolor="#EBEBEB"><div align="left"><strong>Stock</strong> </div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left">Displays Stock of the Product on Current Location.</div></td>
                  </tr>
                  
                  <tr>
                    <td bordercolor="#EBEBEB"><div align="left"><strong>Qty</strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left">Enter Quantity of the selected Product.</div></td>
                  </tr>
                  
                  <tr>
                    <td bordercolor="#EBEBEB"><div align="left"><strong>Remarks</strong></div></td>
                  
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left">Enter Remarks on the selected Product.</div></td>
                  </tr>
                  <tr>
                    <td bordercolor="#EBEBEB"><div align="left"><strong>Add Button</strong> </div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB"><div align="left"><span class="style4">Click to Add Product.to table.<br />
                    </span></div></td>
                  </tr>
                  
                  <tr>
                    <td height="20" colspan="2"><div align="center" class="style8">Table Information </div></td>
                  </tr>
                  
                  <tr>
                    <td bordercolor="#EBEBEB"><div align="left"><strong>Product Code</strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left">Displays Product Code.</div></td>
                  </tr>
                  
                  <tr>
                    <td bordercolor="#EBEBEB"><div align="left"><strong>Product Name </strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left">Displays Product Name.</div></td>
                  </tr>
                  
                  <tr>
                    <td bordercolor="#EBEBEB"><div align="left"><strong>POS Item Code </strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left">Displays POS Item Code </div></td>
                  </tr>
                  
                  <tr>
                    <td bordercolor="#EBEBEB"><div align="left"><strong>Quantity</strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left">Displays Quantity of the Product. </div></td>
                  </tr>
                  
                  <tr>
                    <td bordercolor="#EBEBEB"><div align="left"><strong>Remarks</strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left">Displays Remarks.</div></td>
                  </tr>
                  
                  
                  <tr>
                    <td bordercolor="#EBEBEB"><div align="left"><strong>Delete</strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left">Click to delete an unwanted product from table. </div></td>
                  </tr>
                  
                  <tr>
                    <td bordercolor="#EBEBEB"><div align="left"><strong>Narration</strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left"><span class="style5">Enter more information if any.</span></div></td>
                  </tr>
                  
                  <tr>
                    <td bordercolor="#EBEBEB"><div align="left"><strong>Submit</strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left"><span class="style5">Click to Generate the Code and/or Save changes.</span></div></td>
                  </tr>
                  
                  <tr>
                    <td bordercolor="#EBEBEB"><div align="left"><strong>Reset</strong></div></td>
                    <td align="left" valign="middle" bordercolor="#EBEBEB" class="style4"><div align="left"><span class="style5">Click  to Reset Form or for another selection. </span></div></td>
                  </tr>
                  <tr>
                    <td height="20" colspan="2" align="left" valign="top"><img src="Screen Shots/MaterialReturn.jpg" alt="" width="1275" height="699" /></td>
                  </tr>
</table>
		

		<p>
			<img src="../${pageContext.request.contextPath}/resources/images/webStockHelpImages/WebStockMaterialReturn.jpg" alt="Mountain View" style="width:94%;height:60%;" />
		</p>
</form>
</body>

</html>
