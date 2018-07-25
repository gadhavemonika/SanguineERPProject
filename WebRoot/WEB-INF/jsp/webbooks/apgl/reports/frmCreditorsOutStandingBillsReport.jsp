<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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

		$("#txtFromDate").datepicker({
			dateFormat : 'dd-mm-yy'
		});
		$("#txtFromDate").datepicker('setDate', 'today');

		$("#txtToDate").datepicker({
			dateFormat : 'dd-mm-yy'
		});
		$("#txtToDate").datepicker('setDate', 'today');

	});
</script>
<body>
	<div id="formHeading">
		<label>Creditor OutStanding Bill Report</label>
	</div>

	<br />
	<br />

	<s:form name="FLR3AReport" method="POST" action="" target="_blank">
		<div>
			<table class="masterTable">
				<tr>
					<td><label>From Date</label></td>
					<td><input type="text" id="txtFromDate"
							class="calenderTextBox" required="required" ></td>

					<td><label>To Date</label></td>
					<td><input type="text" id="txtToDate"
							class="calenderTextBox" required="required" ></td>
				</tr>
			</table>
		</div>
	</s:form>


</body>
</html>