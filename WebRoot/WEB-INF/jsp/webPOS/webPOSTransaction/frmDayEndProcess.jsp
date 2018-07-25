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
<title>Day End Process</title>
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
var emailReport,ReportWindow;

	$(function()
		{
			var POSDate="${POSDate}"
			$('#lblDayEndDate').html(POSDate);
		
			$('#lblTotalPax').val("${command.totalpax}");
		}
	);
	
	$(document).ready(function()
			{
		
			var gDayEnd='${DayEnd}'; /*BCOZ in pos global veriale DayEnd initilize as N '${gDayEnd}'; */
			var gShiftEnd='${ShiftEnd}';
			
				if (gDayEnd==("") && gDayEnd==("N"))
		        {
					 ('#btnEnd').disabled=disabled;  
					document.getElementById("btnEnd").disabled=true;
					
				}
		        else if (gDayEnd==("N") && gShiftEnd==("N"))
		        {
		        	 ('#btnStart').disabled=disabled; 
		        	document.getElementById("btnStart").disabled=true;
		        }
			});
	
	
	function funStart()
	{
		
		var DayEnd='${DayEnd}'  /*BCOZ in pos global veriale DayEnd initilize as N '${gDayEnd}'; */
		var ShiftEnd='${ShiftEnd}';
		//alert('DayEndShiftEnd');
		
		 if (DayEnd=="N" && ShiftEnd==("N"))
			 {  
			 		alert('Already Day started');
			 }
		 else{
			var searchurl=getContextPath()+"/StartDayProcess.html?";
			$.ajax({
			        type: "GET",
		    	    url: searchurl,
		        	dataType: "json",
		        
		        	success: function (response) {
		        			//var startday=response.get("DayStart");
		        			//alert(startday);
		        			document.getElementById("btnStart").disabled=true;
		        			location.reload();
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
		 
		 
	}
	function funEndDay()
	{
		var strPOSCode='${loginPOS}';
		var ShiftNo="1";
		var POSDate="${POSDate}"
		var strURL=getContextPath()+"/CheckBillSettleBusyTable.html?";

		$.ajax({
			type: "GET",
    	    url: strURL,
        	dataType: "json",
			
			success :function(response)
			{		
				
					var checkBills='Y';
					var checkTables='Y';
					
					if(response.PendingBills==true)
						{
						  alert('Please settle pending bills');	
						}
					else{
							checkBills='N';//funEndDayProcess();
						}
					if(response.BusyTables==true)
						{
							alert('Sorry Tables are Busy Now');	
						}
					else{
							checkTables='N';//funEndDayProcess();
						} 
					
					if(checkBills=='N' && checkTables=='N')
						{
							 emailReport = confirm("Do you want Email Report ?");
							
			                if( emailReport == true )
			                {
			                	emailReport="Y";
			                	//window.open(url, windowName, windowFeatures, optionalArg4)
			                	ReportWindow= window.open("frmPOSDayEndDialog.html","","dialogHeight:400px;dialogWidth:400px;dialogLeft:400px;")
			                	
			                	//var list=response.
			                	//alert(response);
			                	//funEndDayProcess(emailReport);
			                	//document.write ("User wants Email Report!");
			                  	return true;
			               	}
			                else{
			                	emailReport="N";
			                	funEndDayProcess(emailReport);
			                }
			            	
						}
			
		 
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
		
		//funEndDay();
	}
	function funEndDayProcess(emailReport)
	{
		var ShiftEnd='${ShiftEnd}';
		var searchurl=getContextPath()+"/EndDayProcess.html?emailReport="+emailReport;
		$.ajax({
		        type: "GET",
	    	    url: searchurl,
	        	dataType: "json",
	        
	        	success: function (response) {
				
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

	function funSetData()
	{
		ReportWindow.close();
		funEndDayProcess(emailReport);	
		
		/* var rowCount = table.rows.length;
		alert(rowCount)
	//	var row=table.rows[0].cell.item(0).innerHTML;
		 for(i=0; i<rowCount; i++)
		{
		
			var row=table.rows[i].cells[1].lastElementChild.defaultValue;
			//alert(row)			 
		}
			 //	 $("table tr:eq(0) td:eq(0) input:text").val();
			 //alert(table); 
		 */
		
	}
	</script>
	
<body> <!--  onload="funLoadAllData()"> -->
<br/>
	
	<div id="formHeading">
		<label>Day End Process</label>
	</div>
	<br/>
			<s:form name="POSDayEndProcess" method="POST" action="DayEndProcess.html?saddr=${urlHits}">
			<table class="masterTable">
			<tr>
				<td align="center">
				<b><label  id="lblShiftNo" style="font-size:15px;">Shift No - 1</label></b>
				</td>
				<td align="center">
				<b><label id="lblDayEndDate" style="font-size: 15px;  text-align: right;"></label></b>
				</td>
			</tr>
			<tr>
			<td></td><td></td>
			</tr>
			<tr>
			<td></td><td></td>
			</tr>
			<tr>
			<td colspan="2">
			<div id="divDayEnd" style="width: 100%; height: 200px;">
				<table class="transTablex" style="height: 20px; border: #0F0;width: 99%;font-size:11px;
					font-weight: bold; table-layout: fixed; overflow: scroll" >
					<tr bgcolor="#72BEFC">
						<td width="9%">Settlement Mode</td>					
						<td align ="right" width="9%">Cash(Sales)</td>	
						<td align ="right" width="9%">Float</td>
						<td align ="right" width="9%">TransIn</td>
						<td align ="right" width="9%">Advance</td>
						<td align ="right" width="9%">TotalRec</td>
						<td align ="right" width="9%">Payments</td>
						<td align ="right" width="9%">TransOuts</td>
						<td align ="right" width="9%">Withdraw</td>
						<td align ="right" width="9%">TotalPay</td>
						<td align ="right" width="9%">CashInHand</td>
			 		 </tr>
				</table>
				<div style="background-color: #C0E2FE; border: 1px solid #ccc; height: 90px; margin: auto; 
				overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
					<table id="tblDayEnd" style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
						class="transTablex">
							<c:forEach var="obj"  items="${command.jArrDayEnd}" varStatus="">
							 <tr>
							<c:forEach var="ob" items="${obj}" varStatus="">
							<td align ="right">${ob}</td> 
								</c:forEach>
							</tr> 
						</c:forEach>
					
						
					</table>
				</div>
				&emsp;&ensp;&emsp;&ensp; 
				&emsp;&ensp;&emsp;&ensp;
				<div style="background-color: #C0E2FE; border: 1px solid #ccc; height: 50px; 
				margin: auto; overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
				<table id="tblDayEndTotal"
					style="width: 100%; height: 50%; border: #0F0; table-layout: fixed; overflow: scroll"
					class="masterTable">
					<c:forEach var="obj1"  items="${command.jArrDayEndTotal}" varStatus="">
						<tr>
							<c:forEach var="ob1"  items="${obj1}" varStatus="">
								<td align ="right">${ob1}</td>
									</c:forEach>
							</tr>
						</c:forEach>
				</table>
			</div>
			</div>
		</tr>
		<tr>
		<td colspan="2"></td>
		</tr>
		<tr>
			<td>
				<div id="divSettlement" style="width: 100%; height: 120px;">
					<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
					font-weight: bold; table-layout: fixed; overflow: scroll" class="transTablex" >
					<tr bgcolor="#72BEFC">
						<td width="9%">Settlement Mode</td>					
						<td align ="right" width="9%">Amount</td>	
						<td align ="right" width="9%">No Of Bills</td>
					</tr>
					</table>
					<div style="background-color: #C0E2FE; border: 1px solid #ccc; height: 80px; margin: auto; 
					overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
					<table id="tblSettlement" style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
						class="transTablex">
						<c:forEach var="obj2"  items="${command.jArrSettlement}" varStatus="">
						<tr>
							<c:forEach var="ob2"  items="${obj2}" varStatus="">
								<td align ="right">${ob2}</td>
									</c:forEach>
							</tr>
						</c:forEach>
						</table>
					</div>
				</div>
			</td>
			<td>
			&emsp;&ensp;&emsp;&ensp; 
			&emsp;&ensp;&emsp;&ensp;
			&emsp;&ensp;&emsp;&ensp;
				<div id="divSettlementTotal" style="width: 100%; height: 130px;">
					<div style="background-color: #C0E2FE; border: 1px solid #ccc; height: 80px; margin: auto; 
					overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
					<table id="tblSettlementTotal" style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
						class="transTablex">
						<tr></tr>
						<c:forEach var="obj3"  items="${command.jArrSettlementTotal}" varStatus="">
						<tr>
							<c:forEach var="ob3"  items="${obj3}" varStatus="">
								<td align ="right">${ob3}</td>
									</c:forEach>
							</tr>
						</c:forEach>
						</table>
					</div>
				</div>
			</td>
		</tr>
		<tr>
		<td colspan="2"></td>
		</tr>
		<tr>
			<td>
				<b><label style="font-size:15px; align:center">Sales Under Progress</label></b>
				<div id="divSalesUP" style="width: 100%; height: 155px;">
					<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
					font-weight: bold; table-layout: fixed; overflow: scroll" class="transTablex" >
					<tr bgcolor="#72BEFC">
						<td align ="right" width="9%">Table Name</td>					
						<td align ="right" width="9%">Amount</td>	
					</tr>
					</table>
					<div style="background-color: #C0E2FE; border: 1px solid #ccc; height: 125px; margin: auto; 
					overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
					<table id="tblSalesUP" style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
						class="transTablex">
						<c:forEach var="obj4"  items="${command.jArrSalesInProg}" varStatus="">
						<tr>
							<c:forEach var="ob4"  items="${obj4}" varStatus="">
								<td align ="right">${ob4}</td>
							</c:forEach>
									<%-- <td>${obj4}</td> --%>
							</tr>
						</c:forEach>
						</table>
					</div>
				</div>
			</td>
			<td>
			<b><label style="font-size:15px; align:center;"> UnSettle Bills</label></b>
					<div id="divUnsettleBill" style="width: 100%; height: 155px;">
						<table style="height: 20px; border: #0F0;width: 99%;font-size:11px;
						font-weight: bold; table-layout: fixed; overflow: scroll" class="transTablex" >
						<tr bgcolor="#72BEFC">
							<td align ="right" width="9%">No Of Bills</td>					
							<td align ="right" width="9%">Table Name</td>	
							<td align ="right" width="9%">Bill Amount</td>
						</tr>
						</table>
					<div style="background-color: #C0E2FE; border: 1px solid #ccc; height: 125px; margin: auto; 
						overflow-x: hidden; overflow-y: scroll; width: 99.80%;">
						<table id="tblUnsettleBill" style="width: 100%; border: #0F0; table-layout: fixed; overflow: scroll"
							class="transTablex">
							<c:forEach var="obj5"  items="${command.jArrUnSettlebill}" varStatus="">
						<tr>
							<c:forEach var="ob5"  items="${obj5}" varStatus="">
								<td align ="right">${ob5}</td>
									</c:forEach>
							</tr>
						</c:forEach>
						</table>
					</div>
				</div>
			</td>
		</tr>
		<tr>
		<td>&emsp;&ensp;&emsp;&ensp; 
				&emsp;&ensp;&emsp;&ensp;
			<b><label style="font-size:15px; align:center;"> Total </label>&emsp;&ensp;&emsp;&ensp;
			<label id="lblTotal" style="font-size:15px; align:center;">${command.total} </label>
			</b> 
		</td>
		<td>&emsp;&ensp;&emsp;&ensp; 
				&emsp;&ensp;&emsp;&ensp;
			<b><label style="font-size:15px; align:center;"> Total Pax </label>&emsp;&ensp;&emsp;&ensp;
			<label id="lblTotalPax" style="font-size:15px; align:center;">${command.totalpax}</label>
			</b> 
		</td>
		</tr>
		<tr><td colspan="2"></td>
		</tr>
		<tr>
		<td></td>
		<td>
			&emsp;&ensp;&emsp;&ensp; 
			&emsp;&ensp;&emsp;&ensp;
			<input id="btnStart" type="button" value="START"  class="form_button1" onclick="funStart()" />
				
				&emsp;&ensp;&emsp;&ensp;	&emsp;&ensp;&emsp;&ensp;
			
			<input id="btnEndDay" type="button" value="EndDay" class="form_button1" onclick="funEndDay()"/>
			
			
			
			<!-- <input id="btnEnd"  type="button" value="  End"   onclick="funEnd()" style="height:30px; background: url(./resources/images/big2.png) no-repeat;
				 background-size: 90px 30px;width: 90px;color: #fff;font-size: 13px; font-weight: normal;" /> -->
		</td>
		</tr>
		</table>
					
		
	</s:form>

</body>
</html>