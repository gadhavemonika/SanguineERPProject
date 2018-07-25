<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Group Sub-Group Report</title>
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
	 
		var POSDate="${POSDate}"
		    var startDate="${POSDate}";
		  	var Date = startDate.split(" ");
			var arr = Date[0].split("-");
			Dat=arr[2]+"-"+arr[1]+"-"+arr[0];	
			$("#txtFromDate" ).datepicker({ dateFormat: 'dd-mm-yy' });		
			$("#txtFromDate" ).datepicker('setDate', Dat); 
			$("#txtToDate").datepicker({ dateFormat: 'dd-mm-yy' });
			$("#txtToDate" ).datepicker('setDate', Dat);  
    				
    				
    			}); 


	/**
	* Reset The Group Name TextField
	**/
	
	
</script>
<body >
	<div id="formHeading">
		<label>Group Wise Sales Report</label>
	</div>
	<s:form name="POSGroupSubGroupReport" method="POST" action="rptGroupSubGroupWiseReport.html?saddr=${urlHits}" target="_blank">

		<br />
		<br />
		<table class="masterTable">

			<tr>
				<td width="140px">POS Name</td>
				<td ><s:select id="cmbPOSName" name="cmbPOSName" path="strPOSName" cssClass="BoxW124px" items="${posList}" >
				 </s:select></td>
				<td width="140px">Group Name</td>
				<td colspan="3"><s:select id="cmbGroupName" name="cmbGroupName" path="strGroupName" cssClass="BoxW124px" items="${listGroupName}" >
					
				 </s:select></td>
			</tr>
			<tr>
				<td><label>From Date</label></td>
				<td><s:input id="txtFromDate" required="required" path="fromDate" pattern="\d{1,2}-\d{1,2}-\d{4}" cssClass="calenderTextBox"/></td>
				<td><label>To Date</label></td>
				<td><s:input id="txtToDate" required="required" path="toDate" pattern="\d{1,2}-\d{1,2}-\d{4}" cssClass="calenderTextBox"/></td>
			
			</tr>
			<tr>
				<td><label>Sub Group</label></td>
				<td><s:select id="cmbSGName" name="cmbSGName" path="strSGName" cssClass="BoxW124px" items="${listSubGroupName}" ></s:select></td>
				
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