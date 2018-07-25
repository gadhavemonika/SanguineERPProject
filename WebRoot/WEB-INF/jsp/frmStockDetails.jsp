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
    $(function() 
    		{
    	
		    	$( "#txtFromDate" ).datepicker();
		    	$( "#txtToDate" ).datepicker();
		    	$("stockDetails").submit(function()
		    			{				
		    				if($("#txtFromDate").val()=='')
		    				{
		    					alert("Please enter From Date");
		    					return false;
		    				}
		    				if($("#txtToDate").val()=='')
		    				{
		    					alert("Please enter To Date");
		    					return false;
		    				}
		    			});
		    			
		    				
    			});
    
    
    
    </script>
   
  </head>
  
	<body >
		<s:form name="stockDetails" method="GET" action="rptStockDetails.html" target="_blank">
			<table>
			<tr>
				<td><label>From Date :</label></td>
				<td colspan="1"><input id="txtFromDate" value="" readonly="readonly"/></td>
				<td><label>To Date :</label></td>
				<td colspan="1"><input id="txtToDate" value="" readonly="readonly"/></td>
				
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