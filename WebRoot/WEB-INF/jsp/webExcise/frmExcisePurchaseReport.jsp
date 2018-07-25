<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<script type="text/javascript">

	$(document).ready(function() {

		$("#txtFromDate").datepicker({
			dateFormat : 'yy-mm-dd'
		});
		$("#txtFromDate").datepicker('setDate', 'today');

		$("#txtToDate").datepicker({
			dateFormat : 'yy-mm-dd'
		});
		$("#txtToDate").datepicker('setDate', 'today');

	});
	


</script>



<body>
	<div id="formHeading">
		<label>Purchase Report</label>
	</div>

	<br />
	<br />

	<s:form name="purchaseReport" method="POST" action="rptExcisePurchaseExcelExport.html?saddr=${urlHits}">
		<div>
			<table class="masterTable">
				<tr>
					<td><label>From Date</label></td>
					<td><s:input type="text" id="txtFromDate"
							class="calenderTextBox" path="strFromDate" required="required" />
					</td>

					<td><label>To Date</label></td>
					<td><s:input type="text" id="txtToDate"
							class="calenderTextBox" path="strToDate" required="required" /></td>
				</tr>
			</table>
		</div>
	
	<p align="center">
		<br /> <br /> <input type="submit" value="Export" class="form_button"
	            id="submit"/ > 
	            <!-- 			id="submit" ondblclick="funOpenExport()"> <input type="reset" -->
	            <input type="reset"
			value="Reset" class="form_button" id="btnReset" />

	</p>
</s:form>

</body>
</html>