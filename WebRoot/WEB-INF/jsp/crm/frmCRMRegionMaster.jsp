<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
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



	/**
	* Reset The category Name TextField
	**/
	function funResetFields()
	{
		$("#txtRegionDesc").val("");
		$("#txtRegionCode").val("");
		
    }
	
	
		/**
		* Open Help
		**/
		function funHelp(transactionName)
		{	       
	       window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	    }
		
		/**
		* Get and Set data from help file and load data Based on Selection Passing Value(category Code)
		**/
		function funSetData(code)
		{
			$("#txtRegionCode").val(code);
			var searchurl=getContextPath()+"/loadCRMRegionMasterData.html?code="+code;
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        success: function(response)
				        {
				        	if(response.strRegionCode=='Invalid Code')
				        	{
				        		alert("Invalid Manufacturer Code");
				        		$("#txtRegionCode").val('');
				        	}
				        	else
				        	{
					        	$("#txtRegionDesc").val(response.strRegionDesc);
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
</script>


</head>

<body >
	<div id="formHeading">
		<label>Region Master</label>
	</div>
	<s:form name="manufactureForm" method="POST" action="saveCRMRegionMaster.html?saddr=${urlHits}">

		<br />
		<br />
		<table class="masterTable">

			<tr>
				<td width="150px">Region Code</td>
				<td><s:input id="txtRegionCode" path="strRegionCode"
						cssClass="searchTextBox jQKeyboard form-control"  ondblclick="funHelp('crmRegionMaster')" /></td>
			</tr>
			<tr>
				<td><label>Region Desc</label></td>
				<td><s:input colspan="3" type="text" id="txtRegionDesc" 
						path="strRegionDesc" required="true"
						cssStyle="text-transform: uppercase;" cssClass="longTextBox jQKeyboard form-control"  /> 
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
