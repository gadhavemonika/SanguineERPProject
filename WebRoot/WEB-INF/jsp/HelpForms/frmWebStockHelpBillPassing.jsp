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
				<th height="37" align="left" valign="top" scope="col"><div
						align="center">
						<span class="style1" style="color: #0066ff">Bill Passing</span>
					</div></th>
			</tr>
			<tr>
				<td height="30" align="left" valign="top" scope="row"><span
					class="style4">Bill Passing is located at Accounts &gt; Bill
						Passing .<br /> Bill Passing form is Used by Accounts to pass
						bills to finance department to pay bills to Supplier.
				</span>
				</div></td>
			</tr><tr>
                <td align="center" valign="top" >
			<table border="1">
				<tr>

					<th height="29" align="left" scope="col"><strong>FIELDS</strong></th>
					<th width="836" scope="col"><strong>DESCRIPTION</strong></th>
				</tr>
				<tr>
					<th colspan="3" align="center" valign="top" scope="row">&nbsp;</th>
				</tr>
				<tr>
				<tr>
					<td height="20" class="style4"><div align="left"
							class="style4">
							<strong>Bill Passing No </strong>
						</div></td>
					<td align="left" valign="middle" class="style4"><div
							align="left" class="style4">Bill Passing No uniquely
							identifies each Bill Passing throughout the system</div></td>
				</tr>
				<tr>
					<td width="158" height="20" class="style4"><div align="left"
							class="style4">
							<strong> Date </strong>
						</div></td>
					<td width="1003" align="left" valign="middle" class="style4"><div
							align="left" class="style4">Represents Date of making bill
							passing entry</div></td>
				</tr>
				<tr bordercolor="#EBEBEB">
					<td height="30" class="style4"><div align="left"
							class="style4">
							<strong>Supplier</strong>
						</div></td>
					<td align="left" valign="middle" class="style4"><div
							align="left" class="style4">Select Supplier</div></td>
				</tr>
				<tr>
					<td height="20" class="style4"><div align="left"
							class="style4">
							<strong>Supplier Voucher No </strong>
						</div></td>
					<td align="left" valign="middle" class="style4"><div
							align="left">Enter Supplier Voucher No</div></td>
				</tr>

				<tr>
					<td height="20" class="style4"><div align="left"
							class="style4">
							<strong>Bill Amount </strong>
						</div></td>
					<td align="left" valign="middle" class="style4"><div
							align="left">Enter Bill Amount</div></td>
				</tr>
				<tr>
					<td height="20" class="style4"><div align="left"
							class="style4">
							<strong>Passing Date </strong>
						</div></td>
					<td align="left" valign="middle" class="style4"><div
							align="left">Enter Passing Date</div></td>
				</tr>
				<tr>
					<td bordercolor="#EBEBEB" class="style4"><div align="left"
							class="style4">
							<strong>Narration</strong>
						</div></td>
					<td align="left" valign="middle" bordercolor="#EBEBEB"
						class="style4"><div align="left">Enter Narration</div></td>
				</tr>

				<tr>
					<td bordercolor="#EBEBEB" class="style4"><div align="left"
							class="style4">
							<strong>Currency</strong>
						</div></td>
					<td align="left" valign="middle" bordercolor="#EBEBEB"
						class="style4"><div align="left">Select Currency</div></td>
				</tr>

				<tr>
					<td bordercolor="#EBEBEB" class="style4"><div align="left"
							class="style4">
							<strong>GRN Code </strong>
						</div></td>
					<td align="left" valign="middle" bordercolor="#EBEBEB"
						class="style4"><div align="left">Select GRN</div></td>
				</tr>

				<tr>
					<td bordercolor="#EBEBEB" class="style4"><div align="left"
							class="style4">
							<strong>Date</strong>
						</div></td>
					<td align="left" valign="middle" bordercolor="#EBEBEB"
						class="style4"><div align="left">Displays GRN Date</div></td>
				</tr>

				<tr>
					<td bordercolor="#EBEBEB" class="style4"><div align="left"
							class="style4">
							<strong>Challan No </strong>
						</div></td>
					<td align="left" valign="middle" bordercolor="#EBEBEB"
						class="style4"><div align="left">Displays GRN Challan
							No</div></td>
				</tr>

				<tr>
					<td bordercolor="#EBEBEB" class="style4"><div align="left"
							class="style4">
							<strong>Value</strong>
						</div></td>

					<td align="left" valign="middle" bordercolor="#EBEBEB"
						class="style4"><div align="left">Displays Value</div></td>
				</tr>
				<tr>
					<td bordercolor="#EBEBEB" class="style4"><div align="left">
							<strong>Adjustment</strong>
						</div></td>
					<td align="left" valign="middle" bordercolor="#EBEBEB"
						class="style4"><div align="left">Enter Adjustment</div></td>
				</tr>

				<tr>

					<td height="20" colspan="2"><div align="center">
							<strong>Table Information </strong>
						</div></td>
				</tr>

				<tr>
					<td height="20" class="style4"><div align="left"
							class="style4">
							<strong>Code</strong>
						</div></td>
					<td align="left" valign="middle" class="style4"><div
							align="left" class="style4">Displays GRN Code</div></td>
				</tr>

				<tr>
					<td height="20" class="style4"><div align="left"
							class="style4">
							<strong>Date</strong>
						</div></td>
					<td align="left" valign="middle" class="style4"><div
							align="left" class="style4">Displays GRN Date</div></td>
				</tr>

				<tr>
					<td height="20" class="style4"><div align="left"
							class="style4">
							<strong>Challan No</strong>
						</div></td>
					<td align="left" valign="middle" class="style4"><div
							align="left" class="style4">Displays GRN Challan No</div></td>
				</tr>

				<tr>
					<td height="20" class="style4"><div align="left"
							class="style4">
							<strong>Adjustment</strong>
						</div></td>
					<td align="left" valign="middle" class="style4"><div
							align="left" class="style4">Displays Adjustment</div></td>
				</tr>

				<tr>
					<td height="20" class="style4"><div align="left"
							class="style4">
							<strong>Value</strong>
						</div></td>
					<td align="left" valign="middle" class="style4"><div
							align="left" class="style4">Displays Value</div></td>
				</tr>

				<tr>
					<td height="20" class="style4"><div align="left"
							class="style4">
							<strong>Delete</strong>
						</div></td>
					<td align="left" valign="middle" class="style4"><div
							align="left" class="style4">Click to delete an unwanted
							entry from table.</div></td>
				</tr>

				<tr>
					<td height="20" class="style4"><div align="left"
							class="style4">
							<strong>Sub Total </strong>
						</div></td>
					<td align="left" valign="middle" class="style4"><div
							align="left" class="style4">Displays Sub Total</div></td>
				</tr>
				<tr>
					<td height="20" class="style4"><div align="left"
							class="style4">
							<strong>Tax</strong>
						</div></td>
					<td align="left" valign="middle" class="style4"><div
							align="left" class="style4">Displays Tax</div></td>
				</tr>
				<tr>
					<td height="30" bordercolor="#EBEBEB" class="style4"><div
							align="left" class="style4">
							<strong>Grand Total </strong>
						</div></td>
					<td align="left" valign="middle" class="style4"><div
							align="left" class="style4">Displays Grand Total</div></td>
				</tr>
				<tr>
					<td height="20" class="style4"><div align="left"
							class="style4">
							<strong>Submit</strong>
						</div></td>
					<td align="left" valign="middle" class="style4"><div
							align="left" class="style4">Click to Generate the Code
							and/or Save changes.</div></td>
				</tr>
				<tr>
					<td height="20" class="style4"><div align="left"
							class="style4">
							<strong>Reset</strong>
						</div></td>
					<td align="left" valign="middle" class="style4"><div
							align="left" class="style4">Click to Reset Form or for
							another selection.</div></td>
				</tr>
				<tr>
					<td height="20" class="style4">&nbsp;</td>
					<td align="left" valign="middle" class="style9">TAX</td>
				</tr>
				<tr>
					<td height="20" class="style9"><div align="left"
							class="style4">
							<strong>Tax Code </strong>
						</div></td>
					<td align="left" valign="middle" class="style4"><div
							align="left">Select Tax</div></td>
				</tr>
				<tr>
					<td height="20" class="style9"><div align="left"
							class="style4">
							<strong>Description </strong>
						</div></td>
					<td align="left" valign="middle" class="style4"><div
							align="left">Displays Tax Description</div></td>
				</tr>
				<tr>
					<td height="20" class="style9"><div align="left">
							<strong>Taxable Amount </strong>
						</div></td>
					<td align="left" valign="middle" class="style4"><div
							align="left">Enter Taxable Amount</div></td>
				</tr>
				<tr>
					<td height="20" class="style9"><div align="left">
							<strong>Tax Amount</strong>
						</div></td>
					<td align="left" valign="middle" class="style4"><div
							align="left">Enter Tax Amount</div></td>
				</tr>
				<tr>
					<td height="20" class="style9"><div align="left">
							<strong>Add Button </strong>
						</div></td>
					<td align="left" valign="middle" class="style4"><div
							align="left">Click to Add Entry to table.</div></td>
				</tr>
				<tr>
					<td height="20" class="style9"><div align="left">
							<strong>Delete</strong>
						</div></td>
					<td align="left" valign="middle" class="style4"><div
							align="left">Click to delete an unwanted entry from table.
						</div></td>
				</tr>
				<tr>
					<td height="20" class="style9"><div align="left">
							<strong>Taxable Amt Total</strong>
						</div></td>
					<td align="left" valign="middle" class="style4"><div
							align="left">Displays Taxable Amount</div></td>
				</tr>
				<tr>
					<td height="20" class="style9"><div align="left">
							<strong>Tax</strong>
						</div></td>
					<td align="left" valign="middle" class="style4"><div
							align="left">Displays Tax</div></td>
				</tr>
				<tr>
					<td height="20" class="style9"><div align="left">
							<strong>Grand Total</strong>
						</div></td>
					<td align="left" valign="middle" class="style4"><div
							align="left">Displays Grand Total</div></td>
				</tr>
				<tr>
					<td height="20" class="style4"><div align="left"
							class="style4">
							<strong>Submit</strong>
						</div></td>
					<td align="left" valign="middle" class="style4"><div
							align="left" class="style4">Click to Generate the Code
							and/or Save changes.</div></td>
				</tr>
				<tr>
					<td height="20" class="style4"><div align="left"
							class="style4">
							<strong>Reset</strong>
						</div></td>
					<td align="left" valign="middle" class="style4"><div
							align="left" class="style4">Click to Reset Form or for
							another selection.</div></td>
				</tr>

				<!--                   <tr> -->
				<!--                     <td height="20" colspan="2" align="left" valign="top"><p><img src="Screen Shots/BillPassingGeneral.jpg" alt="" width="1279" height="675" /></p> -->
				<!--                     <p><img src="Screen Shots/BillPassingTax.jpg" alt="" width="1275" height="686" /></p></td> -->
				<!--                   </tr> -->

			</table>
			</td>
			</tr>
		</table>

		<p><img src="../${pageContext.request.contextPath}/resources/images/webStockHelpImages/WebStockBillPassingGeneral.jpg" alt="Mountain View" style="width:94%;height:60%;" />
			<img src="../${pageContext.request.contextPath}/resources/images/webStockHelpImages/WebStockBillPassingTax.jpg" alt="Mountain View" style="width:94%;height:60%;" />
		</p>

</form>
</body>

</html>
