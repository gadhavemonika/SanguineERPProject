<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  	<link rel="stylesheet" type="text/css" href="default.css" />
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Web Stocks</title>
    <script type="text/javascript">
    	
    	var fieldName;
    
    	function funResetFields()
    	{
    		$("#txtGCode").val('');
    	}
    	
    	function funHelp(transactionName)
		{
    	 //	 window.showModalDialog("searchform.html?formname="+transactionName+ "&searchText=", 'window', 'width=600,height=600');
    		 window.open("searchform.html?formname="+transactionName+ "&searchText=", 'window', 'width=600,height=600');
	   }
		
		function funSetData(code)
		{
			$("#txtSGCode").val(code);
		}
    
    </script>
  </head>
  
	<body >
		<s:form name="subGroupList" method="GET" action="rptSubGroupList.html" target="_blank">
			<table>
				<tr>
					<td><label>Group Code :</label></td>
					<td><s:input class="textbox" id="txtGCode" path="strDocCode" ondblclick="funHelp('Gcode')"/></td>
				</tr>
				
				<tr>
					<td><label>Report Type :</label></td>
					<td>
						<s:select id="cmbDocType" path="strDocType">
				    		<s:option value="PDF">PDF</s:option>
				    		<s:option value="XLS">EXCEL</s:option>
				    		<s:option value="HTML">HTML</s:option>
				    		<s:option value="CSV">CSV</s:option>
				    	</s:select>
					</td>
				</tr>
				<tr></tr>
				<tr>
					<td><input type="submit" value="Submit" /></td>
					<td><input type="reset" value="Reset" onclick="funResetFields()"/></td>					
				</tr>
			</table>
		</s:form>
	</body>
</html>