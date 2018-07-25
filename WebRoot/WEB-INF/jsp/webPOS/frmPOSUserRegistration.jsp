<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>USER MASTER</title>
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

var userType;
/*On form Load It Reset form :Ritesh 22 Nov 2014*/
 $(document).ready(function () {
		  $('input#txtUserCode').mlKeyboard({layout: 'en_US'});
		   
		  $(".tab_content").hide();
			$(".tab_content:first").show();

			$("ul.tabs li").click(function() {
				$("ul.tabs li").removeClass("active");
				$(this).addClass("active");
				$(".tab_content").hide();

				var activeTab = $(this).attr("data-state");
				$("#" + activeTab).fadeIn();
			});
		}); 




	
	
		/**
		* Open Help
		**/
		function funHelp(transactionName)
		{
		   funLoadData(); 
	       window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	    }
		
		/**
		* Get and Set data from help file and load data Based on Selection Passing Value(Group Code)
		**/
		function funSetData(code)
		{   
			$("#txtUserCode").val(code);
			var searchurl=getContextPath()+"/loadWebStockUserMasterData.html?UserCode="+code;
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        success: function(response)
				        {
				        	if(response.strCustomerTypeMasterCode=='Invalid Code')
				        	{
				        		alert("Invalid Customer Type Code");
				        		$("#txtUserCode").val('');
				        	}
				        	else
				        	{
					        	$("#txtUserName").val(response.strUserName);
					        	$("#txtUserType").val(response.strUserType);
					        	alert(response.strUserType);
					        	if(response.strUserType=='YES')
				        		{
					        		
					        		//fill Settle Table
					        		var table = document.getElementById("tblMastersTab");
					        		var rowCount = table.rows.length;
					        		$.each(table.rows,function(j,row)
							        		{   
							        			var id='chkMasterGrantApplicable.'+j;
							        			document.getElementById(id).checked="checked";	            				            	
							        		}); 
					        		
						        	
						        	table = document.getElementById("tblUtilitiesTab");
					        		rowCount = table.rows.length;
					        		$.each(table.rows,function(j,row)
							        		{   
							        			var id='chkUtilitiesGrantApplicable.'+j;
							        			document.getElementById(id).checked="checked";	            				            	
							        		}); 
					        		
					        		
					        		table = document.getElementById("tblTransactionsTab");
					        		rowCount = table.rows.length;
					        		$.each(table.rows,function(j,row)
							        		{   
							        			var id='chkGrantApplicable.'+j;
							        			document.getElementById(id).checked="checked";	
							        			id='chkTLAApplicable.'+j;
							        			document.getElementById(id).checked="checked";
							        			id='chkEnableAditing.'+j;
							        			document.getElementById(id).checked="checked";
							        		}); 
						        	
					        		table = document.getElementById("tblReportsTab");
					        		rowCount = table.rows.length;
					        		$.each(table.rows,function(j,row)
							        		{   
							        			var id='chkReportGrantApplicable.'+j;
							        			document.getElementById(id).checked="checked";	            				            	
							        		}); 
					        		
					        		
				        		}
					        	else
					        		{
					        		   funLoadUserModuleTableData(code)
					        		}
				        		
					        	
					        	
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
		
		function funLoadData()
		{
			//document.getElementById("txtAmount").disabled = true;
			//document.getElementById("cmbItemType").disabled = true;
			
			funLoadTableData("M","tblMastersTab");
			funLoadTableData("T","tblTransactionsTab");
			funLoadTableData("U","tblUtilitiesTab");
			funLoadTableData("R","tblReportsTab");
			
		}
		
		function funLoadUserModuleTableData(code)
		{
			var searchurl=getContextPath()+"/loadUsersModuleData.html?userCode="+code;
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        
				        success: function (response) 
				        {
							$("#txtUserName").val(response.strUserName);
							$("#txtUserType").val(response.strUserType);
							$.each(response.listUsersSelectedForms,function(i,item)
							{
								var table = document.getElementById("tblMastersTab");
								$.each(table.rows,function(j,row)
								{
					        		if( document.getElementById('txtMasterFormName.'+j).value==item.strFormName)
					        			{
					        			  if(item.strGrant=='true')
							        		{
					        				  var id='chkMasterGrantApplicable.'+j;
						        			  document.getElementById(id).checked="checked";
							        		}
					        			}	            				            	
					        	}); 
								
								table = document.getElementById("tblTransactionsTab");
								$.each(table.rows,function(j,row)
								{
					        		if( document.getElementById('txtTransactionFormName.'+j).value==item.strFormName)
					        			{
					        			  if(item.strGrant=='true')
							        		{
					        				  var id='chkGrantApplicable.'+j;
						        			  document.getElementById(id).checked="checked";
							        		}
					        			  if(item.strTLA=='true')
							        		{
					        				  var id='chkTLAApplicable.'+j;
						        			  document.getElementById(id).checked="checked";
							        		}
					        			  if(item.strAuditing=='true')
							        		{
					        				  var id='chkEnableAditing.'+j;
						        			  document.getElementById(id).checked="checked";
							        		}
					        			}	            				            	
					        	}); 
								
								table = document.getElementById("tblReportsTab");
								$.each(table.rows,function(j,row)
								{
					        		if( document.getElementById('txtReportFormName.'+j).value==item.strFormName)
					        			{
					        			  if(item.strGrant=='true')
							        		{
					        				  var id='chkReportGrantApplicable.'+j;
						        			  document.getElementById(id).checked="checked";
							        		}
					        			}	            				            	
					        	}); 
								
								table = document.getElementById("tblUtilitiesTab");
								$.each(table.rows,function(j,row)
								{
					        		if( document.getElementById('txtUtilitiesFormName.'+j).value==item.strFormName)
					        			{
					        			  if(item.strGrant=='true')
							        		{
					        				  var id='chkUtilitiesGrantApplicable.'+j;
						        			  document.getElementById(id).checked="checked";
							        		}
					        			}	            				            	
					        	}); 
								
								
							});
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
		
		
		
		function funLoadTableData(moduleType,tableName)
		{
			var searchurl=getContextPath()+"/LoadMasterModuleData.html?ModuleType="+moduleType;
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        
				        success: function (response) 
				        {
				        	funRemoveTableRows(tableName);
				        	 $.each(response,function(i,item)
						            	{
				        		           if (moduleType=="T")
						            	   {
						            		    funfillTransactionTableDetail(item.strModuleName,item.strModuleType);
						            	   }
						            	   else
						            	   {
						            		    funfillTableDetail(item.strModuleName,item.strModuleType,tableName); 
						            	   }
						            	});
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
		
		
		
		
			 
		function funRemoveTableRows(tableId)
		{
			var table = document.getElementById(tableId);
			var rowCount = table.rows.length;
			while(rowCount>0)
			{
				table.deleteRow(0);
				rowCount--;
			}
		}
		
		function funfillTableDetail(strModuleName,strModuleType,tableName)
		{
			var table = document.getElementById(tableName);
			var rowCount = table.rows.length;
			var row = table.insertRow(rowCount);
			
			if (strModuleType=="M")
				{
				  row.insertCell(0).innerHTML= "<input name=\"listMasterForm["+(rowCount)+"].strFormName\" size=\"35%\" readonly=\"readonly\" class=\"Box \" id=\"txtMasterFormName."+(rowCount)+"\" value='"+strModuleName+"'>";
			      row.insertCell(1).innerHTML= "<input type=\"checkbox\" name=\"listMasterForm["+(rowCount)+"].strGrant\" size=\"30%\" id=\"chkMasterGrantApplicable."+(rowCount)+"\" value='"+true+"'>";
				}
			else if(strModuleType=="R")
			   {
				row.insertCell(0).innerHTML= "<input name=\"listReportForm["+(rowCount)+"].strFormName\" size=\"35%\" readonly=\"readonly\" class=\"Box \" id=\"txtReportFormName."+(rowCount)+"\" value='"+strModuleName+"'>";
			      row.insertCell(1).innerHTML= "<input type=\"checkbox\" name=\"listReportForm["+(rowCount)+"].strGrant\" size=\"30%\" id=\"chkReportGrantApplicable."+(rowCount)+"\" value='"+true+"'>";
			   }                                            
			else if(strModuleType=="U")
			   {
				  row.insertCell(0).innerHTML= "<input name=\"listUtilitiesForm["+(rowCount)+"].strFormName\" size=\"35%\" readonly=\"readonly\" class=\"Box \" id=\"txtUtilitiesFormName."+(rowCount)+"\" value='"+strModuleName+"'>";
			      row.insertCell(1).innerHTML= "<input type=\"checkbox\" name=\"listUtilitiesForm["+(rowCount)+"].strGrant\" size=\"30%\" id=\"chkUtilitiesGrantApplicable."+(rowCount)+"\" value='"+true+"'>";
			   }
		}
		
		function funfillTransactionTableDetail(strModuleName,strModuleType)
		{
			var table = document.getElementById("tblTransactionsTab");
			var rowCount = table.rows.length;
			var row = table.insertRow(rowCount);
			  
			 row.insertCell(0).innerHTML= "<input name=\"listTransactionForm["+(rowCount)+"].strFormName\" size=\"35%\" readonly=\"readonly\" class=\"Box \" id=\"txtTransactionFormName."+(rowCount)+"\" value='"+strModuleName+"'>";
		     row.insertCell(1).innerHTML= "<input type=\"checkbox\" name=\"listTransactionForm["+(rowCount)+"].strGrant\" size=\"20%\" id=\"chkGrantApplicable."+(rowCount)+"\" value='"+true+"'>";
		     row.insertCell(2).innerHTML= "<input type=\"checkbox\" name=\"listTransactionForm["+(rowCount)+"].strTLA\" size=\"20%\" id=\"chkTLAApplicable."+(rowCount)+"\" value='"+true+"'>";
		     row.insertCell(3).innerHTML= "<input type=\"checkbox\" name=\"listTransactionForm["+(rowCount)+"].strAuditing\" size=\"20%\" id=\"chkEnableAditing."+(rowCount)+"\" value='"+true+"'>";
			  

		}
	
		/**
		* Success Message After Saving Record
		**/
		
		$(document).ready(function()
		{
			var message='';
			<%if (session.getAttribute("success") != null) 
			{
				if(session.getAttribute("successMessage") != null)
				{%>
					message='<%=session.getAttribute("successMessage").toString()%>';
				    <%
				    session.removeAttribute("successMessage");
				}
				boolean test = ((Boolean) session.getAttribute("success")).booleanValue();
				session.removeAttribute("success");
				if (test) 
				{
					%>alert("Data Saved \n\n"+message);<%
				}
			}%>
		});
		

</script>


</head>

<body onload="funLoadData()" >
	<div id="formHeading">
		<label>User Master</label>
	</div>
	<s:form name="UserRegistrationForm" method="POST" action="saveUsersAccess.html?saddr=${urlHits}">
                                       
		<br />
		<br />
		
		<table class="masterTable">
		  <tr>
		      <td colspan="1" >User Code
			  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			  <s:input id="txtUserCode" path="strUserCode"
					   cssClass="searchTextBox jQKeyboard form-control" readonly="true" ondblclick="funHelp('webstockusermaster')" />
		 </tr>
           <tr>
              
		      <td colspan="1" >User Name
			  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			  <s:input id="txtUserName" path="strUserName" cssClass="BoxW116px" required="true"  />
			  <s:input type="hidden" id="txtUserType" path="strUserType" />
			  <br>
			  <br>
			 </td>	  
			  
		</tr>
		 
        
		 
		 
		  <tr>
			 <td width="900px">
                 <ul class="tabs">
				  <li class="active" data-state="tab1">Masters</li>
				  <li data-state="tab2">Transaction</li>
				  <li data-state="tab3">Utilities</li>
				  <li data-state="tab4">Reports</li>
	
				 </ul>
					
					<div id="tab1" class="tab_content">
					    <table border="1" class="myTable" style="width:100%;margin: auto;"  >
								
								<tr>
								<td style="width:92%">Module Name</td>
								<td style="width:10%">Grant</td>
								</tr>
			            </table>
			            <div style="background-color: #a4d7ff;border: 1px solid #ccc;display: block; height: 500px;
		    				   margin:auto;overflow-x: hidden; overflow-y: scroll;width: 100%;">
								<table id="tblMastersTab" class="transTablex col5-center" style="width:100%;">
								<tbody>    
										<col style="width:95%"><!--  COl1   -->
										<col style="width:5%"><!--  COl2   -->
																		
								</tbody>							
								</table>
						</div>
					</div>
					
					<div id="tab2" class="tab_content">
					    <table border="1" class="myTable" style="width:100%;margin: auto;"  >
								
								<tr>
								<td style="width:85%">Module Name</td>
								<td style="width:4%">Grant</td>
								<td style="width:6%">Transaction Level Authentication</td>
								<td style="width:7%">Enable Auditing</td>
								</tr>
			            </table>
			            <div style="background-color: #a4d7ff;border: 1px solid #ccc;display: block; height: 500px;
		    				   margin:auto;overflow-x: hidden; overflow-y: scroll;width: 100%;">
								<table id="tblTransactionsTab" class="transTablex col5-center" style="width:100%;">
								<tbody>    
										<col style="width:85%"><!--  COl1   -->
										<col style="width:3%"><!--  COl2   -->
										<col style="width:9%"><!--  COl3   -->
										<col style="width:7%"><!--  COl4   -->
										
																		
								</tbody>							
								</table>
						</div>
					</div>
					
					
					<div id="tab3" class="tab_content">
					    <table border="1" class="myTable" style="width:100%;margin: auto;"  >
								
								<tr>
								<td style="width:92%">Module Name</td>
								<td style="width:10%">Grant</td>
								</tr>
			            </table>
			            <div style="background-color: #a4d7ff;border: 1px solid #ccc;display: block; height: 500px;
		    				   margin:auto;overflow-x: hidden; overflow-y: scroll;width: 100%;">
								<table id="tblUtilitiesTab" class="transTablex col5-center" style="width:100%;">
								<tbody>    
										<col style="width:95%"><!--  COl1   -->
										<col style="width:5%"><!--  COl2   -->
																		
								</tbody>							
								</table>
						</div>
					</div>
					
					<div id="tab4" class="tab_content">
					    <table border="1" class="myTable" style="width:100%;margin: auto;"  >
								
								<tr>
								<td style="width:92%">Module Name</td>
								<td style="width:10%">Grant</td>
								</tr>
			            </table>
			            <div style="background-color: #a4d7ff;border: 1px solid #ccc;display: block; height: 500px;
		    				   margin:auto;overflow-x: hidden; overflow-y: scroll;width: 100%;">
								<table id="tblReportsTab" class="transTablex col5-center" style="width:100%;">
								<tbody>    
										<col style="width:95%"><!--  COl1   -->
										<col style="width:5%"><!--  COl2   -->
																		
								</tbody>							
								</table>
						</div>
					</div>
					
				  	
               </td>
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