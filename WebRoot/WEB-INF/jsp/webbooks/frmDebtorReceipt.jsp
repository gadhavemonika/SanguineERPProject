<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@	taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<script type="text/javascript">
	
	var fieldName;
	var debtorName='';
	
	$(function() 
	{
		$("#txtVouchDate").datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtVouchDate").datepicker('setDate', 'today');
		$("#txtChequeDate").datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtChequeDate").datepicker('setDate', 'today');
		
		var message='';
		var retval="";
		<%if (session.getAttribute("success") != null) 
		{
			if(session.getAttribute("successMessage") != null)
			{%>
				message='<%=session.getAttribute("successMessage").toString()%>';
			    <%
			    session.removeAttribute("successMessage");
			}
			boolean test = ((Boolean) session.getAttribute("success")).booleanValue();
			session.removeAttribute("success");
			if (test) 
			{
				%> alert("Data Save successfully\n\n"+message); <%
			}
		}%>
	});


	function funSetData(code){

		switch(fieldName){

			case 'ReceiptNo' : 
				funSetVouchNo(code);
				break;
				
			case 'bankMaster' : 
				funSetBankDetails(code);
				break;
				
			case 'ReceiptNoslip' : 
				funSetVouchNoSlip(code);
				break;
		}
	}


	function funSetVouchNo(code){

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadReceipt.html?docCode=" + code,
			dataType : "json",
			success : function(response){ 
				if(response.strVouchNo!="Invalid")
		    	{
		    		funFillHdData(response);
		    	}
		    	else
			    {
			    	alert("Invalid Receipt No");
			    	$("#txtVouchNo").val("");
			    	$("#txtVouchNo").focus();
			    	return false;
			    }
			},
			error : function(e){
				if (jqXHR.status === 0) {
	                alert('Not connect.n Verify Network.');
	            } else if (jqXHR.status == 404) {
	                alert('Requested page not found. [404]');
	            } else if (jqXHR.status == 500) {
	                alert('Internal Server Error [500].');
	            } else if (exception === 'parsererror') {
	                alert('Requested JSON parse failed.');
	            } else if (exception === 'timeout') {
	                alert('Time out error.');
	            } else if (exception === 'abort') {
	                alert('Ajax request aborted.');
	            } else {
	                alert('Uncaught Error.n' + jqXHR.responseText);
	            }
			}
		});
	}

	
// Fill Header Data	
	function funFillHdData(response)
	{
		$("#txtVouchNo").val(response.strVouchNo);
		$("#txtVouchDate").val(response.dteVouchDate);
		$("#txtChequeDate").val(response.dteChequeDate);
		$("#txtCFCode").val(response.strCFCode);
    	$("#cmbType").val(response.strType);
    	funSetTypeLabel();
    	$("#txtChequeNo").val(response.strChequeNo);
    	$("#txtAmt").val(response.dblAmt);
    	$("#txtDrawnOn").val(response.strDrawnOn);
    	$("#lblDrawnOnDesc").text(response.strBankName);
    	$("#txtBranch").val(response.strBranch);
    	$("#txtReceivedFrom").val(response.strReceivedFrom);
    	$("#txtNarration").val(response.strNarration);
		//funFillDtlGrid(response.listReceiptBeanDtl);
	}
	
	
// Fill Account details and Debtor Details Grid	
	function funFillDtlGrid(resListRecDtlBean)
	{
		funRemoveProductRows();
		$.each(resListRecDtlBean, function(i,item)
        {
			debtorYN=resListRecDtlBean[i].strDebtorYN;
			debtorName=resListRecDtlBean[i].strDebtorName;
			funAddDetailsRow(resListRecDtlBean[i].strAccountCode,resListRecDtlBean[i].strDebtorCode
				,resListRecDtlBean[i].strDescription,resListRecDtlBean[i].strDC,''
				,resListRecDtlBean[i].dblDebitAmt,resListRecDtlBean[i].dblCreditAmt);
        });
	}
	
	
//Delete a All record from a grid
	function funRemoveProductRows()
	{
		var table = document.getElementById("tblReceiptDetails");
		var rowCount = table.rows.length;
		while(rowCount>0)
		{
			table.deleteRow(0);
			rowCount--;
		}
	}
	
	
	
// Function to set CF Code from help	
	function funSetCFCode(code){

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadAccontCodeAndName.html?accountCode=" + code,
			dataType : "json",
			success : function(response){ 
				if(response.strAccountCode=='Invalid Code')
	        	{
	        		alert("Invalid Account Code");
	        		$("#txtCFCode").val('');
	        	}
	        	else
	        	{
	        		$("#txtCFCode").val(response.strAccountCode);
		        	$("#lblCFDesc").text(response.strAccountName);
		        	$("#txtVouchDate").focus();
	        	}
			},
			error : function(e){
				if (jqXHR.status === 0) {
	                alert('Not connect.n Verify Network.');
	            } else if (jqXHR.status == 404) {
	                alert('Requested page not found. [404]');
	            } else if (jqXHR.status == 500) {
	                alert('Internal Server Error [500].');
	            } else if (exception === 'parsererror') {
	                alert('Requested JSON parse failed.');
	            } else if (exception === 'timeout') {
	                alert('Time out error.');
	            } else if (exception === 'abort') {
	                alert('Ajax request aborted.');
	            } else {
	                alert('Uncaught Error.n' + jqXHR.responseText);
	            }
			}
		});
	}


// Function to set account details from help	
	function funSetAccountDetails(code){
		
		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadAccontCodeAndName.html?accountCode=" + code,
			dataType : "json",
			success : function(response){ 
				if(response.strAccountCode=='Invalid Code')
	        	{
	        		alert("Invalid Account Code");
	        		$("#txtAccCode").val('');
	        	}
	        	else
	        	{
	        		$("#txtAccCode").val(response.strAccountCode);
		        	$("#txtDescription").val(response.strAccountName);
		        	if(response.strDebtor=='Yes')
		        	{
		        		$("#txtDebtorCode").prop('disabled', false);
		        	}
		        	else
		        	{
		        		$("#txtDebtorCode").val('');
		        		$("#txtDebtorCode").prop('disabled', true);
		        	}
		        	$("#txtDebtorCode").focus();
	        	}
			},
			error : function(e){
				if (jqXHR.status === 0) {
	                alert('Not connect.n Verify Network.');
	            } else if (jqXHR.status == 404) {
	                alert('Requested page not found. [404]');
	            } else if (jqXHR.status == 500) {
	                alert('Internal Server Error [500].');
	            } else if (exception === 'parsererror') {
	                alert('Requested JSON parse failed.');
	            } else if (exception === 'timeout') {
	                alert('Time out error.');
	            } else if (exception === 'abort') {
	                alert('Ajax request aborted.');
	            } else {
	                alert('Uncaught Error.n' + jqXHR.responseText);
	            }
			}
		});
	}


// Function to set debtor details from help	
	function funSetMemberDetails(code){
		
		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadMemberDetails.html?debtorCode=" + code,
			dataType : "json",
			success : function(response){ 
				if(response.strAccountCode=='Invalid Code')
	        	{
	        		alert("Invalid Debtor Code");
	        		$("#txtDebtorCode").val('');
	        	}
	        	else
	        	{
	        		$("#txtDebtorCode").val(response.strDebtorCode);
	        		debtorName=response.strDebtorName;
	        	}
			},
			error : function(e){
				if (jqXHR.status === 0) {
	                alert('Not connect.n Verify Network.');
	            } else if (jqXHR.status == 404) {
	                alert('Requested page not found. [404]');
	            } else if (jqXHR.status == 500) {
	                alert('Internal Server Error [500].');
	            } else if (exception === 'parsererror') {
	                alert('Requested JSON parse failed.');
	            } else if (exception === 'timeout') {
	                alert('Time out error.');
	            } else if (exception === 'abort') {
	                alert('Ajax request aborted.');
	            } else {
	                alert('Uncaught Error.n' + jqXHR.responseText);
	            }
			}
		});
	}


// Function to set Bank details from help	
	function funSetBankDetails(code){
	
		$.ajax({
			type : "GET",
			url : getContextPath()+"/loadBankMasterData.html?bankCode="+code,
			dataType : "json",
			success : function(response){ 
				if(response.strAccountCode=='Invalid Code')
	        	{
	        		alert("Invalid Bank Code");
	        		$("#txtDrawnOn").val('');
	        	}
	        	else
	        	{
	        		$("#txtDrawnOn").val(response.strBankCode);
	        		$("#lblDrawnOnDesc").text(response.strBankName);
	        		$("#txtBranch").focus();
	        	}
			},
			error : function(e){
				if (jqXHR.status === 0) {
	                alert('Not connect.n Verify Network.');
	            } else if (jqXHR.status == 404) {
	                alert('Requested page not found. [404]');
	            } else if (jqXHR.status == 500) {
	                alert('Internal Server Error [500].');
	            } else if (exception === 'parsererror') {
	                alert('Requested JSON parse failed.');
	            } else if (exception === 'timeout') {
	                alert('Time out error.');
	            } else if (exception === 'abort') {
	                alert('Ajax request aborted.');
	            } else {
	                alert('Uncaught Error.n' + jqXHR.responseText);
	            }
			}
		});
	}


// Get Detail Info From detail fields and pass them to function to add into detail grid
	function funGetDetailsRow() 
	{
		var accountCode =$("#txtAccCode").val().trim();
		var transAmt=parseFloat($("#txtAmount").val());
		
		if(accountCode=='')
		{
			alert('Select Account Code!!!');
			return;
		}
		if(transAmt<1)
		{
			alert('Enter Transaction Amt!!!');
			return;
		}
		
	    var debtorCode=$("#txtDebtorCode").val();
	    var description=$("#txtDescription").val();
	    var transType=$("#cmbDrCr").val();
	    var dimension=$("#cmbDimesion").val();
	    var debitAmt=0;
	    var creditAmt=0;
	    
	    if(transType=='Dr')
	    {
	    	debitAmt=parseFloat(transAmt).toFixed(maxQuantityDecimalPlaceLimit);	
	    }
	    else
	    {
	    	creditAmt=parseFloat(transAmt).toFixed(maxQuantityDecimalPlaceLimit);
	    }
	    
	    funAddDetailsRow(accountCode,debtorCode,description,transType,dimension,debitAmt,creditAmt);
	    
	}
	
	
// Function to add detail grid rows	
	function funAddDetailsRow(accountCode,debtorCode,description,transType,dimension,debitAmt,creditAmt) 
	{
	    var table = document.getElementById("tblReceiptDetails");
	    var rowCount = table.rows.length;
	    var row = table.insertRow(rowCount);
	    
	    row.insertCell(0).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"12%\" name=\"listReceiptBeanDtl["+(rowCount)+"].strAccountCode\" id=\"strAccountCode."+(rowCount)+"\" value='"+accountCode+"' />";
	    row.insertCell(1).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"12%\" name=\"listReceiptBeanDtl["+(rowCount)+"].strDebtorCode\" id=\"strDebtorCode."+(rowCount)+"\" value='"+debtorCode+"' />";
	    row.insertCell(2).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"20%\" name=\"listReceiptBeanDtl["+(rowCount)+"].strDescription\" id=\"strDescription."+(rowCount)+"\" value='"+description+"' />";
	    row.insertCell(3).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"5%\" name=\"listReceiptBeanDtl["+(rowCount)+"].strDC\" id=\"strDC."+(rowCount)+"\" value='"+transType+"' />";
	    row.insertCell(4).innerHTML= "<input readonly=\"readonly\" class=\"Box debitAmt\" size=\"7%\" name=\"listReceiptBeanDtl["+(rowCount)+"].dblDebitAmt\" id=\"dblDebitAmt."+(rowCount)+"\" value='"+debitAmt+"'/>";
	    row.insertCell(5).innerHTML= "<input readonly=\"readonly\" class=\"Box creditAmt\" size=\"7%\" name=\"listReceiptBeanDtl["+(rowCount)+"].dblCreditAmt\" id=\"dblCreditAmt."+(rowCount)+"\" value='"+creditAmt+"'/>";
	    row.insertCell(6).innerHTML= "<input readonly=\"readonly\" class=\"Box\" size=\"8%\" name=\"listReceiptBeanDtl["+(rowCount)+"].strDimension\" id=\"strDimension."+(rowCount)+"\" value='"+dimension+"'/>";	        
	    row.insertCell(7).innerHTML= "<input type=\"button\" class=\"deletebutton\" size=\"2%\" value = \"Delete\" onClick=\"Javacsript:funDeleteRow(this)\"/>";
	    row.insertCell(8).innerHTML= "<input type=\"hidden\" readonly=\"readonly\" size=\"1%\" name=\"listReceiptBeanDtl["+(rowCount)+"].strDebtorName\" id=\"strDebtorName."+(rowCount)+"\" value='"+debtorName+"' />";
	    
	    debtorName='';
	    $("#txtDebtorCode").prop('disabled', true);
	    funCalculateTotalAmt();
	    funResetDetailFields();
	}


//Delete a All record from a grid
	function funRemoveProductRows()
	{
		var table = document.getElementById("tblReceiptDetails");
		var rowCount = table.rows.length;
		while(rowCount>0)
		{
			table.deleteRow(0);
			rowCount--;
		}
	}
	

//Function to Delete Selected Row From Grid
	function funDeleteRow(obj)
	{
	    var index = obj.parentNode.parentNode.rowIndex;
	    var table = document.getElementById("tblReceiptDetails");
	    table.deleteRow(index);
	    funCalculateTotalAmt();
	}

	
//Calculating total amount
	function funCalculateTotalAmt()
	{
		var totalAmt=0.00;
		var totalDebitAmt=0.00;
		var totalCreditAmt=0.00;
		
		$('#tblReceiptDetails tr').each(function() 
		{
			var debitAmt=parseFloat($(this).find(".debitAmt").val());
		    totalDebitAmt = totalDebitAmt + parseFloat($(this).find(".debitAmt").val());
		    totalCreditAmt = totalCreditAmt + parseFloat($(this).find(".creditAmt").val());
		});

		totalDebitAmt=parseFloat(totalDebitAmt).toFixed(maxAmountDecimalPlaceLimit);
		totalCreditAmt=parseFloat(totalCreditAmt).toFixed(maxAmountDecimalPlaceLimit);
		
		totalAmt=totalDebitAmt-totalCreditAmt;
		totalAmt=parseFloat(totalAmt).toFixed(maxAmountDecimalPlaceLimit);
		
		$("#lblDebitAmt").text(totalDebitAmt);
		$("#lblCreditAmt").text(totalCreditAmt);
		$("#lblDiffAmt").text(totalAmt);
		$("#txtTotalAmt").val(totalAmt);
	}


// Reset Detail Fields
	function funResetDetailFields()
	{
		$("#txtAccCode").val('');
	    $("#txtDebtorCode").val('');
	    $("#txtDescription").val('');
	    $("#cmbDrCr").val('Dr');
	    $("#txtAmount").val('0.00');
	    $("#cmbDimesion").val('No');	    
	}
	
	
// Reset Header Fields
	function funResetHeaderFields()
	{
		$("#txtVouchNo").val('');		
		$("#txtNarration").val('');
		$("#txtVouchDate").datepicker('setDate', 'today');
		$("#txtChequeDate").datepicker('setDate', 'today');
		$("#txtDebtorCode").prop('disabled', true);
	}


	
// Function invoked on change value event on select tag.	
	function funSetTypeLabel()
	{
		var type=$("#cmbType").val();
		switch(type)
		{
			case "Cash":
				$("#lblTypeName").text("Cash");
				break;
				
			case "Credit Card":
				$("#lblTypeName").text("Credit Card");
				break;

			case "Cheque":
				$("#lblTypeName").text("Cheque No");
				break;
	
			case "NEFT":
				$("#lblTypeName").text("NEFT No");
				break;
		}
	}
	
	
// Set Debtor Account Description 
	function funSetDebtorAccDesc()
	{
		var debtorAccDesc=$("#cmbDebtorAcc").val();
		$("#txtDebtorAccDesc").val(debtorAccDesc);
	}


// Function to Validate Header Fields
	function funValidateHeaderFields()
	{
		if($("#txtVouchDate").val()=='')
		{
			alert('Please Select Vouch Date!!!');
			return false;
		}
		if($("#txtCFCode").val()=='')
		{
			alert('Please Enter CF Code!!!');
			return false;
		}
		
		var amt=parseFloat($("#txtAmt").val());
		if(amt<1)
		{
			alert('Please Enter Amount!!!');
			return false;
		}
	}


	function funHelp(transactionName)
	{
		fieldName=transactionName;
	//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
	
</script>

</head>
<body>

	<div id="formHeading">
	<label>Receipt</label>
	</div>

	<br/>
	<br/>

	<s:form name="Receipt" method="POST" action="saveDebtorReceipt.html">

		<table class="masterTable">
			<tr>
				<td>
					<label>Receipt No</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtVouchNo" path="strVouchNo" cssClass="searchTextBox" ondblclick="funHelp('ReceiptNo');"/>
				</td>
				<td></td>
				<td>
					<label>Receipt Date</label>
				</td>
				<td>
					<s:input colspan="3" type="text" id="txtVouchDate" path="dteVouchDate" cssClass="calenderTextBox" />
				</td>
			</tr>
			
			<tr>
				<td>
					<label>Cash/Bank Code</label>
				</td>
				<td>
					<s:select id="cmbCFCode" path="strCFCode" class="BoxW124px" items="${CashBankAccounts}">
					</s:select>
				</td>
								
				<td>
					<label>Type</label>
				</td>
				
				<td colspan="2">
					<s:select id="cmbType" path="strType" class="BoxW124px" onchange="funSetTypeLabel()">
						<option value="Cash">Cash</option>
					  	<option value="Cheque">Cheque</option>
					  	<option value="Credit Card">Credit Card</option>
					  	<option value="NEFT">NEFT</option>
					</s:select>
				</td>
			</tr>

			<tr>
				<td>
					<label>Debtor Account</label>
				</td>
				<td>
					<s:select id="cmbDebtorAcc" path="strDebtorAccCode" class="BoxW124px" items="${DebtorAccounts}" onchange="funSetDebtorAccDesc()">
					</s:select>
				</td>
				
				<td colspan="3"><s:input id="txtDebtorAccDesc" type="hidden" path="strDebtorAccDesc"/></td>
			</tr>
			
			<tr>
				<td>
					<label>Received From</label>
				</td>
				<td colspan="4">
					<s:input   type="text" id="txtReceivedFrom" path="strReceivedFrom" cssClass="remarkTextBox" />
				</td>
			</tr>
			
			<tr>
				<td><label>Drawn On</label></td>
				<td colspan="3">
					<s:input  type="text" id="txtDrawnOn" path="strDrawnOn" cssClass="searchTextBox" ondblclick="funHelp('bankMaster');"/>
				</td>
				<td><label id="lblDrawnOnDesc"></label></td>
			</tr>			
			
			<tr>
				<td>
					<label>Branch</label>
				</td>
				<td colspan="4">
					<s:input  type="text" id="txtBranch" path="strBranch" cssClass="longTextBox" />
				</td>
			</tr>
			
			<tr>
				<td>
					<label id="lblTypeName">Cash</label>
				</td>
				<td colspan="2">
					<s:input   type="text" id="txtChequeNo" path="strChequeNo" cssClass="remarkTextBox" />
				</td>
				<td>
					<label>Cheque Date</label>
				</td>
				<td >
					<s:input  type="text" id="txtChequeDate" path="dteChequeDate" cssClass="calenderTextBox" />
				</td>
			</tr>

			<tr>
				<td>
					<label>Amt</label>
				</td>
				<td colspan="4">
					<s:input  type="number" step="0.01" id="txtAmt" path="dblAmt" class="decimal-places numberField" value="0.00" />
				</td>
			</tr>
						
			<tr>
				<td><label>Narration</label></td>
				<td colspan="4">
					<s:textarea id="txtNarration" path="strNarration" />
				</td>
			</tr>
		</table>

		<br>
		<br>
		
		<br/>
		<br/>
		<p align="center">
			<input type="submit" value="Submit" onclick="return funValidateHeaderFields()" tabindex="3" class="form_button" />
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>

	</s:form>
</body>
</html>
