<%@page import="org.json.simple.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="s" %>
    <%@ taglib uri ="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Select Report</title>
</head>
<style>
.ui-autocomplete {
    max-height: 200px;
    overflow-y: auto;
    /* prevent horizontal scrollbar */
    overflow-x: hidden;
    /* add padding to account for vertical scrollbar */
    padding-right: 20px;
</style>
<script type="text/javascript">

	$(document).ready(function() {
		
		 $('#tblDayEndEmailRpt tbody').on('click', 'tr', function () {
		     //   var code = $('td', this).eq(1).value();
		 
			 if($("#tblDayEndEmailRpt tr:eq("+this.rowIndex+") td:eq(1) input:checkbox").is(':checked'))
				{
				 	//alert("check")
				 	$("#tblDayEndEmailRpt tr:eq("+this.rowIndex+") td:eq(1) input:checkbox").prop("checked", true);
				 	$("#tblDayEndEmailRpt tr:eq("+this.rowIndex+") td:eq(1) input:checkbox").prop("value", true);
				 				
				}
		 });
		 
		 var urlHits="${urlHits}";
			if(urlHits>1)
			{
			window.opener.funSetData();
			window.close();
			}
	});
 	 function funLoadAllReports()
	 {
		 var searchURL=getContextPath()+"/loadAllReportsforMail.html";
		 $.ajax({
			 
			 	type: "GET",
		        url: searchURL,
		        dataType: "json",
		        
		        success: function(response) 
		        {
		        	
		        	alert('${loginPOS}');
		       		funRemoveProductRows("tblDayEndEmailRpt");
		           	$.each(response,function(i,item)
		           	{
		           		funfillMailReportTable(response[i].strReportName,response[i].strReportCheck);
		           	
		           	});
		    
		        },	 
	           error : function(jqXHR, exception)
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
	 
	 function funfillMailReportTable(ReportName,CheckedStatus)
	 {
		 	var table = document.getElementById("tblDayEndEmailRpt");
			var rowCount = table.rows.length;
			var row = table.insertRow(rowCount);
			row.insertCell(0).innerHTML= "<input name=\"listMailReport["+(rowCount)+"].strReportName\" readonly=\"readonly\" class=\"Box\" size=\"50%\" id=\"ReportName."+(rowCount)+"\" value='"+ReportName+"'>";
			if(CheckedStatus==true)
			{
				row.insertCell(1).innerHTML= "<input name=\"listMailReport["+(rowCount)+"].strReportCheck\" id=\"chkEmailRpt."+(rowCount)+"\" type=\"checkbox\" checked class=\"GCheckBoxClass\" style=\"text-align:center; size:50% \" value= '"+CheckedStatus+"'>"; // value= '' 
			}
			else{
				row.insertCell(1).innerHTML= "<input name=\"listMailReport["+(rowCount)+"].strReportCheck\" id=\"chkEmailRpt."+(rowCount)+"\" type=\"checkbox\" class=\"GCheckBoxClass\" style=\"text-align:center; size:50%;\" size=\"55%\" value= '"+CheckedStatus+"'>";
			}
	 }
	 function funRemoveProductRows(tableName)
		{
			var table = document.getElementById(tableName);
			var rowCount = table.rows.length;
			while(rowCount>0)
			{
				table.deleteRow(0);
				rowCount--;
			}
		}
	 function funSelectAllChkBox()
		{
			var table = document.getElementById("tblDayEndEmailRpt");
		 	var rowCount = table.rows.length;
		 	if($("#chkReportAllCheck").is(':checked'))
				{
		 		 	for(i=0; i<rowCount; i++)
					 $("#tblDayEndEmailRpt tr:eq("+i+") td:eq(1) input:checkbox").prop("checked", true);
		 		 	$("#tblDayEndEmailRpt tr:eq("+this.rowIndex+") td:eq(1) input:checkbox").prop("value", true);
				}
		 	else{
	 		 	for(i=0; i<rowCount; i++)
					 $("#tblDayEndEmailRpt tr:eq("+i+") td:eq(1) input:checkbox").prop("checked", false);		
	 		 	     $("#tblDayEndEmailRpt tr:eq("+this.rowIndex+") td:eq(1) input:checkbox").prop("value", false);
		 		
		 		}
		 }
			 	 
	function funSelectedReports()
	{
		//alert()
		var status=document.getElementById['frmDayEndMailReportDialog'].submit();
		
		/* if(status=="true")
			{ */
		window.opener.funSetData();
			//}
		window.close();
		//alert("close");
		//window.close();
	}
	 
</script>
<body onload="funLoadAllReports()">
<br>
<br>
	<s:form name="DayEndMailReportDialog" id="frmDayEndMailReportDialog" method="POST" action ="DayEndMailReport.html">
	<table class="masterTable">
	<tr><td></td><td></td></tr>
	<tr><td></td><td>
	&emsp;&ensp;&emsp;&ensp;&emsp;&emsp;&ensp;&emsp;
	&ensp;&emsp;&ensp;&emsp;&ensp;&emsp;&ensp;&emsp;&ensp;
	&emsp;&ensp;&emsp;&ensp;&emsp;&emsp;&ensp;&emsp;&ensp;
	&emsp;&ensp;&emsp;&ensp;&emsp;&ensp;&emsp;&ensp;&emsp;&ensp;&emsp;&ensp;
						
	<label>SELECT ALL</label>
	<s:input  type="checkBox" id="chkReportAllCheck" path="strReportName" onclick="funSelectAllChkBox()"/>
	</td></tr>
	
			<tr>
			<td colspan="2">
			<div id="divReportTables" style="width: 100%; height: 500px;">
				<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
					font-weight: bold;">
					<tr bgcolor="#72BEFC">
						<td align="left" width="10%">
						<label>REPORT NAME</label>
						</td>
						<td align="center" width="10%">SEND EMAIL</td>					
					</tr>
				</table>
				<div style="background-color: #C0E2FE; border: 1px solid #ccc; display: block; height: 80%; margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
					<table id="tblDayEndEmailRpt"
						style="width: 100%; height:90%; border: #0F0; table-layout: fixed; overflow: scroll"
						class="transTablex col11-center">
						<tbody>
							<col style="width:30%">					
							<col style="width:10%;">
						</tbody>
					</table>
				</div>
			</div>
			</td>
			</tr>
			<tr>
			<td>
				<!-- <label id="lblReportsNo" style="display:inline-block; width:100px" > No Of Reports </label> -->
			</td>
			<td align="center">
			&emsp;&ensp;&emsp;&ensp;&emsp;&emsp;&ensp;&emsp;
			&ensp;&emsp;&ensp;&emsp;&ensp;&emsp;&ensp;&emsp;
			&ensp;&emsp;&ensp;&emsp;&ensp;&emsp;&ensp;&emsp;
				
			<input id="btnEmailReports" type="submit" value="SEND EMAIL"  onclick="return funSelectedReports();" class="form_button1" /><!-- onclick="funSelectedReports();" window.close();-->
			
			</td>
			</tr>
			<tr><td></td><td></td></tr>
	</table>
	</s:form>

</body>
</html>