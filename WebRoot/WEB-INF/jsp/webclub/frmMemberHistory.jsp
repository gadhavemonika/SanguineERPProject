<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=8"/>
		
		
		
</head>
<body >
<div id="formHeading">
	<label>Member History</label>
	</div>
	<div>
	<s:form name="frmMemberHistory" action="saveMemberHistory.html?saddr=${urlHits}" method="POST">
		<br>
		<table class="masterTable">
		
		<tr>
				<td width="15%">Member Category Code</td>
				<td width="10%"><s:input id="txtMCCode" path=""
						cssClass="searchTextBox" ondblclick="" /></td>
				<td width="50%"><s:input type="text" id="txtMCName" 
						name="txtMCName" path="" required="true"
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> <s:errors path=""></s:errors></td>
			</tr>
			
			 <tr>
				<td><label>From Date</label></td>
			    <td><s:input id="txtdtMemberFromDate" name="txtdtMemberFromDate" path=""  cssClass="calenderTextBox" /></td>
				
				<td><label>To Date</label>&nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
			    <s:input id="txtdtMemberToDate" name="txtdtMemberToDate" path=""  cssClass="calenderTextBox" /></td>
		</tr>
		
		 
		</table> 
		<br>
		<p align="center">
			<input type="submit" value="Submit"
				onclick=""
				class="form_button" /> &nbsp; &nbsp; &nbsp; <input type="reset"
				value="Reset" class="form_button" onclick="funResetField()" />
		</p>
		<br><br>
	
	</s:form>
</div>


</body>
</html>