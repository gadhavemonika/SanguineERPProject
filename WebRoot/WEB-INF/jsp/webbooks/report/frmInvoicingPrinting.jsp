<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Invoicing Printing</title>

<script type="text/javascript">


 $(document).ready(function () {
       
	 var startDate="${startDate}";
		var arr = startDate.split("/");
		Dat=arr[2]+"-"+arr[1]+"-"+arr[0];
		$( "#txtInnFromDate" ).datepicker({ dateFormat: 'yy-mm-dd' });
		$("#txtInnFromDate" ).datepicker('setDate', Dat);
	    /*$("#txtInnFromDate").datepicker({ dateFormat: 'yy-mm-dd' });
		  $("#txtInnFromDate" ).datepicker('setDate', 'today');
		*/
		$("#txtInnFromDate").datepicker();
		
		 $("#txtInnToDate").datepicker({ dateFormat: 'yy-mm-dd' });
			$("#txtInnToDate" ).datepicker('setDate', 'today');
			$("#txtInnToDate").datepicker();	
}); 
	/**
	* Reset The Group Name TextField
	**/
	function funResetFields()
	{
		
    }
	
	function funForSingleInnvoice()
	{
		var strcmbCat = $("#cmbTFCat").val();
		
		var strcmbDetor = $("#cmbTFDebtor").val();
		
		if(strcmbCat=="T/F")
			{
			$("#cmbTFDebtor").val("T/F");
			}
		
		if(strcmbCat=="Total")
		{
		$("#cmbTFDebtor").val("Total");
		}
		
		
		if(strcmbCat=="T/F" && strcmbDetor =="T/F")
			{
				
				
				$("#txtCatToCode").val("");
				$("#txtCatToCode").attr("disabled", "disabled"); 
				
				$("#txtMemberToCode").val("");
				$("#txtMemberToCode").attr("disabled", "disabled"); 
				
				$("#txtInvoiceToCode").val("");
				$("#txtInvoiceToCode").attr("disabled", "disabled"); 
				
			}
		
		if(strcmbCat=="Total" && strcmbDetor =="Total")
			{
				$("#txtCatToCode").removeAttr("disabled"); 
	    		$("#txtMemberToCode").removeAttr("disabled"); 
	    		$("#txtInvoiceToCode").removeAttr("disabled"); 
			}
		
		
	}
	
	
</script>


</head>

<body onload="funOnLoad();">
	<div id="formHeading">
		<label>Invoicing Printing</label>
	</div>
	<s:form name="frmInvoicingPrinting" method="GET" action="rptInvoicePrinting.html?saddr=${urlHits}">

		<br />
		<br />
		<table class="masterTable">

			
			<tr>
				<td width="140px">T/F Category</td>
				<td colspan="5"><s:select id="cmbTFCat" name="cmbTFCat" path="strTFCat" class="BoxW124px" onchange="funForSingleInnvoice()">
											<option value="Total">Total</option>
											<option value="T/F">T/F</option>
										</s:select></td>
			</tr>
			<tr>
			<td><label>Category Form Code</label></td>
									<td><s:input id="txtCatFormCode" type="text" path="strCatFormCode" class="BoxW116px"  /></td>
			
			<td ><label>Category To Code</label></td>
									<td colspan="3"><s:input id="txtCatToCode" path="strCatToCode" type="text" class="BoxW116px" /></td>
			</tr>
			<tr>
				<td width="140px">T/F Debtor</td>
				<td colspan="5"><s:select id="cmbTFDebtor" name="cmbTFDebtor"
											path="strTFDebtor" Class="BoxW124px" onchange="funForSingleInnvoice()" >
											<option value="Total">Total</option>
											<option value="T/F">T/F</option>
										</s:select></td>
			</tr>
			<tr>
			<td><label>Member Form Code</label></td>
									<td><s:input id="txtMemberFormCode" type="text" path="strMemberFormCode" class="BoxW116px" /></td>
			
			<td><label>Member To Code</label></td>
									<td colspan="3"><s:input id="txtMemberToCode" type="text" path="strMemberToCode" class="BoxW116px" /></td>
			</tr>
			<tr>
			<td><label>Invoice Form Code</label></td>
									<td><s:input id="txtInvoiceFormCode" type="text" path="strInvoiceFormCode" class="BoxW116px" /></td>
			
			<td><label>Invoice To Code</label></td>
									<td colspan="3"><s:input id="txtInvoiceToCode" type="text" path="strInvoiceToCode" class="BoxW116px" /></td>
			</tr>
			<tr>
			<td><label>Invoice From Date</label>
									<td><s:input  id="txtInnFromDate"
											 Class="calenderTextBox" path="strInnFromDate" /></td>
											 
			<td><label>Invoice To Date</label>
									<td colspan="3"><s:input  id="txtInnToDate"
											 Class="calenderTextBox" path="strInnToDate" /></td>								 
			
			
			</tr>
			
			<tr>
				<td>Account For</td>
				<td><s:select id="cmbAccountFor" name="cmbAccountFor"
											Class="BoxW124px" path="strAccountFor">
											<option value="Opening Balance">Opening Balance</option>
											<option value="Y">Yes</option>
										</s:select></td>
										
				<td>Report For</td>
				<td><s:select id="cmbReportFor" name="cmbReportFor"
											Class="BoxW124px" path="strReportFor">
											<option value="Debit Account">Debit Account</option>
											<option value="Credit Account">Credit Account</option>
										</s:select></td>
				<td>Operator</td>
				<td><s:select id="cmbOperator" name="cmbOperator"
											Class="BoxW124px"  path="strOperator" >
											<option value=">"> > </option>
											<option value="<"> < </option>
											<option value=">="> >= </option>
											<option value="<="> <= </option>
											<option value="="> = </option>
											<option value="Between"> Between </option>
											<option value=""> <> </option>
										</s:select></td>													
			</tr>
			
			<tr>
			<td><label>Amount Form</label></td>
									<td><s:input id="txtAmountForm" type="text" class="BoxW116px" path="strAmountForm"/></td>
			
			<td><label>Amount To</label></td>
									<td colspan="3"><s:input id="txtAmountTo" type="text" class="BoxW116px" path="strAmountTo"/></td>
			</tr>
			
			<tr>
			<td>MemberShip Expired</td>
				<td><s:select id="cmbMemExp" name="cmbMemExp"
											Class="BoxW124px" path="strMemExp">
											<option value="Including">Including</option>
											<option value="Excluding">Excluding</option>
											<option value="Only">Only</option>
										</s:select></td>
			<td>Debtor List</td>
			<td colspan="3"><s:textarea Style="width:80%" id="txtDebtorList" path="strDebtorList"></s:textarea></td>							
											
			</tr>
			
			<tr>
			<td>Genration</td>
			<td colspan="5">
				<s:input type="radio" name="Genration" value="Invoice Via E-mail" path="strGenration" />Invoice Via E-mail &nbsp;
				<s:input type="radio" name="Genration" value="Invoice Via Hard Copy" path="strGenration" />Invoice Via Hard Copy &nbsp;
				<s:input type="radio" name="Genration" value="Hard Copy for all" path="strGenration"/>Hard Copy for all &nbsp;
				<s:input type="radio" name="Genration" value="Duplex Printing" path="strGenration"/>Duplex Printing &nbsp;
				<s:input type="radio" name="Genration" value="Genrate" path="strGenration"/>Genrate
						
			</td>
			
			
			
			</tr>
		</table>
		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button"
				onclick="return funCallFormAction('submit',this);" /> <input type="reset"
				value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>
	</s:form>

</body>
</html>