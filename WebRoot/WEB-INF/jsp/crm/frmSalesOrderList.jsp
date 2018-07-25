<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sales Order</title>

<script type="text/javascript">
		
$(document).ready(function() 
		{		
			$(".tab_content").hide();
			$(".tab_content:first").show();
	
			$("ul.tabs li").click(function() {
				$("ul.tabs li").removeClass("active");
				$(this).addClass("active");
				$(".tab_content").hide();
				var activeTab = $(this).attr("data-state");
				$("#" + activeTab).fadeIn();
			});
			
				var startDate="${startDate}";
				var arr = startDate.split("/");
				Dat=arr[2]+"-"+arr[1]+"-"+arr[0];
			    $("#txtFromDate").datepicker({ dateFormat: 'yy-mm-dd' });
				$("#txtFromDate" ).datepicker('setDate', Dat);
				$("#txtFromDate").datepicker();	
				
				$("#txtToDate").datepicker({ dateFormat: 'yy-mm-dd' });
				$("#txtToDate" ).datepicker('setDate', 'today');
				$("#txtToDate").datepicker();	
					
				$("#txtFromFulfillment").datepicker({ dateFormat: 'yy-mm-dd' });
				$("#txtFromFulfillment" ).datepicker('setDate', Dat);
				$("#txtFromFulfillment").datepicker();	
						
						
				$("#txtToFulfillment").datepicker({ dateFormat: 'yy-mm-dd' });
				$("#txtToFulfillment" ).datepicker('setDate', 'today');
				$("#txtToFulfillment").datepicker();	
							
		});
		

		
		
		function funHelp(transactionName)
		{
			fieldName = transactionName;
		//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;top=500,left=500")
			
		}

		 	function funSetData(code)
			{
				switch (fieldName)
				{
					    	
				    case 'custMaster' :
				    	funSetCuster(code);
				    	break;
				        
				}
			}
		 	
		 	function funSetCuster(code)
			{
				gurl=getContextPath()+"/loadPartyMasterData.html?partyCode=";
				$.ajax({
			        type: "GET",
			        url: gurl+code,
			        dataType: "json",
			        success: function(response)
			        {		        	
			        		if('Invalid Code' == response.strPCode){
			        			alert("Invalid Customer Code");
			        			$("#txtPartyCode").val('');
			        			$("#txtPartyCode").focus();
			        			
			        		}else{			   
			        			$("#txtPartyCode").val(response.strPCode);
								$("#lblPartyName").text(response.strPName);
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

	
	
	
	function funCallFormAction(actionName,object) 
	{
			
		if ($("#txtPartyCode").val()=="") 
		    {
			 alert('Invalid Date');
			 $("#txtPartyCode").focus();
			 return false;  
		   }
		
		if ($("#txtFromDate").val()=="") 
	    {
		 alert('Invalid Date');
		 $("#txtFromDate").focus();
		 return false;  
	   }
		
		if ($("#txtToDate").val()=="") 
	    {
		 alert('Invalid Date');
		 $("#txtToDate").focus();
		 return false;  
	   }	
		
		if ($("#txtFromFulfillment").val()=="") 
	    {
		 alert('Invalid Date');
		 $("#txtFromFulfillment").focus();
		 return false;  
	   }	
		
		if ($("#txtToFulfillment").val()=="") 
	    {
		 alert('Invalid Date');
		 $("#txtToFulfillment").focus();
		 return false;  
	   }	
		
		
		
	  else
		{
			return true;
			
		}
	}
	
	$(function()
			{
				$("#txtPartyCode").blur(function() 
						{
							var code=$('#txtPartyCode').val();
							if(code.trim().length > 0 && code !="?" && code !="/")
							{
								funHelp(code);
							}
						});
				
			});
		
</script>

</head>
<body onload="funOnLoad();">
	<div id="formHeading">
		<label>Sales Order List</label>
	</div>
	<s:form name="SalesOrderList" method="GET"
		action="rptSalesOrderList.html" >
		<input type="hidden" value="${urlHits}" name="saddr">
		<br>
		<table class="transTable">
		
							<tr>
							<td width="100px"><label>From Date</label>
									<td><s:input path="dtFromDate" id="txtFromDate"
											 required="required"
											cssClass="calenderTextBox" /></td>
							<td width="100px"><label>To Date</label>
									<td><s:input path="dtToDate" id="txtToDate"
											 required="required"
											cssClass="calenderTextBox" /></td>
											
							</tr>
							<tr>
									<td width="100px"><label>Customer Code</label></td>
									<td colspan="3"><s:input path="strDocCode" id="txtPartyCode"
											ondblclick="funHelp('custMaster')"
											cssClass="searchTextBox" />&nbsp;&nbsp;&nbsp;&nbsp;
									<label id="lblPartyName"></label>	</td>
											
																										
							</tr>
							<tr>
								<td width="100px"><label>Fulfillment Date From</label>
									<td><s:input path="dteFromFulfillment" id="txtFromFulfillment"
											 required="required"
											cssClass="calenderTextBox" /></td>
											
								<td width="100px"><label>Fulfillment Date To</label>
									<td><s:input path="dteToFulfillment" id="txtToFulfillment"
											 required="required"
											cssClass="calenderTextBox" /></td>
								</tr>
								<tr>
									<td><label>Report Format</label></td>
								<td ><s:select id="cmbDocType" path="strDocType"
										cssClass="BoxW124px">
										<s:option value="PDF">PDF</s:option>
										<s:option value="XLS">EXCEL</s:option>
										<s:option value="HTML">HTML</s:option>
										<s:option value="CSV">CSV</s:option>
									</s:select></td>
									
								<td><label>Type</label></td>
								<td ><s:select id="cmbType" path="strReportType"
										cssClass="BoxW124px">
										<s:option value="Summary">Summary</s:option>
										<s:option value="Detail">Detail</s:option>
									</s:select></td>	
								
								
								</tr>
								

		</table>
		<br>
		<p align="center">
			<input type="submit" value="Submit"
				onclick="return funCallFormAction('submit',this)"
				class="form_button" /> &nbsp; &nbsp; &nbsp; <a
				STYLE="text-decoration: none"
				href="frmSalesOrderList.html?saddr=${urlHits}"><input
				type="button" id="reset" name="reset" value="Reset"
				class="form_button" /></a>
		</p>
		<br>
		<div id="wait"
			style="display: none; width: 60px; height: 60px; border: 0px solid black; position: absolute; top: 60%; left: 55%; padding: 2px;">
			<img
				src="../${pageContext.request.contextPath}/resources/images/ajax-loader-light.gif"
				width="60px" height="60px" />
		</div>
	</s:form>
	<script type="text/javascript">
		
	</script>
</body>
</html>