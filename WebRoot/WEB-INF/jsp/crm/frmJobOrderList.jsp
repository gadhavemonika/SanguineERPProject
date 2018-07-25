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
				
			
		});
		
	 	

		 	function funCallFormAction(actionName,object) 
			{
					
							
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
				
				
				
			  else
				{
					return true;
					
				}
			}
		 	
		 	 
		 	 
		 	function funShowSOFieled()
			{
				
				var agianst = $("#cmbAgainst").val();
		
// 				if(agianst=="Sales Projection")
// 					{
// 					document.all["txtSOCode"].style.display = 'block';
// 					document.all["btnFill"].style.display = 'block';
					
// 					}else
// 						{
// 						document.all["txtSOCode"].style.display = 'none';
// 						document.all["btnFill"].style.display = 'none';
// 						}
				
				
				switch(agianst)
	             {
	             case'Direct':
           	         document.getElementById('lblAgainstCode').innerHTML = ''
	            	 document.all["txtSOCode"].style.display = 'none';
// 	            	 document.all["lblAgainstCode"].style.display = 'none';
	            	 break;
	             
	             case'Sales Order':

	            	 document.all["txtSOCode"].style.display = 'block';
 	            	 document.all["lblAgainstCode"].style.display ='block';
             		 document.getElementById('lblAgainstCode').innerHTML = 'Sales Order Code';

 	            	 
 	                 break;
	            		
	            	 
	             case'Production Order':
	            	 document.all["lblAgainstCode"].style.display ='block';
	            	 document.getElementById('lblAgainstCode').innerHTML = 'Production Order  Code';
	            	 document.all["txtSOCode"].style.display = 'block';

    	        	 break;
	            	 
	             case'default':
	            	 document.getElementById('lblAgainstCode').innerHTML = 'none'
	            	 document.all["txtSOCode"].style.display = 'none';
	            	 
            		 break;
	            	 
	             }
			}
		 	
		 	
           function funHelp(transactionName)
			{
				fieldName = transactionName;
			//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
			window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")	
			}
		 	
		 	
		 	function funSetData(code)
			{
				switch (fieldName)
				{
                 case 'salesorder' :
					  $('#txtSOCode').val(code);
					  break;
					  
				 case 'ProductionOrder':
					 $('#txtSOCode').val(code);
					  break;
				        
				}
			}
		 	function funHelpAgainstCode()
		 	{
		 		var against=$("#cmbAgainst").val();
		 		if(against=="Sales Order")
		 			{
		 			funHelp('salesorder');
		 			
		 			}
		 		else
	 			{
	 			funHelp('ProductionOrder');
	 			
	 			}
		 		
		 	}
		 	
			
</script>

</head>
<body onload="funOnLoad();">
	<div id="formHeading">
		<label>Job Order List</label>
	</div>
	<s:form name="JobOrderList" method="GET"
		action="rptJobOrderList.html" >
		<input type="hidden" value="${urlHits}" name="saddr">
		<br>
		<table class="transTable">
								
								<tr>
							<td><label>From Date</label>
									<td><s:input path="dtFromDate" id="txtFromDate"
											 required="required"
											cssClass="calenderTextBox" /></td>
							<td ><label>To Date</label>
									<td><s:input path="dtToDate" id="txtToDate"
											 required="required"
											cssClass="calenderTextBox" /></td>
											
							</tr>
								
							<tr>
			                <td  ><label>Against</label></td>
							<td><s:select id="cmbAgainst" path="strAgainst"
											items="${againstList}" cssClass="BoxW124px" onchange="funShowSOFieled()"/></td>
						 
						 
						 	<td><label>Export Type</label></td>
								<td ><s:select id="cmbType" path="strDocType"
										cssClass="BoxW124px">
										<s:option value="PDF">PDF</s:option>
										<s:option value="EXCEL">EXCEL</s:option>

									</s:select></td>
						 </tr>
							<tr>
							<td ><label id="lblAgainstCode" style="display:none"></label>
							<td colspan="3"><s:input id="txtSOCode" path="strSOCode"
											ondblclick="funHelpAgainstCode()" style="display:none" class="searchTextBox"></s:input></td>
						
						</tr>
															
					

		</table>
		<br>
		<p align="center">
			<input type="submit" value="Submit"
				onclick="return funCallFormAction('submit',this)"
				class="form_button" /> &nbsp; &nbsp; &nbsp; <a
				STYLE="text-decoration: none"
				href="frmJobOrderList.html?saddr=${urlHits}"><input
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

</body>
</html>