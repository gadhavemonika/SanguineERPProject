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
    	
    	$(function() 
    			{ 
			    	$("#txtLocCode").val("${locationCode}");
			    	$("#lblLocName").text("${locationName}");  
			    	
			    	var startDate="${startDate}";
					var arr = startDate.split("/");
					Date1=arr[0]+"-"+arr[1]+"-"+arr[2]; 
			    	$("#txtFromDate").datepicker({ dateFormat: 'dd-mm-yy' });
					$("#txtFromDate" ).datepicker('setDate', Date1);
					
					$("#txtToDate").datepicker({ dateFormat: 'dd-mm-yy' });
					$("#txtToDate" ).datepicker('setDate', 'today');
    			});
    	
    	
    	function funHelp(transactionName)
		{
			fieldName=transactionName;
		//	 window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
			 window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:1000px;dialogLeft:200px;")
	    }
    	
		/**
		* Get and set Location Data Passing value(Location Code)
	    **/
		function funSetLocation(code)
		{
			var searchUrl="";
			searchUrl=getContextPath()+"/loadLocationMasterData.html?locCode="+code;			
			$.ajax({
			        type: "GET",
			        url: searchUrl,
				    dataType: "json",
				    success: function(response)
				    {
				    	if(response.strLocCode=='Invalid Code')
				       	{
				       		alert("Invalid Location Code");
				       		$("#txtLocCode").val('');
				       		$("#lblLocName").text("");
				       		$("#txtLocCode").focus();
				       	}
				       	else
				       	{
				    	$("#txtLocCode").val(response.strLocCode);
		        		$("#lblLocName").text(response.strLocName);	
		        		$("#txtProdCode").focus();
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
		
		function funCallFormAction() 
		{	
			if($("#txtLocCode").val()=='')
				{
					return false;
				}
			
			/* else
					{
					var reportView =$("#cmbReportView").val();
					if(reportView=='Recipe')
						{
							document.stkAdjSlip.action = "rptStockAdjustmentMonthly.html";
							document.stkAdjSlip.submit();
						}else
							{
								document.stkAdjSlip.action = "rptStockAdjustmentMonthlyList.html";
								document.stkAdjSlip.submit();
							}
					} */
			
		}
    
    </script>
  </head>
  
	<body >
	<div id="formHeading">
		<label>Stock Adjustment Report</label>
	</div>
	<br />
	<br />
		<s:form name="stkAdjSlip" method="GET" action="rptStockAdjustmentMonthly.html" target="_blank">
			<table class="masterTable">
	<tr><th colspan="4"></th></tr>
				<tr>
					<td width="150px"><label>From Date</label></td>
					<td><s:input  id="txtFromDate" path="dtFromDate"  cssClass="calenderTextBox" /></td>
					<td width="150px"><label>To Date</label></td>
					<td><s:input  id="txtToDate" path="dtToDate" cssClass="calenderTextBox"  /></td>
				
				</tr>
				<tr>
					<td><label>Location</label></td>
					<td>
						<s:input id="txtLocCode" path="strLocationCode" cssClass="searchTextBox" ondblclick="funHelp('locationmaster')" ></s:input>
					</td>
					<td colspan="2">
					<label id="lblLocName"></label>
					</td>
				</tr>
				<tr>
				<td><label>Stock Adjustment From</label></td>
					<td>
					<s:select id="cmbReportType" path="strReportType" cssClass="BoxW124px">
				    		<s:option value="POS">POS</s:option>
				    		<s:option value="Physical Stock">Physical Stock</s:option>
				    		<s:option value="Manually">Manually</s:option>
				    	</s:select>
					</td>
					
				<td><label>Report view</label></td>
					<td>
					<s:select id="cmbReportView" path="strReportView" cssClass="BoxW124px">
				    		<s:option value="Recipe">Recipe Wise</s:option>
				    		<s:option value="List">List Wise</s:option>
				    		
				    		
				    	</s:select>
					</td>	
				
				
				</tr>
				
				<tr>
					<td><label>Report Type</label></td>
					<td colspan="3">
						<s:select id="cmbDocType" path="strDocType" cssClass="BoxW124px">
				    		<s:option value="PDF">PDF</s:option>
				    		<s:option value="XLS">EXCEL</s:option>
				    	</s:select>
					</td>
				</tr>
			</table>
			<br>
			<p align="center">
				<input type="submit" value="Submit"   class="form_button"/>
				 <input type="button" value="Reset" class="form_button"  onclick="funResetFields()"/>
			</p>
			
		</s:form>
	</body>
</html>