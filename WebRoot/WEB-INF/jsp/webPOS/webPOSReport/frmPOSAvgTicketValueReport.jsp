<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Average Ticket Value Report</title>
<style>
.ui-autocomplete {
    max-height: 200px;
    overflow-y: auto;
    /* prevent horizontal scrollbar */
    overflow-x: hidden;
    /* add padding to account for vertical scrollbar */
    padding-right: 20px;
}
/* IE 6 doesn't support max-height
 * we use height instead, but this forces the menu to always be this tall
 */
* html .ui-autocomplete {
    height: 200px;
}
</style>
<script type="text/javascript">

/*On form Load It Reset form :Ritesh 22 Nov 2014*/
 $(function() 
    			{		
    				$("#txtFromDate").datepicker({ dateFormat: 'dd-mm-yy' });
    				$("#txtFromDate" ).datepicker('setDate', 'today');
    				$("#txtToDate").datepicker({ dateFormat: 'dd-mm-yy' });
    				$("#txtToDate" ).datepicker('setDate', 'today');
    				
    			}); 

 function funSetDate()
	{
		
		var searchurl=getContextPath()+"/getPOSDate.html";
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	/* var dateTime=response.POSDate;
			        	var date=dateTime.split(" ");
			        	$("#txtFromDate").val(date[0]);
			        	$("#txtToDate").val(date[0]); */
			        	
			        var date = new Date(response.POSDate);
			        var	dateTime=date.getDate()  + '-' + (date.getMonth() + 1)+ '-' +  date.getFullYear();
			        var posDate=dateTime.split(" ");
			        $("#txtFromDate").val(posDate[0]);
		        	$("#txtToDate").val(posDate[0]);
		        	
			        },
			        error: function(jqXHR, exception)
			        {
			            if (jqXHR.status === 0) {
			                alert('Not connect.n Verify Network.');
			            } else if (jqXHR.status == 404) {
			                alert('Requested page not found. [404]');
			            } else if (jqXHR.status == 500) {
			                alert('Internal Server Error [500].');
			            } else if (exception === 'parsererror') {
			                alert('Requested JSON parse failed.');
			            } else if (exception === 'timeout') {
			                alert('Time out error.');
			            } else if (exception === 'abort') {
			                alert('Ajax request aborted.');
			            } else {
			                alert('Uncaught Error.n' + jqXHR.responseText);
			            }		            
			        }
		 });
		 
	}
	
	
</script>


</head>

<body onload="funSetDate()">
	<div id="formHeading">
		<label>Average Ticket Value Report</label>
	</div>
	<s:form name="POSAvgTicketValueReportForm" method="POST" action="frmPOSATV.html?saddr=${urlHits}" target="_blank">

		<br />
		<br />
		<table class="masterTable">

			<tr>
				<td width="140px">POS Name</td>
				<td colspan="3"><s:select id="cmbPOSName" name="cmbPOSName" path="strPOSName" cssClass="BoxW124px" items="${posList}" >
					
				 </s:select></td>
			</tr>
			<tr>
				<td><label>From Date</label></td>
				<td><s:input id="txtFromDate" required="required" path="fromDate" pattern="\d{1,2}-\d{1,2}-\d{4}" cssClass="calenderTextBox"/></td>
			</tr>
			<tr>
				<td><label>To Date</label></td>
				<td><s:input id="txtToDate" required="required" path="toDate" pattern="\d{1,2}-\d{1,2}-\d{4}" cssClass="calenderTextBox"/></td>
			</tr>
			<tr>				
				<td><label>Pos Wise</label></td>
				<td >
						<s:select id="cmbPosWise" path="strPosWise" cssClass="BoxW124px">
					    		<s:option value="NO">NO</s:option>
					    		<s:option value="YES">YES</s:option>
					    		
				    		
				    	</s:select>
					</td>
				</tr>
				<tr>				
				<td><label>Date Wise</label></td>
				<td >
						<s:select id="cmbDateWise" path="strDateWise" cssClass="BoxW124px">
				    		<s:option value="NO">NO</s:option>
				    		<s:option value="YES">YES</s:option>
				    	</s:select>
					</td>
				</tr>
			<tr>				
				<td><label>Report Type</label></td>
				<td >
						<s:select id="cmbDocType" path="strDocType" cssClass="BoxW124px">
				    		<s:option value="PDF">PDF</s:option>
				    		<s:option value="XLS">EXCEL</s:option>
				    		
				    	</s:select>
					</td>
				</tr>
			
			<tr>
			
				
			</tr>
			
			
		</table>
		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button"/> 
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>
	</s:form>

</body>
</html>