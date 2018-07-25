<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<script type="text/javascript">
//set date
$(document).ready(function(){
	
	var pmsDate='<%=session.getAttribute("PMSDate").toString()%>';
	
	$("#dteFromDate").datepicker({
		dateFormat : 'dd-mm-yy'
	});
	$("#dteFromDate").datepicker('setDate', pmsDate);
	
	
	$("#dteTodate").datepicker({
		dateFormat : 'dd-mm-yy'
	});
	$("#dteTodate").datepicker('setDate', pmsDate);

});



</script>

<body>
	<div id="formHeading">
		<label>Guest List Report </label>
	</div>
			<br/>
			<br/>
			<br/>
	<s:form name="BillPrinting" method="GET" action="rptGuestListReport.html" target="_blank"> 
	

		<table class="masterTable">
	
			
				<tr>
				<td><label>From Date</label></td>
				<td><s:input type="text" id="dteFromDate" path="dteFromDate" required="true" class="calenderTextBox" /></td>
				<td><label>To Date</label></td>
				<td><s:input type="text" id="dteTodate" path="dteTodate" required="true" class="calenderTextBox" /></td>	
					
			</tr>
		
			
			</table>
				<br/>
			<br/>
			<br/>
					<p align="center">
		   	<input type="submit" value="Submit" tabindex="3" class="form_button" onclick="return funValidateFields()" />
			&nbsp; &nbsp; &nbsp; <input type="button" value="Reset"
				class="form_button" onclick="funResetFields();" />
		</p>
			</s:form>
			
			

</body>
</html>