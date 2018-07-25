<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<script type="text/javascript">
	var fieldName;

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
	
	function funSetAccountsGroupData(groupCode)
	{
	    $("#txtGroupCode").val(groupCode);
		var searchurl=getContextPath()+"/loadACGroupMasterData.html?acGroupCode="+groupCode;
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	if(response.strBankCode=='Invalid Code')
			        	{
			        		alert("Invalid Group Code");
			        		$("#txtGroupCode").val('');
			        	}
			        	else
			        	{
				        	$("#txtGroupName").val(response.strGroupName);
				        	$("#txtGroupName").focus();
				        	$("#txtShortName").val(response.strShortName);
				        	$("#cmbCategory").val(response.strCategory);
				        	$("#cmbDefaultType").val(response.strDefaultType);
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

	function funSetData(code){

		switch(fieldName)
		{		
			case "acGroupCode":
			       funSetAccountsGroupData(code);
				 break;
		}
	}

	function funHelp(transactionName)
	{
		fieldName=transactionName;
	//	window.showModalDialog("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
		window.open("searchform.html?formname="+transactionName+"&searchText=","","dialogHeight:600px;dialogWidth:600px;dialogLeft:400px;");
	}
</script>

</head>
<body>

	<div id="formHeading">
	<label>Group Master</label>
	</div>

<br/>
<br/>

	<s:form name="BankMaster" method="POST" action="saveACGroupMaster.html">

		<table class="masterTable">
			<tr>
			    <td><label >Group Code</label></td>
			    <td><s:input id="txtGroupCode" path="strGroupCode"  ondblclick="funHelp('acGroupCode')" cssClass="searchTextBox"/></td>			        			        
			    <td><s:input id="txtGroupName" path="strGroupName" required="true" cssClass="longTextBox"/></td>			    			    			    		        			 
			</tr>
			<tr>	
				<td><label>Short Name</label></td>
				<td><s:input id="txtShortName" path="strShortName" required="true" cssClass="longTextBox"/></td>
				<td></td>				
			</tr>
			<tr>
				<td><label>Category</label> </td>
				<td><s:select id="cmbCategory" path="strCategory" items="${listCategory}" cssClass="BoxW124px"/></td>		
				<td></td>					
			</tr>
			<tr>
				<td><label>Default Type</label> </td>
				<td><s:select id="cmbDefaultType" path="strDefaultType" items="${listDefaultType}" cssClass="BoxW124px"/></td>
				<td></td>					
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
