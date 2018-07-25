 <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
  <head>
    <link rel="stylesheet" type="text/css" href="default.css" />
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Cost Of Issue</title>
    <style>
  #tblGroup tr:hover , #tblSubGroup tr:hover, #tblloc tr:hover{
	background-color: #72BEFC;
	
}
</style>
    <script type="text/javascript">
    
 	
	    //Set Start Date in date picker
        $(function() 
    		{
	    	      	  
    			$( "#txtFromDate" ).datepicker({ dateFormat: 'yy-mm-dd' });		
    			$("#txtFromDate" ).datepicker('setDate', 'today'); 
    			
    			$( "#txtToDate" ).datepicker({ dateFormat: 'yy-mm-dd' });		
    			$("#txtToDate" ).datepicker('setDate', 'today'); 
    			
    			
    			 
    		});	
      

	</script>    
  </head>
  	
	<body id="PurchaseAnylasisReport" onload="funOnload();">
	<div id="formHeading">
		<label>Purchase Anylasis Report</label>
	</div>
		<s:form name="frmExcisePurchaseAnylasisReport" method="POST" action="rptPurchaseAnylasisReport.html" target="_blank">
	   		<br />
	   		<table class="transTable">
			    <tr>
					<td width="10%"><label>From Date :</label></td>
					<td colspan="1" width="10%"><s:input id="txtFromDate" path="dtFromDate" required="true" readonly="readonly" cssClass="calenderTextBox"/></td>
					<td width="10%"><label>To Date :</label></td>
					<td colspan="1"><s:input id="txtToDate" path="dtToDate" required="true" readonly="readonly" cssClass="calenderTextBox"/>
					</td>
				</tr>
			 
		
			<tr>
				<td width="10%"><label>Report Type :</label></td>
				<td colspan="3"><s:select id="cmbDocType" path="strDocType"
						cssClass="BoxW124px">
<%-- 						<s:option value="PDF">PDF</s:option> --%>
						<s:option value="XLS">EXCEL</s:option>
<%-- 						<s:option value="HTML">HTML</s:option> --%>
<%-- 						<s:option value="CSV">CSV</s:option> --%>
					</s:select></td>
			</tr>

		</table>
		

		<br>
			<p align="center">
				 <input type="button" value="Submit" class="form_button" />
				 			     
			</p>  
			
			
			
			
			<div id="wait"
			style="display: none; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 60%; left: 55%; padding: 2px;">
			<img
				src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"
				width="60px" height="60px" />
		</div>
		</s:form>
	</body>
</html>