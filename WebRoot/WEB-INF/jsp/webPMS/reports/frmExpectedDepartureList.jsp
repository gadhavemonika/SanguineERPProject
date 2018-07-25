<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  	<link rel="stylesheet" type="text/css" href="default.css" />
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>GRN Slip</title>
    <script type="text/javascript">
    	
    	var fieldName;
    	
    	//Set date in date picker when form is loading or document is reddy or initialize with default value
    	$(function() 
    			{		
    		
    				var pmsDate='<%=session.getAttribute("PMSDate").toString()%>';
    		
    				$("#txtFromDate").datepicker({ dateFormat: 'dd-mm-yy' });
    				$("#txtFromDate" ).datepicker('setDate', pmsDate);
    				$("#txtToDate").datepicker({ dateFormat: 'dd-mm-yy' });
    				$("#txtToDate" ).datepicker('setDate', pmsDate);
    				
    			
    			});
    	
    	//Reset the filed
    	
    
    </script>
  </head>
<body>
<div id="formHeading">
		<label>Expected Departure List</label>
	</div>
<s:form name="ExpectedDepartureList" method="POST" action="rptExpectedDepartureList.html" target="_blank">

<br />
<br />
<table class="masterTable">
	<tr><th colspan="8"></th></tr>
	
	<tr>
		
		<td><label>PropertyCode</label></td>
				<td colspan="4"><s:select id="strPropertyCode" path="strPropertyCode" items="${listOfProperty}" required="true" cssClass="BoxW200px"></s:select></td>
		
	</tr>
	
	
	<tr>
				<td width="10%"><label id="lblFromDate">From Date</label></td>
				<td width="10%"><s:input id="txtFromDate" name="fromDate"
						path="dtFromDate" cssClass="calenderTextBox" required="true"/> <s:errors
						path="dtFromDate"></s:errors></td>
						<td></td>
				<td width="10%"><label id="lblToDate">To Date</label></td>
				<td colspan="4"><s:input id="txtToDate" name="toDate"
						path="dtToDate" cssClass="calenderTextBox" required="true"/> <s:errors
						path="dtToDate"></s:errors></td>
						
			</tr>
	
	<tr>
	<td><label>Report Type</label></td>
					<td colspan="4">
						<s:select id="cmbDocType" path="strDocType" cssClass="BoxW124px">
				    		<s:option value="PDF">PDF</s:option>
				    		<s:option value="XLS">EXCEL</s:option>
				    		<s:option value="HTML">HTML</s:option>
				    		<s:option value="CSV">CSV</s:option>
				    	</s:select>
			</td>
	</tr>
	
</table>
<br>
		<p align="center">
			<input type="submit" value="Submit"  class="form_button"/>
			 <input type="button" value="Reset" class="form_button" />
		</p>
<s:input type="hidden" id="hidSuppCode" path="strSuppCode"></s:input>
</s:form>
</body>
</html>