<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<%-- <style type="text/css">
	input[type="text"]:valid 
	{
    	color: green;    
	}
	
	input[type="text"]:valid ~ ::before 
	{
	    content: "#10003";
	    color: green;
	}
	
	input[type="text"]:invalid 
	{
	    color: red;
	}
</style> --%>
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
	
	function funSetBankMasterData(bankCode)
	{
	    $("#txtBankCode").val(bankCode);
		var searchurl=getContextPath()+"/loadBankMasterData.html?bankCode="+bankCode;
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	if(response.strBankCode=='Invalid Code')
			        	{
			        		alert("Invalid Bank Code");
			        		$("#txtBankCode").val('');
			        	}
			        	else
			        	{
				        	$("#txtBankName").val(response.strBankName);
				        	$("#txtBankName").focus();
				        	$("#txtBranchName").val(response.strBranch);
				        	$("#txtMICR").val(response.strMICR);
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
			case "bankCode":
			     funSetBankMasterData(code);
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
	<label>Bank Master</label>
	</div>

<br/>
<br/>

	<s:form name="BankMaster" method="POST" action="saveBankMaster.html">

		<table class="masterTable">
			<tr>
			    <td><label >Bank Code</label></td>
			    <td style="width: 230px"><s:input id="txtBankCode" path="strBankCode"  ondblclick="funHelp('bankCode')" cssClass="searchTextBox"/></td>			        			        
			    <td colspan="2"><s:input id="txtBankName" path="strBankName" required="true" cssClass="longTextBox" style="width: 350px"/></td>			    		        			   
			</tr>
			<tr>
				<td><label >Branch</label></td>
				<td ><s:input id="txtBranchName" path="strBranch" required="true" cssClass="longTextBox"/></td>
				<td></td>
				<td></td>	
			</tr>
			<tr>
				<td><label >MICR</label></td>
				<td ><s:input id="txtMICR" path="strMICR" required="true" cssClass="longTextBox"/></td>
				<td></td>
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
