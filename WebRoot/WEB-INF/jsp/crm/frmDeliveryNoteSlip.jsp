<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>


</head>
<body onload="funOnLoad();">
	<div id="formHeading">
		<label>Delivery Note Slip</label>
	</div>
	<s:form name="DeliveryNoteSlip" method="GET"
		action="" >
		<input type="hidden" value="${urlHits}" name="saddr">
		<br>
		<table class="transTable">
								<tr>
									<td width="120px"><label>Delivery Note Code</label></td>
									<td colspan="3"><s:input path="strDocCode" id="txtJOCode"
											ondblclick="funHelp('')"
											cssClass="searchTextBox" /></td>
											<td colspan="1"><label id="lblJobOrderName"
										class="namelabel"></label></td>
																										
								</tr>
								<tr>
								

				<td><label>Report Type</label></td>
				<td ><s:select id="cmbDocType" path="strDocType"
						cssClass="BoxW124px">
						<s:option value="PDF">INVOICE</s:option>
						<s:option value="XLS">Challan</s:option>

					</s:select></td>




			</tr>

		</table>
		<br>
		<p align="center">
			<input type="submit" value="Submit"
				onclick="return funCallFormAction('submit',this)"
				class="form_button" /> &nbsp; &nbsp; &nbsp; <a
				STYLE="text-decoration: none"
				href="frmDeliveryNoteSlip.html?saddr=${urlHits}"><input
				type="button" id="reset" name="reset" value="Reset"
				class="form_button" /></a>
		</p>
		<br>
		<div id="wait"
			style="display: none; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 60%; left: 55%; padding: 2px;">
			<img
				src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"
				width="60px" height="60px" />
		</div>
	</s:form>

</body>
</html>