<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SUBGROUP MASTER</title>

<script type="text/javascript">
//	var fieldName;

	$(function() 
	{
	});

	
	
	
	
	$(document).ready(function () {
		  $('input#txtSubGroupCode').mlKeyboard({layout: 'en_US'});
		  $('input#txtSubGroupName').mlKeyboard({layout: 'en_US'});
		  $('input#txtIncetives').mlKeyboard({layout: 'en_US'});
		}); 

	
	/**
	* Get and Set data from help file and load data Based on Selection Passing Value(User Code)
	**/
	function funSetData(code){
		$("#txtSubGroupCode").val(code);
		var searchurl=getContextPath()+"/loadPOSSubGroupMasterData.html?POSSubGroupCode="+code;
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	if(response.strGCode=='Invalid Code')
			        	{
			        		alert("Invalid Sub Group Code");
			        		$("#txtSubGroupCode").val('');
			        	}
			        	else
			        	{
				        	$("#txtSubGroupCode").val(response.strSubGroupCode);
				        	$("#txtSubGroupName").val(response.strSubGroupName);
				        	$("#txtSubGroupName").focus();
				        	$("#txtIncentives").val(response.strIncetives);
				        //	$("#txtOperational").val(response.strSuper);
				        //	$("#txtOperational").val(response.strOperational);
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

		switch(fieldName){

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
		

		
		
		/**
		*  Check Validation Before Saving Record
		**/
		function funCallFormAction(actionName,object) 
		{
			var flg=true;
			
			if($('#txtSubGroupCode').val()=='')
			{
				var code = $('#txtSubGroupName').val();
				 $.ajax({
				        type: "GET",
				        url: getContextPath()+"/checkSubGroupName.html?SubGroupName="+code,
				        async: false,
				        dataType: "text",
				        success: function(response)
				        {
				        	if(response=="true")
				        		{
				        			alert("Sub group Name Already Exist!");
				        			$('#txtSubGroupName').focus();
				        			flg= false;
					    		}
					    	else
					    		{
					    			flg=true;
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
			
			
			return flg;
		}


		/**
		* Open Help
		**/
	function funHelp(transactionName)
	{
		fieldName=transactionName;
	//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	}
</script>

</head>
<body>

	<div id="formHeading">
	<label>Sub Group Master</label>
	</div>

<br/>
<br/>

	<s:form name="POSSubGroupMaster" method="POST" action="savePOSSubGroup.html">
	
	   <br />
		<br />
		<div id="jquery-script-menu">

		</div>

		<table class="masterTable">
			<tr>
				
				<td width="140px">Sub Group Code</td>
				<td><s:input id="txtSubGroupCode" path="strSubGroupCode"
						cssClass="searchTextBox jQKeyboard form-control" ondblclick="funHelp('POSSubGroupMaster')" /></td>	
				<td></td>		
			</tr>
			
			<tr>	
			   
			    <td ><label>Sub Group Name</label></td>		
				<td><s:input type="text" id="txtSubGroupName" 
						name="txtSubGroupCode" path="strSubGroupName" required="true" 
						cssStyle="text-transform: uppercase; width :200px;" cssClass="longTextBox jQKeyboard form-control"  /> </td>
				<td></td>
			</tr>
			
			<tr>
			<td><label>Group Code</label></td>
				<td><s:select id="cmbGroupCode" name="cmbGroupCode" path="strGroupCode"
				 cssClass="BoxW124px" >  		 
				 </s:select></td>
				<td></td>
			</tr>
			
						
			<tr>	
			   
			 <td ><label>Incentives</label></td>		
		     <td><s:input colspan="3" type="text" id="txtIncentives" 
						name="txtIncentives" path="strIncentives" required="true"
						cssStyle="text-transform: uppercase;width :200px;" cssClass="longTextBox jQKeyboard form-control"   /> </td>
				<td></td>
			</tr>
			
			
		
			<tr>
				<td><s:input colspan="3" type="hidden"  id="txtGroupCode" value="N" name="txtGroupCode" path="strGroupCode"/> </td>
		
			</tr>
			
			<tr>
				<td><s:input colspan="3" type="hidden"  id="txtIncentives" value="N" name="txtIncentives" path="strIncentives"/> </td>
			</tr>
			
		</table>

		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button" />
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>

	</s:form>
</body>
</html>
