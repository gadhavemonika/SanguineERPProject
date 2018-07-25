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
    $(document).ready(function() 
      {
    	var startDate="${startDate}";
		var arr = startDate.split("/");
		Dat=arr[2]+"-"+arr[1]+"-"+arr[0];
	    $("#txtDate").datepicker({ dateFormat: 'yy-mm-dd' });
		$("#txtDate" ).datepicker('setDate', Dat);
		$("#txtDate").datepicker();
      });
    </script>
  </head>
  
	<body >
	<div id="formHeading">
		<label></label>
	</div>
	<br />
	<br />
		<s:form name="frmConsolidateCustomerWiseSalesOrder" method="GET"  action="consolidateCustomerSOExcel.html" target="_blank"> 
<%-- 		action="consolidateCustomerSOExcel.html" target="_blank"> rptConsolidateCustomerWiseSalesOrder	 --%>
			
			<table class="masterTable">
	<tr><th colspan="4"></th></tr>
				
				
				<tr>
					<td width="100px"><label>Fullfillment Date</label>
									<td><s:input path="dteFromDate" id="txtDate"
											cssClass="calenderTextBox" /></td>
					<td><label>Report Type</label></td>
					<td>
						<s:select id="cmbDocType" path="strDocType" cssClass="BoxW124px">
<%-- 				    		<s:option value="PDF">PDF</s:option> --%>
				    		<s:option value="XLS">EXCEL</s:option>
				    	
				    	</s:select>
					</td>
				</tr>
				
				<tr>
				<td colspan="4"></td>
					<!-- <td><input type="submit" value="Submit" /></td>
					<td><input type="reset" value="Reset" onclick="funResetFields()"/></td>	 -->				
				</tr>
			</table>
			<br>
			<p align="center">
				<input type="submit" value="Submit"  class="form_button"/>
				 <input type="button" value="Reset" class="form_button"  onclick="funResetFields()"/>
			</p>
			
		</s:form>
	</body>
</html>