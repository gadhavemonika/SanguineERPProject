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
	<label>Dependent Master</label>
	</div>
	<div>
	<s:form name="frmDependentMaster" action="saveDependentMaster.html?saddr=${urlHits}" method="POST">
		<br>
			<table class="masterTable">
				
			<tr>
				<td width="16%">Dependent Code</td>
				<td width="17%"><s:input id="txtDependentCode" path=""
						cssClass="searchTextBox" ondblclick="" /></td>
			
				<td><label>Dependent Name</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<s:input type="text" id="txtDependentName" 
						name="txtDependentName" path="" required="true"
						cssStyle="width:53% ; text-transform: uppercase;" cssClass="longTextBox"  /> <s:errors path=""></s:errors></td>
			</tr>
			
			<tr>
					<td><label>Change Dependent Code</label></td>
					<td><s:input  type="text" id="txtDependentName" 
						name="txtChangeDependentCode" path="" required="true"
						cssStyle="searchTextBox" cssClass="longTextBox"  /> <s:errors path=""></s:errors></td>
					<td><s:input  type="text" id="txtDependentName" 
						name="txtChangeDependentCode" path="" required="true"
						cssClass="longTextBox"  /> <s:errors path=""></s:errors></td>
			
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