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
	
	function funSetData(code){

		switch(fieldName)
		{		
			case "accountCodeBank": 
			     funSetAccountDetails(code);
				 break;
			
		}
	}
	
	function funSetAccountDetails(accountCode)
	{
	    $("#txtAccountCode").val(accountCode);
		var searchurl=getContextPath()+"/loadAccountMasterData.html?accountCode="+accountCode;
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	if(response.strAccountCode=='Invalid Code')
			        	{
			        		alert("Invalid Account Code");
			        		$("#txtAccountCode").val('');
			        	}
			        	else
			        	{
				        	$("#txtAccountName").val(response.strAccountName);
				        			        	
			        	}
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
	
	
	
	function funHelp(transactionName)
	{
		fieldName=transactionName;
	//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
	
	
	function funGetVaildation() 
	 {
			if($("#txtAccountCode").val()=="")
	 		{
	 			alert('Please Select Bank Name!! ');
	 			return false;
	 		}
			
			if($("#txtAccountName").val()=="")
	 		{
	 			alert('Bank Name should not be empty!! ');
	 			return false;
	 		}
	  }
</script>
<body>
	<div id="formHeading">
		<label>Bank Book</label>
	</div>

	<br />
	<br />

	<s:form name="Bank Book Report" method="GET" action="rptBankBook.html" target="_blank">
		<div>
			<table class="transTable">
			<tr>
					<td width="10%"><label>Bank Account </label></td>
					 <td><s:input id="txtAccountCode" path="strAccountCode"  ondblclick="funHelp('accountCodeBank')" cssClass="searchTextBox"/></td>			        			        
			    	<td colspan="2"><s:input id="txtAccountName" path="strAccountName"  readonly="true" cssClass="longTextBox"/></td>
					
			</tr>		
					
			    <tr>
					<td width="10%"><label>From Date </label></td>
					<td width="10%" colspan="1"><s:input id="txtFromDate" path="dteFromDate" required="true" readonly="readonly" cssClass="calenderTextBox"/></td>
					<td width="10%"><label>To Date </label></td>
					<td width="10%"><s:input id="txtToDate" path="dteToDate" required="true" readonly="readonly" cssClass="calenderTextBox"/>
					</td>	
				</tr>
				 <tr>   
				    <td><label>Currency </label></td>
					<td><s:select id="cmbCurrency" items="${currencyList}" path="strCurrency" cssClass="BoxW124px">
						</s:select></td>
					<td colspan="2"></td>
				</tr>	
			</table>
		</div>
		<p align="center">
				<input type="submit" value="Submit"  class="form_button" onclick="return funGetVaildation()" />
				 <input type="button" value="Reset" class="form_button"  onclick="funResetFields()"/>
			</p>
	</s:form>

</body>
</html>