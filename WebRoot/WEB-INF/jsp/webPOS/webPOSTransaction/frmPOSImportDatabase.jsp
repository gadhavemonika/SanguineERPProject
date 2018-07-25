<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>GROUP MASTER</title>
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
var flg=false;
/*On form Load It Reset form :Ritesh 22 Nov 2014*/
 $(document).ready(function () {
		  $('input#txtAreaCode').mlKeyboard({layout: 'en_US'});
		  $('input#txtAreaName').mlKeyboard({layout: 'en_US'});
		  
		  $("form").submit(function(event){
			  if(!flg)
				{
					alert("Database Not Connected.");
					return false;
				}
			
			});
		}); 




	/**
	* Reset The Group Name TextField
	**/
	function funResetFields()
	{
		$("#txtAreaName").focus();
		
    }
	
	
	
		/**
		* Success Message After Saving Record
		**/
		$(document).ready(function()
		{
			
			<%if (session.getAttribute("success") != null) 
			{
				 boolean message=false ;
				if(session.getAttribute("successMessage") != null)
				{
				     message = ((Boolean) session.getAttribute("successMessage")).booleanValue();
				    session.removeAttribute("successMessage");
				}
				boolean test = ((Boolean) session.getAttribute("success")).booleanValue();
				session.removeAttribute("success");
				if (test) 
				{
					if(message)
					{
					%>alert("Database Imported Successfully.");<%
					}
					else
					{
						%>alert("Database Import Failed.");<%
					}
				}
			}%>
		});
		
		
		
		
		/**
		*  Check Validation Before Saving Record
		**/
		
		
		function funConnectBtnClicked() 
		{
			
			
				var strDatabaseName = $('#txtDatabaseName').val();
				var strIPAddress = $('#txtIPAddress').val();
				var strPortNo = $('#txtPortNo').val();
				var strUserName = $('#txtUserName').val();
				var strPassword = $('#txtPassword').val();
				 $.ajax({
				        type: "GET",
				        url: getContextPath()+"/ConnectDatabase.html?strDatabaseName="+strDatabaseName+"&strIPAddress="+strIPAddress+"&strPortNo="+strPortNo+"&strUserName="+strUserName+"&strPassword="+strPassword,
				        async: false,
				        dataType: "text",
				        success: function(response)
				        {
				        	if(response=="false")
				        		{
				        			alert("Connection Failed.");
				        			
				        			flg= false;
					    		}
					    	else
					    		{
					    		alert("Connection Successfully.");
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
		<label>Import Database</label>
	</div>
	<s:form name="AreaForm" method="POST" action="importDatabase.html">

		<br />
		<br />
		<table class="masterTable">

			<tr>
				<td width="140px">IP Address</td>
				<td><s:input colspan="3" type="text" id="txtIPAddress" 
						 path="strIPAddress" required="true"
						cssStyle="text-transform: uppercase;" cssClass="longTextBox jQKeyboard form-control"  /></td>
			</tr>
			<tr>
				<td><label>Port No</label></td>
				<td><s:input colspan="3" type="text" id="txtPortNo" 
						name="txtPortNo" path="strPortNo" required="true"
						cssStyle="text-transform: uppercase;" cssClass="longTextBox jQKeyboard form-control"  /> 
				</td>
			</tr>
			
			<tr>
				<td><label>Database Name</label></td>
				<td><s:input colspan="3" type="text" id="txtDatabaseName" 
						name="txtDatabaseName" path="strDatabaseName" required="true"
						cssStyle="text-transform: uppercase;" cssClass="longTextBox jQKeyboard form-control"  /> 
				</td>
			</tr>
			
			<tr>
				<td><label>User Name</label></td>
				<td><s:input colspan="3" type="text" id="txtUserName" 
						path="strUserName" required="true"
						cssStyle="text-transform: uppercase;" cssClass="longTextBox jQKeyboard form-control"  /> 
				</td>
			</tr>
			
			<tr>
				<td><label>Password</label></td>
				<td><s:input colspan="3" type="text" id="txtPassword" 
						 path="strPassword" required="true"
						cssStyle="text-transform: uppercase;" cssClass="longTextBox jQKeyboard form-control"  /> 
				</td>
			</tr>
			
			
		</table>
		<br />
		<br />
		<p align="center">
		<input type="button" value="Connect" class="form_button" onclick="funConnectBtnClicked()"/>
			<input type="submit" value="Import" tabindex="3" class="form_button"/> 
			<input type="reset" value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>
	</s:form>

</body>
</html>
