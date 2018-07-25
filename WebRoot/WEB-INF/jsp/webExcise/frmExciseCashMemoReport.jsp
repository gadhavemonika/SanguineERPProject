
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="default.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cost Of Issue</title>
<style>
#tblGroup tr:hover, #tblSubGroup tr:hover, #tblloc tr:hover {
	background-color: #72BEFC;
}
</style>
<script type="text/javascript">
    
 	
      $(document).ready(function() 
  		{
   	      	  
  			$( "#txtFromDate" ).datepicker({ dateFormat: 'yy-mm-dd' });		
  			$("#txtFromDate" ).datepicker('setDate', 'today'); 
  			
  			$( "#txtToDate" ).datepicker({ dateFormat: 'yy-mm-dd' });		
  			$("#txtToDate" ).datepicker('setDate', 'today'); 
  			
  			$("form").submit(function(event)
  					{
  						var fromDate=$("#txtFromDate").val();
  						var toDate=$("#txtToDate").val();
  						
  						if(fromDate.trim()=='' && fromDate.trim().length==0)
  						{
  							alert("Please Enter From Date");
  							return false;
  						}
  						if(toDate.trim()=='' && toDate.trim().length==0)
  						{
  							alert("Please Enter To Date");
  							return false;
  						}
  						
						if(CalculateDateDiff(fromDate,toDate)){
							return true;
						}else{
							return false;
						}
  					});
  		});	
      
      function CalculateDateDiff(fromDate, toDate) {

			var frmDate = fromDate.split('-');
			var fDate = new Date(frmDate[2], frmDate[1], frmDate[0]);

			var tDate = toDate.split('-');
			var t1Date = new Date(tDate[2], tDate[1], tDate[0]);

			var dateDiff = t1Date - fDate;
			if (dateDiff >= 0) {
				return true;
			} else {
				alert("Please Check From Date And To Date");
				return false
			}
		}      

	</script>
</head>

<body id="CashMemoReport" onload="funOnload();">
	<div id="formHeading">
		<label>Cash Memo Report</label>
	</div>
	<s:form name="rptExciseCashMemoReport" method="POST"
		action="rptExciseCashMemoReport.html" target="_blank">
		<br />
		<table class="masterTable">
			<tr>
				<td><label>From Date :</label></td>
				<td colspan="1" width="10%"><s:input id="txtFromDate"
						path="dtFromDate" required="true" readonly="readonly"
						cssClass="calenderTextBox" /></td>
				<td><label>To Date :</label></td>
				<td colspan="1"><s:input id="txtToDate" path="dtToDate"
						required="true" readonly="readonly" cssClass="calenderTextBox" />
				</td>
			</tr>


			<tr>
				<td><label>Report Type :</label></td>
				<td colspan="3"><s:select id="cmbDocType" path="strDocType"
						cssClass="BoxW124px">
						<s:option value="PDF">PDF</s:option>
						<%-- 						<s:option value="XLS">EXCEL</s:option> --%>
					</s:select></td>
			</tr>

		</table>


		<br>
		<p align="center">
			<input type="submit" value="Submit" class="form_button" /> <input
				type="reset" value="reset" class="form_button" />
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