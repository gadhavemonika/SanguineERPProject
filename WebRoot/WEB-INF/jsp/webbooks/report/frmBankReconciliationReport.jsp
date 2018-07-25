<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@	taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<script type="text/javascript">
	$(function() {
		$(document).ajaxStart(function() {
			$("#wait").css("display", "block");
		});
		$(document).ajaxComplete(function() {
			$("#wait").css("display", "none");
		});
		/*$( "#txtFromDate" ).datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtFromDate" ).datepicker('setDate', 'today');*/
		var startDate="${startDate}";
		var arr = startDate.split("/");
		Dat=arr[0]+"-"+arr[1]+"-"+arr[2];
		$( "#txtFromDate" ).datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtFromDate" ).datepicker('setDate', Dat);
		$( "#txtToDate" ).datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtToDate" ).datepicker('setDate', 'today');
		

	});
	
	function funHelp(transactionName)
	{
		fieldName=transactionName;
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
	
	function funSetData(code)
	{
		switch(fieldName)
		{
		case 'cashBankAccNo' : 
		funSetBankAccountDetails(code);
		break;
		}
	}
	
	// Function to set Bank Account Details
	function funSetBankAccountDetails(code){

		$.ajax({
			type : "GET",
			url : getContextPath()+ "/loadAccontCodeAndName.html?accountCode=" + code,
			dataType : "json",
			success : function(response){ 
				if(response.strAccountCode=='Invalid Code')
	        	{
	        		alert("Invalid Account Code");
	        		$("#txtBankCode").val('');
	        	}
	        	else
	        	{
	        		$("#txtBankCode").val(response.strAccountCode);
		        	$("#lblBankDesc").text(response.strAccountName);
		        	$("#txtBankDesc").val(response.strAccountName);
		        	
		        	funSetBankBalanceAmt(response.strType);
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
	
	function funSetBankBalanceAmt(accType){
		var accCode=$("#txtBankCode").val();
		var invDate=$("#txtToDate").val();
		var currencyCode="";
		
			var gurl1=getContextPath()+"/loadBankBalanceAmt.html?accType="+accType+"&accCode="+accCode+"&toDate="+invDate+"&currency="+currencyCode;
			$.ajax({
			    type: "GET",
			    url: gurl1,
			    dataType: "json",
			    success: function(response)
			    {	
			    	var amount = (response).toFixed(maxAmountDecimalPlaceLimit);
			    	if(amount<0)
			    	{
			    		amount="("+amount*-1+")";
			    	}
			    	
			    	$("#txtBalAmt").val(amount);
				},
				error: function(jqXHR, exception) {
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
</script>
<body>
	<div id="formHeading">
		<label>Bank Reconciliation Report</label>
	</div>

	<br />
	<br />

	<s:form name="frmBankReconciliationReport" method="GET" action="openRptBankReconciliationReport.html" target="_blank">
		<div>
			<table class="transTable">
			    <tr>
					<td width="10%"><label>From Date </label></td>
					<td width="10%" colspan="1"><s:input id="txtFromDate" path="dteFromDate" required="true" readonly="readonly" cssClass="calenderTextBox"/></td>
					<td width="10%"><label>To Date </label></td>
					<td width="10%"><s:input id="txtToDate" path="dteToDate" required="true" readonly="readonly" cssClass="calenderTextBox"/>
					</td>
					<td></td>	
				</tr>
				<tr>
<!-- 					<td><label>Currency </label></td> -->
<%-- 					<td><s:select id="cmbCurrency" items="${currencyList}" path="strCurrency" cssClass="BoxW124px"> --%>
<%-- 						</s:select></td> --%>
				<td><label>Bank Code</label></td>
				<td><s:input type="text" id="txtBankCode" path="strBankCode" cssClass="searchTextBox" ondblclick="funHelp('cashBankAccNo');"/>
				</td>				
				<td><label id="lblBankDesc"></label></td>
				<td><s:input type="hidden" id="txtBankDesc" path="strBankName"/></td>				
				<td><s:input type="hidden" id="txtBalAmt" path="dblBalAmount"/></td>				
					
					
				</tr>
			</table>
		</div>
		<p align="center">
				<input type="submit" value="Submit"  class="form_button" />
				 <input type="button" value="Reset" class="form_button"  onclick="funResetFields()"/>
			</p>
	</s:form>

</body>
</html>