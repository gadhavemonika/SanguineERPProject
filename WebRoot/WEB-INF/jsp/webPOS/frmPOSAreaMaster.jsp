<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>



<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>AREA MASTER</title>
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

 $(document).ready(function () {
		  $('input#txtAreaCode').mlKeyboard({layout: 'en_US'});
		  $('input#txtAreaName').mlKeyboard({layout: 'en_US'});
		  
		  $("form").submit(function(event){
			  if($("#txtAreaName").val().trim()=="")
				{
					alert("Please Enter Area Name");
					return false;
				}
			  else{
				  flg=funCallFormAction();
				  return flg;
			  }
			});
		}); 




	/**
	* Reset  Form
	**/
	function funResetFields()
	{
		$("#txtAreaName").focus();
		
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
			$("#txtAreaCode").val(code);
			var searchurl=getContextPath()+"/loadPOSAreaMasterData.html?POSAreaCode="+code;
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        success: function(response)
				        {
				        	if(response.strAreaCode=='Invalid Code')
				        	{
				        		alert("Invalid Area Code");
				        		$("#txtAreaCode").val('');
				        	}
				        	else
				        	{
					        	$("#txtAreaCode").val(response.strAreaCode);
					        	$("#txtAreaName").val(response.strAreaName);
					        	$("#txtAreaName").focus();
					        	$("#cmbPOSName").val(response.strPOSName);
					        	
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
			
			var name = $('#txtAreaName').val();
			var code= $('#txtAreaCode').val();
			
				 $.ajax({
				        type: "GET",
				        url: getContextPath()+"/checkAreaName.html?areaName="+name+"&areaCode="+code,
				        async: false,
				        dataType: "text",
				        success: function(response)
				        {
				        	if(response=="false")
				        		{
				        			alert("Area Name Already Exist!");
				        			$('#txtAreaName').focus();
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
		<label>Area Master</label>
	</div>
	<s:form name="AreaForm" method="POST" action="savePOSAreaMaster.html">

		<br />
		<br />
		<table class="masterTable">

			<tr>
				<td width="140px">Area Code</td>
				<td><s:input id="txtAreaCode" path="strAreaCode"
						cssClass="searchTextBox jQKeyboard form-control" readonly="true" ondblclick="funHelp('POSAreaMaster')" /></td>
			</tr>
			<tr>
				<td><label>Area Name</label></td>
				<td><s:input colspan="3" type="text" id="txtAreaName" 
						name="txtAreaName" path="strAreaName" required="true"
						cssStyle="text-transform: uppercase;" cssClass="longTextBox jQKeyboard form-control"  /> 
		
			<tr>
			<td><label>POS Name</label></td>
				<td><s:select id="cmbPOSName" name="cmbPOSName" path="strPOSName" items="${posList}" cssClass="BoxW124px" />
				</td>
				
			</tr>
			
			<tr>
				<td></td>
				<td></td>
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
