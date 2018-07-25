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
	
	function funSetInterfaceData(interfaceCode)
	{
	    $("#txtInterfaceCode").val(interfaceCode);
		var searchurl=getContextPath()+"/loadInterfaceMasterData.html?interfaceCode="+interfaceCode;
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	if(response.strInterfaceCode=='Invalid Code')
			        	{
			        		alert("Invalid Interface Code");
			        		$("#txtInterfaceCode").val('');
			        	}
			        	else
			        	{
				        	$("#txtInterfaceName").val(response.strInterfaceName);
				        	$("#txtInterfaceName").focus();		
				        	$("#txtAccountCode").val(response.strAccountCode);
				        	$("#txtAccountName").val(response.strAccountName);
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
	
	function funSetAccountData(accountCode)
	{
	    $("#txtAccountCode").val(accountCode);
		var searchurl=getContextPath()+"/loadAccountMasterData.html?accountCode="+accountCode;
		$.ajax({
	        type: "GET",
	        url: searchurl,
	        dataType: "json",
	        success: function(response)
	        {
	        	if(response.strAccountCode=='Invalid Code')
	        	{
	        		alert("Invalid Account Code");
	        		$("#txtAccountCode").val('');
	        	}
	        	else
	        	{
		        	$("#txtAccountName").val(response.strAccountName);
		        	$("#txtAccountName").focus();						        							        	
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
			case "interfaceCode":
			    funSetInterfaceData(code);			    
				 break;
				 
				case "accountCode":
			         funSetAccountData(code);						
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
	<label>Interface Master</label>
	</div>

<br/>
<br/>

	<s:form name="InterfaceMaster" method="POST" action="saveInterfaceMaster.html">

		<table class="masterTable">
			<tr>
			    <td><label >Interface Code</label></td>			    
                <td><label >Interface Name</label></td>
                <td></td>			    			                   
                <td><label >Account Code</label></td>
                <td><label >Account Name</label></td>
                <td></td>			    			        			        			    			    		        			  
			</tr>
			<tr>				
			    <td><s:input id="txtInterfaceCode" path="strInterfaceCode" readonly="true" ondblclick="funHelp('interfaceCode')" cssClass="searchTextBox"/></td>			   	
				<td><s:input id="txtInterfaceName" path="strInterfaceName" required="true" cssClass="longTextBox"/></td>
				<td></td>						
				<td><s:input id="txtAccountCode" path="strAccountCode" readonly="true" ondblclick="funHelp('accountCode')" cssClass="searchTextBox"/></td>
				<td ><s:input id="txtAccountName" path="strAccountName" required="true" readonly="true" cssClass="longTextBox"/></td>
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
