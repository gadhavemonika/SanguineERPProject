<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@	taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Customer And Sub GroupWise Sales Report</title>
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
		
		

	});
</script>
<body>
	<div id="formHeading">
		<label>Customer And Sub GroupWise Sales Report</label>
	</div>

	<br />
	<br />

	<s:form name="frmSalesAndSalesReturnSummaryReport" method="GET" action="rpSalesAndSalesReturnSummaryReport.html" target="_blank">
		<div>
			<table class="transTable">
			    <tr>
					<td width="10%"><label>From Date </label></td>
					<td width="10%" colspan="1"><s:input id="txtFromDate" path="dteFromDate" required="true" readonly="readonly" cssClass="calenderTextBox"/></td>
					<td width="10%"><label>To Date </label></td>
					<td width="10%"><s:input id="txtToDate" path="dteToDate" required="true" readonly="readonly" cssClass="calenderTextBox"/>
					</td>	
				</tr>
				<tr>
					<td><label>Report Type </label></td>
					<td><s:select id="cmbType" path="strType" cssClass="BoxW124px">
						<s:option value="Summary" selected="selected">Summary</s:option>
                    	<s:option value="Sub GroupWise Tax Breakup">Sub GroupWise Tax Breakup</s:option>
                    	<s:option value="Customer And SubGroup Wise Breakup">Customer And SubGroup Wise Breakup</s:option> 
                   </s:select></td>
					<td colspan="2"></td>
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