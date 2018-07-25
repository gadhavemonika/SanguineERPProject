<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<style>
.ui-autocomplete {
    max-height: 200px;
    overflow-y: auto;
    /* prevent horizontal scrollbar */
    overflow-x: hidden;
    /* add padding to account for vertical scrollbar */
    padding-right: 20px;
}
/* IE 6 doesn't support max-height
 * we use height instead, but this forces the menu to always be this tall
 */
* html .ui-autocomplete {
    height: 200px;
}
</style>

<script type="text/javascript">
 	$(document).ready(function() {
 		var POSDate="${POSDate}"
 			var startDate="${POSDate}";
 		  	var Date = startDate.split(" ");
 			var arr = Date[0].split("-");
 			Dat=arr[2]+"-"+arr[1]+"-"+arr[0];	
 			$("#txtdteToDate").datepicker({ dateFormat: 'dd-mm-yy'  });
 			$("#txtdteToDate" ).datepicker('setDate', Dat);
 		
		
 	});
	
	
	function funInvokeReport()
	{
		var type;
		type=$("#cmbReportType").val();
		
		
	}
	
	
	
	
</script>		

</head>
<body>

<div id="formHeading">
		<label>Cocktail World Interface</label>
	</div>
	<s:form name="Cocktail World Interface" method="POST" action="savePOSCWInterface.html">

		<br />
		<br />
		<table class="masterTable">

		<tr>
			<td>
				<label>POS Name</label>
			</td>
			<td>
				<s:select id="cmbPOSName" name="cmbPOSName" path="strPOSName" items="${posList}" cssClass="BoxW124px" />
			</td>
		</tr>
		<tr>
			<td>
				<label>Date</label>
			</td>
			<td>
				<s:input id="txtdteToDate" name="txtdteToDate" path="dteExpiryDt" value="${posDate}" cssClass="calenderTextBox" />
			</td>
		</tr>
		<tr>
			<td>
				<label>Report Type</label>
			</td>
			<td>
				<s:select id="cmbReportType" name="cmbReportType" path="strcmbReportType" cssClass="BoxW124px" >
				    <option value="Sale Data File">Sale Data File</option>
				    <option value="Menu Item Code File">Menu Item Code File</option>
				</s:select>
			</td>
		</tr>
</table>

<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" /> 
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>
</s:form>
</body>
</html>