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

	});
</script>
<body>
	<div id="formHeading">
		<label>Chart Of Account Report</label>
	</div>
			
	<br />
	<br />

	<s:form name="ChartOfAccountReport" method="GET" action="rptChartOfAccountReport.html" target="_blank">

		<p align="center">
				<input type="submit" value="View Report"  class="form_button" />
				<!--  <input type="button" value="Reset" class="form_button"  onclick="funResetFields()"/> -->
			</p>
	</s:form>

</body>
</html>