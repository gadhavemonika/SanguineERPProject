<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html.dtd">
<html>
<head>
<script>

		$(document).ready(function(){
			var startDate="${startDate}";
			var arr = startDate.split("/");
			Date1=arr[2]+"-"+arr[1]+"-"+arr[0];
			$("#dtFromDate").datepicker({
				dateFormat : 'yy-mm-dd'
			});
			$("#dtFromDate").datepicker('setDate', Date1);	
			
			
			$("#dtToDate").datepicker({
				dateFormat : 'yy-mm-dd'
			});
			$("#dtToDate").datepicker('setDate', 'today');	
		});
		
		
		
		function funSetData(code)
		{
			switch(fieldName)
			{
			
			case 'suppcode':
		    	funSetSupplier(code);
		        break;
			}
		}
		
		//Open Help Form
    	function funHelp(transactionName)
		{
			fieldName=transactionName;
		//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1100px;dialogLeft:200px;")
			window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1100px;dialogLeft:200px;")
	    }
		
		  //Get Supplier data
		function funSetSupplier(code) {
			var searchUrl = "";
			searchUrl = getContextPath()
					+ "/loadSupplierMasterData.html?partyCode=" + code;

			$.ajax({
				type : "GET",
				url : searchUrl,
				dataType : "json",
				success : function(response) {
					if ('Invalid Code' == response.strPCode) {
						alert('Invalid Code');
						$("#txtSuppCode").val('');
						$("#lblSuppName").text('');
						$("#txtSuppCode").focus();
					} else {
											
						$("#txtSuppCode").val(response.strPCode);
						$("#lblSuppName").text(response.strPName);
						
					}
				},
				error : function(jqXHR, exception) {
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
		<label>Purchase Register Report</label>
	</div>
	<br />
	<br />
		<s:form name="frmPurchaseRegisterReport" method="POST" action="rptPurchaseRegisterReport.html" target="_blank" >

			<table class="transTable">
		
			<tr>
				<td><label>From Date</label></td>
				<td><s:input type="text" id="dtFromDate" path="dteFromDate" required="true" class="calenderTextBox" /></td>
				<td><label>To Date</label></td>
				<td colspan="4"><s:input type="text" id="dtToDate" path="dteToDate" required="true" class="calenderTextBox" /></td>				
			</tr>
			
			<tr>
				<td width="140px">Supplier Code</td>
				<td><s:input id="txtSuppCode" path="strDocCode"
						cssClass="searchTextBox" ondblclick="funHelp('suppcode')" /></td>
				<td colspan="2"><label style="font-size: 12px;" id="lblSuppName"> All Supplier </label>	</td>	
				
				
				<td><label>Report Type</label></td>
					<td colspan="4">
						<s:select id="cmbDocType" path="strDocType" cssClass="BoxW124px">
				    		<s:option value="PDF">PDF</s:option>
				    		<s:option value="XLS">EXCEL</s:option>
<%-- 				    		<s:option value="HTML">HTML</s:option> --%>
<%-- 				    		<s:option value="CSV">CSV</s:option> --%>
				    	</s:select>
			</td>		
			
			</tr>
			
			<tr>				
				<td><label>View Type</label>
				</td>
				<td colspan="6"><s:select id="cmbViewType" path="strReportView" items="${mapViewType}" cssClass="BoxW124px"/>				    	
				</td>					
			</tr>
			
			
			
		</table>
			<p align="center">
				<input type="submit" value="Export"  class="form_button" onclick="return funCallFormAction('submit',this)" />
				 <input type="button" value="Reset" class="form_button"  onclick="funResetFields()"/>
			</p>
		</s:form>
</body>
</html>