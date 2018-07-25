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
		$( "#txtFromDate" ).datepicker({ dateFormat: 'dd-mm-yy' });
		$( "#txtToDate" ).datepicker({ dateFormat: 'dd-mm-yy' });
		
		$("#txtFromDate" ).datepicker('setDate', 'today');
		$("#txtToDate" ).datepicker('setDate', 'today');
		var glCode = $("#txtGLCode").val();
		if(glCode!='')
		{
			funSetGLCode(glCode);
		}

	});
</script>
<body>
	<div id="formHeading">
		<label>Debtor OutStanding  List</label>
	</div>

	<br />
	<br />

	<s:form name="FLR3AReport" method="GET" action="rptDebtorOutStandingList.html" target="_blank">
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
					<td><label>Currency </label></td>
					<td><s:select id="cmbCurrency" items="${currencyList}" path="strCurrency" cssClass="BoxW124px">
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