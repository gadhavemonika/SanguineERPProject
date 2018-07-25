<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style>

 #tblGroup tr:hover , #tblSubGroup tr:hover
  {
	background-color: #72BEFC;
	
  }

.searchTextBox 
{
	    background-position: 387px 2px;
}
</style>
<script type="text/javascript">




		//set date
		$(document).ready(function(){
			var startDate="${startDate}";
			var arr = startDate.split("/");
			Dat=arr[0]+"-"+arr[1]+"-"+arr[2];
			$("#dteFromDate").datepicker({ dateFormat : 'dd-mm-yy' });
			$("#dteFromDate").datepicker('setDate', Dat);	
			
			
			$("#dteToDate").datepicker({ dateFormat : 'dd-mm-yy' });
			$("#dteToDate").datepicker('setDate', 'today');	
			
			
			
		});
		

		function update_FromDate(selecteDate){
			var date = $('#dteFromDate').val();
			$('#dteToDate').val(selecteDate);
		}

		

		function update_FromFulFillmentDate(selecteDate){
			var date = $('#dteFromFulfillment').val();
			$('#dteToFulfillment').val(selecteDate);
		}
		
		
		
</script>
</head>
<body>
<div id="formHeading">
		<label>Invoice Profit Report</label>
	</div>
	<br />
	<br />
		<s:form name="frmInvoiceProfitReport" method="POST" action="rptInvoiceProfitReport.html" target="_blank">
	   		
		<table class="transTable">
		
			
			<tr>
				<td><label>From Date</label></td>
				<td><s:input type="text" id="dteFromDate" path="dteFromDate" required="true" class="calenderTextBox" /></td>
				<td><label>To Date</label></td>
				<td><s:input type="text" id="dteToDate" path="dteToDate" required="true" class="calenderTextBox" /></td>				
			</tr>
		
			    <tr>
			      
			      	<td ><label>Report Type</label></td>
					<td colspan="3">	
					<s:select id="cmbDocType" path="strDocType" cssClass="BoxW124px">
				    		<s:option value="PDF">PDF</s:option>
				    		<s:option value="XLS">EXCEL</s:option>
<%-- 				    		<s:option value="HTML">HTML</s:option> --%>
<%-- 				    		<s:option value="CSV">CSV</s:option> --%>
				    </s:select>
					</td>
			   </tr>
			</table>
			<br>
			<p align="center">
				<input type="submit" value="Submit"  class="form_button" onclick="return funCallFormAction('submit',this)" />
				 <input type="button" value="Reset" class="form_button"  onclick="funResetFields()"/>
			</p>
			<div id="wait"
			style="display: none; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 60%; left: 55%; padding: 2px;">
			<img
				src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"
				width="60px" height="60px" />
		</div>
		
		</s:form>
</body>
</html>