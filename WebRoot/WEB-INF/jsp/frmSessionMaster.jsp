<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> <!-- charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> -->
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%-- <script type="text/javascript" src="<spring:url value="/resources/js/hindiTextBox.js"/>"></script> --%>
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
<%-- <script src="http://www.hinkhoj.com/common/js/keyboard.js"></script> --%>
 <%-- <script src="hindiTyping.js"></script> --%>
         <link rel="stylesheet" type="text/css" href="http://www.hinkhoj.com/common/css/keyboard.css" /> 
  
<script type="text/javascript">

/*On form Load It Reset form :Ritesh 22 Nov 2014*/
 $(document).ready(function () {
    resetForms('sessionForm');
    $("#txtSessionName").focus();
}); 


	/**
	* Reset The Session Name TextField
	**/
	function funResetFields()
	{
		$("#txtSessionName").focus();
    }
	
	
		/**
		* Open Help
		**/
		function funHelp(transactionName)
		{	       
	       // window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	       window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;")
	    }
		
		/**
		* Get and Set data from help file and load data Based on Selection Passing Value(Group Code)
		**/
		function funSetData(code)
		{
			$("#txtSessionCode").val(code);
			var searchurl=getContextPath()+"/loadSessionMasterData.html?sessionCode="+code;
			 $.ajax({
				        type: "GET",
				        url: searchurl,
				        dataType: "json",
				        success: function(response)
				        {
				        	if(response.strGCode=='Invalid Code')
				        	{
				        		alert("Invalid Group Code");
				        		$("#txtSessionCode").val('');
				        	}
				        	else
				        	{
					        	$("#txtSessionName").val(response.strSessionName);
					        	$("#txSessionDesc").val(response.strSDesc);
					        	$("#txtSessionName").focus();
					        
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
			<%if (session.getAttribute("success") != null) {
				            if(session.getAttribute("successMessage") != null){%>
				            message='<%=session.getAttribute("successMessage").toString()%>';
				            <%
				            session.removeAttribute("successMessage");
				            }
							boolean test = ((Boolean) session.getAttribute("success")).booleanValue();
							session.removeAttribute("success");
							if (test) {
							%>	
				alert("Data Save successfully\n\n"+message);
			<%
			}}%>

		});
		


		
</script>


</head>

<body >
	<div id="formHeading">
		<label>Session Master</label>
	</div>
	<s:form name="sessionForm" method="POST" action="saveSessionMaster.html?saddr=${urlHits}">

		<br />
		<br />
		<table class="masterTable">

			<tr>
				<td width="140px">Session Code</td>
				<td><s:input id="txtSessionCode" path="strSessionCode"
						cssClass="searchTextBox" ondblclick="funHelp('session')" /></td>
			</tr>
			<tr>
				<td><label>Session Name</label></td>
				<td><s:input colspan="3" type="text" id="txtSessionName" 
						name="txtSessionName" path="strSessionName" required="true"
						cssStyle="text-transform: uppercase;" cssClass="longTextBox"  /> <s:errors path="strSessionName"></s:errors></td>
			</tr>
			<tr>
				<td><label>Description </label></td>
				<td><s:input colspan="3" id="txSessionDesc" name="txSessionDesc"
						cssStyle="text-transform: uppercase;" path="strSDesc" cssClass="longTextBox" autocomplete="off" /> </td>
			</tr>
			<%-- <tr>
				<td><label>Hindi Name</label></td>
					<td> 
					<script type="text/javascript">
	            		CreateHindiTextBox("marathiName",60);
	        		</script>
	       		 </td>
			</tr> --%>
			<tr>
			<%-- <td><s:input type="hidden" id="hidMarathiText" path="strMarathiText"></s:input> --%>
			
			
			</tr>
		</table>
		<br />
		<br />
		<p align="center">
			<input type="submit" value="Submit" tabindex="3" class="form_button"
				onclick="return funCallFormAction('submit',this);" /> <input type="reset"
				value="Reset" class="form_button" onclick="funResetFields()"/>
		</p>
	</s:form>

</body>
</html>