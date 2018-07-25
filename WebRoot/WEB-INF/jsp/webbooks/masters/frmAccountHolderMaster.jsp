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
	
	function funSetAccountHolderData(accountHolderCode)
	{
	    $("#txtACHolderCode").val(accountHolderCode);
		var searchurl=getContextPath()+"/loadACHolderMasterData.html?acHolderCode="+accountHolderCode;
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	if(response.strBankCode=='Invalid Code')
			        	{
			        		alert("Invalid Account Holder Code");
			        		$("#txtACHolderCode").val('');
			        	}
			        	else
			        	{
				        	$("#txtACHolderName").val(response.strACHolderName);
				        	$("#txtACHolderName").focus();	
				        	$("#txtDesignation").val(response.strDesignation);
				        	$("#txtMobileNumber").val(response.intMobileNumber);
				        	$("#txtEmailId").val(response.strEmailId);
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
			case "acHolderCode":
			     funSetAccountHolderData(code);
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
	<label> Account Holder Master</label>
	</div>

<br/>
<br/>

	<s:form name="AccountHolderMaster" method="POST" action="saveAccountHolderMaster.html">

		<table class="masterTable">
			<tr>
			    <td style="width: 125px;"><label >Account Holder Code</label></td>
			    <td><s:input id="txtACHolderCode" path="strACHolderCode" readonly="true" ondblclick="funHelp('acHolderCode')" cssClass="searchTextBox"/></td>			        			        
			    <td><s:input id="txtACHolderName" path="strACHolderName" required="true" cssClass="longTextBox"  style="width: 350px;"/></td>			    		        			   
			</tr>	
			<tr>
				<th></th>	
				<th style="padding-left: 125px;"><label style="font-style: italic;">Contact Details</label></th>
				<th></th>			
			</tr>		
			<tr>
				<td><label>Designation</label></td>
				<td><s:input id="txtDesignation" path="strDesignation" required="true" cssClass="longTextBox" style="width: 200px;"/></td>
				<td></td>
			</tr>
			<tr>
				<td><label>Mobile No.</label></td>
				<td ><s:input id="txtMobileNumber" path="intMobileNumber" required="true" cssClass="longTextBox"/></td>
				<td></td>
			</tr>
			<tr>
				<td><label>Email Id</label></td>
				<td ><s:input id="txtEmailId" path="strEmailId" required="true" cssClass="longTextBox" style="width: 200px;"/></td>
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
