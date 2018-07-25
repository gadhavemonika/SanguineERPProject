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
		$("#txtFromDate" ).datepicker('setDate', 'today');
		*/
		var startDate="${startDate}";
		var arr = startDate.split("/");
		Dat=arr[0]+"-"+arr[1]+"-"+arr[2];
		$( "#txtFromDate" ).datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtFromDate" ).datepicker('setDate', Dat);
		$( "#txtToDate" ).datepicker({ dateFormat: 'dd-mm-yy' });
		$("#txtToDate" ).datepicker('setDate', 'today');
		var glCode = $("#txtGLCode").val();
		if(glCode!='')
		{
			funSetGLCode(glCode);
		}

	});
	
	function funHelp(transactionName)
	{
		fieldName=transactionName;
	//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
	
		function funSetData(code){

		switch(fieldName){

			case 'creditorAccountCode' : 
				funSetGLCode(code);
				break;
				
		
		}
	}
		
		function funSetGLCode(code){

			$.ajax({
				type : "GET",
				url : getContextPath()+ "/loadAccontCodeAndName.html?accountCode=" + code,
				dataType : "json",
				success : function(response){ 
					if(response.strVouchNo!="Invalid")
			    	{
						$("#txtGLCode").val(response.strAccountCode);
						$("#lblGLCode").text(response.strAccountName);
						$("#txtFromDebtorCode").focus();					
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
	
// 	function funSetData(code)
// 	{
	   
// 		var searchurl=getContextPath()+"/loadSundryCreditorMasterData.html?creditorCode="+code;
// 		 $.ajax({
// 			        type: "GET",
// 			        url: searchurl,
// 			        dataType: "json",
// 			        success: function(response)
// 			        {
// 			        	if(response.strDebtorCode=='Invalid Code')
// 			        	{
// 			        		alert("Invalid Debtor Code");
// 			        		$("#txtDebtorCode").val('');
// 			        	}
// 			        	else
// 			        	{					        	    			        	    
// 			        	    /* Debtor Details */
// 			        	    $("#txtDebtorCode").val(code);
// 			        	    var debtorName = response.strFirstName+" "+ response.strMiddleName+" "+response.strLastName;
// 			        	    $("#txtDebtorName").val(debtorName);
// 			        	}
// 					},
// 					error: function(jqXHR, exception) {
// 			            if (jqXHR.status === 0) {
// 			                alert('Not connect.n Verify Network.');
// 			            } else if (jqXHR.status == 404) {
// 			                alert('Requested page not found. [404]');
// 			            } else if (jqXHR.status == 500) {
// 			                alert('Internal Server Error [500].');
// 			            } else if (exception === 'parsererror') {
// 			                alert('Requested JSON parse failed.');
// 			            } else if (exception === 'timeout') {
// 			                alert('Time out error.');
// 			            } else if (exception === 'abort') {
// 			                alert('Ajax request aborted.');
// 			            } else {
// 			                alert('Uncaught Error.n' + jqXHR.responseText);
// 			            }		            
// 			        }
// 		      });
// 	}
	
</script>
<body>
	<div id="formHeading">
		<label>Creditor OutStanding  Report</label>
	</div>

	<br />
	<br />

	<s:form name="FLR3AReport" method="GET" action="rptCreditorsOutStandingReport.html" target="_blank">
		<div>
			<table class="transTable">
			   
<!-- 			   <tr> -->
<!-- 			    <td width="10%"><label>Creditor Code </label></td> -->
<%-- 					<td width="10%" colspan="1"><s:input path="strDebtorCode" id="txtDebtorCode"  cssClass="searchTextBox" ondblclick="funHelp('creditorCode');"/></td> --%>
<!-- 					<td width="10%"><label>Creditor Name</label></td> -->
<%-- 					<td width="10%"><s:input path="strDebtorName" id="txtDebtorName" cssClass="longTextBox"/> --%>
<!-- 					</td>	 -->
<!-- 			    </tr> -->

	 	<tr>	<td>
					<label>GL Code</label>
				</td>
				<td>
					<s:input type="text" id="txtGLCode" path="strAccountCode" class="searchTextBox" ondblclick="funHelp('creditorAccountCode');"/>
				</td>
				<td  colspan="3"><label id="lblGLCode"></label></td></tr>
				
			    <tr>
					<td width="10%"><label>From Date </label></td>
					<td width="10%" colspan="1"><s:input id="txtFromDate" path="dteFromDate" required="true" readonly="readonly" cssClass="calenderTextBox"/></td>
					<td width="10%"><label>To Date </label></td>
					<td width="10%"><s:input id="txtToDate" path="dteToDate" required="true" readonly="readonly" cssClass="calenderTextBox"/>
					</td>	
				</tr>
<!-- 				<tr> -->
<!-- 					<td><label>Currency </label></td> -->
<%-- 					<td><s:select id="cmbCurrency" items="${currencyList}" path="strCurrency" cssClass="BoxW124px"> --%>
<%-- 						</s:select></td> --%>
<!-- 					<td colspan="2"></td> -->
<!-- 				</tr> -->
			</table>
		</div>
		<p align="center">
				<input type="submit" value="Submit"  class="form_button" />
				 <input type="button" value="Reset" class="form_button"  onclick="funResetFields()"/>
			</p>
	</s:form>

</body>
</html>