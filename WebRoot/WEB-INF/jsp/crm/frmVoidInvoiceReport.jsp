<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Void Bill Report</title>
</head>

<script>


	function funValidateFields()
	{
		var flag=false;
			flag=true;
			var fromDate=$("#dteFromDate").val();
			var toDate=$("#dteToDate").val();
			var against=$("#cmbType").val();
			window.open(getContextPath()+"/voidInvoiceReportSummary.html?fromDate="+fromDate+"&toDate="+toDate+"");
			
		return flag;
		
	}


//set date
		$(document).ready(function(){
			
			var startDate="${startDate}";
			var arr = startDate.split("/");
			Date1=arr[0]+"-"+arr[1]+"-"+arr[2];
			
			$("#dteFromDate").datepicker({
				dateFormat : 'dd-mm-yy'
			});
			$("#dteFromDate").datepicker('setDate', Date1);	
			$("#dteFromDate").datepicker();
			
			$("#dteToDate").datepicker({
				dateFormat : 'dd-mm-yy'
			});
			$("#dteToDate").datepicker('setDate', 'today');	
			$("#dteToDate").datepicker();
		});
		
		
		
		
</script>
<body>
	<div id="formHeading">
		<label>Void Invoice Report </label>
	</div>
	<s:form name="frmVoidInvoiceReport" method="GET" action="" >
		
	<table class="masterTable">
		
			<tr>
				<td><label>From Date</label></td>
				<td><s:input type="text" id="dteFromDate" path="dteFromDate" required="true" class="calenderTextBox" /></td>
				<td><label>To Date</label></td>
				<td><s:input type="text" id="dteToDate" path="dteToDate" required="true" class="calenderTextBox" /></td>				
			</tr>
			<tr>
				<td>Report Type</td>
				<td colspan="5"> 
				   <select id="cmbType" class="BoxW124px">
				      <option value="PDF">Pdf</option>
				  </select>
				</td>
			</tr>
		</table>
		<br>
	<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" onclick="return funValidateFields()" />
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>	
	</s:form>

</body>
</html>