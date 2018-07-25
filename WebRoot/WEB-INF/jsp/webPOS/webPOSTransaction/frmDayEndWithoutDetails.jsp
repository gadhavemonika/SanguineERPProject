<%@ page import="org.json.simple.*"  %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s" %>
<%@ taglib uri ="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
        
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Day End Process</title>
</head>
<script type="text/javascript">
		$(documnent).ready(function()
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
			
			 if (DayEnd=="N" && ShiftEnd==("N"))
				 {  
				 		alert('Already Day started');
				 }
			 else{
				var searchurl=getContextPath()+"/startDayProcessWithoutDetails.html?";
				$.ajax({
				        type: "GET",
			    	    url: searchurl,
			        	dataType: "json",
			        
			        	success: function (response) {
			        			//var startday=response.get("DayStart");
			        			//alert(startday);
			        			location.reload();
			        			document.getElementById("btnStart").disabled=true;
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
		var strURL=getContextPath()+"/CheckBillSettleBusyTableWithoutDtl.html?";
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
						funEndDayProcess();	            	
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
	}
	
	function funEndDayProcess()
	{
		var ShiftEnd='${ShiftEnd}';
		var searchurl=getContextPath()+"/blankDayEndDayProcess.html?";
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
	
</script>
<body>

	<div id="formHeading">
		<label>Blank Day End Process</label>
	</div>
	<br/>
	<s:form name="POSBlankDayEndProcess" method="POST" action="BlankDayEndProcess.html?saddr=${urlHits}">
	<table class="masterTable">
	<tr>
		<td align="center">
			<b><label  id="lblShiftNo" style="font-size:15px;">Shift No - 1</label></b>
		</td>
		<td align="center">
			<b><label id="lblDayEndDate" style="font-size: 15px;  text-align: right;">${POSDate}</label></b>
		</td>
	</tr>
	<tr><td></td><td></td></tr>
	<tr><td></td><td></td></tr>
	<tr>
		<td align="center">
			<b><label  id="lblPOSName" style="font-size:15px;"></label></b>
		</td>		
			<td></td>
	</tr>		
	<tr style="height: 350px">
	 <td colspan="2">
	 &emsp;&ensp;&emsp;&ensp;	&emsp;&ensp;&emsp;&ensp;
	 &emsp;&ensp;&emsp;&ensp;	&emsp;&ensp;&emsp;&ensp;
	 &emsp;&ensp;&emsp;&ensp;	&emsp;&ensp;&emsp;&ensp;
	 &emsp;&ensp;&emsp;&ensp;	&emsp;&ensp;&emsp;&ensp;
	 <input id="btnStart" type="button" value="StartDay"  class="form_button1" onclick="funStart()" />
	 				
	 				&emsp;&ensp;&emsp;&ensp;	&emsp;&ensp;&emsp;&ensp;
	 				&emsp;&ensp;&emsp;&ensp;	&emsp;&ensp;&emsp;&ensp;
	 				&emsp;&ensp;&emsp;&ensp;	&emsp;&ensp;&emsp;&ensp;
			
	<input id="btnEndDay" type="button" value="EndDay" class="form_button1" onclick="funEndDay()"/>
	 
	 </td>
	</tr>
	</table>
	</s:form>
		
</body>
</html>