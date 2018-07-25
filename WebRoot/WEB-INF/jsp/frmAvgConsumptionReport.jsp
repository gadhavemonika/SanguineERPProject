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
    
    $(document).ready(function(){
    	var startDate="${startDate}";
		var arr = startDate.split("/");
		Date1=arr[0]+"-"+arr[1]+"-"+arr[2];
		$("#dteFromDate").datepicker({
			dateFormat : 'dd-mm-yy'
		});
		$("#dteFromDate").datepicker('setDate', Date1);	
		
		
		$("#dteToDate").datepicker({
			dateFormat : 'dd-mm-yy'
		});
		$("#dteToDate").datepicker('setDate', 'today');	
		
		var code='<%=session.getAttribute("locationCode").toString()%>';
		funSetLocation(code);
	});
    
    
	function funCallFormAction(actionName,object) 
	{	
		var locCode = $("#txtLocCode").val();
		var formDate =$('#dteFromDate').val();
		var toDate =$('#dteToDate').val();
		var exportType= $('#cmbDocType').val();
		if($("#cmbDocType").val()=="XLS")
    	{
			window.open(getContextPath()+"/loadExcelAvgConsumptionReport.html?formDate="+formDate +"&toDate="+toDate +"&locCode="+locCode,'_blank') ;
    	}
		if($("#cmbDocType").val()=="PDF")
    	{
			window.open(getContextPath()+"/rptAvgConsumptionReport.html?formDate="+formDate +"&toDate="+toDate  +"&locCode="+locCode,'_blank') ;
    	}
	}
	
	
	function funSetLocation(code)
	{
		$.ajax({
		        type: "GET",
		        url: getContextPath()+"/loadLocationMasterData.html?locCode="+code,
		        dataType: "json",
		        success: function(response)
		        {
			       	if(response.strLocCode=='Invalid Code')
			       	{
			       		alert("Invalid Location Code");
			       		$("#txtLocCode").val('');
			       	}
			       	else
			       	{
			       		$("#txtLocCode").val(response.strLocCode);
				       	$("#lblLocName").text(response.strLocName);
				       
			       	}
			    },
			    error: function(jqXHR, exception) {
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
	
	function funSetData(code)
	{			
		switch (fieldName) 
		{			   
		   case 'locationmaster':
		    	funSetLocation(code);
		        break;
		}
	}	
	
	function funHelp(transactionName)
	{
		fieldName=transactionName;
		
		//window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	}
	
	
       
    </script>
  </head>
  
	<body >
	<div id="formHeading">
		<label>Average Consumption</label>
	</div>
	<br />
	<br />
		<s:form name="frmAvgConsumptionReport" method="GET" action="" >
			<table class="masterTable">
	<tr><th colspan="5"></th></tr>
				<tr>
				<td><label>From Date</label></td>
				<td><s:input type="text" id="dteFromDate" path="dteFromDate" required="true" class="calenderTextBox" /></td>
				<td><label>To Date</label></td>
				<td colspan="2"><s:input type="text" id="dteToDate" path="dteToDate" required="true" class="calenderTextBox" /></td>				
			</tr>
				
				<tr>
				<td><label>Location Code</label></td>
				<td><s:input id="txtLocCode" name="txtLocCode" path="strLocationCode" ondblclick="funHelp('locationmaster')"  cssClass="searchTextBox"/></td>
				<td><label id="lblLocName"></label></td>
				
				
				
					<td><label>Report Type</label></td>
					<td>
						<s:select id="cmbDocType" path="strDocType" cssClass="BoxW124px">
				    		<s:option value="PDF">PDF</s:option>
				    		<s:option value="XLS">EXCEL</s:option>
				    	
				    	</s:select>
					</td>
				</tr>
				
				<tr>
				<td colspan="5"></td>
					<!-- <td><input type="submit" value="Submit" /></td>
					<td><input type="reset" value="Reset" onclick="funResetFields()"/></td>	 -->				
				</tr>
			</table>
			<br>
			<p align="center">
				<input type="submit" value="Submit"  class="form_button" onclick="return funCallFormAction('submit',this)" />
				 <input type="button" value="Reset" class="form_button"  onclick="funResetFields()"/>
			</p>
			
		</s:form>
	</body>
</html>