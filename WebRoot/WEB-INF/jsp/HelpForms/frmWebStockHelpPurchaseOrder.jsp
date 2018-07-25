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

<table>
  <tr>
    <td height="20" colspan="2"><div align="center" class="style7">Purchase Order </div></td>
  </tr>
  <tr>
    <td height="20" colspan="2"><div align="left"><span class="style4">Purchase Order form is located at Purchase &gt; Purchase Order.<br />
    Purchase Order form is used by Purchase Department to buy products from Supplier. </span><span class="style4">Purchase Order can be Direct or against Purchase Indent. </span></div></td>
  </tr>
  <tr>
    <td height="28"><div align="left" class="style8">FIELDS</div></td>
    <td align="left" valign="middle" class="style4"><div align="center" class="style8">      
      <div align="left">GENERAL</div>
    </div></td>
  </tr>
  <tr>
    <td height="20" class="style4"><div align="left"><strong>PO Code</strong> </div></td>
    <td align="left" valign="middle" class="style4"><div align="left">PO Code uniquely identifies each PO throughout the system </div></td>
  </tr>
  <tr>
    <td height="20" class="style4"><div align="left"><strong>Our's Ref No</strong> </div></td>
    <td align="left" valign="middle" class="style4"><div align="left">Enter Manual Reference No. </div></td>
  </tr>
  <tr>
    <td width="172" height="20" class="style4"><div align="left"><strong>PO Date</strong> </div></td>
    <td width="1097" align="left" valign="middle" class="style1"><div align="left"><span class="style4">Represents Date of creating PO. </span></div></td>
  </tr>
                  
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Supplier</strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Select Supplier. </div></td>
                  </tr>
                  
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Delivery Date </strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Select Delivery Date. </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Against</strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Select PO is direct or against Purchase Indent. </div></td>
                  </tr>
                  
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Fill</strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Click to fill PI items in table</div></td>
                  </tr>
                  
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Payment Due Date </strong></div></td>
                    <td align="left" valign="middle" class="style4"> <div align="left">Select Payment Due Date </div></td>
                  </tr>
                  
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Pay Mode </strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Select Payment Mode </div></td>
                  </tr>
                  
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Currency</strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Select Currency </div></td>
                  </tr>
                  
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>PI Code </strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Select Purchase Indent Code </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Product</strong> </div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Double click to Select Product</div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Unit Price</strong> </div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays Unit Price. </div></td>
                  </tr>
                  
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Wt/Unit</strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Enter Wt/Unit </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Quantity</strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Enter Quantity </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>UOM</strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Enter UOM </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Highlight</strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Select Yes/No</div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Discount</strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Enter Discount </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Remarks</strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Enter Remarks on the selected Product.</div></td>
                  </tr>
                  
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Add Button</strong> </div></td>
                    <td align="left" valign="middle"><div align="left"><span class="style4">Click to Add Product.to table.<br />
                    </span></div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Your Ref </strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Enter Supplier Ref No </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Permission Ref. in LUT </strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Enter Permission Ref. in LUT </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>PO Items Title </strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Enter PO Items Title </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>PO Footer </strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Click to select  PO Footer </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Short Close PO </strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Select to Short Close PO </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Delivery Schedule </strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Click to select Delivery Schedule </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Sub Total </strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays Sub Total </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Discount % </strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Enter Discount %</div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Discount</strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays Discount Amount </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Extra Charges </strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Enter Extra Charges amount </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Final Amount </strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays Final Amount </div></td>
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
                    <td height="20" colspan="2"><div align="center" class="style8">Table Information </div></td>
                  </tr>
                  
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Product Code</strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays Product Code.</div></td>
                  </tr>
                  
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Prod Name </strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays Product Name.</div></td>
                  </tr>
                  
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>UOM</strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays UOM.</div></td>
                  </tr>
                  
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Supplier Code </strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays Supplier Code </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>S Code </strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Click to add another Supplier </div></td>
                  </tr>
                  
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Supplier Name </strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays Supplier Name </div></td>
                  </tr>
                  
                  
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Order Qty</strong> </div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays Order Qty </div></td>
                  </tr>
                  
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Wt/Unit</strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays Wt/Unit </div></td>
                  </tr>
                  
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Total Wt </strong> </div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays Total Wt </div></td>
                  </tr>
                  
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Price</strong> </div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays Price </div></td>
                  </tr>
                  
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Discount</strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays Discount.</div></td>
                  </tr>
                  
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Amount </strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays Amount </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Remarks</strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays Remarks </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>PI Code</strong> </div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays PI Code </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Update</strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">DIsplays Y/N as per Highlight field </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Delete</strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Click to delete a Product from table </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Submit</strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Click to Generate the Code and/or Save changes.</div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Reset</strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Click  to Reset Form or for another selection.</div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style8">&nbsp;</td>
                    <td align="left" valign="middle" class="style9"><div align="left" class="style8">
                      <div align="left">ADDRESS</div>
                    </div></td>
                  </tr>
                  <tr>
                    <td height="20"><div align="left"><strong class="style4">BILL TO/SHIP TO </strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left"></div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Address Line 1 </strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Enter Address Line 1 </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Address Line 2 </strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Enter Address Line 2 </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>City</strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Enter City Name </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>State</strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Enter State Name </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Country</strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Enter Country Name </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Pin Code </strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Enter Pin Code </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style9"><div align="left">Submit</div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Click to Generate the Code and/or Save changes.</div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Reset</strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Click  to Reset Form or for another selection.</div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style8">&nbsp;</td>
                    <td align="left" valign="middle" class="style9"><div align="left" class="style8">TERM &amp; CONDITIONS </div></td>
                  </tr>
                  <tr>
                    <td height="20"><div align="left"><strong class="style4">TC Code </strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Select Term &amp; Condition </div></td>
                  </tr>
                  <tr>
                    <td height="20"><div align="left"><strong class="style4">TC Description </strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Enter TC Description </div></td>
                  </tr>
                  <tr>
                    <td height="20"><div align="left"><strong class="style4">Add Button </strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Click to add Product to table </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style8">&nbsp;</td>
                    <td align="left" valign="middle" class="style4"><div align="left"><span class="style8"><span class="style9">Term &amp; Condition Table </span></span></div></td>
                  </tr>
                  <tr>
                    <td height="20"><div align="left"><strong class="style4">TC Name  </strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays added  Term &amp; Condition </div></td>
                  </tr>
                  <tr>
                    <td height="20"><div align="left"><strong class="style4">TC Description  </strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays TC Description</div></td>
                  </tr>
                  <tr>
                    <td height="20"><div align="left"><strong class="style4">Delete</strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Click to delete a TC.</div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style8">&nbsp;</td>
                    <td align="left" valign="middle" class="style9"><div align="left" class="style8">TAXES</div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Calculate Tax </strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Click to Calculate Tax </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Tax Code </strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Select Tax</div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Tax Description </strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays Tax Name </div></td>
                  </tr>
                  <tr>
                    <td height="30" class="style4"><div align="left"><strong>Taxable Amount</strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Enter Taxable Amount </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Tax Amount</strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Enter Tax Amount </div></td>
                  </tr>
                  <tr>
                    <td height="20"><div align="left"><strong class="style4">Add Button </strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Click to add tax to tax table. </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style8">&nbsp;</td>
                    <td align="left" valign="middle" class="style4"><div align="left"><span class="style9">Tax Table</span></div></td>
                  </tr>
                  <tr>
                    <td height="20"><div align="left"><strong class="style4">Tax Code </strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays Tax Code </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong class="style4">Description</strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays Tax Description </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong class="style4">Taxable Amount </strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays Taxable Amount </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong class="style4">Tax Amount </strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays Tax Amount </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong class="style4">Delete</strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Click to delete a product from table.</div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong class="style4">Taxable Amt Total </strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays total of Taxable Amount </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong class="style4">Tax</strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays total fo Tax Amount </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong class="style4">Grand Total </strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Displays Grand Total of Taxable Amount and Tax Amount. </div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style9"><div align="left">Submit</div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Click to Generate the Code and/or Save changes.</div></td>
                  </tr>
                  <tr>
                    <td height="20" class="style4"><div align="left"><strong>Reset</strong></div></td>
                    <td align="left" valign="middle" class="style4"><div align="left">Click  to Reset Form or for another selection.</div></td>
                  </tr>
                  
                  <tr>
                    <td height="20" colspan="2" align="left" valign="top"><p><img src="Screen Shots/PurchaseOrderGeneral.jpg" alt="" width="1278" height="886" /></p>
                    <p><img src="Screen Shots/PurchaseOrderAddress.jpg" alt="" width="1279" height="888" /></p>
                    <p><img src="Screen Shots/PurchaseOrderTerm.jpg" alt="" width="1272" height="888" /></p>
                    <p><img src="Screen Shots/PurchaseOrderTaxes.jpg" alt="" width="1280" height="890" /></p></td>
                  </tr>
</table>


		

		<p>
			<img src="../${pageContext.request.contextPath}/resources/images/webStockHelpImages/WebStockPurchaseOrderGeneral.jpg" alt="Mountain View" style="width:94%;height:60%;" />
			<br>
			<img src="../${pageContext.request.contextPath}/resources/images/webStockHelpImages/WebStockPurchaseOrderTaxes.jpg" alt="Mountain View" style="width:94%;height:60%;" />
			<br>
			<img src="../${pageContext.request.contextPath}/resources/images/webStockHelpImages/WebStockPurchaseOrderAddress.jpg" alt="Mountain View" style="width:94%;height:60%;" />
			<br>
			<img src="../${pageContext.request.contextPath}/resources/images/webStockHelpImages/WebStockPurchaseOrderTerm.jpg" alt="Mountain View" style="width:94%;height:60%;" />
		
		</p>
</form>
</body>

</html>