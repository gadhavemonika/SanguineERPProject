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
	
	function funSetSanctionAutherityData(sanctionCode)
	{
	    $("#txtSanctionCode").val(sanctionCode);
		var searchurl=getContextPath()+"/loadSanctionAutherityMasterData.html?sanctionCode="+sanctionCode;
		 $.ajax({
			        type: "GET",
			        url: searchurl,
			        dataType: "json",
			        success: function(response)
			        {
			        	if(response.strSanctionCode=='Invalid Code')
			        	{
			        		alert("Invalid Sanction Code");
			        		$("#txtSanctionCode").val('');
			        	}
			        	else
			        	{
				        	$("#txtSanctionName").val(response.strSanctionName);
				        	$("#txtSanctionName").focus();	
				        	$("#cmbOperational").val(response.strOperational);						        	
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
			case "sanctionCode" :
			    funSetSanctionAutherityData(code);			    
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
	<label>Sanction Autherity Master</label>
	</div>

<br/>
<br/>

	<s:form name="SanctionAutherityMaster" method="POST" action="saveSanctionAutherityMaster.html">

		<table class="masterTable">
			<tr>
			    <td><label>Sanction Code</label></td>
			    <td width="125px"><s:input id="txtSanctionCode" path="strSanctionCode" readonly="true" ondblclick="funHelp('sanctionCode')" cssClass="searchTextBox"/></td>			    			    			        			        			    			    		        			 
			    <td><s:input id="txtSanctionName" path="strSanctionName" required="true" cssClass="longTextBox"  cssStyle="width:75%"/></td>
			</tr>			
			<tr>
				<td><label>Operational</label></td>				
				<td><s:select id="cmbOperational" path="strOperational" items="${listOperational}" cssClass="BoxW124px"/></td>
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
