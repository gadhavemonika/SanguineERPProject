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
			Dat=arr[2]+"-"+arr[1]+"-"+arr[0];
			$("#dtFromDate").datepicker({
				dateFormat : 'yy-mm-dd'
			});
			$("#dtFromDate").datepicker('setDate', Dat);	
			
			
			$("#dtToDate").datepicker({
				dateFormat : 'yy-mm-dd'
			});
			$("#dtToDate").datepicker('setDate', 'today');	
		});


</script>
<body>
<div id="formHeading">
		<label>RG-1 Daily Stock Account</label>
	</div>
	<br />
	<br />
		<s:form name="RG-1DailyStockAccount" method="GET" action="rptRG1DailyStockAccount.html" target="_blank" >

			<table class="transTable">
		
			<tr>
				<td><label>From Date</label></td>
				<td><input type="text" id="dtFromDate" name="dtFromDate" required="true" class="calenderTextBox" /></td>
				<td><label>To Date</label></td>
				<td><input type="text" id="dtToDate" name="dtToDate" required="true" class="calenderTextBox" /></td>				
			</tr>
		</table>
			<p align="center">
				<input type="submit" value="Export"  class="form_button" onclick="return funCallFormAction('submit',this)" />
				 <input type="button" value="Reset" class="form_button"  onclick="funResetFields()"/>
			</p>
		</s:form>
</body>
</html>