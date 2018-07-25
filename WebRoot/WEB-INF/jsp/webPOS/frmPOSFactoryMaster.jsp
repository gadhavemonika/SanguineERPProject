<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Factory Master</title>
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

/*On form Load It Reset form :Ritesh 22 Nov 2014*/
 $(document).ready(function () {
		  $('input#txtFactoryCode').mlKeyboard({layout: 'en_US'});
		  $('input#txtFactoryName').mlKeyboard({layout: 'en_US'});
		  
		  $("form").submit(function(event){
			  if($("#txtFactoryName").val().trim()=="")
				{
					alert("Please Enter Factory Name");
					return false;
				}
			  if($("#txtFactoryName").val().length > 30)
				{
					alert("Factory Name length must be less than 30");
					return false;
				}
			  else{
				  flg=funCallFormAction();
				  return flg;
			  }
			});
		}); 
	




	/**
	* Reset The Group Name TextField
	**/
	function funResetFields()
	{
		$("#txtFactoryName").focus();
		
    }
	
	
		/**
		* Open Help
		**/
		function funHelp(transactionName)
		{	       
			window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	    }
		
		/**
		* Get and Set data from help file and load data Based on Selection Passing Value(Group Code)
		**/
		function funSetData(code)
		{
			$("#txtFactoryCode").val(code);
			var searchurl=getContextPath()+"/loadPOSFactoryMasterData.html?POSFactoryCode="+code;
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        success: function(response)
				        {
				        	if(response.strAreaCode=='Invalid Code')
				        	{
				        		alert("Invalid Area Code");
				        		$("#txtFactoryCode").val('');
				        	}
				        	else
				        	{
					        	$("#txtFactoryCode").val(response.strFactoryCode);
					        	$("#txtFactoryName").val(response.strFactoryName);
					        	$("#txtFactoryName").focus();
					        	
					        	
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
		
		
		$(function()
		{
			$('#txtFactoryName').blur(function () {
				 var strFName=$('#txtFactoryName').val();
			      var st = strFName.replace(/\s{2,}/g, ' ');
			      $('#txtFactoryName').val(st);
				});
			
		});
		
	
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
		function funCallFormAction() 
		{
			var flg=true;
			
// 			if($('#txtFactoryCode').val()=='')
// 			{
				var factoryCode = $('#txtFactoryCode').val();
				var factoryName = $('#txtFactoryName').val();
				 $.ajax({
				        type: "GET",
				        url: getContextPath()+"/checkFactoryName.html?strFactoryCode="+factoryCode+"&strFactoryName="+factoryName,
				        async: false,
				        dataType: "text",
				        success: function(response)
				        {
				        	if(response=="false")
				        		{
				        			alert("This Factory Name is Already Exist!");
				        			$('#txtFactoryName').focus();
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
		
			
			
			return flg;
		}
</script>


</head>

<body >
	<div id="formHeading">
		<label>Factory Master</label>
	</div>
	<s:form name="Factory Master" method="POST" action="savePOSFactoryMaster.html?saddr=${urlHits}">

		<br />
		<br />
		<table class="masterTable">

			<tr>
				<td width="140px">Factory Code</td>
				<td><s:input id="txtFactoryCode" path="strFactoryCode"
						cssClass="searchTextBox jQKeyboard form-control" readonly="true" ondblclick="funHelp('POSFactoryMaster')" /></td>
			</tr>
			<tr>
				<td><label>Factory Name</label></td>
				<td><s:input colspan="3" type="text" id="txtFactoryName" 
						name="txtFactoryName" path="strFactoryName" required="true"
						cssStyle="text-transform: uppercase;" cssClass="longTextBox jQKeyboard form-control"  /> 
		</td></tr>
			
			
			
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


