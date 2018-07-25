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
    			$("#txtFromDate").datepicker({ dateFormat: 'yy-mm-dd' });
				$("#txtFromDate" ).datepicker('setDate', Dat);
				$("#txtFromDate").datepicker();
		
		 		$("#txtToDate").datepicker({ dateFormat: 'yy-mm-dd' });
				$("#txtToDate" ).datepicker('setDate', 'today');
				$("#txtToDate").datepicker();	
		
		
    		});
    
    function update_FromFulFillmentDate(selecteDate){
		var date = $('#txtFromDate').val();
		$('#txtToDate').val(selecteDate);
	}
    
    </script>
  </head>
  
	<body >
	<div id="formHeading">
		<label></label>
	</div>
	<br />
	<br />
		<s:form name="frmConsolidateCustomerWiseAvgSalesOrder" method="GET"  action="consolidateCustomerAvgSOExcel.html" target="_blank"> 
			
			
			
			
			<table class="masterTable">
					<tr><th colspan="4"></th></tr>
				
				
				<tr>
					<td width="100px"><label>From Fullfillment Date</label>
									<td><s:input path="dteFromDate" id="txtFromDate"
											cssClass="calenderTextBox" onchange="update_FromFulFillmentDate(this.value);"/></td>
											
					<td width="100px"><label>To Fullfillment Date</label>
									<td><s:input path="dteToDate" id="txtToDate"
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
			<td><label>Week Day</label></td>
			<td colspan="5">
			
					<s:select id="cmbDay" path="strWeekDay" cssClass="BoxW124px">
<%-- 				    		<s:option value="PDF">PDF</s:option> --%>
				    		<s:option value="2">Monday</s:option> 
				    		<s:option value="3">Tuesday</s:option> 
				    		<s:option value="4">Wednesday</s:option> 
				    		<s:option value="5">Thursday</s:option> 
				    		<s:option value="6">Friday</s:option> 
				    		<s:option value="7">Saturday</s:option> 
				    		<s:option value="1">Sunday</s:option> 	
				    		</s:select>		</td>	
			
			
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