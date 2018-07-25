<%@ page import="org.json.simple.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Mail Day End Report</title>
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
	$(document).ready(function()
		{
		 var urlHits="${urlHits}";
			if(urlHits>1)
			{
				window.opener.funSetData();
				window.close();
			}
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
			
	function funLoadAllReports()
	 {
		 var searchURL=getContextPath()+"/loadAllMailDayEndReports.html";
		 $.ajax({
			 
			 	type: "GET",
		        url: searchURL,
		        dataType: "json",
		        
		        success: function(response) 
		        {
		        	
		        	//alert('${loginPOS}');
		       		funRemoveProductRows("tblDayEndEmailRpt");
		           	$.each(response,function(i,item)
		           	{
		           		funfillMailReportTable(response[i].strReportName,response[i].strReportCheck);
		           	
		           	});
		           	
		           	funNeedMailreport();
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
	 function funNeedMailreport()
	 {
		 emailReport = confirm("Do you want Email Report ?");
		if( emailReport == true )
         {
			var email="yes";
			$("#txtmailReport").val(email);
        	 //$('mailReport').val('true');
        	// document.getElementById("mailReport").value = "true";
         }else{
        	 //$('#mailReport').val('false');
        	 var email="no";
        		$("#txtmailReport").val(email);
        	 //document.getElementById("mailReport").value = "false";
         }
	 }
</script>
<body onload="funLoadAllReports()">
<br>
<br>
	<s:form name="frmMailDayEndReport" id="frmMailDayEndReport" method="POST" action ="MailDayEndReport.html">
	<table class="masterTable">
	<tr><td></td><td></td></tr>
	<tr>
	<td><label>From Date</label>&emsp;&ensp;&emsp;&ensp;
	<s:input id="txtFromDate" required="required" path="fromDate" pattern="\d{1,2}-\d{1,2}-\d{4}" cssClass="calenderTextBox"/>
	</td>
	 <td><label>To Date</label>&emsp;&ensp;&emsp;&ensp;
		<s:input id="txtToDate" required="required" path="toDate" pattern="\d{1,2}-\d{1,2}-\d{4}" cssClass="calenderTextBox"/></td>
	<tr><td></td><td></td></tr>
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
				<s:input type="hidden" id="txtmailReport" path="mailReport" value="$(#txtmailReport)"/>
			</td>
			<td align="center">
			&emsp;&ensp;&emsp;&ensp;&emsp;&emsp;&ensp;&emsp;
			&ensp;&emsp;&ensp;&emsp;&ensp;&emsp;&ensp;&emsp;
			&ensp;&emsp;&ensp;&emsp;&ensp;&emsp;&ensp;&emsp;
				
			<input id="btnEmailReports" type="submit" value="SEND EMAIL" class="form_button1" /><!-- return onclick="funNeedMailreport();"  onclick="funSelectedReports();" window.close();-->
			</td>
			</tr>
			<tr><td></td><td>
			</td></tr>
	</table>
	</s:form>
</body>
</html>