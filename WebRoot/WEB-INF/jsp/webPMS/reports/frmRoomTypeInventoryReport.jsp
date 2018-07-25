<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<script>


	function funValidateFields()
	{
		var flag=false;
			flag=true;
			
			var fromDate=$("#dteFromDate").val();
 			var strDocType=$("#cmbDocType").val();
			
			var fd=fromDate.split("-")[0];
			var fm=fromDate.split("-")[1];
			var fy=fromDate.split("-")[2];

			
			$("#dteFromDate").val(fy+"-"+fm+"-"+fd);
			
		
			
			window.open(getContextPath()+"/rptRoomTypeInventoryReport.html?fromDate="+fy+"-"+fm+"-"+fd+"&strDocType="+strDocType);
		
		return flag;
	}


//set date
		$(document).ready(function(){
			
			var pmsDate='<%=session.getAttribute("PMSDate").toString()%>';
			
			$("#dteFromDate").datepicker({
				dateFormat : 'dd-mm-yy'
			});
			$("#dteFromDate").datepicker('setDate', pmsDate);	
			
			
			$("#dteToDate").datepicker({
				dateFormat : 'dd-mm-yy'
			});
			$("#dteToDate").datepicker('setDate', pmsDate);	
		});
		
		
		
		
</script>
<body onload="funOnLoad();">
	<div id="formHeading">
		<label>Room Type Inventory </label>
	</div>
	<s:form name="frmRoomTypeInventory" method="GET" action="" >
		
	<table class="masterTable">
		<br/><br/>
			<tr>
				<td><label>Date</label></td>
				<td><s:input type="text" id="dteFromDate" path="dteFromDate" required="true" class="calenderTextBox" /></td>
<!-- 				<td><label>To Date</label></td> -->
<%-- 				<td><s:input type="text" id="dteToDate" path="dteToDate" required="true" class="calenderTextBox" /></td>				 --%>
			</tr>
			<tr>
				<td ><label>Report Type </label></td>
				<td ><s:select id="cmbDocType" path="strDocType"
						cssClass="BoxW124px">
						<s:option value="PDF">PDF</s:option>
						<s:option value="XLS">EXCEL</s:option>
						
					</s:select></td>
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